package com.word2vec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.word2vec.domain.WordEntry;

public class Word2VEC {

	public static void main(String[] args) throws IOException {
		 Learn learn1 = new Learn();
		 learn1.learnFile(new File("D:/NLP/精准度测试集1-分词.txt"));
		 learn1.saveModel(new File("D:/NLP/精准度测试集model2.txt"));

		 Learn learn = new Learn();
		 learn.learnFile(new File("D:/NLP/法律-