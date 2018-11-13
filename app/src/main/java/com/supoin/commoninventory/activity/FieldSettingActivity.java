package com.supoin.commoninventory.activity;

import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;

public class FieldSettingActivity extends Activity {
	private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
	private Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(FieldSettingActivity.this, "字段列表设置", false);

		setContentView(R.layout.activity_fieldset);

		checkBox1 = (CheckBox) this.findViewById(R.id.bianma);
		checkBox2 = (CheckBox) this.findViewById(R.id.tiaoma);
		checkBox3 = (CheckBox) this.findViewById(R.id.huowei);
		checkBox4 = (CheckBox) this.findViewById(R.id.shulian);
		checkBox5 = (CheckBox) this.findViewById(R.id.shijian);
		save = (Button)this.findViewById(R.id.btn_save);
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			break;
		case KeyEvent.KEYCODE_ENTER:
			break;
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
	}
}