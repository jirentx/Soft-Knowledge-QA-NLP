package com.word2vec.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WordNeuron extends Neuron {
  public String name;
  public double[] syn0 = null; // input->hidden
  public List<Neuron> neurons