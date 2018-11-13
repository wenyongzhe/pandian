package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.fragment.BarCodeSettingFragment;
import com.supoin.commoninventory.fragment.LengthSettingFragment;
import com.supoin.commoninventory.fragment.ScanSettingFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class SettingParametersActivity extends Activity implements
		OnClickListener {
	private Button btn_back;
	private TextView tv_scan_setting, tv_length_setting, tv_barcode_setting;
	private View view_scan_setting, view_length_setting, view_barcode_setting;
	private LinearLayout llBarcodeSetting;
	private RelativeLayout show_setting_view;
	private ScanSettingFragment mScanSettingFragment;
	private LengthSettingFragment mLengthSettingFragment;
	private BarCodeSettingFragment mBarCodeSettingFragment;
	private String iRightLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(SettingParametersActivity.this, R.string.parameter_settings,false);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		iRightLevel = bundle.getString("iRightLevel");
		setContentView(R.layout.activity_parameters_setting);
		tv_scan_setting = (TextView) findViewById(R.id.tv_scan_setting);
		tv_length_setting = (TextView) findViewById(R.id.tv_length_setting);
		//tv_barcode_setting = (TextView) findViewById(R.id.tv_barcode_setting);
		view_scan_setting = findViewById(R.id.view_scan_setting);
		view_length_setting = findViewById(R.id.view_length_setting);
		//view_barcode_setting = findViewById(R.id.view_barcode_setting);
		show_setting_view = (RelativeLayout) findViewById(R.id.show_setting_view);
		//llBarcodeSetting = (LinearLayout) findViewById(R.id.ll_barcode_setting);

		// 非超级管理员
		if (iRightLevel.equals("2")) {
			//llBarcodeSetting.setVisibility(View.VISIBLE);
			//view_barcode_setting.setVisibility(View.VISIBLE);
		} else {
			//llBarcodeSetting.setVisibility(View.GONE);
			//view_barcode_setting.setVisibility(View.GONE);
		}

		tv_scan_setting.setOnClickListener(this);
		tv_length_setting.setOnClickListener(this);
		//tv_barcode_setting.setOnClickListener(this);

		mScanSettingFragment = new ScanSettingFragment(getIntent().getStringExtra("iRightLevel"));
		addFragment(mScanSettingFragment);
		view_scan_setting.setBackgroundColor(getResources().getColor(R.color.title_yellow));
		tv_scan_setting.setTextColor(getResources().getColor(R.color.title_yellow));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_scan_setting:
			if (mScanSettingFragment == null) {
				mScanSettingFragment = new ScanSettingFragment(getIntent().getStringExtra("iRightLevel"));
				addFragment(mScanSettingFragment);
				showFragment(mScanSettingFragment);
			} else {
				showFragment(mScanSettingFragment);
			}
			view_scan_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_length_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			tv_scan_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_length_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			/*tv_barcode_setting.setTextColor(getResources().getColor(
					R.color.gray_text));*/
			/*view_barcode_setting.setBackgroundColor(getResources().getColor(
					R.color.view));*/
			break;

		case R.id.tv_length_setting:

			if (mLengthSettingFragment == null) {
				mLengthSettingFragment = new LengthSettingFragment();
				addFragment(mLengthSettingFragment);
				showFragment(mLengthSettingFragment);
			} else {
				showFragment(mLengthSettingFragment);
			}
			view_length_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_scan_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			tv_length_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_scan_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			/*tv_barcode_setting.setTextColor(getResources().getColor(
					R.color.gray_text));*/
			/*view_barcode_setting.setBackgroundColor(getResources().getColor(
					R.color.view));*/
			break;
		/*case R.id.tv_barcode_setting:

			if (mBarCodeSettingFragment == null) {
				mBarCodeSettingFragment = new BarCodeSettingFragment();
				addFragment(mBarCodeSettingFragment);
				showFragment(mBarCodeSettingFragment);
			} else {
				showFragment(mBarCodeSettingFragment);
			}
			view_length_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_scan_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			tv_length_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_scan_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_barcode_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			view_barcode_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			break;*/
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
		if (mScanSettingFragment != null) {
			ft.hide(mScanSettingFragment);
		}
		if (mLengthSettingFragment != null) {
			ft.hide(mLengthSettingFragment);
		}
		if (mBarCodeSettingFragment != null) {
			ft.hide(mBarCodeSettingFragment);
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
