package com.supoin.commoninventory.util;
import com.supoin.commoninventory.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomEditDialog extends Dialog {
	private ImageView mIcon;
	private View mView;
	private TextView mTitle;
	private TextView mMessage;
	private Button mBtnLeft;
	private Button mBtnRight;
	private EditText mEditText;
	private Context mContext;
    public CustomEditDialog(Context context) {
        super(context,R.style.cusdom_dialog_whitebg);
    	this.mContext = context;
        setCustomDialog();
    }
    protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(this.mView);
		int width = (int) (0.85D * ((WindowManager) this.mContext
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getWidth());
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.copyFrom(getWindow().getAttributes());
		localLayoutParams.width = width;
		localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(localLayoutParams);
	}
    private void setCustomDialog() {
       mView =LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext, null);
//        		LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext, null);
//        this.mIcon = ((ImageView) mView.findViewById(R.id.dialog_icon1));
		this.mTitle = ((TextView) mView.findViewById(R.id.dialog_title1));
		this.mBtnLeft = ((Button) mView
				.findViewById(R.id.dialog_btnleft1));
		this.mBtnRight = ((Button) mView
				.findViewById(R.id.dialog_btnright1));  
        this.mEditText =  ((EditText) mView
				.findViewById(R.id.dialog_edit));  
//        super.setContentView(mView);
    }
    public void setDialogIcon(int icon) {
		this.mIcon.setImageResource(icon);
	}
    public View getEditText(){
        return mEditText;
    }    
    public void setEditText(CharSequence message){
    		((LinearLayout) this.mView.findViewById(R.id.contentPanel1))
    				.setVisibility(View.VISIBLE);
    		this.mEditText.setText(message);
    }
    public void setPasswordEdit(Boolean isPassword){
    	if(isPassword){
//    		if (cb.isChecked())
//    			mEditText.setTransformationMethod(HideReturnsTransformationMethod
//						.getInstance());
//			else
				mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//    		mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    	}
    }
    public void setTitle(int title) {
		this.mTitle.setText(this.mContext.getString(title));
	}

	public void setTitle(String title) {
		this.mTitle.setText(title);
	}
    /**
     * È·¶¨¼ü¼àÌýÆ÷
     * @param listener
     */ 
    public void setOnPositiveListener(View.OnClickListener paramOnClickListener){ 
    	this.mBtnLeft.setVisibility(View.VISIBLE);
    	mBtnLeft.setOnClickListener(paramOnClickListener); 
    } 
    /**
     * È¡Ïû¼ü¼àÌýÆ÷
     * @param listener
     */ 
    public void setOnNegativeListener(View.OnClickListener paramOnClickListener){ 
    	this.mBtnRight.setVisibility(View.VISIBLE);
    	mBtnRight.setOnClickListener(paramOnClickListener); 
    }
}