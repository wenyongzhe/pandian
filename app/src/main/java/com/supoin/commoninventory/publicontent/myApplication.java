package com.supoin.commoninventory.publicontent;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.LoggerManager;
import com.supoin.commoninventory.util.downinfo;
import com.uhf.uhf.UHF1.UHF1Application;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class myApplication extends UHF1Application {

	private Map<String, downinfo> fileMap;
	private static Stack<Activity> activityStack;
	private List<Activity> activityList = new ArrayList<Activity>();
	private static myApplication singleton;
	private Logger loggerM;
	public final static String CONF_FRIST_START = "isFristStart";
	public static boolean flag = false;
	public static Context mContext;

	// 单例模式,加锁
	public synchronized static myApplication getInstance() {

		if (singleton == null) {
			singleton = new myApplication();
		}
		return singleton;
	}

	public Map<String, downinfo> getFileMap() {
		return fileMap;
	}

	public void setFileMapstate(boolean flag) {
		Iterator it = fileMap.keySet().iterator();
		while (it.hasNext()) {
			String key;
			key = (String) it.next();
			fileMap.get(key).isPause = flag;
		}
	}

	public void setFileMaplive(boolean flag) {
		Iterator it = fileMap.keySet().iterator();
		while (it.hasNext()) {
			String key;
			key = (String) it.next();
			fileMap.get(key).thread_islive = flag;
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this.getApplicationContext();
		//收集错误信息并保存在本地
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());

//		AppExceptionUncatch.getInstance().init(mContext);
		singleton = this;
		Utils.init(singleton);

		FileUtility fileUtility = new FileUtility();
		fileUtility.createDirInSDCard(DrugSystemConst.download_dir);
		fileMap = new HashMap<String, downinfo>();
		LoggerManager.clearCacheFile(SystemConst.logPath);
	}

	/**
	 * add Activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * get current Activity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 *
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 *
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);

			activity = null;
		}
	}

	/**
	 *
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 *
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 *
	 */
	public void AppExit() {
		try {
			finishAllActivity();
		} catch (Exception e) {
		}
	}

	/**
	 * 获取App安装包信息
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public List<Activity> getActivityList() {
		if (activityList != null) {
			return activityList;
		}
		return activityList;
	}

	public void delActivityList(Activity activity) {
		if (activityList != null) {
			activity.finish();
			activityList.remove(activity);
			activity = null;
		}
	}

	public void AddActivity(Activity activity) {

		if (activity != null) {
			activityList.add(activity);
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base); MultiDex.install(this);
	}
	public void exitActivity() {
		try {

			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}

			android.os.Process.killProcess(android.os.Process.myPid());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			System.gc();
			System.exit(0);
		}
	}


}
