package com.supoin.commoninventory.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.fragment.DataExportOutInstockFragment;
import com.supoin.commoninventory.fragment.DataExportOutInventoryFragment;
import com.supoin.commoninventory.fragment.DataExportOutOutstockFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class DataExportOutActivity extends Activity implements OnClickListener {

	private TextView tv_inventory_export, tv_others_setting, tv_outstock_export;
	private View view1, view2, view3;
	private DataExportOutInventoryFragment mDataExportOutInventoryFragment;
	private DataExportOutInstockFragment mDataExportOutInstockFragment;
	private DataExportOutOutstockFragment mDataExportOutOutstockFragment;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(DataExportOutActivity.this, R.string.export_inventory, false);
		setContentView(R.layout.activity_data_export2);
		tv_inventory_export = (TextView) findViewById(R.id.tv_inventory_export);
		tv_others_setting = (TextView) findViewById(R.id.tv_instock_export);
		tv_outstock_export = (TextView) findViewById(R.id.tv_outstock_export);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);

		tv_others_setting.setOnClickListener(this);
		tv_inventory_export.setOnClickListener(this);
		tv_outstock_export.setOnClickListener(this);
		mDataExportOutInventoryFragment = new DataExportOutInventoryFragment();
		addFragment(mDataExportOutInventoryFragment);
		view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
		tv_inventory_export.setTextColor(getResources().getColor(R.color.title_yellow));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_inventory_export:
			if (mDataExportOutInventoryFragment == null) {
				mDataExportOutInventoryFragment = new DataExportOutInventoryFragment();
				addFragment(mDataExportOutInventoryFragment);
				showFragment(mDataExportOutInventoryFragment);
			} else {
				showFragment(mDataExportOutInventoryFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_inventory_export.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_others_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_export.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;

		case R.id.tv_instock_export:

			if (mDataExportOutInstockFragment == null) {
				mDataExportOutInstockFragment = new DataExportOutInstockFragment();
				addFragment(mDataExportOutInstockFragment);
				showFragment(mDataExportOutInstockFragment);
			} else {
				showFragment(mDataExportOutInstockFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_others_setting.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_inventory_export.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_export.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;
		case R.id.tv_outstock_export:

			if (mDataExportOutOutstockFragment == null) {
				mDataExportOutOutstockFragment = new DataExportOutOutstockFragment();
				addFragment(mDataExportOutOutstockFragment);
				showFragment(mDataExportOutOutstockFragment);
			} else {
				showFragment(mDataExportOutOutstockFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_outstock_export
					.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_inventory_export.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_others_setting.setTextColor(getResources().getColor(
					R.color.gray_text));
			break;

		}

	}

	/** Ìí¼ÓFragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.show_setting_view, fragment);
		ft.commit();
	}

	// ** É¾³ýFragment **//
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	// ** ÏÔÊ¾Fragment **//
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (mDataExportOutInventoryFragment != null) {
			ft.hide(mDataExportOutInventoryFragment);
		}
		if (mDataExportOutInstockFragment != null) {
			ft.hide(mDataExportOutInstockFragment);
		}
		if (mDataExportOutOutstockFragment != null) {
			ft.hide(mDataExportOutOutstockFragment);
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

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		myApplication.getInstance().finishActivity(this);
	}

	@Override
	protected void onResume() {
		CustomTitleBar.setActivity(this);
		super.onResume();
	}
}