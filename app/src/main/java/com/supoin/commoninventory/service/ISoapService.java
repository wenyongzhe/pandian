package com.supoin.commoninventory.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface ISoapService {
	public static final String NameSpace = "http://tempuri.org/";
	//配置后台服务器地址，从后台获取商品信息，下载用户信息，库房信息等
	public static final String URL = "";
	String LoadResult() throws UnsupportedEncodingException , ClientProtocolException ,IOException,JSONException ;
}
