package com.supoin.commoninventory.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util{
	/**
	* MD5 32λ���ܷ���һ Сд
	* @param str
	* @return
	*/

	public final static String get32MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			//ʹ��MD5����MessageDigest����
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				//System.out.println((int)b);
				//��û����(int)b����˫�ֽڼ���
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	* MD5 32λ���ܷ����� Сд
	* @param str
	* @return
	*/

	public final static String get32MD5Str(String str) { 
		MessageDigest messageDigest = null; 
		try { 
			messageDigest = MessageDigest.getInstance("MD5"); 
			messageDigest.reset(); 
			messageDigest.update(str.getBytes("UTF-8")); 
		} catch (NoSuchAlgorithmException e) { 
			System.out.println("NoSuchAlgorithmException caught!"); 
			System.exit(-1); 
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} 
		byte[] byteArray = messageDigest.digest(); 
		StringBuffer md5StrBuff = new StringBuffer(); 
		for (int i = 0; i < byteArray.length; i++) { 
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) 
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i])); 
			else 
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i])); 
		} 
		return md5StrBuff.toString(); 
	}

	 

	/**
	* Md5 32λ or 16λ ����
	* @param plainText 
	* @return 32λ����
	*/
	public static String Md5(String plainText ) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(plainText.getBytes());
			byte b[] = md.digest(); 
			int i; 
			buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// Log.e("555","result: " + buf.toString());//32λ�ļ��� 
			//Log.e("555","result: " + buf.toString().substring(8,24));//16λ�ļ���
	
		} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf.toString(); 
	}

	 

	//����java String ת���� MD5 byte[]
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2); 
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray(); 
		for (int i = 0; i < len; i++) {
			int pos = i * 2; 
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
		} 
		return result; 
	} 
	private static byte toByte(char c) 
	{ 
		byte b = (byte) "0123456789abcdef".indexOf(c); 
		return b; 
	}
}