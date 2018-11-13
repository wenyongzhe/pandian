package com.supoin.commoninventory.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.entity.ImportSet;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;

/**
 * �������ݲ�ѯ
 */
public class CheckBaseDataActivity extends Activity {
	private SharedPreferences sp;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private GdInfo mGdInfo;
	private EditText et_GdCode;
	private String strBarCode;
	private GdInfo gdInfoInnerCode = new GdInfo();
	private GdInfo gdInfoOutterCode = new GdInfo();
	private GdInfo gdInfo = new GdInfo();
	// private TextView tv_barCode;
	private String strGdBarcode, strGdName, strGdColorName, strGdSizeName;
	private int mGdStock;
	private float strGdPrice;
	private List<ImportSet> importSetList;
	private String[] importStrArr;// ԭʼ����
	private int[] importSetArr;// ԭʼ������ѡ�е�λ��
	private ImportSet importSet;
	// ����Ҫ��ʾ��Item
	private String[] strGdInfoArr;
	private LinearLayout linear_gdBarcode, linear_gdcode, linear_num,
			linear_style, linear_title, linear_colorid, linear_color,
			linear_sizeid, linear_size, linear_big, linear_small,
			linear_stockcount, linear_price;
	private LinearLayout[] arrLinearLayout = null;
	private TextView[] arrTextViews = null;
	private TextView[] arrTextViews0 = null;
	// ��ţ���λ�����ƣ���ɫ�����룬�۸񣬿�棬���࣬С�࣬��ɫID������ID����ţ�����
	private TextView tv_barcode, tv_gdcode, tv_title, tv_color, tv_size,
			tv_price, tv_stockcount, tv_big, tv_small, tv_colorid, tv_sizeid,
			tv_style, tv_num;
	private TextView tv_barcode0, tv_gdcode0, tv_title0, tv_color0, tv_size0,
	tv_price0, tv_stockcount0, tv_big0, tv_small0, tv_colorid0, tv_sizeid0,
	tv_style0, tv_num0;
	private TextView tv_scan_barCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(CheckBaseDataActivity.this, R.string.basic_data_query, false);
		sqlStockDataSqlite = new SQLStockDataSqlite(CheckBaseDataActivity.this,true);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sp.edit().putString(ConfigEntity.RefreshUIKey, "1").commit();
		setContentView(R.layout.activity_check_base_data);
		et_GdCode = (EditText) findViewById(R.id.et_GdCode);
		tv_scan_barCode = (TextView) findViewById(R.id.tv_scan_barCode);

		tv_barcode = (TextView) findViewById(R.id.tv_barcode);
		tv_gdcode = (TextView) findViewById(R.id.tv_gdcode);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_color = (TextView) findViewById(R.id.tv_color);
		tv_size = (TextView) findViewById(R.id.tv_size);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_stockcount = (TextView) findViewById(R.id.tv_stockcount);
		tv_big = (TextView) findViewById(R.id.tv_big);
		tv_small = (TextView) findViewById(R.id.tv_small);
		tv_colorid = (TextView) findViewById(R.id.tv_colorid);
		tv_sizeid = (TextView) findViewById(R.id.tv_sizeid);
		tv_style = (TextView) findViewById(R.id.tv_style);
		tv_num = (TextView) findViewById(R.id.tv_num);
		
		tv_barcode0 = (TextView) findViewById(R.id.tv_barcode0);
		tv_gdcode0 = (TextView) findViewById(R.id.tv_gdcode0);
		tv_title0 = (TextView) findViewById(R.id.tv_title0);
		tv_color0 = (TextView) findViewById(R.id.tv_color0);
		tv_size0 = (TextView) findViewById(R.id.tv_size0);
		tv_price0 = (TextView) findViewById(R.id.tv_price0);
		tv_stockcount0 = (TextView) findViewById(R.id.tv_stockcount0);
		tv_big0 = (TextView) findViewById(R.id.tv_big0);
		tv_small0 = (TextView) findViewById(R.id.tv_small0);
		tv_colorid0 = (TextView) findViewById(R.id.tv_colorid0);
		tv_sizeid0 = (TextView) findViewById(R.id.tv_sizeid0);
		tv_style0 = (TextView) findViewById(R.id.tv_style0);
		tv_num0 = (TextView) findViewById(R.id.tv_num0);

		linear_gdBarcode = (LinearLayout) findViewById(R.id.line_gdBarcode);
		linear_gdcode = (LinearLayout) findViewById(R.id.line_gdcode);
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

