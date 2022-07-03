package com.cnn.cnnn;

import com.cnn.entity.Cnn;

/**
 * @author 朱宏
 * @description：Cnn正则化类
 * 2019年3月28日
 */
public class CnnNomalization {
	/**
	 * @author 朱宏
	 * @description：标准差正则化
	 * 2019年3月28日
	 * @param cnn
	 */
	public static float[] standardDeviation(Cnn cnn) {
		int listSize = cnn.getCnnDataList().size();
		int listSizeGet0 = cnn.getCnnDataList().get(0).size();
		float totalScore = 0;
		float avgScore = 0;
		float standardDev = 0;
		float s