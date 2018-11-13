package com.supoin.commoninventory.outstore.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Utility;

public class OutStockModityQtyActivity extends Activity implements OnClickListener {
	private Button btn_ok, btn_cancl;
	private EditText et_qty;
	private SharedPreferences sp;
	private SQLInStockDataSqlite sqlStockDataSqlite;
	private Handler handler = new Handler();
	private String title;
	private TextView tv_barcode;
	private String strGdBar;
	private int qty = 0, oldQty = 0;
	private String TAG = "ModityQtyActivity";
//	public  TextView name;
	public TextView num;	
	private String type = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//CustomTitleBar.getTitleBar(ModityQtyActivity.this, "修改数量", false);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		//系统参数 是否输入数量 0输入，1不输入
		if (sp.getString(ConfigEntity.IsPutInNumKey, ConfigEntity.IsPutInNum).equals("0")) {
		CustomTitleBar.getTitleBar(OutStockModityQtyActivity.this,AlertUtil.getString(R.string.enter_quantity), false);
	}else {
		CustomTitleBar.getTitleBar(OutStockModityQtyActivity.this, AlertUtil.getString(R.string.modify_quantity), false);
	}
		setContentView(R.layout.activity_modify_qyt);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strGdBar = bundle.getString("GdBar");
		
		type = sp.getString(ConfigEntity.IsDataDecimalPointKey, ConfigEntity.IsDataDecimalPoint);
		sqlStockDataSqlite = new SQLInStockDataSqlite(OutStockModityQtyActivity.this,true);
		
		initView();
		setData();
		popupInputMethodWindow();
	}

	private void initView() {
		et_qty = (EditText) findViewById(R.id.et_qty);
		et_qty.setFocusable(true);
		et_qty.setFocusableInTouchMode(true);
		et_qty.requestFocus();
		tv_barcode = (TextView) findViewById(R.id.tv_barcode);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancl = (Button) findViewById(R.id.btn_cancl);
//		name = (TextView) findViewById(R.id.name);
		num = (TextView) findViewById(R.id.num);
		if (sp.getString(ConfigEntity.IsPutInNumKey, ConfigEntity.IsPutInNum).equals("0")) {
			num.setText(AlertUtil.getString(R.string.enter_quantity)+":");
		}else {
			num.setText(AlertUtil.getString(R.string.modify_quantity_after)+":");
		}
		
		char[] mychar1 = {'0','1','2','3','4','5','6','7','8','9'};
		char[] mychar2 = {'0','1','2','3','4','5','6','7','8','9','.'};
		final char[] mychar;
		if("1".equals(type) || "2".equals(type)){
			mychar = mychar2;
		} else {
			mychar = mychar1;
		}
		
//		et_qty.setKeyListener(new NumberKeyListener() {
//			
//			@Override
//			public int getInputType() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//			
//			@Override
//			protected char[] getAcceptedChars() {
//				return mychar;
//			}
//		});
	}

	private void setData() {
		tv_barcode.setText(strGdBar);
		btn_ok.setOnClickListener(this);
		btn_cancl.setOnClickListener(this);
		et_qty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE
						|| (event != null
								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
								.getAction() == KeyEvent.ACTION_DOWN)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_qty.getWindowToken(), 0);
					String strQty = et_qty.getText().toString().trim();
					keyDownOk();
					return true;
				}
				return false;
			}
		});
	}

	private void keyDownOk() {
		String strQty = et_qty.getText().toString().trim();
		if (strQty.length() < 1) {
			AlertUtil.showAlert(OutStockModityQtyActivity.this,R.string.warning, R.string.quantity_not_null,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
			et_qty.requestFocus();
			et_qty.selectAll();
			return;
		}
		try {
//			qty = Integer.parseInt(strQty);
//			if (ConfigEntity.UsingBarCode.equals("1")) {
//				if (qty > oldQty) {
//					AlertUtil.showAlert(ModityQtyActivity.this, "警告",
//							"启用唯一码时，数量只能改小。", "确定", new View.OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									// TODO Auto-generated method stub
//									AlertUtil.dismissDialog();
//								}
//							});
//					et_qty.requestFocus();
//					et_qty.selectAll();
//					return;
//				}
//			}
			//获取小数点的位置
			int i = strQty.indexOf(".");
			//目前编辑框控件（et_qty）已经设定只能填写0到9的数字。故不存在小数点的情况了，下面的判断结果都为i < 0。
			if (i < 0) {	//没有小数点，即为整数的情况
				Intent intent = new Intent(OutStockModityQtyActivity.this,
						OutStockDetailsActivity.class);
				intent.putExtra("CheckNum", strQty);
				setResult(Activity.RESULT_OK, intent);
				finish();
				//关闭软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
			} else {  	//有小数点	
				String[] arr = strQty.split("\\.");
				if (arr.length > 1) {
					if (arr[1].length() > 0) {
						if ("1".equals(type)) {
							if (arr[1].length() > 1) {
								AlertUtil.showAlert(OutStockModityQtyActivity.this,
										R.string.dialog_title, R.string.digit_system_one,
										R.string.ok, new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												AlertUtil.dismissDialog();
											}
										});
								return;
							} else {
								Intent intent = new Intent(
										OutStockModityQtyActivity.this,
										OutStockDetailsActivity.class);
								intent.putExtra("CheckNum", strQty);
								setResult(Activity.RESULT_OK, intent);
								finish();
								InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(getCurrentFocus()
										.getWindowToken(), 0);
							}
						} else if ("2".equals(type)) {
							if (arr[1].length() > 2) {
								AlertUtil.showAlert(OutStockModityQtyActivity.this,
										R.string.dialog_title, R.string.digit_system_two,
										R.string.ok, new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												AlertUtil.dismissDialog();
											}
										});
								return;
							} else {
								Intent intent = new Intent(
										OutStockModityQtyActivity.this,
										OutStockDetailsActivity.class);
								intent.putExtra("CheckNum", strQty);
								setResult(Activity.RESULT_OK, intent);
								finish();
								InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(getCurrentFocus()
										.getWindowToken(), 0);
							}
						}
					}
				} else {
					AlertUtil.showAlert(OutStockModityQtyActivity.this,
							R.string.dialog_title, R.string.quantity_error,
							R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method
									// stub
									AlertUtil.dismissDialog();
								}
							});
				}