		scanOperate = new ScanOperate();
		scanOperate.onCreate(CheckBaseDataActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);

		String importStr = sp.getString(ConfigEntity.ImportStrKey,ConfigEntity.ImportStr);
		importStrArr = importStr.split(",");
		importSetArr = new int[importStrArr.length];
		importSet = new ImportSet();

		arrLinearLayout = new LinearLayout[importStrArr.length];
		arrLinearLayout[0] = linear_gdBarcode;
		arrLinearLayout[1] = linear_gdcode;
		arrLinearLayout[2] = linear_num;
		arrLinearLayout[3] = linear_style;
		arrLinearLayout[4] = linear_title;
		arrLinearLayout[5] = linear_colorid;
		arrLinearLayout[6] = linear_color;
		arrLinearLayout[7] = linear_sizeid;
		arrLinearLayout[8] = linear_size;
		arrLinearLayout[9] = linear_big;
		arrLinearLayout[10] = linear_small;
		arrLinearLayout[11] = linear_stockcount;
		arrLinearLayout[12] = linear_price;

		arrTextViews0 = new TextView[importStrArr.length];
		arrTextViews0[0] = tv_barcode0;
		arrTextViews0[1] = tv_gdcode0;
		arrTextViews0[2] = tv_num0;
		arrTextViews0[3] = tv_style0;
		arrTextViews0[4] = tv_title0;
		arrTextViews0[5] = tv_colorid0;
		arrTextViews0[6] = tv_color0;
		arrTextViews0[7] = tv_sizeid0;
		arrTextViews0[8] = tv_size0;
		arrTextViews0[9] = tv_big0;
		arrTextViews0[10] = tv_small0;
		arrTextViews0[11] = tv_stockcount0;
		arrTextViews0[12] = tv_price0;
		for (int i = 0; i < importStrArr.length; i++) {
			arrTextViews0[i].setText(importStrArr[i]+":");
		}
		
		arrTextViews = new TextView[importStrArr.length];
		arrTextViews[0] = tv_barcode;
		arrTextViews[1] = tv_gdcode;
		arrTextViews[2] = tv_num;
		arrTextViews[3] = tv_style;
		arrTextViews[4] = tv_title;
		arrTextViews[5] = tv_colorid;
		arrTextViews[6] = tv_color;
		arrTextViews[7] = tv_sizeid;
		arrTextViews[8] = tv_size;
		arrTextViews[9] = tv_big;
		arrTextViews[10] = tv_small;
		arrTextViews[11] = tv_stockcount;
		arrTextViews[12] = tv_price;

