package com.supoin.commoninventory.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Ref;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.contentprovider.providertool;
import com.supoin.commoninventory.service.msgobject;


import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml.Encoding;
import android.widget.Toast;


public class HttpUtility {
	public static int task_id = 0;//任务数量
	
    public Context m_context;
	public boolean download_flag = false;
	public int task_private = 0;//任务id
	public int flag_sucss = 0;
	public String filenameString;
	public DownloadManager dmg= null;
	public HttpUtility()
	{
		

			
	}
	
	public HttpUtility(Context context)
	{
		m_context = context;
		
		
	}
	public HttpUtility(Context context, String filename)
	{
		m_context = context;
		
		filenameString = filename;
		
	}
	 
	public int tast_increase()
	{
		if (task_id > 4)
		{
			return -1;
		}
		else {
			FileUtility fileUtility = new FileUtility();
			if (!fileUtility.isFileExist(filenameString,DrugSystemConst.download_dir))
			{
				task_id += 1;				
				task_private = task_id;
			}
			else
			{
//				if (((myApplication)m_context).getFileMap() != null&&((myApplication)m_context).getFileMap().get(filenameString)!= null)
//				{
//					task_private = ((myApplication)m_context).getFileMap().get(filenameString).download_id;
//				}
//				else {
//					task_id += 1;				
//					task_private = task_id;
//				}
				
			}
			return 0;
		}
		
		
	}
	public void task_decrease()
	{
		if (task_id != 0)
		{
			task_id -= 1;
		}
	}

