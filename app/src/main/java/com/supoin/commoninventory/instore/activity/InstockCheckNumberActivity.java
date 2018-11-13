package com.supoin.commoninventory.instore.activity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.BaseActivity;
import com.supoin.commoninventory.adapter.CheckNumberListAdapter1;
import com.supoin.commoninventory.adapter.PupopWindowAdpater;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.SQLInStockDataSqlite.StaCheckNODetail;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.instore.adapter.CheckNumberListAdapter;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
/**
 * 编号查询
 * @date 2017-5-23 下午3:16:08 
 *
 */
public class InstockCheckNumberActivity extends BaseActivity {
	private SharedPreferences sp;
	private List<String> strCheckIdAll = new ArrayList<String>();
	private SQLInStockDataSqlite sqlStockDataSqlite;
	private List<MainSummaryInfo> mMainSummaryInfoList, mMainSummaryInfoList1;
	private MainSummaryInfo mMainSummaryInfo;
	private List<StaCheckNODetail> mCheckNODetailList;
	private String strShopID;
	private CheckBox cb_details;
	private Boolean isCheck = true;
	private String strCheckID;
	private ListView mListView, mListView1;
	private LinearLayout ll_show, ll_show_detail;
	//明细
	private CheckNumberListAdapter mCheckNumberListAdapter;
	private TextView tv_amount;
	private HorizontalScrollView hc_show_detail;
	private TextView tv_checkID,tv_bianhao_cx1;
	private ListView pupopListView;
	private PopupWindow mPopWindow;
	private PupopWindowAdpater mPupopWindowAdpater;
	private CheckNumberListAdapter1 mCheckNumberListAdapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		initText();
		CustomTitleBar.getTitleBar(InstockCheckNumberActivity.this, bianhao,R.string.query_title, false);
		sqlStockDataSqlite = new SQLInStockDataSqlite(InstockCheckNumberActivity.this,true);
		setContentView(R.layout.activity_check_number);
		cb_details = (CheckBox) findViewById(R.id.cb_details);
		tv_checkID = (TextView) findViewById(R.id.tv_checkID);
		tv_bianhao_cx1 = (TextView) findViewById(R.id.tv_bianhao_cx1);
		mListView = (ListView) findViewById(R.id.listView1);
		mListView1 = (ListView) findViewById(R.id.listView2);
		ll_show_detail = (LinearLayout) findViewById(R.id.ll_show_detail);
		hc_show_detail = (HorizontalScrollView) findViewById(R.id.hc_show_detail);
		ll_show = (LinearLayout) findViewById(R.id.ll_show);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		cb_details.setVisibility(View.GONE);
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
		mMainSummaryInfoList = sqlStockDataSqlite.GetSummaryList(strShopID);
		if (mMainSummaryInfoList.size() < 1) {
			AlertUtil.showToast(R.string.instock_no_inventory_data, InstockCheckNumberActivity.this);
			tv_checkID.setClickable(false);
		}
		for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
			strCheckIdAll.add(mMainSummaryInfo.getCheckID());
		}

		strCheckIdAll = removeDuplicate(strCheckIdAll);
		String str_checkID = tv_checkID.getText().toString();
		if (str_checkID.equals("")) {
			cb_details.setEnabled(false);
		}
		cb_details.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			
					if (cb_details.isChecked()) {
						isCheck = true;
						handler.sendEmptyMessage(0);
					} else {
						isCheck = false;
						handler.sendEmptyMessage(1);
					}
				

			}
		});
		tv_checkID.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mMainSummaryInfoList.size()>0){
					showPupopWindow();
				}else {
					AlertUtil.showAlert(InstockCheckNumberActivity.this, R.string.dialog_title,
							R.string.instock_no_inventory_data, R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				}
				

			}
		});
		initText2();
	}
	private void initText2() {
		tv_bianhao_cx1.setText(bianhao+":");
		tv_checkID.setHint(AlertUtil.getString(R.string.please_select)+bianhao);		
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
	}
	
	private String bianhao;
	protected void showPupopWindow() {
		View contentView = LayoutInflater.from(InstockCheckNumberActivity.this)
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
		mPupopWindowAdpater = new PupopWindowAdpater(this, strCheckIdAll);
		pupopListView.setAdapter(mPupopWindowAdpater);
		pupopListView.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				tv_checkID.setText(mPupopWindowAdpater.getItem(arg2));
				mPopWindow.dismiss();
				cb_details.setEnabled(true);
				int amount = 0;
				strCheckID = strCheckIdAll.get(arg2);
				if (isCheck) {
					ll_show.setVisibility(View.GONE);
					ll_show_detail.setVisibility(View.VISIBLE);
					hc_show_detail.setVisibility(View.VISIBLE);
					// 选中明细
					mCheckNODetailList = sqlStockDataSqlite.GetStaCheckNODetail(strShopID, strCheckID);
					for (StaCheckNODetail mStaCheckNODetail : mCheckNODetailList) {
						amount += mStaCheckNODetail.getSums();
					}
					if (mCheckNODetailList != null
							&& mCheckNODetailList.size() > 0) {
						mCheckNumberListAdapter = new CheckNumberListAdapter(
								InstockCheckNumberActivity.this, mCheckNODetailList);
						mListView.setAdapter(mCheckNumberListAdapter);
						tv_amount.setText(amount + "");

					} else {
						mListView.setAdapter(null);
						tv_amount.setText("");
						AlertUtil.showToast(R.string.no_pertinent_data, InstockCheckNumberActivity.this);
					}
				} else {
					// 不选中明细
					ll_show.setVisibility(View.VISIBLE);
					ll_show_detail.setVisibility(View.GONE);
					hc_show_detail.setVisibility(View.GONE);
					mMainSummaryInfoList1 = new ArrayList<MainSummaryInfo>();
					for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
						if (strCheckID.equals(mMainSummaryInfo.getCheckID())) {
							mMainSummaryInfoList1.add(mMainSummaryInfo);
							amount += mMainSummaryInfo.getQty();
						}
					}
					mCheckNumberListAdapter1 = new CheckNumberListAdapter1(InstockCheckNumberActivity.this, mMainSummaryInfoList1);
					mListView1.setAdapter(mCheckNumberListAdapter1);
					mCheckNumberListAdapter1.notifyDataSetChanged();
					tv_amount.setText(amount + "");
				}

			}
		});

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
			}
		});
		mPopWindow.setAnimationStyle(R.style.contextMenuAnim);
		View rootview = LayoutInflater.from(InstockCheckNumberActivity.this).inflate(R.layout.activity_main, null);
		mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
	}

	// List去掉重复数据
	private List<String> removeDuplicate(List<String> list) {
		Set<String> set = new LinkedHashSet<String>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		return list;
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

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				ll_show.setVisibility(View.GONE);
				ll_show_detail.setVisibility(View.VISIBLE);
				hc_show_detail.setVisibility(View.VISIBLE);
				// 选中明细
				if (mMainSummaryInfoList != null
						&& mMainSummaryInfoList.size() > 0) {

					mCheckNODetailList = sqlStockDataSqlite
							.GetStaCheckNODetail(strShopID, strCheckID);
					if (mCheckNODetailList != null
							&& mCheckNODetailList.size() > 0) {
						mCheckNumberListAdapter = new CheckNumberListAdapter(
								InstockCheckNumberActivity.this, mCheckNODetailList);
						mListView.setAdapter(mCheckNumberListAdapter);

					} else {
						mListView.setAdapter(null);
						tv_amount.setText("");
						AlertUtil.showToast(R.string.instock_no_inventory_data, InstockCheckNumberActivity.this);
					}

				} else {
					AlertUtil.showToast(R.string.no_inventory_detailed_data, InstockCheckNumberActivity.this);
					mCheckNODetailList = new ArrayList<SQLInStockDataSqlite.StaCheckNODetail>();
					mCheckNumberListAdapter = new CheckNumberListAdapter(
							InstockCheckNumberActivity.this, mCheckNODetailList);
					mListView.setAdapter(mCheckNumberListAdapter);
					return;
				}

			} else if (msg.what == 1) {

				// 不选中明细
				ll_show.setVisibility(View.VISIBLE);
				ll_show_detail.setVisibility(View.GONE);
				hc_show_detail.setVisibility(View.GONE);
				mMainSummaryInfoList1 = new ArrayList<MainSummaryInfo>();
				for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
					if (strCheckID.equals(mMainSummaryInfo.getCheckID())) {
						mMainSummaryInfoList1.add(mMainSummaryInfo);
						// amount += mMainSummaryInfo.getQty();
					}
				}
				mCheckNumberListAdapter1 = new CheckNumberListAdapter1(
						InstockCheckNumberActivity.this, mMainSummaryInfoList1);
				mListView1.setAdapter(mCheckNumberListAdapter1);
				mCheckNumberListAdapter1.notifyDataSetChanged();

			}
		}
	};

	@Override
	public void onClick(View v) {
		
	}

}
