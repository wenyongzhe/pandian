package com.supoin.commoninventory.instore.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.instore.adapter.InStockListAdapter;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;

public class InStockMainActivity extends Activity implements OnItemClickListener {
	private EditText et_CheckID;
	private Button btn_add_goods;
	private TextView empty_list_view,tv_bianhao;
	private ListView mListView;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	private InStockListAdapter mStockInListAdapter;
	private List<MainSummaryInfo> mMainSummaryInfoList = new ArrayList<MainSummaryInfo>();
	private MainSummaryInfo mMainSummaryInfo = new MainSummaryInfo();
	private SQLInStockDataSqlite sqlInStockDataSqlite;
	private SharedPreferences sp;
	private String strCheckID, strPositionID;//编号、货位
	private String strShopID;
	// 由于在下个页面扫描会添加到这个activity的方法
	private Boolean is_intercept = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(InStockMainActivity.this, R.string.bar_store_in, false);
		setContentView(R.layout.activity_in_stock_main);
		sqlInStockDataSqlite = new SQLInStockDataSqlite(InStockMainActivity.this,true);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		//设置门店编号
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
		tv_bianhao = (TextView) findViewById(R.id.tv_bianhao);
//		tv_huowei = (TextView) findViewById(R.id.tv_huowei);
		et_CheckID = (EditText) findViewById(R.id.number);
//		et_PositionID = (EditText) findViewById(R.id.stockPlace);
		btn_add_goods = (Button) findViewById(R.id.add_new_goods);
		mListView = (ListView) findViewById(R.id.listview_scan_goods);
		empty_list_view = (TextView) findViewById(R.id.empty_list_view);
		initText();
		scanOperate = new ScanOperate();
		scanOperate.onCreate(InStockMainActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);
		mMainSummaryInfoList = sqlInStockDataSqlite.GetSummaryList(strShopID);
		
		//存在货位和编号时，加载到ListView中。否则隐藏ListView，显示empty_list_view
		if (mMainSummaryInfoList != null && mMainSummaryInfoList.size() > 0) {
			mStockInListAdapter = new InStockListAdapter(
					InStockMainActivity.this, mMainSummaryInfoList);
			mListView.setAdapter(mStockInListAdapter);
			// mListView.setSelection(mStockInListAdapter.getCount() - 1);
		} else {
			empty_list_view.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		}
		//设置列表项监听，跳转到商品扫面界面，传编号，货位值
		mListView.setOnItemClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mMainSummaryInfoList = sqlInStockDataSqlite
						.GetSummaryList(strShopID);
				strCheckID = mMainSummaryInfoList.get(position).getCheckID();
				strPositionID = mMainSummaryInfoList.get(position)
						.getPositionID();
				Intent intent = new Intent(InStockMainActivity.this,
						InStockDetailsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("strCheckID", strCheckID);
				bundle.putString("strPositionID", strPositionID);
				intent.putExtras(bundle);
				startActivity(intent);
				is_intercept=false;
//				finish();
			}
		});

		et_CheckID.setOnEditorActionListener(new OnEditorActionListener() {
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
					strCheckID = et_CheckID.getText().toString().trim()
							.toUpperCase();//编号
					if (TextUtils.isEmpty(strCheckID)) {
						AlertUtil.showAlert(InStockMainActivity.this, R.string.dialog_title,
								bianhao+" "+AlertUtil.getString(R.string.cannot_empty), R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
										et_CheckID.requestFocus();
									}
								});
						return false;
					}
//					et_PositionID.requestFocus();
					/**
					 * wyz
					if (TextUtils.isEmpty(strPositionID)) {
						AlertUtil.showAlert(InStockMainActivity.this,
								R.string.dialog_title, huowei+" "+AlertUtil.getString(R.string.document_empty),
								R.string.ok, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// stub
										AlertUtil.dismissDialog();
										handler.sendEmptyMessage(0);
									}
								}, R.string.cancel, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
					} else {
					 */
						// 保存地数据库存
						handler.sendEmptyMessage(0);
					//}
					return true;
				}

				return false;
			}
		});