	public String downloadText(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		InputStream is = getInputStreamFromUrl(urlStr);
		if (is == null)
		{
			return null;
		}
		buffer = new BufferedReader(new InputStreamReader(
				is));
		try {
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			buffer.close();
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String utfURL(String str) throws UnsupportedEncodingException
	{

		 StringBuffer sb = new StringBuffer();
		 int len = str.length();
		 
		 for (int i = 0; i < len; i++) 
		    {

		          char c = str.charAt(i);

		              if (c > 0x7f)
		              {

		                sb.append(URLEncoder.encode(String.valueOf(c),"utf-8"));

		                }		             
		              else 
		              {

				sb.append(c);

			}

		}

		return sb.toString();

	}
	public String replaceBlank(String str) 
	{  
		String dest = "";  
		if(str!=null) 
		{  
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
			Matcher m = p.matcher(str);  
			dest = m.replaceAll("");  
		}  
		return dest;  
	} 
	public int downloadBinary(String urlStr, String path, String fileName, Handler handler, Application ctx, Intent intent, providertool tool) throws SocketTimeoutException,ConnectTimeoutException {
//		InputStream inputStream = null;
		
		FileUtility fileUtility = new FileUtility();
		if (!fileUtility.ExistSDCard())
	    {
			Message msg1 = new Message();
			msgobject obj1 = new msgobject();

			obj1.name = fileName;
			msg1.obj = (Object) obj1;
			msg1.what = 14;			
	    	handler.sendMessage(msg1);
	    	return -1;
	    }
		
		if (fileUtility.isFileExist(fileName, path)&&download_flag) {
			handler.sendEmptyMessage(8);
			return 1;
		}else {
			if (download_flag)
			{
				//不应该走到这一步
			}
			else {
				download_flag = true;
//				if (fileUtility.isFileExist(fileName, path))
//				{
//					File file = new File(fileName, path);
//					if (file != null)
//					{
//						file.delete();
//					}
//					
//				}
			}
			if (tast_increase() == -1)
			{
				//检索下载任务数
				FileUtility fileUtility2 = new FileUtility();
				File file = new File("sdcard/"+DrugSystemConst.download_dir);
				File[] arrayfile = file.listFiles();
				int count = 0;
				for (int i = 0; i < arrayfile.length; i++)
				{
					if (arrayfile[i].getName().toLowerCase().endsWith(".tmpd"))
					{
						count++;
					}
					
				}
				if (count >= 5)
				{
//					handler.sendEmptyMessage(6);
					flag_sucss = 0;
//					return -1;
				}
				else {
//					if (((myApplication)ctx).getFileMap().get(fileName)!= null &&((myApplication)ctx).getFileMap().get(fileName).isPause == true)
//					{
//						//继续下载
//						task_private = ((myApplication)ctx).getFileMap().get(fileName).download_id;
//					}
//					else {
//						task_private = count + 1;
//					}
				}
				
//				else {
//					handler.sendEmptyMessage(6);
//					flag_sucss = 0;
//					return -1;
//				}
				
			}
			else
			{
				flag_sucss = 1;
				handler.sendEmptyMessage(7);
			}

			Message msg1 = new Message();
			msgobject obj1 = new msgobject();

			obj1.task_id = task_private;
			msg1.obj = (Object) obj1;
			msg1.what = 9;
			handler.sendMessage(msg1);

			
			File file = null;
			try {
			
			file = write2SDCardByInputStream(path, 
					fileName, 
					intent,
					urlStr, 
					handler, 
					ctx,
					task_private,
					fileUtility,
					tool);
			} catch (SocketTimeoutException e) {
				// TODO: handle exception
				handler.sendEmptyMessage(10);
				task_decrease();
			}
			if (file == null) {
				task_decrease();
				
				return -1;
			}
			else {
				task_decrease();
			}
		}
		
		return 0;
	}
	public File write2SDCardByInputStream(String path, String fileName, Intent intent,
			String urlStr, Handler handler, Application  ctx, int task_private, FileUtility fileUtility, providertool tool) throws SocketTimeoutException {
		File file = null;
		OutputStream output = null;
		
		HttpURLConnection connection = null;
	    RandomAccessFile randomAccessFile = null;
	    String urlString = null;
	    
	    try {

			urlString = utfURL(urlStr);
			// urlString = replaceBlank(urlString);

		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
//			task_decrease();
			return null;
		}
	    InputStream inputStream = null;
	    downinfo downcontent = new downinfo();
	    String[] arrayStr = new String[]{urlStr};
	    if (tool.isHasInfors(arrayStr))
	    {
	    	//第一次下载
	    	downinfo downif = new downinfo();
	    	downif.urllink = urlStr;
	    	//判断数据库是否为空（表已经存在）
	    	
    		downinfo info = new downinfo();
    		info.imageName = intent.getStringExtra("name");
    		info.filename = fileName;
//    		info.filesize = connection.getContentLength();
    		info.downflag = true;
    		info.downsize = 0;
    		info.isPause = true;
    		info.urllink = urlStr;
    		info.thread_islive = true;
    		info.download_id = task_private;
    		
    		
    		//数据库无表项
    		tool.saveInfos(info);
    		downcontent = info;
	    	
	    }
	    else 
	    {
	    	String[] arrayStr1 = new String[]{urlStr};
	    	downcontent = tool.getInfo(arrayStr1);
	    	if (downcontent != null)
	    	{
	    		downcontent.isPause = true;
	    		downcontent.thread_islive = true;
	    	}
	    	
		}
	    
	    
	    String filePath = fileUtility.createDirInSDCard(path);
	    FlushedInputStream  flushed = null;
		try {
				file = fileUtility.createFileInSDCard(fileName, filePath);
	            URL url = new URL(urlString);
	            connection = (HttpURLConnection) url.openConnection();
	            connection.setConnectTimeout(5000);
	            connection.setRequestMethod("GET");
		        // 设置范围，格式为Range：bytes x-y;
//		        connection.setRequestProperty("Range", "bytes="+(downcontent.startpos + downcontent.downsize) + "-" + downcontent.endpos);
		
		        randomAccessFile = new RandomAccessFile(filePath + fileName, "rwd");
		        randomAccessFile.seek(downcontent.startpos + downcontent.downsize);
		        // 将要下载的文件写到保存在保存路径下的文件中
		        inputStream = connection.getInputStream();
//		        inputStream.skip(downcontent.startpos + downcontent.downsize);
		        flushed = new FlushedInputStream(inputStream);
		        flushed.skip(downcontent.startpos + downcontent.downsize);
		}
		catch (SocketTimeoutException e)
		{
			int atest = 1;
			Message msg1 = new Message();
			msgobject obj1 = new msgobject();
			obj1.name = fileName;
			msg1.obj = (Object) obj1;
			msg1.what = 10;
			handler.sendMessage(msg1);
			tool.deleteByname(fileName);
			if (file.exists())
			{
				file.delete();
			}
//			handler.sendEmptyMessage(10);
			return null;
		}
		catch (ConnectTimeoutException e)
		{
			int atest = 2;
			handler.sendEmptyMessage(10);
			tool.deleteByname(fileName);
			if (file.exists())
			{
				file.delete();
			}
			return null;
		}
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tool.deleteByname(fileName);
			if (file.exists())
			{
				file.delete();
			}
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tool.deleteByname(fileName);
				if (file.exists())
				{
					file.delete();
				}
				return null;
			} catch(Exception e)
			{
				e.printStackTrace();
				tool.deleteByname(fileName);
				if (file.exists())
				{
					file.delete();
				}
				handler.sendEmptyMessage(17);
				return null;
			}
		// 设置全局对象，保存下载信息
			Map<String, downinfo> filemap = null;//((myApplication) ctx).getFileMap();
			
			
			downcontent.filesize = connection.getContentLength();
			if ((downcontent.filesize/1024/1024) - fileUtility.getSDFreeSize() >= 0)
			{
				Message msg1 = new Message();
				msgobject obj1 = new msgobject();

				obj1.name = fileName;
				msg1.obj = (Object) obj1;
				msg1.what = 15;
				handler.sendMessage(msg1);
				return null;
			}
//			tool.updataInfos(downcontent.download_id, downcontent.downsize, downcontent.urllink);
//			tool.updataInfoslx(downcontent.download_id, downcontent.filesize, downcontent.urllink);
			String[] arrayStr3 = new String[]{downcontent.urllink};
			tool.updataInfoscc(downcontent.download_id, downcontent.downsize, downcontent.filesize, arrayStr3);
			URL imageUrl;
			if (intent.getStringExtra("imageid") != null)
			{
				try {
					
					imageUrl = new URL(intent.getStringExtra("imageid"));
					InputStream in = imageUrl.openStream();
					downcontent.imageId = Drawable.createFromStream(in, "src");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					downcontent.imageId = null;
					
					return null;
				}
			}
//			downcontent.thread_islive = true;
			filemap.put(fileName, downcontent);
			//下载信息保存进入数据库
//		FlushedInputStream flushed = new FlushedInputStream(inputStream);
		
		try {
			
//			output = new FileOutputStream(file);
			
			byte buffer[] = new byte[4 * 1024];
			int temp = 0;
			int cur = 0;
			int last = 0;
			Map<String, downinfo> downifmap =null;//((myApplication)ctx).getFileMap();
			
			boolean iscancel = false;
			boolean ispause = true;
			if (downifmap.get(fileName)!= null)
			{
				iscancel = downifmap.get(fileName).isCancel;
			}
			if (downifmap.get(fileName)!=null)
			{
				ispause = downifmap.get(fileName).isPause;
			}
			while ((!iscancel)&&(temp != -1))
			{				
				if ((downifmap.get(fileName)!=null)&&(ispause)&&((temp = flushed.read(buffer)) != -1))
				{
					downcontent.downsize += temp;
					downifmap.put(fileName, downcontent);
//					((myApplication)ctx).getFileMap().put(fileName, downcontent);
//					tool.updataInfoslxe(downcontent.download_id, cur, downcontent.urllink);
//					tool.updataInfos(downcontent.download_id, downcontent.downsize, downcontent.urllink);
					String[] arrayStr4 = new String[]{downcontent.urllink};
					tool.updataInfoslxel(downcontent.download_id, downcontent.downsize, cur, arrayStr4);
					randomAccessFile.write(buffer, 0, temp);
					Message msg = new Message(); 
                    msg.what = 4; 
                    if (downcontent.filesize != 0)
                    {
                    	msgobject obj = new msgobject();
                    	obj.percent = (int)(downcontent.downsize*100/downcontent.filesize); 
                    	obj.task_id = downcontent.download_id;
                    	obj.name = fileName;
                    	msg.obj = (Object)obj;
                    	if (downcontent.filesize < 15*1024*1024)
                    	{
                    		cur = (int)(downcontent.downsize*20/downcontent.filesize);
                    	}
                    	else {
                    		cur = (int)(downcontent.downsize*100/downcontent.filesize);
						}
                    	
                    	
                    }
                    
                    if (cur - last > 0)
                    {
                    	handler.sendMessage(msg);
                    }
					
                    last = cur;
				Utility.deBug("info", "已下载文件大小：" + downcontent.downsize);
				}
				if (downifmap.get(fileName) != null)
				{
					ispause = downifmap.get(fileName).isPause;
				}
				
				
				if (downifmap.get(fileName)!= null)
				{
					iscancel = false;//((myApplication)ctx).getFileMap().get(fileName).isCancel;
					//中途取消，删除下载到一半的文件
					if (iscancel)
					{
						
						file.delete();
						//downif.remove(fileName);
					}
					
				} 	
				
			}
			Utility.deBug("info", "下载结束：");
			if (file.exists())
			{
//				output.flush();
			}
			
			//下载完成后，从下载列表中清除该对象
			if (downifmap.get(fileName).downsize >= downifmap.get(fileName).filesize)
			{
				//修改文件名
				String newfilenameString = fileName.replace(".tmpd", ".apk");
				file.renameTo(fileUtility.createFileInSDCard(newfilenameString, filePath));
				file.delete();
				
				downifmap.remove(fileName);
//				((myApplication) ctx).getFileMap().remove(fileName);
				tool.deleteByname(fileName);
				Intent intentl = new Intent("intent.download.end");
				
				Bundle bundle = new Bundle();
				bundle.putString("flag", "1");
				intentl.putExtras(bundle);
				ctx.sendBroadcast(intentl);
				
				msgobject obj1 = new msgobject();
				
            	Message msg2 = new Message();
            	obj1.task_id = downcontent.download_id;
            	obj1.name = fileName;
            	msg2.obj = (Object)obj1;
            	msg2.what = 11;
				handler.sendMessage(msg2);
			}
			//下载取消后，从下载列表中清除该对象
			if (iscancel)
			{
				downifmap.remove(fileName);	
//				((myApplication) ctx).getFileMap().remove(fileName);
				Intent intentl = new Intent("intent.download.end");
				
				Bundle bundle = new Bundle();
				bundle.putString("flag", "2");
				intentl.putExtras(bundle);
				ctx.sendBroadcast(intentl);
				msgobject obj1 = new msgobject();				
            	Message msg2 = new Message();
            	tool.deleteByname(fileName);
//            	tool.deletebyid(Integer.toString(downcontent.download_id));
            	obj1.task_id = downcontent.download_id;
            	obj1.name = fileName;
            	msg2.obj = (Object)obj1;
            	msg2.what = 12;
				handler.sendMessage(msg2);
			}
			
			//handler.sendEmptyMessage(MeTooGameConst.MSG_GET_DATA_SUCC);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			try {
				inputStream.close();
				flushed.close();
				randomAccessFile.close();
				connection.disconnect();
//				tool.closeDb();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TrafficStats.getMobileRxBytes();
		return file;
	}
	static class FlushedInputStream extends FilterInputStream {
		// InputStream in;
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
			// in = inputStream;
		}

		@Override
	    public long skip(long n) throws IOException { 
	        long totalBytesSkipped = 0L; 
	        while (totalBytesSkipped  < n) 
	        { 
	            long bytesSkipped = in.skip(n - totalBytesSkipped); 
	            if (bytesSkipped == 0L) 
	            { 
	                  int leng = read(); 
	                  if (leng == 0) 
	                  { 
	                      break;  // we reached EOF 
	                  } else 
	                  { 
	                      bytesSkipped = 1; // we read one byte 
	                  } 
	           } 
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

	public static InputStream getInputStreamFromUrl(String urlStr) {
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn;

			try {
				urlConn = (HttpURLConnection) url.openConnection();
				InputStream is;
				// urlConn.setConnectTimeout(10*1000);
				// if (urlConn.getResponseCode() != 200)
				// //从Internet获取网页,发送请求,将网页以流的形式读回来
				// {
				// throw new RuntimeException("请求url失败");
				// }
				// else {
				is = urlConn.getInputStream();
				// }

				return is;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static OutputStream getOutputStreamFromUrl(String urlStr) {
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn;

			try {
				urlConn = (HttpURLConnection) url.openConnection();
				OutputStream os;
				// urlConn.setConnectTimeout(10*1000);
				// if (urlConn.getResponseCode() != 200)
				// //从Internet获取网页,发送请求,将网页以流的形式读回来
				// {
				// throw new RuntimeException("请求url失败");
				// }
				// else {
				os = urlConn.getOutputStream();
				// }

				return os;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void uploadData(String urlStr, List<NameValuePair> params, Handler handler) {
		int ret = -1;
		HttpPost httpPost = new HttpPost(urlStr);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
			try {
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
				if(httpResponse.getStatusLine().getStatusCode() == 200)  {
					Object result = EntityUtils.toString(httpResponse.getEntity()); 
					if (result.equals("0")){
						ret = 0; 
					}else if (result.equals("1")){
						ret = 1; 
					}else if (result.equals("201")){
						ret = 1; 
					}else if(result.equals("202")){
						Utility.deBug("HttpUtility", "参数错误啦-------------->" + result);
					}else if(result.equals("203") || result.equals("204")) {
						Utility.deBug("HttpUtility", "服务器异常 请稍后再试--------------->" + result);
					}else{
						Utility.deBug("HttpUtility", "未定义异常--------------->" + result);
					}
				}else {
					Utility.deBug("HttpUtility", "Error Response: "+httpResponse.getStatusLine().toString()); 
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		handler.sendEmptyMessage(ret);
	}
	 public static String HTTPReq(String requestUriString, String reqMethod, String reqContent, boolean  isReqFile, String reqCookie,  Handler handler)
     {
         String str = "";
         String strRet="";
         int ret = -1;
         DataInputStream stream = null;
         InputStream responseStream = null;
         HttpURLConnection request = null;
         String boundary = java.util.UUID.randomUUID().toString(); 
         try
         {
        	 URL url = new URL(requestUriString); 
             request = (HttpURLConnection) url.openConnection(); 
            /* 允许Input、Output，不使用Cache */ 
            request.setDoInput(true); 
            request.setDoOutput(true); 
            request.setUseCaches(false); 
            request.setConnectTimeout(1000);
            request.setReadTimeout(2000);
//             request.Timeout = 1000;
             if (!reqCookie.equals(null))
             {
                 request.setRequestProperty("cookie", "JSESSIONID=" + reqCookie);
             }
             if (reqMethod == "GET")
             {
                 request.setRequestMethod("GET");
             }
             else
             {
            	 if(isReqFile){
                	 stream = new DataInputStream(new FileInputStream(reqContent));
//                     stream = DataOutputStream.File.OpenRead(reqContent);
//                     request.ContentLength = stream.Length;
                     /* 设定传送的method=POST */ 
                     request.setRequestMethod("POST"); 
                      /* setRequestProperty */ 
            	 	 request.setRequestProperty("Content-Length",  String.valueOf(new File(reqContent).length()));
                     request.setRequestProperty("Connection", "Keep-Alive"); 
                     request.setRequestProperty("Charset", "UTF-8"); 
                     request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//                     request.Method = "POST";
//                     request.ContentType = "application/x-www-form-urlencoded";
//                     using (Stream stream2 = request.GetRequestStream())
                     DataOutputStream stream2= new DataOutputStream(request.getOutputStream()); 
                     {
                         byte[] buffer = new byte[8192];
                         int bytesRead = stream.read(buffer, 0, buffer.length);
                         while (bytesRead > 0)
                         {
                             stream2.write(buffer, 0, bytesRead);
                             bytesRead = stream.read(buffer, 0, buffer.length);
                         }
                         stream.close();
                     }
            	 }else{
            		 stream = new DataInputStream(new FileInputStream(reqContent));
            		 File file = new File(reqContent);
            		 byte[] buffer =new byte[(int) file.length()];
            		 while(stream.read(buffer)>0){
            			 
            		 }
//            		 Encoding.UTF_8.GetBytes(reqContent);
//            		 byte[] buffer = Encoding.UTF_8.GetBytes(reqContent);     
	                   request.setRequestMethod("POST");
	                   request.setRequestProperty("Content-Length", String.valueOf(file.length())); 
	                   request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//                   using (Stream stream2 = request.GetRequestStream())
	                   DataOutputStream stream2= new DataOutputStream(request.getOutputStream()); 
	                   {
                       stream2.write(buffer, 0, buffer.length);
	                   } 
            	 }
             }
//             new DefaultHttpClient().execute(httpPost);
//             HttpResponse response =new Htt 
//            		 new DefaultHttpClient().execute
//            		 (null,request);
//
//            		 request.getResponseMessage()
//             using (WebResponse response = request.GetResponse())
//             {
//                 responseStream = request.getreresponse.GetResponseStream();
//                 byte[] buffer = new byte[8192];
//                 int bytesRead = responseStream.read(buffer, 0, buffer.length);
//                 while (bytesRead > 0)
//                 {
//                     str += buffer.toString();//Encoding.UTF_8.GetString(buffer, 0, bytesRead);
//                     bytesRead = responseStream.read(buffer, 0, buffer.length);
//                 }
//                 responseStream.close();
//             }
             /* 取得Response内容 */ 
             InputStream is = request.getInputStream(); 
             int ch; 
             StringBuffer b = new StringBuffer(); 
             while ((ch = is.read()) != -1) { 
                 b.append((char) ch); 
             } 
             strRet += b.toString();//request.getResponseMessage();//str;
             try {
// 				HttpResponse httpResponse = request.getResponseMessage();//new DefaultHttpClient().execute(httpPost);
// 				if(httpResponse.getStatusLine().getStatusCode() == 200)  {
// 					Object result = EntityUtils.toString(httpResponse.getEntity()); 
            	 String result = request.getResponseMessage();
 					if (result.equals("0")){
 						ret = 0; 
 					}else if (result.equals("1")){
 						ret = 1; 
 					}else if (result.equals("201")){
 						ret = 1; 
 					}else if(result.equals("202")){
 						Utility.deBug("HttpUtility", "参数错误啦-------------->" + result);
 					}else if(result.equals("203") || result.equals("204")) {
 						Utility.deBug("HttpUtility", "服务器异常 请稍后再试--------------->" + result);
 					}else{
 						Utility.deBug("HttpUtility", "未定义异常--------------->" + result);
 					}
// 				}else {
// 					Utility.deBug("HttpUtility", "Error Response: "+httpResponse.getStatusLine().toString()); 
// 				}
 			} catch (ClientProtocolException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
             return strRet;
         }
         catch(Exception ex)
         {
             if (request != null)
                 request.disconnect();
             if (stream != null)
				try {
					stream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
             if (responseStream != null)
				try {
					responseStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//             SalesPoint.Utility.Log.LogManage.Instance.WriteLog("上传平台", ex);
             ret =-2;
//             return -2;
         }
//         return 0;
         handler.sendEmptyMessage(ret);
         return strRet;
     }

     public static String readerJson(String strjson, String strname)
     {
//         char[] separator = new char[] { '{', ',', '}', '[', ']' };
    	 String[] separator = new String[] { "{", ",", "}", "[", "]" };
//         String separator ="{}[]";
         String[] strArray = null;
         for(int i=0;i<separator.length;i++){
        	 strArray = strjson.split(separator[i]);
         }
//         String[] strArray = strjson.split(separator);
         for (int i = 0; i < strArray.length; i++)
         {
             if (strArray[i].indexOf(strname) != -1)
             {
                 String[] strArray2 = strArray[i].split(":");
                 if ((strArray2[1].indexOf("'") != -1) || (strArray2[1].indexOf("\"") != -1))
                 {
                     if (strArray2[1].contains("."))
                         strArray2[1] = strArray2[1].substring(1, strArray2[1].lastIndexOf("."));
                     else
                         strArray2[1] = strArray2[1].substring(1, strArray2[1].length() - 2);
                 }
                 return strArray2[1];
             }
         }
         return "-1";
     }
     // new DownloadTask().execute("http://www.google.com");
     /**
      * Implementation of AsyncTask, to fetch the data in the background away from
      * the UI thread.
      */
     private class DownloadTask extends AsyncTask<String, Void, String> {

         @Override
         protected String doInBackground(String... urls) {
             try {
                 return loadFromNetwork(urls[0]);
             } catch (IOException e) {
               return e.getMessage();//getString(R.string.connection_error);
             }
         }

         /**
          * Uses the logging framework to display the output of the fetch
          * operation in the log fragment.
          */
         @Override
         protected void onPostExecute(String result) {
           Log.i("HttpUtility result is", result);
         }
     }

     /** Initiates the fetch operation. */
     private String loadFromNetwork(String urlString) throws IOException {
         InputStream stream = null;
         String str ="";

         try {
             stream = downloadUrl(urlString);
             str = readIt(stream, 500);
        } finally {
            if (stream != null) {
                stream.close();
             }
         }
         return str;
     }

     /**
      * Given a string representation of a URL, sets up a connection and gets
      * an input stream.
      * @param urlString A string representation of a URL.
      * @return An InputStream retrieved from a successful HttpURLConnection.
      * @throws java.io.IOException
      */
     private InputStream downloadUrl(String urlString) throws IOException {

         URL url = new URL(urlString);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setReadTimeout(10000 /* milliseconds */);
         conn.setConnectTimeout(15000 /* milliseconds */);
         conn.setRequestMethod("GET");
         conn.setDoInput(true);
         // Start the query
         conn.connect();
         InputStream stream = conn.getInputStream();
         return stream;

     }

     /** Reads an InputStream and converts it to a String.
      * @param stream InputStream containing HTML from targeted site.
      * @param len Length of string that this method returns.
      * @return String concatenated according to len parameter.
      * @throws java.io.IOException
      * @throws java.io.UnsupportedEncodingException
      */
     private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
         Reader reader = null;
         reader = new InputStreamReader(stream, "UTF-8");
         char[] buffer = new char[len];
         reader.read(buffer);
         return new String(buffer);
     }
     /** 
      * 读取url的xml资源 转成String 
      * @param url 
      * @return 返回 读取url的xml字符串 
      */  
     public String getStringByUrl(String url) {  
         String outputString = "";  
         // DefaultHttpClient  
         DefaultHttpClient httpclient = new DefaultHttpClient();  
         // HttpGet  
         HttpGet httpget = new HttpGet(url);  
         // ResponseHandler  
         ResponseHandler<String> responseHandler = new BasicResponseHandler();  
       
         try {  
             outputString = httpclient.execute(httpget, responseHandler);  
             outputString = new String(outputString.getBytes("ISO-8859-1"), "utf-8");    // 解决中文乱码  
       
             Log.i("HttpClientConnector", "连接成功");  
         } catch (Exception e) {  
             Log.i("HttpClientConnector", "连接失败");  
             e.printStackTrace();  
         }  
         httpclient.getConnectionManager().shutdown();  
         return outputString;  
     }  
     public static HashMap<String, String> parseXml(String url) throws Exception
     {
         HashMap<String, String> hashMap = new HashMap<String, String>();
         
         // 实例化一个文档构建器工厂
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         // 通过文档构建器工厂获取一个文档构建器
         DocumentBuilder builder = factory.newDocumentBuilder();
         // 通过文档通过文档构建器构建一个文档实例
         Document document = builder.parse(new URL(url).openStream());
         //获取XML文件根节点
         Element root = document.getDocumentElement();
        
                     //查找所有persons节点，
         
         NodeList pNodes = root.getElementsByTagName("content");

         
         for (int i = 0; i < pNodes.getLength(); i++) 
         {       	
             //得到第一个person节点
             Element pNode = (Element) pNodes.item(i);
             //获取person节点下的所有子节点
             NodeList cNodes = pNode.getChildNodes();
             //**********************遍历person节点下的所有子节点**********************

             for (int j = 0; j < cNodes.getLength(); j++) 
             {

                 Node node = (Node) cNodes.item(j);
                 //判断是否为元素类型
                 if(node.getNodeType() == Node.ELEMENT_NODE)
                 {
                     Element cNode = (Element) node;
                     //判断是否为name和age元素
                     if ("version".equals(cNode.getNodeName())) 
                     {                               
                     	hashMap.put("version", cNode.getFirstChild().getNodeValue());

                     }
                     else if ("link".equals(cNode.getNodeName()))
                     {
                     	hashMap.put("link", cNode.getFirstChild().getNodeValue());
                     }

                 }

             }
         }

         return hashMap;
     }
}
