package com.supoin.commoninventory.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class DialogUtils {

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) builder.setMessage(msg);
        if (title != null) builder.setTitle(title);
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) builder.setMessage(Html.fromHtml(msg));
        if (title != null) builder.setTitle(title);
        return builder;
    }


    public static AlertDialog.Builder dialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) builder.setView(view);
        if (title > 0) builder.setTitle(title);
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int titleResId, int msgResId) {
        String title = titleResId > 0 ? context.getResources().getString(titleResId) : null;
        String msg = msgResId > 0 ? context.getResources().getString(msgResId) : null;
        return dialogBuilder(context, title, msg);
    }

    public static Dialog showTips(Context context, String title, String des) {
        AlertDialog.Builder builder = DialogUtils.dialogBuilder(context, title, des);
        builder.setCancelable(true);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog showTips(Context context, int title, int des) {
        return showTips(context, context.getString(title), context.getString(des));
    }
    
    /**
	 * 创建普通单按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnName,
			android.content.DialogInterface.OnClickListener listener) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ctx);
		// 设置对话框的图标
		builder.setIcon(iconId);
		// 设置对话框的标题
		builder.setTitle(title);
		// 设置对话框的显示内容
		builder.setMessage(message);
		// 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
		builder.setPositiveButton(btnName, listener);
		// 创建一个普通对话框
		dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	/**
	 * 创建普通双按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon[不想显示就写0] 必填
	 * @param title
	 *            标题 必填
	 * @param message
	 *            显示内容 必填
	 * @param btnPositiveName
	 *            第一个按钮名称 必填
	 * @param listener_Positive
	 *            第一个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @param btnNegativeName
	 *            第二个按钮名称 必填
	 * @param listener_Negative
	 *            第二个监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return 对话框实例
	 */
	public static Dialog createDialog(Context ctx, int iconId, String title,
			String message, String btnPositiveName,
			android.content.DialogInterface.OnClickListener listener_Positive,
			String btnNegativeName,
			android.content.DialogInterface.OnClickListener listener_Negative) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ctx);
		// 设置对话框的图标
		builder.setIcon(iconId);
		// 设置对话框的标题
		builder.setTitle(title);
		// 设置对话框的显示内容
		builder.setMessage(message);
		// 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
		builder.setPositiveButton(btnPositiveName, listener_Positive);
		// 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
		builder.setNegativeButton(btnNegativeName, listener_Negative);
		// 创建一个普通对话框
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 创建列表对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param itemsId
	 *            字符串数组资源id 必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createListDialog(Context ctx, int iconId,
			String title, int itemsId,
			android.content.DialogInterface.OnClickListener listener) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		// 设置对话框的图标
		builder.setIcon(iconId);
		// 设置对话框的标题
		builder.setTitle(title);
		// 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
		builder.setItems(itemsId, listener);
        builder.setCancelable(false);
		// 创建一个列表对话框
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 创建单选按钮对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param itemsId
	 *            字符串数组资源id 必填
	 * @param listener
	 *            单选按钮项监听器，需实现android.content.DialogInterface.OnClickListener接口
	 *            必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener2
	 *            按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createRadioDialog(Context ctx, int iconId,
			String title, int itemsId,
			android.content.DialogInterface.OnClickListener listener,
			String btnName,
			android.content.DialogInterface.OnClickListener listener2) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ctx);
		// 设置对话框的图标
		builder.setIcon(iconId);
		// 设置对话框的标题
		builder.setTitle(title);
		// 0: 默认第一个单选按钮被选中
		builder.setSingleChoiceItems(itemsId, 0, listener);
		// 添加一个按钮
		builder.setPositiveButton(btnName, listener2);
		// 创建一个单选按钮对话框
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 创建复选对话框
	 * 
	 * @param ctx
	 *            上下文 必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 必填
	 * @param title
	 *            标题 必填
	 * @param itemsId
	 *            字符串数组资源id 必填
	 * @param flags
	 *            初始复选情况 必填
	 * @param listener
	 *            单选按钮项监听器，需实现android.content.DialogInterface.
	 *            OnMultiChoiceClickListener接口 必填
	 * @param btnName
	 *            按钮名称 必填
	 * @param listener2
	 *            按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
	 * @return
	 */
	public static Dialog createCheckBoxDialog(
			Context ctx,
			int iconId,
			String title,
			int itemsId,
			boolean[] flags,
			android.content.DialogInterface.OnMultiChoiceClickListener listener,
			String btnName,
			android.content.DialogInterface.OnClickListener listener2) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ctx);
		// 设置对话框的图标
		builder.setIcon(iconId);
		// 设置对话框的标题
		builder.setTitle(title);
		builder.setMultiChoiceItems(itemsId, flags, listener);
		// 添加一个按钮
		builder.setPositiveButton(btnName, listener2);
		// 创建一个复选对话框
		dialog = builder.create();
		return dialog;
	}

	/**
	 * 日期对话框
	 * 
	 * @param context
	 *            上下文
	 * @param v
	 *            带.setText的控件
	 * @return 对话框实例
	 */
	public static Dialog createDateDialog(Context context, final View v) {
		Dialog dialog = null;
		Calendar calender = Calendar.getInstance();
		dialog = new DatePickerDialog(context,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						if (v instanceof TextView) {

							((TextView) v).setText(year + "年"
									+ (monthOfYear + 1) + "月" + dayOfMonth
									+ "日");
						}
						if (v instanceof EditText) {
							((EditText) v).setText(year + "年"
									+ (monthOfYear + 1) + "月" + dayOfMonth
									+ "日");
						}

					}
				}, calender.get(calender.YEAR), calender.get(calender.MONTH),
				calender.get(calender.DAY_OF_MONTH));

		return dialog;
	}

	/**
	 * 加载对话框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            内容
	 * @return
	 */
	public static ProgressDialog createLoadDialog(Context context, String msg) {
		ProgressDialog dialog = new ProgressDialog(context);
		// 设置风格为圆形进度条
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置内容
		dialog.setMessage(msg);
		// 设置进度条是否为不明确
		dialog.setIndeterminate(false);
		// 设置空白出不关闭
		dialog.setCanceledOnTouchOutside(false);
		// 设置进度条是否可以按退回键取消
		dialog.setCancelable(true);
		return dialog;
	}
}
