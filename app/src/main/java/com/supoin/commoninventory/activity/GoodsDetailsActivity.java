package com.supoin.commoninventory.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.GoodsDetailsListAdapter;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Constants;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.Utility;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.Common.InventoryBuffer;

import java.util.ArrayList;
import java.util.List;

import static com.uhf.uhf.Common.Comm.UHF1MESSAGE_TEXT;
import static com.uhf.uhf.Common.Comm.UHF5MESSAGE_TEXT;

public class GoodsDetailsActivity extends BaseActivity implements OnClickListener {
	// 此状态用来判断当启用提示时,没有启用唯一码时候,扫描不在导入资料内条码时情况的判断
	String status = "0";
	//isScan的作用是判断是否是条码扫描的方式
	boolean isScan = false;
	int item = -1;
	boolean flag = false;
	boolean tempStatus = false;
	int globaStatus;
	//货位
	public static String strPositionID = "";
	//编号
	public static String strCheckID = "";
	//条码(唯一码)
	public static String strSingleGdBar = "";
	//条码
	public static String strGdBar = "";
	public static String strBar = "";
	private Button btn_query, btn_modify, btn_switch,btn_insert;
	private EditText et_barcode;
	// 编号，货位，名称，颜色，尺码，价格，库存，大类，小类，颜色ID，尺码ID，款号，货号
	private TextView tv_checkid, tv_positionid, tv_title, tv_color, tv_size,
			tv_price, tv_stockcount, tv_big, tv_small, tv_colorid, tv_sizeid,
			tv_style, tv_num;
	private TextView tv_title0, tv_color0, tv_size0, tv_price0, tv_stockcount0,
			tv_big0, tv_small0, tv_colorid0, tv_sizeid0, tv_style0, tv_num0;
	// 货位数量，编号数量
	public static TextView tv_qty, tv_checknum;
	//textview 修改
	private TextView tv_bianhao_xq,tv_huowei_xq,tv_bianhaosl,tv_huoweisl;
	//是否消减
	private CheckBox cbDecrea;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private SharedPreferences sp;
	//商店编号
	public static String strShopID;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	// 由于在其它页面扫描 查询时会添加到这个activity的方法
	public static Boolean is_intercept = true;
	public static ListView mListView;
	public static GoodsDetailsListAdapter mGoodsDetailsListAdapter;
	private List<CheckListEntity> mCheckDetailList = new ArrayList<CheckListEntity>();
	private String lengthLimit, barCodeCutSetting;
	private int strMin, strMax;
	private List<String> strList;
	public GdInfo gdInfo = new GdInfo();
	public CheckDetail checkDetail = new CheckDetail();
	public int dCheckSum = 0;
	public int dPositionSum = 0;
	private static final String TAG = "GoodsDetailsActivity";
	private String[] arrDisplayItem;
	private List<CheckDetail> listTmp = new ArrayList<CheckDetail>();
	// 货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格";
	private TextView[] arrTextViews = null;
	private TextView[] arrTextViews0 = null;
	private LinearLayout linear_num, linear_style, linear_title,
			linear_colorid, linear_color, linear_sizeid, linear_size,
			linear_big, linear_small, linear_stockcount, linear_price,billscan_linear3,billscan_linear2;
	private View view_line;
	private LinearLayout[] arrLinearLayout = null;
	private List<String> contentList = new ArrayList<String>();
	public static final int REQUEST_INPUTPSW_CODE = 1;
	public static final int REQUEST_QTY_CODE = 2;
	public static final int REQUEST_INPUTQTY_CODE = 3;
	private int itemSelected = 0;
	private int oldQty = 0, newQty = 0;
	public String tempBarCode;
	private String[] arrTextDisplayItem;
	private int selectNumber;
	protected String strBarCode;
	/**是否点击修改数量按钮  默认为不是*/
	private boolean isBtn = false;
	//bug1365
	/**是否输入密码  默认为不是 用来限制onResume()刷新列表*/
	private boolean isPass = false;

	private String barCodeAuth;
	private ProgressDialog rfidDialog;
	private String inOutCode;
	private boolean isRFID;
	private List<String> rfidList = new ArrayList<String>();

