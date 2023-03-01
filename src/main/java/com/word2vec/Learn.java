
package com.word2vec;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.word2vec.domain.HiddenNeuron;
import com.word2vec.domain.Neuron;
import com.word2vec.domain.WordNeuron;
import com.word2vec.util.Haffman;
import com.word2vec.util.MapCount;

public class Learn {

  private Map<String, Neuron> wordMap = new HashMap<>();
  /**
   * 训练多少个特征
   */
  private int layerSize = 50;

  /**
   * 上下文窗口大小
   */
  private int window = 5;

  private double sample = 1e-3;
  private double alpha = 0.025;
  private double startingAlpha = alpha;

  public int EXP_TABLE_SIZE = 1000;

  private Boolean isCbow = false;

  private double[] expTable = new double[EXP_TABLE_SIZE];

  private int trainWordsCount = 0;

  private int MAX_EXP = 6;

  public Learn(Boolean isCbow, Integer layerSize, Integer window, Double alpha,
      Double sample) {
    createExpTable();
    if (isCbow != null) {
      this.isCbow = isCbow;
    }
    if (layerSize != null)
      this.layerSize = layerSize;
    if (window != null)
      this.window = window;
    if (alpha != null)
      this.alpha = alpha;
    if (sample != null)
      this.sample = sample;
  }

  public Learn() {
    createExpTable();
  }

  /**
   * trainModel
   * 
   * @throws IOException
   */
  private void trainModel(File file) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(file)))) {
      String temp = null;
      long nextRandom = 5;
      int wordCount = 0;
      int lastWordCount = 0;
      int wordCountActual = 0;
      while ((temp = br.readLine()) != null) {
        if (wordCount - lastWordCount > 10000) {
          System.out.println("alpha:" + alpha + "\tProgress: "
              + (int) (wordCountActual / (double) (trainWordsCount + 1) * 100)
              + "%");
          wordCountActual += wordCount - lastWordCount;
          lastWordCount = wordCount;
          alpha = startingAlpha
              * (1 - wordCountActual / (double) (trainWordsCount + 1));
          if (alpha < startingAlpha * 0.0001) {
            alpha = startingAlpha * 0.0001;
          }
        }
        String[] strs = temp.split(" ");
        wordCount += strs.length;
        List<WordNeuron> sentence = new ArrayList<WordNeuron>();
        for (int i = 0; i < strs.length; i++) {
          Neuron entry = wordMap.get(strs[i]);
          if (entry == null) {
            continue;
          }
          // The subsampling randomly discards frequent words while keeping the
          // ranking same
          if (sample > 0) {
            double ran = (Math.sqrt(entry.freq / (sample * trainWordsCount)) + 1)
                * (sample * trainWordsCount) / entry.freq;
            nextRandom = nextRandom * 25214903917L + 11;
            if (ran < (nextRandom & 0xFFFF) / (double) 65536) {
              continue;
            }
          }
          sentence.add((WordNeuron) entry);
        }

        for (int index = 0; index < sentence.size(); index++) {
          nextRandom = nextRandom * 25214903917L + 11;
          if (isCbow) {
            cbowGram(index, sentence, (int) nextRandom % window);
          } else {
            skipGram(index, sentence, (int) nextRandom % window);
          }
        }

      }
      System.out.println("Vocab size: " + wordMap.size());
      System.out.println("Words in train file: " + trainWordsCount);
      System.out.println("sucess train over!");
    }
  }

  /**
   * skip gram 模型训练
   * 
   * @param sentence
   * @param neu1
   */
  private void skipGram(int index, List<WordNeuron> sentence, int b) {
    // TODO Auto-generated method stub