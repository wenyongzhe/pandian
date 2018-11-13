package com.supoin.commoninventory.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParserException;

import com.supoin.commoninventory.service.DownloadCheckDataService;
import com.supoin.commoninventory.service.DownloadCodeService;
import com.supoin.commoninventory.service.DownloadMaxNumberService;
import com.supoin.commoninventory.service.DownloadStaffService;
import com.supoin.commoninventory.service.DownloadStoreService;
import com.supoin.commoninventory.service.GetBlockSizeService;
import com.supoin.commoninventory.service.GetUpdateFileLenthService;
import com.supoin.commoninventory.service.GetUpdateFileNameListService;
import com.supoin.commoninventory.service.ISoapService;
import com.supoin.commoninventory.service.JsonVersionGetInfoOfLaterService;
import com.supoin.commoninventory.service.LoadFileByBlockService;
import com.supoin.commoninventory.service.UploadCheckDataService;
import com.supoin.commoninventory.service.VersionGetInfoOfLaterService;
import com.supoin.commoninventory.service.getSomeInfoService;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities {

	private static final String TAG = NetworkUtilities.class.getSimpleName();
	public static String getSomeInfo(){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new getSomeInfoService().LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
				result = (String) soap.getValue();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	//下载登录信息
	public static String DownloadStaffServer(JSONObject inputJson){
		String result = null;

		try {
			result = new DownloadStaffService(inputJson).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	//下载最大盘点数量
	public static String DownloadMaxNumberServer(JSONObject inputJson){
		String result = null;

		try {
			result = new DownloadMaxNumberService(inputJson).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	//下载商品信息
	public static String DownloadCheckDataServer(JSONObject inputJson,String areaPrince){
		String result = null;

		try {
			result = new DownloadCheckDataService(inputJson,areaPrince).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	//下载盘点枪编号
	public static String DownloadCodeServer(JSONObject inputJson,String macAddress){
		String result = null;

		try {
			result = new DownloadCodeService(inputJson,macAddress).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
//下载库房信息
	public static String DownloadStoreServer(JSONObject inputJson){
		String result = null;
		try {
			result = new DownloadStoreService(inputJson).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
//上传盘点结果
	public static String UploadCheckData(JSONObject inputJson,JSONObject loginJson){
		String result = null;

		try {
			result = new UploadCheckDataService(inputJson,loginJson).LoadResult();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}	

	public static String VersionGetInfoOfLater(String CFullISN, String CName){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new VersionGetInfoOfLaterService(CFullISN, CName).LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
				result = (String) soap.getValue();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	
	}
	public static String JsonVersionGetInfoOfLater(String CFullISN, String CName){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new JsonVersionGetInfoOfLaterService(CFullISN, CName).LoadResult();

//			if (soapObj != null
//					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
//				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
//				try {
//					JSONObject jsonObj=new JSONObject(soapObj.toString());
//					if(jsonObj!=null){
//						result=jsonObj.getString("JsonVersionGetInfoOfLaterResult");
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
//			}
			if (soapObj != null) {
				if(SoapPrimitive.class.isInstance(soapObj.getProperty(0))){
					SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
					result = (String) soap.getValue();
				}else{
					Object soap =  soapObj.getProperty(0);
					if(soap.toString().equals("anyType{}"))
						result="latest";
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		{"T_VersionInfo":[{"CName":"1.0.0.5","CRemark":"20160516","CProductID":25,"CCreateDate":"2016-5-16 9:38:11","CPath":"updatefiles\\MI001\\SUPOIN\\SP001\\1.0.0.5.zip","CIsPublish":true,"CFullISN":"MI001\\SUPOIN\\SP001","CParentID":24,"CUID":72}]}
		return result;
	
	}
	public static String GetBlockSize(){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new GetBlockSizeService().LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
				result = (String) soap.getValue();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	
	}
	public static String GetUpdateFileNameList(String strDirectorypath){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new GetUpdateFileNameListService(strDirectorypath).LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
				result = (String) soap.getValue();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}
	public static String GetUpdateFileLenth(String UpLoadFileDir){
		String result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new GetUpdateFileLenthService(UpLoadFileDir).LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
				result = (String) soap.getValue();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}	
	
	public static byte[] LoadFileByBlock (long IstartPost, String UpLoadFileDir){
		byte[] result = null;
		JSONArray array = new JSONArray();
		SoapObject soapObj;
		try {
			soapObj = new LoadFileByBlockService(IstartPost, UpLoadFileDir).LoadResult();
			if (soapObj != null
					&& SoapPrimitive.class.isInstance(soapObj.getProperty(0))) {
				SoapPrimitive soap = (SoapPrimitive) soapObj.getProperty(0);
//					result=soap.get
				result = Base64.decode(soapObj.getProperty(0).toString(), Base64.DEFAULT);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
}
