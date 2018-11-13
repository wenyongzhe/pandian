package com.supoin.commoninventory.activity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.PupopWindowAdpater;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.ToastUtils;
import com.supoin.commoninventory.util.Utility;

/**
 * 货位查询
 */
public class CheckStockPlaceActivity extends Activity {
	private SharedPreferences sp;
	private TextView tv_checkID, tv_positionID;
	private TextView tv_bianhao_cx2, tv_huowei_cx2;
	private EditText et_barCode;
	private ListView mListView;
	private TextView tv_amounts;
	private CheckStockPlaceListAdapter mCheckStockPlaceListAdapter;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private String strShopID;
	private String strCheckID, strPositionID;
	private List<String> strCheckIdList = new ArrayList<String>();
	private List<String> strPositionList = new ArrayList<String>();
	private List<String> strGbBarCodeList;
	private List<MainSummaryInfo> mMainSummaryInfoList;
	private List<CheckDetail> mCheckDetailList;
	private List<CheckListEntity> mStaCheckPositionDetailList;
	private String strGdBar, strBar;
	private List<GdInfo> listGdInfo, listGdInfo1, listGdInfo2;
	public int mPosition = 0;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	// Adapter初始false, 查询条码显示时adapter刷新为true
	private Boolean isCheckBarCode = false;
	private GdInfo mGdInfo;
	private String strOutBar;
	private String lengthCutOrKeepBarCode;
	private String lengthCutOrKeepBarCodeNum;
	private ListView pupopListView;
	private PopupWindow mPopWindow;
	private PupopWindowAdpater mPupopWindowAdpater;

	// 设置为外部码是否存在
	// private Boolean isExist = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		initText();
		CustomTitleBar.getTitleBar(CheckStockPlaceActivity.this, huowei,R.string.query_title, false);
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
		sqlStockDataSqlite = new SQLStockDataSqlite(
				CheckStockPlaceActivity.this, true);
		setContentView(R.layout.activity_check_stock_place);
		strOutBar = sp.getString(ConfigEntity.InOutCode, "");
		tv_checkID = (TextView) findViewById(R.id.tv_checkID);
		tv_bianhao_cx2 = (TextView) findViewById(R.id.tv_bianhao_cx2);
		tv_huowei_cx2 = (TextView) findViewById(R.id.tv_huowei_cx2);
		tv_positionID = (TextView) findViewById(R.id.tv_positionID);
		et_barCode = (EditText) findViewById(R.id.et_barCode);
		mListView = (ListView) findViewById(R.id.listView0);
		tv_amounts = (TextView) findViewById(R.id.tv_amounts);
		scanOperate = new ScanOperate();
		scanOperate.onCreate(CheckStockPlaceActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);
		mMainSummaryInfoList = sqlStockDataSqlite.GetSummaryList(strShopID);
		if (mMainSummaryInfoList.size() < 1) {
			AlertUtil.showToast(R.string.no_inventory_data, CheckStockPlaceActivity.this);
			tv_checkID.setClickable(false);
			tv_positionID.setClickable(false);
		} else {
			// 所有的编号
			for (MainSummaryInfo mMainSummaryInfo : mMainSummaryInfoList) {
				strCheckIdList.add(mMainSummaryInfo.getCheckID());
			}
			strCheckIdList = removeDuplicate(strCheckIdList);
		}