	private boolean bRfidStoped = false;
	/**基础标签*/
	private List<String> checkDetailStrList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CustomTitleBar.getTitleBar(GoodsDetailsActivity.this, R.string.goods_scan, false);
		setContentView(R.layout.activity_goods_details);
		sqlStockDataSqlite = new SQLStockDataSqlite(GoodsDetailsActivity.this,true);
		//初始化sp
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		//获取商店编号
		if (sp.getString(ConfigEntity.ERPKey, ConfigEntity.ERP).equals(ConfigurationKeys.GJP)) {
			strShopID = sp.getString(ConfigEntity.GraspInfoKey,ConfigEntity.GraspInfo).split(",")[0];
		} else {
			if (sp.getString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID).equals("000000"))
				strShopID = "000000";	//门店编号
			else
				strShopID = sp.getString(ConfigEntity.ShopIDKey,ConfigEntity.ShopID);
		}

		//验证方式
		barCodeAuth = sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth);
		// 内部码或外部码 0内部码，1外部码
		inOutCode = sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode);
		//扫描模式 0条码，1RFID
		isRFID = sp.getString(ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern).equals("1");

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		//获取扫描单据页面输入的编号
		strCheckID = bundle.getString("strCheckID");
		//获取扫描单据页面输入的货位号
		strPositionID = bundle.getString("strPositionID");
		scanOperate = new ScanOperate();
		scanOperate.onCreate(GoodsDetailsActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);
		InitInterface();
		initView();
		setData();

		//1为RFID
		Intent intent0 = getIntent();
		if (Utility.isLowPower(intent0)) {
			bRFID = false;
			bEnableRFID = false;
		}

		if (bEnableRFID) {
			if(isRFID){//RFID ，隐藏商品属性控件，单品查询、修改数量按钮
				bRFID = true;
				billscan_linear3.setVisibility(View.GONE);
				billscan_linear2.setVisibility(View.GONE);
				view_line.setVisibility(View.GONE);
				btn_modify.setVisibility(View.GONE);
				btn_query.setVisibility(View.GONE);
			}else{
				bRFID = false;
			}
		}
	}
	//扫描条码后接受到的回调
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ScanOperate.MESSAGE_TEXT:
				String code = (String) msg.obj;
				scanOperate.setVibratortime(50);
				scanOperate.mediaPlayer();
				isScan = true;
				Utility.deBug("条码", code);
				OnGetBarcode(code);
				break;
			case UHF5MESSAGE_TEXT:
			case UHF1MESSAGE_TEXT:
				if (!bRfidStoped)
					return;

				bRfidStoped = false;
				rfidList.clear();
				List<InventoryBuffer.InventoryTagMap> tagLists = new ArrayList<InventoryBuffer.InventoryTagMap>();
				tagLists.addAll(Comm.lsTagList);
				if (tagLists.size() < 1)
					return;

				for(InventoryBuffer.InventoryTagMap item : tagLists){
					if (item.strEPC != null && !item.strEPC.trim().equals(""))
						rfidList.add(item.strEPC.trim().replace(" ", ""));
				}

				int size = rfidList.size();
				if (0 == size) {
					return;
				}
				loadingData();
				Utility.deBug("条码size", size+"");
				break;
			case 4:
				mCheckDetailList.clear();
				mCheckDetailList = sqlStockDataSqlite.GetRFIDCheckPositionDetail(strShopID, strCheckID, strPositionID);
				if(mGoodsDetailsListAdapter!=null){
					if(mCheckDetailList.size()>0){
						mGoodsDetailsListAdapter.setList(mCheckDetailList);
					}
				}else{
					mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(GoodsDetailsActivity.this, mCheckDetailList);
				}
				mListView.setAdapter(mGoodsDetailsListAdapter);
				mGoodsDetailsListAdapter.notifyDataSetChanged();
				tv_checknum.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
				tv_qty.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedPositionIdSum(strShopID, strCheckID, strPositionID)));
				if(rfidDialog!=null){
					if(rfidDialog.isShowing()){
						rfidDialog.dismiss();
					}
				}
				break;
			}
		}
	};

	//TODO 处理扫描到的RFID数据
	protected void loadingData() {
		rfidDialog = AlertUtil.showNoButtonProgressDialog(
				GoodsDetailsActivity.this, AlertUtil.getString(R.string.operate_check));
		smallExecutor.execute(new Runnable() {
			private List<GdInfo> checkInfoList;

			public void run() {
				if (barCodeAuth.equals("1")){//验证
					List<String> rfidRightList = new ArrayList<>();
					for(String item:rfidList){
						if (checkDetailStrList.contains(item)) {
							rfidRightList.add(item);
						}
					}

					checkInfoList = sqlStockDataSqlite.getCheckInfoList(rfidRightList,inOutCode);
					sqlStockDataSqlite.InsertCheckDetailTempRFIDVer(checkInfoList,strShopID,strCheckID,strPositionID,inOutCode);


				}else{
					sqlStockDataSqlite.InsertCheckDetailTempRFID(rfidList,strShopID,strCheckID,strPositionID);
				}

				sqlStockDataSqlite.InsertCheckDetailRFID();
				mHandler.sendEmptyMessage(4);
			}
		});
	}

	private void initView() {
		et_barcode = (EditText) findViewById(R.id.et_barcode);//条码
		tv_checkid = (TextView) findViewById(R.id.tv_checkid);//编号
		tv_positionid = (TextView) findViewById(R.id.tv_positionid);//货位号
		tv_title = (TextView) findViewById(R.id.tv_title);//名称
		tv_color = (TextView) findViewById(R.id.tv_color);//颜色
		mListView = (ListView) findViewById(R.id.listView);//数据展示listview
		tv_size = (TextView) findViewById(R.id.tv_size);//尺码
		tv_qty = (TextView) findViewById(R.id.tv_qty);//货位数量
		tv_checknum = (TextView) findViewById(R.id.tv_checknum);//编号数量
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_stockcount = (TextView) findViewById(R.id.tv_stockcount);
		tv_big = (TextView) findViewById(R.id.tv_big);
		tv_small = (TextView) findViewById(R.id.tv_small);
		tv_colorid = (TextView) findViewById(R.id.tv_colorid);
		tv_sizeid = (TextView) findViewById(R.id.tv_sizeid);
		tv_style = (TextView) findViewById(R.id.tv_style);
		tv_num = (TextView) findViewById(R.id.tv_num);
		cbDecrea = (CheckBox) findViewById(R.id.cbDecrea);//消减勾选框
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
		billscan_linear3 = (LinearLayout) findViewById(R.id.billscan_linear3);
		billscan_linear2 = (LinearLayout) findViewById(R.id.billscan_linear2);
		view_line = (View) findViewById(R.id.view_line);

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

		btn_query = (Button) findViewById(R.id.btn_query);//单品查询
		btn_modify = (Button) findViewById(R.id.btn_modify);//修改数量
		btn_switch = (Button) findViewById(R.id.btn_switch);//消减切换
		btn_insert = (Button) findViewById(R.id.btn_insert);//消减切换

		tv_checkid.setText(strCheckID);
		tv_positionid.setText(strPositionID);

		tv_bianhao_xq = (TextView) findViewById(R.id.tv_bianhao_xq);//编号textview
		tv_huowei_xq = (TextView) findViewById(R.id.tv_huowei_xq);//货位textview
		tv_bianhaosl = (TextView) findViewById(R.id.tv_bianhaosl);//编号数量textview
		tv_huoweisl = (TextView) findViewById(R.id.tv_huoweisl);//货位数量textview
		initText();
	}

	/**动态修改编号货位提示*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		tv_bianhao_xq.setText(importStrArrayList.get(1)+":");
		tv_huowei_xq.setText(importStrArrayList.get(2)+":");
		tv_bianhaosl.setText(importStrArrayList.get(1)+AlertUtil.getString(R.string.quantity));
		tv_huoweisl.setText(importStrArrayList.get(2)+AlertUtil.getString(R.string.quantity));
	}

	private void LoadData() {
		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
					GoodsDetailsActivity.this, AlertUtil.getString(R.string.loading_data));

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Object doInBackground(Void... params) {
				try {
					scannerInited = true;
					String result = "false";
					CheckDetail cde = new CheckDetail();
					cde.strShopID = strShopID;
					cde.strCheckID = strCheckID;
					cde.strPositionID = strPositionID;
					if(isRFID){
						checkDetailStrList = sqlStockDataSqlite.getGdInfoBarcodeList(inOutCode);
					}

					// 判断显示方式，待处理或不处理，待确定...
					listTmp = sqlStockDataSqlite.GetSpecifiedCounterRecords(
							Integer.parseInt(sp.getString(
									ConfigEntity.ScanningShowLineNumbKey,
									ConfigEntity.ScanningShowLineNumb)),
							strShopID, strCheckID, strPositionID);
					// 根据当前门店号，编号，货位号获取总共的数量
					dCheckSum = sqlStockDataSqlite.GetSpecifiedCheckIDSum(
							strShopID, strCheckID, strPositionID);
					result = "true";
					return result;
				} catch (Exception e) {
					Utility.deBug(TAG, "startExport" + e.getMessage());
					e.printStackTrace();
					return e;
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				dialog.dismiss();
				if (!TextUtils.isEmpty(result.toString())) {
					if (result.toString().equals("true")) {
						AlertUtil.showToast(AlertUtil.getString(R.string.load_success), GoodsDetailsActivity.this);
						myHandler.sendEmptyMessage(1);

					} else {
						AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
								AlertUtil.getString(R.string.load_failed), R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {
					AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
							AlertUtil.getString(R.string.load_failed), R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
				}
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
			}
		}.execute();
	}

	public void InitInterface() {
		// strArr ="外部码,内部码,货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格";
		String[] strArr = sp.getString(ConfigEntity.ImportStrKey,
				ConfigEntity.ImportStr).split(",");
		// "门店编号,编号,货位,条码,货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格,数量,时间";
		sp.getString(ConfigEntity.ExportStrKey, ConfigEntity.ExportStr).split(",");
		if (strArr.length < 13) {
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
					R.string.system_import_error, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}

		// 条码,数量,货号,款号,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格
		LoadData();
	}

	private void setData() {

		// 读取系统设置
		GetSystemSetting();

		//设置
		ConfigureInterface();
		btn_query.setOnClickListener(this);
		btn_query.setFocusable(false);
		btn_modify.setOnClickListener(this);
		btn_insert.setOnClickListener(this);

		//获取集合
		mCheckDetailList.clear();
		if(isRFID){
			mCheckDetailList = sqlStockDataSqlite.GetRFIDCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}else{
			mCheckDetailList = sqlStockDataSqlite.GetStaCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}
		// 当设置条码验证方式为提示的时候,并且没有启用唯一码时
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
			mCheckDetailList = singleElement(mCheckDetailList);
		}

		if (mCheckDetailList != null && mCheckDetailList.size() > 0) {
			mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(GoodsDetailsActivity.this, mCheckDetailList);
			mListView.setAdapter(mGoodsDetailsListAdapter);
		}

		dCheckSum = sqlStockDataSqlite.GetSpecifiedCheckIDSum(strShopID,
				strCheckID, strPositionID);
		dPositionSum = sqlStockDataSqlite.GetSpecifiedPositionIdSum(strShopID,
				strCheckID, strPositionID);
		tv_qty.setText(String.valueOf(dPositionSum));
		tv_checknum.setText(String.valueOf(dCheckSum));
		mListView.setOnItemClickListener(new OnItemClickListener() {
			// 点击单个商品查询
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				item = arg2;
				itemSelected = arg2;
				mListView.setSelection(arg2);
				selectNumber = mGoodsDetailsListAdapter.getItem(arg2).dNum;
				strBarCode = mGoodsDetailsListAdapter.getItem(arg2).strBar;
				mGoodsDetailsListAdapter.setItemSelected(arg2);
				mGoodsDetailsListAdapter.notifyDataSetInvalidated();
				CheckDetail cd = sqlStockDataSqlite.GetSpeCheckRecord(
						strShopID, strCheckID, strPositionID,
						mCheckDetailList.get(arg2).strBar);
				contentList.clear();
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

				// 为0是不验证条码
				if (sp.getString(ConfigEntity.BarCodeAuthKey,
						ConfigEntity.BarCodeAuth).equals("0")) {

					// 当验证的时候,点击条目,才会显示出相关属性信息
				} else {
					CheckListEntity checkListEntity = (CheckListEntity) arg0
							.getItemAtPosition(arg2);
					CheckDetail checkDetail = sqlStockDataSqlite
							.getDetailData(checkListEntity.getStrBar());// 通过条码获取CheckDetail中的数据(比如名称，颜色，尺码等)

					if (checkDetail != null) {
						if (checkDetail.getStrGdName() != null) {
							tv_title.setText(checkDetail.getStrGdName());
						}
						if (checkDetail.getStrGdStyle() != null) {
							tv_style.setText(checkDetail.getStrGdStyle());
						}
						if (checkDetail.getStrGdColorName() != null) {
							tv_color.setText(checkDetail.getStrGdColorName());
						}
						if (checkDetail.getStrGdSizeName() != null) {
							tv_size.setText(checkDetail.getStrGdSizeName());
						}
						if (checkDetail.getStrGdSizeID() != null) {
							tv_sizeid.setText(checkDetail.getStrGdSizeID());
						}
						if (checkDetail.getStrProperty1() != null) {
							tv_big.setText(checkDetail.getStrProperty1());
						}
						if (checkDetail.getStrProperty2() != null) {
							tv_small.setText(checkDetail.getStrProperty2());
						}
						if (checkDetail.getStrGdColorID() != null) {
							tv_colorid.setText(checkDetail.getStrGdColorID());
						}
						tv_price.setText(checkDetail.getStrPrice()+"");
						tv_stockcount.setText(String.valueOf(checkDetail
								.getStrStock()));
						tv_num.setText(String.valueOf(checkDetail
								.getStrGdArtNO()));
					}

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
				}

			}
		});
		// 给CheckBox设置事件监听
		cbDecrea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbDecrea.setChecked(true);
				} else {
					cbDecrea.setChecked(false);
				}
			}
		});
		//TODO RFID
		et_barcode.setOnEditorActionListener(new OnEditorActionListener() { //条码编辑完之后点击软键盘上的回车键才会触发
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
				if(!isRfidStart){//开启RFID时，不允许操作
					strBar = et_barcode.getText().toString().trim();
					if (actionId == EditorInfo.IME_ACTION_SEND ||
			                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
			            switch (event.getAction()) {
			                case KeyEvent.ACTION_UP:
			                	if(TextUtils.isEmpty(strBar)){
			                		AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
										R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												AlertUtil.dismissDialog();
												et_barcode.requestFocus();
											}
										});
			                		return false;
			                	}else {
		                			if(!isScan){//不是扫描
		                				et_barcode.setText(strBar.trim());
		                				et_barcode.setSelection(strBar.length());
		                				//当手动输入条码的时候
		                				if(isRFID){
		                					insertRFID(strBar);
		                				}else{
		                					KeyDownOK();
		                				}
			                		}
			                		isScan = false;

									}
			                default:
			                    return true;
			            }
					}else if (actionId == EditorInfo.IME_ACTION_DONE) {
						 if (TextUtils.isEmpty(strBar)) {
								AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
										R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												AlertUtil.dismissDialog();
												et_barcode.requestFocus();
											}
										});
								return false;
							} else {
								if(!isScan){
	                				et_barcode.setText(strBar.trim());
	                				et_barcode.setSelection(strBar.length());
	                				//当手动输入条码的时候
	                				if(isRFID){
	                					insertRFID(strBar);
	                				}else{
	                					KeyDownOK();
	                				}
		                		}
								isScan = false;
							}

			            }


				}else{
					et_barcode.requestFocus();
				}
				return true;
			}


		});
	}
	private void insertRFID(final String strBar) {
		smallExecutor.execute(new Runnable() {
			private List<GdInfo> checkInfoList;
			public void run() {
				rfidList.clear();
				rfidList.add(strBar);
				boolean isExit = sqlStockDataSqlite.checkRFIDCodeByCheckDetail(strBar,strShopID,strCheckID,strPositionID);
				if(isExit){//已存在该标签
					AlertUtil.showToast(R.string.RFID_HAS_EXIST, GoodsDetailsActivity.this);
				}else{
					if (barCodeAuth.equals("1")){//验证
						checkInfoList = sqlStockDataSqlite.getCheckInfoList(rfidList,inOutCode);
						if(checkInfoList==null){//判断标签是否在基础资料中
							AlertUtil.showToast(R.string.RFID_NOT_EXIST, GoodsDetailsActivity.this);
							rfidList.clear();
							rfidDialog.dismiss();
							return;
						}
						sqlStockDataSqlite.InsertCheckDetailTempRFIDVer(checkInfoList,strShopID,strCheckID,strPositionID,inOutCode);
					}else{
						sqlStockDataSqlite.InsertCheckDetailTempRFID(rfidList,strShopID,strCheckID,strPositionID);
					}

					sqlStockDataSqlite.InsertCheckDetailRFID();
					mHandler.sendEmptyMessage(4);
				}
			}
		});
	}
	/*输入密码不执行刷新列表的方法*/
	private boolean isPassAct= false;
	@Override
	public void onClick(View v) {
	   switch (v.getId()) {
	      case R.id.btn_modify:
	    		if(!isRfidStart){
					if(mCheckDetailList.size()-1 < itemSelected){//bug1336，使用itemSelected，导致越界
						itemSelected = 0;
					}
					if (sp.getString(ConfigEntity.ModifyNumPWKey,ConfigEntity.ModifyNumPW).equals("1")) {
						//bug1364 没有商品扫描时，不允许修改数量
						if(mCheckDetailList.size() > 0){
							isPassAct = true;
							Intent intent = new Intent(GoodsDetailsActivity.this,PasswordInputScanGoodActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
							intent.putExtras(bundle);
							startActivityForResult(intent, 10);
						}else{
							AlertUtil.showAlert(GoodsDetailsActivity.this, AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.no_goods_scan));
						}
					} else {
						isBtn = true;
						openModifyQty(true);
					}
				}
	    	  break;
	      case R.id.btn_query:
	    	  if(!isRfidStart){
					openQueryGoods();
				}
	    	  break;
	      case R.id.btn_switch:
	    	  btnSwitch_Click();
	    	  break;
	    	  default :
	    		  break;
	   }
	}

	/**
	 * 单品查询
	 */
	private void openQueryGoods() {
		is_intercept = false;
		if (GlobalRunConfig.GetInstance().iIsF17 == 1) {
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title,
					R.string.no_goods_query, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		Intent intent = new Intent(GoodsDetailsActivity.this,
				QueryGoodsSingleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("CheckID", strCheckID);
		bundle.putString("PositionID", strPositionID);
		bundle.putString("ShopID", strShopID);
		intent.putExtras(bundle);
		startActivity(intent);
	}
/**
 * 消减勾选框状态切换
 */
	private void btnSwitch_Click() {

		if (sp.getString(ConfigEntity.UsingBarCodeKey,
				ConfigEntity.UsingBarCode).equals("1")) {
			if (cbDecrea.isChecked()) {
				cbDecrea.setChecked(false);
			} else {
				cbDecrea.setChecked(true);
			}
			cbDecrea.notifyAll();
			return;
		}
	}
/**
 * 修改数量
 * @param isSaved
 */
	private void openModifyQty(Boolean isSaved) {
		is_intercept = false;
		if (mCheckDetailList.size() > 0) {
			if(!isBtn){//bug1369 使用itemSelected，导致越界
				itemSelected = 0;
			}
			oldQty = mCheckDetailList.get(itemSelected).dNum;
			if (isSaved) {
				strGdBar = mCheckDetailList.get(itemSelected).strBar;
				Intent intent = new Intent();
				intent.setClass(GoodsDetailsActivity.this,ModityQtyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("GdBar", strGdBar);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_QTY_CODE);
			} else {
				if (mCheckDetailList.size() == 0) {
					Intent intent = new Intent();
					intent.setClass(GoodsDetailsActivity.this,ModityQtyActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("GdBar", strGdBar);
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUEST_QTY_CODE);
				} else {
					Intent intent = new Intent();
					intent.setClass(GoodsDetailsActivity.this,ModityQtyActivity.class);
					Bundle bundle = new Bundle();
					if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
						if(isBtn){//bug1356
							strGdBar = mCheckDetailList.get(itemSelected).strBar;
						}
						bundle.putString("GdBar", strGdBar);
					} else {
						strGdBar = mCheckDetailList.get(itemSelected).strBar;
						bundle.putString("GdBar", strGdBar);
					}
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUEST_QTY_CODE);
				}

			}
		} else {
			if (isSaved) {
				isPass = false;
				AlertUtil.showAlert(GoodsDetailsActivity.this, AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.no_goods_scan));
			} else {
				Intent intent = new Intent();
				intent.setClass(GoodsDetailsActivity.this,ModityQtyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("GdBar", strGdBar);
				// 6915349717011
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_QTY_CODE);
			}
		}
	}

	private void ConfigureInterface() {
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) { // 1启用唯一码
			cbDecrea.setVisibility(View.VISIBLE);
			cbDecrea.setEnabled(true);
			btn_switch.setVisibility(View.GONE);
			btn_modify.setVisibility(View.GONE);
		} else {										//0不启用唯一码
			cbDecrea.setVisibility(View.GONE);
			cbDecrea.setEnabled(false);
			btn_switch.setVisibility(View.GONE);
			btn_modify.setVisibility(View.VISIBLE);
		}
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
			} else if (arrDisplayItem[m].equals("2")) {
				arrLinearLayout[m - 2].setVisibility(View.VISIBLE);

			} else {
				arrLinearLayout[m - 2].setVisibility(View.GONE);
			}
		}

	}

	private void GetSystemSetting() {
		strList = new ArrayList<String>();
		// 获取长度设置( 0不限制长度，1等于限制，2范围长度)
		lengthLimit = sp.getString(ConfigEntity.LengthLimitKey,ConfigEntity.LengthLimit);
		if (lengthLimit.equals("1")) {
			// 等于限制
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit1Key,
					ConfigEntity.LengthEqualToLimit1));
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit2Key,
					ConfigEntity.LengthEqualToLimit2));
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit3Key,
					ConfigEntity.LengthEqualToLimit3));
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit4Key,
					ConfigEntity.LengthEqualToLimit4));
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit5Key,
					ConfigEntity.LengthEqualToLimit5));
			strList.add(sp.getString(ConfigEntity.LengthEqualToLimit6Key,
					ConfigEntity.LengthEqualToLimit6));
		} else if (lengthLimit.equals("2")) {
			// 限制范围
			strMin = Integer.parseInt(sp.getString(
					ConfigEntity.LengthLimitRangeMinKey,
					ConfigEntity.LengthLimitRangeMin));
			strMax = Integer.parseInt(sp.getString(
					ConfigEntity.LengthLimitRangeMaxKey,
					ConfigEntity.LengthLimitRangeMax));
		}
		// 获取拆分设置
		barCodeCutSetting = sp.getString(ConfigEntity.BarCodeCutSettingKey,ConfigEntity.BarCodeCutSetting);

		arrDisplayItem = sp.getString(ConfigEntity.DisplayItemsKey,ConfigEntity.DisplayItems).split(",");
		arrTextDisplayItem = sp.getString(ConfigEntity.ImportStrKey,ConfigEntity.ImportStr).split(",");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if (bRFID) {
				if (isRfidStart) {
					bRfidStoped = false;
					Comm.Awl.ReleaseWakeLock();
			        Comm.stopScan();

					pressCount = 0;
					isRfidStart = false;
				}
			}
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
		if (event.getScanCode() == 249 || event.getScanCode() == 261) {
			if (is_canScan == false) {
				scanOperate.openScanLight(false);
			}
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime <= 3000) {
				firstTime = secondTime;
			} else {
				if (bRFID) {
					pressCount = pressCount + 1;
					if (!isRfidStart) {
						isRfidStart = true;
						if (Utility.isLowPower(getIntent())) {
							bRFID = false;
							bEnableRFID = false;
							return false;
						}
						rfidList.clear();
						bRfidStoped = false;
						Comm.Awl.WakeLock();
						Comm.clean();
				        Comm.startScan();

					} else
						isRfidStart = false;
				}
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	//大于2时停止RFID
	private int pressCount = 0;
	//RFID是否开启
	private boolean isRfidStart = false;
	private boolean bRFID = false;
	private long firstTime = 0;
	// 是否允许切换到RFID
	private boolean bEnableRFID = true;
	private boolean scannerInited = false;		//扫描头是否初始化成功
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (event.getScanCode() == 249 || event.getScanCode() == 261) {
			if (bRFID) {
				if (pressCount == 2 && !isRfidStart) {
					bRfidStoped = true;
					Comm.Awl.ReleaseWakeLock();
			        Comm.stopScan();
					pressCount = 0;
				}else if (pressCount > 2) {
					bRfidStoped = true;
					Comm.Awl.ReleaseWakeLock();
			        Comm.stopScan();

					pressCount = 0;
				}else{
					new Thread(){
						public void run(){
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							}
						}
					}.start();
				}
			}
		}
		if (keyCode == 249) {
			Intent scannerIntent = new Intent(ScanOperate.SCN_CUST_ACTION_CANCEL);
			sendBroadcast(scannerIntent);
		}
		return super.onKeyUp(keyCode, event);
	}

	private void setDisplayContent() {
		for (int i = 2; i < arrDisplayItem.length; i++) {
			if (arrDisplayItem[i].equals("1") || arrDisplayItem[i].equals("2")) {
				arrTextViews[i - 2].setText(contentList.get(i - 2));
			}
		}
		tv_title.setText(contentList.get(2));
	}

	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// //--//各列分别代表条码,数量,款号,名称,颜色ID,颜色,尺码ID,尺码,价格
				int i = 0;
				for (i = 0; i < listTmp.size(); ++i) {
					CheckDetail item = listTmp.get(i);
					contentList.add(item.strGdArtNO);
					contentList.add(item.strGdStyle);
					contentList.add(item.strGdName);
					contentList.add(item.strGdColorID);
					contentList.add(item.strGdColorName);
					contentList.add(item.strGdSizeID);
					contentList.add(item.strGdSizeName);
					contentList.add(item.strProperty1);
					contentList.add(item.strProperty2);
					contentList.add(String.valueOf(item.dStock));
					contentList.add(String.valueOf(item.dGdPrice));
					setDisplayContent();
				}
				CheckListCounterAndShowSta();
				listTmp.clear();
				break;
			}
		}
	};


	private void KeyDownOK() {
		try {

			int iRet = 0;
			gdInfo.strGdCode = et_barcode.getText().toString().trim();//内部码
			checkDetail.strGdBar = et_barcode.getText().toString().trim();//条码
			checkDetail.strSingleGdBar = et_barcode.getText().toString().trim(); // 截取前的条码,该字段内容在后续处理时不会被改动,特殊处理唯一的情况
			checkDetail.strShopID = strShopID;//门店编号
			checkDetail.strCheckID = strCheckID;//盘点编号
			checkDetail.strPositionID = strPositionID;//货位编号

			iRet = JudgeConditions(gdInfo, checkDetail); // 扫描唯一码，长度，截取，验证，条码拆分处理

			globaStatus = iRet;
			if (iRet != 1) {
				et_barcode.requestFocus();
				et_barcode.selectAll();

				return;
			}

			// 暂时屏蔽掉条码拆分的功能
			/*
			 * int iR = 1; iR = ExecuteBarSplit(checkDetail.strGdBar,
			 * checkDetail); if (iR == 0) {
			 * AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
			 * "条码拆分规则与实际扫描条码不符，请检查并确认。", R.string.ok, new View.OnClickListener() {
			 * 
			 * @Override public void onClick(View v) {
			 * method stub AlertUtil.dismissDialog(); } });
			 * 
			 * et_barcode.requestFocus(); et_barcode.selectAll(); return; }
			 */

			if (cbDecrea.isChecked()) {

				// 0是累加
				if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("0")) {
					BarDicreaAdd(gdInfo, checkDetail);
					// 逐行显示
				} else if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("1")) {
					BarDicreaNoAdd(gdInfo, checkDetail);
				}

				cbDecrea.setChecked(false);
			} else {
				if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("0"))
					GdNumAdd(gdInfo, checkDetail);

				// 逐行显示
				else if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("1"))
					if(sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("1")){//不输入数量
						GdNumNOAdd(gdInfo, checkDetail);
					}


			}

			if (globaStatus != 0) {
				if (checkDetail != null) {
				// 当验证或提示的时候
				if (!sp.getString(ConfigEntity.BarCodeAuthKey,
						ConfigEntity.BarCodeAuth).equals("0")) {
							if (checkDetail.getStrGdName() != null) {
								tv_title.setText(checkDetail.getStrGdName());
							}
							if (checkDetail.getStrGdStyle() != null) {
								tv_style.setText(checkDetail.getStrGdStyle());
							}
							if (checkDetail.getStrGdColorName() != null) {
								tv_color.setText(checkDetail
										.getStrGdColorName());
							}
							if (checkDetail.getStrGdSizeName() != null) {
								tv_size.setText(checkDetail.getStrGdSizeName());
							}
							if (checkDetail.getStrGdSizeID() != null) {
								tv_sizeid.setText(checkDetail.getStrGdSizeID());
							}
							if (checkDetail.getStrProperty1() != null) {
								tv_big.setText(checkDetail.getStrProperty1());
							}
							if (checkDetail.getStrProperty2() != null) {
								tv_small.setText(checkDetail.getStrProperty2());
							}
							if (checkDetail.getStrGdColorID() != null) {
								tv_colorid.setText(checkDetail
										.getStrGdColorID());
							}
							tv_price.setText(String
									.valueOf(checkDetail.dGdPrice));
							tv_stockcount.setText(String
									.valueOf(checkDetail.getStrStock()));
							tv_num.setText(String.valueOf(checkDetail
									.getStrGdArtNO()));
						}
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
					}
				}
		} catch (Exception ex) {
			ex.printStackTrace();
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
					R.string.internal_scan_set, R.string.ok, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
		} finally {
			et_barcode.requestFocus();
			et_barcode.selectAll();

			// 1为逐行显示
			if (sp.getString(ConfigEntity.ScanningShowModeKey,
					ConfigEntity.ScanningShowMode).equals("1")) {
				tv_checknum.setText(String.valueOf(sqlStockDataSqlite
						.GetSpecifiedCheckIDSum(strShopID, strCheckID,
								strPositionID)));
				tv_qty.setText(String.valueOf(sqlStockDataSqlite
						.GetSpecifiedPositionIdSum(strShopID, strCheckID,
								strPositionID)));
			} else {
				refreshListView();
			}
		}
	}
