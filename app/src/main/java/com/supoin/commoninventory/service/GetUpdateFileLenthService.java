package com.supoin.commoninventory.service;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/// <summary>
/// ��ȡ�����ļ��Ĵ�С
/// </summary>
/// <param name="ServerFileName"></param>
/// <param name="UpLoadFileDir"></param>
/// <returns></returns>
public class GetUpdateFileLenthService {
	public static final String NameSpace ="http://Supoin.MiddlePlatform";
	public static final String URL = "http://download.supoin.com:8012/OutPortForAndroid/OutPortForAndroidService.svc";
	private static final String SOAP_ACTION ="http://Supoin.MiddlePlatform/IOutPortForAndroidServiceContract/GetUpdateFileLenth";
	private static final String MethodName = "GetUpdateFileLenth";
	private byte[] stream;
	private long position;
	private String ServerFileName,UpLoadFileDir;
	private ThreadLocal<Integer> retryCounter = new ThreadLocal<Integer>();
	//��һ�������ǳ���ı��ȫ�ƣ��ڶ��������Ǳ��س���汾��
	public GetUpdateFileLenthService(String UpLoadFileDir) {
		this.UpLoadFileDir=UpLoadFileDir;
	}

	public SoapObject LoadResult() throws IOException, XmlPullParserException {
		SoapObject soapObject = new SoapObject(NameSpace, MethodName);
		soapObject.addProperty("UpLoadFileDir", UpLoadFileDir);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // �汾
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);
		HttpTransportSE trans = new HttpTransportSE(URL);
		try {
			trans.debug = false; // ʹ�õ��Թ���
			trans.call(SOAP_ACTION, envelope);
			Object ret= (Object) envelope.bodyIn;
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