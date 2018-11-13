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
 * 业务数据下载
 * 
 */
public class DownBusinessDataUtils {

	//下载登录信息
	public static String DownloadStaff(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadStaffServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//		// 成功的情况
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}
	//下载最大盘点数量
	public static String DownloadMaxNumber(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadMaxNumberServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//		// 成功的情况
		System.out.println("Call back:" + result);
		if (result != null ) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}
	//下载商品信息
	public static String DownloadCheckData(JSONObject inputJson,String areaPrince) throws JSONException{
		Object result = NetworkUtilities.DownloadCheckDataServer(inputJson,areaPrince);
		String returnStr="";
		errorInfo(result.toString());
//		// 成功的情况
		System.out.println("Call back:" + result);
		if (result != null ) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	//下载盘点枪编号
	public static String DownloadCode(JSONObject inputJson,String macAddress) throws JSONException{
		Object result = NetworkUtilities.DownloadCodeServer(inputJson,macAddress);
		String returnStr="";
		errorInfo(result.toString());
//			// 成功的情况
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	//下载库房信息
	public static String DownloadStore(JSONObject inputJson) throws JSONException{
		Object result = NetworkUtilities.DownloadStoreServer(inputJson);
		String returnStr="";
		errorInfo(result.toString());
//				// 成功的情况
		System.out.println("Call back:" + result);
		if (result != null) {
			if (!TextUtils.isEmpty(result.toString())) {
				returnStr=result.toString();;
			}
		}
		return returnStr;
	}	
	/**
	 * 错误码信息
	 * 
	 * @param result
	 * @throws JSONException
	 */
	private static void errorInfo(String result) throws JSONException {
		try {
			int errorCode = Integer.parseInt(result.trim());
			String errorMsg = "未知错误码" + errorCode;
			switch (errorCode) {
			case -1:
				errorMsg = "操作类型为空";
				break;
			case -2:
				errorMsg = "客户端主键空";
				break;
			case -3:
				errorMsg = "用户信息为空";
				break;
			case -4:
				errorMsg = "公司编号为空";
				break;
			case -5:
				errorMsg = "用户编号为空";
				break;
			case -6:
				errorMsg = "用户名为空";
				break;
			default:
				break;
			}
			throw new JSONException(errorMsg);
		} catch (NumberFormatException nf) {
			// 啥都不干
		}

	}
}