/**
 * 扫描后listview信息展示(条码:   数量:   )
 */
	public void CheckListCounterAndShowSta() {
		if(!isRFID){
			tv_qty.setText(String.valueOf(dPositionSum));
			tv_checknum.setText(String.valueOf(dCheckSum));
																		//iDisplayRows(30)扫描界面显示的行数
			if (mCheckDetailList.size() > GlobalRunConfig.GetInstance().iDisplayRows + 4) {
				int i = 0;
				for (i = mCheckDetailList.size(); i > GlobalRunConfig.GetInstance().iDisplayRows; --i) {
					// 为1时为累加显示
					// mListView.removeViewAt(i-1);
					if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0"))
						mCheckDetailList.remove(i - 1);
				}
					mGoodsDetailsListAdapter.setList(mCheckDetailList);
					mListView.setAdapter(mGoodsDetailsListAdapter);
					mGoodsDetailsListAdapter.notifyDataSetChanged();
			} else {
				if (mCheckDetailList != null && mCheckDetailList.size() > 0) {

					// 0是累加
					if (sp.getString(ConfigEntity.ScanningShowModeKey,
							ConfigEntity.ScanningShowMode).equals("0")) {
						if (newQty > 0) {
							mCheckDetailList.get(itemSelected).dNum = newQty;
						}
						// 1是逐行
						// 在显示界面,设置逐行显示时,并在此时修改了数量,不需要像累加显示那样再进行新数量的赋值
					} else if (sp.getString(ConfigEntity.ScanningShowModeKey,
							ConfigEntity.ScanningShowMode).equals("1")) {

					}

					mGoodsDetailsListAdapter.setList(mCheckDetailList);
					mListView.setAdapter(mGoodsDetailsListAdapter);
					mGoodsDetailsListAdapter.notifyDataSetChanged();

				}
			}
		}
	}

	// / 处理扫描声音
	public void ScanSound() {
		// 声音处理
		// if
		// (sp.getString(ConfigEntity.PDAVersionKey,ConfigEntity.PDAVersion).equals("27"))
		// {
		// SalesPoint.Device.SoundPlay.SpScanReminder(0);
		// }
		// else
		// {
		// if
		// (SupoinInven.Config.ConfigLogic.Instance.Param.ScanSoundType.Equals("0")
		// ||
		// SupoinInven.Config.ConfigLogic.Instance.Param.ScanSoundType.Equals("-1"))
		// //如果是选址纯喇叭或者没找到key值，则都按喇叭来处理
		// {
		// if
		// (SupoinInven.Config.ConfigLogic.Instance.Param.ScanSound.Equals("标准系统声音"))
		// SalesPoint.Device.SoundPlay.Play(SalesPoint.Device.SoundType.ScanSound);
		// else
		// {
		// string strFullPath =
		// System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().GetName().CodeBase.ToString());
		// strFullPath = strFullPath + "\\声音文件\\" +
		// SupoinInven.Config.ConfigLogic.Instance.Param.ScanSound;
		// SalesPoint.Device.SoundPlay.Play(strFullPath);
		// }
		// }
		// else
		// {
		// SalesPoint.Device.SoundPlay.SpScanReminder(0);
		// }
		// }
		// endregion
	}
