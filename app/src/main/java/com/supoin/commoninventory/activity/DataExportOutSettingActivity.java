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
import com.supoin.commoninventory.fragment.ExportInstockSettingFragment;
import com.supoin.commoninventory.fragment.ExportOutstockSettingFragment;
import com.supoin.commoninventory.fragment.ExportSettingFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class DataExportOutSettingActivity extends Activity implements OnClickListener {

	private TextView tv_inventory_display, tv_instock_display, tv_outstock_display;
	private View view1, view2, view3;
	private ExportSettingFragment mExportSettingFragment;
	private ExportInstockSettingFragment mExportInstockSettingFragment;
	private ExportOutstockSettingFragment mExportOutstockSettingFragment;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(DataExportOutSettingActivity.this, R.string.export, false);
		setContentView(R.layout.activity_scandisplay_setting);
		tv_inventory_display = (TextView) findViewById(R.id.tv_inventory_display);
		tv_instock_display = (TextView) findViewById(R.id.tv_instock_display);
		tv_outstock_display = (TextView) findViewById(R.id.tv_outstock_display);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);

		tv_instock_display.setOnClickListener(this);
		tv_inventory_display.setOnClickListener(this);
		tv_outstock_display.setOnClickListener(this);
		String tag = getIntent().getStringExtra("tag");
		switch (Integer.parseInt(tag)) {
		case 0:
			mExportSettingFragment = new ExportSettingFragment();
			addFragment(mExportSettingFragment);
			view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_inventory_display.setTextColor(getResources().getColor(R.color.title_yellow));
			break;
		case 1:
			mExportInstockSettingFragment = new ExportInstockSettingFragment();
			addFragment(mExportInstockSettingFragment);
			view2.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_instock_display.setTextColor(getResources().getColor(R.color.title_yellow));
			break;
		case 2:
			mExportOutstockSettingFragment = new ExportOutstockSettingFragment();
			addFragment(mExportOutstockSettingFragment);
			view3.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_outstock_display.setTextColor(getResources().getColor(R.color.title_yellow));
			break;

		default:
			break;
		}
//		mExportSettingFragment = new ExportSettingFragment();
//		addFragment(mExportSettingFragment);
//		Bundle bundle1 = new Bundle();  
//        bundle1.putInt("id", 1);
//        mExportSettingFragment.setArguments(bundle1); 
//		view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
//		tv_inventory_display.setTextColor(getResources().getColor(R.color.title_yellow));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_inventory_display:
			
			if(mExportSettingFragment!=null){
				removeFragment(mExportSettingFragment);
			}
			mExportSettingFragment = new ExportSettingFragment();
			addFragment(mExportSettingFragment);
	        showFragment(mExportSettingFragment);
			view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_inventory_display.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_instock_display.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_display.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;

		case R.id.tv_instock_display:
			
			if(mExportInstockSettingFragment!=null){
				removeFragment(mExportInstockSettingFragment);
			}
			mExportInstockSettingFragment = new ExportInstockSettingFragment();
			addFragment(mExportInstockSettingFragment);
	        showFragment(mExportInstockSettingFragment);
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_instock_display.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_inventory_display.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_display.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;
		case R.id.tv_outstock_display:
			if(mExportOutstockSettingFragment!=null){
				removeFragment(mExportOutstockSettingFragment);
			}
			mExportOutstockSettingFragment = new ExportOutstockSettingFragment();
			addFragment(mExportOutstockSettingFragment);
	        showFragment(mExportOutstockSettingFragment);
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_outstock_display
					.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_inventory_display.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_instock_display.setTextColor(getResources().getColor(
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
		if (mExportSettingFragment != null) {
			ft.hide(mExportSettingFragment);
		}
		if (mExportInstockSettingFragment != null) {
			ft.hide(mExportInstockSettingFragment);
		}
		if (mExportOutstockSettingFragment != null) {
			ft.hide(mExportOutstockSettingFragment);
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