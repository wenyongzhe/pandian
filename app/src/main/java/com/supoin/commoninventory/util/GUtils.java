package com.supoin.commoninventory.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.supoin.commoninventory.publicontent.myApplication;

public class GUtils {
	/** ���ع㲥ʵ�� */
	public static final LocalBroadcastManager BROADCASTMANAGER = LocalBroadcastManager
			.getInstance(myApplication.mContext);
	private static long lastClickTime;
	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * ��ʾ��Toast
	 * 
	 * @param text
	 */
	public static void showToastLong(String text) {
		Toast.makeText(myApplication.mContext, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ʾ��Toast
	 * 
	 * @param text
	 */
	public static void showToastShort(String text) {
		Toast.makeText(myApplication.mContext, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ���ݸ����ļ�·�������ļ���
	 * 
	 * @param filePpath
	 */
	public static boolean createDirorFileParentDir(File filePpath) {
		if (filePpath == null)
			return false;
		if (filePpath.isFile()) {
			File parentFile = filePpath.getParentFile();
			if (!parentFile.exists())
				return parentFile.mkdirs();
		} else {
			if (!filePpath.exists())
				return filePpath.mkdirs();
		}
		return true;
	}

	/**
	 * ���ݸ����ַ���·�������ļ���
	 * 
	 * @param path
	 */
	public static boolean createDirorFileParentDir(String path) {
		return createDirorFileParentDir(new File(path));
	}

	/**
	 * ��ֹ���ٵ��������ύ
	 * 
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = SystemClock.uptimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 696)
			return true;
		lastClickTime = time;
		return false;
	}

	/**
	 * ��ȡ��ǰʱ�����ڵĺ���ֵ��ʽ���ַ�����eg��2016-11-11 11:11:11
	 * 
	 * @return
	 */
	public static String getNowFormatDateTime() {
		return new SimpleDateFormat(dateTimeFormat, Locale.getDefault())
				.format(new Date());
	}

	public static void scanFile(Context context,String filePath) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(new File(filePath)); 
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
//            Uri localUri = Uri.fromFile(new File(filePath));
//            Intent localIntent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,localUri);
//            context.sendBroadcast(localIntent);

        } else {
        	Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
            scanIntent.setData(Uri.fromFile(new File(filePath)));
            context.sendBroadcast(scanIntent);

        }
		
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//�����4.4�����ϰ汾
			MediaScannerConnection.scanFile(myApplication.mContext, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + filePath}, null, null);
        } else {
        	myApplication.mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory()))); 
        }*/
    }
	public static boolean isArrContainValue(String[] arr,String targetValue){
	    return Arrays.asList(arr).contains(targetValue);
	}
}
