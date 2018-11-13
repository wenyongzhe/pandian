package com.supoin.commoninventory.instore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
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
import com.supoin.commoninventory.activity.BaseActivity;
import com.supoin.commoninventory.activity.PasswordInputActivity;
import com.supoin.commoninventory.activity.PasswordInputScanGoodActivity;
import com.supoin.commoninventory.adapter.GoodsDetailsListAdapter;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.ScanOperate;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.Utility;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.Common.InventoryBuffer;

import java.util.ArrayList;
import java.util.List;

import static com.uhf.uhf.Common.Comm.UHF1MESSAGE_TEXT;
import static com.uhf.uhf.Common.Comm.UHF5MESSAGE_TEXT;

public class InStockDetailsActivity extends BaseActivity implements OnClickListener {
	// ��״̬�����жϵ�������ʾʱ,û������Ψһ��ʱ��,ɨ�費�ڵ�������������ʱ������ж�
	String status = "0";
	//isScan���������ж��Ƿ�������ɨ��ķ�ʽ
	boolean isScan = false;
	int item = -1;
	boolean flag = false;
	boolean tempStatus = false;
	int globaStatus;
	//��λ
	public static String strPositionID = "";
	//���
	public static String strCheckID = "";
	//����(Ψһ��)
	public static String strSingleGdBar = "";
	//����
	public static String strGdBar = "";
	public static String strBar = "";
	private Button btn_query, btn_modify, btn_switch,btn_insert;
	private EditText et_barcode;
	// ��ţ���λtv_positionid�����ƣ���ɫ�����룬�۸񣬿�棬���࣬С�࣬��ɫID������IstrPositionIDD����ţ�����
	private TextView tv_checkid,  tv_title, tv_color, tv_size,
			tv_price, tv_stockcount, tv_big, tv_small, tv_colorid, tv_sizeid,
			tv_style, tv_num;
	private TextView tv_title0, tv_color0, tv_size0, tv_price0, tv_stockcount0,
			tv_big0, tv_small0, tv_colorid0, tv_sizeid0, tv_style0, tv_num0;
	// ��λ������������� tv_qty
	public static TextView  tv_checknum;
	//textview �޸� 	tv_huowei_xq   tv_huoweisl
	private TextView tv_bianhao_xq,tv_bianhaosl;
	//�Ƿ�����
	private CheckBox cbDecrea;
	private SQLInStockDataSqlite sqlInStockDataSqlite;
	private SharedPreferences sp;
	//�̵���
	public static String strShopID;
	private ScanOperate scanOperate;
	private Boolean is_canScan = true;
	// ����������ҳ��ɨ�� ��ѯʱ����ӵ����activity�ķ���
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
	private static final String TAG = "InStockDetailsActivity";
	private String[] arrDisplayItem;
	private List<CheckDetail> listTmp = new ArrayList<CheckDetail>();
	// ����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�";
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
	private boolean isBtn = false; //�Ƿ����޸�������ť
	/**�Ƿ���������  Ĭ��Ϊ���� ��������onResume()ˢ���б�*/
	private boolean isPass = false; 
	
	private String barCodeAuth;
	private ProgressDialog rfidDialog;
	private String inOutCode;
	private boolean isRFID;
	private List<String> rfidList = new ArrayList<String>();
	//private RFIDOperate rfidOperate;
	
	private boolean bRfidStoped = false;
	/**������ǩ*/
	private List<String> checkDetailStrList = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(InStockDetailsActivity.this, R.string.instock_sacn, false);
		setContentView(R.layout.activity_in_stock_goods_details);
		sqlInStockDataSqlite = new SQLInStockDataSqlite(InStockDetailsActivity.this,true);
		//��ʼ��sp
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		//��ȡ�̵���
		if (sp.getString(ConfigEntity.ERPKey, ConfigEntity.ERP).equals(ConfigurationKeys.GJP)) {
			strShopID = sp.getString(ConfigEntity.GraspInfoKey,ConfigEntity.GraspInfo).split(",")[0];
		} else {
			if (sp.getString(ConfigEntity.ShopIDKey, ConfigEntity.ShopID).equals("000000"))
				strShopID = "000000";	//�ŵ���
			else
				strShopID = sp.getString(ConfigEntity.ShopIDKey,ConfigEntity.ShopID);
		}
		
