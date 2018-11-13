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

	/** 弱引用 */
	private static Map<String, WeakReference<Bitmap>> imageCache = new HashMap<String, WeakReference<Bitmap>>();
	
	/**设置超时时间为5秒*/
	private static final int TIMEOUT_IN_MILLIONS = 5000;

	/**
	 * 用post的方式提交，返回字符串
	 * <p>
	 * 支持http和https
	 * </p>
	 * 
	 * @param uri
	 *            请求地址
	 * @param params
	 *            参数集合(请求名字,请求内容)
	 * @return 返回值
	 * @throws org.apache.http.conn.ConnectTimeoutException
	 *             超时异常[重点捕获异常]
	 * @throws java.io.IOException
	 *             Io流异常
	 */
	public static String doPost(String uri, Map<String, String> params)
			throws ConnectTimeoutException, IOException {
		// 进行返回的数据
		String redata = null;
		// 判断请求参数是否为空
		if (params == null) {
			params = new HashMap<String, String>();
		}
		// HttpParams 操作对象
		HttpParams httpparams = new BasicHttpParams();
		// 设置一些基本参数
		HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(httpparams, true);
		HttpProtocolParams
				.setUserAgent(
						httpparams,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(httpparams, 2000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(httpparams, 5000);
		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				httpparams, schReg);
		// http请求
		HttpClient client = new DefaultHttpClient(conMgr, httpparams);
		// post请求
		HttpPost post = new HttpPost(uri);
		// 头部
		post.addHeader("charset", HTTP.UTF_8);
		// NameValuePair(设置)
		ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> set = params.entrySet();
		Iterator<Entry<String, String>> iter = set.iterator();
		// 迭代器
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		/* 防止中文乱码 */
		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = client.execute(post);
		// 获取返回的数据
		if (response.getStatusLine().getStatusCode() == 200) {
			redata = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
		} else {
			redata = null;
		}
		return redata;
	}

	/**
	 * 从Url中获取Bitmap(网络下载图片)
	 * 
	 * @param url
	 *            网络的url连接
	 * @return bitmap图片
	 */
	public static Bitmap getBitmapFormUrl(String url) {
		Bitmap bitmap = null;
		InputStream inputStream = null;
		try {
			// 先判断软应用内是否存在这个bitmap
			if (getImageCache(url) == null) {
				// HttpParams 操作对象
				HttpParams httpparams = new BasicHttpParams();
				// 设置一些基本参数
				HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
				HttpProtocolParams.setUseExpectContinue(httpparams, true);
				HttpProtocolParams
						.setUserAgent(
								httpparams,
								"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
										+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
				/* 从连接池中取连接的超时时间 */
				ConnManagerParams.setTimeout(httpparams, 2000);
				/* 连接超时 */
				HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
				/* 请求超时 */
				HttpConnectionParams.setSoTimeout(httpparams, 5000);
				// 设置我们的HttpClient支持HTTP和HTTPS两种模式
				SchemeRegistry schReg = new SchemeRegistry();
				schReg.register(new Scheme("http", PlainSocketFactory
						.getSocketFactory(), 80));
				schReg.register(new Scheme("https", SSLSocketFactory
						.getSocketFactory(), 443));
				// 使用线程安全的连接管理来创建HttpClient
				ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
						httpparams, schReg);
				// 设置URL
				URL mImageUrl = new URL(url);
				// get请求
				HttpGet httpGet = new HttpGet(mImageUrl.toURI());
				// 获取http操作对象,并设置属性
				HttpClient httpClient = new DefaultHttpClient(conMgr,
						httpparams);
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
						entity);
				inputStream = bufferedHttpEntity.getContent();
				// 二进制数据生成位图
				bitmap = BitmapFactory.decodeStream(inputStream);
				// 添加到软引用
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
	 * 加载图片到软引用
	 * 
	 * @param key
	 *            软应用的key取值
	 * @param bitmap
	 *            位图
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
	 * 获取软引用内的图
	 * 
	 * @param key
	 *            软应用key值
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
	 * 用get的方式提交，返回字符串
	 * 
	 * @return 响应的字符串
	 */
	public static String doGet(String uri) throws Exception {
		// HttpParams 操作对象
		HttpParams httpparams = new BasicHttpParams();
		// 设置一些基本参数
		HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(httpparams, true);
		HttpProtocolParams
				.setUserAgent(
						httpparams,
						"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
								+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(httpparams, 2000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(httpparams, 3000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(httpparams, 5000);
		// 设置我们的HttpClient支持HTTP和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				httpparams, schReg);
		HttpClient client = new DefaultHttpClient(conMgr, httpparams);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		HttpParams hparams = new BasicHttpParams();
		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(hparams, 10000);
		// 设置请求超时
		HttpConnectionParams.setSoTimeout(hparams, 5000);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			throw new IOException("Net Exception!");
		}
	}
	
	/**
	 * 以HttpURLConnection发起Get请求，获得返回数据
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
     * 以HttpClient的DoGet方式向服务器发送请求数据
     * 
     * @param map 传递进来的数据，以map的形式进行了封装
     * @param path 要求服务器servlet的地址
     * @return 返回的String类型的值
     * @throws Exception
     */
    public static String HttpClientDoGet(Map<String, String> map, String path)
            throws Exception
    {
        // 创建HttpClient对象
        HttpClient hc = new DefaultHttpClient();
        // 请求路径与参数拼接
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
        // 创建一个get请求对象
        HttpGet request = new HttpGet(str);
        // HttpGet执行get请求，返回一个响应对象
        HttpResponse response = hc.execute(request);
        String result = null;
        // 获取响应状态码
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
        {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }
        return result;
    }

    /**
     * 以HttpClient的DoPost方式向服务器发送请求数据
     * 
     * @param map 传递进来的数据，以map的形式进行了封装
     * @param path 要求服务器servlet的地址
     * @return 返回的String类型的值
     * @throws Exception
     */
    public static String HttpClientDoPost(Map<String, String> map, String path)
            throws Exception
    {
        /**
         * 1获得一个相当于浏览器对象HttpClient,使用这个操的实现类来创建对象
		 ，DefaultHttpClient
         * DoPost方式请求的时候设置请求，关键是路径
         * 2为请求设置参数，也即是将要上传到服务器上的参数
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
        // 请求实体HttpEntity也是一个接口，我们用它的实现类UrlEncodedFormEntity来创建对象，注意后面一个String类型的参数是用来指定编码的
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        request.setEntity(entity);
        // 3执行请求
        HttpResponse response = hc.execute(request);
        // 4通过返回码来判断请求成功与否
        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK)
        {
            result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        }

        return result;
    }

    /**
     * 普通方式的HttpURLConnection的Post请求提交数据
     * 
     * @param map 传递进来的数据，以map的形式进行了封装
     * @param path 要求服务器servlet的地址
     * @return 返回的String类型的参数
     * @throws Exception
     */
    public static String submitDataByDoPost(Map<String, String> map, String path)
            throws Exception
    {
        // 注意Post地址中是不带参数的，所以newURL的时候要注意不能加上后面的参数
        URL Url = new URL(path);
        // Post方式提交的时候参数和URL是分开提交的，参数形式是这样子的：name=y&age=6
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

        // 获取HttpURLConnection对象
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        // 设置请求方式
        HttpConn.setRequestMethod("POST");
        HttpConn.setReadTimeout(5000);
        // 是否允许输出数据
        HttpConn.setDoOutput(true);
        HttpConn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        HttpConn.setRequestProperty("Contetn-length",
                String.valueOf(str.getBytes().length));
        OutputStream os = HttpConn.getOutputStream();
        // post提交form表单数据
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
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param url
	 *            Service net address[服务器地址]
	 * @param params
	 *            text content [请求的参数]
	 * @param files
	 *            pictures [图片文件]
	 * @return String result of Service response [请求服务器返回的数据]
	 * @throws java.io.IOException
	 *             [IO异常]
	 */
	public static String uploadFile(String url, Map<String, String> params,
			Map<String, File> files) throws IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(10 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setConnectTimeout(720000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
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
		// 发送文件数据
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

		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
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
