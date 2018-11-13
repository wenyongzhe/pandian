package com.supoin.commoninventory.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.supoin.commoninventory.R;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.constvalue.DrugSystemConst;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.entity.VersionInfo;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Constants;
import com.supoin.commoninventory.util.FileIO;
import com.supoin.commoninventory.util.NetworkUtilities;
import com.supoin.commoninventory.util.Utility;
import com.supoin.commoninventory.util.ZipCompress;

public class UpdateActivity extends Activity implements OnClickListener {
	private LinearLayout ll_setting,ll_updating;
	public static final int REQUEST_CODE = 1;
	private String TAG="UpdateActivity";
	public String mSavePath = null;
	private List<VersionInfo> versionInfoList = new ArrayList<VersionInfo>();
	private String updateFile = SystemConst.updateFile;
	HashMap<String, String> mHashMap;
	private static Boolean isThreadStop = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(this,
				getResources().getString(R.string.system_update), false);
		setContentView(R.layout.activity_update);

		init();
	}

	private void init() {
		ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
		ll_updating = (LinearLayout) findViewById(R.id.ll_updating);
		ll_setting.setOnClickListener(this);
		ll_updating.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_setting:
			// 进入更新设置界面需要管理员密码
			Intent intent = new Intent(UpdateActivity.this,
					PasswordInputActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("title", AlertUtil.getString(R.string.enter_password));
			bundle.putString(Constants.KEY_PASSWORD_TYPE,
					Constants.VALUE_PASSWORD_SYSTEM_UPDATE);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
			break;
		case R.id.ll_updating:
			check_update();
			break;
		}
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
						UpdateActivity.this, AlertUtil.getString(R.string.version_Checking));

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
							// {"T_VersionInfo":[{"CName":"1.0.0.5","CRemark":"20160516","CProductID":25,"CCreateDate":"2016-5-16 9:38:11","CPath":"updatefiles\\MI001\\SUPOIN\\SP001\\1.0.0.5.zip","CIsPublish":true,"CFullISN":"MI001\\SUPOIN\\SP001","CParentID":24,"CUID":72}]}
							String result = NetworkUtilities
									.JsonVersionGetInfoOfLater(
											ConfigurationKeys.ProductNum,
											Utility.GetAppVersion(UpdateActivity.this));
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
								UpdateActivity.this);
						mHandler.sendEmptyMessage(6);
					} else if (result instanceof Exception) {
						AlertUtil.showToast(
								AlertUtil.getString(R.string.version_error) + ((Exception) result).getMessage(),
								UpdateActivity.this);
						Log.e(TAG, "", (Exception) result);
						mHandler.sendEmptyMessage(6);
					} else {
						if (result.toString().equals("latest")) {
							// AlertUtil.showToast("没有可更新的版本!",UpdateActivity.this);
							String sdpath = Environment
									.getExternalStorageDirectory() + "/";
							mSavePath = SystemConst.updateFile;
							// 提示安装
							File apkfile = new File(mSavePath,
									DrugSystemConst.appApkName);
							if (apkfile.exists()) {
								FileIO.Delete(mSavePath
										+ DrugSystemConst.appApkName);
							}
							mHandler.sendEmptyMessage(6);
							return;
						}
						JSONObject jsonstr;
						try {
							// {"T_VersionInfo":[{"CName":"1.0.0.5","CRemark":"20160516","CProductID":25,"CCreateDate":"2016-5-16 9:38:11","CPath":"updatefiles\\MI001\\SUPOIN\\SP001\\1.0.0.5.zip","CIsPublish":true,"CFullISN":"MI001\\SUPOIN\\SP001","CParentID":24,"CUID":72}]}

							jsonstr = new JSONObject(result.toString());
							String versionInfoStr = jsonstr
									.getString("T_VersionInfo");
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
									// AlertUtil.showToast("没有可更新的版本!",UpdateActivity.this);
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
													.GetAppVersion(UpdateActivity.this);
											String newVersion = mHashMap
													.get("version");
											String fileName = mHashMap
													.get("fileName");
											if (!fileName
													.equals(DrugSystemConst.appApkName)) {
												AlertUtil
														.showAlert(
																UpdateActivity.this,
																R.string.dialog_title,
																R.string.version_package_error,
																R.string.ok,
																new View.OnClickListener() {
																	@Override
																	public void onClick(
																			View v) {
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
																UpdateActivity.this,
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
														newVersion, localVer) > 0) {
													AlertUtil
															.showAlert(
																	UpdateActivity.this,
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
																				UpdateActivity.this
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
													// UpdateActivity.this);
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
													UpdateActivity.this);
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
										.showToast(errorStr, UpdateActivity.this);
								mHandler.sendEmptyMessage(6);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Utility.deBug(TAG, "check_update" + e.getMessage());
							AlertUtil.showToast(AlertUtil.getString(R.string.version_server_failed),
									UpdateActivity.this);
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
						UpdateActivity.this, AlertUtil.getString(R.string.version_downloading));

				@Override
				protected void onPreExecute() {
					// dialog = AlertUtil.showProgressDialog(
					// UpdateActivity.this,
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
									throw new IOException(AlertUtil.getString(R.string.version_downloading));
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
								UpdateActivity.this);
						mHandler.sendEmptyMessage(0);
					} else if (result instanceof Exception) {
						AlertUtil.showToast(
								AlertUtil.getString(R.string.version_error) + ((Exception) result).getMessage(),
								UpdateActivity.this);
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
							UpdateActivity.this.startActivity(i);
							finish();
						} else if (result.toString().equals("false")) {
							AlertUtil.showToast(AlertUtil.getString(R.string.download_failed), UpdateActivity.this);
							mHandler.sendEmptyMessage(0);
						} else {
							AlertUtil.showToast(AlertUtil.getString(R.string.version_server_failed),
									UpdateActivity.this);
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
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 3:
				finish();
				break;
			case 4:
				startUpdateVersion();
				break;
			case 5:
				check_update();
				break;
			case 6:
//				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (RESULT_OK == resultCode && REQUEST_CODE == requestCode) {
			startActivity(new Intent(UpdateActivity.this,
					UpdateSettingActivity.class));
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
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		super.onResume();
	}
}