		importSetList = sqlStockDataSqlite.GetImportSetList();
		if (null != importSetList && 0 < importSetList.size()) {
			importSet = importSetList.get(0);
			importSetArr[0] = importSet.getiBarcode();
			importSetArr[1] = importSet.getiCode();
			importSetArr[2] = importSet.getiArtNO();
			importSetArr[3] = importSet.getiStyle();
			importSetArr[4] = importSet.getiName();
			importSetArr[5] = importSet.getiColorID();
			importSetArr[6] = importSet.getiColorName();
			importSetArr[7] = importSet.getiSizeID();
			importSetArr[8] = importSet.getiSizeName();
			importSetArr[9] = importSet.getiBig();
			importSetArr[10] = importSet.getiSmall();
			importSetArr[11] = importSet.getiStock();
			importSetArr[12] = importSet.getiPrice();

		}
		// ���ݵ������õ�Item��ʾ
		for (int i = 0; i < importSetArr.length; i++) {
			if (0 < importSetArr[i]) {
				if (i < 2) {
					arrLinearLayout[i].setVisibility(View.GONE);
				} else {
					arrTextViews[i].setText("");
				}
			} else {
				arrLinearLayout[i].setVisibility(View.GONE);
			}
		}
		et_GdCode.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
					strBarCode = et_GdCode.getText().toString().trim();
					if (TextUtils.isEmpty(strBarCode)) {
						AlertUtil.showAlert(CheckBaseDataActivity.this, R.string.dialog_title,
								R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
										et_GdCode.requestFocus();
									}
								});
						return false;
					} else {
						OnGetBarcode(strBarCode);
					}

					return true;
				}
				return false;
			}
		});
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
			Intent scannerIntent = new Intent(
					ScanOperate.SCN_CUST_ACTION_CANCEL);
			sendBroadcast(scannerIntent);
			// }
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	// �����������鷳�Ļ�,����д��BaseActivity��,��ÿ��ҳ�涼�̳�
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
				et_GdCode.selectAll();
				et_GdCode.requestFocus();
				break;
			case 2:

				break;
			}

		}
	};
	private String lengthCutOrKeepBarCode;
	private String lengthCutOrKeepBarCodeNum;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ����Activity&��ջ���Ƴ���Activity
		myApplication.getInstance().finishActivity(this);
		scanOperate.mHandler = null;
		scanOperate.onDestroy(this);
	}
	private void OnGetBarcode(String code) {
		// ��ȥ�������� "0������","1��ȥ�������λ��","2��ȥ����ǰ��λ��","3�����������λ��","4��������ǰ��λ��"
		lengthCutOrKeepBarCode=sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,"");
		lengthCutOrKeepBarCodeNum=sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,"");
		if (lengthCutOrKeepBarCode.equals("1")) // ��ȡ��λ��
		{
			code = code.substring(0, code.length() - Integer.parseInt(lengthCutOrKeepBarCodeNum));
		} else if (lengthCutOrKeepBarCode.equals("2")) // ��ȡǰλ��
		{
		
			code = code.substring(Integer.parseInt(lengthCutOrKeepBarCodeNum),code.length());
		} else if (lengthCutOrKeepBarCode.equals("3")) // ������λ��
		{
			code = code.substring(code.length()- Integer.parseInt(lengthCutOrKeepBarCodeNum),code.length());
		} else if (lengthCutOrKeepBarCode.equals("4")) // ����ǰλ��
		{
			code = code.substring(0,Integer.parseInt(lengthCutOrKeepBarCodeNum));
		}
		et_GdCode.setText(code);
		// ͨ���ⲿ���ȡ��Ʒ��Ϣ
		gdInfoInnerCode = sqlStockDataSqlite.GetDataByBar(code.trim());
		// ͨ���ڲ����ȡ��Ʒ��Ϣ
		gdInfoOutterCode = sqlStockDataSqlite.GetDataByCode(code.trim());
		gdInfo = null;
		if (gdInfoInnerCode != null) {
			gdInfo = gdInfoInnerCode;
		}
		if (gdInfoOutterCode != null ) {
			gdInfo = gdInfoOutterCode;
		}

		if (gdInfo != null) {
			mGdInfo = gdInfo;
			strGdInfoArr = new String[importSetArr.length];
			strGdInfoArr[0] = mGdInfo.getStrGdBarcode();
			strGdInfoArr[1] = mGdInfo.getStrGdCode();
			strGdInfoArr[2] = mGdInfo.getStrGdArtNO(); 
			strGdInfoArr[3] = mGdInfo.getStrGdStyle();
			strGdInfoArr[4] = mGdInfo.getStrGdName();
			strGdInfoArr[5] = mGdInfo.getStrGdColorID();
			strGdInfoArr[6] = mGdInfo.getStrGdColorName();
			strGdInfoArr[7] = mGdInfo.getStrGdSizeID();
			strGdInfoArr[8] = mGdInfo.getStrGdSizeName();
			strGdInfoArr[9] = mGdInfo.getStrProperty1();
			strGdInfoArr[10] = mGdInfo.getStrProperty2();
			strGdInfoArr[11] = mGdInfo.getdGdStock() + "";
			strGdInfoArr[12] = mGdInfo.getdGdPrice() + "";
			tv_scan_barCode.setText(code);
			//���ݵ������õ�Item��ʾ
			for (int i = 0; i < strGdInfoArr.length; i++) {
				if (0 < importSetArr[i]) {
					if (i < 2) {
						arrLinearLayout[i].setVisibility(View.GONE);
					} else {
						arrTextViews[i].setText(strGdInfoArr[i]);
					}
				} else {
					arrLinearLayout[i].setVisibility(View.GONE);
				}
			}

		} else {
			AlertUtil.showAlert(CheckBaseDataActivity.this, R.string.dialog_title, R.string.barCode_not_exist, R.string.ok,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							// ���ݵ������õ�Item��ʾ
							for (int i = 0; i < importSetArr.length; i++) {
								if (0 < importSetArr[i]) {
									if (i < 2) {
										arrLinearLayout[i].setVisibility(View.GONE);
									} else {
										arrTextViews[i].setText("");
									}
								} else {
									arrLinearLayout[i].setVisibility(View.GONE);
								}
							}
							tv_scan_barCode.setText("");
						}
					});

		}
	}
	public void onStop() {
		super.onStop();
		// close scanner
		scanOperate.onStop(this);
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

}
