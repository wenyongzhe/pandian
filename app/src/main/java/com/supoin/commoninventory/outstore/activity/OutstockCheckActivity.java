package com.supoin.commoninventory.outstore.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.BaseActivity;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

import java.util.ArrayList;
import java.util.List;

public class OutstockCheckActivity extends BaseActivity implements OnClickListener {

	private LinearLayout ll_check_number, ll_scan_order;
	private TextView tv_bianhao_cxtx;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(OutstockCheckActivity.this, R.string.query_outstock, false);
		setContentView(R.layout.activity_stock_check);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		tv_bianhao_cxtx = (TextView) findViewById(R.id.tv_bianhao_cxtx);

		ll_check_number = (LinearLayout) findViewById(R.id.ll_check_number);
		ll_scan_order = (LinearLayout) findViewById(R.id.ll_scan_order);
		ll_check_number.setOnClickListener(this);
		ll_scan_order.setOnClickListener(this);
		initText();
	}
	/**动态修改提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_bianhao_cxtx.setText(importStrArrayList.get(1)+OutstockCheckActivity.this.getResources().getString(R.string.query_title));
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
			Intent intent1 = new Intent(OutstockCheckActivity.this,OutstockCheckNumberActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_scan_order:
			Intent intent2 = new Intent(OutstockCheckActivity.this,OutstockCheckScanOrderActivity.class);
			startActivity(intent2);
			break;
		}
	}
}