/**
 * 获取条码
 * @param code
 */
	private void OnGetBarcode(String code) {
		if (code.trim().length() < 1) {
			return;
		}
		ScanSound();
		et_barcode.setText(code.trim());
		KeyDownOK();

	}

	/**
	 * 拆分并保存数据库
	 * @param code
	 */
	private void BarCodeSqlitAndInsert(String code) {
	// 拆分判断没有写
		CheckDetail mCheckDetail = new CheckDetail();

		if (barCodeCutSetting.equals("1")) {
			// 条码不处理
			mCheckDetail.setStrGdBar(code);
			mCheckDetail.setStrShopID(strShopID);
			mCheckDetail.setStrCheckID(code);
			sqlStockDataSqlite.InsertCheckDetail(mCheckDetail, sp.getString(
					ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),
					sp.getString(ConfigEntity.BarCodeAuthKey,
							ConfigEntity.BarCodeAuth), tempStatus, sp
							.getString(ConfigEntity.IsPutInNumKey,
									ConfigEntity.IsPutInNum), 0);
		} else if (barCodeCutSetting.equals("1"))
			sqlStockDataSqlite.InsertCheckDetail(mCheckDetail, sp.getString(
					ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),
					sp.getString(ConfigEntity.BarCodeAuthKey,
							ConfigEntity.BarCodeAuth), tempStatus, sp
							.getString(ConfigEntity.IsPutInNumKey,
									ConfigEntity.IsPutInNum), 0);

	}

	/**
	 * 累加唯一码的消减
	 * @param cg
	 * @param cd
	 * @return
	 */
	private int BarDicreaAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 0;
		int iCounter = 0;
		int flag = 0;

		// 从基础数据中查询是否有此条码
		flag = sqlStockDataSqlite.checkUniqueCode(strShopID, strCheckID,strGdBar);
		// 基础数据有此条码的时候
		if (flag != 0) {
			// 从CheckDetail里查找是否有当前条码
			iCounter = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);
			// 基础数据无此条码时
		} else {
			// 从CheckDetail里查找是否有当前条码
			iCounter = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID, cd.strSingleGdBar);
		}

		if (iCounter != 1) {
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
					R.string.no_unique_barcode, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							is_intercept = true;
						}
					});
			is_intercept = false;
		}
		int dOriginalNum = 0;
		if (flag != 0) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strSingleGdBar);
		}
		checkDetail.dCheckNum = dOriginalNum - 1;

		sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, flag,false, 0,isBtn);
		dCheckSum -= 1;
		dPositionSum -= 1;

		int iIndex = getIndexOfCurBarcode(checkDetail.strGdBar);

		if (mCheckDetailList.size() > 0) {
			if (iIndex != -1) {
				if (checkDetail.dCheckNum == 0) {
					mCheckDetailList.remove(iIndex);
					mGoodsDetailsListAdapter.notifyDataSetChanged();
					tv_title.setText(null);
				} else {
					mCheckDetailList.get(iIndex).dNum = checkDetail.dCheckNum;
					mListView.setSelection(iIndex);
					mListView.requestFocus();
				}
			} else {
				if (checkDetail.dCheckNum != 0) {
					CheckListEntity list1 = new CheckListEntity();
					contentList.clear();
					contentList.add(String.valueOf(cd.dCheckNum));
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
					list1.strBar = cd.strGdBar;
					list1.dNum = cd.dCheckNum;
					mCheckDetailList.add(0, list1);
				}
			}
		} else {
			AlertUtil.showAlert(GoodsDetailsActivity.this, AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.no_unique_barcode));
		}
		return iRet;
	}

	/**
	 * 不累加唯一码的消减
	 * @param cg
	 * @param cd
	 * @return
	 */
	private int BarDicreaNoAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 0;

		int iCounter = 0;
		int flag = 0;

		// 从基础数据中查询是否有此条码
		flag = sqlStockDataSqlite.checkUniqueCode(strShopID, strCheckID,cd.strGdBar);
		// 基础数据有此条码的时候
		if (flag != 0) {
			// 从CheckDetail里查找是否有当前条码
			iCounter = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID ,cd.strGdBar);
			// 基础数据无此条码时
		} else {
			// 从CheckDetail里查找是否有当前条码
			iCounter = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID ,cd.strSingleGdBar);
		}

		if (iCounter < 1) {
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
					AlertUtil.getString(R.string.no_unique_barcode), R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							is_intercept = true;
						}
					});
			is_intercept = false;
		}
		int dOriginalNum = 0;

		if (flag != 0) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strSingleGdBar);
		}

		checkDetail.dCheckNum = dOriginalNum - 1;

		sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, flag,false, 0,isBtn);

		dCheckSum -= 1;
		dPositionSum -= 1;

		CheckListEntity list1 = new CheckListEntity();
		contentList.clear();
		contentList.add("-1");
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
		list1.strBar = cd.strGdBar;
		list1.dNum = cd.dCheckNum;
		mCheckDetailList.add(0, list1);

		refreshListView();

		return iRet;
	}


	public int VerifyBar() {
		int iRet = 0;
		return iRet;
	}

	/**
	 * 1,如拆分，截取
	 * @param cg
	 * @param cd
	 * @return
	 */
	public int JudgeConditions(GdInfo cg, final CheckDetail cd) {
		int iRet = 1;
		int iTmp = 0,iTmp2 = 0;
		final String strBar = et_barcode.getText().toString().trim();
		if (strBar.length() < 1) {
			AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title, AlertUtil.getString(R.string.barCode_not_empty),
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return 0;
		}

		// 当启用唯一条码时
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {
			//当前编号是否存在该条码
			iTmp = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID,strCheckID,cd.strGdBar);
			//当前货号是否存在该条码
			iTmp2 = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID,strCheckID,strPositionID,cd.strGdBar);

			if (iTmp == 1) {
				//bug1352
				if(!cbDecrea.isChecked()){
					AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title, R.string.barcode_repeat,
						R.string.ok, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
					return 0;
				}else{
					if(iTmp2 != 1){//当前货号不存在该条码，且勾选消减，提示不存在所消减的唯一条码
						AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.dialog_title, R.string.no_unique_barcode,
							R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
						return 0;
					}
				}
			}
		}

		// 条码处理 长度验证
		if (sp.getString(ConfigEntity.LengthLimitKey, ConfigEntity.LengthLimit).equals("1"))
		{
			if (strBar.length() == Integer.parseInt(sp.getString(
					ConfigEntity.LengthEqualToLimit1Key,
					ConfigEntity.LengthEqualToLimit1))
					|| strBar.length() == Integer.parseInt(sp.getString(
							ConfigEntity.LengthEqualToLimit2Key,
							ConfigEntity.LengthEqualToLimit2))
					|| strBar.length() == Integer.parseInt(sp.getString(
							ConfigEntity.LengthEqualToLimit3Key,
							ConfigEntity.LengthEqualToLimit3))
					|| strBar.length() == Integer.parseInt(sp.getString(
							ConfigEntity.LengthEqualToLimit4Key,
							ConfigEntity.LengthEqualToLimit4))
					|| strBar.length() == Integer.parseInt(sp.getString(
							ConfigEntity.LengthEqualToLimit5Key,
							ConfigEntity.LengthEqualToLimit5))
					|| strBar.length() == Integer.parseInt(sp.getString(
							ConfigEntity.LengthEqualToLimit6Key,
							ConfigEntity.LengthEqualToLimit6))) {

			} else {
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
						 R.string.code_not_match_setting, R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				return 0;
			}
			// 范围限制
		} else if (sp.getString(ConfigEntity.LengthLimitKey,ConfigEntity.LengthLimit).equals("2")) {
			if (strBar.length() > Integer.parseInt(sp.getString(ConfigEntity.LengthLimitRangeMaxKey,ConfigEntity.LengthLimitRangeMax)) || strBar.length() < Integer.parseInt(sp.getString(ConfigEntity.LengthLimitRangeMinKey,ConfigEntity.LengthLimitRangeMin))) {
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
						R.string.code_not_match_setting, R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				return 0;
			}
		}

		int iR = 0;
		iR = ExecuteCutReserveBar(strBar, true);
		if (iR == 0)
			return 0;
		et_barcode.setText(strGdBar);
		cg.strGdCode = strGdBar;

		// 条码验证--是否验证
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("1")) {

			GdInfo gdInfoInnerCode = new GdInfo();
			GdInfo gdInfoOutterCode = new GdInfo();

			//内部码的属性
			gdInfoInnerCode = sqlStockDataSqlite.GetDataByBar(strGdBar);
			if(gdInfoInnerCode==null){
				//外部码的属性
				gdInfoOutterCode = sqlStockDataSqlite.GetDataByCode(strGdBar);
			}
			if (gdInfoInnerCode == null && gdInfoOutterCode == null) {

				MediaPlayer.create(this,R.raw.error).start();
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
						R.string.barCode_not_exist, R.string.ok, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								et_barcode.setText("");
								clearGdInfo();
							}
						});
				return 0;
			}

			if (gdInfoInnerCode != null) {
				cg = gdInfoInnerCode;
			} else if (gdInfoOutterCode != null) {
				cg = gdInfoOutterCode;
			}

			// 内部码
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
				cd.strGdBar = cg.strGdCode;
				// 外部码
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
				cd.strGdBar = cg.strGdBarcode;
			} else {
				cd.strGdBar = strGdBar;
			}

			int checkBarFromCheckDetail = 0;

			// 内部码
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {

				checkBarFromCheckDetail = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);

				// 外部码
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {

				checkBarFromCheckDetail = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);
			}

			// 1是启用唯一条码
			if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {
//				// 基础资料无此条码的时候
					// CheckDetail中无此条码时
					if (checkBarFromCheckDetail != 1) {

						// 基础资料有此条码时
					} else {
						if (cbDecrea.isChecked()) {
							return 1;
						} else {
							AlertUtil.showAlert(GoodsDetailsActivity.this,AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.barcode_repeat));
							return 0;
						}
					}
