package com.supoin.commoninventory.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.db.SQLDataSqlite;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.VersionInfo;
import com.supoin.commoninventory.publicontent.SettingLoad;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.receiver.ConnectionChangeReceiver;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.CustomEditDialog;
import com.supoin.commoninventory.util.FileIO;
import com.supoin.commoninventory.util.NetworkUtilities;
import com.supoin.commoninventory.util.Utility;
import com.supoin.commoninventory.util.ZipCompress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {
	private Handler handler;
	public String mSavePath = null;
	public int progress = 0;
	public String curVersion = null;
	public String newVersion = null;
	public boolean cancelUpdate = false;
	public boolean butfunc = false;
	public static final int VERSION_NEW = 1;
	public static final int VERSION_UPDATE = 2;
	public static final int DOWN_ING = 3;
	public static final int DOWN_END = 4;
	public ProgressDialog progressDialog;
	HashMap<String, String> mHashMap;
	private static String title = "";
	private SharedPreferences sp;
	private CustomEditDialog mEditDialog;
	public ProgressBar progressBar;
	private ProgressDialog tprogressDlg = null;
	private ProgressDialog fprogressDlg = null;
	private Boolean canCancel = false;
	private Boolean canPress = false;
	private Boolean isRun = false;
	private SQLDataSqlite sqlStockDataSqlite;
	protected static final String TAG = "MainActivity";
	private String updateFile = SystemConst.updateFile;
	private String BaseDataPath = SystemConst.baseDataPath;
	private String DownloadPath = SystemConst.downloadPath;
	private List<VersionInfo> versionInfoList = new ArrayList<VersionInfo>();
	private ConnectionChangeReceiver connectionChangeReceiver;
	private static Boolean isThreadStop = false;
	// 扫描设置
	public static final String SCN_CUST_DB_OUTPUT_MODE = "SCANNER_OUTPUT_MODE";
	private TextView tv_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题
		setContentView(R.layout.activity_main);
		tv_version = (TextView) findViewById(R.id.tv_version);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			tv_version.setText(AlertUtil.getString(R.string.versionV) + version);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Build bd = new Build();
		String brand = bd.BRAND;
		if (!brand.equals("SUPOIN")) {
			System.out.print("您使用的是非法的设备，请选择销邦设备安装！");
			AlertUtil.showAlert(MainActivity.this, "警告", "非法设备，请选择销邦设备安装！",
					"确定", new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							finish();
						}
					});
			return;
		}*/
		/*if (!Build.MANUFACTURER.equals("SUPOIN")){
			AlertUtil.showAlert(MainActivity.this, "警告", "非法设备，请选择销邦设备安装！",
					"确定", new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
							finish();
						}
					});
			return;
		}*/
		
	/*	// 扫描设置默认为API
		boolean enableApi = (Settings.Secure.getInt(getContentResolver(),SCN_CUST_DB_OUTPUT_MODE, 0) > 0);
		if (!enableApi) {
			Settings.System.putInt(getContentResolver(),SCN_CUST_DB_OUTPUT_MODE, 1);
		}*/
		
		sqlStockDataSqlite = new SQLStockDataSqlite(MainActivity.this, true);
		
		
		SettingLoad.SettingLoad(this, sp.getBoolean("is_first", true),MODE_PRIVATE);
						
		if (Utility.isConnectingToInternet(getApplicationContext())) {
			{
				//检查更新
				check_update();
//				OpenDateOrNextActivity();
			}
		} else {
			OpenDateOrNextActivity();
		}
		
		ConnectionChangeReceiver.ConnectionSuccessCallBack succCB = new ConnectionChangeReceiver.ConnectionSuccessCallBack() {
			@Override
			public void exec() {
				// TODO Auto-generated method stub
				if (connectionChangeReceiver != null)
					unregisterReceiver(connectionChangeReceiver);
				 check_update();
//				OpenDateOrNextActivity();
			}
		};
		connectionChangeReceiver = new ConnectionChangeReceiver(succCB);
		if (!Utility.isConnectingToInternet(MainActivity.this)) {
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			registerReceiver(connectionChangeReceiver, filter);
			return;
		}
	}

	private void startLogoTimer() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == DrugSystemConst.MSG_QUIT) {
					Intent intent = new Intent();
					// if (sp.getBoolean("is_first",
					// true)/*DrugDrugSystemConst.isFirst*/) {
					// // intent.setClass(MainActivity.this,
					// DateActivity.class);
					// // startActivity(intent);
					// OpenDateSetDailog();
					// } else {
					intent.setClass(MainActivity.this, LoginActivity.class);
					startActivity(intent);
					// }
					// finish();
				}
			}
		};
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(DrugSystemConst.LOGO_TIMEOUT);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Utility.deBug(TAG, "startLogoTimer" + e.getMessage());
				}
				// String dbFileName = "drugcodeData.sdf";
				// String dbFileName1 = "customerData.sdf";
				// File file = new
				// File("data/data/"+getPackageName()+"/databases/"+dbFileName);
				// File file1 = new
				// File("data/data/"+getPackageName()+"/databases/"+dbFileName1);
				// try {
				// //从外部资源文件夹中获取数据库，并将其保存到数据库文件夹中
				// StreamUtil.getInputStream(getAssets().open(dbFileName), new
				// FileOutputStream(file)); //将外部资源中的数据库文件拷贝到数据库文件夹中
				// SQLiteDatabase db = openOrCreateDatabase(dbFileName,
				// Context.MODE_PRIVATE, null);
				// StreamUtil.getInputStream(getAssets().open(dbFileName1), new
				// FileOutputStream(file1)); //将外部资源中的数据库文件拷贝到数据库文件夹中
				// SQLiteDatabase db1 = openOrCreateDatabase(dbFileName1,
				// Context.MODE_PRIVATE, null);
				// // Cursor c = db.query("city_info", new
				// String[]{"_id","city_name"}, null, null, null, null, null);
				// // if(c!=null){
				// // //查询外部数据库中的城市数据，并在新构建的数据库中将原始数据和对应的拼音/首字母也保存到数据库中，待后续查询使用
				// // while(c.moveToNext()){
				// // int id = c.getInt(c.getColumnIndex("_id")); //获取id
				// // String cityName =
				// c.getString(c.getColumnIndex("city_name")); //获取城市名称
				// // citys.add(new CityEntity(id, cityName,
				// pyUtil.getStringPinYin(cityName),
				// pyUtil.getFirstSpell(cityName)));
				// //pyutil是汉字与拼音转化的工具类，需要引入Jar包：pinyin4j
				// // }
				// // c.close();
				// // }
				// db.close();
				// db1.close();
				// Message msg = new Message();
				// msg.what = DrugDrugSystemConst.MSG_QUIT;
				// handler.sendMessage(msg);
				// } catch (FileNotFoundException e) {
				// e.printStackTrace();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				Message msg = new Message();
				msg.what = DrugSystemConst.MSG_QUIT;
				handler.sendMessage(msg);
			}
		};
		new Thread(runnable).start();
	}

	private void OpenDateOrNextActivity() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, StockMenuActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private static void dimBehind(Dialog dialog) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 1.0f;
		lp.flags = lp.flags | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lp.dimAmount = 0.5f;
		dialog.getWindow().setAttributes(lp);
	}

	// public static void dismissDialog() {
	// if (mDialog != null && mDialog.isShowing()) {
	// mDialog.dismiss();
	// }
	// mDialog = null;
	// }
	public void OpenDateSetDailog() {
		mEditDialog = new CustomEditDialog(MainActivity.this);
		EditText editText = (EditText) mEditDialog.getEditText();// 方法在CustomDialog中实现
		Date date = new Date();
		String timeStr = Utility.getCurDate();
		// editText.setText(timeStr.substring(0,timeStr.length()-3));
		dimBehind(mEditDialog);
		mEditDialog.setTitle("时间设置");
		mEditDialog.setEditText(timeStr.substring(0, timeStr.length() - 3));
		mEditDialog.setOnPositiveListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// dosomething youself
				// String strInput=editText.getText().toString();
				mEditDialog.dismiss();
				mEditDialog = null;
				Intent intent = new Intent();
				if (sp.getBoolean("is_first", true)/*
													 * DrugDrugSystemConst.isFirst
													 */)
					// intent.setClass(MainActivity.this,
					// CorpChoiceActivity.class);
					startActivity(intent);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从栈中移除该Activity
		myApplication.getInstance().finishActivity(this);
	}

	Handler myhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case VERSION_NEW:
				AlertUtil.showToast(AlertUtil.getString(R.string.version_latest), MainActivity.this);
				OpenDateOrNextActivity();
				// startLogoTimer();
				break;
			case VERSION_UPDATE:
				AlertUtil.showAlert(MainActivity.this, R.string.dialog_title,
						R.string.version_new, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								butfunc = !butfunc;
								ShowProgress(AlertUtil.getString(R.string.version_downloading));
								// 启动线程开始下载
								Thread thread = new Thread(new Runnable() {
									public void run() {
										// TODO Auto-generated method stub
										try {
											// 判断SD卡是否存在，并且是否具有读写权限
											if (Environment
													.getExternalStorageState()
													.equals(Environment.MEDIA_MOUNTED)) {
												// 获得存储卡的路径
												String sdpath = Environment
														.getExternalStorageDirectory()
														+ "/";
												mSavePath =SystemConst.updateFile;
												URL url = new URL(
														"http://192.168.25.75:8077/"
																+ DrugSystemConst.appApkName/*
																							 * mHashMap
																							 * .
																							 * get
																							 * (
																							 * "link"
																							 * )
																							 */);
												// 创建连接
												HttpURLConnection conn = (HttpURLConnection) url
														.openConnection();
												conn.connect();
												// 获取文件大小
												int length = conn
														.getContentLength();
												// 创建输入流
												InputStream is = conn
														.getInputStream();

												File file = new File(mSavePath);
												// 判断文件目录是否存在
												if (!file.exists()) {
													file.mkdir();
												}
												File apkFile = new File(
														mSavePath,
														DrugSystemConst.appApkName);
												FileOutputStream fos = new FileOutputStream(
														apkFile);
												int count = 0;
												// 缓存
												byte buf[] = new byte[1024];
												// 写入到文件中
												do {
													int numread = is.read(buf);
													count += numread;
													// 计算进度条位置
													progress = (int) (((float) count / length) * 100);
													// 更新进度
													myhandler
															.sendEmptyMessage(DOWN_ING);
													if (numread <= 0) {
														// 下载完成
														myhandler
																.sendEmptyMessage(DOWN_END);
														break;
													}
													// 写入文件
													fos.write(buf, 0, numread);
												} while (!cancelUpdate);// 点击取消就停止下载.
												fos.close();
												is.close();
											}
										} catch (MalformedURLException e) {
											e.printStackTrace();
											// startLogoTimer();
											Utility.deBug(
													TAG,
													"myhandler"
															+ e.getMessage());
											OpenDateOrNextActivity();
										} catch (IOException e) {
											e.printStackTrace();
											Utility.deBug(
													TAG,
													"myhandler"
															+ e.getMessage());
											OpenDateOrNextActivity();
											// startLogoTimer();
										}
										// 取消下载对话框显示

									}
								});
								thread.start();
								AlertUtil.dismissDialog();
							}
						}, R.string.cancel, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								OpenDateOrNextActivity();
								// startLogoTimer();
							}
						});
				break;
			case DOWN_ING:

				break;
			case DOWN_END:
				// 获得存储卡的路径
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				mSavePath = SystemConst.updateFile;
				// 提示安装
				File apkfile = new File(mSavePath, DrugSystemConst.appApkName);
				if (!apkfile.exists()) {
					return;
				}
				// 通过Intent安装APK文件
				Intent i = new Intent(Intent.ACTION_VIEW);

				i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
						"application/vnd.android.package-archive");
				MainActivity.this.startActivity(i);
				finish();
				break;
			default:
				return;
			}
		}
	};

	private void ShowProgress(final String title, Boolean canCancel,
			Boolean canPress) {
		if (canCancel) {
			if (tprogressDlg != null) {
				if (this.title != title) {
					this.title = title;
					tprogressDlg.show();
					handler.post(changeMessage);
				}
			} else {
				this.title = title;
				tprogressDlg = AlertUtil.showProgressDialog(MainActivity.this,
						title, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// ExecutionTrans();
								tprogressDlg.dismiss();
							}
						});
			}

		} else {
			if (fprogressDlg != null) {
				if (this.title != title) {
					this.title = title;
					// handler.sendEmptyMessage(0x01);
					fprogressDlg.show();
					handler.post(changeMessage);
				}
			} else {
				this.title = title;
				fprogressDlg = AlertUtil.showProgressDialog(MainActivity.this,
						title, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								fprogressDlg.dismiss();
							}
						});
			}
		}
		this.canCancel = canCancel;
		this.canPress = canPress;
		isRun = true;
	}

	Runnable changeMessage = new Runnable() {
		@Override
		public void run() {
			if (canCancel)
				AlertUtil.setMessage(title);
			else
				AlertUtil.setMessage(title);
		}
	};

	public void ShowProgress(String title) {
		// this.PowerMgr.AllowPowerOff(false);
		if (canCancel) {
			if (tprogressDlg != null) {
				if (this.title != title) {
					this.title = title;
					// handler.sendEmptyMessage(0x01);
					tprogressDlg.show();
					handler.post(changeMessage);
				}

			} else {
				this.title = title;
				tprogressDlg = AlertUtil.showProgressDialog(MainActivity.this,
						title, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// ExecutionTrans();
								tprogressDlg.dismiss();
								OpenDateOrNextActivity();
							}
						});
			}

		} else {
			if (fprogressDlg != null) {
				if (this.title != title) {
					this.title = title;
					fprogressDlg.show();
					handler.post(changeMessage);
				}

			} else {
				this.title = title;
				fprogressDlg = AlertUtil.showProgressDialog(MainActivity.this,
						title, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								fprogressDlg.dismiss();
								OpenDateOrNextActivity();
							}
						});
			}
		}
	}

	private void HideProgress() {
		if (canCancel) {
			if (tprogressDlg != null)
				tprogressDlg.dismiss();
			tprogressDlg = null;
		} else {
			if (fprogressDlg != null)
				fprogressDlg.dismiss();
			fprogressDlg = null;
		}
		isRun = false;
	}

	private Handler resultHandler = new Handler() {
		public void handleMessage(Message msg) {

			String strTitle = (String) msg.obj;
			if (strTitle.equals(null))
				strTitle = AlertUtil.getString(R.string.version_downloading_failed);
			switch (msg.what) {
			case 1:
				AlertUtil.showAlert(MainActivity.this, R.string.dialog_title, strTitle, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								myhandler.sendEmptyMessage(DOWN_END);
							}
						});
				break;
			case 0:
				// ShowProgress(strTitle);
				AlertUtil.showAlert(MainActivity.this, R.string.dialog_title, strTitle, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								OpenDateOrNextActivity();
							}
						});
				break;
			}
			super.handleMessage(msg);
		}
	};
	private Handler startHandler = new Handler() {
		public void handleMessage(Message msg) {
			String strTitle = (String) msg.obj;
			switch (msg.what) {
			case 1:
				AlertUtil.showAlert(MainActivity.this, R.string.dialog_title,
						R.string.version_new, R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								// DownloadUpdateApk();
							}
						}, R.string.cancel, new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								OpenDateOrNextActivity();
							}
						});

				break;
			case 0:
				OpenDateOrNextActivity();
				break;
			}
			super.handleMessage(msg);
		}
	};
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// case 55:
			// mDialog.setProgress(hasRead);
			// break;
			// case 56:
			// mDialog.setProgress(hasRead);
			// mDialog.dismiss();
			// break;

			case 0:
				String currentTime = Utility.getCurDateTime();
				String lastTime = sp.getString("lastTime", "");
				// "HH:mm yyyy/MM/dd "
				if (lastTime.equals("") || lastTime == null) {
					UpdateStaff();
				} else {
					String year1 = currentTime.substring(0, 4);
					String year2 = lastTime.substring(0, 4);
					String month1 = currentTime.substring(5, 7);
					String month2 = lastTime.substring(5, 7);
					String day1 = currentTime.substring(8, 10);
					String day2 = lastTime.substring(8, 10);

					if (year1.equals(year2) && month1.equals(month2)
							&& day1.equals(day2)) {
						AlertUtil.showAlert(MainActivity.this,
								R.string.dialog_title, R.string.version_user_has_downloading,
								R.string.ok, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
										UpdateStaff();

									}
								}, R.string.cancel, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
										mHandler.sendEmptyMessage(1);

									}
								});

					} else {
						UpdateStaff();
					}

				}

				// UpdateStaff();
				break;
			case 1:
				String currentTime2 = Utility.getCurDateTime();
				String lastTime2 = sp.getString("lastTime", "");
				// "HH:mm yyyy/MM/dd "
				if (lastTime2.equals("") || lastTime2 == null) {
					UpdateStoreDatabase();
				} else {

					String year1 = currentTime2.substring(0, 4);
					String year2 = lastTime2.substring(0, 4);
					String month1 = currentTime2.substring(5, 7);
					String month2 = lastTime2.substring(5, 7);
					String day1 = currentTime2.substring(8, 10);
					String day2 = lastTime2.substring(8, 10);

					if (year1.equals(year2) && month1.equals(month2)
							&& day1.equals(day2)) {
						AlertUtil.showAlert(MainActivity.this,
								R.string.dialog_title, R.string.version_warehouse_has_downloading,
								R.string.ok, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
										UpdateStoreDatabase();

									}
								}, R.string.cancel, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
										// mHandler.sendEmptyMessage(3);

										// 不覆盖就不用再吐司和更新时间了
										mHandler.sendEmptyMessage(6);

									}
								});

					} else {
						UpdateStoreDatabase();
					}

				}

				// UpdateStoreDatabase();
				break;
			case 2:
				UpdateGoodsDatabase();
				break;
			case 3:
				AlertUtil.showToast(AlertUtil.getString(R.string.last_data) + Utility.getCurDateTime(),
						MainActivity.this);
				sp.edit().putString("lastTime", Utility.getCurDateTime())
						.commit();
				OpenDateOrNextActivity();
				break;
			case 4:
				startUpdateVersion();
				break;
			case 5:
				check_update();
				break;
			case 6:
				OpenDateOrNextActivity();
				break;
			case 7:
				AlertUtil.showAlert(MainActivity.this, R.string.warning, R.string.not_registered,
						R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								finish();
							}
						});
				break;
			}
			super.handleMessage(msg);
		}
	};

	private Boolean isRegisterOfDevice() {

		return true;
	}

	private void UpdateGoodsDatabase() {

	}

	private void startDownGoodsData(final String urlStr) {

	}

	private void UpdateStaff() {

	}

	private void UpdateStoreDatabase() {

	}

	public void check_update() {
		try {
			final String sdPath = SystemConst.updateFile;
			// 下载更新文件
			// Boolean isDown = DownFile(sdPath, "Update.xml");
			String pathName = sdPath + "update.xml";
			// 下载文件前先删除
			// FileIO.Delete(pathName);
			final File file = new File(pathName);
			new AsyncTask<Void, Void, Object>() {
				ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
						MainActivity.this, AlertUtil.getString(R.string.version_Checking));

				@Override
				protected void onPreExecute() {
				}

				@Override
				protected Object doInBackground(Void... params) {
					if (isThreadStop) {
						isThreadStop = false;
						return null;
					} else {
						try {
							// String retStr=NetworkUtilities.getSomeInfo();
							String result = NetworkUtilities.JsonVersionGetInfoOfLater(ConfigurationKeys.ProductNum,Utility.GetAppVersion(MainActivity.this));
							if (TextUtils.isEmpty(result)) {
								throw new IOException(AlertUtil.getString(R.string.network_timeout));
							}
							return result;
						} catch (Exception e) {
							e.printStackTrace();
							Utility.deBug(TAG, "check_update" + e.getMessage());
							return e;
						}
					}
				}

				@Override
				protected void onPostExecute(Object result) {
					dialog.dismiss();
					if (result instanceof IOException) {
						AlertUtil.showToast(AlertUtil.getString(R.string.network_failed),
								MainActivity.this);
						mHandler.sendEmptyMessage(6);
					} else if (result instanceof Exception) {
						AlertUtil.showToast(
								AlertUtil.getString(R.string.version_error) + ((Exception) result).getMessage(),
								MainActivity.this);
						Log.e(TAG, "", (Exception) result);
						mHandler.sendEmptyMessage(6);
					} else {
						//如果是最新版本的话
						if (result.toString().equals("latest")) {
							// AlertUtil.showToast("没有可更新的版本!",MainActivity.this);
							String sdpath = Environment
									.getExternalStorageDirectory() + "/";
							mSavePath = SystemConst.updateFile;
							// 提示安装
							File apkfile = new File(mSavePath,
									DrugSystemConst.appApkName);
							if (apkfile.exists()) {
								FileIO.Delete(mSavePath + DrugSystemConst.appApkName);
							}
							mHandler.sendEmptyMessage(6);
							return;
						}
						JSONObject jsonstr;
						try {
							jsonstr = new JSONObject(result.toString());
							String versionInfoStr = jsonstr.getString("T_VersionInfo");
							if (!TextUtils.isEmpty(versionInfoStr)) {
								JSONArray data = new JSONArray(versionInfoStr);
								for (int i = 0; i < data.length(); i++) {
									JSONObject jobj = data.getJSONObject(i);
									VersionInfo versionInfo = JSON.parseObject(
											jobj.toString(), VersionInfo.class);
									if (versionInfo != null)
										versionInfoList.add(versionInfo);
								}
								if (versionInfoList.size() <= 0) {
									// AlertUtil.showToast("没有可更新的版本!",MainActivity.this);
									// 获得存储卡的路径
									String sdpath = Environment
											.getExternalStorageDirectory()
											+ "/";
									mSavePath = SystemConst.updateFile;
									// 提示安装
									File apkfile = new File(mSavePath,
											DrugSystemConst.appApkName);
									if (apkfile.exists()) {
										FileIO.Delete(mSavePath
												+ DrugSystemConst.appApkName);
										mHandler.sendEmptyMessage(6);
									} else {
										mHandler.sendEmptyMessage(6);
									}
									return;
								} else {
									// update.xml存在
									if (file.exists()) {
										try {
											mHashMap = Utility
													.parseXml(updateFile
															+ "update.xml");
											String localVer = Utility
													.GetAppVersion(MainActivity.this);
											String newVersion = mHashMap
													.get("version");
											String fileName = mHashMap
													.get("fileName");
											if (!fileName
													.equals(DrugSystemConst.appApkName)) {
												AlertUtil
														.showAlert(
																MainActivity.this,
																R.string.dialog_title,
																R.string.version_package_error,
																R.string.ok,
																new View.OnClickListener() {
																	@Override
																	public void onClick(
																			View v) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																		AlertUtil
																				.dismissDialog();
																		mHandler.sendEmptyMessage(6);
																	}
																});
												return;
											}

											// 先比较本地的update.xml和网上的最大版本号比较，如果是最新的就不需要再下载，如果不是删除
											VersionInfo versionInfo = versionInfoList
													.get(versionInfoList.size() - 1);
											if (Utility.CompareVersion(
													versionInfo.CName,
													newVersion) > 0) {
												AlertUtil
														.showAlert(
																MainActivity.this,
																R.string.dialog_title,
																R.string.version_needs_updated,
																R.string.ok,
																new View.OnClickListener() {

																	@Override
																	public void onClick(
																			View v) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																		AlertUtil
																				.dismissDialog();
																		file.delete();
																		mHandler.sendEmptyMessage(4);
																	}
																},
																R.string.cancel,
																new View.OnClickListener() {

																	@Override
																	public void onClick(
																			View v) {
																		// TODO
																		// Auto-generatedmethodstub
																		AlertUtil
																				.dismissDialog();
																		mHandler.sendEmptyMessage(6);
																	}
																});
											} else {
												if (Utility.CompareVersion(
														newVersion,localVer) > 0) {
													AlertUtil
															.showAlert(
																	MainActivity.this,
																	R.string.dialog_title,
																	R.string.version_needs_updated,
																	R.string.ok,
																	new View.OnClickListener() {

																		@Override
																		public void onClick(
																				View v) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub
																			AlertUtil
																					.dismissDialog();
																			// 获得存储卡的路径
																			String sdpath = Environment
																					.getExternalStorageDirectory()
																					+ "/";
																			mSavePath = SystemConst.updateFile;
																			// 提示安装
																			File apkfile = new File(
																					mSavePath,
																					DrugSystemConst.appApkName);
																			if (apkfile
																					.exists()) {
																				// 通过Intent安装APK文件
																				Intent i = new Intent(
																						Intent.ACTION_VIEW);
																				i.setDataAndType(
																						Uri.parse("file://"
																								+ apkfile
																										.toString()),
																						"application/vnd.android.package-archive");
																				MainActivity.this
																						.startActivity(i);
																				finish();
																			} else
																				mHandler.sendEmptyMessage(4);
																		}
																	},
																	R.string.cancel,
																	new View.OnClickListener() {

																		@Override
																		public void onClick(
																				View v) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub
																			AlertUtil
																					.dismissDialog();
																			mHandler.sendEmptyMessage(6);
																		}
																	});
												} else {
													// AlertUtil.showToast("当前已经是最新版本",
													// MainActivity.this);
													// 获得存储卡的路径
													String sdpath = Environment
															.getExternalStorageDirectory()
															+ "/";
													mSavePath = SystemConst.updateFile;
													// 提示安装
													File apkfile = new File(
															mSavePath,
															DrugSystemConst.appApkName);
													if (apkfile.exists()) {
														FileIO.Delete(mSavePath
																+ DrugSystemConst.appApkName);
														mHandler.sendEmptyMessage(6);
													} else {
														mHandler.sendEmptyMessage(6);
													}
													// mHandler.sendEmptyMessage(0);
													return;
												}
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											Utility.deBug(TAG, "check_update"
													+ e.getMessage());
											AlertUtil.showToast(AlertUtil.getString(R.string.version_parsing_failed),
													MainActivity.this);
											mHandler.sendEmptyMessage(6);
											return;
										}
									} else {
										mHandler.sendEmptyMessage(4);
									}
								}
							} else {
								String errorStr = jsonstr.getString("error");
								AlertUtil
										.showToast(errorStr, MainActivity.this);
								mHandler.sendEmptyMessage(6);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Utility.deBug(TAG, "check_update" + e.getMessage());
							AlertUtil.showToast(AlertUtil.getString(R.string.version_server_failed),
									MainActivity.this);
							mHandler.sendEmptyMessage(6);
						}
					}
				}

				@Override
				protected void onCancelled() {
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug(TAG, "check_update" + e.getMessage());
		}
	}

	private void startUpdateVersion() {
		try {
			String sdPath = SystemConst.updateFile;

			final String serverFullPath = versionInfoList.get(versionInfoList
					.size() - 1).CPath;
			String FileName = serverFullPath.substring(serverFullPath
					.lastIndexOf("\\") + 1);
			String serverPDAPath = serverFullPath.substring(0,
					serverFullPath.lastIndexOf("\\"));
			final String downFileFullName = sdPath + FileName;
			FileIO.Delete(downFileFullName);
			// 下载更新文件
			// Boolean isDown = DownFile(sdPath, "Update.xml");
			String pathName = sdPath + DrugSystemConst.appApkName;
			// 下载文件前先删除
			FileIO.Delete(pathName);
			final File file = new File(downFileFullName);
			new AsyncTask<Void, Void, Object>() {
				ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
						MainActivity.this, AlertUtil.getString(R.string.version_downloading));

				@Override
				protected void onPreExecute() {
					// dialog = AlertUtil.showProgressDialog(
					// MainActivity.this,
					// "正在检查版本，请稍候");
				}

				@Override
				protected Object doInBackground(Void... params) {
					if (isThreadStop) {
						isThreadStop = false;
						return null;
					} else {
						try {
							if (isCancelled())
								return null;
							int BlockSize = 0;
							byte[] FileByte = null;
							long IstartPost = 0;
							long fileLenth = 0;
							String result = "";
							BlockSize = Integer.parseInt(NetworkUtilities
									.GetBlockSize());
							fileLenth = Long.parseLong(NetworkUtilities
									.GetUpdateFileLenth(serverFullPath));
							long total = fileLenth;
							while (total > 0) {
								byte[] buffer = NetworkUtilities
										.LoadFileByBlock(IstartPost,
												serverFullPath);
								if (buffer == null) {
									throw new IOException(AlertUtil.getString(R.string.network_timeout));
								}
								FileIO.writeFile(file, buffer, 0, buffer.length);
								IstartPost += buffer.length;
								total -= buffer.length;
							}
							if (total == 0) {
								FileIO.Delete(updateFile
										+ DrugSystemConst.appApkName);
								FileIO.Delete(updateFile + "update.xml");
								ZipCompress.UnZipFile(
										downFileFullName,
										updateFile.substring(0,
												updateFile.length() - 1));
								result = "true";
							} else {
								result = "false";
							}
							return result;
						} catch (Exception e) {
							e.printStackTrace();
							Utility.deBug(TAG,
									"startUpdateVersion" + e.getMessage());
							return e;
						}
					}
				}

				@Override
				protected void onPostExecute(Object result) {
					dialog.dismiss();
					if (result instanceof IOException) {
						AlertUtil.showToast(AlertUtil.getString(R.string.network_failed),
								MainActivity.this);
						mHandler.sendEmptyMessage(0);
					} else if (result instanceof Exception) {
						AlertUtil.showToast(
								AlertUtil.getString(R.string.version_error) + ((Exception) result).getMessage(),
								MainActivity.this);
						// Log.e(TAG, "", (Exception) result);
						Utility.deBug(TAG, "startUpdateVersion"
								+ (Exception) result);
						mHandler.sendEmptyMessage(0);
					} else {
						if (result.toString().equals("true")) {
							// 获得存储卡的路径
							String sdpath = Environment
									.getExternalStorageDirectory() + "/";
							mSavePath = SystemConst.updateFile;
							// 提示安装
							File apkfile = new File(mSavePath,
									DrugSystemConst.appApkName);
							if (!apkfile.exists()) {
								return;
							}
							// 通过Intent安装APK文件
							Intent i = new Intent(Intent.ACTION_VIEW);

							i.setDataAndType(
									Uri.parse("file://" + apkfile.toString()),
									"application/vnd.android.package-archive");
							MainActivity.this.startActivity(i);
							finish();
						} else if (result.toString().equals("false")) {
							AlertUtil.showToast(AlertUtil.getString(R.string.version_downloading_failed), MainActivity.this);
							mHandler.sendEmptyMessage(0);
						} else {
							AlertUtil.showToast(AlertUtil.getString(R.string.version_server_failed),
									MainActivity.this);
							mHandler.sendEmptyMessage(0);
						}
					}
				}

				@Override
				protected void onCancelled() {
				}
			}.execute();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug(TAG, "startUpdateVersion" + e.getMessage());
		}

	}
}