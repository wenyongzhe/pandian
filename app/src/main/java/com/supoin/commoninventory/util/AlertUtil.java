package com.supoin.commoninventory.util;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.publicontent.myApplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AlertUtil {
	private static CustomDialog mDialog;
	private static CustomDialog2 mDialog2;
	private static Context mContext;
	private static ProgressDialog mProgressDialog;
	private static TextView tv ;
	
	public TextView getTv() {
		return tv;
	}

	
	public static void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		mDialog = null;
	}

	public static void dismissDialog2() {
		if (mDialog2 != null && mDialog2.isShowing()) {
			mDialog2.dismiss();
		}
		mDialog2 = null;
	}

	public static void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		mProgressDialog = null;
	}

	public static void dismissDialog1() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	public static void dismissDialog(DialogInterface.OnDismissListener listener) {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.setOnDismissListener(listener);
			mDialog.dismiss();
		}
		mDialog = null;
	}

	private static void dimBehind(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 1.0f;
		lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
	}

	public static void showAlert(Context paramContext, int title, int message) {
		mContext = paramContext;
		if (mDialog != null) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissDialog();
			}
		});
		mDialog.show();
		adjustDialogPosition(mDialog);
	}

	public static void showAlert(Context paramContext, int title, int message,
			int positiveText, View.OnClickListener listener) {
		showAlert(paramContext, getString(title), getString(message),
				getString(positiveText), listener);
	}

	public static String getString(int resId) {
		mContext = myApplication.mContext;
		return mContext.getResources().getString(resId);
	}

	public static void showAlert(Context paramContext, CharSequence title,
			CharSequence message, CharSequence positiveText,
			View.OnClickListener listener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, listener);
		// mDialog.setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss(DialogInterface dialog) {
		// mDialog = null;
		// }
		// });
		mDialog.setCancelable(false);
		mDialog.show();
		adjustDialogPosition(mDialog);
	}
	public static void showAlert(Context paramContext, int title,
			CharSequence message, int positiveText,
			View.OnClickListener listener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, listener);
		// mDialog.setOnDismissListener(new OnDismissListener() {
		//
		// @Override
		// public void onDismiss(DialogInterface dialog) {
		// mDialog = null;
		// }
		// });
		mDialog.setCancelable(false);
		mDialog.show();
		adjustDialogPosition(mDialog);
	}

	public static void showAlert(Context paramContext, int title, int message,
			int positiveText, View.OnClickListener positiveBtnListener,
			int negativeText, View.OnClickListener negativeBtnListener) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(positiveText, positiveBtnListener);
		mDialog.setNegativeButton(negativeText, negativeBtnListener);
		mDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				mDialog = null;
			}
		});
		mDialog.setCancelable(false);
		mDialog.show();
		adjustDialogPosition(mDialog);
	}

	public static void showAlert2(Context paramContext, int title, int message,
			int positiveText, View.OnClickListener positiveBtnListener,
			int negativeText, View.OnClickListener negativeBtnListener) {
		mContext = paramContext;
		if (mDialog2 != null && mDialog2.isShowing()) {
			return;
		}
		mDialog2 = new CustomDialog2(paramContext,
				R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog2);
		mDialog2.setTitle(title);
		mDialog2.setMessage(message);
		mDialog2.setPositiveButton(positiveText, positiveBtnListener);
		mDialog2.setNegativeButton(negativeText, negativeBtnListener);
		mDialog2.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				mDialog2 = null;
			}
		});
		mDialog2.setCancelable(false);
		mDialog2.show();
		adjustDialogPosition(mDialog2);
	}

	/**
	 * 这个把message用String类型了，方便格式化
	 * 
	 * @param paramContext
	 * @param title
	 * @param message
	 * @param positiveText
	 * @param positiveBtnListener
	 * @param negativeText
	 * @param negativeBtnListener
	 */
	public static void showAlert(Context paramContext, int title,
			CharSequence message, int positiveText,
			View.OnClickListener positiveBtnListener, int negativeText,
			View.OnClickListener negativeBtnListener) {
		showAlert(paramContext, title, R.string.dialog_title, positiveText,
				positiveBtnListener, negativeText, negativeBtnListener);
		mDialog.setMessage(message);
		adjustDialogPosition(mDialog);
	}
	

	public static void showAlert2(Context paramContext, int title,
			CharSequence message, int positiveText,
			View.OnClickListener positiveBtnListener, int negativeText,
			View.OnClickListener negativeBtnListener) {
		showAlert2(paramContext, title, R.string.dialog_title, positiveText,
				positiveBtnListener, negativeText, negativeBtnListener);
		mDialog2.setMessage(message);
		adjustDialogPosition(mDialog2);
	}

	public static void showAlert(Context paramContext, String title,
			String message) {
		mContext = paramContext;
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog_whitebg);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissDialog();
			}
		});
		adjustDialogPosition(mDialog);
		mDialog.show();
	}

	/**
	 * 
	 * @param paramContext
	 * @param title
	 * @param message
	 * @param paramOnClickListener
	 */
	public static void showDialog(Context paramContext, int title, int message,
			View.OnClickListener paramOnClickListener) {
		mContext = paramContext;
		mDialog = new CustomDialog(paramContext, R.style.cusdom_dialog);
		dimBehind(mDialog);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setPositiveButton(paramOnClickListener);
		mDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				mDialog = null;
			}
		});
		adjustDialogPosition(mDialog);
		mDialog.show();
	}

	public static ProgressDialog showProgressDialog(Context context,
			String message) {
		mContext = context;
		mProgressDialog = ProgressDialog.show(context, "请稍等", "", true);
		mProgressDialog.setContentView(R.layout.progressbar_content_center);
		mMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				((AnimationDrawable) ((ImageView) mProgressDialog
						.findViewById(R.id.progressbar_center_loading))
						.getDrawable()).start();
			}
		}, 50L);
		((TextView) mProgressDialog.findViewById(R.id.progressbar_textview))
				.setText(message);
		((Button) mProgressDialog.findViewById(R.id.progressbar_button1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mProgressDialog != null
								&& mProgressDialog.isShowing()) {
							mProgressDialog.dismiss();
						}
						mProgressDialog = null;
					}
				});
		mProgressDialog.setCancelable(false);
		adjustDialogPosition(mProgressDialog);
		return mProgressDialog;
	}

	public static ProgressDialog showProgressDialog(Context context,
			String message, View.OnClickListener paramOnClickListener) {
		mContext = context;
		mProgressDialog = ProgressDialog.show(context, "请稍等", "", true);
		mProgressDialog
				.setContentView(R.layout.progressbar_content_button_center);
		mMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				((AnimationDrawable) ((ImageView) mProgressDialog
						.findViewById(R.id.progressbar_center_button_loading))
						.getDrawable()).start();
			}
		}, 50L);
		((TextView) mProgressDialog
				.findViewById(R.id.progressbar_button_textview))
				.setText(message);
		((Button) mProgressDialog.findViewById(R.id.progressbar_button))
				.setOnClickListener(paramOnClickListener);
		mProgressDialog.setCancelable(false);
		adjustDialogPosition(mProgressDialog);
		return mProgressDialog;
	}

	// 无按钮dialog
	public static ProgressDialog showNoButtonProgressDialog(Context context,
			String message) {
		
		
		mContext = context;
		mProgressDialog = ProgressDialog.show(context, "请稍等", "", true);
		mProgressDialog.setContentView(R.layout.progressbar_nobutton);
		mMainHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				((AnimationDrawable) ((ImageView) mProgressDialog
						.findViewById(R.id.progressbar_center_button_loading1))
						.getDrawable()).start();
			}
		}, 50L);
	    tv =((TextView) mProgressDialog.findViewById(R.id.progressbar_button_textview1));
				tv.setText(message);
		mProgressDialog.setCancelable(false);
		adjustDialogPosition(mProgressDialog);
		return mProgressDialog;
	}
	// 有圆形进度条的
		public static ProgressDialog showNoButtonProDialog(Context context,
				String message) {
			mContext = context;
			mProgressDialog = ProgressDialog.show(context, "请稍等", "", true);
			mProgressDialog.setContentView(R.layout.progressbar_nobutton_code);
		   
			mMainHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
				
					((AnimationDrawable) ((ImageView) mProgressDialog
							.findViewById(R.id.progressbar_center_button_loading1))
							.getDrawable()).start();
				}
			}, 50L);
			((TextView) mProgressDialog
					.findViewById(R.id.progressbar_button_textview1))
					.setText(message);
			mProgressDialog.setCancelable(false);
			adjustDialogPosition(mProgressDialog);
			return mProgressDialog;
		}
	public static void setMessage(String message) {
		if (mProgressDialog != null)
			((TextView) mProgressDialog
					.findViewById(R.id.progressbar_button_textview))
					.setText(message);
	}

	private static void adjustDialogPosition(Dialog dialog) {
		Window dialogWindow = dialog.getWindow();
		dialogWindow
				.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP/* CENTER_VERTICAL */);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		Point size = Utility.getDisplaySize(mContext);
		lp.y = (int) (size.y * 0.26);
		dialogWindow.setAttributes(lp);
	}

	private static final int SHOW_TOAST = 0;

	private static Handler mMainHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			int what = msg.what;
			Object obj = msg.obj;

			if (SHOW_TOAST == what) {
				Toast toast = new Toast(mContext);
				View localView = ((LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
						.inflate(R.layout.toast, null);
				((TextView) localView.findViewById(R.id.toast_message))
						.setText((CharSequence) obj);
				toast.setView(localView);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
		}
	};

	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(int message, Context paramContext) {
		mContext = paramContext;
		mMainHandler.obtainMessage(SHOW_TOAST, getString(message))
				.sendToTarget();
	}

	/**
	 * 在子线程也可以调用
	 */
	public static void showToast(CharSequence message, Context paramContext) {
		mContext = paramContext;
		mMainHandler.obtainMessage(SHOW_TOAST, message).sendToTarget();
	}

}
