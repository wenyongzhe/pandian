package com.supoin.commoninventory.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.LoginActivity;
import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.contentprovider.providertool;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.HttpUtility;
import com.supoin.commoninventory.util.Utility;
import com.supoin.commoninventory.util.downinfo;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AppDownloadService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		registerNetworkReceiver();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unRegisterNetworkReceiver();
	}

	private String urlLink;
	private String name;
	private int index;
	private int len; 
    public NotificationManager manager; 
    private Notification notif;
    private static Thread  currentThread[];
	//public static int thread_id = 0;
	private int tast_id = 0;
	private List<String> download_task_List = null;
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("WrongConstant")
	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Utility.deBug("onStartCommand", "start-----------------");
		if (intent == null)
		{
			Utility.deBug("onStartCommand", "intent = null-----------------");
			return 0;
		}
		providertool tool = new providertool(getApplicationContext());
		tool.cleandatabase();
		
		urlLink = intent.getStringExtra("downloadLink");	
		name = intent.getStringExtra("name");
		index = intent.getIntExtra("index", 0xFEFE);
		Utility.deBug("onStartCommand", "end-----------------");
		//启动通知提示
		if (download_task_List == null)
		{
			download_task_List = new ArrayList<String>();
			
			Map<String, downinfo> map = new HashMap<String, downinfo>();
			map = tool.getallinfo();
			tool.closeDb();
			 Iterator it = map.keySet().iterator(); 
			   while (it.hasNext()){ 
			    String key; 
			    key=(String)it.next(); 		    
//			    downResult.add(filemap.get(key));
			    downinfo info = map.get(key);
			    download_task_List.add(info.filename);
//			    ((myApplication)getApplicationContext()).getFileMap().put(info.filename, info);
			    
			   }
//			   if (download_task_List.size() >= 5)
//			   {
//				   handler.sendEmptyMessage(6);
//				   return 0;
//			   }
			   
		}
		//整理下载链表
		List<String> tmp_List = new ArrayList<String>();
		for(int i = 0; i < download_task_List.size(); i++)
		{
			FileUtility fileUtility  = new FileUtility();
			if (!fileUtility.isFileExist(download_task_List.get(i), DrugSystemConst.download_dir))
			{
				tmp_List.add(download_task_List.get(i));
			}
			
			
		}
		for (int j = 0; j < tmp_List.size(); j++)
		{
			download_task_List.remove(tmp_List.get(j));
		}
		
		if (download_task_List.contains(name+".tmpd"))
		{
			Utility.deBug("download_task_List", "download_task_List=true----------------");
			if ((((myApplication)getApplicationContext()).getFileMap().get(name+".tmpd") != null)&&((myApplication)getApplicationContext()).getFileMap().get(name+".tmpd").isPause == true)
			{
				//正常进入下载
				Utility.deBug("download_task_List", "download_task_List=true1111----------------");
				if (((myApplication)getApplicationContext()).getFileMap().get(name+".tmpd").thread_islive == true)
				{
					Utility.deBug("download_task_List", "download_task_List=true2222----------------");
					return 0;
				}
				
			}
			else {
				Utility.deBug("download_task_List", "download_task_List=true3333----------------");
				return 0;
			}
			
			
		}
		else {
			Utility.deBug("download_task_List", "download_task_List=false----------------");
			 if (download_task_List.size() >= 5)
			   {
				   handler.sendEmptyMessage(6);
				   return 0;
			   }
			download_task_List.add(name+".tmpd"); 
		}
		
		 
         
//         manager.notify(tast_id, notif);
		Thread newThread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				
				HttpUtility httpDownload = new HttpUtility(getApplicationContext(), name + ".tmpd");
				//				if (httpDownload.flag_sucss == 0)
//				{
//					//失败
//					return;
//				}
				try {
					
					providertool tool = new providertool(getApplicationContext());
					if (HttpUtility.task_id == 0)
					{						
						HttpUtility.task_id  = tool.getTableNum();
					}
					httpDownload.downloadBinary(urlLink, DrugSystemConst.download_dir, name + ".tmpd", handler,getApplication(), intent, tool);
				} catch (SocketTimeoutException e) {
					// TODO: handle exception
					handler.sendEmptyMessage(10);
				}catch (ConnectTimeoutException e) {
					// TODO: handle exception
					handler.sendEmptyMessage(10);
				}
				
			}

		};
		newThread.setName(name + ".tmpd");
		newThread.start();