		tv_checkID.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						0);
				showPupopWindow();
				

			}
		});
		tv_positionID.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						0);
				String tv_checkID0 = tv_checkID.getText().toString();
				if (tv_checkID0.equals("")) {
					AlertUtil.showAlert(CheckStockPlaceActivity.this, R.string.dialog_title,
							AlertUtil.getString(R.string.please_select)+bianhao, R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				} else {
					showPupopWindow2();
					et_barCode.setText("");
				}

			}
		});
		et_barCode.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null
								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
								.getAction() == KeyEvent.ACTION_DOWN)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(), 0);
					strGdBar = et_barCode.getText().toString().trim();
					if (TextUtils.isEmpty(strGdBar)) {
						mCheckStockPlaceListAdapter.notifyDataSetChanged();
						AlertUtil.showAlert(CheckStockPlaceActivity.this, R.string.dialog_title,
								R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
										et_barCode.requestFocus();
									}
								});
					} else if (!TextUtils.isEmpty(strGdBar)) {
						ScrollToPosition(strGdBar);
					}
					return true;
				}

				return false;
			}
		});
		initText2();
	}
	private void initText2() {
		tv_bianhao_cx2.setText(bianhao+":");
		tv_checkID.setHint(AlertUtil.getString(R.string.please_select)+bianhao);	
		tv_huowei_cx2.setText(huowei+":");
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
		View contentView = LayoutInflater.from(CheckStockPlaceActivity.this)
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
		View rootview = LayoutInflater.from(CheckStockPlaceActivity.this)
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
				et_barCode.setText("");
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
		View contentView = LayoutInflater.from(CheckStockPlaceActivity.this)
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
		View rootview = LayoutInflater.from(CheckStockPlaceActivity.this)
				.inflate(R.layout.activity_main, null);
		mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
		pupopListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				tv_positionID.setText(mPupopWindowAdpater.getItem(arg2));
				mPopWindow.dismiss();
				strPositionID = strPositionList.get(arg2);
				if(TextUtils.isEmpty(strPositionID)){//当货号为空时，货号编辑框提示语内置为空
					tv_positionID.setHint("");
				}
				isCheckBarCode = false;
				int amount = 0;
				strGbBarCodeList = new ArrayList<String>();
				mStaCheckPositionDetailList = sqlStockDataSqlite
						.GetStaCheckPositionDetail(strShopID, strCheckID,
								strPositionID);
				for (CheckListEntity mStaCheckPositionDetail : mStaCheckPositionDetailList) {
					amount += mStaCheckPositionDetail.getdNum();
					strGbBarCodeList.add(mStaCheckPositionDetail.getStrBar());
				}

				if (mStaCheckPositionDetailList != null
						&& mStaCheckPositionDetailList.size() > 0) {
					mCheckStockPlaceListAdapter = new CheckStockPlaceListAdapter(
							CheckStockPlaceActivity.this,
							mStaCheckPositionDetailList);
					mListView.setAdapter(mCheckStockPlaceListAdapter);
					tv_amounts.setText(amount + "");

				} else {
					AlertUtil.showToast(R.string.no_pertinent_data, CheckStockPlaceActivity.this);
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

	// 有此条码时处理，滚动该行的背景改变颜色
	public void ScrollToPosition(String str) {
		if (mMainSummaryInfoList.size() < 1) {
			AlertUtil.showToast("无盘点数据", CheckStockPlaceActivity.this);
			return;
		}

		isCheckBarCode = true;

		// 设置为内部码
		if (strOutBar.equals("0")) {
			mGdInfo = sqlStockDataSqlite.GetDataByBar(str);
			if (listGdInfo != null && listGdInfo.size() > 0) {
				if (str.equals(mGdInfo.getStrGdBarcode())) {
					strGdBar = mGdInfo.getStrGdCode();
					if (strGbBarCodeList != null) {
						for (int i = 0; i < strGbBarCodeList.size(); i++) {

							if (strGbBarCodeList.get(i).contains(strGdBar)) { // 有此条码时处理，滚动该行的背景改变颜色
								// isCheckBarCode = true;
								mCheckStockPlaceListAdapter = new CheckStockPlaceListAdapter(
										CheckStockPlaceActivity.this,
										mStaCheckPositionDetailList);
								mListView.setAdapter(mCheckStockPlaceListAdapter);
								// mListView.smoothScrollToPosition(mPosition);

							} else {
								strGdBar = "";
								AlertUtil.showAlert(
										CheckStockPlaceActivity.this, R.string.dialog_title,
										R.string.no_inventoried_goods, R.string.ok,
										new View.OnClickListener() {

											@Override
											public void onClick(View v) { // TODO
												AlertUtil.dismissDialog();
												et_barCode.selectAll();
												et_barCode.requestFocus();

											}
										});
							}
						}
					} else {
						ToastUtils.show(CheckStockPlaceActivity.this,
								R.string.current_barcode_no__data);
					}

				}
			} else {
				strGdBar = "";
				mCheckStockPlaceListAdapter.notifyDataSetChanged();
				AlertUtil.showAlert(CheckStockPlaceActivity.this, R.string.dialog_title,
						R.string.no_inventoried_goods, R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();

							}
						});

			}
		} else {
			// 设置为外部码
			// isCheckBarCode = false;
			
			if(strGbBarCodeList==null){
				AlertUtil.showToast(AlertUtil.getString(R.string.please_select)+bianhao+AlertUtil.getString(R.string.or)+huowei, CheckStockPlaceActivity.this);
				return;
			}
			
			if (strGbBarCodeList.contains(str)) {
				// isCheckBarCode = true;
				strGdBar = str;
				mCheckStockPlaceListAdapter = new CheckStockPlaceListAdapter(
						CheckStockPlaceActivity.this,
						mStaCheckPositionDetailList);
				mListView.setAdapter(mCheckStockPlaceListAdapter);
				
				mListView.setSelection(mPosition);
				// mListView.smoothScrollToPosition(mPosition);
			} else {
				strGdBar = "";
				AlertUtil.showAlert(CheckStockPlaceActivity.this, R.string.dialog_title,
						R.string.no_inventoried_goods, R.string.ok, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();

							}
						});
			}
		}
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
		case 249:// KeyEvent.KEYCODE_MUTE:
			if (is_canScan) {
				if (event.getRepeatCount() == 0) {
					scanOperate.setScannerContinuousMode();
					scanOperate.Scanning();
					Intent scannerIntent = new Intent(
							ScanOperate.SCN_CUST_ACTION_START);
					sendBroadcast(scannerIntent);
				}
			}
			break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case 249:// KeyEvent.KEYCODE_MUTE:
			// if (is_canScan) {
			Intent scannerIntent = new Intent(
					ScanOperate.SCN_CUST_ACTION_CANCEL);
			sendBroadcast(scannerIntent);
			// }
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	// 这段如果觉得麻烦的话,可以写在BaseActivity中,让每个页面都继承
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ScanOperate.MESSAGE_TEXT:
				String code = (String) msg.obj;
				scanOperate.setVibratortime(50);
				scanOperate.mediaPlayer();
				// 截去或保留条码
				// "0不处理","1截去条码后面位数","2截去条码前面位数","3保留条码后面位数","4保留条码前面位数"
				lengthCutOrKeepBarCode = sp.getString(
						ConfigEntity.LengthCutOrKeepBarCodeKey, "");
				lengthCutOrKeepBarCodeNum = sp.getString(
						ConfigEntity.LengthCutOrKeepBarCodeNumKey, "");
				if (lengthCutOrKeepBarCode.equals("1")) // 截取后位数
				{
					code = code
							.substring(
									0,
									code.length()
											- Integer
													.parseInt(lengthCutOrKeepBarCodeNum));
				} else if (lengthCutOrKeepBarCode.equals("2")) // 截取前位数
				{

					code = code.substring(
							Integer.parseInt(lengthCutOrKeepBarCodeNum),
							code.length());
				} else if (lengthCutOrKeepBarCode.equals("3")) // 保留后位数
				{
					code = code
							.substring(
									code.length()
											- Integer
													.parseInt(lengthCutOrKeepBarCodeNum),
									code.length());
				} else if (lengthCutOrKeepBarCode.equals("4")) // 保留前位数
				{
					code = code.substring(0,
							Integer.parseInt(lengthCutOrKeepBarCodeNum));
				}
				et_barCode.setText(code);
				ScrollToPosition(code);
				et_barCode.selectAll();
				et_barCode.requestFocus();
				break;
			case 2:

				break;
			}

		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
		scanOperate.mHandler = null;
		scanOperate.onDestroy(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		scanOperate.mHandler = mHandler;
		// start the scanner.
		scanOperate.onResume(this);
		super.onResume();
	}

	public void onStop() {
		super.onStop();
		// close scanner
		scanOperate.onStop(this);
	}

	public class CheckStockPlaceListAdapter extends BaseAdapter {
		private List<CheckListEntity> list;
		private Context context;

		public List<CheckListEntity> getList() {
			return list;
		}

		public void setList(List<CheckListEntity> list) {
			this.list = list;
		}

		public CheckStockPlaceListAdapter(Context context,
				List<CheckListEntity> list) {
			super();
			this.context = context;
			this.list = list;
			positionIndexOfOne();
		}

		private void positionIndexOfOne() {
			
			//第一次初始化跳过
			if (!isCheckBarCode) {
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				if (strGdBar.equals(list.get(i).getStrBar())) {
					mPosition=i;
					return;
				}
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public CheckListEntity getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(context);
				convertView = mInflater.inflate(
						R.layout.item_listview_stockplace, null);
				viewHolder.tv_order_number = (TextView) convertView
						.findViewById(R.id.tv_order_number);
				viewHolder.tv_GdBar = (TextView) convertView
						.findViewById(R.id.tv_GdBar);
				viewHolder.tv_nums = (TextView) convertView
						.findViewById(R.id.tv_nums);
				viewHolder.tv_GdBar.setSelected(true);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (isCheckBarCode) {
				CheckListEntity mCheckListEntity = list.get(position);
				strBar = mCheckListEntity.getStrBar();
				if (strGdBar.equals(strBar)) {
					mPosition = position;
					convertView.setBackgroundResource(R.color.gray_bg);

				} else {
					convertView.setBackgroundColor(Color.TRANSPARENT);
				}
			}

			viewHolder.tv_order_number.setText(position + 1 + "");
			viewHolder.tv_GdBar.setText(list.get(position).getStrBar());

			if (Utility.isInteger(list.get(position).getdNum())) {
				viewHolder.tv_nums.setText((int) list.get(position).getdNum()
						+ "");
			} else {
				viewHolder.tv_nums.setText(list.get(position).getdNum() + "");
			}

			// 扫描有结果背景颜色则gray

			return convertView;
		}

		private class ViewHolder {
			TextView tv_order_number;
			TextView tv_GdBar;
			TextView tv_nums;
		}
	}
}