//		et_PositionID.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_DONE
//						|| (event != null
//								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
//								.getAction() == KeyEvent.ACTION_DOWN)) {
//					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//					imm.hideSoftInputFromWindow(getCurrentFocus()
//							.getWindowToken(), 0);
//					strCheckID = et_CheckID.getText().toString().trim()
//							.toUpperCase();//编号
//					strPositionID = et_PositionID.getText().toString().trim()
//							.toUpperCase();//货位
//					if (TextUtils.isEmpty(strCheckID)) {
//						AlertUtil.showAlert(StockInMainActivity.this, R.string.dialog_title,
//								bianhao+AlertUtil.getString(R.string.cannot_empty), R.string.ok, new View.OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										AlertUtil.dismissDialog();
//										et_CheckID.requestFocus();
//									}
//								});
//						return false;
//					}
//					if (TextUtils.isEmpty(strPositionID)) {
//						AlertUtil.showAlert(StockInMainActivity.this,
//								R.string.dialog_title, huowei+AlertUtil.getString(R.string.document_empty),
//								R.string.ok, new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										// stub
//										AlertUtil.dismissDialog();
//										handler.sendEmptyMessage(0);
//
//									}
//								}, R.string.cancel, new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										AlertUtil.dismissDialog();
//									}
//								});
//					} else {
//						// 保存地数据库存
//						handler.sendEmptyMessage(0);
//					}
//					return true;
//				}
//				return false;
//			}
//		});

		btn_add_goods.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strCheckID = et_CheckID.getText().toString().trim()
						.toUpperCase();
