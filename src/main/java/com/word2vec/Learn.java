
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