package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.fragment.GoodsIdSizeSeparatorFragment;
import com.supoin.commoninventory.fragment.StyleColorSizeSeparatorFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class BarCodeSplitBySeparatorActivity extends Activity implements
		OnClickListener {
	// �ָ��� ���
	private TextView tv_style_color_size, tv_goodsid_size;
	private View view_style_color_size, view_goodsid_size;
	private RelativeLayout show_setting_view;
	private StyleColorSizeSeparatorFragment mStyleColorSizeSeparatorFragment;
	private GoodsIdSizeSeparatorFragment mGoodsIdSizeSeparatorFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(BarCodeSplitBySeparatorActivity.this,
				"�ָ�������", false);
		setContentView(R.layout.activity_barcode_separator);
		Intent intent = getIntent();
		String tag = intent.getStringExtra("tag");
		tv_style_color_size = (TextView) findViewById(R.id.tv_style_color_size);
		tv_goodsid_size = (TextView) findViewById(R.id.tv_goodsid_size);
		view_style_color_size = findViewById(R.id.view_style_color_size);
		view_goodsid_size = findViewById(R.id.view_goodsid_size);
		show_setting_view = (RelativeLayout) findViewById(R.id.show_setting_view);
		tv_style_color_size.setOnClickListener(this);
		tv_goodsid_size.setOnClickListener(this);
		if (tag.equals("0")) {
			mStyleColorSizeSeparatorFragment = new StyleColorSizeSeparatorFragment();
			addFragment(mStyleColorSizeSeparatorFragment);
			showFragment(mStyleColorSizeSeparatorFragment);
			view_style_color_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			tv_style_color_size.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_goodsid_size.setClickable(false);
		} else if (tag.equals("1")) {
			mGoodsIdSizeSeparatorFragment = new GoodsIdSizeSeparatorFragment();
			addFragment(mGoodsIdSizeSeparatorFragment);
			showFragment(mGoodsIdSizeSeparatorFragment);
			view_goodsid_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			tv_style_color_size.setClickable(false);
			tv_goodsid_size.setTextColor(getResources().getColor(
					R.color.title_yellow));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_style_color_size:
			if (mStyleColorSizeSeparatorFragment == null) {
				mStyleColorSizeSeparatorFragment = new StyleColorSizeSeparatorFragment();
				addFragment(mStyleColorSizeSeparatorFragment);
				showFragment(mStyleColorSizeSeparatorFragment);
			} else {
				showFragment(mStyleColorSizeSeparatorFragment);
			}
			view_style_color_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_goodsid_size.setBackgroundColor(getResources().getColor(
					R.color.view));
			tv_style_color_size.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_goodsid_size
					.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.tv_goodsid_size:
			if (mGoodsIdSizeSeparatorFragment == null) {
				mGoodsIdSizeSeparatorFragment = new GoodsIdSizeSeparatorFragment();
				addFragment(mGoodsIdSizeSeparatorFragment);
				showFragment(mGoodsIdSizeSeparatorFragment);
			} else {
				showFragment(mGoodsIdSizeSeparatorFragment);

			}
			view_goodsid_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_style_color_size.setBackgroundColor(getResources().getColor(
					R.color.view));
			tv_goodsid_size.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_style_color_size.setTextColor(getResources().getColor(
					R.color.black));
			break;

		}

	}

	/** ���Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.show_setting_view, fragment);
		ft.commit();
	}

	// ** ɾ��Fragment **//
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	// ** ��ʾFragment **//
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (mStyleColorSizeSeparatorFragment != null) {
			ft.hide(mStyleColorSizeSeparatorFragment);
		}
		if (mGoodsIdSizeSeparatorFragment != null) {
			ft.hide(mGoodsIdSizeSeparatorFragment);
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
		// ����Activity&��ջ���Ƴ���Activity
		myApplication.getInstance().finishActivity(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}
}
