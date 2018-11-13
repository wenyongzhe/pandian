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
		Utility.deBug("ConnectionChangeReceiver", "����״̬�ı�");
		if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
			// ����������ӷ���
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
			Utility.deBug("ConnectionChangeReceiver", "��������ʧ��");
		}
	}

	public interface ConnectionSuccessCallBack {
		void exec();
	}
}
