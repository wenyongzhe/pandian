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
	public int pecent;//���ذٷֱ�
	public String time;
	public String packagename;
	public int curVersion;
	public boolean downflag;//�����Ƿ����
	public boolean isPause;//�Ƿ���ͣ
	public boolean isCancel;//�Ƿ�ȡ��
	public boolean thread_islive;//�߳��Ƿ����
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
		isPause = false;//Ĭ������ͣ״̬
		isCancel = false;
		filename = null;
		pecent = 0;
		thread_islive = false;//�����߳�Ĭ��������
	}

}
