package com.supoin.commoninventory.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class UpdateVersionService implements ISoapService {
	private static final String SOAP_ACTION = NameSpace
			+ "IAPPService/UpgradeInfo";
	private static final String MethodName = "UpgradeInfo";

	private String inputJson;

	private ThreadLocal<Integer> retryCounter = new ThreadLocal<Integer>();

	public UpdateVersionService(String inputJson) {
		this.inputJson = inputJson;
	}

	@Override
	public String LoadResult() throws  UnsupportedEncodingException , ClientProtocolException ,IOException {
		String result ="";
		
		HttpPost httpRequest = new HttpPost(URL);  
        
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("login", inputJson));  
        params.add(new BasicNameValuePair("action", MethodName));  
        params.add(new BasicNameValuePair("data", "true"));  
        HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");  
        httpRequest.setEntity(httpEntity);  
          
        HttpClient httpClient = new DefaultHttpClient();  
        HttpResponse httpResponse = httpClient.execute(httpRequest);  
          
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
            result = EntityUtils.toString(httpResponse.getEntity());  
        }else{
        	
        }
		return result;	
	}

}
