package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.supoin.commoninventory.constvalue.DrugSystemConst;

public final class Utility {
	public static boolean  isLowPower(Intent intent){
		Bundle bundle = intent.getExtras();  
        //获取当前电量  
        int current = bundle.getInt("level");  
        //获取总电量　  
        int total = bundle.getInt("scale");  
        //如果当前电量小于总电量的10%,不允许切换RFID
        if(current * 1.0 / total < 0.10)
        	return true;
        else
        	return false;
	}
	// private static long touchTime = 0;
	private static boolean isExit = false;
	private ConnectivityManager mCM; 
	public static String getRadomString(){
		StringWidthWeightRandom random = new StringWidthWeightRandom(new char[]{'0','1','2','3','4','5','6','7','8','9'});  
		return random.getNextString(64);
	}
	public static String getGUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	public static boolean isActivityRunning(Context mContext,String activityClassName){  
	    ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);  
	    List<RunningTaskInfo> info = activityManager.getRunningTasks(1);  
	    if(info != null && info.size() > 0){  
	        ComponentName component = info.get(0).topActivity;  
	        if(activityClassName.equals(component.getClassName())){  
	            return true;  
	            }  
	        }  
	    return false;  
	}  
	 public static HashMap<String, String> parseXml(String filePath) throws Exception
	     {
	         HashMap<String, String> hashMap = new HashMap<String, String>();
	         File file = new File(filePath);
	         if(!file.exists())
	        	 return null;
	         FileInputStream fileInputStream= new FileInputStream(file);
	         // 实例化一个文档构建器工厂
	         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	         // 通过文档构建器工厂获取一个文档构建器
	         DocumentBuilder builder = factory.newDocumentBuilder();
	         // 通过文档通过文档构建器构建一个文档实例
	         Document document = builder.parse(fileInputStream);
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
	                     else if ("fileName".equals(cNode.getNodeName()))
	                     {
	                     	hashMap.put("fileName", cNode.getFirstChild().getNodeValue());
	                     }

	                 }

	             }
	         }

	         return hashMap;
	     }
	//List去掉重复数据
	 public static List<String> removeDuplicate(List<String> list)
	 {
	         Set set = new LinkedHashSet<String>();
	         set.addAll(list);
	         list.clear();
	         list.addAll(set);
	         return list;
	 }
	public static Boolean isValidRackPlace(String rackPlace){
		Pattern pattern = Pattern.compile("^([0-9]{10}$");//("[0-9]*");
		if (rackPlace.length() == 10) {
			if (pattern.matcher(rackPlace).matches()) 
			{
				return true;
			}
		}
		return false;

	}
	/* 
	* MD5加密 
	*/ 
	public static String md5(String str) {
//	    byte[] hash;
//	    try {
//	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
//
//	    } catch (NoSuchAlgorithmException e) {
//
//	        throw new RuntimeException("Huh, MD5 should be supported?", e);
//
//	    } catch (UnsupportedEncodingException e) {
//
//	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
//
//	    }
//	    StringBuilder hex = new StringBuilder(hash.length * 2);
//	    for (byte b : hash) {
//	        if ((b & 0xFF) < 0x10) hex.append("0");
//	        hex.append(Integer.toHexString(b & 0xFF));
//	    }
//	    return hex.toString();
		try {
				MessageDigest bmd5 = MessageDigest.getInstance("MD5");
				bmd5.update(str.getBytes());
				int i;
				StringBuffer buf = new StringBuffer();
				byte[] b = bmd5.digest();
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				return buf.toString();
			} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			}
			return "";
	}
	// 可逆的解密算法
	public static String encryptmd5(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
		a[i] = (char) (a[i] ^'l');
		}
		String s = new String(a);
		return s;
	}
	// / <summary>
		// / 获取程序版本
		// / </summary>
		// / <returns>程序版本</returns>
		public static String GetAppVersion(Context context){
			PackageInfo packinfo;
			String version="";
			try {
				packinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
				String versionname = packinfo.versionName;
				version = versionname;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				version="1.0";
			}	
	        return version;
	 }
	// / <summary>
	// / 比较应用程序版本
	// / </summary>
	// / <param name="ver1"></param>
	// / <param name="ver2"></param>
	// / <returns>-1:ver1 lt ver2;0 ver1=ver2;1:ver1 gl ver2</returns>
	public static int CompareVersion(String ver1, String ver2){
         return ver1.compareTo(ver2);
         
	}
	public static String getCurTime(){
		Date now_date = new Date();
		DateFormat dFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
//		String curTime=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now_date);
		String curTime=new  SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now_date);
		return curTime;
	}
	public static String getCurDateTime(){
		Date now_date = new Date();
		DateFormat dFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		String curTime=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now_date);
		return curTime;
	}
	public static String getCurDate(){
		Date now_date = new Date();
		DateFormat dFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
		String curTime=new  SimpleDateFormat("yyyy-MM-dd").format(now_date);
		return curTime;
	}
	public static String getMAC(Context context) {
		String mac = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
		.getConnectionInfo().getMacAddress();
		if (mac != null)
		{
			mac = mac.replace(":","-");
			return mac.toUpperCase();
		}else{
			Utility.deBug(context, "getMAC 未获取到Mac地址！");
			return "";			
		}
	}
	  public static String GetDrugTypeByCode(String code) {
			String strHead = "";
			if (code.length() == 20)
				strHead = code.substring(0, 2);
			else
				strHead = code.substring(0, 1);
			if (strHead.equals("1") || strHead.equals("89")) {
				return "特殊药品";
			}

			if (strHead.equals("80") || strHead.equals("81")
					|| strHead.equals("82") || strHead.equals("83")
					|| strHead.equals("84") || strHead.equals("85")
					|| strHead.equals("86") || strHead.equals("87")
					|| strHead.equals("88")) {
				return "普通药品";
			}
			return "";
		}
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}
	public static void backInfo(String result) throws JSONException{
		try {
			int errorCode = Integer.parseInt(result.trim());
			String errorMsg = "未知错误码" + errorCode;
			switch (errorCode) {
			case -1:
				errorMsg = "操作类型为空";
				break;
			case -2:
				errorMsg = "客户端主键空";
				break;
			case -3:
				errorMsg = "用户信息为空";
				break;
			case -4:
				errorMsg = "公司编号为空";
				break;
			case -5:
				errorMsg = "用户编号为空";
				break;
			case -6:
				errorMsg = "用户名为空";
				break;
			default:
				break;
			}
			throw new JSONException(errorMsg);
		} catch (NumberFormatException nf) {
			// 啥都不干
		}

	}
	public static boolean getIsexit()
	{
		return isExit;
	}
	public static void setexit()
	{
		isExit = true;
		
	}
