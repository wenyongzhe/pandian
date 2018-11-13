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
import com.supoin.commoninventory.fragment.GoodsIdSizeBitLengthFragment;
import com.supoin.commoninventory.fragment.StyleColorSizeBitLengthFragment;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class BarCodeSplitByBitLengthActivity extends Activity implements
		OnClickListener {
	// λ�����

	private TextView tv_style_color_size, tv_goodsid_size;
	private View view_style_color_size, view_goodsid_size;
	private RelativeLayout show_setting_view;
	private StyleColorSizeBitLengthFragment mStyleColorSizeFragment;
	private GoodsIdSizeBitLengthFragment mGoodsIdSizeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(BarCodeSplitByBitLengthActivity.this,"λ������", false);
		setContentView(R.layout.activity_barcode_bit_length);
		
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
			mStyleColorSizeFragment = new StyleColorSizeBitLengthFragment();
			addFragment(mStyleColorSizeFragment);
			showFragment(mStyleColorSizeFragment);
			view_style_color_size.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_style_color_size.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_goodsid_size.setClickable(false);
		} else if (tag.equals("1")) {
			mGoodsIdSizeFragment = new GoodsIdSizeBitLengthFragment();
			addFragment(mGoodsIdSizeFragment);
			showFragment(mGoodsIdSizeFragment);
			view_goodsid_size.setBackgroundColor(getResources().getColor(R.color.title_yellow));
			tv_style_color_size.setClickable(false);
			tv_goodsid_size.setTextColor(getResources().getColor(R.color.title_yellow));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_style_color_size:
			if (mStyleColorSizeFragment == null) {
				mStyleColorSizeFragment = new StyleColorSizeBitLengthFragment();
				addFragment(mStyleColorSizeFragment);
				showFragment(mStyleColorSizeFragment);
			} else {
				showFragment(mStyleColorSizeFragment);
			}
			view_style_color_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_goodsid_size.setBackgroundColor(getResources().getColor(
					R.color.gray_text));
			tv_style_color_size.setTextColor(getResources().getColor(
					R.color.title_yellow));
			tv_goodsid_size
					.setTextColor(getResources().getColor(R.color.view));
			break;
		case R.id.tv_goodsid_size:
			if (mGoodsIdSizeFragment == null) {
				mGoodsIdSizeFragment = new GoodsIdSizeBitLengthFragment();
				addFragment(mGoodsIdSizeFragment);
				showFragment(mGoodsIdSizeFragment);
			} else {
				showFragment(mGoodsIdSizeFragment);
			}
			view_goodsid_size.setBackgroundColor(getResources().getColor(
					R.color.title_yellow));
			view_style_color_size.setBackgroundColor(getResources().getColor(
					R.color.gray_text));
			tv_goodsid_size.setTextColor(getResources().getColor(R.color.title_yellow));
			tv_style_color_size.setTextColor(getResources().getColor(
					R.color.view));
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
		if (mStyleColorSizeFragment != null) {
			ft.hide(mStyleColorSizeFragment);
		}
		if (mGoodsIdSizeFragment != null) {
			ft.hide(mGoodsIdSizeFragment);
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
