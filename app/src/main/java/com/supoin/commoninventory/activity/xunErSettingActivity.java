package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;

public class xunErSettingActivity extends Activity implements OnClickListener {

	private SharedPreferences sp;
	private Button btn_save;
	private TextView tv_num1, tv_num2, tv_num3, tv_num4;
	private String strNum1, strNum2, strNum3, strNum4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(xunErSettingActivity.this, "迅尔盘点设置", false);
		setContentView(R.layout.activity_xuner_setting);
		sp = xunErSettingActivity.this.getSharedPreferences("InvenConfig",
				MODE_PRIVATE);
		btn_save = (Button) findViewById(R.id.btn_save);
		tv_num1 = (TextView) findViewById(R.id.num1);
		tv_num3 = (TextView) findViewById(R.id.num3);
		tv_num4 = (TextView) findViewById(R.id.num4);
		tv_num2 = (TextView) findViewById(R.id.num2);
		btn_save.setOnClickListener(this);
		tv_num2.setOnClickListener(this);
		tv_num1.setOnClickListener(this);
		tv_num3.setOnClickListener(this);
		tv_num4.setOnClickListener(this);
		strNum1 = sp.getString(ConfigEntity.XunerSetting1Key, "");
		strNum2 = sp.getString(ConfigEntity.XunerSetting2Key, "");
		strNum3 = sp.getString(ConfigEntity.XunerSetting3Key, "");
		strNum4 = sp.getString(ConfigEntity.XunerSetting4Key, "");
		tv_num1.setText(strNum1);
		tv_num2.setText(strNum2);
		tv_num3.setText(strNum3);
		tv_num4.setText(strNum4);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}
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

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_save:
			strNum1 = tv_num1.getText().toString();
			strNum2 = tv_num2.getText().toString();
			strNum3 = tv_num3.getText().toString();
			strNum4 = tv_num4.getText().toString();
			sp.edit()
					.putString(ConfigEntity.XunerSetting1Key,strNum1)
					.putString(ConfigEntity.XunerSetting2Key,strNum2)
					.putString(ConfigEntity.XunerSetting3Key,strNum3)
					.putString(ConfigEntity.XunerSetting4Key,strNum4)
					.commit();
			AlertUtil.showAlert(xunErSettingActivity.this, "提示", "保存成功");
			break;
		case R.id.back:
			finish();
			break;

		}
	}
}
