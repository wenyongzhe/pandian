package com.supoin.commoninventory.service;

import java.io.File;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.Utility;

public class mService extends Service{
	private long downloadid;
	private String currentfilenameString = null;
	private DownloadManager dMgrDownloadManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		dMgrDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		Utility.deBug("notify",intent.getStringExtra("url"));
		
		currentfilenameString = intent.getStringExtra("name");
		DownloadManager.Request dmreqRequest = new DownloadManager.Request(Uri.parse(intent.getStringExtra("url")));
		dmreqRequest.setTitle("download title");
		dmreqRequest.setDescription("download description");
		dmreqRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
		FileUtility fileUtility = new FileUtility();
		if (!fileUtility.ExistSDCard())
		{
			return 0;
		}
		
		fileUtility.createDirInSDCard("download");
		dmreqRequest.setDestinationInExternalPublicDir("/download/", currentfilenameString);
		IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		
		registerReceiver(mReceiver, filter);
		
		downloadid = dMgrDownloadManager.enqueue(dmreqRequest);
		
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle extras = intent.getExtras();
			Utility.deBug("mReceiver", "onReceive11111111111111");
			long donedownloadid =  extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
			if (donedownloadid == downloadid)
			{
				Utility.deBug("mReceiver", "onReceive222222222222");
				Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
				String sdpath = Environment.getExternalStorageDirectory() + "/";
                String mSavePath = sdpath + "download";
                if (currentfilenameString == null)
                {
                	return;
                }
				File apkfile = new File(mSavePath, currentfilenameString);
		        if (!apkfile.exists())
		        {
		            return;
		        }
		        // 通过Intent安装APK文件
		        Intent i = new Intent(Intent.ACTION_VIEW);
		        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		        startActivity(i);
				unregisterReceiver(mReceiver);
				stopSelf();
			}
		}
	};
};
