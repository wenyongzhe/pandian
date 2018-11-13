package com.supoin.commoninventory.activity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.CheckScanOrderListAdapter;
import com.supoin.commoninventory.adapter.PupopWindowAdpater;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite.ScanOrderInfo;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;

/**
 * 扫描顺序查询
 */
public class CheckScanOrderActivity extends Activity {
	private SharedPreferences sp;
	private String strShopID;
	private TextView tv_checkID, tv_positionID;
	private ListView mListView;
	private TextView tv_amounts,tv_bianhao_cx3,tv_huowei_cx3;
	private String strCheckID, strPositionID;
	private List<String> strCheckIdList = new ArrayList<String>();
	private List<String> strPositionList = new ArrayList<String>();
	private SQLStockDataSqlite sqlStockDataSqlite;
	private List<MainSummaryInfo> mMainSummaryInfoList;
	private List<ScanOrderInfo> mScanOrderInfoList;

	private CheckScanOrderListAdapter mCheckScanOrderListAdapter;
	private ListView pupopListView;
	private PopupWindow mPopWindow;
	private PupopWindowAdpater mPupopWindowAdpater;
	protected List<CheckListEntity> mStaCheckPositionDetailList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		initText();
		CustomTitleBar
				.getTitleBar(CheckScanOrderActivity.this, R.string.scan_sequence_query, false);
		sqlStockDataSqlite = new SQLStockDataSqlite(
				CheckScanOrderActivity.this, true);
		setContentView(R.layout.activity_check_scan_order);
		sp.edit().putString(ConfigEntity.RefreshUIKey, "1").commit();
		if (sp.getString(ConfigEntity.ERPKey, ConfigEntity.ERP).equals(
				ConfigurationKeys.GJP)) {
			strShopID = sp.getString(ConfigEntity.GraspInfoKey,
					ConfigEntity.GraspInfo).split(",")[0];
		} else {
			if (sp.getString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID)
					.equals("000000"))
				strShopID = "000000";
			else
				strShopID = sp.getString(ConfigEntity.ShopIDKey,
						ConfigEntity.ShopID);
		}
		tv_checkID = (TextView) findViewById(R.id.tv_checkID);
		tv_positionID = (TextView) findViewById(R.id.tv_positionID);
		mListView = (ListView) findViewById(R.id.listView0);
		tv_amounts = (TextView) findViewById(R.id.tv_amounts);
		tv_bianhao_cx3 = (TextView) findViewById(R.id.tv_bianhao_cx3);
		tv_huowei_cx3 = (TextView) findViewById(R.id.tv_huowei_cx3);

		mMainSummaryInfoList = sqlStockDataSqlite.GetSummaryList(strShopID);

		if (mMainSummaryInfoList.size() < 1) {
			AlertUtil.showToast(AlertUtil.getString(R.string.no_inventory_data), CheckScanOrderActivity.this);
			tv_checkID.setClickable(false);
			tv_positionID.setClickable(false);
		}
		for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
			strCheckIdList.add(mMainSummaryInfo.getCheckID());
		}
		strCheckIdList = removeDuplicate(strCheckIdList);
		tv_checkID.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// InputMethodManager imm = (InputMethodManager)
				// getSystemService(INPUT_METHOD_SERVICE);
				// imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				// 0);
				if (mMainSummaryInfoList.size() > 0) {
					showPupopWindow();
				} else {
					AlertUtil.showAlert(CheckScanOrderActivity.this, R.string.dialog_title,
							R.string.no_inventory_data, R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				}
			}
		});
		tv_positionID.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// InputMethodManager imm = (InputMethodManager)
				// getSystemService(INPUT_METHOD_SERVICE);
				// imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				// 0);
				String tv_checkID0 = tv_checkID.getText().toString();
				if (tv_checkID0.equals("")) {
					AlertUtil.showAlert(CheckScanOrderActivity.this, R.string.dialog_title,
							AlertUtil.getString(R.string.please_select)+bianhao, R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				} else {
					showPupopWindow2();
				}

			}
		});

		// mSpinnerAdapter1 = new SpinnerAdapter1(CheckScanOrderActivity.this,
		// strCheckIdList);
		// mSpinnerAdapter2 = new SpinnerAdapter1(CheckScanOrderActivity.this,
		// strPositionList);
		// spinner_checkID.setAdapter(mSpinnerAdapter1);
		// spinner_positionID.setAdapter(mSpinnerAdapter2);

		// spinner_checkID.setOnItemSelectedListener(new
		// OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// strCheckID = strCheckIdList.get(arg2);
		// strPositionList = new ArrayList<String>();
		// for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
		// if (strCheckID.equals(mMainSummaryInfo.getCheckID())) {
		// strPositionList.add(mMainSummaryInfo.getPositionID());
		// }
		// }
		// mSpinnerAdapter2 = new SpinnerAdapter1(
		// CheckScanOrderActivity.this, strPositionList);
		// spinner_positionID.setAdapter(mSpinnerAdapter2);
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		// spinner_positionID
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// int amount = 0;
		// strPositionID = strPositionList.get(arg2);
		// mScanOrderInfoList = sqlStockDataSqlite
		// .GetRecordsScanOrder(strShopID, strCheckID,
		// strPositionID);
		// for (ScanOrderInfo mScanOrderInfo : mScanOrderInfoList) {
		// amount += mScanOrderInfo.getCheckNum();
		// }
		//
		// if (mScanOrderInfoList != null
		// && mScanOrderInfoList.size() > 0) {
		// mCheckScanOrderListAdapter = new CheckScanOrderListAdapter(
		// CheckScanOrderActivity.this,
		// mScanOrderInfoList);
		// mListView.setAdapter(mCheckScanOrderListAdapter);
		// tv_amounts.setText(amount + "");
		//
		// } else {
		// AlertUtil.showToast("无盘点数据", CheckScanOrderActivity.this);
		// }
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// }
		// });
		initText2();
	}

	private void initText2() {
		tv_bianhao_cx3.setText(bianhao+":");
		tv_checkID.setHint(AlertUtil.getString(R.string.please_select)+bianhao);	
		tv_huowei_cx3.setText(huowei+":");
		tv_positionID.setHint(AlertUtil.getString(R.string.please_select)+huowei);
	}
	/**动态修改提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		bianhao = importStrArrayList.get(1);
		huowei = importStrArrayList.get(2);
	}
	private String bianhao,huowei;

	protected void showPupopWindow() {
		View contentView = LayoutInflater.from(CheckScanOrderActivity.this)
				.inflate(R.layout.pupopwindow, null);
		mPopWindow = new PopupWindow(contentView);
		mPopWindow.setWidth(LayoutParams.FILL_PARENT);
		mPopWindow.setHeight(LayoutParams.FILL_PARENT);
		mPopWindow.setFocusable(true);
		final View view = (View) contentView.findViewById(R.id.view);
		final TextView tv_title = (TextView) contentView
				.findViewById(R.id.popupwindow_title);
		ListView pupopListView = (ListView) contentView
				.findViewById(R.id.listview_pupopwindow);
		tv_title.setText(AlertUtil.getString(R.string.please_select)+bianhao);
		mPupopWindowAdpater = new PupopWindowAdpater(this, strCheckIdList);
		pupopListView.setAdapter(mPupopWindowAdpater);
		mPopWindow.setAnimationStyle(R.style.contextMenuAnim);
		View rootview = LayoutInflater.from(CheckScanOrderActivity.this)
				.inflate(R.layout.activity_main, null);
		mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
		pupopListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				tv_checkID.setText(mPupopWindowAdpater.getItem(arg2));
				mPopWindow.dismiss();
				strCheckID = strCheckIdList.get(arg2);
				strPositionList = new ArrayList<String>();
				for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
					if (strCheckID.equals(mMainSummaryInfo.getCheckID())) {
						strPositionList.add(mMainSummaryInfo.getPositionID());
					}
				}
				if (strPositionList.size() < 1) {
					tv_positionID.setClickable(false);
				} else {
					tv_positionID.setClickable(true);
				}
				tv_positionID.setText("");
				mListView.setAdapter(null);
				tv_amounts.setText("");
			}

		});

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
			}
		});
	}

	protected void showPupopWindow2() {
		View contentView = LayoutInflater.from(CheckScanOrderActivity.this)
				.inflate(R.layout.pupopwindow, null);
		mPopWindow = new PopupWindow(contentView);
		mPopWindow.setWidth(LayoutParams.FILL_PARENT);
		mPopWindow.setHeight(LayoutParams.FILL_PARENT);
		mPopWindow.setFocusable(true);
		final View view = (View) contentView.findViewById(R.id.view);
		final TextView tv_title = (TextView) contentView
				.findViewById(R.id.popupwindow_title);
		ListView pupopListView = (ListView) contentView
				.findViewById(R.id.listview_pupopwindow);
		tv_title.setText(AlertUtil.getString(R.string.please_select)+huowei);
		mPupopWindowAdpater = new PupopWindowAdpater(this, strPositionList);
		pupopListView.setAdapter(mPupopWindowAdpater);
		mPopWindow.setAnimationStyle(R.style.contextMenuAnim);
		View rootview = LayoutInflater.from(CheckScanOrderActivity.this)
				.inflate(R.layout.activity_main, null);
		mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
		pupopListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(TextUtils.isEmpty(mPupopWindowAdpater.getItem(arg2))){
					tv_positionID.setHint("");
				}
				tv_positionID.setText(mPupopWindowAdpater.getItem(arg2));
				mPopWindow.dismiss();
				mListView.setAdapter(null);
				tv_amounts.setText("");
				strPositionID = strPositionList.get(arg2);
				int amount = 0;
				strPositionID = strPositionList.get(arg2);
				mScanOrderInfoList = sqlStockDataSqlite.GetRecordsScanOrder(strShopID, strCheckID, strPositionID);
				
				for (ScanOrderInfo mScanOrderInfo : mScanOrderInfoList) {
//					if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {//启用唯一码时，不加负数
//						if (mScanOrderInfo.getCheckNum() <= 0) { 
//						}else {
//							amount += mScanOrderInfo.getCheckNum();
//						}
//					}else {
						amount += mScanOrderInfo.getCheckNum();
//					}
				}

				if (mScanOrderInfoList != null && mScanOrderInfoList.size() > 0) {
					mCheckScanOrderListAdapter = new CheckScanOrderListAdapter(CheckScanOrderActivity.this, mScanOrderInfoList);
					mListView.setAdapter(mCheckScanOrderListAdapter);
					tv_amounts.setText(amount + "");

				} else {
					AlertUtil.showToast(AlertUtil.getString(R.string.no_inventory_data), CheckScanOrderActivity.this);
					mListView.setAdapter(null);
					tv_amounts.setText("");
				}
			}
		});

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
			}
		});
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

	// List去掉重复数据
	private List<String> removeDuplicate(List<String> list) {
		Set<String> set = new LinkedHashSet<String>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		return list;

	}

}
