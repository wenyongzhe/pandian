package com.supoin.commoninventory.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zhangfan
 * @date 2016-03-31
 */
public class ToastUtils {

	private Toast mToast;
	private Context context;

	private static Toast toast = null;
	public static int LENGTH_LONG = Toast.LENGTH_LONG;
	public static int LENGTH_SHORT = Toast.LENGTH_SHORT;

	public ToastUtils(Context context) {
		this.context = context.getApplicationContext();
	}

	public Toast getSingletonToast(int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		} else {
			mToast.setText(resId);
		}
		return mToast;
	}

	public Toast getSingletonToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		return mToast;
	}

	public static void show(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getText(resId), duration);
	}

	public static void show(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, CharSequence text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	public static void show(Context context, int resId, Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				Toast.LENGTH_SHORT);
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration,
			Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				duration);
	}

	public static void show(Context context, String format, int duration,
			Object... args) {
		show(context, String.format(format, args), duration);
	}

	public Toast getToast(int resId) {
		return Toast.makeText(context, resId, Toast.LENGTH_SHORT);
	}

	public Toast getToast(String text) {
		return Toast.makeText(context, text, Toast.LENGTH_SHORT);
	}

	public Toast getLongToast(int resId) {
		return Toast.makeText(context, resId, Toast.LENGTH_LONG);
	}

	public Toast getLongToast(String text) {
		return Toast.makeText(context, text, Toast.LENGTH_LONG);
	}

	public void showSingletonToast(int resId) {
		getSingletonToast(resId).show();
	}

	public void showSingletonToast(String text) {
		getSingletonToast(text).show();
	}

	public void showToast(int resId) {
		getToast(resId).show();
	}

	public void showToast(String text) {
		getToast(text).show();
	}

	public void showLongToast(int resId) {
		getLongToast(resId).show();
	}

	public void showLongToast(String text) {
		getLongToast(text).show();
	}

	/**
	 * 普通文本消息提示,显示在中央
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void textToastCenter(Context context, CharSequence text,
			int duration) {
		// 创建一个Toast提示消息
		toast = Toast.makeText(context, text, duration);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 显示消息
		toast.show();
	}

	/**
	 * 普通文本消息提示
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void textToastBottom(Context context, CharSequence text,
			int duration) {
		// 创建一个Toast提示消息
		toast = Toast.makeText(context, text, duration);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		// 显示消息
		toast.show();
	}

	/**
	 * 带图片消息提示
	 * 
	 * @param context
	 * @param ImageResourceId
	 * @param text
	 * @param duration
	 */
	public static void imageToast(Context context, int ImageResourceId,
			CharSequence text, int duration) {
		// 创建一个Toast提示消息
		toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 获取Toast提示消息里原有的View
		View toastView = toast.getView();
		// 创建一个ImageView
		ImageView img = new ImageView(context);
		img.setImageResource(ImageResourceId);
		// 创建一个LineLayout容器
		LinearLayout ll = new LinearLayout(context);
		// 向LinearLayout中添加ImageView和Toast原有的View
		ll.addView(img);
		ll.addView(toastView);
		// 将LineLayout容器设置为toast的View
		toast.setView(ll);
		// 显示消息
		toast.show();
	}

	/**
	 * 
	 * @param con
	 *            :Context
	 * @param viewId
	 *            :your custom layoutview
	 * @param ivId
	 *            :the imageview in your custom layoutview
	 * @param tvId
	 *            :the textview in your custom layoutview
	 * @param str
	 *            :text the textview will show
	 * @param drawable
	 *            :your R.drawable.xx,
	 * @param time
	 *            :Toast.LENGTH_SHORT/Toast.LENGTH_LONG
	 * @param gravity
	 *            :Gravity.Bottom/... etc.
	 * @param x
	 *            :distance on x
	 * @param y
	 *            :distance on y
	 */
	public static void CustomToast(Context context, int viewId, int ivId,
			int tvId, String str, int drawable, int time, int gravity, int x,
			int y) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(viewId, null);
		TextView text = (TextView) v.findViewById(tvId);
		text.setText(str);
		ImageView imageView = (ImageView) v.findViewById(ivId);
		imageView.setBackgroundResource(drawable);

		Toast toast = new Toast(context);
		toast.setDuration(time);
		toast.setGravity(gravity, x, y);
		toast.setView(v);
		toast.show();

	}

}
