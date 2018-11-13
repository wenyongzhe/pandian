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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class InStockQueryGoodsSingleActivity extends Activity implements
		OnClickListener {
	private Button btn_ok, btn_cancl;
	private EditText et_goodsBar;
	private SharedPreferences sp;
	private SQLInStockDataSqlite sqlStockDataSqlite;
	private Handler handler = new Handler();
	private String strCheckID, strPositionID, strShopID;
	private TextView tv_checkID, tv_positionID, tv_barcode, tv_qty;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	private String strBar;
	private String strGdBar;
	private String strLengthCutOrKeepBarCode;
	private String strLengthCutOrKeepBarCodeNum;
	private String strBarCodeAuth;
	// ��ţ���λ�����ƣ���ɫ�����룬�۸񣬿�棬���࣬С�࣬��ɫID������ID����ţ�����
	private TextView tv_checkid, tv_positionid, tv_title, tv_color, tv_size,
			tv_price, tv_stockcount, tv_big, tv_small, tv_colorid, tv_sizeid,
			tv_style, tv_num;
	private TextView tv_title0, tv_color0, tv_size0, tv_price0, tv_stockcount0,
			tv_big0, tv_small0, tv_colorid0, tv_sizeid0, tv_style0, tv_num0;
	
	private TextView tv_bianhao_cx,tv_huowei_cx;
	
	private TextView[] arrTextViews = null;
	private TextView[] arrTextViews0 = null;
	private LinearLayout linear_num, linear_style, linear_title,
			linear_colorid, linear_color, linear_sizeid, linear_size,
			linear_big, linear_small, linear_stockcount, linear_price;
	private LinearLayout[] arrLinearLayout = null;
	private String[] arrTextDisplayItem;
	private LinearLayout ll_display_all;
	private String[] arrDisplayItem;
	private List<String> contentList = new ArrayList<String>();
	private String strBarCode;
	private GdInfo gdInnerCode = new GdInfo();
	private GdInfo gdOutterCode = new GdInfo();
	private GdInfo gdInfo = new GdInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CustomTitleBar
				.getTitleBar(InStockQueryGoodsSingleActivity.this, AlertUtil.getString(R.string.query_instock), false);
		setContentView(R.layout.activity_in_stock_query_goods_single);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strCheckID = bundle.getString("CheckID");
		strPositionID = bundle.getString("PositionID");
		strShopID = bundle.getString("ShopID");
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sqlStockDataSqlite = new SQLInStockDataSqlite(
				InStockQueryGoodsSingleActivity.this, true);
		scanOperate = new ScanOperate();
		scanOperate.onCreate(InStockQueryGoodsSingleActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);
		// ������֤--�Ƿ���֤/// �ֱ�Ϊ��1��֤��0Ϊ����֤��2Ϊ��ʾ
		strBarCodeAuth = sp.getString(ConfigEntity.BarCodeAuthKey, "");
		// �ڲ��ⲿ������   //0�ڲ��룬1�ⲿ��
		strBarCode = sp.getString(ConfigEntity.InOutCodeKey, "");
		initView();
		setData();
	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_color = (TextView) findViewById(R.id.tv_color);
		tv_size = (TextView) findViewById(R.id.tv_size);
		tv_qty = (TextView) findViewById(R.id.tv_qty);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_stockcount = (TextView) findViewById(R.id.tv_stockcount);
		tv_big = (TextView) findViewById(R.id.tv_big);
		tv_small = (TextView) findViewById(R.id.tv_small);
		tv_colorid = (TextView) findViewById(R.id.tv_colorid);
		tv_sizeid = (TextView) findViewById(R.id.tv_sizeid);
		tv_style = (TextView) findViewById(R.id.tv_style);
		tv_num = (TextView) findViewById(R.id.tv_num);

		linear_num = (LinearLayout) findViewById(R.id.line_num);
		linear_style = (LinearLayout) findViewById(R.id.line_style);
		linear_title = (LinearLayout) findViewById(R.id.line_title);
		linear_colorid = (LinearLayout) findViewById(R.id.line_colorid);
		linear_color = (LinearLayout) findViewById(R.id.line_color);
		linear_sizeid = (LinearLayout) findViewById(R.id.line_sizeid);
		linear_size = (LinearLayout) findViewById(R.id.line_size);
		linear_big = (LinearLayout) findViewById(R.id.line_big);
		linear_small = (LinearLayout) findViewById(R.id.line_small);
		linear_stockcount = (LinearLayout) findViewById(R.id.line_stockcount);
		linear_price = (LinearLayout) findViewById(R.id.line_price);

		tv_num0 = (TextView) findViewById(R.id.tv_num0);
		tv_style0 = (TextView) findViewById(R.id.tv_style0);
		tv_title0 = (TextView) findViewById(R.id.tv_title0);
		tv_colorid0 = (TextView) findViewById(R.id.tv_colorid0);
		tv_color0 = (TextView) findViewById(R.id.tv_color0);
		tv_sizeid0 = (TextView) findViewById(R.id.tv_sizeid0);
		tv_size0 = (TextView) findViewById(R.id.tv_size0);
		tv_big0 = (TextView) findViewById(R.id.tv_big0);
		tv_small0 = (TextView) findViewById(R.id.tv_small0);
		tv_stockcount0 = (TextView) findViewById(R.id.tv_stockcount0);
		tv_price0 = (TextView) findViewById(R.id.tv_price0);

		ll_display_all = (LinearLayout) findViewById(R.id.ll_display_all);
		et_goodsBar = (EditText) findViewById(R.id.et_check_barcode);
		tv_checkID = (TextView) findViewById(R.id.tv_checkid);
		tv_positionID = (TextView) findViewById(R.id.tv_positionid);
		tv_barcode = (TextView) findViewById(R.id.tv_barcode);
		tv_qty = (TextView) findViewById(R.id.tv_qty);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancl = (Button) findViewById(R.id.btn_cancl);
		
		tv_bianhao_cx = (TextView) findViewById(R.id.tv_bianhao_cx);
		tv_huowei_cx = (TextView) findViewById(R.id.tv_huowei_cx);
		initText();
	}
	/**��̬�޸ı�Ż�λ��ʾ*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_bianhao_cx.setText(importStrArrayList.get(1)+":");
		tv_huowei_cx.setText(importStrArrayList.get(2)+":");
	}
	
	private void setData() {
		// if(strBarCodeAuth.equals("1")){
		// ll_display_all.setVisibility(View.VISIBLE);
		// }else{
		// ll_display_all.setVisibility(View.GONE);
		// }
		tv_checkID.setText(strCheckID);
		tv_positionID.setText(strPositionID);
		// ����ɨ�������ʾѡ���������ֵ
		//	�ⲿ��,�ڲ���,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�
		arrDisplayItem = sp.getString(ConfigEntity.DisplayItemsInKey,
				ConfigEntity.DisplayItemsIn).split(",");
		// ��ʾѡ��ĵ��봮
		arrTextDisplayItem = sp.getString(ConfigEntity.ImportStrKey,
				ConfigEntity.ImportStr).split(",");
		ConfigureInterface();

		btn_ok.setOnClickListener(this);
		btn_cancl.setOnClickListener(this);
		et_goodsBar
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE
								|| (event != null
										&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event
										.getAction() == KeyEvent.ACTION_DOWN)) {
							InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									et_goodsBar.getWindowToken(), 0);
							strBar = et_goodsBar.getText().toString().trim();
							if (strBar.trim().length() < 1) {
								AlertUtil.showAlert(
										InStockQueryGoodsSingleActivity.this, R.string.dialog_title,
										R.string.barCode_not_empty, R.string.ok,
										new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method
												// stub
												AlertUtil.dismissDialog();
											}
										});
								return false;
							}
							KeyDownOK();
							return true;
						}
						return false;
					}
				});
	}

	/**
	 * 	������ͼ�ؼ�
	 */
	private void ConfigureInterface() {
		arrLinearLayout = new LinearLayout[arrDisplayItem.length - 2];
		arrLinearLayout[0] = linear_num;
		arrLinearLayout[1] = linear_style;
		arrLinearLayout[2] = linear_title;
		arrLinearLayout[3] = linear_colorid;
		arrLinearLayout[4] = linear_color;
		arrLinearLayout[5] = linear_sizeid;
		arrLinearLayout[6] = linear_size;
		arrLinearLayout[7] = linear_big;
		arrLinearLayout[8] = linear_small;
		arrLinearLayout[9] = linear_stockcount;
		arrLinearLayout[10] = linear_price;

		arrTextViews0 = new TextView[arrTextDisplayItem.length - 2];
		arrTextViews0[0] = tv_num0;
		arrTextViews0[1] = tv_style0;
		arrTextViews0[2] = tv_title0;
		arrTextViews0[3] = tv_colorid0;
		arrTextViews0[4] = tv_color0;
		arrTextViews0[5] = tv_sizeid0;
		arrTextViews0[6] = tv_size0;
		arrTextViews0[7] = tv_big0;
		arrTextViews0[8] = tv_small0;
		arrTextViews0[9] = tv_stockcount0;
		arrTextViews0[10] = tv_price0;
		for (int i = 0; i < arrTextDisplayItem.length; i++) {
			if (i > 1) {
				arrTextViews0[i - 2].setText(arrTextDisplayItem[i] + ":");
			}
		}

		arrTextViews = new TextView[arrDisplayItem.length - 2];
		arrTextViews[0] = tv_num;
		arrTextViews[1] = tv_style;
		arrTextViews[2] = tv_title;
		arrTextViews[3] = tv_colorid;
		arrTextViews[4] = tv_color;
		arrTextViews[5] = tv_sizeid;
		arrTextViews[6] = tv_size;
		arrTextViews[7] = tv_big;
		arrTextViews[8] = tv_small;
		arrTextViews[9] = tv_stockcount;
		arrTextViews[10] = tv_price;

		for (int m = 2; m < arrDisplayItem.length; m++) {
			if (arrDisplayItem[m].equals("1")) {
				arrLinearLayout[m - 2].setVisibility(View.VISIBLE);
				arrTextViews[m - 2].setSingleLine(false);
			} else if (arrDisplayItem[m].equals("2")) {
				arrLinearLayout[m - 2].setVisibility(View.VISIBLE);
				arrTextViews[m - 2].setSingleLine(true);
				arrTextViews[m - 2].setEllipsize(TextUtils.TruncateAt
						.valueOf("MARQUEE"));
			} else {
				arrLinearLayout[m - 2].setVisibility(View.GONE);
			}
		}

	}

	// / ִ�������ȡ��������
	// / <param name="fullBar"></param>
	// / <param name="bWithPrompt"></param>
	// / <param name="iRet">�Ƿ����ı��,1�ɹ���0ʧ��</param>
	// / <returns>���ؽ�ȡ��ͣ���������</returns>
	
	/**
	 * @param fullBar ִ��ǰ������
	 * @param bWithPrompt �Ƿ������ʾ��Ϣ,1���У�0������
	 * @return �Ƿ����ı��,1�ɹ���0ʧ��
	 * strGdBarΪ��ȥ����������� 
	 */
	public int ExecuteCutReserveBar(String fullBar, boolean bWithPrompt) {
		strGdBar = fullBar;
		int iRet = 1;
		strLengthCutOrKeepBarCode = sp.getString(
				ConfigEntity.LengthCutOrKeepBarCodeKey,
				ConfigEntity.LengthCutOrKeepBarCode);
		strLengthCutOrKeepBarCodeNum = sp.getString(
				ConfigEntity.LengthCutOrKeepBarCodeNumKey,
				ConfigEntity.LengthCutOrKeepBarCodeNum);
		// ��ȥ�������� "0������","1��ȥ�������λ��","2��ȥ����ǰ��λ��","3�����������λ��","4��������ǰ��λ��"
		if (strLengthCutOrKeepBarCode.equals("1")) // ��ȡ��λ��
		{
			if (Integer.parseInt(strLengthCutOrKeepBarCodeNum) >= strBar
					.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(InStockQueryGoodsSingleActivity.this, R.string.warning,
							R.string.inventory_code_length, R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				}
				iRet = 0;
				return 0;
			}
			strGdBar = strBar.substring(
					0,
					strBar.length()
							- Integer.parseInt(strLengthCutOrKeepBarCodeNum));
		} else if (strLengthCutOrKeepBarCode.equals("2")) // ��ȡǰλ��
		{
			if (Integer.parseInt(strLengthCutOrKeepBarCodeNum) >= strBar
					.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(InStockQueryGoodsSingleActivity.this, R.string.warning,
							R.string.inventory_code_length, R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});
				}
				iRet = 0;
				return 0;
			}
			strGdBar = strBar.substring(
					Integer.parseInt(strLengthCutOrKeepBarCodeNum),
					strBar.length()
							- Integer.parseInt(strLengthCutOrKeepBarCodeNum));
		} else if (strLengthCutOrKeepBarCode.equals("3")) // ������λ��
		{
			if (Integer.parseInt(strLengthCutOrKeepBarCodeNum) >= strBar
					.length()) {
				;
			} else {
				strGdBar = strBar
						.substring(
								strBar.length()
										- Integer
												.parseInt(strLengthCutOrKeepBarCodeNum),
								Integer.parseInt(strLengthCutOrKeepBarCodeNum));
			}
		} else if (strLengthCutOrKeepBarCode.equals("4")) // ����ǰλ��
		{
			if (Integer.parseInt(strLengthCutOrKeepBarCodeNum) >= strBar
					.length()) {
				;
			} else {
				strGdBar = strBar.substring(0,
						Integer.parseInt(strLengthCutOrKeepBarCodeNum));
			}
		}
		return iRet;
	}
	
	private void KeyDownOK() {
		int qty = 0;
		CheckDetail checkDetail = new CheckDetail();
		checkDetail.strShopID = strShopID;
		checkDetail.strCheckID = strCheckID;
		checkDetail.strPositionID = strPositionID;
		// ִ�������ȡ��������
		int iR = ExecuteCutReserveBar(strBar, false);
		et_goodsBar.setText(strGdBar);
		checkDetail.strGdBar = strGdBar;

		// ������֤--�Ƿ���֤/// �ֱ�Ϊ��1��֤��0Ϊ����֤��2Ϊ��ʾ
		strBarCodeAuth = sp.getString(ConfigEntity.BarCodeAuthKey, "");
		if (strBarCodeAuth.equals("1")) {	//������֤

			if (strBarCode.equals("1")) {   //�ⲿ��
				// ɨ���ⲿ���ȡ
				gdInnerCode = sqlStockDataSqlite.GetDataByBar(strGdBar);
				if (gdInnerCode != null) {
					strGdBar = gdInnerCode.getStrGdBarcode(); 
					gdInfo = gdInnerCode;
				}
				// ɨ���ڲ����ȡ
				gdOutterCode = sqlStockDataSqlite.GetDataByCode(strGdBar);
				if (gdOutterCode != null) {
					strGdBar = gdOutterCode.getStrGdBarcode();
					// strGdBar = listGdInfo2.get(0).getStrGdCode();
					gdInfo = gdOutterCode;
				}
			} else {	//�ڲ���
				// ɨ���ⲿ���ȡ
				gdInnerCode = sqlStockDataSqlite.GetDataByBar(strGdBar);
				if (gdInnerCode != null) {
					strGdBar = gdInnerCode.getStrGdCode();
					gdInfo = gdInnerCode;
				}
				// ɨ���ڲ����ȡ
				gdOutterCode = sqlStockDataSqlite.GetDataByCode(strGdBar);
				if (gdOutterCode != null) {
					strGdBar = gdOutterCode.getStrGdCode();
					// strGdBar = listGdInfo2.get(0).getStrGdCode();
					gdInfo = gdOutterCode;
				}
			

			}
			//��Ʒ��ϢΪ��ʱ
			if (gdInfo == null) {
				tv_qty.setText("0");
				contentList.clear();
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				setDisplayContent();
				AlertUtil.showToast("�޴�����", InStockQueryGoodsSingleActivity.this);

			} else {  //��Ʒ��Ϣ��Ϊ��
				
				qty = sqlStockDataSqlite.GetGoodsQty(strShopID, strCheckID,
						strPositionID, strGdBar);
				//��λ������Ϊ��
				if (qty != 0) {
					if (Utility.isInteger(qty)) {
						tv_qty.setText(String.valueOf((int) qty));
					} else {
						tv_qty.setText(String.valueOf(qty));
					}
					
					//��ȡCheckDetail����
					CheckDetail cd = sqlStockDataSqlite.GetSpeCheckRecord(
							strShopID, strCheckID, strPositionID, strGdBar);
					contentList.clear();
					// contentList.add(String.valueOf(cd.dCheckNum));
					contentList.add(cd.strGdArtNO);
					contentList.add(cd.strGdStyle);
					contentList.add(cd.strGdName);
					contentList.add(cd.strGdColorID);
					contentList.add(cd.strGdColorName);
					contentList.add(cd.strGdSizeID);
					contentList.add(cd.strGdSizeName);
					contentList.add(cd.strProperty1);
					contentList.add(cd.strProperty2);
					contentList.add(String.valueOf(cd.dStock));
					contentList.add(String.valueOf(cd.dGdPrice));
					setDisplayContent();
				} else {
					//��λ����Ϊ��
					contentList.clear();
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					contentList.add("");
					setDisplayContent();
					AlertUtil.showToast(AlertUtil.getString(R.string.barCode_not_exist), InStockQueryGoodsSingleActivity.this);
					tv_qty.setText("0");

				}
			}
		} else {	//���벻��֤����ʾʱ
			qty = sqlStockDataSqlite.GetGoodsQty(strShopID, strCheckID,
					strPositionID, strGdBar);
			//��λ������Ϊ��
			if (qty != 0) {
				if (Utility.isInteger(qty)) {
					tv_qty.setText(String.valueOf((int) qty));
				} else {
					tv_qty.setText(String.valueOf(qty));
				}

				CheckDetail cd = sqlStockDataSqlite.GetSpeCheckRecord(
						strShopID, strCheckID, strPositionID, strGdBar);
				contentList.clear();
				// contentList.add(String.valueOf(cd.dCheckNum));
				contentList.add(cd.strGdArtNO);
				contentList.add(cd.strGdStyle);
				contentList.add(cd.strGdName);
				contentList.add(cd.strGdColorID);
				contentList.add(cd.strGdColorName);
				contentList.add(cd.strGdSizeID);
				contentList.add(cd.strGdSizeName);
				contentList.add(cd.strProperty1);
				contentList.add(cd.strProperty2);
				contentList.add(String.valueOf(cd.dStock));
				contentList.add(String.valueOf(cd.dGdPrice));
				setDisplayContent();
			} else {  //��λ����Ϊ��
				contentList.clear();
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				contentList.add("");
				setDisplayContent();
				AlertUtil.showToast(AlertUtil.getString(R.string.barCode_not_exist), InStockQueryGoodsSingleActivity.this);
				tv_qty.setText("0");
			}

		}
		tv_barcode.setText(strGdBar);
		et_goodsBar.selectAll();
		et_goodsBar.requestFocus();

	}

	/**
	 * ����Ҫ��ʾ�Ŀؼ�
	 */
	private void setDisplayContent() {
		for (int i = 2; i < arrDisplayItem.length; i++) {
			if (arrDisplayItem[i].equals("1") || arrDisplayItem[i].equals("2")) {
				arrTextViews[i - 2].setText(contentList.get(i - 2));
			}
		}
		tv_title.setText(contentList.get(2));
	}

	/**
	 * ��ɨ������ݴ���
	 * @param code
	 */
	private void OnGetBarcode(String code) {
		if (code.trim().length() < 1) {
			return;
		}
		strBar = code.trim();
		et_goodsBar.setText(strBar);
		KeyDownOK();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_ok:
			strBar = et_goodsBar.getText().toString().trim();
			if (strBar.trim().length() < 1) {
				AlertUtil.showAlert(InStockQueryGoodsSingleActivity.this, R.string.dialog_title,
						R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method
								// stub
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
			KeyDownOK();
			//�ر������
			InputMethodManager inputMethodManager1 = (InputMethodManager) getSystemService(InStockQueryGoodsSingleActivity.INPUT_METHOD_SERVICE);
			inputMethodManager1.hideSoftInputFromWindow(et_goodsBar.getWindowToken(), 0);
			//GoodsDetailsActivity.is_intercept = true;
			break;
		case R.id.btn_cancl:
			InStockDetailsActivity.is_intercept = true;
			//�ر������
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(InStockQueryGoodsSingleActivity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(et_goodsBar.getWindowToken(), 0);
			finish();
			break;

		}

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ScanOperate.MESSAGE_TEXT:
				String code = (String) msg.obj;
				scanOperate.setVibratortime(50);
				scanOperate.mediaPlayer();
				// ɨ����ȡ���ݴ���
				OnGetBarcode(code);
				//�ر������
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et_goodsBar.getWindowToken(), 0);
				break;
			case 2:

				break;
			}

		}
	};

	@Override
	protected void onResume() {
		CustomTitleBar.setActivity(this);
		scanOperate.mHandler = mHandler;
		scanOperate.onResume(this);
		super.onResume();
	}

	public void onStop() {
		super.onStop();

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
	protected void onPause() {
		super.onPause();
		scanOperate.mHandler = null;
		scanOperate.onDestroy(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ����Activity&��ջ���Ƴ���Activity
		myApplication.getInstance().finishActivity(this);
		InStockDetailsActivity.is_intercept = true;
	}
}