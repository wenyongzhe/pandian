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
import com.supoin.commoninventory.fragment.DeleteDataInstockFragment;
import com.supoin.commoninventory.fragment.DeleteDataInventoryFragment;
import com.supoin.commoninventory.fragment.DeleteDataOutstockFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class DeleteDataActivity extends Activity implements OnClickListener {

	private TextView tv_inventory_delete, tv_instock_delete, tv_outstock_delete;
	private View view1, view2, view3;
	private DeleteDataInventoryFragment mOutInSettingFragment;
	private DeleteDataInstockFragment mOtherSettingFragment;
	private DeleteDataOutstockFragment mVioceSettingFragment;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(DeleteDataActivity.this, R.string.data_delete, false);
		setContentView(R.layout.activity_delete_data2);
		tv_inventory_delete = (TextView) findViewById(R.id.tv_inventory_delete);
		tv_instock_delete = (TextView) findViewById(R.id.tv_instock_delete);
		tv_outstock_delete = (TextView) findViewById(R.id.tv_outstock_delete);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);

		tv_instock_delete.setOnClickListener(this);
		tv_inventory_delete.setOnClickListener(this);
		tv_outstock_delete.setOnClickListener(this);
		mOutInSettingFragment = new DeleteDataInventoryFragment();
		addFragment(mOutInSettingFragment);
		view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
		tv_inventory_delete.setTextColor(getResources().getColor(R.color.title_yellow));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_inventory_delete:
			if (mOutInSettingFragment == null) {
				mOutInSettingFragment = new DeleteDataInventoryFragment();
				addFragment(mOutInSettingFragment);
				showFragment(mOutInSettingFragment);
			} else {
				showFragment(mOutInSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_inventory_delete.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_instock_delete.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_delete.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;

		case R.id.tv_instock_delete:

			if (mOtherSettingFragment == null) {
				mOtherSettingFragment = new DeleteDataInstockFragment();
				addFragment(mOtherSettingFragment);
				showFragment(mOtherSettingFragment);
			} else {
				showFragment(mOtherSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			view3.setBackgroundColor(getResources().getColor(R.color.view));
			tv_instock_delete.setTextColor(getResources()
					.getColor(R.color.title_yellow));
			tv_inventory_delete.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_outstock_delete.setTextColor(getResources()
					.getColor(R.color.gray_text));
			break;
		case R.id.tv_outstock_delete:

			if (mVioceSettingFragment == null) {
				mVioceSettingFragment = new DeleteDataOutstockFragment();
				addFragment(mVioceSettingFragment);
				showFragment(mVioceSettingFragment);
			} else {
				showFragment(mVioceSettingFragment);
			}
			view1.setBackgroundColor(getResources().getColor(R.color.view));
			view2.setBackgroundColor(getResources().getColor(R.color.view));
			view3.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_outstock_delete
					.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_inventory_delete.setTextColor(getResources().getColor(
					R.color.gray_text));
			tv_instock_delete.setTextColor(getResources().getColor(
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