//				if (GlobalRunConfig.GetInstance().iSingleBarVerify == 1
//						&& qty > 1) // 选中了唯一码的功能，数量修改只能修改为0或1，其实只能为0，因为本身的数量已经是1了
//				{
//					AlertUtil.showAlert(ModityQtyActivity.this, "警告",
//							"启用唯一码功能时，单个条码数量不能大于1!", "确定",
//							new View.OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									// TODO Auto-generated method stub
//									AlertUtil.dismissDialog();
//								}
//							});
//					et_qty.requestFocus();
//					et_qty.selectAll();
//					return;
//				} else {
//					Intent intent = new Intent(ModityQtyActivity.this,
//							GoodsDetailsActivity.class);
//					intent.putExtra("CheckNum", strQty);
//					setResult(Activity.RESULT_OK, intent);
//					finish();
//					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//					imm.hideSoftInputFromWindow(getCurrentFocus()
//							.getWindowToken(), 0);
//				}
			}
		} catch (Exception ex) {
			Utility.deBug(TAG,  R.string.modify_quantity+" Error:" + ex.getMessage());
			AlertUtil.showAlert(OutStockModityQtyActivity.this, R.string.dialog_title, R.string.quantity_error,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
			et_qty.requestFocus();
			et_qty.selectAll();
		} finally {
		}

	}

	/**
	 * 显示软键盘
	 */
	private void popupInputMethodWindow() {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) getSystemService(OutStockModityQtyActivity.INPUT_METHOD_SERVICE);
				// 显示软键盘
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 200);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_ok:
			keyDownOk();
			OutStockDetailsActivity.is_intercept = true;
			break;
		case R.id.btn_cancl:
			OutStockDetailsActivity.is_intercept = true;
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(OutStockModityQtyActivity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(OutStockModityQtyActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			Intent intent = new Intent(
					OutStockModityQtyActivity.this,
					OutStockDetailsActivity.class);
			setResult(RESULT_CANCELED, intent);
			
			finish();
			
			//List<CheckListEntity> list = new ArrayList<SQLStockDataSqlite.CheckListEntity>();
			//list = sqlStockDataSqlite.GetStaCheckPositionDetail(GoodsDetailsActivity.strShopID, GoodsDetailsActivity.strCheckID, GoodsDetailsActivity.strPositionID);
			//GoodsDetailsActivity.mListView.setAdapter(new GoodsDetailsListAdapter(ModityQtyActivity.this, list));
			OutStockDetailsActivity.tv_checknum.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedCheckSum(OutStockDetailsActivity.strShopID, OutStockDetailsActivity.strCheckID)));
//			InStockDetailsActivity.tv_qty.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedPositionSum(InStockDetailsActivity.strShopID, InStockDetailsActivity.strCheckID,InStockDetailsActivity.strPositionID)));
			OutStockDetailsActivity.is_intercept = true;
			
			break;

		}

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
		// TODO 自动生成的方法存根
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
		OutStockDetailsActivity.is_intercept = true;
	}
}