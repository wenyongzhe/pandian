package com.supoin.commoninventory.util;

import android.R.drawable;
import android.R.integer;
import android.R.string;
import android.graphics.drawable.Drawable;

public class downinfo {
	public String imageName;
	public String labelName;
	public String filename;
	public Drawable    imageId;
	public long   filesize;
	public long   downsize;
	public int pecent;//下载百分比
	public String time;
	public String packagename;
	public int curVersion;
	public boolean downflag;//下载是否完成
	public boolean isPause;//是否暂停
	public boolean isCancel;//是否取消
	public boolean thread_islive;//线程是否活着
	public int startpos;
	public int endpos;
	public String urllink;
	public int download_id;
	public downinfo()
	{
		imageName = null;
		imageId = null;
		filesize = 0;
		downsize = -1;
		downflag = false;
		time = null;
		packagename = null;
		isPause = false;//默认是暂停状态
		isCancel = false;
		filename = null;
		pecent = 0;
		thread_islive = false;//下载线程默认是死的
	}

}
