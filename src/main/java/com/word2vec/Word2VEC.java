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
		 learn.learnFile(new File("D:/NLP/法律-分词.txt"));
		 learn.saveModel(new File("D:/NLP/法律-分词model.txt"));

		Word2VEC vec = new Word2VEC();
		vec.loadJavaModel("D:/NLP/法律-分词model.txt");

		 System.out.println("国" + "\t" +
		 Arrays.toString(vec.getWordVector("国")));
		String str = "谁";
		
		System.out.println(vec.distance(str));

		System.out.println("the familar of two word:"+vec.wordDis("谁", "去"));
		
		System.out.println(vec.analogy("小", "区", "行"));
		
		System.out.println(vec.wordDis("欺", "认"));
		
	}

	private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();

	private int words;
	private int size;
	private int topNSize = 40;
	
	/**
	 * 计算两个字的相似度    朱宏
	 * @param queryword1
	 * @param queryword2
	 * @return
	 */
	public float wordDis(String queryword1, String queryword2) {
		float[] vector1 = wordMap.get(queryword1);
		float[] vector2 = wordMap.get(queryword2);

		float dist = 0;
		if (vector1 == null || vector2 ==null) {
			return 0;
		}
		for (int i = 0; i < vector1.length; i++) {
			dist += vector1[i] * vector2[i];
		}
		return dist;
	}
	/**
	 * 加载模型
	 * 
	 * @param path
	 *            模型的路径
	 * @throws IOException
	 */
	public void loadGoogleModel(String path) throws IOException {
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		double len = 0;
		float vector = 0;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			dis = new DataInputStream(bis);
			// //读取词数
			words = Integer.parseInt(readString(dis));
			// //大小
			size = Integer.parseInt(readString(dis));
			String word;
			float[] vectors = null;
			for (int i = 0; i < words; i++) {
				word = readString(dis);
				vectors = new float[size];
				len = 0;
				for (int j = 0; j < size; j++) {
					vector = readFloat(dis);
					len += vector * vector;
					vectors[j] = (float) vector;
				}
				len = Math.sqrt(len);

				for (int j = 0; j < size; j++) {
					vectors[j] /= len;
				}

				wordMap.put(word, vectors);
				dis.read();
			}
		} finally {
			bis.close();
			dis.close();
		}
	}

	/**
	 * 加载模型
	 * 
	 * @param path
	 *            模型的路径
	 * @throws IOException
	 */
	public void loadJavaModel(String path) throws IOException {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
			words = dis.readInt();
			size = dis.readInt();

			float vector = 0;

			String key = null;
			float[] value = null;
			for (int i = 0; i < words; i++) {
				double len = 0;
				key = dis.readUTF();
				value = new float[size];
				for (int j = 0; j < size; j++) {
					vector = dis.readFloat();
					len += vector * vector;
					value[j] = vector;
				}

				len = Math.sqrt(len);

				for (int j = 0; j < size; j++) {
					value[j] /= len;
				}
				wordMap.put(key, value);
			}

		}
	}

	private static final int MAX_SIZE = 50;

	/**
	 * 近义词
	 * 
	 * @return
	 */
	public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
		float[] wv0 = getWordVector(word0);
		float[] wv1 = getWordVector(word1);
		float[] wv2 = getWordVector(word2);

		if (wv1 == null || wv2 == null || wv0 == null) {
			return null;
		}
		float[] wordVector = new float[size];
		for (int i = 0; i < size; i++) {
			wordVector[i] = wv1[i] - wv0[i] + wv2[i];
		}
		float[] tempVector;
		String name;
		List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
		for (Entry<String, float[]> entry : wordMap.entrySet()) {
			name = entry.getKey();
			if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
				continue;
			}
			float dist = 0;
			tempVector = entry.getValue();
			for (int i = 0; i < wordVector.length; i++) {
				dist += wordVect