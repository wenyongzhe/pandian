package com.supoin.commoninventory.publicontent;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.supoin.commoninventory.R;

public class CustomTitleBar {

	private static Activity mActivity;

	/**
	 * @see [自定义标题栏]
	 * @param activity
	 * @param title
	 */
	public static void getTitleBar(Activity activity,String title,Boolean isExit) {
		mActivity = activity;
		activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		activity.setContentView(R.layout.custom_title);
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		TextView textView = (TextView) activity.findViewById(R.id.head_left_text);
		textView.setText(title);
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		Button titleBackBtn = (Button) activity.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		if(isExit){
//			activity.findViewById(R.id.head_TitleRightBtn).setVisibility(View.VISIBLE);
		Button titleExitBtn = (Button) activity.findViewById(R.id.head_TitleRightBtn);
		titleExitBtn.setVisibility(View.VISIBLE);
		titleExitBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		}
	}
	/**
	 * @see [自定义标题栏]
	 * @param activity
	 * @param title
	 */
	public static void getTitleBar(Activity activity,int title,Boolean isExit) {
		mActivity = activity;
		activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		activity.setContentView(R.layout.custom_title);
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		TextView textView = (TextView) activity.findViewById(R.id.head_left_text);
		textView.setText(title);
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		Button titleBackBtn = (Button) activity.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		if(isExit){
//			activity.findViewById(R.id.head_TitleRightBtn).setVisibility(View.VISIBLE);
			Button titleExitBtn = (Button) activity.findViewById(R.id.head_TitleRightBtn);
			titleExitBtn.setVisibility(View.VISIBLE);
			titleExitBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_BACK);
					mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
				}
			});
		}
	}
	/**
	 * @see [自定义标题栏]
	 * @param activity
	 * @param title
	 */
	public static void getTitleBar(Activity activity,String title1,int title,Boolean isExit) {
		mActivity = activity;
		activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		activity.setContentView(R.layout.custom_title);
		activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		TextView textView = (TextView) activity.findViewById(R.id.head_left_text);
		textView.setText(title1 + activity.getResources().getString(title));
		textView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		Button titleBackBtn = (Button) activity.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_BACK);
				mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
			}
		});
		if(isExit){
//			activity.findViewById(R.id.head_TitleRightBtn).setVisibility(View.VISIBLE);
			Button titleExitBtn = (Button) activity.findViewById(R.id.head_TitleRightBtn);
			titleExitBtn.setVisibility(View.VISIBLE);
			titleExitBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
							KeyEvent.KEYCODE_BACK);
					mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);
				}
			});
		}
	}
	/**
	 * @see 重新获取Activity
	 * @param activity
	 */
	public static void setActivity(Activity activity){
		mActivity=activity;
	}
}
