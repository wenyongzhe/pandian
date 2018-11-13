package com.supoin.commoninventory.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.text.TextUtils;
/**
 * ҵ����������
 * 
 */
public class DownBusinessDataUtils {

	//���ص�¼��Ϣ
	public static String DownloadStaff(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadStaffServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//		// �ɹ������
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}
	//��������̵�����
	public static String DownloadMaxNumber(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadMaxNumberServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//		// �ɹ������
		System.out.println("Call back:" + result);
		if (result != null ) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}
	//������Ʒ��Ϣ
	public static String DownloadCheckData(JSONObject inputJson,String areaPrince) throws JSONException{
		Object result = NetworkUtilities.DownloadCheckDataServer(inputJson,areaPrince);
		String returnStr="";
		errorInfo(result.toString());
//		// �ɹ������
		System.out.println("Call back:" + result);
		if (result != null ) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	//�����̵�ǹ���
	public static String DownloadCode(JSONObject inputJson,String macAddress) throws JSONException{
		Object result = NetworkUtilities.DownloadCodeServer(inputJson,macAddress);
		String returnStr="";
		errorInfo(result.toString());
//			// �ɹ������
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	//���ؿⷿ��Ϣ
	public static String DownloadStore(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadStoreServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//				// �ɹ������
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	/**
	 * ��������Ϣ
	 * 
	 * @param result
	 * @throws JSONException
	 */
	private static void errorInfo(String result) throws JSONException {
		try {
			int errorCode = Integer.parseInt(result.trim());
			String errorMsg = "δ֪������" + errorCode;
			switch (errorCode) {
			case -1:
				errorMsg = "��������Ϊ��";
				break;
			case -2:
				errorMsg = "�ͻ���������";
				break;
			case -3:
				errorMsg = "�û���ϢΪ��";
				break;
			case -4:
				errorMsg = "��˾���Ϊ��";
				break;
			case -5:
				errorMsg = "�û����Ϊ��";
				break;
			case -6:
				errorMsg = "�û���Ϊ��";
				break;
			default:
				break;
			}
			throw new JSONException(errorMsg);
		} catch (NumberFormatException nf) {
			// ɶ������
		}

	}
}
