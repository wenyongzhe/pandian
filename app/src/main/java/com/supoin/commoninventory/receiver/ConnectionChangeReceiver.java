package com.supoin.commoninventory.receiver;



import com.supoin.commoninventory.util.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;


public class ConnectionChangeReceiver extends BroadcastReceiver {
	ConnectionSuccessCallBack callback;

	public ConnectionChangeReceiver(ConnectionSuccessCallBack callback) {
		super();
		this.callback = callback;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Utility.deBug("ConnectionChangeReceiver", "网络状态改变");
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
			// 获得网络连接服务
			ConnectivityManager connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			//wifi
			State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.getState();
			if (State.CONNECTED == state) {
				callback.exec();
				return;
			}
			//gprs
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState();
			if (State.CONNECTED == state) {
				callback.exec();
				return;
			}
			Utility.deBug("ConnectionChangeReceiver", "网络连接失败");
		}
	}

	public interface ConnectionSuccessCallBack {
		void exec();
	}
}