//	mCM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
	public static void toastInfo(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
	public void Utility(Context context){
		mCM =(ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);  
	}
	public static boolean exitAcitvity(final Context context, int keyCode,
			KeyEvent event) {
		// if (event.getAction() == KeyEvent.ACTION_DOWN
		// && KeyEvent.KEYCODE_BACK == keyCode) {
		// long currentTime = System.currentTimeMillis();
		// if ((currentTime - touchTime) >= MeTooGameConst.CONFIRM_QUIT_TIMEOUT)
		// {
		// toastInfo(
		// context,
		// context.getResources().getString(
		// R.string.confirm_quit_text));
		// touchTime = currentTime;
		// return true;
		// } else {
		// ((Activity) context).finish();
		// System.exit(0);
		// return false;
		// }
		// }

		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
//			new AlertDialog.Builder(context)
//					.setTitle("离开")
//					.setIcon(R.drawable.exit)
//					.setMessage("亲，真的要走吗？")
//					.setNegativeButton("确定",
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									// TODO Auto-generated method stub
//									//清掉所有的通知
//									
//									NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//									manager.cancelAll();
//									
//									((Activity) context).finish();
//									System.exit(0);
//									isExit = true;
//										
//								}
//							})
//					.setPositiveButton("取消",
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									isExit = false;
//								}
//							}).show();
		}
		return isExit;
	}
	/**
	*得到Exception所在代码的行数
	*如果没有行信息,返回-1
	*/
	public static int getLineNumber(Exception e){
		StackTraceElement[] trace =e.getStackTrace();
		if(trace==null||trace.length==0)
			return -1; //
		return trace[0].getLineNumber();
	}
	public static void deBug(Context context, String msg) {
		if (DrugSystemConst.DEBUG) {
			Logger loggerM=LoggerManager.getLoggerInstance(); 
			loggerM.debug(LoggerManager.getLogMessage(msg));  
		}
	}
	public static void deBug(String tag, String msg) {
		if (DrugSystemConst.DEBUG) {
			Logger loggerM=LoggerManager.getLoggerInstance(); 
//			String fileName="AppLog.txt";
//			String path=FileUtility.getSdCardPath()+"/Supoin/log/";
//			File filePath=new File(path);
//			if(!filePath.exists())
//				filePath.mkdirs();
//			File file =new File(path+fileName);
//			if(!file.exists()){
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//			String className = null;
//			String fullClassName;
//			String methodName = null;
//			int lineNumber = 0;
//			if (elements.length < 3) {
//				Log.e(tag, "Stack to shallow");
//			}else{
//				 fullClassName = elements[3].getClassName();
//				 className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
//				 methodName = elements[3].getMethodName();
//				 lineNumber = elements[3].getLineNumber();
//				Log.i(className + "." + methodName + "():" + lineNumber,msg);
//			}
//		
////			String fileContent=getCurTime()+" "+tag+" "+msg+"\r\n";
//			String fileContent=getCurTime()+"    "+className + "." + methodName + "():" + lineNumber+"    "+msg;

			loggerM.debug(LoggerManager.getLogMessage(msg));  
//			FileIO.WriteFile(path, fileName, fileContent);
		}
	}
	/**
	 * 检测网络是否连接
	 * 
	 * @return
	 */
	public static boolean checkNetworkState(Context context) {
		boolean flag = false;
		// 得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}
	/**
	 * @param context
	 * @return 获取网络连接状态
	 */
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		toastInfo(context, "网络连接错误 请检查网络");
		return false;
	}
	public static boolean isConnectingToInternetnotip(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
//		toastInfo(context, "网络连接错误 请检查网络");
		return false;
	}
	public static int DipToPixels(Context context, int dip) {
		float SCALE = context.getResources().getDisplayMetrics().density;
		return (int) (dip * SCALE + 0.5f);
	}

	public static float PixelsToDip(Context context, int Pixels) {
		final float SCALE = context.getResources().getDisplayMetrics().density;
		return Pixels / SCALE;
	}

	//打开或关闭GPRS   
    public boolean gprsEnabled(boolean bEnable)  
    {  
        Object[] argObjects = null;  
                  
        boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled");  
        if(isOpen == !bEnable)  
        {  
        	setGprsEnabled("setMobileDataEnabled", bEnable);  
        }  
          
        return isOpen;    
    }  
      
    //检测GPRS是否打开   
    public boolean gprsIsOpenMethod(String methodName)  
    {  
        Class cmClass       = mCM.getClass();  
        Class[] argClasses  = null;  
        Object[] argObject  = null;  
          
        Boolean isOpen = false;  
        try  
        {  
            Method method = cmClass.getMethod(methodName, argClasses);  
  
            isOpen = (Boolean) method.invoke(mCM, argObject);  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
  
        return isOpen;  
    }  
      
    //开启/关闭GPRS   
    public void setGprsEnabled(String methodName, boolean isEnable)  
    {  
        Class cmClass       = mCM.getClass();  
        Class[] argClasses  = new Class[1];  
        argClasses[0]       = boolean.class;  
          
        try  
        {  
            Method method = cmClass.getMethod(methodName, argClasses);  
            method.invoke(mCM, isEnable);  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
    /**
     * 判断网络是否已连接
     * @param context
     * @return
     */
    public static boolean checkConnection(Context context) {
    	boolean isConnected = true;
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
        	isConnected = false;
        }
        return isConnected;
    }
    
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static Point getDisplaySize(Context context) {
    	Point size = new Point();
    	Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	if (Build.VERSION.SDK_INT < 13) {
    		size.x = display.getWidth();
    		size.y = display.getHeight();
    	} else {
    		display.getSize(size);
    	}
    	return size;
    }
	
    /**
	 * 返回当前程序版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;

		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		return versionCode;
	}

	/**
	 * 返回当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		return versionName;
	}
	public static class AppState{
		private int appstate;
		private String packagename;
		
		public AppState(int appstate, String packagename) {
			this.appstate = appstate;
			this.packagename = packagename;
		}
		
		public int getAppstate() {
			return appstate;		}
		
		public void setAppstate(int appstate) {
			this.appstate = appstate;
		}
		
		public String getPackagename() {
			return packagename;
		}
	}
	
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isInteger(float num){
		String[] nums = String.valueOf(num).split("\\.");
		if(Integer.parseInt(nums[1]) > 0){
			return false;
		}
		
		return true;
	}

}
