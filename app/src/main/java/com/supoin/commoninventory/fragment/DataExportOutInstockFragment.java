package com.supoin.commoninventory.fragment;

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
import com.supoin.commoninventory.activity.DataExportOutSettingActivity;
import com.supoin.commoninventory.activity.SelectExportFileActivity;
import com.supoin.commoninventory.adapter.ExportDataAdapter;
import com.supoin.commoninventory.adapter.ExportDataAdapter.DataDetailInfo;
import com.supoin.commoninventory.constvalue.ConfigurationKeys;
import com.supoin.commoninventory.db.SQLInStockDataSqlite;
import com.supoin.commoninventory.db.entity.ExportCustomiseInfo;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.CheckMain;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.ExportSet;
import com.supoin.commoninventory.entity.STROrder;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.ExcelUtil;
import com.supoin.commoninventory.util.FileIO;
import com.supoin.commoninventory.util.FileUtility;
import com.supoin.commoninventory.util.GUtils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * ��⵼��
 * @date 2017-6-2 ����2:14:27 
 *
 */

public class DataExportOutInstockFragment extends Fragment implements OnClickListener {

	private MainSummaryInfo mSummaryInfo;
	private List<MainSummaryInfo> list = new ArrayList<MainSummaryInfo>();
	private List<ExportCustomiseInfo> customiseInfos = new ArrayList<ExportCustomiseInfo>();
	FileUtility fileUtility;
	String fileUrl;
	private List<String> itemNumList = new ArrayList<String>();
	private Button btn_select_all;
	private Button btn_export;
	private TextView empty_list_view;
	private String strShopID;
	private ExportDataAdapter mExportDataAdapter;
	private SQLInStockDataSqlite sqlStockDataSqlite;
	private SharedPreferences sp;
	private ListView listView;
	private String strExportType;
	private HashMap<Integer, Boolean> isSelected;
	private TextView tv_checkID, tv_postionID;
	private LinearLayout linearLayout;
	private int itemSelected = 0;
	private List<CheckMain> checkMainList = new ArrayList<CheckMain>();
	private List<DataDetailInfo> dataList = new ArrayList<DataDetailInfo>();
	// �Ƿ�ȫѡ��tureȫѡ��falseȡ��ȫѡ
	private Boolean isSelectAll = false;
	// ѡ���б�źͻ�λ
	private String strCheckID, strPositionID;
	// �зָ���
	private String strSeparator;
	// ����������ʽ
	private String strExportNameWay;
	// �ϲ�����0Ϊ�ϲ�������1Ϊȫ������ʱ�ֱ𵼳�
	private String strMergeAllExport;
	// ��ͷ
	private String[] strExportDatasTitle;
	private String IsExportHeader;
	private String DataFilePath;
	private String[] exportDecaIndex;
	private String strExportPrefix;
	private String strIsExportScanOrder;
	private int numOrder;
	private static Boolean isThreadStop = false;
	private int order;
	private int counts;
	public static final int REQUEST_CODE = 1;
	private View view;
	/**�Ƿ��ǵ�һ�У�Ĭ����*/
	private boolean flag = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fileUtility = new FileUtility();
		fileUtility.createDirInSDCardExtra("exportedFile");
		DataFilePath = FileUtility.getSdCardPath();
		fileUrl = FileUtility.getSdCardPath();
		sqlStockDataSqlite = new SQLInStockDataSqlite(getActivity());
		sp = getActivity().getSharedPreferences("InvenConfig",
				getActivity().MODE_PRIVATE);
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
		// ��������λ��0Ϊ����ŵ�����1Ϊ����ţ���λ���� <summary>
		strExportType = "0";
		// ��ȡ��ͷ
		strExportDatasTitle = sp.getString("ExportDatasInTitle", "").split(",");
		// ȫ�������Ƿ�ϲ������0Ϊ�ϲ�������1Ϊȫ������ʱ�ֱ𵼳�
		strMergeAllExport = sp.getString(ConfigEntity.MergeAllExportKey, "");
		// �Ƿ񵼳�ɨ��˳��0Ϊ��������1Ϊ����
		strIsExportScanOrder = sp.getString(ConfigEntity.IsExportSOKey, "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_data_export, null);
		initView();
		setData();
		initText();
		return view;
	}
	private void initView() {
		listView = (ListView) view.findViewById(R.id.listView0);
		empty_list_view = (TextView) view.findViewById(R.id.empty_list_view);
		btn_select_all = (Button) view.findViewById(R.id.btn_select_all);
		btn_export = (Button) view.findViewById(R.id.btn_export);
		// strExportTypeΪ0������ŵ�����Ϊ1����ţ���λ����
//		if (strExportType.equals("0")) {
//			linearLayout.setVisibility(View.INVISIBLE);
//		} else {
//			linearLayout.setVisibility(View.VISIBLE);
//		}
		btn_select_all.setOnClickListener(this);
		btn_export.setOnClickListener(this);
	}

