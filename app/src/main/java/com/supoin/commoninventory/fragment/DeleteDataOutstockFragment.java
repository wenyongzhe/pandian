package com.supoin.commoninventory.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.activity.PasswordInputActivity;
import com.supoin.commoninventory.adapter.DeleteDataAdapter;
import com.supoin.commoninventory.adapter.DeleteDataAdapter.DataDetailInfo;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLOutStockDataSqlite;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.CheckMain;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.CreateAndDelLog;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.Constants;
import com.supoin.commoninventory.util.Utility;

public class DeleteDataOutstockFragment extends Fragment implements OnClickListener {
	private Button btn_del, btn_delall;
	private ListView listView;
	private SharedPreferences sp;
	private SQLOutStockDataSqlite sqlStockDataSqlite;
	private String strShopID;
	private DeleteDataAdapter adapter;
	private List<DataDetailInfo> dataList = new ArrayList<DataDetailInfo>();
	private HashMap<Integer, Boolean> isSelected;
	private String strDeleteType;
	private String isPasswordControl;
	public CheckMain checkMain = new CheckMain();
	public CheckDetail checkDetail = new CheckDetail();
	public CreateAndDelLog CAndDLog = new CreateAndDelLog();
	private TextView tv_checkID, tv_postionID;
	private List<CheckMain> checkMainList = new ArrayList<CheckMain>();
	public static final int REQUEST_DELETEPSW_CODE = 1;
	public static final int REQUEST_DELETEALLPSW_CODE = 2;
	private int itemSelected = 0;
	protected static final String TAG = "DeleteDataActivity";
	private LinearLayout linearLayout;
	private TextView empty_list_view1;
	private View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
		sqlStockDataSqlite = new SQLOutStockDataSqlite(getActivity(),
				true);
		strDeleteType = "0";
		isPasswordControl = sp.getString(ConfigEntity.DeleteDataPSWKey,
				ConfigEntity.DeleteDataPSW);
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
		isSelected = new HashMap<Integer, Boolean>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_delete_data, null);
		initView();
		setData();
		return view;
	}

	private void initView() {
		listView = (ListView) view.findViewById(R.id.listView0);
		listView.setOnItemClickListener(listener);
		btn_del = (Button) view.findViewById(R.id.btn_delete);
		btn_delall = (Button) view.findViewById(R.id.btn_deleteall);
		tv_checkID = (TextView) view.findViewById(R.id.textView4);
		tv_postionID = (TextView) view.findViewById(R.id.textView6);
		linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
		empty_list_view1 = (TextView) view.findViewById(R.id.empty_list_view1);
		// strDeleteType为0，按编号删除，为1按编号，货位删除
//		if (strDeleteType.equals("0")) {
//			tv_postionID.setVisibility(View.GONE);
//			linearLayout.setVisibility(View.GONE);
//			tv_checkID.setText(sp.getString(ConfigEntity.ExportStrKey,
//					ConfigEntity.ExportStr).split(",")[1]);
//		} else if (strDeleteType.equals("1")) {
//			tv_checkID.setText(sp.getString(ConfigEntity.ExportStrKey,
//					ConfigEntity.ExportStr).split(",")[1]);
//			tv_postionID.setText(sp.getString(ConfigEntity.ExportStrKey,
//					ConfigEntity.ExportStr).split(",")[2]);
//		}
	}

	private void setData() {
		btn_del.setOnClickListener(this);
		btn_delall.setOnClickListener(this);
		checkMainList = sqlStockDataSqlite.GetMainDataReader(strDeleteType,
				strShopID);
		if (checkMainList != null && checkMainList.size() > 0) {
			for (int i = 0; i < checkMainList.size(); i++) {
				DataDetailInfo dataDetailInfo = new DataDetailInfo();
				dataDetailInfo.CheckID = checkMainList.get(i).strCheckID;
				dataDetailInfo.PositionID = checkMainList.get(i).strPositionID;
				isSelected.put(i, false);
				dataList.add(dataDetailInfo);
			}
			adapter = new DeleteDataAdapter(getActivity(), dataList, strDeleteType);
			adapter.setIsSelected(isSelected);
			listView.setAdapter(adapter);
		} else {
			empty_list_view1.setText(AlertUtil.getString(R.string.outstock_no_inventory_data));
			empty_list_view1.setVisibility(View.VISIBLE);
		}
	}

	private OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			adapter.setSelectedPosition(position);
			if (isSelected.get(position)) {
				isSelected.put(position, false);
			} else {
				isSelected.put(position, true);
			}
			// for (Integer key : isSelected.keySet()) {
			// isSelected.put(key, false);
			// }
			// isSelected.put(position, true);
			adapter.setIsSelected(isSelected);
			adapter.notifyDataSetChanged();
			itemSelected = position;

		}
	};

	private List<DataDetailInfo> getSelectedDatas() {
		List<DataDetailInfo> selectList = new ArrayList<DataDetailInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			if (adapter.getIsSelected().get(i)) {
				selectList.add(dataList.get(i));
			}
		}
		return selectList;
	}

	@Override
	public void onClick(View v) {
		if (v == btn_del) {
			DeleteData();
		} else if (v == btn_delall) {
			DeleteAllData();
			
		}
	}

	private void startDelete() {
		final List<DataDetailInfo> list = getSelectedDatas();
		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
					getActivity(), AlertUtil.getString(R.string.deleting_data));

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Object doInBackground(Void... params) {
				try {
					String result = "false";
					int iRet = 0;
					for (int i = 0; i < list.size(); i++) {
						DataDetailInfo detailInfo = list.get(i);
						iRet = sqlStockDataSqlite.DeleteSingleData(
								strDeleteType, detailInfo.CheckID,
								detailInfo.PositionID, strShopID);
						if (iRet == 0) {
							break;
						}
						result = "true";
					}
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
						for (int i = 0; i < list.size(); i++) {
							checkMain.strCheckID = checkDetail.strCheckID = dataList
									.get(i).CheckID;
							CAndDLog.strContent = String.format(
									"删除门店：{0}，编号：{1} 成功", strShopID,
									checkMain.strCheckID);
							if (strDeleteType.equals("1")) {
								checkMain.strPositionID = checkDetail.strPositionID = dataList
										.get(i).PositionID;
								CAndDLog.strContent = String.format(
										"删除门店：{0}，编号：{1}，货位：{2} 成功", strShopID,
										checkMain.strCheckID,
										checkMain.strPositionID);
							}
							sqlStockDataSqlite.InsertLog("",
									CAndDLog.strContent);
						}
						checkMainList = sqlStockDataSqlite.GetMainDataReader(
								strDeleteType, strShopID);

						if (checkMainList != null && checkMainList.size() > 0) {
							dataList.clear();
							for (int i = 0; i < checkMainList.size(); i++) {
								DataDetailInfo dataDetailInfo = new DataDetailInfo();
								dataDetailInfo.CheckID = checkMainList.get(i).strCheckID;
								dataDetailInfo.PositionID = checkMainList
										.get(i).strPositionID;
								isSelected.put(i, false);
								dataList.add(dataDetailInfo);
							}
							adapter.setList(dataList);
							adapter.setIsSelected(isSelected);
							listView.setAdapter(adapter);
						} else {
							dataList.clear();
							adapter.setList(dataList);
							listView.setAdapter(adapter);
						}
						AlertUtil.showAlert(getActivity(), R.string.dialog_title,
								R.string.delete_data_success, R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					} else {
						for (int i = 0; i < list.size(); i++) {
							checkMain.strCheckID = checkDetail.strCheckID = dataList
									.get(i).CheckID;
							CAndDLog.strContent = String.format(
									"删除门店：{0}，编号：{1} 失败", strShopID,
									checkMain.strCheckID);
							if (strDeleteType.equals("1")) {
								checkMain.strPositionID = checkDetail.strPositionID = dataList
										.get(i).PositionID;
								CAndDLog.strContent = String.format(
										"删除门店：{0}，编号：{1}，货位：{2} 失败", strShopID,
										checkMain.strCheckID,
										checkMain.strPositionID);
							}

							sqlStockDataSqlite.InsertLog("",
									CAndDLog.strContent);
						}
						AlertUtil.showAlert(getActivity(), R.string.dialog_title,
								R.string.delete_data_failed, R.string.ok,
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {
					AlertUtil.showAlert(getActivity(), R.string.dialog_title,
							R.string.delete_data_failed, R.string.ok,
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
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

	public void DeleteData() {
		final List<DataDetailInfo> selectList = getSelectedDatas();
		try {
			if (selectList.size() < 1) {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.no_data_delete, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
			if (isPasswordControl.equals("1")) {
				Intent intent = new Intent(getActivity(),
						PasswordInputActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", AlertUtil.getString(R.string.enter_password));
				bundle.putString(Constants.KEY_PASSWORD_TYPE, Constants.PWD_GENERAL);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_DELETEPSW_CODE);

			} else {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title,
						R.string.delete_confirm, R.string.ok,
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								startDelete();
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								return;
							}
						});
			}

		} catch (Exception ex) {
			Utility.deBug(TAG, "FrmDel DeleteData Error:" + ex.toString());
			AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.delete_data_exception,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
		}
	}

	private void startDeleteAll() {
		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showNoButtonProgressDialog(
					getActivity(), AlertUtil.getString(R.string.deleting_data));

			@Override
			protected void onPreExecute() {
			}

			@Override
			protected Object doInBackground(Void... params) {
				try {
					String result = "false";
					int iRet = 0;
					iRet = sqlStockDataSqlite.DeleteAllCheckData(strShopID);
					if (iRet == 1)
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
						dataList.clear();
						adapter.setList(dataList);
						listView.setAdapter(adapter);
						CAndDLog.strContent = String.format(
								"删除门店：{0}下所有编号数据成功", strShopID);
						sqlStockDataSqlite.InsertLog("", CAndDLog.strContent);
					} else {
						CAndDLog.strContent = String.format(
								"删除门店：{0}下所有编号数据时发生错误", strShopID);
						sqlStockDataSqlite.InsertLog("", CAndDLog.strContent);
						AlertUtil.showAlert(getActivity(), R.string.dialog_title,
								R.string.delete_data_failed, R.string.ok,
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {

					AlertUtil.showAlert(getActivity(), R.string.dialog_title,
							R.string.delete_data_failed, R.string.ok,
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
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

	public void DeleteAllData() {
		for (int i = 0; i < dataList.size(); i++) {
			adapter.getIsSelected().put(i, true);
			adapter.notifyDataSetChanged();
		}
		final List<DataDetailInfo> selectList = getSelectedDatas();
		try {
			if (dataList.size() < 1) {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.no_data_delete, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
			if (isPasswordControl.equals("1")) {
				Intent intent = new Intent(getActivity(),
						PasswordInputActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("title", AlertUtil.getString(R.string.enter_password));
				bundle.putString(Constants.KEY_PASSWORD_TYPE, Constants.PWD_GENERAL);
				intent.putExtras(bundle);
				startActivityForResult(intent, REQUEST_DELETEALLPSW_CODE);
			} else {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title,
						R.string.delete_confirm, R.string.ok,
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								startDelete();
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
								return;
							}
						});
			}

		} catch (Exception ex) {
			Utility.deBug(TAG, "FrmDel DeleteAllData Error:" + ex.toString());
			AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.delete_all_data_exception,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
						}
					});
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		// 密码正确返回做处理
		if (requestCode == REQUEST_DELETEPSW_CODE) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title,
					R.string.delete_confirm, R.string.ok,
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							startDelete();
						}
					}, R.string.cancel, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							return;
						}
					});
		} else if (requestCode == REQUEST_DELETEALLPSW_CODE) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title,
					R.string.delete_confirm, R.string.ok,
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							startDelete();
						}
					}, R.string.cancel, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissDialog();
							return;
						}
					});
			// startDeleteAll();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}