		//��֤��ʽ
		barCodeAuth = sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth);
		// �ڲ�����ⲿ�� 0�ڲ��룬1�ⲿ��
		inOutCode = sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode);
		//ɨ��ģʽ 0���룬1RFID
		isRFID = sp.getString(ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern).equals("1");

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		//��ȡɨ�赥��ҳ������ı��
		strCheckID = bundle.getString("strCheckID");
		//��ȡɨ�赥��ҳ������Ļ�λ��
		strPositionID = bundle.getString("strPositionID");
		scanOperate = new ScanOperate();
		scanOperate.onCreate(InStockDetailsActivity.this, R.raw.scanok);
		scanOperate.openScannerPower(is_canScan);
		//rfidOperate = new RFIDOperate();
		InitInterface();
		initView();
		setData();
		
		//1ΪRFID
		Intent intent0 = getIntent();
		if (Utility.isLowPower(intent0)) {
			bRFID = false;
			bEnableRFID = false;
		}
		
		if (bEnableRFID) {
			if(isRFID){//RFID ��������Ʒ���Կؼ�����Ʒ��ѯ���޸�������ť
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

	//ɨ���������ܵ��Ļص�
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ScanOperate.MESSAGE_TEXT:
				String code = (String) msg.obj;
				scanOperate.setVibratortime(50);
				scanOperate.mediaPlayer();
				isScan = true;
				Utility.deBug("����", code);
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
				Utility.deBug("����size", size+"");
				break;
			case 4:
				mCheckDetailList.clear();
				mCheckDetailList = sqlInStockDataSqlite.GetRFIDCheckPositionDetail(strShopID, strCheckID, strPositionID);
				if(mGoodsDetailsListAdapter!=null){
					if(mCheckDetailList.size()>0){
						mGoodsDetailsListAdapter.setList(mCheckDetailList);
					}
				}else{
					mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(InStockDetailsActivity.this, mCheckDetailList);
				}
				mListView.setAdapter(mGoodsDetailsListAdapter);
				mGoodsDetailsListAdapter.notifyDataSetChanged();
				tv_checknum.setText(String.valueOf(sqlInStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
				if(rfidDialog!=null){
					if(rfidDialog.isShowing()){
						rfidDialog.dismiss();
					}
				}
				break;
			}

		}
	};
	
	//TODO ����ɨ�赽��RFID����
	protected void loadingData() {
		rfidDialog = AlertUtil.showNoButtonProgressDialog(
				InStockDetailsActivity.this, AlertUtil.getString(R.string.operate_check));
		smallExecutor.execute(new Runnable() {
			private List<GdInfo> checkInfoList;
			public void run() {
				if (barCodeAuth.equals("1")){//��֤
					List<String> rfidRightList = new ArrayList<>();
					for(String item:rfidList){
						if (checkDetailStrList.contains(item)) {
							rfidRightList.add(item);
						}
					}

					checkInfoList = sqlInStockDataSqlite.getCheckInfoList(rfidRightList,inOutCode);
					sqlInStockDataSqlite.InsertCheckDetailTempRFIDVer(checkInfoList,strShopID,strCheckID,strPositionID,inOutCode);
				}else{
					sqlInStockDataSqlite.InsertCheckDetailTempRFID(rfidList,strShopID,strCheckID,strPositionID);
				}
			
				sqlInStockDataSqlite.InsertCheckDetailRFID();
				mHandler.sendEmptyMessage(4);
			}
		});
	}
		
	private void initView() {
		et_barcode = (EditText) findViewById(R.id.et_barcode);//����
		tv_checkid = (TextView) findViewById(R.id.tv_checkid);//���
//		tv_positionid = (TextView) findViewById(R.id.tv_positionid);//��λ��
		tv_title = (TextView) findViewById(R.id.tv_title);//����
		tv_color = (TextView) findViewById(R.id.tv_color);//��ɫ
		mListView = (ListView) findViewById(R.id.listView);//����չʾlistview
		tv_size = (TextView) findViewById(R.id.tv_size);//����
//		tv_qty = (TextView) findViewById(R.id.tv_qty);//��λ����
		tv_checknum = (TextView) findViewById(R.id.tv_checknum);//�������
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_stockcount = (TextView) findViewById(R.id.tv_stockcount);
		tv_big = (TextView) findViewById(R.id.tv_big);
		tv_small = (TextView) findViewById(R.id.tv_small);
		tv_colorid = (TextView) findViewById(R.id.tv_colorid);
		tv_sizeid = (TextView) findViewById(R.id.tv_sizeid);
		tv_style = (TextView) findViewById(R.id.tv_style);
		tv_num = (TextView) findViewById(R.id.tv_num);
		cbDecrea = (CheckBox) findViewById(R.id.cbDecrea);//������ѡ��
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

		btn_query = (Button) findViewById(R.id.btn_query);//��Ʒ��ѯ
		btn_modify = (Button) findViewById(R.id.btn_modify);//�޸�����
		btn_switch = (Button) findViewById(R.id.btn_switch);//�����л�
		btn_insert = (Button) findViewById(R.id.btn_insert);//�����л�

		tv_checkid.setText(strCheckID);
//		tv_positionid.setText(strPositionID);

		tv_bianhao_xq = (TextView) findViewById(R.id.tv_bianhao_xq);//���textview
//		tv_huowei_xq = (TextView) findViewById(R.id.tv_huowei_xq);//��λtextview
		tv_bianhaosl = (TextView) findViewById(R.id.tv_bianhaosl);//�������textview
//		tv_huoweisl = (TextView) findViewById(R.id.tv_huoweisl);//��λ����textview
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
		tv_bianhao_xq.setText(importStrArrayList.get(1)+":");
//		tv_huowei_xq.setText(importStrArrayList.get(2)+":");
		tv_bianhaosl.setText(importStrArrayList.get(1)+AlertUtil.getString(R.string.quantity));
//		tv_huoweisl.setText(importStrArrayList.get(2)+AlertUtil.getString(R.string.quantity));
	}
	
	private void LoadData() {
		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
					InStockDetailsActivity.this, AlertUtil.getString(R.string.loading_data));

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Object doInBackground(Void... params) {
				try {
					/*if (bEnableRFID) {
						rfidOperate.onCreate(InStockDetailsActivity.this, DrugConstants.stockOpw);
						rfidOperate.openScannerPower(true);
					}*/
					scannerInited = true;
					if (bRFID) {
						scanOperate.openScanLight(false); 
					} else {
						scanOperate.openScanLight(true);
					}
					
					String result = "false";
					CheckDetail cde = new CheckDetail();
					cde.strShopID = strShopID;
					cde.strCheckID = strCheckID;
					cde.strPositionID = strPositionID;
					if(isRFID){
						checkDetailStrList = sqlInStockDataSqlite.getGdInfoBarcodeList(inOutCode);
					}

					// �ж���ʾ��ʽ��������򲻴�����ȷ��...
					listTmp = sqlInStockDataSqlite.GetSpecifiedCounterRecords(
							Integer.parseInt(sp.getString(
									ConfigEntity.ScanningShowLineNumbKey,
									ConfigEntity.ScanningShowLineNumb)),
							strShopID, strCheckID, strPositionID);
					// ���ݵ�ǰ�ŵ�ţ���ţ���λ�Ż�ȡ�ܹ�������
					dCheckSum = sqlInStockDataSqlite.GetSpecifiedCheckIDSum(
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
						AlertUtil.showToast(AlertUtil.getString(R.string.load_success), InStockDetailsActivity.this);
						myHandler.sendEmptyMessage(1);

					} else {
						AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
								AlertUtil.getString(R.string.load_failed), R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {
					AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
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
		// strArr ="�ⲿ��,�ڲ���,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�";  
		String[] strArr = sp.getString(ConfigEntity.ImportStrKey,
				ConfigEntity.ImportStr).split(",");
		// "�ŵ���,���,��λ,����,����,��ʽ,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�,����,ʱ��";
		sp.getString(ConfigEntity.ExportStrKey, ConfigEntity.ExportStr).split(",");
		if (strArr.length < 13) {
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
					R.string.system_import_error, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}

		// ����,����,����,���,����,��ɫID,��ɫ,����ID,����,����,С��,���,�۸�
		LoadData();
	}

	private void setData() {

		// ��ȡϵͳ����
		GetSystemSetting();
		
		//����
		ConfigureInterface();
		btn_query.setOnClickListener(this);
		btn_query.setFocusable(false);
		btn_modify.setOnClickListener(this);
		btn_insert.setOnClickListener(this);
		
		//��ȡ����
		mCheckDetailList.clear();
		mCheckDetailList = sqlInStockDataSqlite.GetStaCheckPositionDetail(strShopID, strCheckID, strPositionID);

		// ������������֤��ʽΪ��ʾ��ʱ��,����û������Ψһ��ʱ
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
			mCheckDetailList = singleElement(mCheckDetailList);
		}

		if (mCheckDetailList != null && mCheckDetailList.size() > 0) {
			mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(InStockDetailsActivity.this, mCheckDetailList);
			mListView.setAdapter(mGoodsDetailsListAdapter);
		}

		dCheckSum = sqlInStockDataSqlite.GetSpecifiedCheckIDSum(strShopID,
				strCheckID, strPositionID);
		dPositionSum = sqlInStockDataSqlite.GetSpecifiedPositionIdSum(strShopID,
				strCheckID, strPositionID);
//		tv_qty.setText(String.valueOf(dPositionSum));
		tv_checknum.setText(String.valueOf(dCheckSum));
		mListView.setOnItemClickListener(new OnItemClickListener() {
			// ���������Ʒ��ѯ
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
				CheckDetail cd = sqlInStockDataSqlite.GetSpeCheckRecord(
						strShopID, strCheckID, strPositionID,
						mCheckDetailList.get(arg2).strBar);
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

				// Ϊ0�ǲ���֤����
				if (sp.getString(ConfigEntity.BarCodeAuthKey,
						ConfigEntity.BarCodeAuth).equals("0")) {

					// ����֤��ʱ��,�����Ŀ,�Ż���ʾ�����������Ϣ
				} else {
					CheckListEntity checkListEntity = (CheckListEntity) arg0
							.getItemAtPosition(arg2);
					CheckDetail checkDetail = sqlInStockDataSqlite
							.getDetailData(checkListEntity.getStrBar());// ͨ�������ȡCheckDetail�е�����(�������ƣ���ɫ�������)

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
						if (checkDetail.getStrGdColorID() != null) {
							tv_colorid.setText(checkDetail.getStrGdColorID());
						}
						if (checkDetail.getStrProperty2() != null) {
							tv_small.setText(checkDetail.getStrProperty2());
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
		// ��CheckBox�����¼�����
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
		et_barcode.setOnEditorActionListener(new OnEditorActionListener() { //����༭��֮����������ϵĻس����Żᴥ��
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
				if(!isRfidStart){//����RFIDʱ�����������
					strBar = et_barcode.getText().toString().trim();
					if (actionId == EditorInfo.IME_ACTION_SEND ||  
			                (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {  
			            switch (event.getAction()) {  
			                case KeyEvent.ACTION_UP:
			                	if(TextUtils.isEmpty(strBar)){
			                		AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
										R.string.barCode_not_empty, R.string.ok, new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												AlertUtil.dismissDialog();
												et_barcode.requestFocus();
											}
										});	
			                		return false;
			                	}else {
		                			if(!isScan){//����ɨ��
		                				et_barcode.setText(strBar.trim());
		                				et_barcode.setSelection(strBar.length());
		                				//���ֶ����������ʱ��
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
								AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
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
	                				//���ֶ����������ʱ��
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
				boolean isExit = sqlInStockDataSqlite.checkRFIDCodeByCheckDetail(strBar,strShopID,strCheckID,strPositionID);
				if(isExit){//�Ѵ��ڸñ�ǩ
					AlertUtil.showToast(R.string.RFID_HAS_EXIST, InStockDetailsActivity.this);
				}else{
					if (barCodeAuth.equals("1")){//��֤
						checkInfoList = sqlInStockDataSqlite.getCheckInfoList(rfidList,inOutCode);
						if(checkInfoList==null){//�жϱ�ǩ�Ƿ��ڻ���������
							AlertUtil.showToast(R.string.RFID_NOT_EXIST, InStockDetailsActivity.this);
							rfidList.clear();
							rfidDialog.dismiss();
							return;
						}
						sqlInStockDataSqlite.InsertCheckDetailTempRFIDVer(checkInfoList,strShopID,strCheckID,strPositionID,inOutCode);
					}else{
						sqlInStockDataSqlite.InsertCheckDetailTempRFID(rfidList,strShopID,strCheckID,strPositionID);
					}
				
					sqlInStockDataSqlite.InsertCheckDetailRFID();
					mHandler.sendEmptyMessage(4);
				}
			}
		});
	}
	/*�������벻ִ��ˢ���б�ķ���*/
	private boolean isPassAct= false;
	@Override
	public void onClick(View v) {
		if (v == btn_modify) {
			if(!isRfidStart){
				if(mCheckDetailList.size()-1 < itemSelected){//bug1336��Խ��
					itemSelected = 0;
				}
				
				if (sp.getString(ConfigEntity.ModifyNumPWKey,ConfigEntity.ModifyNumPW).equals("1")) {
					//bug1364
					if(mCheckDetailList.size() > 0){
						isPassAct = true;
						Intent intent = new Intent(InStockDetailsActivity.this,PasswordInputScanGoodActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
						intent.putExtras(bundle);
						startActivityForResult(intent, 10);
					}else{
						AlertUtil.showAlert(InStockDetailsActivity.this, AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.no_goods_scan));
					}
	
				} else {
					isBtn = true;
					openModifyQty(true);
	
				}
			}
		} else if (v == btn_query) {
			if(!isRfidStart){
				openQueryGoods();
			}
		} else if (v == btn_switch) {
			btnSwitch_Click();
		} else if(v == btn_insert){
			//TODO ѹ������
		}
	}

	/**
	 * ��Ʒ��ѯ
	 */
	private void openQueryGoods() {
		is_intercept = false;
		if (GlobalRunConfig.GetInstance().iIsF17 == 1) {
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title,
					R.string.no_goods_query, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		Intent intent = new Intent(InStockDetailsActivity.this,
				InStockQueryGoodsSingleActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("CheckID", strCheckID);
		bundle.putString("PositionID", strPositionID);
		bundle.putString("ShopID", strShopID);
		intent.putExtras(bundle);
		startActivity(intent);
	}
/**
 * ������ѡ��״̬�л�
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
 * �޸�����
 * @param isSaved
 */
	private void openModifyQty(Boolean isSaved) {
		is_intercept = false;
		if (mCheckDetailList.size() > 0) {
			if(!isBtn){//bug1369 ʹ��itemSelected������Խ��
				itemSelected = 0;
			}
			oldQty = mCheckDetailList.get(itemSelected).dNum;
			if (isSaved) {
				strGdBar = mCheckDetailList.get(itemSelected).strBar;
				Intent intent = new Intent();
				intent.setClass(InStockDetailsActivity.this,InStockModityQtyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("GdBar", strGdBar);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_QTY_CODE);
			} else {
				if (mCheckDetailList.size() == 0) {
					Intent intent = new Intent();
					intent.setClass(InStockDetailsActivity.this,InStockModityQtyActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("GdBar", strGdBar);
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUEST_QTY_CODE);
				} else {
					Intent intent = new Intent();
					intent.setClass(InStockDetailsActivity.this,InStockModityQtyActivity.class);
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
				AlertUtil.showAlert(InStockDetailsActivity.this, AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.no_goods_scan));
			} else {
				Intent intent = new Intent();
				intent.setClass(InStockDetailsActivity.this,InStockModityQtyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("GdBar", strGdBar);
				// 6915349717011
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_QTY_CODE);
			}
		}
	}

	private void ConfigureInterface() {
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) { // 1����Ψһ��
			cbDecrea.setVisibility(View.VISIBLE);
			cbDecrea.setEnabled(true);
			btn_switch.setVisibility(View.GONE);
			btn_modify.setVisibility(View.GONE);
		} else {										//0������Ψһ��
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
				// arrTextViews[m - 2].setSingleLine(false);
			} else if (arrDisplayItem[m].equals("2")) {
				arrLinearLayout[m - 2].setVisibility(View.VISIBLE);
				// arrTextViews[m - 2].setSingleLine(true);
				// arrTextViews[m -
				// 2].setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));
			} else {
				arrLinearLayout[m - 2].setVisibility(View.GONE);
			}
		}

	}

	private void GetSystemSetting() {
		strList = new ArrayList<String>();
		// ��ȡ��������( 0�����Ƴ��ȣ�1�������ƣ�2��Χ����)
		lengthLimit = sp.getString(ConfigEntity.LengthLimitKey,ConfigEntity.LengthLimit);
		if (lengthLimit.equals("1")) {
			// ��������
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
			// ���Ʒ�Χ
			strMin = Integer.parseInt(sp.getString(
					ConfigEntity.LengthLimitRangeMinKey,
					ConfigEntity.LengthLimitRangeMin));
			strMax = Integer.parseInt(sp.getString(
					ConfigEntity.LengthLimitRangeMaxKey,
					ConfigEntity.LengthLimitRangeMax));
		}
		// ��ȡ�������
		barCodeCutSetting = sp.getString(ConfigEntity.BarCodeCutSettingKey,ConfigEntity.BarCodeCutSetting);

		arrDisplayItem = sp.getString(ConfigEntity.DisplayItemsInKey,ConfigEntity.DisplayItemsIn).split(",");
		arrTextDisplayItem = sp.getString(ConfigEntity.ImportStrKey,ConfigEntity.ImportStr).split(",");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if (bRFID) {
				if (isRfidStart) {
					//rfidOperate.stop();
					
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
//							ivScan.setImageResource(R.drawable.barcode);
							bEnableRFID = false;
							return false;
						}
						rfidList.clear();
						//rfidOperate.startScan(1, 0);
						
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

	//����2ʱֹͣRFID
	private int pressCount = 0;
	//RFID�Ƿ���
	private boolean isRfidStart = false;
	private boolean bRFID = false;
	private long firstTime = 0;
	// �Ƿ������л���RFID
	private boolean bEnableRFID = true;
	private boolean scannerInited = false;		//ɨ��ͷ�Ƿ��ʼ���ɹ�
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (event.getScanCode() == 249 || event.getScanCode() == 261) {
			if (bRFID) {
				if (pressCount == 2 && !isRfidStart) {
					//rfidOperate.stop();
					
					bRfidStoped = true;
					Comm.Awl.ReleaseWakeLock();
			        Comm.stopScan();
					
					pressCount = 0;
				}else if (pressCount > 2) {
					//rfidOperate.stop();
					
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
				// //--//���зֱ��������,����,���,����,��ɫID,��ɫ,����ID,����,�۸�
				int i = 0;
				for (i = 0; i < listTmp.size(); ++i) {
					CheckDetail item = listTmp.get(i);
					// contentList.add(String.valueOf(item.dCheckNum));
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
			gdInfo.strGdCode = et_barcode.getText().toString().trim();//�ڲ���
			checkDetail.strGdBar = et_barcode.getText().toString().trim();//����
			checkDetail.strSingleGdBar = et_barcode.getText().toString().trim(); // ��ȡǰ������,���ֶ������ں�������ʱ���ᱻ�Ķ�,���⴦��Ψһ�����
			checkDetail.strShopID = strShopID;//�ŵ���
			checkDetail.strCheckID = strCheckID;//�̵���
			checkDetail.strPositionID = strPositionID;//��λ���

			iRet = JudgeConditions(gdInfo, checkDetail); // ɨ��Ψһ�룬���ȣ���ȡ����֤�������ִ���

			globaStatus = iRet;
			if (iRet != 1) {
				et_barcode.requestFocus();
				et_barcode.selectAll();

				return;
			}

			if (cbDecrea.isChecked()) {

				// 0���ۼ�
				if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("0")) {
					BarDicreaAdd(gdInfo, checkDetail);
					// ������ʾ
				} else if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("1")) {
					BarDicreaNoAdd(gdInfo, checkDetail);
				}

				cbDecrea.setChecked(false);
			} else {
				if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("0"))
					GdNumAdd(gdInfo, checkDetail);

				// ������ʾ
				else if (sp.getString(ConfigEntity.ScanningShowModeKey,
						ConfigEntity.ScanningShowMode).equals("1"))
					if(sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("1")){//����������
						GdNumNOAdd(gdInfo, checkDetail);
					}

			}
			if (globaStatus != 0) {
				if (checkDetail != null) {

					// ������֤��ʱ��
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
						if (checkDetail.getStrGdColorID() != null) {
							tv_colorid.setText(checkDetail
									.getStrGdColorID());
						}
						if (checkDetail.getStrProperty2() != null) {
							tv_small.setText(checkDetail.getStrProperty2());
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
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
					R.string.internal_scan_set, R.string.ok, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
		} finally {
			et_barcode.requestFocus();
			et_barcode.selectAll();

			// 1Ϊ������ʾ
			if (sp.getString(ConfigEntity.ScanningShowModeKey,
					ConfigEntity.ScanningShowMode).equals("1")) {
				tv_checknum.setText(String.valueOf(sqlInStockDataSqlite
						.GetSpecifiedCheckIDSum(strShopID, strCheckID,
								strPositionID)));

			} else {
				refreshListView();
			}
		}


	}
/**
 * ɨ���listview��Ϣչʾ(����:   ����:   )
 */
	public void CheckListCounterAndShowSta() {
		if(!isRFID){
	//		tv_qty.setText(String.valueOf(dPositionSum));
			tv_checknum.setText(String.valueOf(dCheckSum));
																		//iDisplayRows(30)ɨ�������ʾ������
			if (mCheckDetailList.size() > GlobalRunConfig.GetInstance().iDisplayRows + 4) {   
				int i = 0;
				for (i = mCheckDetailList.size(); i > GlobalRunConfig.GetInstance().iDisplayRows; --i) {
					// Ϊ1ʱΪ�ۼ���ʾ
					// mListView.removeViewAt(i-1);
					if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0"))
						mCheckDetailList.remove(i - 1);
				}
					mGoodsDetailsListAdapter.setList(mCheckDetailList);
					mListView.setAdapter(mGoodsDetailsListAdapter);
					mGoodsDetailsListAdapter.notifyDataSetChanged();
			} else {
				if (mCheckDetailList != null && mCheckDetailList.size() > 0) {
	
					// 0���ۼ�
					if (sp.getString(ConfigEntity.ScanningShowModeKey,
							ConfigEntity.ScanningShowMode).equals("0")) {
						if (newQty > 0) {
							mCheckDetailList.get(itemSelected).dNum = newQty;
						}
						// 1������
						// ����ʾ����,����������ʾʱ,���ڴ�ʱ�޸�������,����Ҫ���ۼ���ʾ�����ٽ����������ĸ�ֵ
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

	// / ����ɨ������
	public void ScanSound() {
		// ��������
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
		// //�����ѡַ�����Ȼ���û�ҵ�keyֵ���򶼰�����������
		// {
		// if
		// (SupoinInven.Config.ConfigLogic.Instance.Param.ScanSound.Equals("��׼ϵͳ����"))
		// SalesPoint.Device.SoundPlay.Play(SalesPoint.Device.SoundType.ScanSound);
		// else
		// {
		// string strFullPath =
		// System.IO.Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().GetName().CodeBase.ToString());
		// strFullPath = strFullPath + "\\�����ļ�\\" +
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
 * ��ȡ����
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
	 * ��ֲ��������ݿ� 
	 * @param code
	 */
	private void BarCodeSqlitAndInsert(String code) {
		// ShopID,checkID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
		// +
		// "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg
		// ����ж�û��д
		CheckDetail mCheckDetail = new CheckDetail();
		
		if (barCodeCutSetting.equals("1")) {
			// ���벻����
			mCheckDetail.setStrGdBar(code);
			mCheckDetail.setStrShopID(strShopID);
			mCheckDetail.setStrCheckID(code);
			sqlInStockDataSqlite.InsertCheckDetail(mCheckDetail, sp.getString(
					ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),
					sp.getString(ConfigEntity.BarCodeAuthKey,
							ConfigEntity.BarCodeAuth), tempStatus, sp
							.getString(ConfigEntity.IsPutInNumKey,
									ConfigEntity.IsPutInNum), 0);
		} else if (barCodeCutSetting.equals("1"))
			sqlInStockDataSqlite.InsertCheckDetail(mCheckDetail, sp.getString(
					ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),
					sp.getString(ConfigEntity.BarCodeAuthKey,
							ConfigEntity.BarCodeAuth), tempStatus, sp
							.getString(ConfigEntity.IsPutInNumKey,
									ConfigEntity.IsPutInNum), 0);

	}

	/**
	 * �ۼ�Ψһ�������
	 * @param cg
	 * @param cd
	 * @return
	 */
	private int BarDicreaAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 0;
		int iCounter = 0;
		int flag = 0;

		// �ӻ��������в�ѯ�Ƿ��д�����
		flag = sqlInStockDataSqlite.checkUniqueCode(strShopID, strCheckID,strGdBar);
		// ���������д������ʱ��
		if (flag != 0) {
			// ��CheckDetail������Ƿ��е�ǰ����
			iCounter = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID, cd.strGdBar);
			// ���������޴�����ʱ
		} else {
			// ��CheckDetail������Ƿ��е�ǰ����
			iCounter = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strSingleGdBar);
		}

		if (iCounter != 1) {
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
					R.string.no_unique_barcode, R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							is_intercept = true;
						}
					});
			is_intercept = false;
			// return 0;
		}
		int dOriginalNum = 0;
		if (flag != 0) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strSingleGdBar);
		}
		checkDetail.dCheckNum = dOriginalNum - 1;

		sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, flag,false, 0,isBtn);
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
					// listView.Items[iIndex].SubItems[1].Text =
					// list[iIndex].dNum.ToString(DotEntity.dotFormat);
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
			AlertUtil.showAlert(InStockDetailsActivity.this, AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.no_unique_barcode));
//			refreshListView();
		}

//		refreshListView();

		return iRet;
	}

	/**
	 * ���ۼ�Ψһ�������
	 * @param cg
	 * @param cd
	 * @return
	 */
	private int BarDicreaNoAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 0;

		int iCounter = 0;
		int flag = 0;

		// �ӻ��������в�ѯ�Ƿ��д�����
		flag = sqlInStockDataSqlite.checkUniqueCode(strShopID, strCheckID,cd.strGdBar);
		// ���������д������ʱ��
		if (flag != 0) {
			// ��CheckDetail������Ƿ��е�ǰ����
			iCounter = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);
			// ���������޴�����ʱ
		} else {
			// ��CheckDetail������Ƿ��е�ǰ����
			iCounter = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,  cd.strSingleGdBar);
		}

		if (iCounter < 1) {
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
					AlertUtil.getString(R.string.no_unique_barcode), R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							is_intercept = true;
						}
					});
			is_intercept = false;
			// return 0;
		}
		int dOriginalNum = 0;

		if (flag != 0) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strSingleGdBar);
		}

		checkDetail.dCheckNum = dOriginalNum - 1;

		sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, flag,false, 0,isBtn);

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
	 * 1,���֣���ȡ
	 * @param cg
	 * @param cd
	 * @return
	 */
	public int JudgeConditions(GdInfo cg, final CheckDetail cd) {
		int iRet = 1;
		int iTmp = 0;
		final String strBar = et_barcode.getText().toString().trim();
		if (strBar.length() < 1) {
			AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title, AlertUtil.getString(R.string.barCode_not_empty),
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return 0;
		}


		// ������֤--Ψһ��,������Ψһ����ʱ
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1") && !cbDecrea.isChecked()) {

			iTmp = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID,strCheckID,cd.strGdBar);

			if (iTmp == 1) {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.dialog_title, R.string.barcode_repeat,
						R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								is_intercept = true;
							}
						});

				is_intercept = false;

				return 0;

			}
		}
	
		// ���봦�� ������֤
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
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
						 R.string.code_not_match_setting, R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				return 0;
			}
			// ��Χ����
		} else if (sp.getString(ConfigEntity.LengthLimitKey,ConfigEntity.LengthLimit).equals("2")) {
			if (strBar.length() > Integer.parseInt(sp.getString(ConfigEntity.LengthLimitRangeMaxKey,ConfigEntity.LengthLimitRangeMax)) || strBar.length() < Integer.parseInt(sp.getString(ConfigEntity.LengthLimitRangeMinKey,ConfigEntity.LengthLimitRangeMin))) {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
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

		// ������֤--�Ƿ���֤
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("1")) {
			
			GdInfo gdInfoInnerCode = new GdInfo(); 
			GdInfo gdInfoOutterCode = new GdInfo();
			
			//�ڲ��������
			gdInfoInnerCode = sqlInStockDataSqlite.GetDataByBar(strGdBar);
			//�ⲿ�������
			gdInfoOutterCode = sqlInStockDataSqlite.GetDataByCode(strGdBar);
			if (gdInfoInnerCode == null && gdInfoOutterCode == null) {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
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

			// �ڲ���
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
				cd.strGdBar = cg.strGdCode;
				// �ⲿ��
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
				cd.strGdBar = cg.strGdBarcode;
			} else {
				cd.strGdBar = strGdBar;
			}

			int checkFlag = 0;
			int checkBarFromCheckDetail = 0;

			// �ڲ���
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {

				checkFlag = sqlInStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID, cd.strGdBar);

				// �ⲿ��
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {

				checkFlag = sqlInStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID,cd.strGdBar);
			}

			// 1������Ψһ����
			if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {
				// ���������޴������ʱ��
				if (checkFlag != 1) {

				} else {
					// CheckDetail���޴�����ʱ
					if (checkBarFromCheckDetail != 1) {

						// ���������д�����ʱ
					} else {
						if (cbDecrea.isChecked()) {
							return 1;
						} else {
							AlertUtil.showAlert(InStockDetailsActivity.this,AlertUtil.getString(R.string.dialog_title),AlertUtil.getString(R.string.barcode_repeat));
							return 0;
						}
					}
				}
				// 0�ǲ�����Ψһ����
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

			// Ϊ2ʱ����ʾ
		} else if (sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth).equals("2")) {
			
			GdInfo gdInfoInnerCode = new GdInfo(); 
			GdInfo gdInfoOutterCode = new GdInfo();

			gdInfoInnerCode = sqlInStockDataSqlite.GetDataByBar(strGdBar);
			gdInfoOutterCode = sqlInStockDataSqlite.GetDataByCode(strGdBar);

			if (gdInfoInnerCode != null) {
				cg = gdInfoInnerCode;
			} else if (gdInfoOutterCode != null) {
				cg = gdInfoOutterCode;
			}

			// �ڲ���
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
				cd.strGdBar = cg.strGdCode;
				// �ⲿ��
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

			// �ڲ���
			if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {

				checkFlag = sqlInStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID, cd.strGdBar);

				// �ⲿ��
			} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {

				checkFlag = sqlInStockDataSqlite.checkUniqueCode(strShopID,strCheckID, cd.strGdBar);
				checkBarFromCheckDetail = sqlInStockDataSqlite.checkUniqueCodeFromCheckDetail(strShopID, strCheckID, cd.strGdBar);
			}

			// 0������Ψһ����
			if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
				// ��������������޴������ʱ��
				if (checkFlag < 1) { 
					if (checkBarFromCheckDetail != 1||(gdInfoInnerCode==null&&gdInfoOutterCode==null)) {
						AlertUtil.showAlert(InStockDetailsActivity.this,
								R.string.dialog_title, R.string.code_not_in_data,
								R.string.ok, new OnClickListener() {

									@Override
									public void onClick(View v) {
										cd.strGdBar = strGdBar;

										// 0����������
										if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
											openModifyQty(false);
											// 0���ۼ�
											if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0")) {
													refreshListView();
													// 1������
												} else if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("1")) {
											}
											// ������
										} else {
											sqlInStockDataSqlite.InsertCheckDetailBySpecial(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);
											// 0���ۼ�
											if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("0")) {
												refreshListView();
												// 1������
											} else if (sp.getString(ConfigEntity.ScanningShowModeKey,ConfigEntity.ScanningShowMode).equals("1")) {
												CheckListEntity list1 = new CheckListEntity();
												list1.strBar = cd.strGdBar;
												list1.dNum = 1;
												mCheckDetailList.add(0, list1);
												if (mGoodsDetailsListAdapter == null) {
													mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(
															InStockDetailsActivity.this, mCheckDetailList);
												}
												mGoodsDetailsListAdapter.setList(mCheckDetailList);
												mListView.setAdapter(mGoodsDetailsListAdapter);
												tv_checknum.setText(String.valueOf(sqlInStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
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
							Intent intent = new Intent(InStockDetailsActivity.this,PasswordInputActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
							intent.putExtras(bundle);
							startActivityForResult(intent, 12);
							is_intercept = false;

							// ��CheckDetail�д����ݵ�ʱ��
						} else {

							// 0����������
							if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {
								Intent intent = new Intent();
								intent.setClass(InStockDetailsActivity.this,InStockModityQtyActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("GdBar", strGdBar);
								intent.putExtras(bundle);
								startActivityForResult(intent, REQUEST_QTY_CODE);
								// 1�ǲ���������
							} else if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("1")) {
								tempStatus = true;
								iRet = 0;

								AlertUtil.showAlert(
										InStockDetailsActivity.this,
										R.string.dialog_title,
										R.string.code_not_in_data,
										R.string.ok,
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												cd.strGdBar = strGdBar;
												if (sp.getString(ConfigEntity.ModifyNumPWKey,ConfigEntity.ModifyNumPW).equals("1")) {
													Intent intent = new Intent(InStockDetailsActivity.this,PasswordInputActivity.class);
													Bundle bundle = new Bundle();
													bundle.putString("title",AlertUtil.getString(R.string.pass_admin));
													intent.putExtras(bundle);
													startActivityForResult(intent, 12);
													is_intercept = false;
												} else {

													sqlInStockDataSqlite.InsertCheckDetailBySpecial(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);
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
				// 1������Ψһ����,�Ҳ�����������
			} else if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1")) {
				// ������������޴������ʱ��
				if (checkFlag < 1) {
					// ���CheckDetail��Ҳ�޴�����ʱ
					if (checkBarFromCheckDetail != 1) {
						// ���"����"��ѡ�е������
						if (cbDecrea.isChecked()) {

							iRet = 1;
							refreshListView();

							// ���"����"��δ��ѡ�е������
						} else {
							AlertUtil.showAlert(
									InStockDetailsActivity.this,
									R.string.dialog_title,
									R.string.code_not_in_data,
									R.string.ok,
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											cd.strGdBar = strGdBar;
											cd.strSingleGdBar = strGdBar;
											sqlInStockDataSqlite.InsertCheckDetail(cd,sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth),tempStatus,sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum),1);

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
					// checkDetail�����ڴ��ڴ���Ŀ��ʱ��
					if (checkBarFromCheckDetail != 1) {
						refreshListView();
						tempStatus = true;
						iRet = 1;
						// checkDetail���ڴ���Ŀ��ʱ��
					} else {
						int iIndex = 0;
						// ����ѡ�ѡ�е������
						if (cbDecrea.isChecked()) {

							// 0���ڲ���
							if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("0")) {
								iIndex = getIndexOfCurBarcode(checkDetail.strGdBar);
								// 1���ⲿ��
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

								AlertUtil.showAlert(InStockDetailsActivity.this,
										AlertUtil.getString(R.string.dialog_title), AlertUtil.getString(R.string.no_unique_barcode));
								is_intercept = false;
								refreshListView();
							}

						} else {
							AlertUtil.showAlert(InStockDetailsActivity.this,
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

				// �ڲ���
				if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("0")) {
					cd.strGdBar = cg.strGdCode;
					// �ⲿ��
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

		// �Ƿ���������
		// 0λ��������
		if (sp.getString(ConfigEntity.IsPutInNumKey, ConfigEntity.IsPutInNum).equals("0")) {

			// 2����ʾ��ʱ��,��������������ʱ��
			if (sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {

			} else {
				openModifyQty(false);
			}

		} else
			cd.dCheckNum = 1;

		return iRet;
	}

	// ���ҵ�ǰ�б��и�ɨ��������ͬ��
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
	 * �ۼӴ���
	 * @param cg
	 * @param cd
	 * @return
	 */
	public int GdNumAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 1, i = 0;
		int dOriginalNum = 0;

		// �ڲ���
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
			// �ⲿ��
		} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, strGdBar);
		}

		if (dOriginalNum == 0) {
			i = sqlInStockDataSqlite.InsertCheckDetail(cd, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 0);
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
					mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(InStockDetailsActivity.this, mCheckDetailList);
				}
				mGoodsDetailsListAdapter.setList(mCheckDetailList);
				mListView.setAdapter(mGoodsDetailsListAdapter);
				mListView.setSelection(0);
				mListView.requestFocus();

				dCheckSum += cd.dCheckNum;
				dPositionSum += cd.dCheckNum;
			} else {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.error,
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
			// ������������ʱ��
			if (sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")) {

			} else {
				cd.dCheckNum = cd.dCheckNum + dOriginalNum;
				i = sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, cd, 1,false, 0,isBtn);

				if (i != 1) {
					AlertUtil.showAlert(InStockDetailsActivity.this, R.string.error,
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
								InStockDetailsActivity.this, mCheckDetailList);
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
	 * ִ�������ȡ��������
	 * @param fullBar
	 * @param bWithPrompt
	 * @return
	 */
	public int ExecuteCutReserveBar(String fullBar, boolean bWithPrompt) {
		strGdBar = fullBar;
		int iRet = 1;

		// region ���봦��-��ȡλ��������λ������
		if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,ConfigEntity.LengthCutOrKeepBarCode).equals("1")) // ��ȡ��λ��
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
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
				ConfigEntity.LengthCutOrKeepBarCode).equals("2")) // ��ȡǰλ��
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				if (bWithPrompt) {
					AlertUtil.showAlert(InStockDetailsActivity.this, R.string.warning,
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
				ConfigEntity.LengthCutOrKeepBarCode).equals("3")) // ������λ��
		{
			if (Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)) >= strGdBar.length()) {
				;
			} else {
				strGdBar = strGdBar.substring(strGdBar.length()- Integer.parseInt(sp.getString(ConfigEntity.LengthCutOrKeepBarCodeNumKey,ConfigEntity.LengthCutOrKeepBarCodeNum)),strGdBar.length());
			}
		} else if (sp.getString(ConfigEntity.LengthCutOrKeepBarCodeKey,ConfigEntity.LengthCutOrKeepBarCode).equals("4")) // ����ǰλ��
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
 * ������ʾ
 * @param cg
 * @param cd
 * @return
 */
	public int GdNumNOAdd(GdInfo cg, CheckDetail cd) {
		int iRet = 1, i = 0;
		int dOriginalNum = 0;//��ʼ����

		// �ڲ���
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode).equals("0")) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
			// �ⲿ��
		} else if (sp.getString(ConfigEntity.InOutCodeKey,ConfigEntity.InOutCode).equals("1")) {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, cd.strGdBar);
		} else {
			dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID,strCheckID, strPositionID, strGdBar);
		}

		if (dOriginalNum == 0) {
			i = sqlInStockDataSqlite.InsertCheckDetail(cd, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 0);
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
							InStockDetailsActivity.this, mCheckDetailList);
				}
				mGoodsDetailsListAdapter.setList(mCheckDetailList);
				mListView.setAdapter(mGoodsDetailsListAdapter);

				dCheckSum += cd.dCheckNum;
				dPositionSum += cd.dCheckNum;
			} else {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.error,
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
			//�̵�����
			cd.dCheckNum = cd.dCheckNum + dOriginalNum;
			i = sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, cd, 1, false,
					0,isBtn);
			if (i != 1) {
				AlertUtil.showAlert(InStockDetailsActivity.this, R.string.error,
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
						InStockDetailsActivity.this, mCheckDetailList);
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
 * �޸�����
 */
	private void saveCheckDetailQty() {
		if (isBtn&&newQty == oldQty)
			return;
		int dOriginalNum = 0;
		checkDetail.strGdBar = strGdBar;
		dOriginalNum = sqlInStockDataSqlite.GetOriginalNum(strShopID, strCheckID,strPositionID, checkDetail.strGdBar);

		// ����ɨ����ʾʱ��ѡ��һ���޸�Ϊ0ʱ����ͬ��������ݱ�Ϊ0��BUG
		int sQty = dOriginalNum - oldQty;
		if (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")&& newQty == 0 && sQty > 0) {
			if(sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum).equals("0")){//bug1355��Ϊ��������ʱ,����0������
				checkDetail.dCheckNum = newQty;
			}else{
				checkDetail.dCheckNum = dOriginalNum - oldQty;
			}
		} else {
			checkDetail.dCheckNum = newQty;
		}

		CheckDetail tempCheckDetail = null;

		// Ϊ��֤�������
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("1")||sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2")) {
			tempCheckDetail = sqlInStockDataSqlite.getDetailData(checkDetail.strGdBar);
		} else {
			tempCheckDetail = sqlInStockDataSqlite.GetSpeCheckRecord(strShopID,strCheckID, strPositionID, checkDetail.strGdBar);
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
		if(!isBtn&&checkDetail.dCheckNum==0){//��Ϊ�ۼ���ʾ���޸�������������0ʱ���������ո���
		}else{
			if (mCheckDetailList.size() > 0) {
				sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, 1,flag, mCheckDetailList.get(itemSelected).dNum,isBtn);
			} else {
				sqlInStockDataSqlite.SaveCheckDetail(dOriginalNum, checkDetail, 1,flag, 0,isBtn);
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
//		if(requestCode != 10){//�޸�������������
//			itemSelected = 0;
//		}
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

		// �޸�������ȷ����������
		if (requestCode == REQUEST_INPUTPSW_CODE) {
			Intent intent = new Intent();
			intent.setClass(InStockDetailsActivity.this, InStockModityQtyActivity.class);
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
				flag = false;
			}

			saveCheckDetailQty();

		} else if (requestCode == 10) {
			isBtn = true;
			isPass = true;
			openModifyQty(false);
		} else if (requestCode == 12) {
			sqlInStockDataSqlite.InsertCheckDetail(checkDetail, sp.getString(ConfigEntity.UsingBarCodeKey, ConfigEntity.UsingBarCode),sp.getString(ConfigEntity.BarCodeAuthKey,ConfigEntity.BarCodeAuth), tempStatus, sp.getString(ConfigEntity.IsPutInNumKey,ConfigEntity.IsPutInNum), 1);
			refreshListView();
		} else if (requestCode == 13) {
			Intent intent = new Intent();
			intent.setClass(InStockDetailsActivity.this, InStockModityQtyActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);
			startActivityForResult(intent, REQUEST_QTY_CODE);
		} else if (requestCode == Activity.RESULT_CANCELED) {
			refreshListView();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		CustomTitleBar.setActivity(this);
		if(scanOperate.mHandler==null){
			scanOperate = new ScanOperate();
			scanOperate.onCreate(InStockDetailsActivity.this, R.raw.scanok);
			scanOperate.openScannerPower(is_canScan);
		}
		scanOperate.mHandler = mHandler;
		scanOperate.onResume(this);

		
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
		// ����Activity&��ջ���Ƴ���Activity
		myApplication.getInstance().finishActivity(this);
		Comm.mInventoryHandler = null;
		myApplication.flag = false;
	}

	public void refreshListView() {
		isPassAct = false;
		mCheckDetailList.clear();
		if(isRFID){
			mCheckDetailList = sqlInStockDataSqlite.GetRFIDCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}else{
			mCheckDetailList = sqlInStockDataSqlite.GetStaCheckPositionDetail(strShopID, strCheckID, strPositionID);
		}

		Log.i("===", mCheckDetailList.size()+"");
		// ������Ϊ��ʾ��ʱ��,����û������Ψһ��ʱ
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth).equals("2") && sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("0")) {
			mCheckDetailList = singleElement(mCheckDetailList);
		}

		if (mCheckDetailList != null) {
			mGoodsDetailsListAdapter = new GoodsDetailsListAdapter(InStockDetailsActivity.this, mCheckDetailList);
			mListView.setAdapter(mGoodsDetailsListAdapter);
		}
		tv_checknum.setText(String.valueOf(sqlInStockDataSqlite.GetSpecifiedCheckIDSum(strShopID, strCheckID, strPositionID)));
//		tv_qty.setText(String.valueOf(sqlInStockDataSqlite.GetSpecifiedPositionIdSum(strShopID, strCheckID, strPositionID)));

		if (mGoodsDetailsListAdapter != null) {
			mGoodsDetailsListAdapter.notifyDataSetChanged();
		}
	}

	// ȥ����list���ظ��Ķ���Ԫ��
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
	/**�����Ʒ����*/
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