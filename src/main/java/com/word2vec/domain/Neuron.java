package com.word2vec.domain;

public abstract class Neuron implements Comparable<Neuron> {
  public double freq;
  public Neuron parent;
  public int code;
  // 语料预分类
  public int category = -1;

  public int