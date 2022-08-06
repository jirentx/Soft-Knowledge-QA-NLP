package com.cnn.cnnn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 朱宏
 * @description：训练模型
 * 2019年4月3日
 */
public class ModelTrain {
public static void main(String[] args) throws IOException {
	File file = new File("D:/NLP/精准度测试集1.txt");
	String encoding = "utf-8";
	if (file.isFile() && file.exists()) {
		//float a  = mainLayer(input, "在小区里养鸡的是什么心态跟谁投诉");
		List<Float> score = new ArrayList<Float>();
		List<Float> score1 = new ArrayList<Float>();
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
		BufferedReader bufferedReader = 