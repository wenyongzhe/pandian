package com.supoin.commoninventory.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.supoin.commoninventory.db.entity.CheckListEntity;
import com.supoin.commoninventory.db.entity.MainSummaryInfo;
import com.supoin.commoninventory.entity.BarSplitLenSet;
import com.supoin.commoninventory.entity.CheckDetail;
import com.supoin.commoninventory.entity.CheckMain;
import com.supoin.commoninventory.entity.ConfigEntity;
import com.supoin.commoninventory.entity.ExportSet;
import com.supoin.commoninventory.entity.GdInfo;
import com.supoin.commoninventory.entity.ImportSet;
import com.supoin.commoninventory.entity.LengthSet;
import com.supoin.commoninventory.entity.STROrder;
import com.supoin.commoninventory.entity.User;
import com.supoin.commoninventory.entity.VerifyWay;
import com.supoin.commoninventory.util.LogUtil;
import com.supoin.commoninventory.util.Utility;

public class SQLOutStockDataSqlite extends SQLDataSqlite {

	protected StockDataOpenHelper helper = null;
	protected LocalStockDataOpenHelper helperLocal = null;

	public SQLOutStockDataSqlite() {
		super();
	}

	public SQLOutStockDataSqlite(Context context) {
		this.context = context;
		helper = StockDataOpenHelper.getInstance(context);
	}

	public SQLOutStockDataSqlite(Context context, boolean isWrite) {
		this.context = context;
		helper = StockDataOpenHelper.getInstance(context);
		if (isWrite) {
			db = helper.getWritableDatabase();
		} else {
			db = helper.getReadableDatabase();
		}

		helperLocal = LocalStockDataOpenHelper.getInstance(context);
		if (isWrite) {
			dbLocal = helperLocal.getWritableDatabase();
		} else {
			dbLocal = helperLocal.getReadableDatabase();
		}

		sp = context.getSharedPreferences("InvenConfig", context.MODE_PRIVATE);
	}

