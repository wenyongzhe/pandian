package com.supoin.commoninventory.entity;
public class DotEntity
{
	/// <summary>
	/// 数量的处理方式，0，1，2分别表示不处理小数，小数后处理1位，处理2位.
	/// </summary>
	public static int dotCounter;
	/// <summary>
	/// 根据设置格式货数量，库存等涉及到数量性质的字段的显示格式等
	/// </summary>
	public static String dotFormat;

	public static int getDotCounter() {
		return dotCounter;
	}

	public static void setDotCounter(int dotCounter) {
		DotEntity.dotCounter = dotCounter;
	}
	public static String getDotFormat() {
		if(getDotCounter()==1)
			dotFormat="0.#";
		else
			dotFormat="0.##";
		return dotFormat;
	}
	public static void setDotFormat(String dotFormat) {
		DotEntity.dotFormat = dotFormat;
	}
}