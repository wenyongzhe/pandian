package com.supoin.commoninventory.constvalue;

import com.supoin.commoninventory.util.FileUtility;

/**
 * Created by Administrator on 2017/2/13.
 * 
 */

public class SystemConst {
	// 文件路径
	public static final String appName = "com.supoin.commoninventory";
	
	public static final String download_dir = "Supoin";

	public final static String updateFile = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/Update/";

	public final static String baseDataPath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/basedata/";

	public final static String crashPath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/Crash/";
	
	public final static String downloadPath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/download/";
	
	public final static String databasePath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/database/";
	
	public final static String logPath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/log/";
	
	public final static String importPath = FileUtility.getSdCardPath() + "/"
			+ download_dir + "/Import/";
	
	

	
}