	// / <summary>
	// / 获取表的列名
	// / </summary>
	// / <returns></returns>
	/**
	 * 判断某张表是否存在
	 * 
	 * @param tabName
	 *            表名
	 * @return
	 */
	public boolean tabbleIsExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public String GetContentByexeSql(String strSql) {
		String content = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			if (cursor.moveToFirst()) {
				content = cursor.getString(cursor.getColumnCount() - 1);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug("GetContentByexeSql error ", e.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return content;
	}

	// 获取ImportSetInfo的列名
	public List<String> GetImportSetColumnList() {
		List<String> columnList = new ArrayList<String>();
		Cursor cursor = null;
		String strSql = "select  *  from sqlite_master WHERE type='table' and name = 'ImportSetInfo'";
		// String strSql="select * from ImportSetInfo";
		db.beginTransaction();
		try {

			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				strSql = "select  name,sql  from sqlite_master WHERE type='table' AND name = 'ImportSetInfo'";
				Cursor cursor1 = db.rawQuery(strSql, null);
				while (cursor1.moveToNext()) {
					String createSql = cursor1.getString(cursor1
							.getColumnIndex("sql"));
					String tempStr = createSql
							.substring(createSql.indexOf("("),
									createSql.length() - 1).substring(1)
							.replace("integer(4)", "")
							.replace("nvarchar(20)", "")
							.replace(" integer(8)", "").trim();
					String[] strArr = tempStr.split(",");
					for (int i = 0; i < strArr.length; i++) {
						String childStr = strArr[i].replace(",", "").trim();
						columnList.add(childStr);
					}
				}
				cursor1.close();
			}
		} catch (Exception e) {

		} finally {
			db.endTransaction();
			cursor.close();
		}
		return columnList;
	}

	// / <summary>
	// / 获取ImportSet列表
	// / </summary>
	// / <returns></returns>
	public List<ImportSet> GetImportSetList() {
		List<ImportSet> importSetList = new ArrayList<ImportSet>();
		Cursor cursor = null;
		String strSql = "select * from ImportSetInfo";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				ImportSet importSet = new ImportSet();
				importSet.iArtNO = cursor
						.getInt(cursor.getColumnIndex("ArtNO"));
				importSet.iBarcode = cursor.getInt(cursor
						.getColumnIndex("BarCode"));
				importSet.iBig = cursor.getInt(cursor.getColumnIndex("Big"));
				importSet.iCode = cursor.getInt(cursor.getColumnIndex("Code"));
				importSet.iColorID = cursor.getInt(cursor
						.getColumnIndex("ColorID"));
				importSet.iColorName = cursor.getInt(cursor
						.getColumnIndex("ColorName"));
				importSet.iColumnNum = cursor.getInt(cursor
						.getColumnIndex("ColumnNum"));
				importSet.iName = cursor.getInt(cursor.getColumnIndex("Name"));
				importSet.iPrice = cursor
						.getInt(cursor.getColumnIndex("Price"));
				importSet.iSizeID = cursor.getInt(cursor
						.getColumnIndex("SizeID"));
				importSet.iSizeName = cursor.getInt(cursor
						.getColumnIndex("SizeName"));
				importSet.iSmall = cursor
						.getInt(cursor.getColumnIndex("Small"));
				importSet.iStock = cursor
						.getInt(cursor.getColumnIndex("Stock"));
				importSet.iStyle = cursor
						.getInt(cursor.getColumnIndex("Style"));
				importSet.strListSeparator = cursor.getString(cursor
						.getColumnIndex("ListSeparator"));
				importSetList.add(importSet);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return importSetList;
	}

	// / <summary>
	// / 保存ImportSet列表
	// / </summary>
	// / <returns></returns>
	public void saveImportSet(ImportSet importSet) {
		String strSql = "delete from ImportSetInfo";
		db.beginTransaction();
		try {
			db.execSQL(strSql);
			strSql = "insert into ImportSetInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			db.execSQL(strSql, new Object[] { importSet.iBarcode,
					importSet.iCode, importSet.iArtNO, importSet.iStyle,
					importSet.iName, importSet.iColorID, importSet.iColorName,
					importSet.iStock, importSet.iSizeID, importSet.iSizeName,
					importSet.iBig, importSet.iSmall, importSet.iPrice,
					importSet.iColumnNum, importSet.strListSeparator });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	// / <summary>
	// / 保存ExportSet列表
	// / </summary>
	// / <returns></returns>
	public void saveExportSet(ExportSet exportSet) {
		String strSql = "delete from OutStockExportSetInfo";
		db.beginTransaction();
		try {
			db.execSQL(strSql);
			strSql = "insert into OutStockExportSetInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			db.execSQL(strSql, new Object[] { exportSet.iShopID,
					exportSet.iCheckID, exportSet.iPositionID, exportSet.iBar,
					exportSet.iArtNO, exportSet.iStyle, exportSet.iName,
					exportSet.iColorID, exportSet.iColorName, exportSet.iStock,
					exportSet.iSizeID, exportSet.iSizeName, exportSet.iBig,
					exportSet.iSmall, exportSet.iPrice, exportSet.iQty,
					exportSet.iColumnNum, exportSet.strListSeparator,
					exportSet.iHead, exportSet.iScanDate });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	// / <summary>
	// / 获取ExportSet列表
	// / </summary>
	// / <returns></returns>
	public List<ExportSet> getExportSetList() {
		List<ExportSet> exportSetList = new ArrayList<ExportSet>();
		Cursor cursor = null;
		String strSql = "select * from OutStockExportSetInfo";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				ExportSet exportSet = new ExportSet();
				exportSet.iArtNO = cursor
						.getInt(cursor.getColumnIndex("ArtNO"));
				exportSet.iBar = cursor.getInt(cursor.getColumnIndex("Bar"));
				exportSet.iShopID = cursor.getInt(cursor
						.getColumnIndex("ShopID"));
				exportSet.iCheckID = cursor.getInt(cursor
						.getColumnIndex("CheckID"));
				exportSet.iPositionID = cursor.getInt(cursor
						.getColumnIndex("PositionID"));
				exportSet.iBig = cursor.getInt(cursor.getColumnIndex("Big"));
				exportSet.iColorID = cursor.getInt(cursor
						.getColumnIndex("ColorID"));
				exportSet.iColorName = cursor.getInt(cursor
						.getColumnIndex("ColorName"));
				exportSet.iColumnNum = cursor.getInt(cursor
						.getColumnIndex("ColumnNum"));
				exportSet.iName = cursor.getInt(cursor.getColumnIndex("Name"));
				exportSet.iPrice = cursor
						.getInt(cursor.getColumnIndex("Price"));
				exportSet.iSizeID = cursor.getInt(cursor
						.getColumnIndex("SizeID"));
				exportSet.iSizeName = cursor.getInt(cursor
						.getColumnIndex("SizeName"));
				exportSet.iSmall = cursor
						.getInt(cursor.getColumnIndex("Small"));
				exportSet.iStock = cursor
						.getInt(cursor.getColumnIndex("Stock"));
				exportSet.iStyle = cursor
						.getInt(cursor.getColumnIndex("Style"));
				exportSet.iQty = cursor.getInt(cursor.getColumnIndex("Qty"));
				exportSet.iScanDate = cursor.getInt(cursor
						.getColumnIndex("ScanDate"));
				exportSet.iHead = cursor.getInt(cursor.getColumnIndex("Head"));
				exportSet.strListSeparator = cursor.getString(cursor
						.getColumnIndex("ListSeparator"));
				exportSetList.add(exportSet);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug(" getExportSetList Method Error", e.toString());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return exportSetList;
	}

	// / <summary>
	// /
	// / </summary>
	// / <returns></returns>
	public LengthSet GetLengthSetInUsage() {
		LengthSet lengthSet = new LengthSet();
		String strSql = "select Item,lenMin,LenMax,Content from LengthSet where InUsage=1";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				lengthSet.iItem = cursor.getInt(cursor.getColumnIndex("Item"));
				if (lengthSet.iItem == 2) // 长度限制
				{
					String strTmp = cursor.getString(cursor
							.getColumnIndex("Content"));
					String[] strArr = strTmp.split(",");
					if (strArr.length != 6) {
						throw new Exception("LengthSet Lengths error!");
					} else {
						lengthSet.iLen1 = Integer.parseInt((strArr[0]));
						lengthSet.iLen2 = Integer.parseInt((strArr[1]));
						lengthSet.iLen3 = Integer.parseInt((strArr[2]));
						lengthSet.iLen4 = Integer.parseInt((strArr[3]));
						lengthSet.iLen5 = Integer.parseInt((strArr[4]));
						lengthSet.iLen6 = Integer.parseInt((strArr[5]));
					}
				} else if (lengthSet.iItem == 3) {
					lengthSet.iLenMin = cursor.getInt(cursor
							.getColumnIndex("LenMin"));
					lengthSet.iLenMax = cursor.getInt(cursor
							.getColumnIndex("LenMax"));
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("LengthSet GetLengthSetInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return lengthSet;
	}

	// / <summary>
	// /
	// / </summary>
	// / <returns></returns>
	public int UpdateLengthSetInUsage(LengthSet lengthSet) {
		int iRet = 1;
		String strSql = "Update LengthSet set InUsage = 0";
		db.beginTransaction();
		try {
			db.execSQL(strSql);

			strSql = "Update LengthSet set InUsage = 1,LenMin=?,LenMax=?,content=? where Item=?";
			db.execSQL(
					strSql,
					new Object[] {
							lengthSet.iLenMin,
							lengthSet.iLenMax,
							String.format("{0},{1},{2},{3},{4},{5}",
									lengthSet.iLen1, lengthSet.iLen2,
									lengthSet.iLen3, lengthSet.iLen4,
									lengthSet.iLen5, lengthSet.iLen6),
							lengthSet.iItem });
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("LengthSet UpdateInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	/**
	 * 长度设置
	 * 
	 * @param lengthSet
	 */
	public int UpdateLengthSetInUsage1(LengthSet lengthSet) {
		int iRet = 1;
		String strSql = "Update LengthSet set InUsage = 0";
		db.beginTransaction();
		try {
			db.execSQL(strSql);

			strSql = "select * from LengthSet where Item = ?";
			Cursor cursor = db.rawQuery(strSql,
					new String[] { String.valueOf(lengthSet.iItem) });

			if (cursor.moveToFirst()) {
				strSql = "Update LengthSet set InUsage = 1,LenMin=?,LenMax=?,content=? where Item=?";
				db.execSQL(
						strSql,
						new Object[] {
								lengthSet.iLenMin,
								lengthSet.iLenMax,
								String.format("%1$d,%2$d,%3$d,%4$d,%5$d,%6$d",
										lengthSet.iLen1, lengthSet.iLen2,
										lengthSet.iLen3, lengthSet.iLen4,
										lengthSet.iLen5, lengthSet.iLen6),
								lengthSet.iItem });
			} else {
				strSql = "insert into LengthSet values(?,?,?,?,?,?)";
				lengthSet.strContent = String.format(
						"%1$d,%2$d,%3$d,%4$d,%5$d,%6$d", lengthSet.iLen1,
						lengthSet.iLen2, lengthSet.iLen3, lengthSet.iLen4,
						lengthSet.iLen5, lengthSet.iLen6);
				db.execSQL(strSql, new Object[] { lengthSet.iItem, 1,
						lengthSet.iLenMin, lengthSet.iLenMax,
						lengthSet.strContent, lengthSet.strDescription });
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("LengthSet UpdateInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			if (null != cursor) {
				cursor.close();
			}
		}
		return iRet;
	}

	// 通过条码获取CheckDetail中的数据
	public CheckDetail getDetailData(String gdBar) {

		String sqString = "select GdBarCode,GdArtNO,GdStyle,GdName,GdColorID,GdColorName,GdSizeID,"
				+ "GdSizeName,Property1,Property2,GdStock,GdPrice FROM GdInfo WHERE GdBarCode = ? OR GdCode = ? ";
		CheckDetail checkDetail = null;
		Cursor cursor;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(sqString, new String[] { gdBar, gdBar });
			checkDetail = new CheckDetail();
			if (cursor.moveToFirst()) {
				if (cursor.getString(cursor.getColumnIndex("GdBarCode")) != null) {
					checkDetail.strGdBar = cursor.getString(cursor
							.getColumnIndex("GdBarCode"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdArtNO")) != null) {
					checkDetail.strGdArtNO = cursor.getString(cursor
							.getColumnIndex("GdArtNO"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdStyle")) != null) {
					checkDetail.strGdStyle = cursor.getString(cursor
							.getColumnIndex("GdStyle"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdName")) != null) {
					checkDetail.strGdName = cursor.getString(cursor
							.getColumnIndex("GdName"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdColorID")) != null) {
					checkDetail.strGdColorID = cursor.getString(cursor
							.getColumnIndex("GdColorID"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdColorName")) != null) {
					checkDetail.strGdColorName = cursor.getString(cursor
							.getColumnIndex("GdColorName"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdSizeID")) != null) {
					checkDetail.strGdSizeID = cursor.getString(cursor
							.getColumnIndex("GdSizeID"));
				}
				if (cursor.getString(cursor.getColumnIndex("GdSizeName")) != null) {
					checkDetail.strGdSizeName = cursor.getString(cursor
							.getColumnIndex("GdSizeName"));
				}
				if (cursor.getString(cursor.getColumnIndex("Property1")) != null) {
					checkDetail.strProperty1 = cursor.getString(cursor
							.getColumnIndex("Property1"));
				}
				if (cursor.getString(cursor.getColumnIndex("Property2")) != null) {
					checkDetail.strProperty2 = cursor.getString(cursor
							.getColumnIndex("Property2"));
				}
				if (cursor.getInt(cursor.getColumnIndex("GdStock")) != 0) {
					checkDetail.dStock = cursor.getInt(cursor
							.getColumnIndex("GdStock"));
				}
				if (cursor.getFloat(cursor.getColumnIndex("GdPrice")) != 0) {
					checkDetail.dGdPrice = cursor.getFloat(cursor
							.getColumnIndex("GdPrice"));
				}

			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
		} finally {
			db.endTransaction();
		}

		return checkDetail;
	}

	// / <summary>
	// / useless ,2012.6.15
	// / </summary>
	// / <param name="strType"></param>
	// / <returns></returns>
	public int DeleteDetailData(String strType, CheckDetail checkDetail) {
		int iRet = 1;
		try {
			String strSql = "";
			if (strType.equals("0")) {
				strSql = "delete from OutStockCheckDetail where ShopID=? and CheckID=?";
				db.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID });
			} else {
				strSql = "delete from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=?";
				db.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID });
			}
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail DeleteDetailData Error:",
					ex.getMessage());
		}
		return iRet;
	}

	/** 获取导出数据，拆分条码形式为条码+1 **/
	public List<String[]> GetExportDatasSplitBarcode(String strExportType,
			List<STROrder> strOrders, String strCheckID, String strShopID,
			String strPositionID) {
		// ID ShopID CheckID PositionID GdBar CheckNum GdArtNO GdStyleGdColorID
		// GdSizeID CheckTime GdName GdColorName GdSizeName Property1 Property2
		// GdStock GdPrice
		// List<String[]> stringList= new ArrayList<String[]>();
		List<String[]> stringList = new ArrayList<String[]>();
		String[] str = new String[strOrders.size()];
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		boolean flag = false;// 判断CheckNum是否大于1
		try {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < strOrders.size(); i++) {
				buffer.append(strOrders.get(i).strOriName + ",");
			}
			String sName = buffer.toString().substring(0,
					buffer.toString().length() - 1);
			LogUtil.i(sName);
			if (strExportType.equals("0")) {
				strSql = "select " + sName + " From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=?";

				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID });
				while (cursor.moveToNext()) {
					flag = false;
					str = new String[strOrders.size()];
					for (int i = 0; i < strOrders.size(); i++) {
						str[i] = cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName));
						if (strOrders.get(i).strOriName.equals("CheckNum")) {// 数量
							int checkNum = Integer.parseInt(str[i]);
							if (checkNum > 1) {
								flag = true;
								for (int j = 0; j < checkNum; j++) {
									str[i] = 1 + "";
									stringList.add(str);
								}
							}
						}
						if (str[i] == null) {
							str[i] = "";
						}
					}
					if (!flag) {
						stringList.add(str);
					}

				}
			} else if (strExportType.equals("1")) {
				strSql = "select " + sName + " From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? and PositionID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					flag = false;
					str = new String[strOrders.size()];
					for (int i = 0; i < strOrders.size(); i++) {
						str[i] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (strOrders.get(i).strOriName.equals("CheckNum")) {// 数量
							int checkNum = Integer.parseInt(str[i]);
							if (checkNum > 1) {
								flag = true;
								for (int j = 0; j < checkNum; j++) {
									str[i] = 1 + "";
									stringList.add(str);
								}
							}
						}
						if (str[i] == null) {
							str[i] = "";
						}
					}
					if (!flag) {
						stringList.add(str);
					}
				}
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetExportCustom Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return stringList;
	}

	public List<String[]> GetExportDatas(String strExportType,
			List<STROrder> strOrders, String strCheckID, String strShopID,
			String strPositionID) {
		// ID ShopID CheckID PositionID GdBar CheckNum GdArtNO GdStyleGdColorID
		// GdSizeID CheckTime GdName GdColorName GdSizeName Property1 Property2
		// GdStock GdPrice
		// List<String[]> stringList= new ArrayList<String[]>();
		List<String[]> stringList = new ArrayList<String[]>();
		String[] str = new String[strOrders.size()];
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < strOrders.size(); i++) {
				buffer.append(strOrders.get(i).strOriName + ",");
			}
			String sName = buffer.toString().substring(0,
					buffer.toString().length() - 1);
			LogUtil.i(sName);
			if (strExportType.equals("0")) {
				strSql = "select " + sName + " From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=?";

				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size()];
					for (int i = 0; i < strOrders.size(); i++) {
						str[i] = cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					stringList.add(str);

				}
			} else if (strExportType.equals("1")) {
				strSql = "select " + sName + " From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? and PositionID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size()];
					for (int i = 0; i < strOrders.size(); i++) {
						str[i] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					stringList.add(str);
				}
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetExportCustom Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return stringList;
	}

	/** 导出数据根据数量拆分为一条一条 **/
	public List<String[]> GetExportDatasByScanOrderSplitBarcode(
			String strExportType, List<STROrder> strOrders, String strCheckID,
			String strShopID, String strPositionID) {
		List<String[]> stringList = new ArrayList<String[]>();
		String[] str = null;
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			boolean flag = false;// 判断CheckNum是否大于1
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < strOrders.size(); i++) {
				buffer.append(strOrders.get(i).strOriName + ",");
			}
			if (strExportType.equals("0")) {
				strSql = "select ShopID," + buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? order by ID asc";

				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 1] = (cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					stringList.add(str);

				}
			} else if (strExportType.equals("1")) {
				strSql = "select ShopID,"
						+ buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? and PositionID=? order by ID asc";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					flag = false;
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					int checkNum = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					if (checkNum > 1) {
						flag = true;
						for (int j = 0; j < checkNum; j++) {
							str[strOrders.size() + 1] = 1 + "";
							stringList.add(str);
						}
					} else {
						str[strOrders.size() + 1] = 1 + "";
					}
					if (!flag) {
						stringList.add(str);
					}

				}
			} else {
				// 全部合并导出
				strSql = "select ShopID," + buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? order by ID asc";
				cursor = db.rawQuery(strSql, new String[] { strShopID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					int checkNum = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					if (checkNum > 1) {
						flag = true;
						for (int j = 0; j < checkNum; j++) {
							str[strOrders.size() + 1] = 1 + "";
							stringList.add(str);
						}
					} else {
						str[strOrders.size() + 1] = 1 + "";
					}
					if (!flag) {
						stringList.add(str);
					}

				}
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetExportCustom Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return stringList;
	}

	public List<String[]> GetExportDatasByScanOrder(String strExportType,
			List<STROrder> strOrders, String strCheckID, String strShopID,
			String strPositionID) {
		List<String[]> stringList = new ArrayList<String[]>();
		String[] str = null;
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < strOrders.size(); i++) {
				buffer.append(strOrders.get(i).strOriName + ",");
			}
			if (strExportType.equals("0")) {
				strSql = "select ShopID," + buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? order by ID asc";

				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 1] = (cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					stringList.add(str);

				}
			} else if (strExportType.equals("1")) {
				strSql = "select ShopID,"
						+ buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? and CheckID=? and PositionID=? order by ID asc";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 1] = (cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					stringList.add(str);
				}
			} else {
				// 全部合并导出
				strSql = "select ShopID," + buffer
						+ "CheckNum,CheckTime From OutStockCheckDetail"
						+ " where ShopID=? order by ID asc";
				cursor = db.rawQuery(strSql, new String[] { strShopID });
				while (cursor.moveToNext()) {
					str = new String[strOrders.size() + 3];
					str[0] = (cursor.getString(cursor.getColumnIndex("ShopID")));
					for (int i = 0; i < strOrders.size(); i++) {
						str[i + 1] = (cursor.getString(cursor
								.getColumnIndex(strOrders.get(i).strOriName)));
						if (str[i] == null) {
							str[i] = "";
						}
					}
					str[strOrders.size() + 1] = (cursor.getString(cursor
							.getColumnIndex("CheckNum")));
					str[strOrders.size() + 2] = (cursor.getString(cursor
							.getColumnIndex("CheckTime")));
					stringList.add(str);
				}
			}

			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetExportCustom Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return stringList;
	}

	// / <summary>
	// / 获取指定导出方式下的导出盘点单的导出数量之和，如果出错，则返回-1
	// / </summary>
	// / <param name="strExportType">0，1，2分别表示按编号导出，按编号、货位导出，按当前系统全部导出</param>
	// / <returns></returns>
	public float GetSpeTotalQty(String strExportType, String strShopID,
			String strCheckID, String strPositionID) {
		float dTotal = 0;
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			if (strExportType.equals("0")) // 按编号
			{
				strSql = "select CheckNum From OutStockCheckDetail where ShopID=? And CheckID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					float checkNum = cursor.getFloat(cursor
							.getColumnIndex("CheckNum"));
					dTotal += checkNum;
				}
			} else if (strExportType.equals("1")) // 按编号，货位
			{
				strSql = "select CheckNum From OutStockCheckDetail where ShopID=? And CheckID=? And PositionID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					float checkNum = cursor.getFloat(cursor
							.getColumnIndex("CheckNum"));
					dTotal += checkNum;
				}
			} else // 全部导出
			{
				strSql = "select CheckNum from OutStockCheckDetail where ShopID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID });
				while (cursor.moveToNext()) {
					float checkNum = cursor.getFloat(cursor
							.getColumnIndex("CheckNum"));
					dTotal += checkNum;
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail ExistSpecifyBar Method Error",
					ex.getMessage());
			// dTotal = -1M;
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return dTotal;
	}

	// / <summary>
	// / Inuse
	// / </summary>
	// / <param name="strType"></param>
	// / <returns></returns>
	public int DeleteSingleData(String strType, String strCheckID,
			String strPositionID, String strShopID) {
		int iRet = 1;
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			String strSqlMain = "";
			String strSqlDetail = "";
			String strSqlSingle = "";
			if (strType.equals("0")) {
				strSqlMain = "delete from OutStockCheckMain where ShopID=? and CheckID=?";
				strSqlDetail = "delete from OutStockCheckDetail where ShopID=? and CheckID=?";
				strSqlSingle = "delete from OutStockCheckDetailSingle where ShopID=? and CheckID=?";
				db.execSQL(strSqlMain, new Object[] { strShopID, strCheckID });
				db.execSQL(strSqlDetail, new Object[] { strShopID, strCheckID });
				db.execSQL(strSqlSingle, new Object[] { strShopID, strCheckID });
				dbLocal.execSQL(strSqlMain, new Object[] { strShopID,
						strCheckID });
				dbLocal.execSQL(strSqlDetail, new Object[] { strShopID,
						strCheckID });
				dbLocal.execSQL(strSqlSingle, new Object[] { strShopID,
						strCheckID });
			} else {
				strSqlMain = "delete from OutStockCheckMain where ShopID=? and CheckID=? and PositionID=?";
				strSqlDetail = "delete from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=?";
				strSqlSingle = "delete from OutStockCheckDetailSingle where ShopID=? and CheckID=? and PositionID=?";
				db.execSQL(strSqlMain, new Object[] { strShopID, strCheckID,
						strPositionID });
				db.execSQL(strSqlDetail, new Object[] { strShopID, strCheckID,
						strPositionID });
				db.execSQL(strSqlSingle, new Object[] { strShopID, strCheckID,
						strPositionID });
				dbLocal.execSQL(strSqlMain, new Object[] { strShopID,
						strCheckID, strPositionID });
				dbLocal.execSQL(strSqlDetail, new Object[] { strShopID,
						strCheckID, strPositionID });
				dbLocal.execSQL(strSqlSingle, new Object[] { strShopID,
						strCheckID, strPositionID });
			}
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail DeleteDetailData Error:",
					ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	public int DeleteAllDetailData(String shopID) {
		int iRet = 1;
		String strSql = "delete from OutStockCheckDetail where ShopID=?";
		try {
			db.execSQL(strSql, new Object[] { shopID });
			dbLocal.execSQL(strSql, new Object[] { shopID });
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail DeleteAllDetailData Error:",
					ex.getMessage());
		}
		return iRet;
	}

	public int DeleteAllCheckData(String strShopID) {
		int iRet = 1;
		String strSqlMain = "delete from OutStockCheckMain where ShopID=?";
		String strSqlDetail = "delete from OutStockCheckDetail where ShopID=?";
		String strSqlDetailSingle = "delete from OutStockCheckDetailSingle where ShopID=?";
		try {
			db.execSQL(strSqlMain, new Object[] { strShopID });
			db.execSQL(strSqlDetail, new Object[] { strShopID });
			db.execSQL(strSqlDetailSingle, new Object[] { strShopID });
			dbLocal.execSQL(strSqlMain, new Object[] { strShopID });
			dbLocal.execSQL(strSqlDetail, new Object[] { strShopID });
			dbLocal.execSQL(strSqlDetailSingle, new Object[] { strShopID });
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail DeleteAllDetailData Error:",
					ex.getMessage());
		}
		return iRet;
	}

	public int DeleteAllShops() {
		int iRet = 1;
		String strSqlMain = "delete from OutStockCheckMain";
		String strSqlDetail = "delete from OutStockCheckDetail";
		String strSqlDetailSingle = "delete from OutStockCheckDetailSingle";
		try {
			db.execSQL(strSqlMain);
			db.execSQL(strSqlDetail);
			db.execSQL(strSqlDetailSingle);
			dbLocal.execSQL(strSqlMain);
			dbLocal.execSQL(strSqlDetail);
			dbLocal.execSQL(strSqlDetailSingle);
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail DeleteAllDetailData Error:",
					ex.getMessage());
		}
		return iRet;
	}