//				}
				// 0是不启用唯一条码
			} else if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {

			}

			cd.dGdPrice = cg.dGdPrice;
			cd.strGdColorID = cg.strGdColorID;
			cd.strGdColorName = cg.strGdColorName;
			cd.strGdName = cg.strGdName;
			cd.strGdSizeID = cg.strGdSizeID;
			cd.strGdSizeName = cg.strGdSizeName;
			cd.strProperty1 = cg.strProperty1;
			cd.strProperty2 = cg.strProperty2;
			cd.strGdArtNO = cg.strGdArtNO;
			cd.strGdStyle = cg.strGdStyle;
			cd.dStock = cg.dGdStock;
			cd.strCheckID = strCheckID;
			cd.strShopID = strShopID;
			cd.strPositionID = strPositionID;

			// 为2时是提示
		} else if (sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth).equals("2")) {

			GdInfo gdInfoInnerCode = new GdInfo();
			GdInfo gdInfoOutterCode = new GdInfo();

			gdInfoInnerCode = sqlStockDataSqlite.GetDataByBar(strGdBar);
			if(gdInfoInnerCode==null){
				gdInfoOutterCode = sqlStockDataSqlite.GetDataByCode(strGdBar);
			}

			if (gdInfoInnerCode != null) {
				cg = gdInfoInnerCode;
			} else if (gdInfoOutterCode != null) {
				cg = gdInfoOutterCode;
			}

			// 内部码
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
				cd.strGdBar = cg.strGdCode;
				// 外部码
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
				cd.strGdBar = cg.strGdBarcode;
			} else {
				cd.strGdBar = strGdBar;
			}

			cd.dGdPrice = cg.dGdPrice;
			cd.strGdColorID = cg.strGdColorID;
			cd.strGdColorName = cg.strGdColorName;
			cd.strGdName = cg.strGdName;
			cd.strGdSizeID = cg.strGdSizeID;
			cd.strGdSizeName = cg.strGdSizeName;
			cd.strProperty1 = cg.strProperty1;
			cd.strProperty2 = cg.strProperty2;
			cd.strGdArtNO = cg.strGdArtNO;
			cd.strGdStyle = cg.strGdStyle;
			cd.dStock = cg.dGdStock;
			cd.strCheckID = strCheckID;
			cd.strShopID = strShopID;
			cd.strPositionID = strPositionID;

			int checkFlag = 0;
			int checkBarFromCheckDetail = 0;

			// 内部码
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {

				checkFlag = sqlStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);

				// 外部码
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {

				checkFlag = sqlStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);
			}

			// 0不启用唯一条码
			if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
				// 如果基础资料中无此条码的时候
				if (checkFlag < 1) {
					if (checkBarFromCheckDetail != 1||(gdInfoInnerCode==null&&gdInfoOutterCode==null)) {
						MediaPlayer.create(this,R.raw.error).start();
						AlertUtil.showAlert(GoodsDetailsActivity.this,
								R.string.dialog_title, R.string.code_not_in_data,
								R.string.ok, new OnClickListener() {

									@Override
									public void onClick(View v) {
										cd.strGdBar = strGdBar;

										// 0是输入数量
										if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
											openModifyQty(false);
											// 0是累加
											if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0")) {
													refreshListView();
													// 1是逐行
												} else if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("1")) {
											}
											// 不输入
										} else {
											sqlStockDataSqlite.InsertCheckDetailBySpecial(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);
											// 0是累加
											if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0")) {
												refreshListView();
												// 1是逐行
											} else if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("1")) {
												CheckListEntity list1 = new CheckListEntity();
												list1.strBar = cd.strGdBar;
												list1.dNum = 1;
												mCheckDetailList.add(0, list1);
												if (mGoodsDetailsListAdapter == null) {
													mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(
															GoodsDetailsActivity.this, mCheckDetailList);
												}
												mGoodsDetailsListAdapter.setList(mCheckDetailList);
												mListView.setAdapter(mGoodsDetailsListAdapter);
												//bug1368
												tv_checknum.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
												tv_qty.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedPositionIdSum(strShopID, strCheckID, strPositionID)));
											}
										}
 										AlertUtil.dismissDialog();
									}
								}, R.string.cancel, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
						//bug1357 and 1359
						iRet = 0;
					} else {
						cd.strGdBar = strGdBar;
						if (sp.getString(ConfigEntity.ModifyNumPWKey,ConfigEntity.ModifyNumPW).equals("1")) {
							Intent intent = new Intent(GoodsDetailsActivity.this,PasswordInputActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
							bundle.putString(Constants.KEY_PASSWORD_TYPE, Constants.PWD_GENERAL);
							intent.putExtras(bundle);
							startActivityForResult(intent, 12);
							is_intercept = false;

							// 当CheckDetail有此数据的时候
						} else {

							// 0是输入数量
							if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
								Intent intent = new Intent();
								intent.setClass(GoodsDetailsActivity.this,ModityQtyActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("GdBar", strGdBar);
								intent.putExtras(bundle);
								startActivityForResult(intent, REQUEST_QTY_CODE);
								// 1是不输入数量
							} else if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("1")) {
								tempStatus = true;
								iRet = 0;
								MediaPlayer.create(this,R.raw.error).start();
								AlertUtil.showAlert(
										GoodsDetailsActivity.this,
										R.string.dialog_title,
										R.string.code_not_in_data,
										R.string.ok,
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												cd.strGdBar = strGdBar;
												if (sp.getString(ConfigEntity.ModifyNumPWKey,ConfigEntity.ModifyNumPW).equals("1")) {
													Intent intent = new Intent(GoodsDetailsActivity.this,PasswordInputActivity.class);
													Bundle bundle = new Bundle();
													bundle.putString("title",AlertUtil.getString(R.string.pass_admin));
													bundle.putString(Constants.KEY_PASSWORD_TYPE, Constants.PWD_GENERAL);
													intent.putExtras(bundle);
													startActivityForResult(intent, 12);
													is_intercept = false;
												} else {
													sqlStockDataSqlite.InsertCheckDetailBySpecial(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);
													refreshListView();

												}

												refreshListView();
												AlertUtil.dismissDialog();
											}
										}, R.string.cancel,
										new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												AlertUtil.dismissDialog();
												refreshListView();

											}
										});
							}

						}

					}

				} else {

					if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
						openModifyQty(false);
					} else {
						tempStatus = true;
						iRet = 1;
					}

				}
				// 1是启用唯一条码,且不能输入数量
			} else if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {
				// 如果基础资料无此条码的时候
				if (checkFlag < 1) {
					// 如果CheckDetail中也无此条码时
					if (checkBarFromCheckDetail != 1) {
						// 如果"消减"框被选中的情况下
						if (cbDecrea.isChecked()) {

							iRet = 1;
							refreshListView();

							// 如果"消减"框被未被选中的情况下
						} else {
							MediaPlayer.create(this,R.raw.error).start();
							AlertUtil.showAlert(
									GoodsDetailsActivity.this,
									R.string.dialog_title,
									R.string.code_not_in_data,
									R.string.ok,
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											cd.strGdBar = strGdBar;
											cd.strSingleGdBar = strGdBar;
											sqlStockDataSqlite.InsertCheckDetail(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);

											refreshListView();

											AlertUtil.dismissDialog();
										}
									}, R.string.cancel,
									new View.OnClickListener() {

										@Override
										public void onClick(View v) {
											AlertUtil.dismissDialog();
											refreshListView();
										}
									});
						}

					} else {

					}

				} else {
					// checkDetail不存在存在此条目的时候
					if (checkBarFromCheckDetail != 1) {
						refreshListView();
						tempStatus = true;
						iRet = 1;
						// checkDetail存在此条目的时候
					} else {
						int iIndex = 0;
						// 消减选项被选中的情况下
						if (cbDecrea.isChecked()) {

							// 0是内部码
							if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("0")) {
								iIndex = getIndexOfCurBarcode(checkDetail.strGdBar);
								// 1是外部码
							} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
								iIndex = getIndexOfCurBarcode(checkDetail.strSingleGdBar);
							}

							if (mCheckDetailList.size() > 0) {
								if (iIndex != -1) {
									if (checkDetail.dCheckNum == 0) {
										mCheckDetailList.remove(iIndex);
										mGoodsDetailsListAdapter.notifyDataSetChanged();

										tv_title.setText(null);
									} else {
										mCheckDetailList.get(iIndex).dNum = checkDetail.dCheckNum;
										mListView.setSelection(iIndex);
										mListView.requestFocus();
									}
								} else {
									if (checkDetail.dCheckNum != 0) {
										CheckListEntity list1 = new CheckListEntity();
										contentList.clear();
										contentList.add(String.valueOf(cd.dCheckNum));
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
										list1.strBar = cd.strGdBar;
										list1.dNum = cd.dCheckNum;
										mCheckDetailList.add(0, list1);
									}
								}
							} else {

								AlertUtil.showAlert(GoodsDetailsActivity.this,
										AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.no_unique_barcode));
								is_intercept = false;
								refreshListView();
							}

						} else {
							AlertUtil.showAlert(GoodsDetailsActivity.this,
									AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.barcode_repeat));
							iRet = 0;
						}

					}

				}

				if (gdInfoInnerCode != null) {
					cg = gdInfoInnerCode;
				} else if (gdInfoOutterCode != null) {
					cg = gdInfoOutterCode;
				}

				// 内部码
				if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("0")) {
					cd.strGdBar = cg.strGdCode;
					// 外部码
				} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
					cd.strGdBar = cg.strGdBarcode;
				} else {
					cd.strGdBar = this.strBar;
				}

				cd.dGdPrice = cg.dGdPrice;
				cd.strGdColorID = cg.strGdColorID;
				cd.strGdColorName = cg.strGdColorName;
				cd.strGdName = cg.strGdName;
				cd.strGdSizeID = cg.strGdSizeID;
				cd.strGdSizeName = cg.strGdSizeName;
				cd.strProperty1 = cg.strProperty1;
				cd.strProperty2 = cg.strProperty2;
				cd.strGdArtNO = cg.strGdArtNO;
				cd.strGdStyle = cg.strGdStyle;
				cd.dStock = cg.dGdStock;
				cd.strCheckID = strCheckID;
				cd.strShopID = strShopID;
				cd.strPositionID = strPositionID;
			} else {
				iRet = 1;
			}

		} else {
			cd.strGdBar = strGdBar;
			cd.strCheckID = strCheckID;
			cd.strShopID = strShopID;
			cd.strPositionID = strPositionID;
		}

		// 是否输入数量
		// 0位输入数量
		if (sp.getString(ConfigEntity.IsPutInNumKey, ConfigEntity.IsPutInNum).equals("0")) {
			// 2是提示的时候,并且输入数量的时候
			if (sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {

			} else {
				openModifyQty(false);
			}

		} else
			cd.dCheckNum = 1;

		return iRet;
	}

	// 查找当前列表中跟扫描条码相同的
	private int getIndexOfCurBarcode(String barcode) {
		int index = 0;
		for (int i = 0; i < mCheckDetailList.size(); i++) {
			CheckListEntity item = mCheckDetailList.get(i);
			if (barcode.equals(item.strBar)) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * 累加处理
	 * @param cg
	 * @param cd
	 * @return
	 */
	public int GdNumAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 1, i = 0;
		int dOriginalNum = 0;

		// 内部码
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
			// 外部码
		} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, strGdBar);
		}

		if (dOriginalNum == 0) {
			i = sqlStockDataSqlite.InsertCheckDetail(cd, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 0);
			if (i == 1) {

				CheckListEntity list1 = new CheckListEntity();
				contentList.clear();
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

				list1.strBar = cd.strGdBar;
				list1.dNum = cd.dCheckNum;
				mCheckDetailList.add(0, list1);
				if (mGoodsDetailsListAdapter == null) {
					mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(GoodsDetailsActivity.this, mCheckDetailList);
				}
				mGoodsDetailsListAdapter.setList(mCheckDetailList);
				mListView.setAdapter(mGoodsDetailsListAdapter);
				mListView.setSelection(0);
				mListView.requestFocus();

				dCheckSum += cd.dCheckNum;
				dPositionSum += cd.dCheckNum;
			} else {
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.error,
						R.string.inventory_failed_check, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				return 0;
			}
		} else {
			// 当输入数量的时候
			if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {

			} else {
				cd.dCheckNum = cd.dCheckNum + dOriginalNum;
				i = sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, cd, 1,false, 0,isBtn);

				if (i != 1) {
					AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.error,
							R.string.inventory_failed_check, R.string.ok,
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
					return 0;
				}

				i = -1;
				String strBarTmp = cd.strGdBar;
				i = getIndexOfCurBarcode(strBarTmp);
				if (i == -1) {
					CheckListEntity list1 = new CheckListEntity();
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

					mListView.setSelection(0);
					mListView.requestFocus();

					list1.strBar = cd.strGdBar;
					list1.dNum = cd.dCheckNum;
					mCheckDetailList.add(0, list1);
					if (mGoodsDetailsListAdapter == null) {
						mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(
								GoodsDetailsActivity.this, mCheckDetailList);
					}
					mGoodsDetailsListAdapter.setList(mCheckDetailList);
					mListView.setAdapter(mGoodsDetailsListAdapter);
				} else {
					mCheckDetailList.get(i).dNum = cd.dCheckNum;
					mListView.setSelection(i);
					mListView.requestFocus();
				}
				dCheckSum -= dOriginalNum;
				dPositionSum -= dOriginalNum;
				dCheckSum += cd.dCheckNum;
				dPositionSum += cd.dCheckNum;
			}

		}

		return iRet;

	}

	/**
	 * 执行条码截取保留功能
	 * @param fullBar
	 * @param bWithPrompt
	 * @return
	 */
	public int ExecuteCutReserveBar(String fullBar, boolean bWithPrompt) {
		strGdBar = fullBar;
		int iRet = 1;

		// region 条码处理-截取位数，保留位数处理
		if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,ConfigEntity.LengthCutOrKeepBarCode).equals("1")) // 截取后位数
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
							R.string.inventory_code_length, R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
				}
				iRet = 0;
				return 0;
			}
			strGdBar = strGdBar.substring(0,strGdBar.length() - Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)));
		} else if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,
				ConfigEntity.LengthCutOrKeepBarCode).equals("2")) // 截取前位数
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.warning,
							R.string.inventory_code_length, R.string.ok, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
				}
				iRet = 0;
				return 0;
			}
			strGdBar = strGdBar.substring(Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)), strGdBar.length());
		} else if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,
				ConfigEntity.LengthCutOrKeepBarCode).equals("3")) // 保留后位数
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				;
			} else {
				strGdBar = strGdBar.substring(strGdBar.length()- Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)),strGdBar.length());
			}
		} else if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,ConfigEntity.LengthCutOrKeepBarCode).equals("4")) // 保留前位数
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				;
			} else {
				strGdBar = strGdBar.substring(0, Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)));
			}
		}

		return iRet;
	}
