package com.supoin.commoninventory.activity;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.LogUtil;
import com.supoin.commoninventory.util.StringUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GuanJiaPoSettingActivity extends Activity {

	private SharedPreferences sp;
	private EditText shopNo;
	private EditText shopName;
	private Button btn_save;
	private Button back;
	private String strShopNo, strShopName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(GuanJiaPoSettingActivity.this, "管家婆设置",
				false);
		setContentView(R.layout.activity_guanjiapo_setting);
		sp = this.getSharedPreferences("InvenConfig", MODE_PRIVATE);
		shopNo = (EditText) findViewById(R.id.shopNoText);
		shopName = (EditText) findViewById(R.id.shopNameText);
		btn_save = (Button) findViewById(R.id.btn_save);
		back = (Button) findViewById(R.id.back);
		shopNo.setText(sp.getString(ConfigEntity.GuanJiaPoshopNoKey, ""));
		shopName.setText(sp.getString(ConfigEntity.GuanJiaPoshopNameKey, ""));

		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				strShopNo = shopNo.getText().toString();
				strShopName = shopName.getText().toString();

				if (TextUtils.isEmpty(strShopName)
						&& TextUtils.isEmpty(strShopName)) {
					AlertUtil.showAlert(GuanJiaPoSettingActivity.this, "提示",
							"不能为空");
				} else {
					sp.edit()
							.putString(ConfigEntity.GuanJiaPoshopNoKey,
									strShopNo)
							.putString(ConfigEntity.GuanJiaPoshopNameKey,
									strShopName).commit();
					AlertUtil.showAlert(GuanJiaPoSettingActivity.this, "提示",
							"保存成功");
				}

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}

}