//		currentThread.
		return super.onStartCommand(intent, flags, startId);
	}
	
	public BroadcastReceiver networkBroadcast= new BroadcastReceiver()
		{

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if (!Utility.isConnectingToInternet(context))
				{
					((myApplication )getApplicationContext()).setFileMapstate(false);
					((myApplication )getApplicationContext()).setFileMaplive(false);
					Intent intentl = new Intent("intent.download.end");					
					Bundle bundle = new Bundle();
					bundle.putString("flag", "3");
					intentl.putExtras(bundle);
					sendBroadcast(intentl);
				}
				else {
					providertool tool = new providertool(getApplicationContext());
					tool.cleandatabase();					
					Map<String, downinfo>filemaple = new HashMap<String, downinfo>();
					filemaple =	tool.getallinfo();					
					Iterator it = filemaple.keySet().iterator(); 
					   while (it.hasNext()){ 
					    String key; 
					    key=(String)it.next();
					    
//					    filemaple.get(key).filename
					   }
					
				}
				
			}};
	
		
	

	private void registerNetworkReceiver() {  
	    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);  
	    getApplicationContext().registerReceiver(networkBroadcast, filter);  
	}  
	  
	private void unRegisterNetworkReceiver() {  
		getApplicationContext().unregisterReceiver(networkBroadcast);  
	} 
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		private Handler handler = new Handler() {
		
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(msg.what == 4)
					{
						len = ((msgobject)(msg.obj)).percent;
						notif.contentView.setTextViewText(R.id.content_view_text1, len+"%"); 
						
						notif.contentView.setTextViewText(R.id.textView1, ((msgobject)(msg.obj)).name);
		                notif.contentView.setProgressBar(R.id.content_view_progress, 100, len, false); 
		                manager.notify(((msgobject)(msg.obj)).task_id, notif); 
					}	
					else if (msg.what == 6)
					{
						Toast.makeText(getApplicationContext(), "已达同时下载任务上限", Toast.LENGTH_SHORT).show();
					}
					else if (msg.what == 7)
					{
						Utility.toastInfo(getApplicationContext(), "开始下载");
						  sendDownloadReceiver();
					}
					else if (msg.what == 8)
					{
						Toast.makeText(getApplicationContext(), "文件已存在", Toast.LENGTH_SHORT).show();
					}
					else if (msg.what == 9)
					{
						Intent lintent = new Intent(getApplicationContext(),LoginActivity.class); 
						
						lintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
						PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, lintent, 0); 
						
						ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
						List<RunningTaskInfo> list = am.getRunningTasks(100);
						
						boolean isAppRunning = false;
						String MY_PKG_NAME = SystemConst.appName;
//						String MY_PKG_NAME = "com.supoin.commoninventory";
						for (RunningTaskInfo info : list) {
							if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
								isAppRunning = true;
								
		//						Log.i(TAG,info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
								break;
							}
						}
						 lintent.putExtra("isapprun", isAppRunning);
				         manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
				         notif = new Notification(); 
				         notif.icon = R.drawable.icon; 
				         notif.flags |= Notification.FLAG_AUTO_CANCEL;
				         notif.tickerText = name; 
				         //通知栏显示所用到的布局文件 
				         notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view); 
				         notif.contentIntent = pIntent; 
				         notif.contentView.setTextViewText(R.id.textView1, name);
				         manager.notify(((msgobject)(msg.obj)).task_id, notif);
				         
					}
					else if (msg.what == 10)
					{
						if (download_task_List != null)
						{
							if (download_task_List.contains(((msgobject)(msg.obj)).name))
							{
								download_task_List.remove(((msgobject)(msg.obj)).name);
							}
						}
						Toast.makeText(getApplicationContext(), "下载超时，请重新下载", Toast.LENGTH_SHORT).show();
						
					}
					else if (msg.what == 12)
					{
						if (manager != null)
						{
							manager.cancel(((msgobject)(msg.obj)).task_id);
							
						}
						String sdpath = Environment.getExternalStorageDirectory() + "/";
		                String mSavePath = sdpath + DrugSystemConst.download_dir;
		                if(((msgobject)(msg.obj)).name == null)
		                {
		                	return;
		                }
		                if (download_task_List != null)
						{
							if (download_task_List.contains(((msgobject)(msg.obj)).name))
							{
								download_task_List.remove(((msgobject)(msg.obj)).name);
							}
						}
		                
						
					}
					else if (msg.what == 14)
					{
						if (download_task_List != null)
						{
							if (download_task_List.contains(((msgobject)(msg.obj)).name))
							{
								download_task_List.remove(((msgobject)(msg.obj)).name);
							}
						}
						
						Toast.makeText(getApplicationContext(), "T卡不存在，请安装T卡", Toast.LENGTH_SHORT).show();
						
					}
					else if (msg.what == 15)
					{
						if (download_task_List != null)
						{
							if (download_task_List.contains(((msgobject)(msg.obj)).name))
							{
								download_task_List.remove(((msgobject)(msg.obj)).name);
							}
						}
						Toast.makeText(getApplicationContext(), "剩余空间小于文件大小", Toast.LENGTH_SHORT).show();
					}
					else if (msg.what == 11)
					{
						if (manager != null)
						{
							manager.cancel(((msgobject)(msg.obj)).task_id);
							
						}
						String sdpath = Environment.getExternalStorageDirectory() + "/";
		                String mSavePath = sdpath + DrugSystemConst.download_dir;
		                if(((msgobject)(msg.obj)).name == null)
		                {
		                	return;
		                }
		                if (download_task_List != null)
						{
							if (download_task_List.contains(((msgobject)(msg.obj)).name))
							{
								download_task_List.remove(((msgobject)(msg.obj)).name);
							}
						}
		//                String newfilenameString = ((msgobject)(msg.obj)).name;
		                String newfilenameString = ((msgobject)(msg.obj)).name.replace(".tmpd", ".apk");
		                File apkfile = new File(mSavePath, newfilenameString);
		                Intent i = new Intent(Intent.ACTION_VIEW);
				        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
				        startActivity(i);
				        
				        Notification note = new Notification(R.drawable.icon, 
								"下载完成，点击安装", 
								System.currentTimeMillis());
				        
				        Intent lintent = new Intent(getApplicationContext(),LoginActivity.class); 
						 lintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_CLEAR_TOP);
						PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, lintent, 0);

						note.setLatestEventInfo(getApplicationContext(), "下载完成", ((msgobject)(msg.obj)).name,  pIntent);
				        note.flags |= Notification.FLAG_AUTO_CANCEL;
				        NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
				        manager1.notify(1000, note);
				        
					}
					else if (msg.what == 17)
					{
						Toast.makeText(getApplicationContext(), "该链接无效，退出下载", Toast.LENGTH_SHORT).show();
					}
					else 
					{
						String filePath = new FileUtility().getFilePathFromFileName(name, ".apk");
						if ( filePath == null) {
							return;
						}
						File apkfile = new File(filePath);
				        if (!apkfile.exists()) {
				            return;
				        }    
				        Intent i = new Intent(Intent.ACTION_VIEW);
				        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
				        //startActivity(i);
				        getApplicationContext().startActivity(i);
					}
					
				}
				
			};

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what == 4)
			{
				len = ((msgobject)(msg.obj)).percent;
				notif.contentView.setTextViewText(R.id.content_view_text1, len+"%"); 
				
				notif.contentView.setTextViewText(R.id.textView1, ((msgobject)(msg.obj)).name);
                notif.contentView.setProgressBar(R.id.content_view_progress, 100, len, false); 
                manager.notify(((msgobject)(msg.obj)).task_id, notif); 
			}	
			else if (msg.what == 6)
			{
				Toast.makeText(getApplicationContext(), "已达同时下载任务上限", Toast.LENGTH_SHORT).show();
			}
			else if (msg.what == 7)
			{
				Utility.toastInfo(getApplicationContext(), "开始下载");
				  sendDownloadReceiver();
			}
			else if (msg.what == 8)
			{
				Toast.makeText(getApplicationContext(), "文件已存在", Toast.LENGTH_SHORT).show();
			}
			else if (msg.what == 9)
			{
				Intent lintent = new Intent(getApplicationContext(),LoginActivity.class); 
				
				lintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_CLEAR_TOP);

				PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, lintent, 0); 
				
				ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
				List<RunningTaskInfo> list = am.getRunningTasks(100);
				
				boolean isAppRunning = false;
