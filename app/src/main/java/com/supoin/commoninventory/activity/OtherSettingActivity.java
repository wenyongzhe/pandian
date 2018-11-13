package com.supoin.commoninventory.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.fragment.ImportExportSettingFragment;
import com.supoin.commoninventory.fragment.OtherSettingFragment;
import com.supoin.commoninventory.fragment.VioceSettingFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class OtherSettingActivity extends Activity implements OnClickListener {

	private Button button1, button2;
	private TextView tv_out_in_setting, tv_others_setting, tv_vioce_setting;
	private View view1, view2, view3;
	private ImportExportSettingFragment mOutInSettingFragment;
	private OtherSettingFragment mOtherSettingFragment;
	private VioceSettingFragment mVioceSettingFragment;
	private String iRightLevel;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(OtherSettingActivity.this, R.string.other_settings, false);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		iRightLevel = bundle.getString("iRightLevel");
		setContentView(R.layout.activity_others_setting);
		tv_out_in_setting = (TextView) findViewById(R.id.tv_out_in_setting);
		tv_others_setting = (TextView) findViewById(R.id.tv_other_setting);
		tv_vioce_setting = (TextView) findViewById(R.id.tv_vioce_setting);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);

		tv_others_setting.setOnClickListener(this);
		tv_out_in_setting.setOnClickListener(this);
		tv_vioce_setting.setOnClickListener(this);
		mOutInSettingFragment = new ImportExportSettingFragment();
		addFragment(mOutInSettingFragment);
		view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
		tv_out_in_setting.setTextColor(getResources().getColor(R.color.title_yellow));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_out_in_setting:
			if (mOutInSettingFragment == null) {
				mOutInSettingFragment = new ImportExportSettingFragment();
				addFragment(mOutInSettingFragment);
				showFragment(mOutInSettingFragment);
			} else {
				showFragment(mOutInSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_out_in_setting.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_others_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_vioce_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;

		case R.id.tv_other_setting:

			if (mOtherSettingFragment == null) {
				mOtherSettingFragment = new OtherSettingFragment();
				Bundle bundle1 = new Bundle();  
				bundle1.putString("iRightLevel",iRightLevel);  
				mOtherSettingFragment.setArguments(bundle1);
				addFragment(mOtherSettingFragment);
				showFragment(mOtherSettingFragment);
			} else {
				showFragment(mOtherSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_others_setting.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_out_in_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_vioce_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;
		case R.id.tv_vioce_setting:

			if (mVioceSettingFragment == null) {
				mVioceSettingFragment = new VioceSettingFragment();
				addFragment(mVioceSettingFragment);
				showFragment(mVioceSettingFragment);
			} else {
				showFragment(mVioceSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_vioce_setting
					.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_out_in_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_others_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			break;

		}

	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.show_setting_view, fragment);
		ft.commit();
	}

	// ** 删除Fragment **//
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	// ** 显示Fragment **//
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (mOutInSettingFragment != null) {
			ft.hide(mOutInSettingFragment);
		}
		if (mOtherSettingFragment != null) {
			ft.hide(mOtherSettingFragment);
		}
		if (mVioceSettingFragment != null) {
			ft.hide(mVioceSettingFragment);
		}

		ft.show(fragment);
		ft.commit();

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
