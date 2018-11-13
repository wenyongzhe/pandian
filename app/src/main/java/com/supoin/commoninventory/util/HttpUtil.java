package com.supoin.commoninventory.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtil {

	/** ������ */
	private static Map<String, WeakReference<Bitmap>> imageCache = new HashMap<String, WeakReference<Bitmap>>();
	
	/**���ó�ʱʱ��Ϊ5��*/
	private static final int TIMEOUT_IN_MILLIONS = 5000;

	/**
	 * ��post�ķ�ʽ�ύ�������ַ���
	 * <p>
	 * ֧��http��https
	 * </p>
	 * 
	 * @param uri
	 *            �����ַ
	 * @param params
	 *            ��������(��������,��������)
	 * @return ����ֵ
	 * @throws org.apache.http.conn.ConnectTimeoutException
	 *             ��ʱ�쳣[�ص㲶���쳣]
	 * @throws java.io.IOException
	 *             Io���쳣
	 */
	public static String doPost(String uri, Map<String, String> params)
			throws ConnectTimeoutException, IOException {
		// ���з��ص�����
		String redata = null;
		// �ж���������Ƿ�Ϊ��
		if (params == null) {
			params = new HashMap<String, String>();
		}
		// HttpParams ��������
		HttpParams httpparams = new BasicHttpParams();
		// ����һЩ��������
		HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(httpparams, true);
		HttpProtocolParams
				.setUserAgent(
						httpparams,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		/* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
		ConnManagerParams.setTimeout(httpparams, 2000);
		/* ���ӳ�ʱ */
		HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
		/* ����ʱ */
		HttpConnectionParams.setSoTimeout(httpparams, 5000);
		// �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		// ʹ���̰߳�ȫ�����ӹ���������HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				httpparams, schReg);
		// http����
		HttpClient client = new DefaultHttpClient(conMgr, httpparams);
		// post����
		HttpPost post = new HttpPost(uri);
		// ͷ��
		post.addHeader("charset", HTTP.UTF_8);
		// NameValuePair(����)
		ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> set = params.entrySet();
		Iterator<Entry<String, String>> iter = set.iterator();
		// ������
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		/* ��ֹ�������� */
		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		// ��ȡ���ص�����
		if (response.getStatusLine().getStatusCode() == 200) {
			redata = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		} else {
			redata = null;
		}
		return redata;
	}

	/**
	 * ��Url�л�ȡBitmap(��������ͼƬ)
	 * 
	 * @param url
	 *            �����url����
	 * @return bitmapͼƬ
	 */
	public static Bitmap getBitmapFormUrl(String url) {
		Bitmap bitmap = null;
		InputStream inputStream = null;
		try {
			// ���ж���Ӧ�����Ƿ�������bitmap
			if (getImageCache(url) == null) {
				// HttpParams ��������
				HttpParams httpparams = new BasicHttpParams();
				// ����һЩ��������
				HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
				HttpProtocolParams.setUseExpectContinue(httpparams, true);
				HttpProtocolParams
						.setUserAgent(
								httpparams,
								"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
										+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
				/* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
				ConnManagerParams.setTimeout(httpparams, 2000);
				/* ���ӳ�ʱ */
				HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
				/* ����ʱ */
				HttpConnectionParams.setSoTimeout(httpparams, 5000);
				// �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
				SchemeRegistry schReg = new SchemeRegistry();
				schReg.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));
				schReg.register(new Scheme("https", SSLSocketFactory
						.getSocketFactory(), 443));
				// ʹ���̰߳�ȫ�����ӹ���������HttpClient
				ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
						httpparams, schReg);
				// ����URL
				URL mImageUrl = new URL(url);
				// get����
				HttpGet httpGet = new HttpGet(mImageUrl.toURI());
				// ��ȡhttp��������,����������
				HttpClient httpClient = new DefaultHttpClient(conMgr,
						httpparams);
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
						entity);
				inputStream = bufferedHttpEntity.getContent();
				// ��������������λͼ
				bitmap = BitmapFactory.decodeStream(inputStream);
				// ��ӵ�������
				addImageCache(url, bitmap);
			} else {
				return getImageCache(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bitmap;
	}

	/**
	 * ����ͼƬ��������
	 * 
	 * @param key
	 *            ��Ӧ�õ�keyȡֵ
	 * @param bitmap
	 *            λͼ
	 */
	private static void addImageCache(String key, Bitmap bitmap) {
		if (bitmap != null) {
			if (getImageCache(key) == null) {
				imageCache.put(key.replaceAll("[^\\w]", ""),
						new WeakReference<Bitmap>(bitmap));
			}
		}
	}

	/**
	 * ��ȡ�������ڵ�ͼ
	 * 
	 * @param key
	 *            ��Ӧ��keyֵ
	 * @return
	 */
	private static Bitmap getImageCache(String key) {
		if (imageCache.get(key.replaceAll("[^\\w]", "")) != null) {
			return imageCache.get(key.replaceAll("[^\\w]", "")).get();
		} else {
			return null;
		}
	}

	/**
	 * ��get�ķ�ʽ�ύ�������ַ���
	 * 
	 * @return ��Ӧ���ַ���
	 */
	public static String doGet(String uri) throws Exception {
		// HttpParams ��������
		HttpParams httpparams = new BasicHttpParams();
		// ����һЩ��������
		HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(httpparams, true);
		HttpProtocolParams
				.setUserAgent(
						httpparams,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		/* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
		ConnManagerParams.setTimeout(httpparams, 2000);
		/* ���ӳ�ʱ */
		HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
		/* ����ʱ */
		HttpConnectionParams.setSoTimeout(httpparams, 5000);
		// �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		// ʹ���̰߳�ȫ�����ӹ���������HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				httpparams, schReg);
		HttpClient client = new DefaultHttpClient(conMgr, httpparams);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		HttpParams hparams = new BasicHttpParams();
		// �������ӳ�ʱ
		HttpConnectionParams.setConnectionTimeout(hparams, 10000);
		// ��������ʱ
		HttpConnectionParams.setSoTimeout(hparams, 5000);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			throw new IOException("Net Exception!");
		}
	}
	
	/**
	 * ��HttpURLConnection����Get���󣬻�÷�������
	 * 
	 * @param urlStr
	 * @return null
	 * @throws Exception
	 */
	public static String DoGet(String urlStr) 
	{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200)
			{
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1)
				{
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else
			{
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (is != null)
					is.close();
			} catch (IOException e)
			{
			}
			try
			{
				if (baos != null)
					baos.close();
			} catch (IOException e)
			{
			}
			conn.disconnect();
		}
		
		return null ;

	}
	
	/**
     * ��HttpClient��DoGet��ʽ�������������������
     * 
     * @param map ���ݽ��������ݣ���map����ʽ�����˷�װ
     * @param path Ҫ�������servlet�ĵ�ַ
     * @return ���ص�String���͵�ֵ
     * @throws Exception
     */
    public static String HttpClientDoGet(Map<String, String> map, String path)
            throws Exception
    {
        // ����HttpClient����
        HttpClient hc = new DefaultHttpClient();
        // ����·�������ƴ��
        StringBuilder sb = new StringBuilder(path);
        sb.append("?");
        for (Entry<String, String> entry : map.entrySet())
        {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str = sb.toString().replace("\"", "%22").replace("{", "%7b")
                .replace("}", "%7d");
        // ����һ��get�������
        HttpGet request = new HttpGet(str);
        // HttpGetִ��get���󣬷���һ����Ӧ����
        HttpResponse response = hc.execute(request);
        String result = null;
        // ��ȡ��Ӧ״̬��
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
        {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        return result;
    }

    /**
     * ��HttpClient��DoPost��ʽ�������������������
     * 
     * @param map ���ݽ��������ݣ���map����ʽ�����˷�װ
     * @param path Ҫ�������servlet�ĵ�ַ
     * @return ���ص�String���͵�ֵ
     * @throws Exception
     */
    public static String HttpClientDoPost(Map<String, String> map, String path)
            throws Exception
    {
        /**
         * 1���һ���൱�����������HttpClient,ʹ������ٵ�ʵ��������������
		 ��DefaultHttpClient
         * DoPost��ʽ�����ʱ���������󣬹ؼ���·��
         * 2Ϊ�������ò�����Ҳ���ǽ�Ҫ�ϴ����������ϵĲ���
         * 
         */
        HttpClient hc = new DefaultHttpClient();
        HttpPost request = new HttpPost(path);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : map.entrySet())
        {
            NameValuePair nameValuePairs = new BasicNameValuePair(
                    entry.getKey(), entry.getValue());
            parameters.add(nameValuePairs);
        }
        // ����ʵ��HttpEntityҲ��һ���ӿڣ�����������ʵ����UrlEncodedFormEntity����������ע�����һ��String���͵Ĳ���������ָ�������
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        request.setEntity(entity);
        // 3ִ������
        HttpResponse response = hc.execute(request);
        // 4ͨ�����������ж�����ɹ����
        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
        {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }

        return result;
    }

    /**
     * ��ͨ��ʽ��HttpURLConnection��Post�����ύ����
     * 
     * @param map ���ݽ��������ݣ���map����ʽ�����˷�װ
     * @param path Ҫ�������servlet�ĵ�ַ
     * @return ���ص�String���͵Ĳ���
     * @throws Exception
     */
    public static String submitDataByDoPost(Map<String, String> map, String path)
            throws Exception
    {
        // ע��Post��ַ���ǲ��������ģ�����newURL��ʱ��Ҫע�ⲻ�ܼ��Ϻ���Ĳ���
        URL Url = new URL(path);
        // Post��ʽ�ύ��ʱ�������URL�Ƿֿ��ύ�ģ�������ʽ�������ӵģ�name=y&age=6
        StringBuilder sb = new StringBuilder();
        // sb.append("?");
        for (Entry<String, String> entry : map.entrySet())
        {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        if (sb.length() > 0)
        {
            sb.deleteCharAt(sb.length() - 1);
        }
        String str = sb.toString();

        // ��ȡHttpURLConnection����
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        // ��������ʽ
        HttpConn.setRequestMethod("POST");
        HttpConn.setReadTimeout(5000);
        // �Ƿ������������
        HttpConn.setDoOutput(true);
        HttpConn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        HttpConn.setRequestProperty("Contetn-length",
                String.valueOf(str.getBytes().length));
        OutputStream os = HttpConn.getOutputStream();
        // post�ύform������
        os.write(str.getBytes());
        String runbook = "";
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    HttpConn.getInputStream(), "utf-8"));
            while ((str = br.readLine()) != null)
            {
                runbook += str;
            }
        }
        return runbook;
    }

	/**
	 * ͨ��ƴ�ӵķ�ʽ�����������ݣ�ʵ�ֲ��������Լ��ļ�����
	 * 
	 * @param url
	 *            Service net address[��������ַ]
	 * @param params
	 *            text content [����Ĳ���]
	 * @param files
	 *            pictures [ͼƬ�ļ�]
	 * @return String result of Service response [������������ص�����]
	 * @throws java.io.IOException
	 *             [IO�쳣]
	 */
	public static String uploadFile(String url, Map<String, String> params,
			Map<String, File> files) throws IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(10 * 1000); // ������ʱ��
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false); // ������ʹ�û���
		conn.setConnectTimeout(720000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// �����ļ�����
		if (files != null)
			for (Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);

				sb1.append("Content-Disposition: form-data; name=\""
						+ file.getKey() + "\"; filename=\""
						+ file.getValue().getName() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

		// ���������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// �õ���Ӧ��
		int res = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			int ch;
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2.toString();
	}

}
