package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.adapter.SpinnerBuleAdapter;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.User;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.StringWidthWeightRandom;

public class SystemSettingActivity extends Activity implements OnClickListener {
	private String iRightLevel;
	private SQLStockDataSqlite sqlStockDataSqlite;
	private SharedPreferences sp;
	private LinearLayout ll_setting_parameters, ll_other_settting,
			ll_import_export_setting, ll_system_update, ll_scan_display,
			ll_export_setting;
	private View view1, view2, view3, view4;
	// 扫描模式
	private String[] str1 = { AlertUtil.getString(R.string.pattern2),
			AlertUtil.getString(R.string.pattern3) };
	private SpinnerBuleAdapter adapter1;
	private String str01;
	private Spinner mSpinner1;
	private int index1;
	private boolean isFrist = false; // 第一次进入不弹出Toast提示

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(SystemSettingActivity.this,
				R.string.system_settings, false);
		setContentView(R.layout.activity_system_setting);
		sqlStockDataSqlite = new SQLStockDataSqlite(SystemSettingActivity.this,
				true);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		iRightLevel = bundle.getString("iRightLevel");
		ll_setting_parameters = (LinearLayout) findViewById(R.id.ll_setting_parameters);
		ll_other_settting = (LinearLayout) findViewById(R.id.ll_other_settting);
		ll_import_export_setting = (LinearLayout) findViewById(R.id.ll_import_setting);
		ll_export_setting = (LinearLayout) findViewById(R.id.ll_export_setting);
		ll_scan_display = (LinearLayout) findViewById(R.id.ll_scan_display);
		ll_system_update = (LinearLayout) findViewById(R.id.ll_system_update);
		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		ll_setting_parameters.setOnClickListener(this);
		ll_other_settting.setOnClickListener(this);
		ll_import_export_setting.setOnClickListener(this);
		ll_system_update.setOnClickListener(this);
		ll_export_setting.setOnClickListener(this);
		ll_scan_display.setOnClickListener(this);
		if (iRightLevel.equals("2")) {
			ll_import_export_setting.setVisibility(View.VISIBLE);
			ll_system_update.setVisibility(View.VISIBLE);
			view1.setVisibility(View.VISIBLE);
			view2.setVisibility(View.VISIBLE);
			ll_export_setting.setVisibility(View.VISIBLE);
			ll_scan_display.setVisibility(View.VISIBLE);
			view3.setVisibility(View.VISIBLE);
			view4.setVisibility(View.VISIBLE);
		} else {
			ll_import_export_setting.setVisibility(View.GONE);
			ll_system_update.setVisibility(View.GONE);
			view1.setVisibility(View.GONE);
			view2.setVisibility(View.GONE);
			ll_export_setting.setVisibility(View.GONE);
			ll_scan_display.setVisibility(View.GONE);
			view3.setVisibility(View.GONE);
			view4.setVisibility(View.GONE);
		}
		initPattern();
	}

	private void initPattern() {
		mSpinner1 = (Spinner) findViewById(R.id.spinner1);
		adapter1 = new SpinnerBuleAdapter(this, str1);
		mSpinner1.setAdapter(adapter1);
		str01 = sp.getString(ConfigEntity.ScanPatternKey,
				ConfigEntity.ScanPattern);
		index1 = Integer.parseInt(str01);
		// 设置默认值
		mSpinner1.setSelection(Integer.parseInt(str01), true);
		mSpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapter,
							View arg1, int arg2, long arg3) {
						str01 = arg2 + "";
						index1 = arg2;
						sp.edit().putString(ConfigEntity.ScanPatternKey, str01)
								.commit();
						if (isFrist) {
							isFrist = false;
						} else {
							String tishiString = str01.equals("0") ?AlertUtil.getString(R.string.pattern5)
									: AlertUtil.getString(R.string.pattern6);
							AlertUtil.showToast(
									AlertUtil.getString(R.string.pattern4)
											+ tishiString,
									SystemSettingActivity.this);
						}
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				AlertUtil.showAlert(SystemSettingActivity.this,
						R.string.dialog_title, R.string.sure_save, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								myHandler.sendEmptyMessage(1);

							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								finish();
							}
						});
				break;
			case 1:
				AlertUtil.showAlert(SystemSettingActivity.this,
						R.string.dialog_title, R.string.confirm_again,
						R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								myHandler.sendEmptyMessage(2);
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				break;
			case 2:
				StringWidthWeightRandom random = new StringWidthWeightRandom(
						new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
								'8', '9' });
				sp.edit()
						.putString(ConfigEntity.ConfigureItemKey,
								random.getNextString(64))
						.putString(ConfigEntity.ReplaceIndirDBKey, "1")
						.commit();
				User user = new User();
				user.iUserLevel = 1;
				user.strPassword = random.getNextString(64);
				sqlStockDataSqlite.UpdateSpePassword(user);
				DeleteAllData();
				break;

			case 3:
				GlobalRunConfig.GetInstance().iRightLevel = 0;
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void DeleteAllData() {
		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
					SystemSettingActivity.this,
					AlertUtil.getString(R.string.deleting_data));

			@Override
			protected void onPreExecute() {
			}

			@Override
			protected Object doInBackground(Void... params) {
				try {
					String result = "false";
					int iRet = sqlStockDataSqlite.DeleteAllShops();
					int iRet1 = sqlStockDataSqlite.DeleteAllLog();
					if (iRet == 1 && iRet1 == 1)
						result = "true";
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					return e;
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				dialog.dismiss();
				if (!TextUtils.isEmpty(result.toString())) {
					if (result.toString().equals("true")) {
						AlertUtil.showAlert(SystemSettingActivity.this,
								R.string.dialog_title, R.string.delete_success,
								R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
										myHandler.sendEmptyMessage(3);
									}
								});

					} else {
						AlertUtil.showAlert(SystemSettingActivity.this,
								R.string.dialog_title, R.string.delete_failed,
								R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {
					AlertUtil.showAlert(SystemSettingActivity.this,
						R.string.dialog_title, R.string.delete_failed,
						R.string.ok, new View.OnClickListener() {

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			if (iRightLevel.equals("0")) {
				finish();
			} else {
				// myHandler.sendEmptyMessage(0);
			}
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

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.ll_setting_parameters:
			intent.setClass(SystemSettingActivity.this,
					SettingParametersActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("iRightLevel", iRightLevel);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.ll_other_settting:
			intent.setClass(SystemSettingActivity.this,
					OtherSettingActivity.class);
			Bundle bundle1 = new Bundle();
			bundle1.putString("iRightLevel", iRightLevel);
			intent.putExtras(bundle1);
			startActivity(intent);
			break;
		case R.id.ll_import_setting:
			intent.setClass(SystemSettingActivity.this, ImSettingActivity.class);
			intent.putExtra("tag", "1");
			startActivity(intent);
			break;
		case R.id.ll_export_setting:
			intent.setClass(SystemSettingActivity.this,
					DataExportOutSettingActivity.class);
			intent.putExtra("tag", "0");
			startActivity(intent);
			break;
		case R.id.ll_scan_display:
			intent.setClass(SystemSettingActivity.this,
					ScanDisplaySettingActivity.class);
			intent.putExtra("tag", "1");
			startActivity(intent);
			break;
		case R.id.ll_system_update:
			intent.setClass(SystemSettingActivity.this, UpdateActivity.class);
			startActivity(intent);
			break;
		}
	}
}