//				String MY_PKG_NAME = "com.supoin.commoninventory";
				String MY_PKG_NAME = SystemConst.appName;;
				for (RunningTaskInfo info : list) {
					if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
						isAppRunning = true;
						
//						Log.i(TAG,info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
						break;
					}
				}
				 lintent.putExtra("isapprun", isAppRunning);
		         manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
		         notif = new Notification(); 
		         notif.icon = R.drawable.icon; 
		         notif.flags |= Notification.FLAG_AUTO_CANCEL;
		         notif.tickerText = name; 
		         //通知栏显示所用到的布局文件 
		         notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view); 
		         notif.contentIntent = pIntent; 
		         notif.contentView.setTextViewText(R.id.textView1, name);
		         manager.notify(((msgobject)(msg.obj)).task_id, notif);
		         
			}
			else if (msg.what == 10)
			{
				if (download_task_List != null)
				{
					if (download_task_List.contains(((msgobject)(msg.obj)).name))
					{
						download_task_List.remove(((msgobject)(msg.obj)).name);
					}
				}
				Toast.makeText(getApplicationContext(), "下载超时，请重新下载", Toast.LENGTH_SHORT).show();
				
			}
			else if (msg.what == 12)
			{
				if (manager != null)
				{
					manager.cancel(((msgobject)(msg.obj)).task_id);
					
				}
				String sdpath = Environment.getExternalStorageDirectory() + "/";
                String mSavePath = sdpath + DrugSystemConst.download_dir;
                if(((msgobject)(msg.obj)).name == null)
                {
                	return;
                }
                if (download_task_List != null)
				{
					if (download_task_List.contains(((msgobject)(msg.obj)).name))
					{
						download_task_List.remove(((msgobject)(msg.obj)).name);
					}
				}
                
				
			}
			else if (msg.what == 14)
			{
				if (download_task_List != null)
				{
					if (download_task_List.contains(((msgobject)(msg.obj)).name))
					{
						download_task_List.remove(((msgobject)(msg.obj)).name);
					}
				}
				
				Toast.makeText(getApplicationContext(), "T卡不存在，请安装T卡", Toast.LENGTH_SHORT).show();
				
			}
			else if (msg.what == 15)
			{
				if (download_task_List != null)
				{
					if (download_task_List.contains(((msgobject)(msg.obj)).name))
					{
						download_task_List.remove(((msgobject)(msg.obj)).name);
					}
				}
				Toast.makeText(getApplicationContext(), "剩余空间小于文件大小", Toast.LENGTH_SHORT).show();
			}
			else if (msg.what == 11)
			{
				if (manager != null)
				{
					manager.cancel(((msgobject)(msg.obj)).task_id);
					
				}
				String sdpath = Environment.getExternalStorageDirectory() + "/";
                String mSavePath = sdpath + DrugSystemConst.download_dir;
                if(((msgobject)(msg.obj)).name == null)
                {
                	return;
                }
                if (download_task_List != null)
				{
					if (download_task_List.contains(((msgobject)(msg.obj)).name))
					{
						download_task_List.remove(((msgobject)(msg.obj)).name);
					}
				}
//                String newfilenameString = ((msgobject)(msg.obj)).name;
                String newfilenameString = ((msgobject)(msg.obj)).name.replace(".tmpd", ".apk");
                File apkfile = new File(mSavePath, newfilenameString);
                Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
		        startActivity(i);
		        
		        Notification note = new Notification(R.drawable.icon, 
						"下载完成，点击安装", 
						System.currentTimeMillis());
		        
		        Intent lintent = new Intent(getApplicationContext(),LoginActivity.class); 
				 lintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_CLEAR_TOP);
				PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, lintent, 0); 
				
				note.setLatestEventInfo(getApplicationContext(), "下载完成", ((msgobject)(msg.obj)).name,  pIntent);
		        note.flags |= Notification.FLAG_AUTO_CANCEL;
		        NotificationManager manager1 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
		        manager1.notify(1000, note);
		        
			}
			else if (msg.what == 17)
			{
				Toast.makeText(getApplicationContext(), "该链接无效，退出下载", Toast.LENGTH_SHORT).show();
			}
			else 
			{
				String filePath = new FileUtility().getFilePathFromFileName(name, ".apk");
				if ( filePath == null) {
					return;
				}
				File apkfile = new File(filePath);
		        if (!apkfile.exists()) {
		            return;
		        }    
		        Intent i = new Intent(Intent.ACTION_VIEW);
		        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
		        //startActivity(i);
		        getApplicationContext().startActivity(i);
			}
			
		}
		
	};
	
	private void sendDownloadReceiver(){
		Intent intent=new Intent();
        intent.setAction("activity.GameDetailActivity.UPDATE");
        intent.putExtra("index", index);
        sendBroadcast(intent);
	}
}
