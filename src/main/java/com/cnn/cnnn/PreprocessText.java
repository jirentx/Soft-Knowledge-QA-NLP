package com.cnn.cnnn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.cnn.utils.CnnUtils;


public class PreprocessText {
	public static void main(String[] args) throws IOException {
		preProcessText("D:/NLP/精准度测试集1.txt", "D:/NLP/精准度测试集1-分词.txt", "UTF-8");
	}
	/**
	 * @description 文本预处理：朱宏
	 * @throws IOException
	 */
	public static void