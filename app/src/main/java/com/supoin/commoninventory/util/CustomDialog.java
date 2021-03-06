package com.supoin.commoninventory.util;

import com.supoin.commoninventory.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义对话框，类似AlertDialog
 * 
 * @author bojuzi.com
 */
public class CustomDialog extends Dialog {
	private Context mContext;
	private ImageView mIcon;
	private View mLayout;
	private TextView mTitle;
	private TextView mMessage;
	private Button mBtnLeft;
	private Button mBtnRight;
	private EditText mEditText;
	private View mView;

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
		initView();
	}

	public void initView() {
		this.mLayout = ((LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.dialog, null);
		this.mIcon = ((ImageView) this.mLayout.findViewById(R.id.dialog_icon));
		this.mTitle = ((TextView) this.mLayout.findViewById(R.id.dialog_title));
		this.mMessage = ((TextView) this.mLayout
				.findViewById(R.id.dialog_message));
		this.mBtnLeft = ((Button) this.mLayout
				.findViewById(R.id.dialog_btnleft));
		this.mBtnRight = ((Button) this.mLayout
				.findViewById(R.id.dialog_btnright));
		this.mView =  ((View) this.mLayout
				.findViewById(R.id.dialog_padding));
	}
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(this.mLayout);
//		int width = (int) (0.85D * ((WindowManager) this.mContext
//				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
//				.getWidth());
		WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
		int width =wm.getDefaultDisplay().getWidth();
		int screen_height = wm.getDefaultDisplay().getHeight();
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(getWindow().getAttributes());
		if(screen_height>1080)
			localLayoutParams.width = width-160;
		else
			localLayoutParams.width = width-100;
		localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(localLayoutParams);
	}

	public void setDialogIcon(int icon) {
		this.mIcon.setImageResource(icon);
	}
	public View getEditText(){
        return mEditText;
    }
	public void setMessage(int message) {
		((LinearLayout) this.mLayout.findViewById(R.id.dialog_line3))
		.setVisibility(View.VISIBLE);
		((LinearLayout) this.mLayout.findViewById(R.id.contentPanel))
				.setVisibility(View.VISIBLE);
		((LinearLayout) this.mLayout.findViewById(R.id.dialog_line))
				.setVisibility(View.VISIBLE);
		this.mLayout.findViewById(R.id.dialog_padding).setVisibility(View.GONE);
		this.mMessage.setText(this.mContext.getString(message));
	}
	public View getView(){
		return mView;
	}
	public void setView(View view) {
		((LinearLayout) this.mLayout.findViewById(R.id.dialog_line3))
		.setVisibility(View.VISIBLE);
		((LinearLayout) this.mLayout.findViewById(R.id.contentPanel))
				.setVisibility(View.VISIBLE);
		((LinearLayout) this.mLayout.findViewById(R.id.dialog_line))
				.setVisibility(View.VISIBLE);
		this.mLayout.findViewById(R.id.dialog_padding).setVisibility(View.VISIBLE);
//		this.mMessage.setText(this.mContext.getString(message));
//		this.setView(message)
		this.mView=view;
		
	}
	public void setMessage(CharSequence message) {
		((LinearLayout) this.mLayout.findViewById(R.id.contentPanel))
				.setVisibility(View.VISIBLE);
		((LinearLayout) this.mLayout.findViewById(R.id.dialog_line))
				.setVisibility(View.VISIBLE);
		this.mLayout.findViewById(R.id.dialog_padding).setVisibility(View.GONE);
		this.mMessage.setText(message);
	}

	public void setNegativeButton(int text,
			View.OnClickListener paramOnClickListener) {
		setNegativeButton(this.mContext.getString(text), paramOnClickListener);
	}
	
	public void setNegativeButton(CharSequence text,
			View.OnClickListener paramOnClickListener) {
		this.mBtnRight.setVisibility(View.VISIBLE);
		this.mBtnRight.setText(text);
		this.mBtnRight.setOnClickListener(paramOnClickListener);
	}

	public void setNegativeButton(View.OnClickListener paramOnClickListener) {
		this.mBtnRight.setVisibility(View.VISIBLE);
		this.mBtnRight.setOnClickListener(paramOnClickListener);
	}

	public void setPositiveButton(int text,
			View.OnClickListener paramOnClickListener) {
		setPositiveButton(this.mContext.getString(text), paramOnClickListener);
	}
	
	public void setPositiveButton(CharSequence text,
			View.OnClickListener paramOnClickListener) {
		this.mBtnLeft.setVisibility(View.VISIBLE);
		this.mBtnLeft.setFocusable(true);
		this.mBtnLeft.setFocusableInTouchMode(true);
		this.mBtnLeft.requestFocus();
		this.mBtnLeft.setText(text);
		this.mBtnLeft.setOnClickListener(paramOnClickListener);
	}

	public void setPositiveButton(View.OnClickListener paramOnClickListener) {
		this.mBtnLeft.setVisibility(View.VISIBLE);
		this.mBtnLeft.setOnClickListener(paramOnClickListener);
	}

	public void setPositiveButtonCenter() {
		new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT).addRule(
				RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
	}

	public void setTitle(int title) {
		this.mTitle.setText(this.mContext.getString(title));
	}

	public void setTitle(String title) {
		this.mTitle.setText(title);
	}
}
