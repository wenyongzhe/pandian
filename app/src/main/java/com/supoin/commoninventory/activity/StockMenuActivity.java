package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.BarSplitLenSet;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.LengthSet;
import com.supoin.commoninventory.entity.VerifyWay;
import com.supoin.commoninventory.instore.activity.InStockMainActivity;
import com.supoin.commoninventory.outstore.activity.OutStockMainActivity;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Constants;
import com.supoin.commoninventory.util.CustomEditDialog;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.Utility;
import com.uhf.uhf.Common.Comm;
import com.uhf.uhf.UHF1.UHF1Function.AndroidWakeLock;
import com.uhf.uhf.UHF1.UHF1Function.SPconfig;

import java.util.Date;

import static com.uhf.uhf.Common.Comm.Awl;
import static com.uhf.uhf.Common.Comm.checkDevice;
import static com.uhf.uhf.Common.Comm.context;
import static com.uhf.uhf.Common.Comm.isrun;
import static com.uhf.uhf.Common.Comm.soundPool;

public class StockMenuActivity extends BaseActivity implements OnClickListener {
	private LinearLayout ll_scan, ll_count_data, ll_data_export,
			ll_delete_data, ll_system_setting, ll_data_import, ll_store_in, ll_store_out;
	private TextView tv_export, tv_img_top;
	private ImageView img_export;

	private SharedPreferences sp;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private String content;
	public static final int REQUEST_CODE = 1;
	private CustomEditDialog mEditDialog;
	public static final int REQUEST_INPUTPSW_CODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		// CustomTitleBar.getTitleBar(StockMenuActivity.this, "主菜单", false);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sqlStockDataSqlite = new SQLStockDataSqlite(StockMenuActivity.this,true);
		setContentView(R.layout.activity_stockmenu);
		tv_img_top = (TextView) findViewById(R.id.tv_img_top);
		ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
		ll_count_data = (LinearLayout) findViewById(R.id.ll_count_data);
		ll_data_export = (LinearLayout) findViewById(R.id.ll_data_export);
		ll_delete_data = (LinearLayout) findViewById(R.id.ll_delete_data);
		ll_system_setting = (LinearLayout) findViewById(R.id.ll_system_setting);
		ll_data_import = (LinearLayout) findViewById(R.id.ll_data_import);
		tv_export = (TextView) findViewById(R.id.tv_export);
		img_export = (ImageView) findViewById(R.id.img_export);

		//wyz 2017.4.28
		ll_store_in = (LinearLayout) findViewById(R.id.ll_store_in);
		ll_store_out = (LinearLayout) findViewById(R.id.ll_store_out);

		tv_img_top.setOnClickListener(this);
		ll_scan.setOnClickListener(this);
		ll_count_data.setOnClickListener(this);
		ll_data_export.setOnClickListener(this);
		ll_delete_data.setOnClickListener(this);
		ll_system_setting.setOnClickListener(this);
		ll_data_import.setOnClickListener(this);

		//wyz 2017.4.28
		ll_store_in.setOnClickListener(this);
		ll_store_out.setOnClickListener(this);

