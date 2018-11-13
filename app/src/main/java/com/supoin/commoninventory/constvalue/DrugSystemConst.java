package com.supoin.commoninventory.constvalue;

import android.os.Build;


public final class DrugSystemConst {	
	//消息
	public static final int MSG_QUIT = 0x01;
	public static final int MSG_NEXT = 0x02;
	public static final int MSG_GET_DATA_SUCC = 0x03;
	public static final int MSG_GET_DATA_NO_DATA = 0x04;
	public static final int MSG_CATE_PANEL_BTN = 0X05;
	public static final int MSG_LOCATON_SUCC = 0x06;
	public static final int MSG_LOCATON_OPEN_GPS = 0x07;
	public static final int MSG_LOCATON_FAIL = 0x08;
	public static final int MSG_LOCATON_RESERVE = 0x09;
	public static final int MSG_LOCATON_START = 0x0A;
	//conn type
	public static final int CONN_TYPE_LINE = 0;
	public static final int CONN_TYPE_WIFI = 1;
	public static final int CONN_TYPE_GPRS = 2;
	//connstate
	public static final int ConnectSuccess = 1;
	public static final int NoConnect = 0;
	//手势触摸距离
	public static final int FLING_MIN_DISTANCE = 100;
	public static final int FLING_MIN_VELOCITY = 50;
	
	//线程超时
	public static final int LOGO_TIMEOUT = 2*1000;
	public static final int CONFIRM_QUIT_TIMEOUT = 2*1000;
	public static final int CATE_BUTTON_TIMEOUT = 10*1000;
	public static final int CHECKUSER_TIMEOUT = 10*1000;
	
	//调试开关
	public static final boolean DEBUG = true;

	
	//打开定位提示
	public static final boolean isAllowGPS = false;
	public static final String download_dir = "Supoin";
	public static String packageName="com.supoin.commoninventory";
	public static String appApkName="com.supoin.commoninventory.apk";
	public static boolean isFirst = true;
}