	private void setData() {
		checkMainList = sqlStockDataSqlite.GetMainDataReader(strExportType,
				strShopID);
		if (checkMainList != null && checkMainList.size() > 0) {
			for (int i = 0; i < checkMainList.size(); i++) {
				DataDetailInfo dataDetailInfo = new DataDetailInfo();
				dataDetailInfo.CheckID = checkMainList.get(i).strCheckID;
				dataDetailInfo.PositionID = checkMainList.get(i).strPositionID;
				isSelected.put(i, false);
				dataList.add(dataDetailInfo);
			}
			mExportDataAdapter = new ExportDataAdapter(getActivity(), dataList, strExportType);
			mExportDataAdapter.setIsSelected(isSelected);
			listView.setAdapter(mExportDataAdapter);
		} else {
			empty_list_view.setVisibility(View.VISIBLE);
		}

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				mExportDataAdapter.setSelectedPosition(position);
				if (isSelected.get(position)) {
					isSelected.put(position, false);
					btn_select_all.setText(R.string.select_all);
				} else {
					isSelected.put(position, true);
					ToExportSetting();
				}

				mExportDataAdapter.setIsSelected(isSelected);
				mExportDataAdapter.notifyDataSetChanged();
				// ѡ��������������������
				if (mExportDataAdapter.getCount() == getSelectedDatas().size()) {
					btn_select_all.setText(R.string.deselect_all);
					isSelectAll = true;
				} else {
					isSelectAll = false;
				}
				itemSelected = position;

			}
		});

	}
	String mShopId;
	String mShuliang;
	String mTime;

	/**��̬�޸���ʾ*/
	private void initText() {
		List<String> importStrArrayList = new ArrayList<String>();
		String importStr = sp.getString(ConfigEntity.ExportStrKey, "");
		String[] exportStrArr = importStr.split(",");
		for (int i = 0; i < exportStrArr.length; i++) {
			importStrArrayList.add(exportStrArr[i]);
		}
		mShopId = importStrArrayList.get(0);
		mShuliang = importStrArrayList.get(15);
		mTime = importStrArrayList.get(16);
	}
	protected void ToExportSetting() {
		List<ExportSet> list = new ArrayList<ExportSet>();
		list = sqlStockDataSqlite.getExportSetList();
		if (list.size() < 1) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title,
					R.string.export_not_saved, R.string.ok, new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent1 = new Intent(
									getActivity(),
									DataExportOutSettingActivity.class);
							intent1.putExtra("tag", "1");
							startActivity(intent1);
							AlertUtil.dismissDialog();
						}
					});
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select_all:
			if (dataList.size() < 1) {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.no_data_available, R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
			if (!isSelectAll) {
				ToExportSetting();
				for (int i = 0; i < dataList.size(); i++) {
					mExportDataAdapter.getIsSelected().put(i, true);
					mExportDataAdapter.notifyDataSetChanged();
				}
				btn_select_all.setText(R.string.deselect_all);
				isSelectAll = true;
			} else {
				btn_select_all.setText(R.string.select_all);
				for (int i = 0; i < dataList.size(); i++) {
					mExportDataAdapter.getIsSelected().put(i, false);
					mExportDataAdapter.notifyDataSetChanged();
				}
				isSelectAll = false;
			}
			break;
		case R.id.btn_export:

			List<ExportSet> list = new ArrayList<ExportSet>();
			list = sqlStockDataSqlite.getExportSetList();
			if (list.size() < 1) {
				AlertUtil.showAlert(getActivity(), R.string.dialog_title,
						R.string.export_not_saved, R.string.ok, new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent1 = new Intent(
										getActivity(),
										DataExportOutSettingActivity.class);
								intent1.putExtra("tag", "1");
								startActivity(intent1);
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
			ExportProduct();

			break;
		}

	}

	private void ExportProduct() {

		if (dataList.size() < 1) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.no_data_available, R.string.ok,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		final List<DataDetailInfo> selectList = getSelectedDatas();
		if (selectList.size() == 0 || selectList == null
				&& dataList.size() != 0) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title,  R.string.select_one_record, R.string.ok,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		}
		if (dataList.size() < 1) {
			AlertUtil.showAlert(getActivity(), R.string.dialog_title, R.string.no_exportable_data, R.string.ok,
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							AlertUtil.dismissDialog();
						}
					});
			return;
		} else {
			if (mExportDataAdapter.getCount() != getSelectedDatas().size()
					&& mExportDataAdapter.getCount() > 1
					&& getSelectedDatas().size() != 1
					&& strMergeAllExport.equals("0")
					&& strIsExportScanOrder.equals("1")) {// bug1366 ����������ɨ��˳�򵼳�����ѡ(��ȫѡ��)���ܺϲ�������
				AlertUtil.showAlert(getActivity(), R.string.dialog_title,
						R.string.multiselect_no_merge, R.string.ok,
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
								return;
							}
						});

			} else {
				AlertUtil.showAlert(getActivity(),
						R.string.dialog_title, R.string.export_data, R.string.ok,
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										getActivity(),
										SelectExportFileActivity.class);
								startActivityForResult(intent, REQUEST_CODE);
								AlertUtil.dismissDialog();
							}
						}, R.string.cancel, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								AlertUtil.dismissDialog();
							}
						});
			}
		}

	}

	protected void ExportDatas() {
		// //ID ShopID CheckID PositionID GdBar CheckNum GdArtNO GdStyle
		// GdColorID GdColorName
		// GdSizeID CheckTime GdName GdSizeName Property1 Property2 GdStock
		// GdPrice
		// �Ƿ񵼳�ɨ��˳��0Ϊ������1Ϊ������
		strIsExportScanOrder = sp.getString(ConfigEntity.IsExportSOKey, "");
		List<String> listColumnName = new ArrayList<String>();
		if (strIsExportScanOrder.equals("0")) {
			listColumnName.add("ShopID");
		}
		listColumnName.add("CheckID");
		listColumnName.add("PositionID");
		listColumnName.add("GdBar");
		listColumnName.add("GdArtNO");
		listColumnName.add("GdStyle");
		listColumnName.add("GdName");
		listColumnName.add("GdColorID");
		listColumnName.add("GdColorName");
		listColumnName.add("GdSizeID");
		listColumnName.add("GdSizeName");
		listColumnName.add("Property1");
		listColumnName.add("Property2");
		listColumnName.add("GdStock");
		listColumnName.add("GdPrice");
		// �������ɨ��˳��ʱ�����������ʱ��
		if (strIsExportScanOrder.equals("0")) {
			listColumnName.add("CheckNum");
			listColumnName.add("CheckTime");
		}

		List<ExportSet> exportSetlist = new ArrayList<ExportSet>();
		exportSetlist = sqlStockDataSqlite.getExportSetList();
		final List<STROrder> listColumn = new ArrayList<STROrder>();
		STROrder strOrder;
		if (strIsExportScanOrder.equals("1")) {
			if (exportSetlist.get(0).iCheckID > 0) {
				strOrder = new STROrder(0, listColumnName.get(0),
						exportSetlist.get(0).iCheckID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iPositionID > 0) {
				strOrder = new STROrder(1, listColumnName.get(1),
						exportSetlist.get(0).iPositionID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iBar > 0) {
				strOrder = new STROrder(2, listColumnName.get(2),
						exportSetlist.get(0).iBar);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iArtNO > 0) {
				strOrder = new STROrder(3, listColumnName.get(3),
						exportSetlist.get(0).iArtNO);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iStyle > 0) {
				strOrder = new STROrder(4, listColumnName.get(4),
						exportSetlist.get(0).iStyle);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iName > 0) {
				strOrder = new STROrder(5, listColumnName.get(5),
						exportSetlist.get(0).iName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iColorID > 0) {
				strOrder = new STROrder(6, listColumnName.get(6),
						exportSetlist.get(0).iColorID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iColorName > 0) {
				strOrder = new STROrder(7, listColumnName.get(7),
						exportSetlist.get(0).iColorName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSizeID > 0) {
				strOrder = new STROrder(8, listColumnName.get(8),
						exportSetlist.get(0).iSizeID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSizeName > 0) {
				strOrder = new STROrder(9, listColumnName.get(9),
						exportSetlist.get(0).iSizeName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iBig > 0) {
				strOrder = new STROrder(10, listColumnName.get(10),
						exportSetlist.get(0).iBig);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSmall > 0) {
				strOrder = new STROrder(11, listColumnName.get(11),
						exportSetlist.get(0).iSmall);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iStock > 0) {
				strOrder = new STROrder(12, listColumnName.get(12),
						exportSetlist.get(0).iStock);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iPrice > 0) {
				strOrder = new STROrder(13, listColumnName.get(13),
						exportSetlist.get(0).iPrice);
				listColumn.add(strOrder);
			}

		} else {
			if (exportSetlist.get(0).iShopID > 0) {
				strOrder = new STROrder(0, listColumnName.get(0),
						exportSetlist.get(0).iShopID);
				listColumn.add(strOrder);
			}

			if (exportSetlist.get(0).iCheckID > 0) {
				strOrder = new STROrder(1, listColumnName.get(1),
						exportSetlist.get(0).iCheckID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iPositionID > 0) {
				strOrder = new STROrder(2, listColumnName.get(2),
						exportSetlist.get(0).iPositionID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iBar > 0) {
				strOrder = new STROrder(3, listColumnName.get(3),
						exportSetlist.get(0).iBar);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iArtNO > 0) {
				strOrder = new STROrder(4, listColumnName.get(4),
						exportSetlist.get(0).iArtNO);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iStyle > 0) {
				strOrder = new STROrder(5, listColumnName.get(5),
						exportSetlist.get(0).iStyle);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iName > 0) {
				strOrder = new STROrder(6, listColumnName.get(6),
						exportSetlist.get(0).iName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iColorID > 0) {
				strOrder = new STROrder(7, listColumnName.get(7),
						exportSetlist.get(0).iColorID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iColorName > 0) {
				strOrder = new STROrder(8, listColumnName.get(8),
						exportSetlist.get(0).iColorName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSizeID > 0) {
				strOrder = new STROrder(9, listColumnName.get(9),
						exportSetlist.get(0).iSizeID);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSizeName > 0) {
				strOrder = new STROrder(10, listColumnName.get(10),
						exportSetlist.get(0).iSizeName);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iBig > 0) {
				strOrder = new STROrder(11, listColumnName.get(11),
						exportSetlist.get(0).iBig);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iSmall > 0) {
				strOrder = new STROrder(12, listColumnName.get(12),
						exportSetlist.get(0).iSmall);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iStock > 0) {
				strOrder = new STROrder(13, listColumnName.get(13),
						exportSetlist.get(0).iStock);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iPrice > 0) {
				strOrder = new STROrder(14, listColumnName.get(14),
						exportSetlist.get(0).iPrice);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iQty > 0) {
				strOrder = new STROrder(15, listColumnName.get(15),
						exportSetlist.get(0).iQty);
				listColumn.add(strOrder);
			}
			if (exportSetlist.get(0).iScanDate > 0) {
				strOrder = new STROrder(16, listColumnName.get(16),
						exportSetlist.get(0).iScanDate);
				listColumn.add(strOrder);
			}
		}

		// ����
		Collections.sort(listColumn, new ComparatorValues());

		// �зָ��� SeparatorStr = "�Ʊ��(�ո�(,(|(#(;($(~"
		if (exportSetlist.get(0).strListSeparator.equals("0")) {
			strSeparator = "\t";
		} else if (exportSetlist.get(0).strListSeparator.equals("1")) {
			strSeparator = " ";
		} else if (exportSetlist.get(0).strListSeparator.equals("2")) {
			strSeparator = ",";
		} else if (exportSetlist.get(0).strListSeparator.equals("3")) {
			strSeparator = "|";
		} else if (exportSetlist.get(0).strListSeparator.equals("4")) {
			strSeparator = "#";
		} else if (exportSetlist.get(0).strListSeparator.equals("5")) {
			strSeparator = ";";
		} else if (exportSetlist.get(0).strListSeparator.equals("6")) {
			strSeparator = "$";
		} else if (exportSetlist.get(0).strListSeparator.equals("7")) {
			strSeparator = "~";
		}

		// ȫ���̵㵥�ļ�����ʽ
		strExportNameWay = sp.getString(ConfigEntity.ExportNameWayKey, "");
		// �Ƿ񵼳� ��ͷ 0������ͷ 1��������ͷ
		IsExportHeader = sp.getString(ConfigEntity.IsExportHeaderKey, "");
		// ����ǰ׺
		strExportPrefix = sp.getString(ConfigEntity.ExportInPrefixKey, "");
		// �Ƿ񵼳�ɨ��˳��0Ϊ������1Ϊ������
		strIsExportScanOrder = sp.getString(ConfigEntity.IsExportSOKey, "");
		// �������η������������,�ָ��������Ƿ�Ҫ���һλΪ1��ʾ���ݰ������η�
		exportDecaIndex = sp.getString(ConfigEntity.ExportDecaInIndexKey, "0,0")
				.split(",");
		// �ж��������� һ��˳�򣬣������Ƿ�Ϊ1��ʾ���ݰ������η���
		for (int s = 0; s < strExportDatasTitle.length; s++) {
			if (strExportDatasTitle[s].equals(mShuliang)) {
				numOrder = s;
			}
			;
		}

		new AsyncTask<Void, Void, Object>() {
			ProgressDialog dialog = AlertUtil.showProgressDialog(
					getActivity(),AlertUtil.getString(R.string.exporting_instock),
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AlertUtil.dismissProgressDialog();
							cancel(true);
							isThreadStop = true;
						}
					});
			private String strDateWay;

			@Override
			protected void onPreExecute() {

			}

			@Override
			protected Object doInBackground(Void... params) {
				if (isThreadStop) {
					return null;
				} else {
					try {
						String result = "false";
						// �����ļ�����׺ �����ļ����ͣ�0txt,1csv����
						String strExportFTKey = sp.getString(
								ConfigEntity.ExportFTKey, "");
						if (strExportFTKey.equals("0")) {
							strExportFTKey = ".txt";
						} else if (strExportFTKey.equals("1")) {
							strExportFTKey = ".csv";
						}else if (strExportFTKey.equals("2")) {
							strExportFTKey = ".xls";
						}
						// �Ƿ񵼳�ɨ��˳��0Ϊ��������1Ϊ����
						if (strIsExportScanOrder.equals("0")) {
							StringBuffer stringBuffer = new StringBuffer();
							// �Ƿ��б�ͷ
							if (IsExportHeader.equals("0")&&strMergeAllExport.equals("0")) {
								for (int i = 0; i < strExportDatasTitle.length; i++) {
									stringBuffer.append(strExportDatasTitle[i]
											+ strSeparator);
								}
								String strTitle = stringBuffer.substring(0,
										stringBuffer.toString().length()
												- strSeparator.toString()
														.length());
								stringBuffer = new StringBuffer(strTitle);
								stringBuffer.append("\r\n");
							}

							List<String[]> exportDataList = new ArrayList<String[]>();
							List<String[]> exportDataListAll = new ArrayList<String[]>();
							for (int i = 0; i < dataList.size(); i++) {
								if (mExportDataAdapter.getIsSelected().get(i)) {
									strCheckID = dataList.get(i).getCheckID();
									strPositionID = dataList.get(i)
											.getPositionID();
									if(sp.getString(ConfigEntity.ExportModeKey, ConfigEntity.ExportMode).equals("0")){//����+����
										exportDataList = sqlStockDataSqlite
											.GetExportDatas(strExportType,
													listColumn, strCheckID,
													strShopID, strPositionID);
									}else{//����+1
										exportDataList = sqlStockDataSqlite
												.GetExportDatasSplitBarcode(strExportType,
														listColumn, strCheckID,
														strShopID, strPositionID);
									}
									exportDataListAll.addAll(exportDataList);	
									// �Ƿ��б�ͷ
									if (IsExportHeader.equals("0")&&strMergeAllExport.equals("1")) {
										for (int k = 0; k < strExportDatasTitle.length; k++) {
											stringBuffer.append(strExportDatasTitle[k]
													+ strSeparator);
										}
										String strTitle = stringBuffer.substring(0,
												stringBuffer.toString().length()
														- strSeparator.toString()
																.length());
										stringBuffer = new StringBuffer(strTitle);
										stringBuffer.append("\r\n");
									}

									
									for (String[] strings : exportDataList) {
										for (int j = 0; j < strings.length; j++) {
											// ���η�
											if (exportDecaIndex[0].equals("1")) {
												if (exportDecaIndex[0]
														.equals("1")) {

													if (exportDecaIndex[1]
															.equals("1")) {
														stringBuffer
																.append("\""
																		+ strings[j]
																		+ "\""
																		+ strSeparator);
													} else {
														if (j == numOrder) {
															stringBuffer
																	.append(strings[j]
																			+ strSeparator);
														} else {
															stringBuffer
																	.append("\""
																			+ strings[j]
																			+ "\""
																			+ strSeparator);
														}
													}

												} else {
													stringBuffer.append("\""
															+ strings[j] + "\""
															+ strSeparator);
												}

											} else {
												stringBuffer.append(strings[j]
														+ strSeparator);
											}
										}
										String strBytes = stringBuffer
												.substring(0, stringBuffer
														.toString().length()
														- strSeparator
																.toString()
																.length());
										stringBuffer = new StringBuffer(
												strBytes);
										stringBuffer.append("\r\n");
									}

									// ��ȡ������
									// counts+=sqlStockDataSqlite.GetSpeTotalQty(strExportType,strCheckID,
									// strShopID, strPositionID);
									// 0Ϊ�ϲ�������1Ϊ������ʱ��ֵ���
									if (strMergeAllExport.equals("1")) {
										// int s=counts;
										StringBuffer strFileName = new StringBuffer();
										strDateWay = getDate();
										strFileName.append(strExportPrefix
												+ "_" + strCheckID + "_"
												+ strPositionID + "��"
												+ strDateWay);
										String fileName = strFileName
												.toString() + strExportFTKey;
										String exportPath = DataFilePath;
										final File file = new File(exportPath);
										if (!file.exists())
											file.mkdirs();
										String path = DataFilePath + fileName;
										if(strExportFTKey.equals(".xls")){
											ExcelUtil.writeExcel(getActivity(),strExportDatasTitle,exportDataList,fileName,exportPath);
										}else{
											FileIO.Delete(path);
											FileIO.WriteFile(DataFilePath,
													fileName,
													stringBuffer.toString());
										}
										GUtils.scanFile(getActivity(),path);
										stringBuffer = new StringBuffer();
										// counts=0;
									}
								}
							}

							// ȫ�������Ƿ�ϲ������0Ϊ�ϲ�������1Ϊȫ������ʱ�ֱ𵼳� </summary>
							if (strMergeAllExport.equals("0")) {
								StringBuffer strFileName = new StringBuffer();
								strDateWay = getDate();
								strFileName.append(strExportPrefix + "_�ϲ�����"
										+ "_" + strDateWay);
								String fileName = strFileName.toString()
										+ strExportFTKey;
								String exportPath = DataFilePath;
								final File file = new File(exportPath);
								if (!file.exists())
									file.mkdirs();
								String path = DataFilePath + fileName;
								if(strExportFTKey.equals(".xls")){
									ExcelUtil.writeExcel(getActivity(),strExportDatasTitle,exportDataListAll,fileName,exportPath);
								}else{
									FileIO.Delete(path);
									FileIO.WriteFile(DataFilePath, fileName,
											stringBuffer.toString());
								}
								GUtils.scanFile(getActivity(),path);
								exportDataListAll.clear();
							}
							result = "true";
						} else {
							/*
							 * ��ɨ��˳�򵼳�
							 */
							order = 0;
							String[] strExportDatasTitle0 = null;
							// ����ɨ��˳��
							List<String[]> exportDataList0 = new ArrayList<String[]>();
							List<String[]> exportDataListAll0 = new ArrayList<String[]>();
							if (mExportDataAdapter.getCount() == getSelectedDatas()
									.size() && strMergeAllExport.equals("0")) {
								// ȫ�������Ƿ�ϲ������0Ϊ�ϲ�������1Ϊ��ֵ���
								// �ϲ�ȫ������ʱ���Զ���Ϊ2
								strExportType = "2";
								if(sp.getString(ConfigEntity.ExportModeKey, ConfigEntity.ExportMode).equals("0")){//����+����
									exportDataList0 = sqlStockDataSqlite
											.GetExportDatasByScanOrder(
													strExportType, listColumn,
													strCheckID, strShopID,
													strPositionID);
								}else{//���е���
									exportDataList0 = sqlStockDataSqlite
											.GetExportDatasByScanOrderSplitBarcode(
													strExportType, listColumn,
													strCheckID, strShopID,
													strPositionID);
								}
								exportDataListAll0.addAll(exportDataList0);
								StringBuffer stringBuffer0 = new StringBuffer();
								if (IsExportHeader.equals("0")) {
									// ��ͷ
									if(!strExportFTKey.equals(".xls")){
										stringBuffer0.append("���" + "\t" + mShopId + "\t");
									}else{
										stringBuffer0.append(mShopId + "\t");
									}
									for (int i = 0; i < strExportDatasTitle.length; i++) {
										if (!strExportDatasTitle[i].equals(mShopId)
												&& !strExportDatasTitle[i].equals(mShuliang)
												&& !strExportDatasTitle[i].equals(mTime)) {
											stringBuffer0.append(strExportDatasTitle[i]
													+ "\t");
										}
									}
									stringBuffer0.append(mShuliang + "\t" + mTime + "\r\n");
									if(strExportFTKey.equals(".xls")){
										strExportDatasTitle0=stringBuffer0.toString().split("\t");
									}
								}

								for (String[] strings : exportDataList0) {
									order++;
									stringBuffer0.append(order + "" + "\t");
									for (int j = 0; j < strings.length; j++) {
										// ���η�
										if (exportDecaIndex[0].equals("1")) {
											if (exportDecaIndex[0]
													.equals("1")) {

												if (exportDecaIndex[1]
														.equals("1")) {
													stringBuffer0
															.append("\""
																	+ strings[j]
																	+ "\""
																	+ strSeparator);
												} else {
													if (j == numOrder) {
														stringBuffer0
																.append(strings[j]
																		+ strSeparator);
													} else {
														stringBuffer0
																.append("\""
																		+ strings[j]
																		+ "\""
																		+ strSeparator);
													}
												}

											} else {
												stringBuffer0.append("\""
														+ strings[j] + "\""
														+ strSeparator);
											}

										} else {
											stringBuffer0.append(strings[j]
													+ strSeparator);
										}
									}
									String strBytes = stringBuffer0
											.substring(0, stringBuffer0
													.toString().length()
													- strSeparator
															.toString()
															.length());
									stringBuffer0 = new StringBuffer(
											strBytes);
									stringBuffer0.append("\r\n");
								}
								
								/*for (String[] strArr : exportDataList0) {
									order++;
									stringBuffer0.append(order + "" + "\t");
									for (int j = 0; j < strArr.length; j++) {
										stringBuffer0.append(strArr[j] + "\t");
									}
									stringBuffer0.append("\r\n");
								}*/

								StringBuffer strFileName = new StringBuffer();
								strDateWay = getDate();
								strFileName.append(strExportPrefix
										+ "_�ϲ��������̵�ɨ��˳��" + "_" + strDateWay);
								String fileName = strFileName.toString()
										+ strExportFTKey;
								String exportPath = DataFilePath;
								final File file = new File(exportPath);
								if (!file.exists())
									file.mkdirs();
								String path = DataFilePath + fileName;
								if(strExportFTKey.equals(".xls")){
									ExcelUtil.writeExcel(getActivity(),strExportDatasTitle0,exportDataListAll0,fileName,exportPath);
								}else{
									FileIO.Delete(path);
									FileIO.WriteFile(DataFilePath, fileName,
											stringBuffer0.toString());
								}
								GUtils.scanFile(getActivity(),path);
								exportDataListAll0.clear();
							} else {
								if(strMergeAllExport.equals("0")){
									strExportType = "0";	
								}
								// ����,���ѡ���ֵ���
								String s = strMergeAllExport;

								for (int i = 0; i < dataList.size(); i++) {
									//bug1367
									flag = true;
									order = 0;
									StringBuffer stringBuffer0 = new StringBuffer();
									//bug1370
									if (IsExportHeader.equals("0")) {
										// ��ͷ
										if(!strExportFTKey.equals(".xls")){
											stringBuffer0.append("���" + "\t" + mShopId + "\t");
										}else{
											stringBuffer0.append(mShopId + "\t");
										}
										for (int k = 0; k < strExportDatasTitle.length; k++) {
											if (!strExportDatasTitle[k].equals(mShopId)
													&& !strExportDatasTitle[k].equals(mShuliang)
													&& !strExportDatasTitle[k].equals(mTime)) {
												stringBuffer0.append(strExportDatasTitle[k]
														+ "\t");
											}
										}
										stringBuffer0.append(mShuliang + "\t" + mTime + "\r\n");
										if(strExportFTKey.equals(".xls")){
											strExportDatasTitle0=stringBuffer0.toString().split("\t");
										}
									}
									if (mExportDataAdapter.getIsSelected().get(
											i)) {
										strCheckID = dataList.get(i)
												.getCheckID();
										strPositionID = dataList.get(i)
												.getPositionID();
										if(sp.getString(ConfigEntity.ExportModeKey, ConfigEntity.ExportMode).equals("0")){//����+����
											exportDataList0 = sqlStockDataSqlite
													.GetExportDatasByScanOrder(
															strExportType, listColumn,
															strCheckID, strShopID,
															strPositionID);
										}else{//���е���
											exportDataList0 = sqlStockDataSqlite
													.GetExportDatasByScanOrderSplitBarcode(
															strExportType, listColumn,
															strCheckID, strShopID,
															strPositionID);
										}
										//bug1367 ��ֵ�������û�б�ͷ��ʱ�򣬰�stringBuffer���
										if (IsExportHeader.equals("0")){//0Ϊ������ͷ
											if(!flag){
												stringBuffer0.delete(0,stringBuffer0.length());
											}
										}else {//��������ͷ
											stringBuffer0.delete(0,stringBuffer0.length());
										}
										for (String[] strings : exportDataList0) {
											flag = false;
											order++;
											stringBuffer0.append(order + "" + "\t");
											for (int j = 0; j < strings.length; j++) {
												// ���η�
												if (exportDecaIndex[0].equals("1")) {
													if (exportDecaIndex[0]
															.equals("1")) {

														if (exportDecaIndex[1]
																.equals("1")) {
															stringBuffer0
																	.append("\""
																			+ strings[j]
																			+ "\""
																			+ strSeparator);
														} else {
															if (j == numOrder) {
																stringBuffer0
																		.append(strings[j]
																				+ strSeparator);
															} else {
																stringBuffer0
																		.append("\""
																				+ strings[j]
																				+ "\""
																				+ strSeparator);
															}
														}

													} else {
														stringBuffer0.append("\""
																+ strings[j] + "\""
																+ strSeparator);
													}

												} else {
													stringBuffer0.append(strings[j]
															+ strSeparator);
												}
											}
											String strBytes = stringBuffer0
													.substring(0, stringBuffer0
															.toString().length()
															- strSeparator
																	.toString()
																	.length());
											stringBuffer0 = new StringBuffer(
													strBytes);
											stringBuffer0.append("\r\n");
										}
										/*for (String[] strArr : exportDataList0) {
											order++;
											stringBuffer0.append(order + ""
													+ "\t");
											for (int j = 0; j < strArr.length; j++) {
												stringBuffer0.append(strArr[j]
														+ "\t");
											}
											stringBuffer0.append("\r\n");
										}*/
										StringBuffer strFileName = new StringBuffer();
										strDateWay = getDate();
										strFileName.append(strExportPrefix
												+ "_" + strCheckID + "_"
												+ strPositionID + "�̵�ɨ��˳��"
												+ strDateWay);
										String fileName = strFileName
												.toString() + strExportFTKey;
										String exportPath = DataFilePath;
										final File file = new File(exportPath);
										if (!file.exists())
											file.mkdirs();
										String path = DataFilePath + fileName;
										if(strExportFTKey.equals(".xls")){
											ExcelUtil.writeExcel(getActivity(),strExportDatasTitle0,exportDataList0,fileName,exportPath);
										}else{
											FileIO.Delete(path);
											FileIO.WriteFile(DataFilePath,
													fileName,
													stringBuffer0.toString());
										}
										GUtils.scanFile(getActivity(),path);
									}
								}

							}

							result = "true";
						}

						return result;
					} catch (Exception e) {
						e.printStackTrace();
						return e;
					}
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				dialog.dismiss();
				if (!TextUtils.isEmpty(result.toString())) {
					if (result.toString().equals("true")) {
						AlertUtil.showAlert(getActivity(), R.string.dialog_title,
								R.string.export_data_success, R.string.ok, new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										AlertUtil.dismissDialog();
									}
								});
					} else {
						AlertUtil.showAlert(getActivity(), R.string.dialog_title,
								R.string.export_data_failed + result.toString(), R.string.ok,
								new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					}
				} else {
					// AlertUtil.showToast("���ݵ���ʧ�ܣ�",
					// getActivity());
					AlertUtil.showAlert(getActivity(), R.string.dialog_title,
							R.string.export_data_failed + result.toString(), R.string.ok,
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
				isThreadStop = false;
				super.onCancelled();
			}
		}.execute();

	}

	List<DataDetailInfo> selectList;

	private List<DataDetailInfo> getSelectedDatas() {
		selectList = new ArrayList<DataDetailInfo>();
		for (int i = 0; i < dataList.size(); i++) {
			if (mExportDataAdapter.getIsSelected().get(i)) {
				selectList.add(dataList.get(i));
			}
		}
		return selectList;
	}

	public final class ComparatorValues implements Comparator<STROrder> {

		@Override
		public int compare(STROrder object1, STROrder object2) {
			int m1 = object1.iDestIndex;
			int m2 = object2.iDestIndex;
			int result = 0;
			if (m1 > m2) {
				result = 1;
			}
			if (m1 < m2) {
				result = -1;
			}
			return result;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		// ��ȷ����������
		if (requestCode == REQUEST_CODE) {
			Bundle bundle = null;
			if (data != null && (bundle = data.getExtras()) != null) {
				String fileString = bundle.getString("file");
				StringBuffer fileBuffer = new StringBuffer(fileString);
				if (!fileBuffer.substring(fileBuffer.length() - 1,
						fileBuffer.length()).equals("/")) {
					fileBuffer.append("/");
				}
				DataFilePath = fileBuffer.toString();
				ExportDatas();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// ����Activity&��ջ���Ƴ���Activity
	}

	@Override
	public void onResume() {
		super.onResume();
		// ��ȡ��ͷ
		strExportDatasTitle = sp.getString("ExportDatasInTitle", "").split(",");
		// ȫ�������Ƿ�ϲ������0Ϊ�ϲ�������1Ϊȫ������ʱ�ֱ𵼳�
		strMergeAllExport = sp.getString(ConfigEntity.MergeAllExportKey, "");
		strIsExportScanOrder = sp.getString(ConfigEntity.IsExportSOKey, "");
	}

	public String getDate() {
		long longtime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH��mm");
		Date d1 = new Date(longtime);
		String time = format.format(d1);
		return time;
	}

}