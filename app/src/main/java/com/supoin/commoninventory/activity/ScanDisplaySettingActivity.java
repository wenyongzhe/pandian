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
import com.supoin.commoninventory.fragment.ScanDisplaySettingFragment2;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class ScanDisplaySettingActivity extends Activity implements OnClickListener {

	private TextView tv_inventory_display, tv_instock_display, tv_outstock_display;
	private View view1, view2, view3;
	private ScanDisplaySettingFragment2 mInventoryDisplayFragment;
	private ScanDisplaySettingFragment2 mInstockDisplayFragment;
	private ScanDisplaySettingFragment2 mOutstockDisplayFragment;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(ScanDisplaySettingActivity.this, R.string.scan_display, false);
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
		mInventoryDisplayFragment = new ScanDisplaySettingFragment2();
		addFragment(mInventoryDisplayFragment);
		Bundle bundle1 = new Bundle();  
        bundle1.putInt("id", 1);
        mInventoryDisplayFragment.setArguments(bundle1); 
		view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
		tv_inventory_display.setTextColor(getResources().getColor(R.color.title_yellow));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_inventory_display:
			
			if(mInventoryDisplayFragment!=null){
				removeFragment(mInventoryDisplayFragment);
			}
			mInventoryDisplayFragment = new ScanDisplaySettingFragment2();
			addFragment(mInventoryDisplayFragment);
			Bundle bundle1 = new Bundle();  
	        bundle1.putInt("id", 1);
	        mInventoryDisplayFragment.setArguments(bundle1);  
	        showFragment(mInventoryDisplayFragment);
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
			
			if(mInstockDisplayFragment!=null){
				removeFragment(mInstockDisplayFragment);
			}
			mInstockDisplayFragment = new ScanDisplaySettingFragment2();
			addFragment(mInstockDisplayFragment);
			Bundle bundle2 = new Bundle();  
	        bundle2.putInt("id", 2);
	        mInstockDisplayFragment.setArguments(bundle2);
	        showFragment(mInstockDisplayFragment);
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
			if(mOutstockDisplayFragment!=null){
				removeFragment(mOutstockDisplayFragment);
			}
			mOutstockDisplayFragment = new ScanDisplaySettingFragment2();
			addFragment(mOutstockDisplayFragment);
			Bundle bundle3 = new Bundle();  
	        bundle3.putInt("id", 3);
	        mOutstockDisplayFragment.setArguments(bundle3);
	        showFragment(mOutstockDisplayFragment);
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
		if (mInventoryDisplayFragment != null) {
			ft.hide(mInventoryDisplayFragment);
		}
		if (mInstockDisplayFragment != null) {
			ft.hide(mInstockDisplayFragment);
		}
		if (mOutstockDisplayFragment != null) {
			ft.hide(mOutstockDisplayFragment);
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