/**
 * 逐行显示
 * @param cg
 * @param cd
 * @return
 */
	public int GdNumNOAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 1, i = 0;
		int dOriginalNum = 0;//初始数量

		// 内部码
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
			// 外部码
		} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, strGdBar);
		}

		if (dOriginalNum == 0) {
			i = sqlStockDataSqlite.InsertCheckDetail(cd, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 0);
			if (i == 1) {
				CheckListEntity list1 = new CheckListEntity();
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

				list1.strBar = cd.strGdBar;
				list1.dNum = cd.dCheckNum;
				mCheckDetailList.add(0, list1);
				if (mGoodsDetailsListAdapter == null) {
					mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(
							GoodsDetailsActivity.this, mCheckDetailList);
				}
				mGoodsDetailsListAdapter.setList(mCheckDetailList);
				mListView.setAdapter(mGoodsDetailsListAdapter);

				dCheckSum += cd.dCheckNum;
				dPositionSum += cd.dCheckNum;
			} else {
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.error,
						R.string.inventory_failed_check, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});

				return 0;
			}
		} else {
			//盘点数量
			cd.dCheckNum = cd.dCheckNum + dOriginalNum;
			i = sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, cd, 1, false,
					0,isBtn);
			if (i != 1) {
				AlertUtil.showAlert(GoodsDetailsActivity.this, R.string.error,
						R.string.inventory_failed_check, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});

				return 0;
			}
			contentList.clear();
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
			CheckListEntity list1 = new CheckListEntity();
			list1.strBar = cd.strGdBar;

			list1.dNum = 1;
			mCheckDetailList.add(0, list1);
			if (mGoodsDetailsListAdapter == null) {
				mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(
						GoodsDetailsActivity.this, mCheckDetailList);
			}
			mGoodsDetailsListAdapter.setList(mCheckDetailList);
			mListView.setAdapter(mGoodsDetailsListAdapter);
			dCheckSum -= dOriginalNum;
			dPositionSum -= dOriginalNum;
			dCheckSum += cd.dCheckNum;
			dPositionSum += cd.dCheckNum;
		}
		mListView.setSelection(0);
		mListView.requestFocus();
		return iRet;
	}