//				strPositionID = et_PositionID.getText().toString().trim()
//						.toUpperCase();
				strPositionID = "";
				if (TextUtils.isEmpty(strCheckID)) {
					AlertUtil.showAlert(InStockMainActivity.this, R.string.dialog_title, bianhao+AlertUtil.getString(R.string.cannot_empty),
							R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
									et_CheckID.requestFocus();
								}
							});
					return;
				}
				/**
				 * 取消判断 货位
				if (TextUtils.isEmpty(strPositionID)) {
					AlertUtil.showAlert(InStockMainActivity.this,
							R.string.dialog_title, huowei+AlertUtil.getString(R.string.document_empty),
							R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// stub
									AlertUtil.dismissDialog();
									handler.sendEmptyMessage(0);
								}
							}, R.string.cancel, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
				} else {
					// 保存地数据库存
				}*/
				handler.sendEmptyMessage(0);
			}
		});
		
	}
	/**动态修改编号货位提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		bianhao = importStrArrayList.get(1);
		huowei = importStrArrayList.get(2);
		tv_bianhao.setText(bianhao+":");
		et_CheckID.setHint(AlertUtil.getString(R.string.please_enter)+bianhao);
//		tv_huowei.setText(huowei+":");
//		et_PositionID.setHint(AlertUtil.getString(R.string.please_enter)+huowei);
		String language = getResources().getConfiguration().locale.getLanguage();
		 if (language.endsWith("en")){
			 btn_add_goods.setText(AlertUtil.getString(R.string.newly_added));
	     }else{
	        btn_add_goods.setText(AlertUtil.getString(R.string.newly_added)+importStrArrayList.get(1));
//	        +importStrArrayList.get(2));
	    }
	}
	private String bianhao,huowei;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0) {
				strCheckID = et_CheckID.getText().toString().trim()
						.toUpperCase(); // 盘点编号
//				strPositionID = et_PositionID.getText().toString().trim()
//						.toUpperCase(); // 库位编号
				final List<MainSummaryInfo> tempSummaryList = sqlInStockDataSqlite
						.GetSummaryList(strCheckID, strPositionID, strShopID);
				if (tempSummaryList != null && tempSummaryList.size() > 0) {
					//存在该盘点单据
					AlertUtil.showAlert(InStockMainActivity.this,
							R.string.dialog_title, R.string.document_ruku_exists,
							R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
									//保存数据到数据库中
									sqlInStockDataSqlite.SaveCheckMain(strShopID,
											strCheckID, strPositionID);
									mMainSummaryInfo.setCheckID(strCheckID);
									mMainSummaryInfo
											.setPositionID(strPositionID);
									// mMainSummaryInfoList.add(0,
									// mMainSummaryInfo);
									if (mMainSummaryInfoList.size() == 1) {
										mStockInListAdapter = new InStockListAdapter(
												InStockMainActivity.this,
												mMainSummaryInfoList);
										mListView
												.setAdapter(mStockInListAdapter);
										empty_list_view
												.setVisibility(View.GONE);
										mListView.setVisibility(View.VISIBLE);
									} else {
										mStockInListAdapter
												.setList(mMainSummaryInfoList);
										mListView
												.setAdapter(mStockInListAdapter);
									}

									Intent intent = new Intent(
											InStockMainActivity.this,
											InStockDetailsActivity.class);
									Bundle bundle = new Bundle();
									bundle.putString("strCheckID", et_CheckID
											.getText().toString().trim()
											.toUpperCase());
//									bundle.putString("strPositionID",
//											et_PositionID.getText().toString()
//													.trim().toUpperCase());
									bundle.putString("strPositionID","");//wyz
									intent.putExtras(bundle);
									startActivity(intent);
									et_CheckID.setText("");
//									et_PositionID.setText("");
									is_intercept=false;
//									finish();
								}
							}, R.string.cancel, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
									return;
								}
							});

					// ScanGoodsActivity.this, R.string.dialog_title, "已经存在该盘点单据（确定续盘）。", R.string.ok,
					// new View.OnClickListener() {
					// @Override
					// public void onClick(View v) {
					// // TODO Auto-generated method stub
					// AlertUtil.dismissDialog();
					// for (int i = 0; i < tempSummaryList.size(); i++)
					// {
					// MainSummaryInfo item=tempSummaryList.get(i);
					// if(item.CheckID.equals(strCheckID)&&item.PositionID.equals(strPositionID))
					// {
					// mListView.requestFocus();
					// mListView.setSelection(i);
					// itemSelected = i;
					// break;
					// }
					// }
					// }
					// }

				} else {	//不存在该盘点单据，则新增
					//保存数据到数据库中
					sqlInStockDataSqlite.SaveCheckMain(strShopID, strCheckID,
							strPositionID);
					mMainSummaryInfo.setCheckID(strCheckID);
					mMainSummaryInfo.setPositionID(strPositionID);
					mMainSummaryInfoList.add(0, mMainSummaryInfo);
					if (mMainSummaryInfoList.size() == 1) {
						mStockInListAdapter = new InStockListAdapter(
								InStockMainActivity.this, mMainSummaryInfoList);
						mListView.setAdapter(mStockInListAdapter);
						empty_list_view.setVisibility(View.GONE);
						mListView.setVisibility(View.VISIBLE);
					} else {
						mStockInListAdapter.setList(mMainSummaryInfoList);
						mListView.setAdapter(mStockInListAdapter);
					}

					Intent intent = new Intent(InStockMainActivity.this,
							InStockDetailsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("strCheckID", et_CheckID.getText()
							.toString().trim().toUpperCase());
//					bundle.putString("strPositionID", et_PositionID.getText()
//							.toString().trim().toUpperCase());
					bundle.putString("strPositionID","");//wyz
					intent.putExtras(bundle);
					startActivity(intent);
//					et_CheckID.setText("");
//					et_PositionID.setText("");
					is_intercept=false;
//					finish();
				}

			} else if (msg.what == 1) {

			}
		}

	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	// myApplication.getInstance().addActivity(this);
	// CustomTitleBar.getTitleBar(CheckScanOrderActivity.this, "扫描顺序查询",false);

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
					Intent scannerIntent = new Intent(ScanOperate.SCN_CUST_ACTION_START);
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
			Intent scannerIntent = new Intent(ScanOperate.SCN_CUST_ACTION_CANCEL);
			sendBroadcast(scannerIntent);
			// }
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 扫描后获取的数据处理
	 * @param code
	 */
	private void OnGetBarcode(String code) {
		if (et_CheckID.isFocused()) {
			et_CheckID.setText(code);
			et_CheckID.setSelection(et_CheckID.getText().length());
			strCheckID = code;
			// if (TextUtils.isEmpty(strCheckID)) {
			// AlertUtil.showAlert(ScanGoodsActivity.this, R.string.dialog_title,
			// "编号不能为空", R.string.ok, new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// AlertUtil.dismissDialog();
			// et_CheckID.requestFocus();
			// }
			// });
			// return;
			// }
//			et_PositionID.requestFocus();
			// if (TextUtils.isEmpty(strPositionID)) {
			// AlertUtil.showAlert(ScanGoodsActivity.this,
			// R.string.dialog_title, "货位为空！是否继续添加当前盘点单据？",
			// R.string.ok, new View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method
			// // stub
			// AlertUtil.dismissDialog();
			// handler.sendEmptyMessage(0);
			// }
			// }, R.string.cancel, new View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// AlertUtil.dismissDialog();
			// }
			// });
			// } else {
			// // 保存地数据库存
			// handler.sendEmptyMessage(0);
			// }
		} 
//		else if (et_PositionID.isFocused()) {
//			et_PositionID.setText(code);
//			et_PositionID.setSelection(et_PositionID.getText().length());
//		}
		// strPositionID=code;
		// if (TextUtils.isEmpty(strCheckID)) {
		// AlertUtil.showAlert(ScanGoodsActivity.this, R.string.dialog_title,
		// "盘点编号不能为空", R.string.ok, new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// AlertUtil.dismissDialog();
		// et_CheckID.requestFocus();
		// }
		// });
		// return;
		// }
		// if (TextUtils.isEmpty(strPositionID)) {
		// AlertUtil.showAlert(ScanGoodsActivity.this,
		// R.string.dialog_title, "货位为空！是否继续添加当前盘点单据？",
		// R.string.ok, new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method
		// // stub
		// AlertUtil.dismissDialog();
		// handler.sendEmptyMessage(0);
		// }
		// }, R.string.cancel, new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// AlertUtil.dismissDialog();
		// }
		// });
		// } else {
		// // 保存地数据库存
		// handler.sendEmptyMessage(0);
		// }
		//
		// }
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
				// 扫描后获取数据处理
				if (is_intercept) {
					OnGetBarcode(code);
				}
				// 连续扫描
				// Intent scannerIntent = new Intent(
				// ScanOperate.SCN_CUST_ACTION_START);
				// sendBroadcast(scannerIntent);
				break;
			case 2:

				break;
			}

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		scanOperate.mHandler = mHandler;
		// start the scanner.
		scanOperate.onResume(this);
		mMainSummaryInfoList = sqlInStockDataSqlite.GetSummaryList(strShopID);
		if (mMainSummaryInfoList != null && mMainSummaryInfoList.size() > 0) {
			mStockInListAdapter.setList(mMainSummaryInfoList);
			mListView.setAdapter(mStockInListAdapter);
		}
		is_intercept=true;
		super.onResume();
	}

	public void onStop() {
		super.onStop();
		// close scanner
		scanOperate.onStop(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
		scanOperate.mHandler = null;
		scanOperate.onDestroy(this);
	}

	/*
	 * private class ComparatorByTime implements Comparator<MainSummaryInfo> {
	 * 
	 * @Override public int compare(MainSummaryInfo lhs, MainSummaryInfo rhs) {
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * sDate; Date rDate; try { sDate = sdf.parse(lhs.ScanTime); rDate =
	 * sdf.parse(rhs.ScanTime); // 对日期字段进行升序，如果欲降序可采用after方法 if
	 * (sDate.before(rDate)) { return 1; }
	 * 
	 * } catch (ParseException e) { // TODO Auto-generated catch block
	 * Utility.deBug(TAG, "ComparatorByTime" + e.getMessage());
	 * e.printStackTrace(); } return -1; } }
	 */
}