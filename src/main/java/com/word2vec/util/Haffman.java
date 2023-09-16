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

  private TreeSet