/**
 * 修改数量
 */
	private void saveCheckDetailQty() {
		if (isBtn&&newQty == oldQty)
			return;
		int dOriginalNum = 0;
		checkDetail.strGdBar = strGdBar;
		dOriginalNum = sqlStockDataSqlite.GetOriginalNum(strShopID, strCheckID,strPositionID, checkDetail.strGdBar);

		// 逐行扫描显示时，选择一项修改为0时出现同条码的数据变为0的BUG
		int sQty = dOriginalNum - oldQty;
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")&& newQty == 0 && sQty > 0) {
			if(sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")){//bug1355但为输入数量时,输入0的问题
				checkDetail.dCheckNum = newQty;
			}else{
				checkDetail.dCheckNum = dOriginalNum - oldQty;
			}
		} else {
			checkDetail.dCheckNum = newQty;
		}

		CheckDetail tempCheckDetail = null;

		// 为验证的情况下
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("1")||sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2")) {
			tempCheckDetail = sqlStockDataSqlite.getDetailData(checkDetail.strGdBar);
		} else {
			tempCheckDetail = sqlStockDataSqlite.GetSpeCheckRecord(strShopID,strCheckID, strPositionID, checkDetail.strGdBar);
		}

		if (tempCheckDetail != null) {
			checkDetail.strGdArtNO = tempCheckDetail.strGdArtNO;
			checkDetail.strGdStyle = tempCheckDetail.strGdStyle;
			checkDetail.strGdName = tempCheckDetail.strGdName;
			checkDetail.strGdColorID = tempCheckDetail.strGdColorID;
			checkDetail.strGdColorName = tempCheckDetail.strGdColorName;
			checkDetail.strGdSizeID = tempCheckDetail.strGdSizeID;
			checkDetail.strGdSizeName = tempCheckDetail.strGdSizeName;
			checkDetail.strProperty1 = tempCheckDetail.strProperty1;
			checkDetail.strProperty2 = tempCheckDetail.strProperty2;
			checkDetail.dStock = tempCheckDetail.dStock;
			checkDetail.dGdPrice = tempCheckDetail.dGdPrice;
			checkDetail.strSingleGdBar = tempCheckDetail.strSingleGdBar;
			checkDetail.strReservedMg = tempCheckDetail.strReservedMg;
		}

		checkDetail.strCheckID = strCheckID;
		checkDetail.strPositionID = strPositionID;
		checkDetail.strShopID = strShopID;
		checkDetail.strGdBar = strGdBar;
		if(!isBtn&&checkDetail.dCheckNum==0){//当为累加显示，修改数量，且输入0时。会出现清空该项
		}else{
			if (mCheckDetailList.size() > 0) {
				sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, 1,flag, mCheckDetailList.get(itemSelected).dNum,isBtn);
			} else {
				sqlStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, 1,flag, 0,isBtn);
			}
		}

		dCheckSum -= oldQty;
		dCheckSum += newQty;
		dPositionSum -= oldQty;
		dPositionSum += newQty;

		if (newQty == 0) {
			if (sp.getString(ConfigEntity.ScanningShowModeKey, "").equals("0")){
				if (mCheckDetailList.size() == 0) {

				} else {
					mCheckDetailList.remove(itemSelected);
				}
				//bug1353
				if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")){
					itemSelected = 0;
				}
			}
			CheckListCounterAndShowSta();
			if(isPassAct){
				refreshListView();
			}
			isBtn = false;
			return;
		}
		//0为累加显示
		if (sp.getString(ConfigEntity.ScanningShowModeKey, "").equals("0")) {
			if (mCheckDetailList.size() == 0) {

			} else {
				mCheckDetailList.get(itemSelected).dNum = newQty;
			}
		}
		mListView.setSelection(itemSelected);
		mListView.requestFocus();
		CheckListCounterAndShowSta();
		refreshListView();
		isBtn = false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		is_intercept = true;
		if (resultCode == RESULT_CANCELED) {
			refreshListView();
		}

		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		if (resultCode == Activity.RESULT_OK) {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_barcode.getWindowToken(), 0);
		}

		// 修改数量正确返回做处理
		if (requestCode == REQUEST_INPUTPSW_CODE) {
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, ModityQtyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("GdBar", mCheckDetailList.get(itemSelected).strBar);
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_QTY_CODE);
		} else if (requestCode == REQUEST_QTY_CODE) {
			isPass = false;
			String strQty = data.getStringExtra("CheckNum");

			newQty = Integer.parseInt(strQty);

			if (newQty != 0) {
				flag = true;
			}else{
				//bug1340
				flag = false;
			}

			saveCheckDetailQty();

		} else if (requestCode == 10) {
			isBtn = true;
			isPass = true;
			openModifyQty(false);
		} else if (requestCode == 12) {
			sqlStockDataSqlite.InsertCheckDetail(checkDetail, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 1);
			refreshListView();

		} else if (requestCode == 13) {
			Intent intent = new Intent();
			intent.setClass(GoodsDetailsActivity.this, ModityQtyActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_QTY_CODE);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		CustomTitleBar.setActivity(this);

		if(scanOperate.mHandler==null){
			scanOperate = new ScanOperate();
			scanOperate.onCreate(GoodsDetailsActivity.this, R.raw.scanok);
			scanOperate.openScannerPower(is_canScan);
		}
		scanOperate.mHandler = mHandler;
		scanOperate.onResume(this);
		if (bRFID) {
			scanOperate.openScanLight(false);
		}else{
			scanOperate.openScanLight(true);
		}
		if (Comm.moduleType == Comm.Module.UHF005)
            Comm.rfidOperate.onResume(this);
	
		Comm.mInventoryHandler = mHandler;
		if(!isPassAct){
			refreshListView();
		}

		is_intercept = true;
		super.onResume();
	}

	public void onStop() {
		super.onStop();
		is_intercept = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (bRFID) {
			scanOperate.openScanLight(true);
		}
		scanOperate.mHandler = null;
		scanOperate.onDestroy(this);
		is_intercept = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Comm.mInventoryHandler = null;
		myApplication.flag = false;
	}

	public void refreshListView() {
		isPassAct = false;
		mCheckDetailList.clear();
		if(isRFID){
			mCheckDetailList = sqlStockDataSqlite.GetRFIDCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}else{
			mCheckDetailList = sqlStockDataSqlite.GetStaCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}
		// 当设置为提示的时候,并且没有启用唯一码时
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
			mCheckDetailList = singleElement(mCheckDetailList);
		}

		if (mCheckDetailList != null ) {
			mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(GoodsDetailsActivity.this, mCheckDetailList);
			mListView.setAdapter(mGoodsDetailsListAdapter);
		}
		tv_checknum.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
		tv_qty.setText(String.valueOf(sqlStockDataSqlite.GetSpecifiedPositionIdSum(strShopID, strCheckID, strPositionID)));

		if (mGoodsDetailsListAdapter != null) {
			mGoodsDetailsListAdapter.notifyDataSetChanged();
		}
	}

	// 去除掉list中重复的对象元素
	private static List<CheckListEntity> singleElement(List<CheckListEntity> al) {
		List<CheckListEntity> list = new ArrayList<CheckListEntity>();
		java.util.Iterator<CheckListEntity> it = al.iterator();
		while (it.hasNext()) {
			CheckListEntity obj = it.next();
			if (!(list.contains(obj))) {
				list.add(obj);
			}
		}
		return list;
	}
	/**清空商品详情*/
	private void clearGdInfo(){
		tv_title.setText("");
		tv_style.setText("");
		tv_color.setText("");
		tv_size.setText("");
		tv_sizeid.setText("");
		tv_big.setText("");
		tv_small.setText("");
		tv_colorid.setText("");
		tv_price.setText("");
		tv_stockcount.setText("");
		tv_num.setText("");
	}

}