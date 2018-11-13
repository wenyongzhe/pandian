package com.supoin.commoninventory.util;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * ��Ļ������--��ȡ�ֻ���Ļ��Ϣ
 * 
 * 
 */
public class ScreenUtil {

	/**
	 * ��ȡ��Ļ�Ŀ��
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * ��ȡ��Ļ�ĸ߶�
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * ��ȡ��Ļ�пؼ�����λ�õĸ߶�--���ؼ�������Y��
	 * 
	 * @return
	 */
	public static int getScreenViewTopHeight(View view) {
		return view.getTop();
	}

	/**
	 * ��ȡ��Ļ�пؼ��ײ�λ�õĸ߶�--���ؼ��ײ���Y��
	 * 
	 * @return
	 */
	public static int getScreenViewBottomHeight(View view) {
		return view.getBottom();
	}

	/**
	 * ��ȡ��Ļ�пؼ�����λ��--���ؼ�����X��
	 * 
	 * @return
	 */
	public static int getScreenViewLeftHeight(View view) {
		return view.getLeft();
	}

	/**
	 * ��ȡ��Ļ�пؼ��Ҳ��λ��--���ؼ��Ҳ��X��
	 * 
	 * @return
	 */
	public static int getScreenViewRightHeight(View view) {
		return view.getRight();
	}

}