package com.supoin.commoninventory.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class CountDataActivity extends Activity implements OnClickListener {

	private SharedPreferences sp;
	private LinearLayout ll_check_number, ll_check_stock_place, ll_scan_order;
	private TextView tv_bianhao_cxtx,tv_huowei_cxtx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(CountDataActivity.this, R.string.query_inventory, false);
		setContentView(R.layout.activity_countdata);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		
		ll_check_number = (LinearLayout) findViewById(R.id.ll_check_number);
		ll_check_stock_place = (LinearLayout) findViewById(R.id.ll_check_stock_place);
		ll_scan_order = (LinearLayout) findViewById(R.id.ll_scan_order);
		
		tv_bianhao_cxtx = (TextView) findViewById(R.id.tv_bianhao_cxtx);
		tv_huowei_cxtx = (TextView) findViewById(R.id.tv_huowei_cxtx);
		initText();
		
		ll_check_number.setOnClickListener(this);
		ll_check_stock_place.setOnClickListener(this);
		ll_scan_order.setOnClickListener(this);
	}

	/**动态修改提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_bianhao_cxtx.setText(importStrArrayList.get(1)+CountDataActivity.this.getResources().getString(R.string.query_title));
		tv_huowei_cxtx.setText(importStrArrayList.get(2)+CountDataActivity.this.getResources().getString(R.string.query_title));
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
		CustomTitleBar.setActivity(this);
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_check_number:
			Intent intent1 = new Intent(CountDataActivity.this,CheckNumberActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_check_stock_place:
			Intent intent2 = new Intent(CountDataActivity.this,CheckStockPlaceActivity.class);
			startActivity(intent2);
			break;
		case R.id.ll_scan_order:
			Intent intent3 = new Intent(CountDataActivity.this,CheckScanOrderActivity.class);
			startActivity(intent3);
			break;
		

		}
	}

}
