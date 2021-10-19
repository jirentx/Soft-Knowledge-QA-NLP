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
//		System.out.println("程序执行时间"+(long)(endTime - startTime));
		
		
		@SuppressWarnings("resource")
		//Scanner sc = new Scanner(System.in);
		//String input = sc.nextLine();
		String input = "在小区里养鸡的是什么心态跟谁投诉";
//		float a  = mainLayer(input, "在小区里养鸡的是什么心态跟谁投诉");
//		System.err.println(a);
		File file = new File("D:/NLP/法律-原始文档.txt");
		String encoding = "utf-8";
		if (file.isFile() && file.exists()) {
			//float a  = mainLayer(input, "在小区里养鸡的是什么心态跟谁投诉");
			HashMap<String , Float> score = new HashMap<String, Float>();
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			float sco = 0;
			int flag = 0;
			while ((lineTxt = bufferedReader.readLine()) != null) {		
				sco = mainLayer("我看电影看了八十块钱", "那场电影要看我八十块钱");
				score.put(lineTxt, sco);
				flag++;
			}
			read.close();	
			System.err.println("句子相似度为：" + score);
			float a = score.get("在小区里养鸡的是什么心态跟谁投诉");
			System.err.println(a);
		} else {
			System.out.println("执行异常");
		}
		
	}
	
	/**
	 * @author 朱宏
	 * @description：cnn数据输入层
	 * 2019年3月25日
	 * @param cnn
	 * @throws IOException
	 */
	public static void inputLayer(Cnn cnn) throws IOException {
		Word2VEC vec = new Word2VEC();
		//vec.loadJavaModel("D:/N