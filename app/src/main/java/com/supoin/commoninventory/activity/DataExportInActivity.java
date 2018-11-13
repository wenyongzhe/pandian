package com.supoin.commoninventory.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.constvalue.SystemConst;
import com.supoin.commoninventory.db.SQLStockDataSqlite;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.entity.ImportSet;
import com.supoin.commoninventory.entity.STROrder;
import com.supoin.commoninventory.publicontent.CustomTitleBar;
import com.supoin.commoninventory.publicontent.myApplication;
import com.supoin.commoninventory.util.AlertUtil;
import com.supoin.commoninventory.util.CommonProgressDialog;
import com.supoin.commoninventory.util.GlobalRunConfig;
import com.supoin.commoninventory.util.ImportErrorLog;
import com.supoin.commoninventory.util.Utility;

public class DataExportInActivity extends Activity implements OnClickListener {
	private CheckBox checkBox1, checkBox2;
	private Button button;
	private SharedPreferences sp;
	private SQLStockDataSqlite sqlStockDataSqlite;
	protected static final String TAG = "DataExportInActivity";
	private String ImportPath = SystemConst.importPath;
	private String LogPath = SystemConst.logPath;

	// private TextView tv_number, tv_version, tv_city, tv_room;
	private static Boolean isThreadStop = false;
	private HttpURLConnection connection;
	private CommonProgressDialog mDialog;
	private int hasRead;
	private String content;
	List<ImportSet> importSetList = new ArrayList<ImportSet>();
	private String strIndexBarcode = "Index1GdInfo0000111987";
	private String strIndexcode = "Index2GdInfo1111222876";
	List<GdInfo> gdInfoList = new ArrayList<GdInfo>();
	public static final int REQUEST_CODE = 1;
	private String fileName = "";
	private int[] importSetArr;// ԭʼ������ѡ�е�λ��
	private ImportSet importSet;
	private String[] importStrArr;// ԭʼ����
	private Boolean isSetting = false;
	int iPriceTag = -1, iStockTag = -1; // ���ڱ���Ƿ����˼۸�Ϳ��,�����������¼�䵼�������
	int iInnerBarTag = -1, iOutBarTag = -1; // ���ڱ���Ƿ������ڲ�����ⲿ��,
	String encoding = "GBK";
	ImportErrorLog errLog = new ImportErrorLog();
	ProgressDialog dialog ;
	int isNumberImportedFiles=0;//�����ļ��������Ա���ʾ��������tiaoshu
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApplication.getInstance().addActivity(this);
		CustomTitleBar.getTitleBar(DataExportInActivity.this,
				R.string.data_import, false);
		setContentView(R.layout.activity_dataexportin);
		sp = getSharedPreferences("InvenConfig", MODE_PRIVATE);
		sqlStockDataSqlite = new SQLStockDataSqlite(DataExportInActivity.this,
				true);
		initView();
	}

	public void initView() {
		button = (Button) this.findViewById(R.id.btn_export);
		checkBox1 = (CheckBox) this.findViewById(R.id.wifi);
		checkBox2 = (CheckBox) this.findViewById(R.id.Offline);
		button.setOnClickListener(this);
		checkBox1.setOnClickListener(this);
		checkBox2.setOnClickListener(this);
		importSetList = sqlStockDataSqlite.GetImportSetList();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.relativelayout:
			finish();
			break;

		case R.id.wifi:
			if (checkBox1.isChecked() == true) {
				checkBox2.setChecked(false);
			}
			break;
		case R.id.Offline:
			if (checkBox2.isChecked() == true) {
				checkBox1.setChecked(false);
			}
			break;
		case R.id.btn_export:
			if (checkBox1.isChecked() == true) {
				// sqlStockDataSqlite.clearStockData();
			} else if (checkBox2.isChecked() == true) {
				String importStr = sp.getString(ConfigEntity.ImportStrKey,
						ConfigEntity.ImportStr);
				importStrArr = importStr.split(",");
				importSetArr = new int[importStrArr.length];
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

					// ���ݵ������õ�Item��ʾ
					for (int i = 0; i < importSetArr.length; i++) {
						if (0 < importSetArr[i]) {
							// ����������
							isSetting = true;
						}
						// ��ת��������ҳ��
					}
				}
				if (isSetting) {
					Intent intent = new Intent(DataExportInActivity.this,
							SelectImportFileActivity.class);
					startActivityForResult(intent, REQUEST_CODE);
				} else {
					AlertUtil.showAlert(DataExportInActivity.this,
							R.string.dialog_title,
							R.string.setting_before_import, R.string.setting,
							new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
									Intent intent = new Intent();
									intent.setClass(DataExportInActivity.this,
											ImSettingActivity.class);
									intent.putExtra("tag", "1");
									startActivity(intent);
								}
							}, R.string.cancel, new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									AlertUtil.dismissDialog();
								}
							});

				}

			} else if (checkBox1.isChecked() == false
					&& checkBox2.isChecked() == false) {
				AlertUtil.showToast(R.string.select_import_mode,
						DataExportInActivity.this);
			}
			break;
		}

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

	public void ImportProduct() {
		isNumberImportedFiles=0;
		if (importSetList.size() < 1) {
			AlertUtil.showAlert(DataExportInActivity.this,
					R.string.dialog_title, R.string.configure_import_items,
					R.string.ok, new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

						}
					});
		}
		File importFile = new File(ImportPath);
		if (!importFile.exists()) {
			importFile.mkdirs();
		}
		// ��¼�ϴ��û�ѡ��ĵ���������Ϸ�ʽ��0Ϊ���ǵ��룬1Ϊ��������
		if (sp.getString(ConfigEntity.LastImportWayKey, "").equals("0")) {
			GdInfo gd = new GdInfo();
			if (sqlStockDataSqlite.DropAndRecreate(sp.getString(
					ConfigEntity.InOutCodeKey, "")) != 1) {
				AlertUtil.showAlert(DataExportInActivity.this,
						R.string.dialog_title,
						R.string.exception_when_underlying_data, R.string.ok,
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								AlertUtil.dismissDialog();
							}
						});
				return;
			}
		}

		List<String> listColumnName = new ArrayList<String>();
		listColumnName.add("GdBarCode");
		listColumnName.add("GdCode");
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
		
		List<STROrder> listColumn = new ArrayList<STROrder>();
		int i = 0, j = 0;
		String className;
		// for (i = 0; i < 13; i++)
		{
			// if(Integer.parseInt(columnList.get(i))>0){
			// STROrder strOrder = new
			// STROrder(Integer.parseInt(columnList.get(i)),
			// listColumnName.get(i), i);
			// listColumn.add(strOrder);
			// }
			STROrder strOrder;
			if (importSetList.get(0).iBarcode > 0) {
				strOrder = new STROrder(0, listColumnName.get(0),
						importSetList.get(0).iBarcode);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iCode > 0) {
				strOrder = new STROrder(1, listColumnName.get(1),
						importSetList.get(0).iCode);
				listColumn.add(strOrder);

			}
			if (importSetList.get(0).iArtNO > 0) {
				strOrder = new STROrder(2, listColumnName.get(2),
						importSetList.get(0).iArtNO);
				listColumn.add(strOrder);

			}
			if (importSetList.get(0).iStyle > 0) {
				strOrder = new STROrder(3, listColumnName.get(3),
						importSetList.get(0).iStyle);
				listColumn.add(strOrder);

			}
			if (importSetList.get(0).iName > 0) {
				strOrder = new STROrder(4, listColumnName.get(4),
						importSetList.get(0).iName);
				listColumn.add(strOrder);

			}
			if (importSetList.get(0).iColorID > 0) {
				strOrder = new STROrder(5, listColumnName.get(5),
						importSetList.get(0).iColorID);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iColorName > 0) {
				strOrder = new STROrder(6, listColumnName.get(6),
						importSetList.get(0).iColorName);
				listColumn.add(strOrder);
			}

			if (importSetList.get(0).iSizeID > 0) {
				strOrder = new STROrder(7, listColumnName.get(7),
						importSetList.get(0).iSizeID);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iSizeName > 0) {
				strOrder = new STROrder(8, listColumnName.get(8),
						importSetList.get(0).iSizeName);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iBig > 0) {
				strOrder = new STROrder(9, listColumnName.get(9),
						importSetList.get(0).iBig);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iSmall > 0) {
				strOrder = new STROrder(10, listColumnName.get(10),
						importSetList.get(0).iSmall);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iStock > 0) {
				strOrder = new STROrder(11, listColumnName.get(11),
						importSetList.get(0).iStock);
				listColumn.add(strOrder);
			}
			if (importSetList.get(0).iPrice > 0) {
				strOrder = new STROrder(12, listColumnName.get(12),
						importSetList.get(0).iPrice);
				listColumn.add(strOrder);
			}
			/*
			 * if (importSetList.get(0).iColumnNum > 0) { strOrder = new
			 * STROrder(12, listColumnName.get(12),
			 * importSetList.get(0).iColumnNum); listColumn.add(strOrder); }
			 */
		}
		ComparatorValues mComparatorValues = new ComparatorValues();
		Collections.sort(listColumn,mComparatorValues);
	
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ");
		String strDeleteSql = "delete from GdInfo where ";
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode)
				.equals("1")) // �ⲿ��
		{
			strDeleteSql += "GdBarcode=?";
		} else {
			strDeleteSql += "GdCode=?";
		}

		String strInsertSql = "Insert Into GdInfo("; // ���ڱ���Insert
		//������е�����											// In����������ƣ����Զ��ŷָ�
		for (j = 0; j < listColumn.size(); j++) {
			
			sbSql.append(listColumn.get(j).strOriName);
			sbSql.append(",");
			strInsertSql += listColumn.get(j).strOriName + ",";
		}
		// GdCode,GdStyle,GdName,GdColorName,Property1,GdPrice
		sbSql.delete(sbSql.length() - 1, sbSql.length());
		if (strInsertSql.length() > 0)
			strInsertSql = strInsertSql.substring(0, strInsertSql.length() - 1);
		strInsertSql += ") Values(";
		for (j = 0; j < listColumn.size(); j++) {
			if (listColumn.get(j).iDestIndex == 11
					|| listColumn.get(j).iDestIndex == 12) {
				strInsertSql += String.format("?,", j);
				continue;
			}
			strInsertSql += String.format("?,", j);
		}
		if (strInsertSql.length() > 0)
			strInsertSql = strInsertSql.substring(0, strInsertSql.length() - 1);
		strInsertSql += ");";

		sbSql.append(" from GdInfo");
		 dialog = AlertUtil.showNoButtonProgressDialog(
				DataExportInActivity.this,
				AlertUtil.getString(R.string.import_data));
		 
		
		LoadGoodsFromLocal(sbSql, strDeleteSql, strInsertSql, listColumn);
	}

	// ���ߵ������������
	private void LoadGoodsFromLocal(final StringBuilder sbSql,
			final String strDeleteSql, final String strInsertSql,
			final List<STROrder> listColumn) {
		try {
			// final List<String> columnList=sqlStockDataSqlite.();
			new AsyncTask<Void, Void, Object>() {
				
				private int iTotalNum;

				@Override
				protected void onPreExecute() {
				}

				@SuppressLint("DefaultLocale") @SuppressWarnings("unused")
				@Override
				protected Object doInBackground(Void... params) {
					
					if (isThreadStop) {
						isThreadStop = false;
						return null;
					} else {

						int iColumnNum = importSetList.get(0).iColumnNum;
						
//						String strInsertSql = "insert into GdInfo (";
//						for (int i = 0; i < iColumnNum; i++) {
//							strInsertSql += listColumn
//									.get(i).strOriName
//									+ ",";
//						}
//						strInsertSql = strInsertSql
//								.substring(0, strInsertSql
//										.length() - 1)
//								+ ") values(";
//						for (int j = 0; j < iColumnNum; j++) {
//							strInsertSql += "?,";
//						}
//						// insert into GdInfo
//						// (GdCode,GdStyle,GdName,GdColorName,GdSizeName)
//						// values(?,?,?,?,?)
//						strInsertSql = strInsertSql
//								.substring(0, strInsertSql
//										.length() - 1)
//								+ ")";
//						String strFileName = fileName;
						int iCurrent = 0;
						String strTemp = "";
						int iRet = 1;
						String strFilePath = fileName;// ImportPath+strFileName;// "�ⲿ��-�ڲ���-����-��ɫ-����-�۸�.txt";			
						errLog.setErr_file_path(LogPath);
						String cSeparator;
					
						for (STROrder stro : listColumn) {
							if (stro.iDestIndex == 1)
								iInnerBarTag = stro.iOriIndex - 1;
							else if (stro.iDestIndex == 0)
								iOutBarTag = stro.iOriIndex - 1;
							else if (stro.iDestIndex == 11)
								iStockTag = stro.iOriIndex - 1;
							else if (stro.iDestIndex == 12)
								iPriceTag = stro.iOriIndex - 1;
						}
						if (importSetList.get(0).strListSeparator.toString() == "�Ʊ��")
							cSeparator = "\t";
						else if (importSetList.get(0).strListSeparator
								.toString() == "�ո�")
							cSeparator = " ";
						else {
							cSeparator = importSetList.get(0).strListSeparator
									.toString();
						}

						// �зָ��� SeparatorStr = "�Ʊ��(�ո�(,(|(#(;($(~"
						if (importSetList.get(0).strListSeparator.equals("0")) {
							cSeparator = "\t";
						} else if (importSetList.get(0).strListSeparator
								.equals("1")) {
							cSeparator = " ";
						} else if (importSetList.get(0).strListSeparator
								.equals("2")) {
							cSeparator = ",";
						} else if (importSetList.get(0).strListSeparator
								.equals("3")) {
							cSeparator = "|";
						} else if (importSetList.get(0).strListSeparator
								.equals("4")) {
							cSeparator = "#";
						} else if (importSetList.get(0).strListSeparator
								.equals("5")) {
							cSeparator = ";";
						} else if (importSetList.get(0).strListSeparator
								.equals("6")) {
							cSeparator = "$";
						} else if (importSetList.get(0).strListSeparator
								.equals("7")) {
							cSeparator = "~";
						}

						// �����ļ��Ƿ���̧ͷ��1Ϊ�У�0Ϊ��
//						if (sp.getString(ConfigEntity.ImportTopTitleKey,
//								ConfigEntity.ImportTopTitle).equals("1")) {
//							// String headStr
//							// =FileIO.ReadTxtFile(strFilePath,encoding);
//							// if (sp.getString(ConfigEntity.ERPKey,
//							// ConfigEntity.ERP).equals(DrugConstants.GJP))
//							// {
//							// //����ǹܼ��ŵģ�����������ļ�
//							// String[] GraspInfo = headStr.replace(cSeparator,
//							// "\t").split("\t");
//							// sp.edit().putString(ConfigEntity.GraspInfoKey,
//							// String.format("{0},{1}", GraspInfo[0],
//							// GraspInfo[1])).commit();
//							// }
//						}
//						 sqlStockDataSqlite.exeSql("delete from GdInfo");
						sqlStockDataSqlite.clearGdInfoData();
						// 0Ϊ���ǵ��룬1Ϊ��������
						if (sp.getString(ConfigEntity.LastImportWayKey,
								ConfigEntity.LastImportWay).equals("0"))
							strTemp = String.format("�����ǵ����������,��{0}��,�ѵ�0��",
									iTotalNum);
						else if (sp.getString(ConfigEntity.LastImportWayKey,
								ConfigEntity.LastImportWay).equals("1"))
							strTemp = String.format("�����������������,��{0}��,�ѵ�0��",
									iTotalNum);
						else if (sp.getString(ConfigEntity.LastImportWayKey,
								ConfigEntity.LastImportWay).equals("2")) // �÷�ʽ�Ѿ�ȡ��,ֻ����ǰ���ֵĵ��뷽��
							strTemp = String.format("����ȫ����������������ϣ���{0}�У��ѵ���0��",
									iTotalNum);
						try {
							InputStream instream = new FileInputStream(
									strFilePath);
							if (instream == null) {
								return 0;
							}
							try {
								InputStreamReader inputreader = new InputStreamReader(
										instream, encoding);
								BufferedReader buffreader = new BufferedReader(
										inputreader);
								StringBuffer sBuffer = new StringBuffer();
								String strLine = null;
								String[] strColumn = null;
								Object[] objValue = null;
//								Boolean isRightFormat = true;
								String strErrMsg = "";
								// sBuffer.append(strLine + "\n");
								// String strItem=strLine;
								// String[] itemArr=strLine.split("	");
								// GdInfo gdInfo=new GdInfo();
								// gdInfo.strGdBarcode=itemArr[0];
								// gdInfo.strGdCode=itemArr[1];
								// gdInfo.strGdName=itemArr[2];
								// gdInfo.strGdColorName=itemArr[3];
								// gdInfo.strGdSizeName=itemArr[4];
								// sqlStockDataSqlite.saveGdInfo(gdInfo);
								// iTotalNum++;
								// Utility.deBug(TAG,"�����ǵ����������,��"+iTotalNum+"��");
							
								try {
								
									List<ContentValues> list;
									list=new ArrayList<ContentValues>();
									while ((strLine = buffreader.readLine()) != null) {
								
										iCurrent++;
										String strReadLine = strLine;
										strTemp = strReadLine;
										if (!cSeparator.equals("0")) {
											strTemp = strTemp.replace(
													cSeparator, "\t");
											// �������η�ImportDecaration = "��,˫����";
											if (!sp.getString(ConfigEntity.ImportDecaIndexKey,ConfigEntity.ImportDecaIndex).equals("0"))
												strTemp = strTemp.replace(sp.getString(ConfigEntity.ImportDecarationKey,ConfigEntity.ImportDecaration).split(",")[Integer.parseInt(sp.getString(ConfigEntity.ImportDecaIndexKey,ConfigEntity.ImportDecaIndex))],
																"");
											strTemp = strTemp.replace("\"", "��");
											strTemp = strTemp.replace("'", "��");
										}
										if (strTemp.length() < 1){
											dialog.dismiss();
										    AlertUtil.showAlert(DataExportInActivity.this,	R.string.Input_code_bad,"û����Ҫ������",R.string.ok,
												new View.OnClickListener() {public void onClick(View v) {
														AlertUtil.dismissDialog();
													}
												});
											break;
										}
										// String[] strColumn =
										// null;�������Ϊ�ַ������鷵��
										strColumn = strTemp.split("\t");
										// �����б�С�����õ�item
										if (strColumn.length < iColumnNum) {
											// PDMessageBox.ShowError(R.string.warning,"�ļ���ʽ�����ò�����");
//											isRightFormat = false;
											// strErrMsg =
											// String.Format("��������[{0}]��ϵͳ��������[{1}]��һ�£�����ʧ�ܣ�\r\n\r\n����λ�ã�{2}��.",
											// strColumn.Length, iColumnNum,
											// iCurrent);
											strErrMsg = String.format("��������[{0}]��ϵͳ��������[{1}]��һ�£�����ʧ�ܣ�",	strColumn.length,	iColumnNum);
											iRet = -1;
											// iRet = 1;
											errLog.WriteErrorWithoutTime(strReadLine);
											dialog.dismiss();
											AlertUtil.showAlert(DataExportInActivity.this,	R.string.Input_code_bad,"����������������Ҫ�����txt�ļ�����������,����������",R.string.ok,
													new View.OnClickListener() {public void onClick(View v) {
															AlertUtil.dismissDialog();
														}
													});
                                            
											iRet = 2;
											break;
										}
//										// �������۸���Ϊ�յ����
										if (iStockTag != -1
												&& strColumn.length > iStockTag) {
											if (strColumn[iStockTag].trim()
													.length() < 1)
												strColumn[iStockTag] = "0";
										}
										if (iPriceTag != -1
												&& strColumn.length > iPriceTag) {
											if (strColumn[iPriceTag].trim()
													.length() < 1)
												strColumn[iPriceTag] = "0";
										}

										// 69153 9716953 205501001 2 Ů����ӡ����֯������
										// ��ɫ 155/80A��155/80
										
										if (iCurrent % 500 == 0) {
											strTemp = String.format(
													"�����ǵ����������,��{0}��,�ѵ���{1}��",
													iTotalNum, iCurrent);
										}
										// 69153 9716953 205501001 2 Ů����ӡ����֯������
										// ��ɫ 155/80A��155/80
										objValue = new Object[iColumnNum]; 
					                    
										ContentValues a = new ContentValues(); 
										
										for (int i = 0; i < iColumnNum; i++) {
											for(int j = 0; j < iColumnNum; j++){
												if(!strColumn[i].trim().equals("")){
													objValue[i] = strColumn[i].trim();
													
												}
											}
											a.put(listColumn.get(i).strOriName.toString().trim(), (String) objValue[i]);
											// [69153,9716953,205501001,2,Ů����ӡ����֯������]
										
										   	}
										
										list.add(a);
										
										
									}
									
//									Log.e("-----------lzw--��ʼʱ��-",getNowTime() +list.size());
									if (list.size()>0&&sp.getString(ConfigEntity.ImportTopTitleKey,
												ConfigEntity.ImportTopTitle).equals("1")) {
										list.remove(0);
									}
									if(list.size()>0){
										
										dialog.setMax(list.size());
										sqlStockDataSqlite.exeSql(null,list,mHandler);
									}
									
//									Log.e("-------Lzw----����ʱ��---",getNowTime());
									
									iRet = 1;
								} catch (Exception ex) {
									dialog.dismiss();
									ex.printStackTrace();
									Utility.deBug(TAG, ex.getMessage()
											.toString());
									iRet = 0;
								}
								Log.e("--22------------",getNowTime());
								
								// ������ʾ����
//								 exe.SetMessage(strTemp);
								String strSql = "";
								strSql = sbSql.toString();
								try {
									if (iRet == 1) {
										strTemp = String.format(
												"���ڵ���������ϣ���{0}�У��ѵ���{0}��",
												iTotalNum);
										strSql = "select GdBarCode from GdInfo where GdBarcode='123455677'";
										Object obj = sqlStockDataSqlite
												.GetContentByexeSql(strSql);
										strSql = "select GdBarCode from GdInfo where GdCode='655353535'";
										obj = sqlStockDataSqlite
												.GetContentByexeSql(strSql);

										// region ���������뵼�룬���п�ɫ�����������ĵ���
										if (false/*
												 * Integer.parseInt(columnList.get
												 * (0))== 0
												 * &&Integer.parseInt(columnList
												 * .get(1)) == 0
												 */) {
											if (sp.getString(
													ConfigEntity.BarCodeCutSettingKey,
													ConfigEntity.BarCodeCutSetting)
													.equals("1")) // �ɫ���볤�Ȳ��
											{
												if (GlobalRunConfig
														.GetInstance().barSplitSet.iColorSerial == 2) {
													strSql = "Update GdInfo set GdCode=GdStyle+GDColorID+GDSizeID";
												} else if (GlobalRunConfig
														.GetInstance().barSplitSet.iSizeSerial == 2) {
													strSql = "Update GdInfo set GdCode=GdStyle+GDSizeID+GDColorID";
												}
											} else if (sp
													.getString(
															ConfigEntity.BarCodeCutSettingKey,
															ConfigEntity.BarCodeCutSetting)
													.equals("2")) // ���ţ��볤�Ȳ��
											{
												strSql = "Update GdInfo set GdCode=GdArtNO+GDSizeID";
											} else if (sp
													.getString(
															ConfigEntity.BarCodeCutSettingKey,
															ConfigEntity.BarCodeCutSetting)
													.equals("3")) // �ɫ����ָ����ָ�
											{
												if (GlobalRunConfig
														.GetInstance().barSplitSet.iColorSerial == 2) {
													strSql = "Update GdInfo set GdCode=GdStyle"
															+ "+'"
															+ GlobalRunConfig
																	.GetInstance().barSplitSet.strSeparator1;
													strSql += "'+"
															+ "GDColorID"
															+ "+'"
															+ GlobalRunConfig
																	.GetInstance().barSplitSet.strSeparator2
															+ "'+";
													strSql += "GDSizeID";
												} else if (GlobalRunConfig
														.GetInstance().barSplitSet.iSizeSerial == 2) {
													strSql = "Update GdInfo set GdCode=GdStyle"
															+ "+'"
															+ GlobalRunConfig
																	.GetInstance().barSplitSet.strSeparator1;
													strSql += "'+"
															+ "GDSizeID"
															+ "+'"
															+ GlobalRunConfig
																	.GetInstance().barSplitSet.strSeparator2
															+ "'+";
													strSql += "GDColorID";
												}
											} else if (sp
													.getString(
															ConfigEntity.BarCodeCutSettingKey,
															ConfigEntity.BarCodeCutSetting)
													.equals("4")) // ���ţ���ָ����ָ�
											{
												strSql = "Update GdInfo set GdCode=GdArtNO"
														+ "+'"
														+ GlobalRunConfig
																.GetInstance().barSplitSet.strSeparator1
														+ "'+" + "GDSizeID";
											} else {
												strSql = "select 1";
											}
											// DBAccess.DBAccess.DALDir.ExecuteNonQuery(strSql,
											// false);
											sqlStockDataSqlite.exeSql(strSql,null,null);
										}
										// endregion
										
//										 exe.SetMessage("���ݵ�����ɣ�");
										iRet = 1;
									
									} else {
//										 sr.Close();
//										 exe.SetMessage("���ݵ������");
										dialog.dismiss();
										iRet = 2;
									
									}
									dialog.dismiss();
								} catch (Exception ex) {
									dialog.dismiss();
									ex.printStackTrace();
									String strTmp = ex.getMessage()
											.toUpperCase();
									Utility.deBug(TAG, ex.getMessage());

									if (strTmp.indexOf(strIndexBarcode
											.toUpperCase()) != -1
											|| strTmp.indexOf(strIndexcode
													.toUpperCase()) != -1)
										iRet = -2;
									else
										iRet = 0;
								}
								
									try {
										buffreader.close();
										instream.close();
									} catch (IOException e) {							
										e.printStackTrace();
										iRet = 0;
									}										
								dialog.dismiss();
							} catch (UnsupportedEncodingException e) {
								
								e.printStackTrace();
								iRet = 0;
							}
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iRet = 0;
						}
						return iRet;
					
					}
				}           
				@Override
				protected void onPostExecute(Object result) {
					dialog.dismiss();
					int returnInt = (Integer) result;
					if (returnInt == 1) {
						AlertUtil.showAlert(DataExportInActivity.this,
								R.string.dialog_title,
								R.string.import_data_success, R.string.ok,
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
								
										AlertUtil.dismissDialog();

									}
								});
					} else if (returnInt == -2) {
						AlertUtil.showAlert(DataExportInActivity.this,
								R.string.warning, R.string.import_error,
								R.string.ok, new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					} else if (returnInt == -1) {
						AlertUtil.showAlert(DataExportInActivity.this,
								R.string.warning,
								R.string.import_error_columns, R.string.ok,
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										AlertUtil.dismissDialog();
									}
								});
					} else {
						AlertUtil.showAlert(DataExportInActivity.this,
								R.string.warning,
								R.string.import_error_content, R.string.ok,
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
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug(TAG, "UpdateGoodsDatabase" + e.getMessage());
		}

	}
	public static String getNowTime(){      
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");      
        Date date = new Date(System.currentTimeMillis());      
        return simpleDateFormat.format(date);      
    }     
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 66:
				mDialog.setProgress(hasRead);
				break;
			case 6:
				AlertUtil.showAlert(DataExportInActivity.this,
						R.string.dialog_title, R.string.data_not_found,
						R.string.ok, new View.OnClickListener() {

							@Override
							public void onClick(View v) {
                                	AlertUtil.dismissDialog();
							}
						});
				break;
			case 10:
				
				isNumberImportedFiles++;
				 if(isNumberImportedFiles%50==0){
					 TextView tv =((TextView) dialog.findViewById(R.id.progressbar_button_textview1));
					 tv.setText(AlertUtil.getString(R.string.import_data)+"( "+isNumberImportedFiles+"/"+dialog.getMax()+" )");
				 }
			
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		// ��ȷ����������
		if (requestCode == REQUEST_CODE) {
			Bundle bundle = null;
			if (data != null && (bundle = data.getExtras()) != null) {
				fileName = bundle.getString("file");
				ImportProduct();
			}
		}
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			finish();
			break;
		}

		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ����Activity&��ջ���Ƴ���Activity
		myApplication.getInstance().finishActivity(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		CustomTitleBar.setActivity(this);
		initView();
		super.onResume();
	}
}