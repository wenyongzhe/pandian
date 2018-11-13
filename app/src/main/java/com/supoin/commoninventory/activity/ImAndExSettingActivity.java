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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.fragment.ScanDisplaySettingFragment;
import com.supoin.commoninventory.fragment.ERPSettingFragment;
import com.supoin.commoninventory.fragment.ExportSettingFragment;
import com.supoin.commoninventory.fragment.ImportSettingFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class ImAndExSettingActivity extends Activity implements OnClickListener {
	private Button btn_back;
	private TextView tv_import_setting, tv_export_setting, tv_display_setting,tv_erp_setting;
	private View view_import_setting, view_export_setting,
			view_display_setting, view_erp_setting;
	private RelativeLayout show_setting_view;
	private ImportSettingFragment mImportSettingFragment;
	private ExportSettingFragment mExportSettingFragment;
	private ScanDisplaySettingFragment mDisplaySettingFragment;
	private ERPSettingFragment mERPSettingFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(ImAndExSettingActivity.this, R.string.export_import_settings, false);
		setContentView(R.layout.activity_imandex_setting);
		Intent intent = getIntent();
		String tag = intent.getStringExtra("tag");
		tv_import_setting = (TextView) findViewById(R.id.tv_import_setting);
		tv_export_setting = (TextView) findViewById(R.id.tv_export_setting);
		tv_display_setting = (TextView) findViewById(R.id.tv_display_setting);
		//tv_erp_setting = (TextView) findViewById(R.id.tv_erp_setting);
		view_import_setting = findViewById(R.id.view_import_setting);
		view_export_setting = findViewById(R.id.view_export_setting);
		view_display_setting = findViewById(R.id.view_display_setting);
		//view_erp_setting = findViewById(R.id.view_erp_setting);

		show_setting_view = (RelativeLayout) findViewById(R.id.show_setting_view);

		tv_import_setting.setOnClickListener(this);
		tv_export_setting.setOnClickListener(this);
		tv_display_setting.setOnClickListener(this);
		//tv_erp_setting.setOnClickListener(this);
		
		if (tag != null) {
			if (tag.equals("0")) {
				mExportSettingFragment = new ExportSettingFragment();
				addFragment(mExportSettingFragment);
				view_export_setting.setBackgroundColor(getResources().getColor(
						R.color.title_yellow));
				tv_export_setting.setTextColor(getResources().getColor(
						R.color.title_yellow));
			} else {
				mImportSettingFragment = new ImportSettingFragment();
				addFragment(mImportSettingFragment);
				view_import_setting.setBackgroundColor(getResources().getColor(
						R.color.title_yellow));
				tv_import_setting.setTextColor(getResources().getColor(
						R.color.title_yellow));
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_import_setting:
			if (mImportSettingFragment == null) {
				mImportSettingFragment = new ImportSettingFragment();
				addFragment(mImportSettingFragment);
				showFragment(mImportSettingFragment);
			} else {
				showFragment(mImportSettingFragment);
			}
			view_import_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_export_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_display_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			/*view_erp_setting.setBackgroundColor(getResources().getColor(
					R.color.view));*/
			tv_import_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_export_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_display_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			//tv_erp_setting.setTextColor(getResources().getColor(R.color.gray_text));
			break;

		case R.id.tv_export_setting:

			if (mExportSettingFragment == null) {
				mExportSettingFragment = new ExportSettingFragment();
				addFragment(mExportSettingFragment);
				showFragment(mExportSettingFragment);
			} else {
				showFragment(mExportSettingFragment);
			}
			view_import_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_export_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_display_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			/*view_erp_setting.setBackgroundColor(getResources().getColor(
					R.color.view));*/
			tv_import_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_export_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_display_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			//tv_erp_setting.setTextColor(getResources().getColor(R.color.gray_text));
			break;
		case R.id.tv_display_setting:

			if (mDisplaySettingFragment == null) {
				mDisplaySettingFragment = new ScanDisplaySettingFragment();
				addFragment(mDisplaySettingFragment);
				showFragment(mDisplaySettingFragment);
			} else {
				showFragment(mDisplaySettingFragment);
			}
			view_import_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_export_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_display_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			/*view_erp_setting.setBackgroundColor(getResources().getColor(
					R.color.view));*/
			tv_import_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_export_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_display_setting.setTextColor(getResources().getColor(
					R.color.title_yellow));
			//tv_erp_setting.setTextColor(getResources().getColor(R.color.gray_text));
			break;
		/*case R.id.tv_erp_setting:
			if (mERPSettingFragment == null) {
				mERPSettingFragment = new ERPSettingFragment();
				addFragment(mERPSettingFragment);
				showFragment(mERPSettingFragment);
			} else {
				showFragment(mERPSettingFragment);
			}
			view_import_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_export_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_display_setting.setBackgroundColor(getResources().getColor(
					R.color.view));
			view_erp_setting.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			tv_import_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_export_setting.setTextColor(getResources()
					.getColor(R.color.gray_text));
			tv_display_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_erp_setting.setTextColor(getResources().getColor(
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
		if (mImportSettingFragment != null) {
			ft.hide(mImportSettingFragment);
		}
		if (mExportSettingFragment != null) {
			ft.hide(mExportSettingFragment);
		}
		if (mDisplaySettingFragment != null) {
			ft.hide(mDisplaySettingFragment);
		}
		if (mERPSettingFragment != null) {
			ft.hide(mERPSettingFragment);
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
