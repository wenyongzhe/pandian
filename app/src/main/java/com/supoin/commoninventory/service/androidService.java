package com.supoin.commoninventory.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.supoin.commoninventory.thread.ThreadReadWriterIOSocket;
import com.supoin.commoninventory.util.Utility;

public class androidService extends Service
{

	public static final String TAG = "androidService";
	public static Boolean mainThreadFlag = true;
	public static Boolean ioThreadFlag = true;
	ServerSocket serverSocket = null;
	final int SERVER_PORT = 10086;
	private Context mContext;
	private sysBroadcastReceiver sysBR;
	public void androidService(Context context){
		this.mContext = context;
	}
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Utility.deBug(TAG, "androidService--->onCreate()");
		/* 创建内部类sysBroadcastReceiver 并注册registerReceiver */
		sysRegisterReceiver();
		/*
		 * new Thread() { public void run() { doListen(); }; }.start();
		 */
	}

	private void doListen()
	{
		serverSocket = null;
		try
		{
			Utility.deBug("chl", "doListen()");
			serverSocket = new ServerSocket(SERVER_PORT);
			Utility.deBug("chl", "doListen() 2");
			while (mainThreadFlag)
			{
				Utility.deBug("chl", "doListen() 4");
				Socket socket = serverSocket.accept();
				Utility.deBug("chl", "doListen() 3");
				new Thread(new ThreadReadWriterIOSocket(this, socket)).start();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* 创建内部类sysBroadcastReceiver 并注册registerReceiver */
	private void sysRegisterReceiver()
	{
		Utility.deBug("chl", Thread.currentThread().getName() + "---->" + "sysRegisterReceiver");
		sysBR = new sysBroadcastReceiver();
		/* 注册BroadcastReceiver */
		IntentFilter filter1 = new IntentFilter();
		/* 新的应用程序被安装到了设备上的广播 */
		filter1.addAction("android.intent.action.PACKAGE_ADDED");
		filter1.addDataScheme("package");
		filter1.addAction("android.intent.action.PACKAGE_REMOVED");
		filter1.addDataScheme("package");
		registerReceiver(sysBR, filter1);
	}

	/* 内部类：BroadcastReceiver 用于接收系统事件 */
	private class sysBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if (action.equalsIgnoreCase("android.intent.action.PACKAGE_ADDED"))
			{
				// ReadInstalledAPP();
			} else if (action.equalsIgnoreCase("android.intent.action.PACKAGE_REMOVED"))
			{
				// ReadInstalledAPP();
			}
			Utility.deBug(TAG, Thread.currentThread().getName() + "---->" + "sysBroadcastReceiver onReceive");
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Utility.deBug("chl", "androidService----->onStartCommand()");
		mainThreadFlag = true;
		new Thread()
		{
			public void run()
			{
				doListen();
			};
		}.start();
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 关闭线程
		mainThreadFlag = false;
		ioThreadFlag = false;
		// 关闭服务器
		try
		{
			Utility.deBug(TAG, Thread.currentThread().getName() + "---->" + "serverSocket.close()");
			if (serverSocket != null) serverSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		Utility.deBug(TAG, Thread.currentThread().getName() + "---->" + "**************** onDestroy****************");
	}

}