		initView();
		// InitParams();
		sp.edit().putBoolean("is_first", false).commit();
		if (sp.getString(ConfigEntity.SetTimeKey, ConfigEntity.SetTime).equals(
				"1")) {
			OpenDateSetDailog();
		}
		Awl = new AndroidWakeLock((PowerManager) getSystemService(Context.POWER_SERVICE));
		uhfhandler.postDelayed(init, 500);
	}

	public Handler uhfhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			try {

			} catch (Exception e)
			{
				e.printStackTrace();
				Log.e("uhfhandler", e.getMessage().toString());
			}
		}
	};

	Runnable init = new Runnable(){
		@Override
		public void run(){
			initRfid();
		}
	};

	private void initRfid(){

		Log.d("Main", "connect SUC");
		Comm.repeatSound=true;
		Comm.app = getApplication();
		Comm.spConfig = new SPconfig(this);

		context = this;
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.beep51, 1);

		checkDevice();
		Comm.initWireless(Comm.app);
		Comm.connecthandler = connectH;
		Comm.Connect();
		Log.d("test", "connect");
	}

	public Handler connectH = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			try {
				Comm.mInventoryHandler= uhfhandler;
				Bundle bd = msg.getData();
				String strMsg = bd.get("Msg").toString();
				if (!TextUtils.isEmpty(strMsg)) {
					Log.e("connectH", strMsg.toString());

				} else
					Log.e("connectH", "模块初始化失败".toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void releaseRfid() {

		try {
			if (isrun)
				Comm.stopScan();
			Comm.powerDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openScanner(boolean on){
		if (on) {
			sendBroadcast(new Intent("com.android.server.scannerservice.onoff")
					.putExtra("scanneronoff", 1));

		} else {
			sendBroadcast(new Intent("com.android.server.scannerservice.onoff")
					.putExtra("scanneronoff", 0));
		}
	}

	private void initView() {
		if (sp.getString(ConfigEntity.BarCodeAuthKey, ConfigEntity.BarCodeAuth)
				.equals("0")) {
			tv_export.setVisibility(View.GONE);
			img_export.setVisibility(View.VISIBLE);
			ll_data_import.setClickable(false);
		} else {
			tv_export.setVisibility(View.VISIBLE);
			img_export.setVisibility(View.GONE);
			ll_data_import.setClickable(true);
		}
	}

	public void OpenDateSetDailog() {
		mEditDialog = new CustomEditDialog(StockMenuActivity.this);
		EditText editText = (EditText) mEditDialog.getEditText();// 方法在CustomDialog中实现
		Date date = new Date();
		String timeStr = Utility.getCurDateTime();
		// editText.setText(timeStr.substring(0,timeStr.length()-3));
		dimBehind(mEditDialog);
		mEditDialog.setTitle("时间设置");
		mEditDialog.setEditText(timeStr.substring(0, timeStr.length() - 3));
		mEditDialog.setOnPositiveListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// dosomething youself

				mEditDialog.dismiss();
				mEditDialog = null;
				finish();
			}
		});
		mEditDialog.setOnNegativeListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mEditDialog.dismiss();
				mEditDialog = null;
				finish();
			}
		});
		mEditDialog.show();
		adjustDialogPosition(mEditDialog);
	}

	private static void adjustDialogPosition(Dialog dialog) {
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL
				| Gravity.CENTER_VERTICAL);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setAttributes(lp);
	}

	private static void dimBehind(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 1.0f;
		lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
	}

	private void InitParams() {
		// if(sp.getString(ConfigEntity.IsCloseWIFIKey,
		// ConfigEntity.IsCloseWIFI).equals("1"))
		// {
		// Thread thClose = new Thread(new ThreadStart(CloseWifi));
		// thClose.Start();
		// }

		// List<VerifyWay> verifyWayList=sqlStockDataSqlite.GetVerifyWay();

		GlobalRunConfig.GetInstance().iBarVerifyWay = sqlStockDataSqlite
				.GetBarVerifyWayInUsage(10, 1).iItem;
		// ConfigImport();
		GlobalRunConfig.GetInstance().iSingleBarVerify = sqlStockDataSqlite
				.GetBarVerifyWayInUsage(20, 1).iItem;

		VerifyWay enTmp = sqlStockDataSqlite.GetBarVerifyWayInUsage(10, 3);
		if (enTmp != null) {
			GlobalRunConfig.GetInstance().iScanDisplayWay = enTmp.iItem;

			if (!Utility.isEmpty(enTmp.strValue)) {
				GlobalRunConfig.GetInstance().iDisplayRows = Integer
						.parseInt(enTmp.strValue);
			}
		}
		GlobalRunConfig.GetInstance().iScanInputQty = sqlStockDataSqlite
				.GetBarVerifyWayInUsage(10, 4).iItem;
		enTmp = sqlStockDataSqlite.GetBarVerifyWayInUsage(10, 5);
		if (enTmp != null) {
			GlobalRunConfig.GetInstance().iCurTailWay = enTmp.iItem;

			if (!Utility.isEmpty(enTmp.strValue)) {
				GlobalRunConfig.GetInstance().iCurTailLen = Integer
						.parseInt(enTmp.strValue);
			}
		}
		GlobalRunConfig.GetInstance().iBarOutIn = sqlStockDataSqlite
				.GetBarVerifyWayInUsage(10, 6).iItem;
		LengthSet lengthSet = new LengthSet();
		lengthSet = sqlStockDataSqlite.GetLengthSetInUsage();
		GlobalRunConfig.GetInstance().lengthSet.iItem = lengthSet.iItem;
		GlobalRunConfig.GetInstance().lengthSet.iLen1 = lengthSet.iLen1;
		GlobalRunConfig.GetInstance().lengthSet.iLen2 = lengthSet.iLen2;
		GlobalRunConfig.GetInstance().lengthSet.iLen3 = lengthSet.iLen3;
		GlobalRunConfig.GetInstance().lengthSet.iLen4 = lengthSet.iLen4;
		GlobalRunConfig.GetInstance().lengthSet.iLen5 = lengthSet.iLen5;
		GlobalRunConfig.GetInstance().lengthSet.iLen6 = lengthSet.iLen6;
		GlobalRunConfig.GetInstance().lengthSet.iLenMin = lengthSet.iLenMin;
		GlobalRunConfig.GetInstance().lengthSet.iLenMax = lengthSet.iLenMax;

		BarSplitLenSet barSplit = new BarSplitLenSet();
		barSplit = sqlStockDataSqlite.GetBarSplitLenSetInUsage();
		GlobalRunConfig.GetInstance().barSplitSet.iItem = barSplit.iItem;
		GlobalRunConfig.GetInstance().barSplitSet.iArtNO = barSplit.iArtNO;
		GlobalRunConfig.GetInstance().barSplitSet.iArtNOSerial = barSplit.iArtNOSerial;
		GlobalRunConfig.GetInstance().barSplitSet.iStyle = barSplit.iStyle;
		GlobalRunConfig.GetInstance().barSplitSet.iStyleSerial = barSplit.iStyleSerial;
		GlobalRunConfig.GetInstance().barSplitSet.iColor = barSplit.iColor;
		GlobalRunConfig.GetInstance().barSplitSet.iColorSerial = barSplit.iColorSerial;
		GlobalRunConfig.GetInstance().barSplitSet.iSize = barSplit.iSize;
		GlobalRunConfig.GetInstance().barSplitSet.iSizeSerial = barSplit.iSizeSerial;
		GlobalRunConfig.GetInstance().barSplitSet.strSeparator1 = barSplit.strSeparator1;
		GlobalRunConfig.GetInstance().barSplitSet.strSeparator2 = barSplit.strSeparator2;
		// SupoinInven.DataUtility.DotEntity.dotCounter =
		// Convert.ToInt32(SupoinInven.Config.ConfigLogic.Instance.Param.DotCounter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

			case KeyEvent.KEYCODE_BACK:

				if (sp.getString(ConfigEntity.ExitSystemPSWKey, ConfigEntity.ExitSystemPSW).equals("0")) {
					AlertUtil.showAlert(StockMenuActivity.this, R.string.dialog_title,
							R.string.exit_application, R.string.ok, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();

									releaseRfid();
									openScanner(true);

									android.os.Process.killProcess(android.os.Process.myPid());
									System.gc();
									System.exit(0);
									finish();
									//myApplication.getInstance().AppExit();
								}
							}, R.string.cancel, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									AlertUtil.dismissDialog();
								}
							});
				}else if (sp.getString(ConfigEntity.ExitSystemPSWKey, ConfigEntity.ExitSystemPSW).equals("1")) {
					Intent intent = new Intent(StockMenuActivity.this,PasswordInputActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUEST_INPUTPSW_CODE);

				}


				break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		// 密码正确返回做处理
		if (requestCode == REQUEST_CODE) {
			Bundle cBundle = data.getExtras();
			String iRightLevel = cBundle.getString("iRightLevel");
			Intent intent = new Intent();
			intent.setClass(StockMenuActivity.this, SystemSettingActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("iRightLevel", iRightLevel);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		if (requestCode == REQUEST_INPUTPSW_CODE) {
			AlertUtil.showAlert(StockMenuActivity.this, R.string.dialog_title,
					R.string.exit_application, R.string.ok, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();

							releaseRfid();
							openScanner(true);

							android.os.Process.killProcess(android.os.Process.myPid());
							System.gc();
							System.exit(0);
							finish();
							//myApplication.getInstance().AppExit();
						}
					}, R.string.cancel, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
		}
		super.onActivityResult(requestCode, resultCode, data);
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
		initView();

		Comm.mInventoryHandler= uhfhandler;

		super.onResume();
	}

	//wyz 2017.4.28
	private void ShowNoConfiguredDialog() {
		AlertUtil.showAlert(StockMenuActivity.this, R.string.dialog_title,
				R.string.not_configured, R.string.ok,
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertUtil.dismissDialog();
					}
				}
		);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			//wyz 2017.4.28 start
			case R.id.ll_store_out:
				if (GlobalRunConfig.GetInstance().iRightLevel == 2) {
					ShowNoConfiguredDialog();
					return;
				}
				intent.setClass(StockMenuActivity.this, OutStockMainActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_store_in:
				if (GlobalRunConfig.GetInstance().iRightLevel == 2) {
					ShowNoConfiguredDialog();
					return;
				}
				intent.setClass(StockMenuActivity.this, InStockMainActivity.class);
				startActivity(intent);
				break;
			//wyz end

			case R.id.ll_scan:
				if (GlobalRunConfig.GetInstance().iRightLevel == 2) {
					ShowNoConfiguredDialog();
					return;
				}
				intent.setClass(StockMenuActivity.this, ScanGoodsActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_count_data:
				intent.setClass(StockMenuActivity.this, CheckTypeSelectActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_data_export:
				intent.setClass(StockMenuActivity.this, DataExportOutActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_delete_data:
				intent.setClass(StockMenuActivity.this, DeleteDataActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_system_setting:
				intent = new Intent(StockMenuActivity.this,
						PasswordInputActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", AlertUtil.getString(R.string.pass_admin));
				bundle.putString(Constants.KEY_PASSWORD_TYPE,
						Constants.VALUE_PASSWORD_SYSTEM_SETTING);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_CODE);
				break;
			case R.id.ll_data_import:
				AlertUtil.showAlert(StockMenuActivity.this, R.string.dialog_title,
						R.string.import_basic_data, R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								Intent intent = new Intent();
								intent.setClass(StockMenuActivity.this,DataExportInActivity.class);
								startActivity(intent);
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				break;

		}

	}



}