	// 插入详情数据
	// usingBarCode 唯一码
	// valitedMethod 设置是否提示
	public int InsertCheckDetailBySpecial(CheckDetail checkDetail,
			String usingBarCode, String valitedMethod, Boolean status,
			String isInputNum, int specialStatus) {
		int iRet = 0;
		String strSql = "insert into OutStockCheckDetail (ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.beginTransaction();
		int i = checkUniqueCode(checkDetail.strShopID, checkDetail.strCheckID,
				checkDetail.strGdBar);
		// bug1363
		int j = checkUniqueCodeFromCheckDetail(checkDetail.strShopID,
				checkDetail.strCheckID, checkDetail.strGdBar);
		try {

			// 设置输入输入的时候
			if (isInputNum.equals("0")) {

				// 2是设置提示的情况
			} else if (valitedMethod.equals("2")) {
				// 如果基础资料中无此条码时
				if (i != 1) {

				} else {
					// 如果CheckDetail中无此条码时
					if (j != 1) {
						db.execSQL(
								strSql,
								new Object[] { checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar,
										checkDetail.dCheckNum,
										checkDetail.strGdArtNO,
										checkDetail.strGdStyle,
										checkDetail.strGdColorID,
										checkDetail.strGdSizeID,
										checkDetail.strGdName,
										checkDetail.strGdColorName,
										checkDetail.strGdSizeName,
										checkDetail.strProperty1,
										checkDetail.strProperty2,
										checkDetail.dStock,
										checkDetail.dGdPrice,
										checkDetail.strReservedMg,
										Utility.getCurTime() });
						dbLocal.execSQL(
								strSql,
								new Object[] { checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar,
										checkDetail.dCheckNum,
										checkDetail.strGdArtNO,
										checkDetail.strGdStyle,
										checkDetail.strGdColorID,
										checkDetail.strGdSizeID,
										checkDetail.strGdName,
										checkDetail.strGdColorName,
										checkDetail.strGdSizeName,
										checkDetail.strProperty1,
										checkDetail.strProperty2,
										checkDetail.dStock,
										checkDetail.dGdPrice,
										checkDetail.strReservedMg,
										Utility.getCurTime() });
						// 如果CheckDetail中有此条码时
					} else {
						// 获取原有的数量,每次提示,点击确定 + 1
						int num = GetOriginalNum(checkDetail.strShopID,
								checkDetail.strCheckID,
								checkDetail.strPositionID, checkDetail.strGdBar);
						checkDetail.dCheckNum = num + 1;
						strSql = "Update OutStockCheckDetail Set CheckNum=? where ShopID=? And CheckID=? and PositionID=? And GdBar=?";
						db.execSQL(strSql,
								new Object[] { num, checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar });
						dbLocal.execSQL(strSql,
								new Object[] { num, checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar });
					}

				}

				// 当不输入数量的情况下
				if (isInputNum.equals("1")) {
					// 如果CheckDetail中无此条码时
					if (j != 1) {
						db.execSQL(
								strSql,
								new Object[] { checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar,
										checkDetail.dCheckNum,
										checkDetail.strGdArtNO,
										checkDetail.strGdStyle,
										checkDetail.strGdColorID,
										checkDetail.strGdSizeID,
										checkDetail.strGdName,
										checkDetail.strGdColorName,
										checkDetail.strGdSizeName,
										checkDetail.strProperty1,
										checkDetail.strProperty2,
										checkDetail.dStock,
										checkDetail.dGdPrice,
										checkDetail.strReservedMg,
										Utility.getCurTime() });
						dbLocal.execSQL(
								strSql,
								new Object[] { checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar,
										checkDetail.dCheckNum,
										checkDetail.strGdArtNO,
										checkDetail.strGdStyle,
										checkDetail.strGdColorID,
										checkDetail.strGdSizeID,
										checkDetail.strGdName,
										checkDetail.strGdColorName,
										checkDetail.strGdSizeName,
										checkDetail.strProperty1,
										checkDetail.strProperty2,
										checkDetail.dStock,
										checkDetail.dGdPrice,
										checkDetail.strReservedMg,
										Utility.getCurTime() });
						// 如果CheckDetail中有此条码时
					} else {
						// 获取原有的数量,每次提示,点击确定 + 1
						int num = GetOriginalNum(checkDetail.strShopID,
								checkDetail.strCheckID,
								checkDetail.strPositionID, checkDetail.strGdBar);
						checkDetail.dCheckNum = num + 1;
						strSql = "Update OutStockCheckDetail Set CheckNum=? where ShopID=? And CheckID=? and PositionID=? And GdBar=?";
						db.execSQL(strSql,
								new Object[] { checkDetail.dCheckNum,
										checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar });
						dbLocal.execSQL(strSql,
								new Object[] { checkDetail.dCheckNum,
										checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar });
					}
				}

			}

			// region 保存单行唯一表，如果没有启用唯一条码，就不用保存
			if (usingBarCode.equals("1")) {

				String strSqlSingle = "Insert Into OutStockCheckDetailSingle(ShopID,CheckID,PositionID,"
						+ "GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID, GdName,GdColorName,"
						+ "GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,"
						+ "GdBarSingle,CheckTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				db.execSQL(strSqlSingle, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strSingleGdBar, checkDetail.dCheckNum,
						checkDetail.strGdArtNO, checkDetail.strGdStyle,
						checkDetail.strGdColorID, checkDetail.strGdSizeID,
						checkDetail.strGdName, checkDetail.strGdColorName,
						checkDetail.strGdSizeName, checkDetail.strProperty1,
						checkDetail.strProperty2, checkDetail.dStock,
						checkDetail.dGdPrice, checkDetail.strReservedMg,
						checkDetail.strSingleGdBar, Utility.getCurTime() });
				dbLocal.execSQL(strSqlSingle, new Object[] {
						checkDetail.strShopID, checkDetail.strCheckID,
						checkDetail.strPositionID, checkDetail.strSingleGdBar,
						checkDetail.dCheckNum, checkDetail.strGdArtNO,
						checkDetail.strGdStyle, checkDetail.strGdColorID,
						checkDetail.strGdSizeID, checkDetail.strGdName,
						checkDetail.strGdColorName, checkDetail.strGdSizeName,
						checkDetail.strProperty1, checkDetail.strProperty2,
						checkDetail.dStock, checkDetail.dGdPrice,
						checkDetail.strReservedMg, checkDetail.strSingleGdBar,
						Utility.getCurTime() });

				if (specialStatus == 1) {
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strSingleGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
					dbLocal.execSQL(strSql, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID,
							checkDetail.strSingleGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
				}

			} else {

			}

			// endregion
			db.setTransactionSuccessful();
			iRet = 1;
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail SqlInsert Method Error",
					ex.getMessage());

		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	/**
	 * 插入详情数据
	 * 
	 * @param checkDetail
	 * @param usingBarCode
	 *            唯一码
	 * @param valitedMethod
	 *            设置是否提示
	 * @param status
	 * @param isInputNum
	 * @param specialStatus
	 * @return
	 */
	public int InsertCheckDetail(CheckDetail checkDetail, String usingBarCode,
			String valitedMethod, Boolean status, String isInputNum,
			int specialStatus) {
		int iRet = 0;
		String strSql = "insert into OutStockCheckDetail (ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.beginTransaction();
		int i = checkUniqueCode(checkDetail.strShopID, checkDetail.strCheckID,
				checkDetail.strGdBar);
		try {

			// 设置输入输入的时候
			if (isInputNum.equals("0")) {

				// 2是设置提示的情况
			} else if (valitedMethod.equals("2")) {
				// 如果基础资料中无此条码时
				if (i != 1) {

				} else {

					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
					dbLocal.execSQL(strSql, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID, checkDetail.strGdBar,
							checkDetail.dCheckNum, checkDetail.strGdArtNO,
							checkDetail.strGdStyle, checkDetail.strGdColorID,
							checkDetail.strGdSizeID, checkDetail.strGdName,
							checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });

				}

			} else {
				db.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strGdBar, checkDetail.dCheckNum,
						checkDetail.strGdArtNO, checkDetail.strGdStyle,
						checkDetail.strGdColorID, checkDetail.strGdSizeID,
						checkDetail.strGdName, checkDetail.strGdColorName,
						checkDetail.strGdSizeName, checkDetail.strProperty1,
						checkDetail.strProperty2, checkDetail.dStock,
						checkDetail.dGdPrice, checkDetail.strReservedMg,
						Utility.getCurTime() });
				dbLocal.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strGdBar, checkDetail.dCheckNum,
						checkDetail.strGdArtNO, checkDetail.strGdStyle,
						checkDetail.strGdColorID, checkDetail.strGdSizeID,
						checkDetail.strGdName, checkDetail.strGdColorName,
						checkDetail.strGdSizeName, checkDetail.strProperty1,
						checkDetail.strProperty2, checkDetail.dStock,
						checkDetail.dGdPrice, checkDetail.strReservedMg,
						Utility.getCurTime() });
			}

			// region 保存单行唯一表，如果没有启用唯一条码，就不用保存
			if (usingBarCode.equals("1")) {

				String strSqlSingle = "Insert Into OutStockCheckDetailSingle(ShopID,CheckID,PositionID,"
						+ "GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID, GdName,GdColorName,"
						+ "GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,"
						+ "GdBarSingle,CheckTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				db.execSQL(strSqlSingle, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strSingleGdBar, checkDetail.dCheckNum,
						checkDetail.strGdArtNO, checkDetail.strGdStyle,
						checkDetail.strGdColorID, checkDetail.strGdSizeID,
						checkDetail.strGdName, checkDetail.strGdColorName,
						checkDetail.strGdSizeName, checkDetail.strProperty1,
						checkDetail.strProperty2, checkDetail.dStock,
						checkDetail.dGdPrice, checkDetail.strReservedMg,
						checkDetail.strSingleGdBar, Utility.getCurTime() });
				dbLocal.execSQL(strSqlSingle, new Object[] {
						checkDetail.strShopID, checkDetail.strCheckID,
						checkDetail.strPositionID, checkDetail.strSingleGdBar,
						checkDetail.dCheckNum, checkDetail.strGdArtNO,
						checkDetail.strGdStyle, checkDetail.strGdColorID,
						checkDetail.strGdSizeID, checkDetail.strGdName,
						checkDetail.strGdColorName, checkDetail.strGdSizeName,
						checkDetail.strProperty1, checkDetail.strProperty2,
						checkDetail.dStock, checkDetail.dGdPrice,
						checkDetail.strReservedMg, checkDetail.strSingleGdBar,
						Utility.getCurTime() });

				if (specialStatus == 1) {
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strSingleGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
					dbLocal.execSQL(strSql, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID,
							checkDetail.strSingleGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
				}

			} else {

			}

			db.setTransactionSuccessful();
			iRet = 1;
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail SqlInsert Method Error",
					ex.getMessage());

		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	public int GetOriginalNum(String strShopID, String strCheckID,
			String strPositionID, String strGdBar) {
		int dAlreadyQty = 0;
		String strSql = "select CheckNum From OutStockCheckDetail where ShopID=? And CheckID=? and PositionID=? and GdBar=?";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID, strGdBar });
			while (cursor.moveToNext()) {
				int checkNum = cursor.getInt(cursor.getColumnIndex("CheckNum"));
				dAlreadyQty += checkNum;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetOriginalNum Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return dAlreadyQty;
	}

	// 扫描顺序查询
	public List<ScanOrderInfo> GetRecordsScanOrder(String strShopID,
			String strCheckID, String strPositionID) {
		List<ScanOrderInfo> scanOrderInfoList = new ArrayList<ScanOrderInfo>();
		String strSql = "select ID,GdBar,CheckNum from OutStockCheckDetail where ShopID =?"
				+ " and CheckID=? and PositionID=? order by ID asc";
		Cursor cursor = null;
		dbLocal.beginTransaction();
		try {
			cursor = dbLocal.rawQuery(strSql, new String[] { strShopID,
					strCheckID, strPositionID });
			while (cursor.moveToNext()) {
				ScanOrderInfo scanOrderInfo = new ScanOrderInfo();
				scanOrderInfo.CheckNum = cursor.getFloat(cursor
						.getColumnIndex("CheckNum"));
				scanOrderInfo.ID = cursor.getInt(cursor.getColumnIndex("ID"));
				scanOrderInfo.GdBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));

				// if
				// (sp.getString(ConfigEntity.UsingBarCodeKey,ConfigEntity.UsingBarCode).equals("1"))
				// {//启用唯一码时，不加负数
				// if (scanOrderInfo.CheckNum <= 0) {
				// }else {
				// scanOrderInfoList.add(scanOrderInfo);
				// }
				// }else {
				scanOrderInfoList.add(scanOrderInfo);
				// }

			}
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			dbLocal.endTransaction();
			cursor.close();
		}
		return scanOrderInfoList;
	}

	// 扫描顺序查询
	public List<ScanOrderInfo> GetRecordsScanOrderOnLine(String strShopID,
			String strCheckID, String strPositionID) {
		List<ScanOrderInfo> scanOrderInfoList = new ArrayList<ScanOrderInfo>();
		String strSql = "select ID,GdBar,CheckNum from OutStockCheckDetail where ShopID =?"
				+ " and CheckID=? and PositionID=? order by ID asc";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID });
			while (cursor.moveToNext()) {
				ScanOrderInfo scanOrderInfo = new ScanOrderInfo();
				scanOrderInfo.CheckNum = cursor.getInt(cursor
						.getColumnIndex("CheckNum"));
				scanOrderInfo.ID = cursor.getInt(cursor.getColumnIndex("ID"));
				scanOrderInfo.GdBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));
				scanOrderInfoList.add(scanOrderInfo);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return scanOrderInfoList;
	}

	// 编号查询
	public List<StaPositionSummary> GetStaPositionSummary(String strShopID,
			String strCheckID) {
		List<StaPositionSummary> staPositionSummaryList = new ArrayList<StaPositionSummary>();
		String strSql = "select PostionID from OutStockCheckDetail where shopID=? and CheckID=? order by PositionID";
		Cursor cursor = null;
		float sums = 0;
		db.beginTransaction();
		try {
			cursor = db
					.rawQuery(strSql, new String[] { strShopID, strCheckID });
			while (cursor.moveToNext()) {
				StaPositionSummary staPositionSummary = new StaPositionSummary();
				staPositionSummary.PositionID = cursor.getString(cursor
						.getColumnIndex("PostionID"));
				Cursor cursor1 = db.rawQuery(
						"select CheckNum from where PositionID=?",
						new String[] { staPositionSummary.PositionID });
				while (cursor1.moveToNext()) {
					float checkNum = cursor.getFloat(cursor
							.getColumnIndex("CheckNum"));
					sums += checkNum;
				}
				cursor1.close();
				staPositionSummary.Sums = sums;
				staPositionSummaryList.add(staPositionSummary);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return staPositionSummaryList;
	}

	// 编号查询明细
	public List<StaCheckNODetail> GetStaCheckNODetail(String strShopID,
			String strCheckID) {
		List<StaCheckNODetail> staCheckNODetailList = new ArrayList<StaCheckNODetail>();
		Cursor cursor = null;
		String strSql = "select ID,PositionID,GdBar,CheckNum from OutStockCheckDetail where ShopID=? and CheckID=? order by PositionID,GdBar";
		db.beginTransaction();
		try {
			cursor = db
					.rawQuery(strSql, new String[] { strShopID, strCheckID });
			while (cursor.moveToNext()) {
				StaCheckNODetail staCheckNODetail = new StaCheckNODetail();
				staCheckNODetail.PostionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				staCheckNODetail.GdBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));
				staCheckNODetail.ID = cursor
						.getInt(cursor.getColumnIndex("ID"));
				staCheckNODetail.Sums = cursor.getFloat(cursor
						.getColumnIndex("CheckNum"));
				staCheckNODetailList.add(staCheckNODetail);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return staCheckNODetailList;
	}

	public List<CheckListEntity> GetStaCheckPositionDetail(String strShopID,
			String strCheckID, String strPositionID) {
		List<CheckListEntity> staCheckPositionDetailList = new ArrayList<CheckListEntity>();
		Cursor cursor = null;
		String strSql = "select GdBar from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=? order by CheckTime desc";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID });
			while (cursor.moveToNext()) {
				CheckListEntity checkListEntity = new CheckListEntity();
				checkListEntity.strBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));
				int sums = 0;
				Cursor cursor1 = db
						.rawQuery(
								"select CheckNum from OutStockCheckDetail where GdBar=? and ShopID=? and CheckID=? and PositionID=?",
								new String[] { checkListEntity.strBar,
										strShopID, strCheckID, strPositionID });
				while (cursor1.moveToNext()) {
					float checkNum = cursor1.getFloat(cursor1
							.getColumnIndex("CheckNum"));
					sums += checkNum;
				}
				cursor1.close();
				checkListEntity.dNum = sums;
				staCheckPositionDetailList.add(checkListEntity);
			}
			cursor.close();
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();

		}
		return staCheckPositionDetailList;
	}

	public List<StaCheckDetail> GetStaCheckDetail(String strShopID,
			String strCheckID, String strGdBar) {
		List<StaCheckDetail> staCheckDetailList = new ArrayList<StaCheckDetail>();
		Cursor cursor = null;
		String strSql = "Select ID,PositionID from OutStockCheckDetail where ShopID=? and CheckID=? and GdBar=? order by PositionID";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strGdBar });
			while (cursor.moveToNext()) {
				StaCheckDetail staCheckDetail = new StaCheckDetail();
				staCheckDetail.ID = cursor.getInt(cursor.getColumnIndex("ID"));
				staCheckDetail.PositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				float sums = 0;
				Cursor cursor1 = db
						.rawQuery(
								"select CheckNum from OutStockCheckDetail where PositionID=?",
								new String[] { staCheckDetail.PositionID });
				while (cursor1.moveToNext()) {
					float checkNum = cursor1.getInt(cursor1
							.getColumnIndex("CheckNum"));
					sums += checkNum;
				}
				cursor1.close();
				staCheckDetail.CheckNum = sums;
				staCheckDetailList.add(staCheckDetail);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return staCheckDetailList;
	}

	// /// <summary>
	// /// 是否已经存在该唯一码，用于扫描重复检验。与02的区别是该方法只检测到盘点的编号，而02检测到货位号
	// /// </summary>
	// /// <param name="iCounter"></param>
	// /// <returns></returns>
	public int ExistSpecifyBar01(String strShopID, String strCheckID,
			String strSingleGdBar) {
		int iRet = 0;
		Cursor cursor = null;
		String strSql = "select * from OutStockCheckDetailSingle where ShopID=? And CheckID=? and GdBarSingle=? and CheckNum > 0";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strSingleGdBar });
			if (cursor.moveToFirst()) {
				iRet = 1;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail ExistSpecifyBar Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return iRet;
	}

	public int checkUniqueCode(String strShopID, String strCheckID,
			String strSingleGdBar) {
		int iRet = 0;
		Cursor cursor = null;
		String strSql = null;
		// 内部码
		if (sp.getString(ConfigEntity.InOutCodeKey, ConfigEntity.InOutCode)
				.equals("0")) {
			strSql = "select GdArtNO from GdInfo where GdCode=?";
			// 外部码
		} else if (sp.getString(ConfigEntity.InOutCodeKey,
				ConfigEntity.InOutCode).equals("1")) {
			strSql = "select GdArtNO from GdInfo where GdBarCode=?";
		}

		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strSingleGdBar });
			if (cursor.moveToFirst()) {
				iRet = 1;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail ExistSpecifyBar Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return iRet;
	}

	public int checkUniqueCodeFromCheckDetail(String strShopID,
			String strCheckID, String strSingleGdBar) {
		int iRet = 0;
		Cursor cursor = null;
		String strSql = "select GdArtNO from OutStockCheckDetail where GdBar=? and ShopID = ? and CheckID = ?";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strSingleGdBar,
					strShopID, strCheckID });
			if (cursor.moveToFirst()) {
				iRet = 1;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail ExistSpecifyBar Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return iRet;
	}

	// /// <summary>
	// /// 是否存在有要消减的唯一码记录。用于消减检验时执行该方法用的
	// /// </summary>
	// /// <param name="iCounter"></param>
	// /// <returns></returns>
	public int ExistSpecifyBar02(String strShopID, String strCheckID,
			String strPositionID, String strSingleGdBar) {
		int iRet = 0;
		Cursor cursor = null;
		String strSql = "Select * From OutStockCheckDetailSingle where ShopID=? And CheckID=? and PositionID=? and GdBarSingle=? And CheckNum > 0";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID, strSingleGdBar });
			if (cursor.moveToFirst()) {
				iRet = 1;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail ExistSpecifyBar Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return iRet;
	}

	/**
	 * 
	 * @param dOldNum
	 *            处理该次操作之前的盘点的数量
	 * @param checkDetail
	 * @param uniqueStatus
	 * @param isAddQuantity
	 *            为当为逐行显示,并且要修改数量时
	 * @param choosedQuantity
	 *            为当为逐行显示时,点击要被修改条码的数量
	 * @param isBtn
	 *            区分是点击修改数量按钮，还是跳转修改数量
	 * @return
	 */
	// /// <summary>
	// /// iAddedOrSingle为1表示累加，0为单行显示
	// /// </summary>
	// /// <param name="iOldNum">处理该次操作之前的盘点的数量@param
	// /// <returns></returns>
	// isAddQuantity为当为逐行显示,并且要修改数量时
	// choosedQuantity为当为逐行显示时,点击要被修改条码的数量
	public int SaveCheckDetail(int dOldNum, CheckDetail checkDetail,
			int uniqueStatus, boolean isAddQuantity, int choosedQuantity,
			boolean isBtn) {

		int iRet = 0;
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			if (checkDetail.dCheckNum == 0) {
				strSql = "delete from OutStockCheckDetail where ShopID=? And CheckID=? and PositionID=? And GdBar=?";

				// 为0表示基础资料无此条码时,uniqueStatus这个状态代表扫描的条码是否存在于基础资料中
				if (uniqueStatus == 0) {
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strSingleGdBar });
				} else {
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strGdBar });
				}

			} else if (checkDetail.dCheckNum > 0) {

				String sqlString = "select count(*) from OutStockCheckDetail where ShopID=? And CheckID=? and PositionID=? and GdBar=?";
				Cursor condition = db.rawQuery(sqlString, new String[] {
						checkDetail.strShopID, checkDetail.strCheckID,
						checkDetail.strPositionID, checkDetail.strGdBar });

				while (condition.moveToNext()) {

					if (condition.getInt(condition.getColumnIndex("count(*)")) == 0) {
						String test = "Insert Into OutStockCheckDetail(ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
								+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						db.execSQL(
								test,
								new Object[] { checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar,
										checkDetail.dCheckNum,
										checkDetail.strGdArtNO,
										checkDetail.strGdStyle,
										checkDetail.strGdColorID,
										checkDetail.strGdSizeID,
										checkDetail.strGdName,
										checkDetail.strGdColorName,
										checkDetail.strGdSizeName,
										checkDetail.strProperty1,
										checkDetail.strProperty2,
										checkDetail.dStock,
										checkDetail.dGdPrice,
										checkDetail.strReservedMg,
										Utility.getCurTime() });
					} else if (condition.getInt(condition
							.getColumnIndex("count(*)")) > 0) {
						// 当为逐行显示的时候
						if (sp.getString(ConfigEntity.ScanningShowModeKey,
								ConfigEntity.ScanningShowMode).equals("1")
								&& isBtn) {

							if (isAddQuantity == true) {
								checkDetail.dCheckNum = dOldNum
										+ checkDetail.dCheckNum
										- choosedQuantity;
							}

							// 当为累加显示并且为输入数量的时候
						}
						if (!isBtn) {
							// 不屏蔽：修改数量累加
							if (sp.getString(ConfigEntity.ScanningShowModeKey,
									ConfigEntity.ScanningShowMode).equals("0")
									&& sp.getString(ConfigEntity.IsPutInNumKey,
											ConfigEntity.IsPutInNumKey).equals(
											"0")) {
								checkDetail.dCheckNum = dOldNum
										+ checkDetail.dCheckNum;
							} else if (sp.getString(
									ConfigEntity.ScanningShowModeKey,
									ConfigEntity.ScanningShowMode).equals("1")
									&& sp.getString(ConfigEntity.IsPutInNumKey,
											ConfigEntity.IsPutInNumKey).equals(
											"0")) {
								checkDetail.dCheckNum = dOldNum
										+ checkDetail.dCheckNum;
							}
						}
						strSql = "Update OutStockCheckDetail Set CheckNum=? , CheckTime = ? where ShopID=? And CheckID=? and PositionID=? And GdBar=?";
						db.execSQL(strSql,
								new Object[] { checkDetail.dCheckNum,Utility.getCurTime(),
										checkDetail.strShopID,
										checkDetail.strCheckID,
										checkDetail.strPositionID,
										checkDetail.strGdBar });
					}

				}

			}
			String strSqlSingle = "";
			int iUpdateNum = checkDetail.dCheckNum - dOldNum;

			if (checkDetail.dCheckNum == 0) {
				strSql = "delete from OutStockCheckDetail where ShopID=? And CheckID=? and PositionID=?  And GdBar=?";
				dbLocal.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strGdBar });

			} else {
				strSql = "Insert Into OutStockCheckDetail(ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
						+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice, ReservedMg,CheckTime) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				dbLocal.execSQL(strSql, new Object[] { checkDetail.strShopID,
						checkDetail.strCheckID, checkDetail.strPositionID,
						checkDetail.strGdBar, iUpdateNum,
						checkDetail.strGdArtNO, checkDetail.strGdStyle,
						checkDetail.strGdColorID, checkDetail.strGdSizeID,
						checkDetail.strGdName, checkDetail.strGdColorName,
						checkDetail.strGdSizeName, checkDetail.strProperty1,
						checkDetail.strProperty2, checkDetail.dStock,
						checkDetail.dGdPrice, checkDetail.strReservedMg,
						Utility.getCurTime() });
			}

			// #region 保存唯一条码表，如果没有启用唯一条码选项，该动作不执行
			if (sp.getString(ConfigEntity.UsingBarCodeKey,
					ConfigEntity.UsingBarCode).equals("1")) {

				// 如果本次数量小于原数量,是唯一码的时候则需要进行删除操作，删除某一个唯一条码才是
				if (checkDetail.dCheckNum < dOldNum) {
					String strSql1 = "select ID from OutStockCheckDetailSingle where ShopID=? And CheckID=? and PositionID=? And GdBarSingle=?";
					cursor = db.rawQuery(strSql1, new String[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID,
							checkDetail.strSingleGdBar });
					while (cursor.moveToNext()) {
						strSqlSingle = "delete from OutStockCheckDetailSingle where ID =?";
						int id = cursor.getInt(cursor.getColumnIndex("ID"));
						db.execSQL(strSqlSingle, new Object[] { id });
					}
					cursor = dbLocal.rawQuery(strSql1, new String[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID,
							checkDetail.strSingleGdBar });
					while (cursor.moveToNext()) {
						strSqlSingle = "delete from OutStockCheckDetailSingle where ID =?";
						int id = cursor.getInt(cursor.getColumnIndex("ID"));
						dbLocal.execSQL(strSqlSingle, new Object[] { id });
					}
				} else {
					strSqlSingle = "Insert Into OutStockCheckDetailSingle(ShopID,checkID,PositionID,GdBar,CheckNum,"
							+ "GdArtNO,GdStyle,GdColorID,GdSizeID,GdName,GdColorName,GdSizeName,Property1,"
							+ "Property2,GdStock,GdPrice,ReservedMg,GdBarSingle) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					db.execSQL(strSqlSingle, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID, checkDetail.strGdBar,
							iUpdateNum, checkDetail.strGdArtNO,
							checkDetail.strGdStyle, checkDetail.strGdColorID,
							checkDetail.strGdSizeID, checkDetail.strGdName,
							checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg,
							checkDetail.strSingleGdBar });
					dbLocal.execSQL(strSqlSingle, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID, checkDetail.strGdBar,
							iUpdateNum, checkDetail.strGdArtNO,
							checkDetail.strGdStyle, checkDetail.strGdColorID,
							checkDetail.strGdSizeID, checkDetail.strGdName,
							checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg,
							checkDetail.strSingleGdBar });
				}
			}
			// #endregion
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
			iRet = 1;
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail SqlCheckAndInsert Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
			if (cursor != null)
				cursor.close();
		}
		return iRet;
	}

	@SuppressWarnings("unused")
	public List<CheckDetail> GetSpecifiedCounterRecords(int iRows,
			String strShopID, String strCheckID, String strPositionID) {
		List<CheckDetail> list = new ArrayList<CheckDetail>();
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			if (false)// GlobalRunConfig.GetInstance().iScanDisplayWay == 0)
			{
				strSql = "Select a.GdBar,a.CheckNum,a.GdArtNO,"
						+ "a.GdStyle,a.GdName,a.GdColorID,a.GdColorName,a.GdSizeID,a.GdSizeName,"
						+ "a.Property1,a.Property2,a.GdStock,a.GdPrice,a.ID From OutStockCheckDetailSingle a where a.ShopID=?"
						+ "and a.CheckID=? and a.PositionID=? order by ID desc Limit"
						+ iRows;
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					CheckDetail checkDetail = new CheckDetail();
					checkDetail.strGdBar = cursor.getString(cursor
							.getColumnIndex("GdBar"));
					checkDetail.dCheckNum = cursor.getInt(cursor
							.getColumnIndex("CheckNum"));
					checkDetail.strGdArtNO = cursor.getString(cursor
							.getColumnIndex("GdArtNO"));
					checkDetail.strGdStyle = cursor.getString(cursor
							.getColumnIndex("GdStyle"));
					checkDetail.strGdName = cursor.getString(cursor
							.getColumnIndex("GdName"));
					checkDetail.strGdColorID = cursor.getString(cursor
							.getColumnIndex("GdColorID"));
					checkDetail.strGdColorName = cursor.getString(cursor
							.getColumnIndex("GdColorName"));
					checkDetail.strGdSizeID = cursor.getString(cursor
							.getColumnIndex("GdSizeID"));
					checkDetail.strGdSizeName = cursor.getString(cursor
							.getColumnIndex("GdSizeName"));
					checkDetail.strProperty1 = cursor.getString(cursor
							.getColumnIndex("Property1"));
					checkDetail.strProperty2 = cursor.getString(cursor
							.getColumnIndex("Property2"));
					checkDetail.dStock = cursor.getInt(cursor
							.getColumnIndex("GdStock"));
					checkDetail.dGdPrice = cursor.getFloat(cursor
							.getColumnIndex("GdPrice"));
					list.add(checkDetail);
				}
				db.setTransactionSuccessful();
			} else {
				if (sp.getString(ConfigEntity.InOutCodeKey,
						ConfigEntity.InOutCode).equals("1")) // 内部码
				{
					strSql = "Select  a.GdBar,a.CheckNum,a.GdArtNO,a.GdStyle,a.GdName,a.GdColorID,"
							+ "a.GdColorName,a.GdSizeID,a.GdSizeName,a.Property1,a.Property2,a.GdStock,a.GdPrice,a.ID "
							+ "From OutStockCheckDetail a where a.ShopID=?"
							+ "And a.CheckID=? and a.PositionID=? order by ID desc Limit "
							+ iRows;
				} else if (sp.getString(ConfigEntity.InOutCodeKey,
						ConfigEntity.InOutCode).equals("0")) // 外部码
				{
					strSql = "Select a.GdBar,a.CheckNum,a.GdArtNO,a.GdStyle,a.GdName,a.GdColorID,"
							+ "a.GdColorName,a.GdSizeID,a.GdSizeName,a.Property1,a.Property2,a.GdStock,a.GdPrice,a.ID "
							+ "From OutStockCheckDetail a where a.ShopID=? "
							+ "And a.CheckID=? and a.PositionID=? order by ID desc Limint "
							+ iRows;
				}
				cursor = db.rawQuery(strSql, new String[] { strShopID,
						strCheckID, strPositionID });
				while (cursor.moveToNext()) {
					CheckDetail checkDetail = new CheckDetail();
					checkDetail.strGdBar = cursor.getString(cursor
							.getColumnIndex("GdBar"));
					checkDetail.dCheckNum = cursor.getInt(cursor
							.getColumnIndex("CheckNum"));
					checkDetail.strGdArtNO = cursor.getString(cursor
							.getColumnIndex("GdArtNO"));
					checkDetail.strGdStyle = cursor.getString(cursor
							.getColumnIndex("GdStyle"));
					checkDetail.strGdName = cursor.getString(cursor
							.getColumnIndex("GdName"));
					checkDetail.strGdColorID = cursor.getString(cursor
							.getColumnIndex("GdColorID"));
					checkDetail.strGdColorName = cursor.getString(cursor
							.getColumnIndex("GdColorName"));
					checkDetail.strGdSizeID = cursor.getString(cursor
							.getColumnIndex("GdSizeID"));
					checkDetail.strGdSizeName = cursor.getString(cursor
							.getColumnIndex("GdSizeName"));
					checkDetail.strProperty1 = cursor.getString(cursor
							.getColumnIndex("Property1"));
					checkDetail.strProperty2 = cursor.getString(cursor
							.getColumnIndex("Property2"));
					checkDetail.dStock = cursor.getInt(cursor
							.getColumnIndex("GdStock"));
					checkDetail.dGdPrice = cursor.getFloat(cursor
							.getColumnIndex("GdPrice"));
					list.add(checkDetail);

					LogUtil.i(checkDetail.toString());
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetSpecifiedQtyRecords Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			if (null != cursor) {
				cursor.close();
			}
		}
		return list;
	}

	// 根据当前门店号，编号，货位号获取总共的数量
	public float GetSpecifiedCheckSum(String strShopID, String strCheckID) {
		float dCheckSum = 0;
		Cursor cursor = null;
		String strSql = "Select CheckNum From OutStockCheckDetail where ShopID=? And CheckID=?";
		db.beginTransaction();
		try {
			cursor = db
					.rawQuery(strSql, new String[] { strShopID, strCheckID });
			while (cursor.moveToNext()) {
				float checkNum = cursor.getFloat(cursor
						.getColumnIndex("CheckNum"));
				dCheckSum += checkNum;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetSpecifiedSum Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return dCheckSum;
	}

	public int GetSpecifiedPositionSum(String strShopID, String strCheckID,
			String strPositionID) {
		int dCheckSum = 0;
		Cursor cursor = null;
		String strSql = "Select CheckNum From OutStockCheckDetail where ShopID=? And CheckID=? and PositionID=?";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID });
			while (cursor.moveToNext()) {
				float checkNum = cursor.getFloat(cursor
						.getColumnIndex("CheckNum"));
				dCheckSum += checkNum;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetSpecifiedSum Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return dCheckSum;
	}

	// 根据编号获取数量
	public int GetSpecifiedCheckIDSum(String strShopID, String strCheckID,
			String strPositionID) {
		int dCheckSum = 0;
		// dPositionSum = 0;
		Cursor cursor = null;
		String strSql = "Select sum(CheckNum) From OutStockCheckDetail where ShopID=? And CheckID=?";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql,
					new String[] { strShopID, strCheckID, });
			if (cursor.moveToFirst()) {
				dCheckSum = cursor.getInt(cursor
						.getColumnIndex("sum(CheckNum)"));
			}
			cursor.close();
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetSpecifiedSum Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();

		}
		return dCheckSum;
	}

	// 根据货位获取数量
	public int GetSpecifiedPositionIdSum(String strShopID, String strCheckID,
			String strPositionID) {
		int dCheckSum = 0;
		// dPositionSum = 0;
		Cursor cursor = null;
		String strSql = "Select sum(CheckNum) From OutStockCheckDetail where ShopID=? And PositionId=? And CheckId = ?";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID,
					strPositionID, strCheckID });
			if (cursor.moveToFirst()) {
				dCheckSum = cursor.getInt(cursor
						.getColumnIndex("sum(CheckNum)"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetSpecifiedSum Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return dCheckSum;
	}

	/**
	 * 通过ShopID，CheckID，PostionID和GdBar获取货物数量
	 * 
	 * @param strShopID
	 * @param strCheckID
	 * @param strPositionID
	 * @param strGdBar
	 * @return
	 */
	public int GetGoodsQty(String strShopID, String strCheckID,
			String strPositionID, String strGdBar) {
		Cursor cursor1 = null;
		int dQty = 0;
		String strSql = "Select CheckNum from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=? and GdBar = ?";
		db.beginTransaction();
		try {
			cursor1 = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID, strGdBar });
			while (cursor1.moveToNext()) {
				float checkNum = cursor1.getFloat(cursor1
						.getColumnIndex("CheckNum"));
				dQty += checkNum;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetGoodsQty Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			if (cursor1 != null) {
				cursor1.close();
			}

		}
		return dQty;
	}

	/**
	 * 通过ShopID，CheckID，PostionID和GdBar获取CheckDetail详情
	 * 
	 * @param strShopID
	 * @param strCheckID
	 * @param strPositionID
	 * @param strGdBar
	 * @return
	 */
	public CheckDetail GetSpeCheckRecord(String strShopID, String strCheckID,
			String strPositionID, String strGdBar) {
		CheckDetail checkDetail = new CheckDetail();
		String strSql = "select GdBar,CheckNum,GdArtNO,GdStyle,GdName,GdColorID,"
				+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice "
				+ "From OutStockCheckDetail where ShopID=?"
				+ "And CheckID=? and PositionID=? And GdBar=?";
		Cursor cursro = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID, strGdBar });
			if (cursor.moveToFirst()) {
				checkDetail.strShopID = strShopID;
				checkDetail.strCheckID = strCheckID;
				checkDetail.strPositionID = strPositionID;
				checkDetail.strGdBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));
				checkDetail.dCheckNum = cursor.getInt(cursor
						.getColumnIndex("CheckNum"));
				checkDetail.strGdArtNO = cursor.getString(cursor
						.getColumnIndex("GdArtNO"));
				checkDetail.strGdStyle = cursor.getString(cursor
						.getColumnIndex("GdStyle"));
				checkDetail.strGdName = cursor.getString(cursor
						.getColumnIndex("GdName"));
				checkDetail.strGdColorID = cursor.getString(cursor
						.getColumnIndex("GdColorID"));
				checkDetail.strGdColorName = cursor.getString(cursor
						.getColumnIndex("GdColorName"));
				checkDetail.strGdSizeID = cursor.getString(cursor
						.getColumnIndex("GdSizeID"));
				checkDetail.strGdSizeName = cursor.getString(cursor
						.getColumnIndex("GdSizeName"));
				checkDetail.strProperty1 = cursor.getString(cursor
						.getColumnIndex("Property1"));
				checkDetail.strProperty2 = cursor.getString(cursor
						.getColumnIndex("Property2"));
				checkDetail.dStock = cursor.getInt(cursor
						.getColumnIndex("GdStock"));
				checkDetail.dGdPrice = cursor.getFloat(cursor
						.getColumnIndex("GdPrice"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug(
					"OutStockCheckDetail GetSpeCheckRecord Method Error:",
					ex.getMessage());
		} finally {
			db.endTransaction();

			cursor.close();
		}
		return checkDetail;
	}

	/**
	 * 插入数据CheckMain
	 * 
	 * @param strShopID
	 * @param strCheckID
	 * @param strPositionID
	 * @return
	 */
	public int SaveCheckMain(String strShopID, String strCheckID,
			String strPositionID) {
		strPositionID = strPositionID == null ? "" : strPositionID;// wyz
		int iRet = 0;
		String strSql = "insert into OutStockCheckMain(ShopID,CheckID,PositionID,CheckTime) values(?,?,?,?)";
		db.beginTransaction();
		try {
			db.execSQL(strSql, new Object[] { strShopID, strCheckID,
					strPositionID, Utility.getCurTime() });
			db.setTransactionSuccessful();
			iRet = 1;
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckMain SqlInsert Error", ex.getMessage());
		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	public MainSummaryInfo GetSummaryQty(String strShopID) {
		MainSummaryInfo summaryInfo = new MainSummaryInfo();
		String strSql = "select a.CheckID,a.PositionID,"
				+ " from OutStockCheckMain a Left Join OutStockCheckDetail b on a.ShopID=b.ShopID and a.CheckID=b.CheckID and a.PositionID=b.PositionID "
				+ "where a.ShopID=? Group by a.CheckID,a.PositionID Order by a.CheckID,a.PositionID";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID });
			while (cursor.moveToNext()) {
				summaryInfo.CheckID = cursor.getString(cursor
						.getColumnIndex("CheckID"));
				summaryInfo.PositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				Cursor cursor1 = db.rawQuery(
						"select CheckNum from OutStockCheckDetail where CheckID=? and "
								+ "PostionID=?", new String[] {
								summaryInfo.CheckID, summaryInfo.PositionID });
				float Qty = 0;
				while (cursor1.moveToNext()) {
					float checkNum = cursor1.getFloat(cursor1
							.getColumnIndex("CheckNum"));
					Qty += checkNum;
				}
				cursor1.close();
				summaryInfo.Qty = Qty;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("CheckMain GetSummaryQty Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return summaryInfo;
	}

	/**
	 * 获取当前编号，货位号，门店号存在的数量
	 * 
	 * @param strCheckID
	 * @param strPositionID
	 * @param strShopID
	 * @return
	 */
	public List<MainSummaryInfo> GetSummaryList(String strCheckID,
			String strPositionID, String strShopID) {
		List<MainSummaryInfo> summaryInfoList = new ArrayList<MainSummaryInfo>();
		String strSql = "select a.CheckID,a.PositionID"
				+ " from OutStockCheckMain a Left Join OutStockCheckDetail b on a.ShopID=b.ShopID and a.CheckID=b.CheckID and a.PositionID=b.PositionID "
				+ "where a.ShopID=? and a.CheckID=? and a.PositionID=? Group by a.CheckID,a.PositionID Order by a.CheckID,a.PositionID";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID });
			while (cursor.moveToNext()) {
				MainSummaryInfo summaryInfo = new MainSummaryInfo();
				summaryInfo.CheckID = cursor.getString(cursor
						.getColumnIndex("CheckID"));
				summaryInfo.PositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));

				summaryInfoList.add(summaryInfo);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("CheckMain GetSummaryList Error", ex.getMessage());
		} finally {
			db.endTransaction();
			if (cursor != null)
				cursor.close();
		}
		return summaryInfoList;
	}

	/**
	 * 通过门店编号查询编号，货位，数量
	 * 
	 * @param strShopID
	 * @return
	 */
	public List<MainSummaryInfo> GetSummaryList(String strShopID) {
		List<MainSummaryInfo> summaryInfoList = new ArrayList<MainSummaryInfo>();
		String strSql = "select a.CheckID,a.PositionID"
				+ " from OutStockCheckMain a Left Join OutStockCheckDetail b on a.ShopID=b.ShopID and a.CheckID=b.CheckID and a.PositionID=b.PositionID "
				+ "where a.ShopID=? Group by a.CheckID,a.PositionID Order by a.CheckID,a.PositionID";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID });
			while (cursor.moveToNext()) {
				MainSummaryInfo summaryInfo = new MainSummaryInfo();
				summaryInfo.CheckID = cursor.getString(cursor
						.getColumnIndex("CheckID"));
				summaryInfo.PositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				Cursor cursor1 = db.rawQuery(
						"select CheckNum from OutStockCheckDetail where ShopID=? and CheckID=? and "
								+ "PositionID=?", new String[] { strShopID,
								summaryInfo.CheckID, summaryInfo.PositionID });
				float Qty = 0;
				while (cursor1.moveToNext()) {
					float checkNum = cursor1.getFloat(cursor1
							.getColumnIndex("CheckNum"));
					Qty += checkNum;
				}
				cursor1.close();
				summaryInfo.Qty = Qty;
				summaryInfoList.add(summaryInfo);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckMain GetSummaryList Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			if (cursor != null)
				cursor.close();
		}
		return summaryInfoList;
	}

	// / <summary>
	// / 没有用了，用的下面一个GetMainDataReader，
	// / 这里存在一个问题，为什么用这个方法不能，你懂的！
	// / </summary>
	// / <param name="strType"></param>
	// / <param name="dt"></param>
	// / <returns></returns>
	public List<CheckMain> GetMainData(String strType, String strShopID) {
		List<CheckMain> list = new ArrayList<CheckMain>();
		String strSql = "";
		Cursor cursor = null;
		if (strType.equals("0")) {
			strSql = "select distinct CheckID from OutStockCheckMain where ShopID=?";
		} else {
			strSql = "select distinct CheckID,PositionID from OutStockCheckMain where ShopID=?";
		}
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID });
			while (cursor.moveToNext()) {
				CheckMain checkMain = new CheckMain();
				checkMain.strCheckID = cursor.getString(cursor
						.getColumnIndex("CheckID"));
				checkMain.strPositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				list.add(checkMain);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("CheckMain GetMainData Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return list;
	}

	public List<CheckMain> GetMainDataReader(String strType, String strShopID) {
		List<CheckMain> list = new ArrayList<CheckMain>();
		String strSql = "";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			if (strType.equals("0")) {
				strSql = "select distinct CheckID from OutStockCheckMain where ShopID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID });
				while (cursor.moveToNext()) {
					CheckMain checkMain = new CheckMain();
					checkMain.strCheckID = cursor.getString(cursor
							.getColumnIndex("CheckID"));
					list.add(checkMain);
				}
			} else {
				strSql = "select distinct CheckID,PositionID from OutStockCheckMain where ShopID=?";
				cursor = db.rawQuery(strSql, new String[] { strShopID });
				while (cursor.moveToNext()) {
					CheckMain checkMain = new CheckMain();
					checkMain.strCheckID = cursor.getString(cursor
							.getColumnIndex("CheckID"));
					checkMain.strPositionID = cursor.getString(cursor
							.getColumnIndex("PositionID"));
					list.add(checkMain);
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("CheckMain GetMainData Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return list;
	}

	public List<String> GetSpeCheckIDPositionID(String strShopID,
			String strCheckID) {
		List<String> listPositionID = new ArrayList<String>();
		String strSql = "select distinct PositionID from OutStockCheckMain where ShopID=? and CheckID=?'";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db
					.rawQuery(strSql, new String[] { strShopID, strCheckID });
			while (cursor.moveToNext()) {
				String strPositionID = cursor.getString(cursor
						.getColumnIndex("PositionID"));
				listPositionID.add(strPositionID);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("CheckMain GetSpeCheckIDPositionID Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return listPositionID;
	}

	// / <summary>
	// / useless, 2012.6.15
	// / </summary>
	// / <param name="strType"></param>
	// / <returns></returns>
	public int DeleteMainData(String strType, String strShopID,
			String strCheckID, String strPositionID) {
		int iRet = 1;
		String strSql = "";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			if (strType.equals("0")) {
				strSql = "delete from OutStockCheckMain where ShopID=? and CheckID=?";
				db.execSQL(strSql, new Object[] { strShopID, strCheckID });
				dbLocal.execSQL(strSql, new Object[] { strShopID, strCheckID });
			} else {
				strSql = "delete from OutStockCheckMain where ShopID=? and CheckID=? and PositionID=?";
				db.execSQL(strSql, new Object[] { strShopID, strCheckID,
						strPositionID });
				dbLocal.execSQL(strSql, new Object[] { strShopID, strCheckID,
						strPositionID });
			}
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("CheckMain DeleteMainData Error", ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	// / <summary>
	// / useless ,2012.6.15
	// / </summary>
	// / <returns></returns>
	public int DeleteAllMainData(String strShopID) {
		int iRet = 1;
		String strSql = "delete from OutStockCheckMain where ShopID=?";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL(strSql, new Object[] { strShopID });
			dbLocal.execSQL(strSql, new Object[] { strShopID });
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("CheckMain DeleteAllMainData Error", ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	public int InsertLog(String strLogin, String strContent) {
		int iRet = 1;
		String strSql = "insert into CheckAndDelLog(Login,Content,DateTime) values(?,?,?)";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL(strSql,
					new Object[] { strLogin, strContent, Utility.getCurTime() });
			dbLocal.execSQL(strSql, new Object[] { strLogin, strContent,
					Utility.getCurTime() });
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("CheckAndDelLog InsertLog Error:", ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	public int DeleteAllLog() {
		int iRet = 1;
		String strSql = "delete from CheckAndDelLog";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL(strSql);
			dbLocal.execSQL(strSql);
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("CheckAndDelLog DeleteAllLog Error:", ex.getMessage());
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	// 保存商品资料
	public void saveGdInfo(GdInfo gdInfo) {
		String strSql = "insert into GdInfo(GdBarcode,GdCode,GdArtNO,GdStyle,GdName,GdColorID,"
				+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.beginTransaction();
		try {
			db.execSQL(strSql, new Object[] { gdInfo.strGdBarcode,
					gdInfo.strGdCode, gdInfo.strGdArtNO, gdInfo.strGdStyle,
					gdInfo.strGdName, gdInfo.strGdColorID,
					gdInfo.strGdColorName, gdInfo.strGdSizeID,
					gdInfo.strGdSizeName, gdInfo.strProperty1,
					gdInfo.strProperty2, gdInfo.dGdStock, gdInfo.dGdPrice });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug("saveGdInfo Error:", e.toString());
		} finally {
			db.endTransaction();
		}
	}

	public void saveGdInfoList(List<GdInfo> gdInfoList) {
		String strSql = "insert into GdInfo(GdBarcode,GdCode,GdArtNO,GdStyle,GdName,GdColorID,"
				+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		db.beginTransaction();
		try {
			for (int i = 0; i < gdInfoList.size(); i++) {
				GdInfo gdInfo = gdInfoList.get(i);
				db.execSQL(strSql, new Object[] { gdInfo.strGdBarcode,
						gdInfo.strGdCode, gdInfo.strGdArtNO, gdInfo.strGdStyle,
						gdInfo.strGdName, gdInfo.strGdColorID,
						gdInfo.strGdColorName, gdInfo.strGdSizeID,
						gdInfo.strGdSizeName, gdInfo.strProperty1,
						gdInfo.strProperty2, gdInfo.dGdStock, gdInfo.dGdPrice });
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			Utility.deBug("saveGdInfo Error:", e.toString());
		} finally {
			db.endTransaction();
		}
	}

	// 通过内部码获取商品信息
	public GdInfo GetDataByCode(String strGdCode) {
		String strSql = "Select GdBarCode,GdCode,GdArtNO,GdStyle,GdName,GdColorID,"
				+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice from GdInfo where GdCode=?";
		Cursor cursor = null;
		GdInfo gdInfo = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strGdCode });
			if (cursor.moveToFirst()) {

				gdInfo = new GdInfo();
				gdInfo.strGdBarcode = cursor.getString(cursor
						.getColumnIndex("GdBarCode"));
				gdInfo.strGdCode = cursor.getString(cursor
						.getColumnIndex("GdCode"));
				gdInfo.strGdArtNO = cursor.getString(cursor
						.getColumnIndex("GdArtNO"));
				gdInfo.strGdStyle = cursor.getString(cursor
						.getColumnIndex("GdStyle"));
				gdInfo.strGdName = cursor.getString(cursor
						.getColumnIndex("GdName"));
				gdInfo.strGdColorID = cursor.getString(cursor
						.getColumnIndex("GdColorID"));
				gdInfo.strGdColorName = cursor.getString(cursor
						.getColumnIndex("GdColorName"));
				gdInfo.strGdSizeID = cursor.getString(cursor
						.getColumnIndex("GdSizeID"));
				gdInfo.strGdSizeName = cursor.getString(cursor
						.getColumnIndex("GdSizeName"));
				gdInfo.strProperty1 = cursor.getString(cursor
						.getColumnIndex("Property1"));
				gdInfo.strProperty2 = cursor.getString(cursor
						.getColumnIndex("Property2"));
				gdInfo.dGdStock = cursor.getInt(cursor
						.getColumnIndex("GdStock"));
				gdInfo.dGdPrice = cursor.getFloat(cursor
						.getColumnIndex("GdPrice"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("GdInfo GetDateByBar Method Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return gdInfo;
	}

	// 通过外部码获取商品信息
	public GdInfo GetDataByBar(String strGdBarcode) {
		String strSql = "select GdCode,GdArtNO,GdStyle,GdName,GdColorID,GdColorName,GdSizeID,GdSizeName,"
				+ "Property1,Property2,GdStock,GdPrice from GdInfo where GdBarcode=?";
		Cursor cursor = null;
		GdInfo gdInfo = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strGdBarcode });
			if (cursor.moveToFirst()) {
				gdInfo = new GdInfo();
				gdInfo.strGdBarcode = strGdBarcode;
				gdInfo.strGdCode = cursor.getString(cursor
						.getColumnIndex("GdCode"));
				gdInfo.strGdArtNO = cursor.getString(cursor
						.getColumnIndex("GdArtNO"));
				gdInfo.strGdStyle = cursor.getString(cursor
						.getColumnIndex("GdStyle"));
				gdInfo.strGdName = cursor.getString(cursor
						.getColumnIndex("GdName"));
				gdInfo.strGdColorID = cursor.getString(cursor
						.getColumnIndex("GdColorID"));
				gdInfo.strGdColorName = cursor.getString(cursor
						.getColumnIndex("GdColorName"));
				gdInfo.strGdSizeID = cursor.getString(cursor
						.getColumnIndex("GdSizeID"));
				gdInfo.strGdSizeName = cursor.getString(cursor
						.getColumnIndex("GdSizeName"));
				gdInfo.strProperty1 = cursor.getString(cursor
						.getColumnIndex("Property1"));
				gdInfo.strProperty2 = cursor.getString(cursor
						.getColumnIndex("Property2"));
				gdInfo.dGdStock = cursor.getInt(cursor
						.getColumnIndex("GdStock"));
				gdInfo.dGdPrice = cursor.getFloat(cursor
						.getColumnIndex("GdPrice"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("GdInfo GetDateByBar Method Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return gdInfo;
	}

	// / <summary>
	// / drop然后重新创建gdinfo表
	// / </summary>
	// / <returns></returns>
	public int DropAndRecreate(String iBarInOrOut) {
		try {
			String strIndexBarcode = "Index1GdInfo0000111987";
			String strIndexcode = "Index2GdInfo1111222876";
			String sql = "Drop table GdInfo";
			db.execSQL(sql);
			sql = "create table GdInfo (" + "GdBarCode nvarchar(48),"
					+ "GdCode nvarchar(48)," + "GdArtNO nvarchar(36),"
					+ "GdStyle nvarchar(36)," + "GdName nvarchar(128),"
					+ "GdColorID nvarchar(32)," + "GdColorName nvarchar(32),"
					+ "GdSizeID nvarchar(32)," + "GdSizeName nvarchar(32),"
					+ "Property1 nvarchar(64)," + "Property2 nvarchar(64),"
					+ "GdStock numeric (12,3) DEFAULT 0,"
					+ "GdPrice numeric (12,2) DEFAULT 0,"
					+ "IDNO integer primary key ASC AUTOINCREMENT NOT NULL)";

			db.execSQL(sql);
			if (iBarInOrOut.equals("1"))
				sql = " CREATE INDEX Index1GdInfo0000111987 ON GdInfo (GdBarCode);";
			else
				sql = "CREATE INDEX Index1GdInfo0000111987 ON GdInfo (GdBarCode)"; // Unique,去掉唯一索引，原为唯一索引
			db.execSQL(sql);

			if (iBarInOrOut.equals("1"))
				sql = "CREATE INDEX Index2GdInfo1111222876 ON GdInfo ( GdCode )"; // Unique，去掉唯一索引，原为唯一索引
			else
				sql = " CREATE INDEX Index2GdInfo1111222876 ON GdInfo ( GdCode )";
			db.execSQL(sql);
		} catch (Exception ex) {
			Utility.deBug("Gdinfo DropAndRecreate Error: ", ex.getMessage());
			return 0;
		}
		return 1;
	}

	// / <summary>
	// /
	// / </summary>
	// / <param name="iValue"></param>
	// / <returns></returns>
	public BarSplitLenSet GetBarSplitLenSetInUsage() {
		BarSplitLenSet barSplitLenSet = new BarSplitLenSet();
		String strSql = "Select Item,ArtNO,Style,Color,Size,ArtNOSerial,StyleSerial,ColorSerial,SizeSerial,"
				+ "Separator1,Separator2 From OutStockBarSplitLenSet where InUsage=1";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				barSplitLenSet.iItem = cursor.getInt(cursor
						.getColumnIndex("Item"));
				if (barSplitLenSet.iItem == 1) // 款，色，码长度拆分
				{
					barSplitLenSet.iStyle = cursor.getInt(cursor
							.getColumnIndex("Style"));
					barSplitLenSet.iColor = cursor.getInt(cursor
							.getColumnIndex("Color"));
					barSplitLenSet.iSize = cursor.getInt(cursor
							.getColumnIndex("Size"));
					barSplitLenSet.iStyleSerial = cursor.getInt(cursor
							.getColumnIndex("StyleSerial"));
					barSplitLenSet.iColorSerial = cursor.getInt(cursor
							.getColumnIndex("ColorSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
				} else if (barSplitLenSet.iItem == 2) // 货号，码长度拆分
				{
					barSplitLenSet.iArtNO = cursor.getInt(cursor
							.getColumnIndex("ArtNO"));
					barSplitLenSet.iSize = cursor.getInt(cursor
							.getColumnIndex("Size"));
					barSplitLenSet.iArtNOSerial = cursor.getInt(cursor
							.getColumnIndex("ArtNOSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
				} else if (barSplitLenSet.iItem == 3) // 款，色，码分隔符分隔
				{
					barSplitLenSet.iStyleSerial = cursor.getInt(cursor
							.getColumnIndex("StyleSerial"));
					barSplitLenSet.iColorSerial = cursor.getInt(cursor
							.getColumnIndex("ColorSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
					barSplitLenSet.strSeparator1 = cursor.getString(cursor
							.getColumnIndex("Separator1"));
					barSplitLenSet.strSeparator2 = cursor.getString(cursor
							.getColumnIndex("Separator2"));
				} else if (barSplitLenSet.iItem == 4) // 货号，码分隔符分隔
				{
					barSplitLenSet.iArtNOSerial = cursor.getInt(cursor
							.getColumnIndex("ArtNOSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
					barSplitLenSet.strSeparator1 = cursor.getString(cursor
							.getColumnIndex("Separator1"));
				} else if (barSplitLenSet.iItem == 5) // 不处理相关拆分
				{
					;
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("BarSplitSet GetBarSplitLenSetInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return barSplitLenSet;
	}

	// / <summary>
	// / 获取指定项的配置数据，指定项由speEnt的Item指定
	// / </summary>
	// / <param name="speEnt"></param>
	// / <returns></returns>
	public BarSplitLenSet GetSpecifiedItem(String iItem) {
		BarSplitLenSet barSplitLenSet = new BarSplitLenSet();
		String strSql = "Select Item,ArtNO,Style,Color,Size,ArtNOSerial,StyleSerial,ColorSerial,SizeSerial,Separator1,"
				+ "Separator2 From OutStockBarSplitLenSet where Item=?";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { iItem });
			while (cursor.moveToNext()) {
				barSplitLenSet.iItem = cursor.getInt(cursor
						.getColumnIndex("Item"));
				if (barSplitLenSet.iItem == 1) // 款，色，码长度拆分
				{
					barSplitLenSet.iStyle = cursor.getInt(cursor
							.getColumnIndex("Style"));
					barSplitLenSet.iColor = cursor.getInt(cursor
							.getColumnIndex("Color"));
					barSplitLenSet.iSize = cursor.getInt(cursor
							.getColumnIndex("Size"));
					barSplitLenSet.iStyleSerial = cursor.getInt(cursor
							.getColumnIndex("StyleSerial"));
					barSplitLenSet.iColorSerial = cursor.getInt(cursor
							.getColumnIndex("ColorSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
				} else if (barSplitLenSet.iItem == 2) // 货号，码长度拆分
				{
					barSplitLenSet.iArtNO = cursor.getInt(cursor
							.getColumnIndex("ArtNO"));
					barSplitLenSet.iSize = cursor.getInt(cursor
							.getColumnIndex("Size"));
					barSplitLenSet.iArtNOSerial = cursor.getInt(cursor
							.getColumnIndex("ArtNOSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
				} else if (barSplitLenSet.iItem == 3) // 款，色，码分隔符分隔
				{
					barSplitLenSet.iStyleSerial = cursor.getInt(cursor
							.getColumnIndex("StyleSerial"));
					barSplitLenSet.iColorSerial = cursor.getInt(cursor
							.getColumnIndex("ColorSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
					barSplitLenSet.strSeparator1 = cursor.getString(cursor
							.getColumnIndex("Separator1"));
					barSplitLenSet.strSeparator2 = cursor.getString(cursor
							.getColumnIndex("Separator2"));
				} else if (barSplitLenSet.iItem == 4) // 货号，码分隔符分隔
				{
					barSplitLenSet.iArtNOSerial = cursor.getInt(cursor
							.getColumnIndex("ArtNOSerial"));
					barSplitLenSet.iSizeSerial = cursor.getInt(cursor
							.getColumnIndex("SizeSerial"));
					barSplitLenSet.strSeparator1 = cursor.getString(cursor
							.getColumnIndex("Separator1"));
				} else if (barSplitLenSet.iItem == 5) // 不处理相关拆分
				{
					;
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("BarSplitSet GetSpecifiedItem Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return barSplitLenSet;
	}

	// / <summary>
	// /
	// / </summary>
	// / <returns></returns>
	public int UpdateBarSplitLenSetInUsage(BarSplitLenSet barSplitLenSet) {
		int iRet = 1;
		String strSql = "Update OutStockBarSplitLenSet set InUsage = 0";
		db.beginTransaction();
		try {
			db.execSQL(strSql);
			strSql = "Update OutStockBarSplitLenSet "
					+ "set InUsage = 1,ArtNO=?,Style=?,Color=?,Size=?,ArtNOSerial=?,StyleSerial=?,"
					+ "ColorSerial=?,SizeSerial=?,Separator1=?,Separator2=? where Item=?";
			db.execSQL(strSql, new Object[] { barSplitLenSet.iArtNO,
					barSplitLenSet.iStyle, barSplitLenSet.iColor,
					barSplitLenSet.iSize, barSplitLenSet.iArtNOSerial,
					barSplitLenSet.iStyleSerial, barSplitLenSet.iColorSerial,
					barSplitLenSet.iSizeSerial, barSplitLenSet.strSeparator1,
					barSplitLenSet.strSeparator2, barSplitLenSet.iItem });
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("BarSplitSet UpdateInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	public User GetSpePassword(String iUserLevel) {
		User user = new User();
		String strSql = "Select Password,PwdPrompt from Users where UserLevel=?";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { iUserLevel });
			while (cursor.moveToNext()) {
				byte[] bTemp = new byte[360];
				byte[] bDest = new byte[360];
				String strTemp = "";
				strTemp = cursor.getString(cursor.getColumnIndex("Password"));
				bTemp = strTemp.getBytes();
				user.strPassword = new String(bDest, 0, bDest.length).trim();
				user.strPwdPrompt = cursor.getString(cursor
						.getColumnIndex("PwdPrompt"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("Users GetSpePassword Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return user;
	}

	// / <summary>
	// /
	// / </summary>
	// / <returns></returns>
	public int UpdateSpePassword(User user) {
		int iRet = 0;
		try {
			byte[] strSour = new byte[128];
			byte[] strEncry = new byte[224];
			strSour = user.strPassword.getBytes();// Encoding.US_ASCII.GetBytes(user.strPassword);
			// SupoinInven.DataBusiness.ValidEncrypt.EncryptAll(strSour,
			// strEncry, 224);
			user.strPassword = new String(strEncry, 0, strEncry.length).trim();

			String strSql = "Update Users Set Password=?,PwdPrompt=? where UserLevel=?";
			db.execSQL(strSql, new Object[] { user.strPassword,
					user.strPwdPrompt, user.iUserLevel });
		} catch (Exception ex) {
			Utility.deBug("Users UpdateSpePassword Error", ex.getMessage());
		}
		return iRet;
	}

	// / <summary>
	// / 获取当前的使用的识别方式
	// / </summary>
	// / <param name="iValue"></param>
	// / <returns></returns>
	// / <summary>
	public VerifyWay GetBarVerifyWayInUsageByIndex(int iValue, int index) {
		VerifyWay verifyWay = new VerifyWay();
		String strSql = "Select Item from VerifyWay where InUsage=1";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				int item = cursor.getInt(cursor.getColumnIndex("Item"));
				if ((item / iValue) == 1)
					verifyWay.iItem = iValue + (index);
				verifyWay.strValue = cursor.getString(cursor
						.getColumnIndex("Value"));
				verifyWay.strDescription = cursor.getString(cursor
						.getColumnIndex("Description"));
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("VerifyWay GetBarVerifyWayInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return verifyWay;
	}

	// 插入初始值
	public void insertVerifyWayInfo() {
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery("select * from VerifyWay", null);
			if (cursor.getCount() == 0) {
				db = helper.getWritableDatabase();

				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','11','Y','Judge Validation of the Bar')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','10','N','No Judge Bar')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','12','Y','Judge,if is Invalid Just Prompt')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','21','Y','Judge Validation of Single Bar')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','20','N','No Judge Single Bar')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','31','30','Overlapped Quantity')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','30','30','No Overlapped')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','41','Y','Input Quantity')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','40','N','No Input Quantity')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','50','0','UnProcess')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','51','3','Cut Head Length')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','52','0','Cut Tail Length')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','53','13','Reserve Head Length')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','54','0','Reserve Tail Length')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('1','61','Y','Internal Bar')");
				db.execSQL("insert into VerifyWay (InUsage,Item,Value,Description) values('0','60','N','External Bar')");
				db.setTransactionSuccessful();

				// db.endTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
		}
		// this.close();
	}

	// / 获取当前的使用的识别方式
	// / </summary>
	// / <param name="iValue"></param>
	// iValue 是10，20，30这种数，被除数，num为结果
	// / <returns></returns>
	public VerifyWay GetBarVerifyWayInUsage(int iValue, int num) {
		VerifyWay verifyWay = new VerifyWay();
		String strSql = "Select * from VerifyWay where InUsage=1";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				int item = cursor.getInt(cursor.getColumnIndex("Item"));
				if (item / iValue == num) {
					verifyWay.iInUsage = cursor.getInt(cursor
							.getColumnIndex("InUsage"));
					verifyWay.iItem = cursor.getInt(cursor
							.getColumnIndex("Item")) % (iValue * num);
					verifyWay.strValue = cursor.getString(cursor
							.getColumnIndex("Value"));
					verifyWay.strDescription = cursor.getString(cursor
							.getColumnIndex("Description"));
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("VerifyWay GetBarVerifyWayInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return verifyWay;
	}

	// / <summary>
	// /更新当前Item的值
	// / </summary>
	// / <returns></returns>
	// / <summary>
	public void UpdateCurVerifyWay(VerifyWay verifyWay) {
		String strSql = "select * from VerifyWay where Item=?";
		Cursor cursor = null;
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql,
					new String[] { String.valueOf(verifyWay.iItem) });
			while (cursor.moveToNext()) {
				strSql = "Update VerifyWay set Value = ? and InUsage = ?  where Item =?";
				db.execSQL(strSql, new Object[] { verifyWay.strValue,
						verifyWay.iInUsage, verifyWay.iItem });
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			cursor.close();
		}
	}

	// /更新表中所有值
	// / </summary>
	// / <returns></returns>
	public int UpdateVerifyWayInUsage(int func, List<VerifyWay> listVeri) {
		int iRet = 1;
		String strSql = "";
		db.beginTransaction();
		try {
			if (func == 0 || func == 1) {
				strSql = "Update VerifyWay set InUsage = 0";
				db.execSQL(strSql);

				String strCon = "";
				for (int i = 0; i < listVeri.size(); i++) {
					VerifyWay verifyWay = listVeri.get(i);
					strCon += String.valueOf(verifyWay.iItem);
					strCon += ",";
				}
				if (strCon.length() > 0)
					strCon = strCon.substring(0, strCon.length() - 1);

				strSql = "Update VerifyWay set InUsage = 1 where Item=?";
				db.execSQL(strSql, new Object[] { strCon });
				VerifyWay scanRow = null;
				for (int i = 0; i < listVeri.size(); i++) {
					VerifyWay en = listVeri.get(i);
					if ((en.iItem / 30) == 1) {
						scanRow = en;
					}
				}
				if (scanRow != null) {
					strSql = "Update VerifyWay set Value = ? where Item =?";
					db.execSQL(strSql, new Object[] { scanRow.strValue,
							scanRow.iItem });
				}
				VerifyWay curRos = null;
				for (int i = 0; i < listVeri.size(); i++) {
					VerifyWay en = listVeri.get(i);
					if ((en.iItem / 50) == 1) {
						curRos = en;
					}
				}
				if (curRos != null) {
					strSql = "Update VerifyWay set value = ? where Item =?";
					db.execSQL(strSql, new Object[] { curRos.strValue,
							curRos.iItem });
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("VerifyWay UpdateInUsage Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
		}
		return iRet;
	}

	// / <summary>
	// / 获取验证的各个项目，其中，
	// / 10，11表示是否验证条码的有效性,
	// / 20，21表示是否是唯一码，
	// / 30，31表示扫描界面的显示方式和显示数量,
	// / 40，41表示扫描时是否输入数量,
	// / 50，51表示是否截断条码，截取的值表示截取位数,
	// / 60，61表示是内部编码还是处部编码
	// / </summary>
	// / <param name="list"></param>
	// / <returns></returns>
	public List<VerifyWay> GetVerifyWay() {
		String strSql = "Select InUsage,Item,Value,Description from VerifyWay where InUsage = 1 Order by Item";
		Cursor cursor = null;
		List<VerifyWay> listVeri = new ArrayList<VerifyWay>();
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				VerifyWay verifyWay = new VerifyWay();
				verifyWay.iInUsage = cursor.getInt(cursor
						.getColumnIndex("InUsage"));
				verifyWay.iItem = cursor.getInt(cursor.getColumnIndex("Item"));
				verifyWay.strValue = cursor.getString(cursor
						.getColumnIndex("Value"));
				verifyWay.strDescription = cursor.getString(cursor
						.getColumnIndex("Description"));
				listVeri.add(verifyWay);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("GetVerifyWay Method Error", ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return listVeri;
	}

	public void clearGdInfoData() {
		db = helper.getWritableDatabase();
		dbLocal = helperLocal.getWritableDatabase();
		String strSql = "update sqlite_sequence set seq=0 where name='GdInfo'";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL("delete from GdInfo");
			dbLocal.execSQL("delete from GdInfo");
			// db.execSQL("select * from sqlite_sequence"); //第二条的作用是查询当前的id值
			db.execSQL("update sqlite_sequence set seq=0 where name='GdInfo'");
			dbLocal.execSQL("update sqlite_sequence set seq=0 where name='GdInfo'");
			// db.execSQL(strSql);
			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}

	}

	public void clearUserData() {
		db = helper.getWritableDatabase();
		db.execSQL("delete from User");
	}

	public void close() {
		if (db != null) {
			db.close();
		}
	}

	public static class ScanOrderInfo {
		public int ID;// 序号
		public String GdBar;// 商品条码
		public float CheckNum;// 数量

		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getGdBar() {
			return GdBar;
		}

		public void setGdBar(String gdBar) {
			GdBar = gdBar;
		}

		public float getCheckNum() {
			return CheckNum;
		}

		public void setCheckNum(float checkNum) {
			CheckNum = checkNum;
		}

	}

	public static class StaPositionSummary {
		public String PositionID;
		public float Sums;

		public String getPositionID() {
			return PositionID;
		}

		public void setPositionID(String positionID) {
			PositionID = positionID;
		}

		public float getSums() {
			return Sums;
		}

		public void setSums(float sums) {
			Sums = sums;
		}
	}

	public static class StaCheckNODetail {
		public int ID;
		public String PostionID;
		public String GdBar;
		public float Sums;

		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getPostionID() {
			return PostionID;
		}

		public void setPostionID(String postionID) {
			PostionID = postionID;
		}

		public String getGdBar() {
			return GdBar;
		}

		public void setGdBar(String gdBar) {
			GdBar = gdBar;
		}

		public float getSums() {
			return Sums;
		}

		public void setSums(float sums) {
			Sums = sums;
		}

	}

	public static class StaCheckPositionDetail {
		public int ID;
		public String GdBar;
		public int CheckNum;

		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getGdBar() {
			return GdBar;
		}

		public void setGdBar(String gdBar) {
			GdBar = gdBar;
		}

		public int getCheckNum() {
			return CheckNum;
		}

		public void setCheckNum(int checkNum) {
			CheckNum = checkNum;
		}

	}

	public static class StaCheckDetail {
		public int ID;
		public String PositionID;
		public float CheckNum;

		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getPositionID() {
			return PositionID;
		}

		public void setPositionID(String positionID) {
			PositionID = positionID;
		}

		public float getCheckNum() {
			return CheckNum;
		}

		public void setCheckNum(float checkNum) {
			CheckNum = checkNum;
		}
	}

	/** 压力测试 **/
	public boolean InsertCheckData(List<CheckDetail> checkData) {
		boolean isok = false;
		String sql = "insert into OutStockCheckDetail (ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		SQLiteStatement stat = db.compileStatement(sql);
		db.beginTransaction();
		try {
			for (CheckDetail var : checkData) {
				stat.bindString(1, var.strShopID);
				stat.bindString(2, var.strCheckID);
				stat.bindString(3, var.strPositionID);
				stat.bindString(4, var.strGdBar);
				stat.bindDouble(5, 1);
				stat.bindString(6, var.strGdArtNO);
				stat.bindString(7, var.strGdStyle);
				stat.bindString(8, var.strGdSizeID);
				stat.bindString(9, var.strGdName);
				stat.bindString(10, var.strGdColorName);
				stat.bindString(11, var.strGdSizeName);
				stat.bindString(12, var.strProperty1);
				stat.bindString(13, var.strProperty2);
				stat.bindDouble(14, var.dStock);
				stat.bindDouble(15, var.dGdPrice);
				stat.bindString(16, var.strReservedMg);
				stat.bindString(17, Utility.getCurTime());
				stat.executeInsert();
			}
			db.setTransactionSuccessful();
			isok = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isok;
	}

	/**
	 * -----------------------------------RFID----------------------------------
	 **/
	/** 获取基础信息，且判断是否存在不在基础资料中的商品 */
	public List<GdInfo> getCheckInfoList(List<String> rfidList, String inOutCode) {
		String strSql;
		if (inOutCode.equals("0")) {// 内部码
			strSql = "Select GdBarCode,GdCode,GdArtNO,GdStyle,GdName,GdColorID,"
					+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice from GdInfo where GdCode = ?";
		} else {// 外部码
			strSql = "Select GdBarCode,GdCode,GdArtNO,GdStyle,GdName,GdColorID,"
					+ "GdColorName,GdSizeID,GdSizeName,Property1,Property2,GdStock,GdPrice from GdInfo where GdBarCode = ?";
		}
		Cursor cursor = null;
		ArrayList<GdInfo> lisGdInfos = new ArrayList<GdInfo>();
		GdInfo gdInfo = null;
		db.beginTransaction();
		try {
			for (String code : rfidList) {
				gdInfo = null;
				if (!TextUtils.isEmpty(code)) {
					cursor = db.rawQuery(strSql, new String[] { code.trim() });
					if (cursor.moveToFirst()) {
						gdInfo = new GdInfo();
						gdInfo.strGdBarcode = cursor.getString(cursor
								.getColumnIndex("GdBarCode"));
						gdInfo.strGdCode = cursor.getString(cursor
								.getColumnIndex("GdCode"));
						gdInfo.strGdArtNO = cursor.getString(cursor
								.getColumnIndex("GdArtNO"));
						gdInfo.strGdStyle = cursor.getString(cursor
								.getColumnIndex("GdStyle"));
						gdInfo.strGdName = cursor.getString(cursor
								.getColumnIndex("GdName"));
						gdInfo.strGdColorID = cursor.getString(cursor
								.getColumnIndex("GdColorID"));
						gdInfo.strGdColorName = cursor.getString(cursor
								.getColumnIndex("GdColorName"));
						gdInfo.strGdSizeID = cursor.getString(cursor
								.getColumnIndex("GdSizeID"));
						gdInfo.strGdSizeName = cursor.getString(cursor
								.getColumnIndex("GdSizeName"));
						gdInfo.strProperty1 = cursor.getString(cursor
								.getColumnIndex("Property1"));
						gdInfo.strProperty2 = cursor.getString(cursor
								.getColumnIndex("Property2"));
						gdInfo.dGdStock = cursor.getInt(cursor
								.getColumnIndex("GdStock"));
						gdInfo.dGdPrice = cursor.getFloat(cursor
								.getColumnIndex("GdPrice"));
					}
				}
				if (gdInfo == null) {
					return null;
				} else {
					lisGdInfos.add(gdInfo);
				}

			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Log.e("CheckDetail SqlInsert Method Error", ex.getMessage() + "--");
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return lisGdInfos;
	}
	public List<String> getGdInfoBarcodeList(String inOutCode) {
		String strSql;
		if(inOutCode.equals("0")){//内部码
			strSql = "Select GdCode from GdInfo";
		}else{//外部码
			strSql = "Select GdBarCode from GdInfo";
		}
		Cursor cursor = null;
		ArrayList<String> lisGdInfos = new ArrayList<String>();
		GdInfo gdInfo = null;
		db.beginTransaction();
		try {
			String code = null;
			cursor = db.rawQuery(strSql, new String[] {});
			while (cursor.moveToNext()) {
				if(inOutCode.equals("0")){
					code = cursor.getString(cursor
							.getColumnIndex("GdCode"));
				}else{
					code = cursor.getString(cursor
							.getColumnIndex("GdBarCode"));
				}
				lisGdInfos.add(code);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
		} finally {
			db.endTransaction();
			if(cursor!=null){
				cursor.close();
			}
		}
		return lisGdInfos;
	}
	/** 验证---插入缓存表 */
	public synchronized int InsertCheckDetailTempRFIDVer(List<GdInfo> rfidList,
			String strShopID, String strCheckID, String strPositionID,
			String inOutCode) {
		int iRet = 0;
		String strSql = "insert into OutStockTemTable (ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String deleteSqlString = "delete from OutStockTemTable";

		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL(deleteSqlString);
			dbLocal.execSQL(deleteSqlString);
			CheckDetail checkDetail = new CheckDetail();
			for (GdInfo gdBar : rfidList) {
				String code;
				if (inOutCode.equals("0")) {// 内部码
					code = gdBar.strGdCode;
				} else {// 外部码
					code = gdBar.strGdBarcode;
				}
				if (!TextUtils.isEmpty(code)) {
					checkDetail.setStrGdBar(code.trim());
					checkDetail.setStrShopID(strShopID);
					checkDetail.setStrCheckID(strCheckID);
					checkDetail.setStrPositionID(strPositionID);
					checkDetail.setdCheckNum(1);
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strGdBar, checkDetail.dCheckNum,
							gdBar.strGdArtNO, gdBar.strGdStyle,
							gdBar.strGdColorID, gdBar.strGdSizeID,
							gdBar.strGdName, gdBar.strGdColorName,
							gdBar.strGdSizeName, gdBar.strProperty1,
							gdBar.strProperty2, gdBar.dGdStock, gdBar.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
					dbLocal.execSQL(strSql, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID, checkDetail.strGdBar,
							checkDetail.dCheckNum, gdBar.strGdArtNO,
							gdBar.strGdStyle, gdBar.strGdColorID,
							gdBar.strGdSizeID, gdBar.strGdName,
							gdBar.strGdColorName, gdBar.strGdSizeName,
							gdBar.strProperty1, gdBar.strProperty2,
							gdBar.dGdStock, gdBar.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
				}

			}

			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
			iRet = 1;
		} catch (Throwable ex) {
			iRet = 0;
			if (ex != null) {
				Log.e("CheckDetail SqlInsert Method Error", ex.getMessage()
						+ "--");
			}

		} finally {
			db.endTransaction();
			dbLocal.endTransaction();

		}
		return iRet;
	}

	/** 不验证---插入缓存表 */
	public synchronized int InsertCheckDetailTempRFID(List<String> rfidList,
			String strShopID, String strCheckID, String strPositionID) {
		int iRet = 0;
		String strSql = "insert into OutStockTemTable (ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String deleteSqlString = "delete from OutStockTemTable";

		db.beginTransaction();
		dbLocal.beginTransaction();
		try {
			db.execSQL(deleteSqlString);
			dbLocal.execSQL(deleteSqlString);
			CheckDetail checkDetail = new CheckDetail();
			for (String gdBar : rfidList) {
				if (!TextUtils.isEmpty(gdBar)) {
					checkDetail.setStrGdBar(gdBar.trim());
					checkDetail.setStrShopID(strShopID);
					checkDetail.setStrCheckID(strCheckID);
					checkDetail.setStrPositionID(strPositionID);
					checkDetail.setdCheckNum(1);
					db.execSQL(strSql, new Object[] { checkDetail.strShopID,
							checkDetail.strCheckID, checkDetail.strPositionID,
							checkDetail.strGdBar, checkDetail.dCheckNum,
							checkDetail.strGdArtNO, checkDetail.strGdStyle,
							checkDetail.strGdColorID, checkDetail.strGdSizeID,
							checkDetail.strGdName, checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
					dbLocal.execSQL(strSql, new Object[] {
							checkDetail.strShopID, checkDetail.strCheckID,
							checkDetail.strPositionID, checkDetail.strGdBar,
							checkDetail.dCheckNum, checkDetail.strGdArtNO,
							checkDetail.strGdStyle, checkDetail.strGdColorID,
							checkDetail.strGdSizeID, checkDetail.strGdName,
							checkDetail.strGdColorName,
							checkDetail.strGdSizeName,
							checkDetail.strProperty1, checkDetail.strProperty2,
							checkDetail.dStock, checkDetail.dGdPrice,
							checkDetail.strReservedMg, Utility.getCurTime() });
				}

			}

			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
			iRet = 1;
		} catch (Throwable ex) {
			iRet = 0;
			if (ex != null) {
				Log.e("CheckDetail SqlInsert Method Error", ex.getMessage()
						+ "--");
			}

		} finally {
			db.endTransaction();
			dbLocal.endTransaction();

		}
		return iRet;
	}

	public synchronized int InsertCheckDetailRFID() {
		int iRet = 0;
		String strSql = "insert into OutStockCheckDetail(ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime)"
				+ " select ShopID,CheckID,PositionID,GdBar,CheckNum,GdArtNO,GdStyle,GdColorID,GdSizeID,"
				+ "GdName,GdColorName,GdSizeName,Property1,Property2,GdStock,GdPrice,ReservedMg,CheckTime from OutStockTemTable"
				+ " t1 where not exists(select 1 from OutStockCheckDetail t2 where t1.GdBar=t2.GdBar and t1.ShopID=t2.ShopID and t1.CheckID=t2.CheckID and t1.PositionID=t2.PositionID)";
		db.beginTransaction();
		dbLocal.beginTransaction();
		try {

			db.execSQL(strSql, new Object[] {});
			dbLocal.execSQL(strSql, new Object[] {});

			db.setTransactionSuccessful();
			dbLocal.setTransactionSuccessful();
			iRet = 1;
		} catch (Exception ex) {
			iRet = 0;
			Utility.deBug("OutStockCheckDetail SqlInsert Method Error",
					ex.getMessage());

		} finally {
			db.endTransaction();
			dbLocal.endTransaction();
		}
		return iRet;
	}

	public List<CheckListEntity> GetRFIDCheckPositionDetail(String strShopID,
			String strCheckID, String strPositionID) {
		List<CheckListEntity> staCheckPositionDetailList = new ArrayList<CheckListEntity>();
		Cursor cursor = null;
		String strSql = "select GdBar from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=? order by CheckTime desc";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID });
			while (cursor.moveToNext()) {
				CheckListEntity checkListEntity = new CheckListEntity();
				checkListEntity.strBar = cursor.getString(cursor
						.getColumnIndex("GdBar"));
				checkListEntity.dNum = 1;
				staCheckPositionDetailList.add(checkListEntity);
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return staCheckPositionDetailList;
	}

	public boolean checkRFIDCodeByCheckDetail(String strBar, String strShopID,
			String strCheckID, String strPositionID) {
		boolean isExit = false;
		Cursor cursor = null;
		String strSql = "select GdBar from OutStockCheckDetail where ShopID=? and CheckID=? and PositionID=? and GdBar=?";
		db.beginTransaction();
		try {
			cursor = db.rawQuery(strSql, new String[] { strShopID, strCheckID,
					strPositionID, strBar });
			while (cursor.moveToNext()) {
				isExit = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception ex) {
			Utility.deBug("OutStockCheckDetail GetRecordsScanOrder Method Error",
					ex.getMessage());
		} finally {
			db.endTransaction();
			cursor.close();
		}
		return isExit;
	}
}
