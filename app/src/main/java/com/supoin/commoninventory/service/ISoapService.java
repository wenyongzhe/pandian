package com.supoin.commoninventory.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface ISoapService {
	public static final String NameSpace = "http://tempuri.org/";
	//���ú�̨��������ַ���Ӻ�̨��ȡ��Ʒ��Ϣ�������û���Ϣ���ⷿ��Ϣ��
	public static final String URL = "";
	String LoadResult() throws UnsupportedEncodingException , ClientProtocolException ,IOException,JSONException ;
}
