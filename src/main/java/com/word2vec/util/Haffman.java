package com.word2vec.util;

import java.util.Collection;
import java.util.TreeSet;

import com.word2vec.domain.HiddenNeuron;
import com.word2vec.domain.Neuron;

/**
 * 构建Haffman编码树
 * 
 * @author ansj
 *
 */
public class Haffman {
  private int layerSize;

  public Haffman(int layerSize) {
    this.layerSize = layerSize;
  }

  private TreeSet<Neuron> set = new TreeSet<>();

  public void make(Collection<Neuron> neurons) {
    set.addAll(neurons);
    while (set.size() > 1) {
      merger();
    }
  }

  private void merger() {
    HiddenNeuron hn = new HiddenNeuron(layerSize);
    Neuron min1 = set.pollFirst();
    Neuron min2 = set.pollFirst();
    hn.category = min2.category;
    hn.freq = min1.freq + min2.freq;
    min1.par