package com.supoin.commoninventory.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.supoin.commoninventory.activity.StockMenuActivity;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.publicontent.myApplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * 主线程未捕获异常处理
 * @author qiujie
 *
 * @date 2017-6-9 上午11:03:40 
 *
 */
public class AppExceptionUncatch implements UncaughtExceptionHandler {
	private static AppExceptionUncatch uncatch = new AppExceptionUncatch();
	private Context context;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new LinkedHashMap<String, String>();
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss", Locale.getDefault());

	private AppExceptionUncatch() {
	}

	/** 获取AppExceptionUncatch实例 ,单例模式 */
	public static AppExceptionUncatch getInstance() {
		return uncatch;
	}

	public void init(Context context) {
		if (context == null)
			return;
		this.context = context;
		Thread.setDefaultUncaughtExceptionHandler(uncatch);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("===", "主线程，发生未捕获异常了！！！\n", ex);
		handleException(ex);
		SystemClock.sleep(2000);
		Intent intent = new Intent(context, StockMenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		// 退出程序
		System.exit(1);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private void handleException(final Throwable ex) {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(myApplication.mContext, "程序发生了严重的未知异常，即将重启！", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		collectDeviceInfo(context);
		saveCrashInfo2File(ex);
	}

	/**
	 * 收集设备参数信息
	 */
	private void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = "" + pi.versionCode;
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e("===", "收集包信息时发生错误", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				Log.e("===", "收集崩溃信息时发生的错误", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 */
	private void saveCrashInfo2File(Throwable ex) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
//			String dbDir=android.os.Environment.getExternalStorageDirectory().toString();  
//            dbDir += "/Supoin/crash";//数据库所在目录  
			String dbDir = SystemConst.crashPath;//数据库所在目录  
			GUtils.createDirorFileParentDir(dbDir);
			String fileName = "/crash-" + formatter.format(new Date()) + ".log";
			File logFile = new File(dbDir, fileName);
			FileOutputStream fos = new FileOutputStream(logFile);
			fos.write(sb.toString().getBytes());
			fos.close();
		} catch (Exception e) {
			Log.e("===", "写入文件时发生错误…", e);
		}
	}

}
