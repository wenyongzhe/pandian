package com.supoin.commoninventory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.instore.activity.InstockCheckActivity;
import com.supoin.commoninventory.outstore.activity.OutstockCheckActivity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;

public class CheckTypeSelectActivity extends BaseActivity implements OnClickListener {

	private LinearLayout ll_query_inventory, ll_query_instock, ll_query_outstock,ll_check_base_data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(CheckTypeSelectActivity.this, R.string.summary_statistics, false);
		setContentView(R.layout.activity_checktype_select);
		
		ll_query_inventory = (LinearLayout) findViewById(R.id.ll_query_inventory);
		ll_query_instock = (LinearLayout) findViewById(R.id.ll_query_instock);
		ll_query_outstock = (LinearLayout) findViewById(R.id.ll_query_outstock);
		ll_check_base_data = (LinearLayout) findViewById(R.id.ll_check_base_data);
		ll_query_inventory.setOnClickListener(this);
		ll_query_instock.setOnClickListener(this);
		ll_query_outstock.setOnClickListener(this);
		ll_check_base_data.setOnClickListener(this);
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
		case R.id.ll_query_inventory:
			Intent intent1 = new Intent(CheckTypeSelectActivity.this,CountDataActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_query_instock:
			Intent intent2 = new Intent(CheckTypeSelectActivity.this,InstockCheckActivity.class);
			startActivity(intent2);
			break;
		case R.id.ll_query_outstock:
			Intent intent3 = new Intent(CheckTypeSelectActivity.this,OutstockCheckActivity.class);
			startActivity(intent3);
			break;
		case R.id.ll_check_base_data:
			Intent intent4 = new Intent(CheckTypeSelectActivity.this,CheckBaseDataActivity.class);
			startActivity(intent4);
			break;
		}
	}
}