package com.cnn.cnnn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.analysis.tokenattributes.FlagsAttribute;
import org.apache.lucene.search.ScoreCachingWrappingScorer;

import com.cnn.entity.Cnn;
import com.cnn.utils.CnnUtils;
import com.word2vec.Word2VEC;


public class CnnLayer {
	public static void main(String[] args) throws IOException {
//		long startTime = System.currentTimeMillis();
//		String str0 = "哪些交通违规行为可以不被处罚";
//		String str1 = "机动车驾驶人的哪些驾驶行为会被扣分";
//		
//		mainLayer(str0, str1);
//		long endTime = System.currentTimeMillis();
//		System.out.p