package com.supoin.commoninventory.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

//���ؿⷿ��Ϣ
public class DownloadStoreService implements ISoapService {
	private static final String SOAP_ACTION = NameSpace
			+ "IPDAService/DownDrugCode";
	private static final String MethodName = "downloadStore";
	private JSONObject inputJson;
	private static final String APPLICATION_JSON = "application/json";
	private ThreadLocal<Integer> retryCounter = new ThreadLocal<Integer>();	
	public   DownloadStoreService(JSONObject inputJson) {
		this.inputJson=inputJson;
	}
	public String LoadResult() throws UnsupportedEncodingException , ClientProtocolException ,IOException, JSONException {
		String result ="";
        JSONObject jsonparam = new JSONObject();
		jsonparam.put("login", inputJson);
		jsonparam.put("action", MethodName);
		jsonparam.put("data", true);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);
      //�������ӳ�ʱ
        httpClient.getParams().setParameter(CoreConnectionPNames.
                            CONNECTION_TIMEOUT, 5000);
        
        //���ö�ȡ��ʱ
        httpClient.getParams().setParameter(
                            CoreConnectionPNames.SO_TIMEOUT, 5000);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        StringEntity se = new StringEntity(jsonparam.toString());
        httpPost.setEntity(se);
        HttpResponse httpResponse =httpClient.execute(httpPost);     
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
            result = EntityUtils.toString(httpResponse.getEntity());  
        }else{
        	
        }
		return result;	
	}

}