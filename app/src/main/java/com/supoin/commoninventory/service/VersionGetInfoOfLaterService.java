package com.supoin.commoninventory.service;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

//获取版本列表
public class VersionGetInfoOfLaterService {
	public static final String NameSpace = "http://tempuri.org/";
	public static final String URL = "http://download.supoin.com:8012/OutPortService/OutPortService.svc";
	private static final String SOAP_ACTION = NameSpace
			+ "IOutPortServiceContract/VersionGetInfoOfLater";
	private static final String MethodName = "VersionGetInfoOfLater";
	private byte[] stream;
	private long position;
	private String CFullISN,CName;
	private ThreadLocal<Integer> retryCounter = new ThreadLocal<Integer>();
	//第一个参数是程序的编号全称，第二个参数是本地程序版本号
	public VersionGetInfoOfLaterService(String CFullISN, String CName) {
		this.CFullISN=CFullISN;
		this.CName=CName;
	}

	public SoapObject LoadResult() throws IOException, XmlPullParserException {
		SoapObject soapObject = new SoapObject(NameSpace, MethodName);
		soapObject.addProperty("CFullISN",CFullISN);
		soapObject.addProperty("CName",CName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // 版本
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);
		HttpTransportSE trans = new HttpTransportSE(URL);
		try {
			trans.debug = false; // 使用调试功能
			trans.call(SOAP_ACTION, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			System.out.println("Call Successful!");
			return result;
		} catch (IOException e) {
			Integer tryC = retryCounter.get();
			if (tryC == null) {
				retryCounter.set(1);
			} else if (tryC != null && tryC.intValue() > 1) {
				throw new IOException(e);
			} else {
				int k = tryC.intValue() + 1;
				retryCounter.set(k);
			}
			return LoadResult();
		}
	}

}