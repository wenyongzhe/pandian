package com.supoin.commoninventory.entity;
public class DotEntity
{
	/// <summary>
	/// �����Ĵ���ʽ��0��1��2�ֱ��ʾ������С����С������1λ������2λ.
	/// </summary>
	public static int dotCounter;
	/// <summary>
	/// �������ø�ʽ�������������漰���������ʵ��ֶε���ʾ��ʽ��
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