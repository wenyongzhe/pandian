package com.supoin.commoninventory.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class StockDataOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION = 3;
	private static StockDataOpenHelper mInstance = null;
	private String CREATE_STOCKDETAIL = "create table StockDetail ("
			+ "ID integer primary key ASC AUTOINCREMENT NOT NULL ,"
			+ "RackPlace nvarchar(30) ," + "Barcode nvarchar(30), "
			+ "SKU nvarchar(60)," + "RecvQty nvarchar(10),"
			+ "ScanTime nvarchar(30), " + "NeedQty nvarchar(10), "
			+ "KeeperId nvarchar(10)," + "GoodsName nvarchar(30),"
			+ "StockPlace nvarchar(60))";

	private String CREATE_TEMP_STOCKDETAIL = "alter table StockDetail rename to temp_StockDetail";
	// private String INSERT_DATA =
	// "insert into StockDetail() select *,'1' from temp_StockDetail";
	private String INSERT_DATA = "insert into StockDetail(RackPlace,Barcode,SKU,RecvQty,ScanTime,NeedQty,"
			+ "KeeperId,GoodsName,StockPlace) values(?,?,?,?,?,?,?,?,?)";
	// private String INSERT_DATA =
	// "insert into StockDetail(RackPlace,Barcode,SKU,RecvQty,ScanTime,NeedQty,"
	// +
	// "KeeperId,GoodsName,StockPlace) select (RackPlace,Brocade,SKU,RecvQty,ScanTime,NeedQty,"
	// + "KeeperId,GoodsName,StockPlace) from temp_StockDetail";
	private String DROP_STOCKDETAIL = "drop table temp_StockDetail";

	public synchronized static StockDataOpenHelper getInstance(
			Context context) {
		if (mInstance == null) {
			mInstance = new StockDataOpenHelper(context);
		}
		return mInstance;
	};

	public StockDataOpenHelper(Context context) {
		this(context, SQLDataSqlite.dbName, VERSION);
	}

	public StockDataOpenHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public StockDataOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table BarSplitLenSet ("
				+ "Item integer(4) primary key NOT NULL,"
				+ "InUsage integer(4), " + "ArtNO integer(4),"
				+ "ArtNOSerial integer(4), " + "Style integer(4),"
				+ "StyleSerial integer(4), " + "Color integer(4),"
				+ "ColorSerial integer(4), " + "Size integer(4),"
				+ "SizeSerial integer(4), " + "Separator1 nvarchar(8),"
				+ "Separator2 nvarchar(8))");

		db.execSQL("create table BoxDetail ("
				+ "ID integer(4) primary key NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL, "
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48),"
				+ "BoxNO nvarchar(16) NOT NULL, "
				+ "GdBar nvarchar(64) NOT NULL," + "Qty integer(9),"
				+ "GdArtNO nvarchar(36)," + "GdStyle nvarchar(36),"
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32),"
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64),"
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32),"
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64),"
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");

		db.execSQL("create table CheckDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table CheckDetailSingle ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9),"
				+ "GdBarSingle nvarchar(48) NOT NULL)");

		db.execSQL("create table CheckAndDelLog ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "Login nvarchar(50), " + "Content nvarchar(200),"
				+ "DateTime nvarchar(30))");

		db.execSQL("create table CheckMain ("
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48)  NOT NULL, "
				+ "Remark nvarchar(32)," + "CheckTime nvarchar(30),"
				+ "ReservedMg nvarchar(64),"
				+ "primary key(ShopID, CheckID,PositionID))");
		
		db.execSQL("create table ExportSetInfo (" + "ShopID integer(4),"
				+ "CheckID integer(4)," + "PositionID integer(4), "
				+ "Bar integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "Qty integer(8)," + "ColumnNum integer(4), "
				+ "ListSeparator nvarchar(20)," + "Head integer(4), "
				+ "ScanDate integer(4))");

		db.execSQL("create table GdInfo ("
				+ "IDNO integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "GdBarCode nvarchar(64)," + "GdCode nvarchar(64), "
				+ "GdArtNO nvarchar(36), " + "GdName nvarchar(128),"
				+ "GdStyle nvarchar(36), " + "GdColorID nvarchar(32),"
				+ "GdSizeID nvarchar(32), " + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table ImportSetInfo (" + "BarCode integer(4),"
				+ "Code integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "ColumnNum integer(4), " + "ListSeparator nvarchar(20))");

		db.execSQL("create table LengthSet ("
				+ "Item integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "InUsage integer(4), " + "LenMin integer(4),"
				+ "LenMax integer(4), " + "Content nvarcar(128),"
				+ "Description nvarchar(24) NOT NULL)");

		db.execSQL("create table Users ("
				+ "UserNO nvarchar(36) primary key NOT NULL,"
				+ "UserName nvarchar(36)," + "Password nvarchar(224),"
				+ "PwdPrompt nvarchar(64),"
				+ "UserType nvarchar(16) NOT NULL,"
				+ "Remark nvarchar(36)," + "UserLevel integer(4) NOT NULL)");

		db.execSQL("create table VerifyWay ("
				+ "Item integer primary key  ASC AUTOINCREMENT NOT NULL,"
				+ "InUsage integer(4)," + "Value nvarchar(16) NOT NULL,"
				+ "Description nvarchar(36))");
		
	{//in stock
		db.execSQL("create table CheckInMain ("
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48)  NOT NULL, "
				+ "Remark nvarchar(32)," + "CheckTime nvarchar(30),"
				+ "ReservedMg nvarchar(64),"
				+ "primary key(ShopID, CheckID,PositionID))");
		
		db.execSQL("create table InStockBarSplitLenSet ("
				+ "Item integer(4) primary key NOT NULL,"
				+ "InUsage integer(4), " + "ArtNO integer(4),"
				+ "ArtNOSerial integer(4), " + "Style integer(4),"
				+ "StyleSerial integer(4), " + "Color integer(4),"
				+ "ColorSerial integer(4), " + "Size integer(4),"
				+ "SizeSerial integer(4), " + "Separator1 nvarchar(8),"
				+ "Separator2 nvarchar(8))");

		db.execSQL("create table CheckInDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		db.execSQL("create table InStockBoxDetail ("
				+ "ID integer(4) primary key NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL, "
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48),"
				+ "BoxNO nvarchar(16) NOT NULL, "
				+ "GdBar nvarchar(64) NOT NULL," + "Qty integer(9),"
				+ "GdArtNO nvarchar(36)," + "GdStyle nvarchar(36),"
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32),"
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64),"
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32),"
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64),"
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");

		db.execSQL("create table StockInDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
				
		db.execSQL("create table InStockCheckDetailSingle ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9),"
				+ "GdBarSingle nvarchar(48) NOT NULL)");

		db.execSQL("create table InStockCheckAndDelLog ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "Login nvarchar(50), " + "Content nvarchar(200),"
				+ "DateTime nvarchar(30))");

		db.execSQL("create table StockInMain ("
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48)  NOT NULL, "
				+ "Remark nvarchar(32)," + "CheckTime nvarchar(30),"
				+ "ReservedMg nvarchar(64),"
				+ "primary key(ShopID, CheckID,PositionID))");
		
		db.execSQL("create table InStockCheckDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table InStockExportSetInfo (" + "ShopID integer(4),"
				+ "CheckID integer(4)," + "PositionID integer(4), "
				+ "Bar integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "Qty integer(8)," + "ColumnNum integer(4), "
				+ "ListSeparator nvarchar(20)," + "Head integer(4), "
				+ "ScanDate integer(4))");

		db.execSQL("create table InStockGdInfo ("
				+ "IDNO integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "GdBarCode nvarchar(64)," + "GdCode nvarchar(64), "
				+ "GdArtNO nvarchar(36), " + "GdName nvarchar(128),"
				+ "GdStyle nvarchar(36), " + "GdColorID nvarchar(32),"
				+ "GdSizeID nvarchar(32), " + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table InStockImportSetInfo (" + "BarCode integer(4),"
				+ "Code integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "ColumnNum integer(4), " + "ListSeparator nvarchar(20))");

		db.execSQL("create table InStockLengthSet ("
				+ "Item integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "InUsage integer(4), " + "LenMin integer(4),"
				+ "LenMax integer(4), " + "Content nvarcar(128),"
				+ "Description nvarchar(24) NOT NULL)");
		
		db.execSQL("CREATE INDEX CheckInDetailid_index ON CheckInDetail (ID)");
		db.execSQL("CREATE INDEX CheckInDetailShopID_index ON CheckDetail (ShopID)");
		db.execSQL("CREATE INDEX CheckInDetailCheckID_index ON CheckDetail (CheckID)");
		db.execSQL("CREATE INDEX CheckInDetailPositionID_index ON CheckDetail (PositionID)");
		db.execSQL("CREATE INDEX CheckInDetailGdBar_index ON CheckDetail (GdBar)");
		db.execSQL("CREATE INDEX CheckInDetailCheckNum_index ON CheckDetail (CheckNum)");
		db.execSQL("CREATE INDEX CheckInDetailGdArtNO_index ON CheckDetail (GdArtNO)");
		db.execSQL("CREATE INDEX CheckInDetailGdStyle_index ON CheckDetail (GdStyle)");
		db.execSQL("CREATE INDEX CheckInDetailGdColorID_index ON CheckDetail (GdColorID)");
		db.execSQL("CREATE INDEX CheckInDetailGdSizeID_index ON CheckDetail (GdSizeID)");
		db.execSQL("CREATE INDEX CheckInDetailCheckTime_index ON CheckDetail (CheckTime)");
		db.execSQL("CREATE INDEX CheckInDetailReservedMg_index ON CheckDetail (ReservedMg)");
		db.execSQL("CREATE INDEX CheckInDetailGdName_index ON CheckDetail (GdName)");
		db.execSQL("CREATE INDEX CheckInDetailGdColorName_index ON CheckDetail (GdColorName)");
		db.execSQL("CREATE INDEX CheckInDetailGdSizeName_index ON CheckDetail (GdSizeName)");
		db.execSQL("CREATE INDEX CheckInDetailProperty1_index ON CheckDetail (Property1)");
		db.execSQL("CREATE INDEX CheckInDetailProperty2_index ON CheckDetail (Property2)");
		db.execSQL("CREATE INDEX CheckInDetailGdStock_index ON CheckDetail (GdStock)");
		db.execSQL("CREATE INDEX CheckInDetailGdPrice_index ON CheckDetail (GdPrice)");
		
		db.execSQL("CREATE INDEX CheckInMainShopID_index ON CheckInMain (ShopID)");
		db.execSQL("CREATE INDEX CheckInMainCheckID_index ON CheckInMain (CheckID)");
		db.execSQL("CREATE INDEX CheckInMainPositionID_index ON CheckInMain (PositionID)");
		db.execSQL("CREATE INDEX CheckInMainCheckTime_index ON CheckInMain (CheckTime)");
		
	}//in stock
	
	{//out stock
		db.execSQL("create table OutStockCheckMain ("
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48)  NOT NULL, "
				+ "Remark nvarchar(32)," + "CheckTime nvarchar(30),"
				+ "ReservedMg nvarchar(64),"
				+ "primary key(ShopID, CheckID,PositionID))");
		
		db.execSQL("create table OutStockBarSplitLenSet ("
				+ "Item integer(4) primary key NOT NULL,"
				+ "InUsage integer(4), " + "ArtNO integer(4),"
				+ "ArtNOSerial integer(4), " + "Style integer(4),"
				+ "StyleSerial integer(4), " + "Color integer(4),"
				+ "ColorSerial integer(4), " + "Size integer(4),"
				+ "SizeSerial integer(4), " + "Separator1 nvarchar(8),"
				+ "Separator2 nvarchar(8))");

//		db.execSQL("create table OutStockBoxDetail ("
//				+ "ID integer(4) primary key NOT NULL,"
//				+ "ShopID nvarchar(24) NOT NULL, "
//				+ "CheckID nvarchar(24) NOT NULL,"
//				+ "PositionID nvarchar(24),"
//				+ "BoxNO nvarchar(16) NOT NULL, "
//				+ "GdBar nvarchar(48) NOT NULL," + "Qty integer(9),"
//				+ "GdArtNO nvarchar(36)," + "GdStyle nvarchar(36),"
//				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32),"
//				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64),"
//				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32),"
//				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64),"
//				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
//				+ "GdPrice integer(9))");

		db.execSQL("create table OutStockDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
				
		db.execSQL("create table OutStockCheckDetailSingle ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9),"
				+ "GdBarSingle nvarchar(48) NOT NULL)");

		db.execSQL("create table OutStockCheckAndDelLog ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "Login nvarchar(50), " + "Content nvarchar(200),"
				+ "DateTime nvarchar(30))");

		db.execSQL("create table OutStockMain ("
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48)  NOT NULL, "
				+ "Remark nvarchar(32)," + "CheckTime nvarchar(30),"
				+ "ReservedMg nvarchar(64),"
				+ "primary key(ShopID, CheckID,PositionID))");
		
		db.execSQL("create table OutStockCheckDetail ("
				+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "ShopID nvarchar(24) NOT NULL,"
				+ "CheckID nvarchar(48) NOT NULL,"
				+ "PositionID nvarchar(48), "
				+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
				+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
				+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
				+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
				+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table OutStockExportSetInfo (" + "ShopID integer(4),"
				+ "CheckID integer(4)," + "PositionID integer(4), "
				+ "Bar integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "Qty integer(8)," + "ColumnNum integer(4), "
				+ "ListSeparator nvarchar(20)," + "Head integer(4), "
				+ "ScanDate integer(4))");

		db.execSQL("create table OutStockGdInfo ("
				+ "IDNO integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "GdBarCode nvarchar(64)," + "GdCode nvarchar(64), "
				+ "GdArtNO nvarchar(36), " + "GdName nvarchar(128),"
				+ "GdStyle nvarchar(36), " + "GdColorID nvarchar(32),"
				+ "GdSizeID nvarchar(32), " + "GdColorName nvarchar(32), "
				+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
				+ "Property2 nvarchar(64)," + "GdStock integer(9),"
				+ "GdPrice integer(9))");
		
		
		db.execSQL("create table OutStockImportSetInfo (" + "BarCode integer(4),"
				+ "Code integer(4)," + "ArtNO integer(4), "
				+ "Style integer(4)," + "Name integer(4), "
				+ "ColorID integer(4)," + "ColorName integer(4), "
				+ "Stock integer(4)," + "SizeID integer(4), "
				+ "SizeName integer(4)," + "Big integer(4), "
				+ "Small integer(8)," + "Price integer(4), "
				+ "ColumnNum integer(4), " + "ListSeparator nvarchar(20))");

		db.execSQL("create table OutStockLengthSet ("
				+ "Item integer primary key ASC AUTOINCREMENT NOT NULL,"
				+ "InUsage integer(4), " + "LenMin integer(4),"
				+ "LenMax integer(4), " + "Content nvarcar(128),"
				+ "Description nvarchar(24) NOT NULL)");
		
		db.execSQL("CREATE INDEX OutStockCheckDetail_id_index ON OutStockCheckDetail (ID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_ShopID_index ON CheckDetail (ShopID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_CheckID_index ON CheckDetail (CheckID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_PositionID_index ON CheckDetail (PositionID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdBar_index ON CheckDetail (GdBar)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_CheckNum_index ON CheckDetail (CheckNum)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdArtNO_index ON CheckDetail (GdArtNO)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdStyle_index ON CheckDetail (GdStyle)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdColorID_index ON CheckDetail (GdColorID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdSizeID_index ON CheckDetail (GdSizeID)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_CheckTime_index ON CheckDetail (CheckTime)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_ReservedMg_index ON CheckDetail (ReservedMg)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdName_index ON CheckDetail (GdName)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdColorName_index ON CheckDetail (GdColorName)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdSizeName_index ON CheckDetail (GdSizeName)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_Property1_index ON CheckDetail (Property1)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_Property2_index ON CheckDetail (Property2)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdStock_index ON CheckDetail (GdStock)");
		db.execSQL("CREATE INDEX OutStockCheckDetail_GdPrice_index ON CheckDetail (GdPrice)");
		
		db.execSQL("CREATE INDEX OutStockCheckMain_ShopID_index ON OutStockCheckMain (ShopID)");
		db.execSQL("CREATE INDEX OutStockCheckMain_CheckID_index ON OutStockCheckMain (CheckID)");
		db.execSQL("CREATE INDEX OutStockCheckMain_PositionID_index ON OutStockCheckMain (PositionID)");
		db.execSQL("CREATE INDEX OutStockCheckMain_CheckTime_index ON OutStockCheckMain (CheckTime)");
	}//out stock
		
		db.execSQL("CREATE INDEX id_index ON CheckDetail (ID)");
		db.execSQL("CREATE INDEX ShopID_index ON CheckDetail (ShopID)");
		db.execSQL("CREATE INDEX CheckID_index ON CheckDetail (CheckID)");
		db.execSQL("CREATE INDEX PositionID_index ON CheckDetail (PositionID)");
		db.execSQL("CREATE INDEX GdBar_index ON CheckDetail (GdBar)");
		db.execSQL("CREATE INDEX CheckNum_index ON CheckDetail (CheckNum)");
		db.execSQL("CREATE INDEX GdArtNO_index ON CheckDetail (GdArtNO)");
		db.execSQL("CREATE INDEX GdStyle_index ON CheckDetail (GdStyle)");
		db.execSQL("CREATE INDEX GdColorID_index ON CheckDetail (GdColorID)");
		db.execSQL("CREATE INDEX GdSizeID_index ON CheckDetail (GdSizeID)");
		db.execSQL("CREATE INDEX CheckTime_index ON CheckDetail (CheckTime)");
		db.execSQL("CREATE INDEX ReservedMg_index ON CheckDetail (ReservedMg)");
		db.execSQL("CREATE INDEX GdName_index ON CheckDetail (GdName)");
		db.execSQL("CREATE INDEX GdColorName_index ON CheckDetail (GdColorName)");
		db.execSQL("CREATE INDEX GdSizeName_index ON CheckDetail (GdSizeName)");
		db.execSQL("CREATE INDEX Property1_index ON CheckDetail (Property1)");
		db.execSQL("CREATE INDEX Property2_index ON CheckDetail (Property2)");
		db.execSQL("CREATE INDEX GdStock_index ON CheckDetail (GdStock)");
		db.execSQL("CREATE INDEX GdPrice_index ON CheckDetail (GdPrice)");
		
		db.execSQL("CREATE INDEX GdInfoIDNO_index ON GdInfo (IDNO)");
		db.execSQL("CREATE INDEX GdInfoGdBarCode_index ON GdInfo (GdBarCode)");
		db.execSQL("CREATE INDEX GdInfoGdArtNO_index ON GdInfo (GdArtNO)");
		db.execSQL("CREATE INDEX GdInfoGdName_index ON GdInfo (GdName)");
		db.execSQL("CREATE INDEX GdInfoGdStyle_index ON GdInfo (GdStyle)");
		db.execSQL("CREATE INDEX GdInfoGdColorID_index ON GdInfo (GdColorID)");
		db.execSQL("CREATE INDEX GdInfoGdSizeID_index ON GdInfo (GdSizeID)");
		db.execSQL("CREATE INDEX GdInfoGdColorName_index ON GdInfo (GdColorName)");
		db.execSQL("CREATE INDEX GdInfoGdSizeName_index ON GdInfo (GdSizeName)");
		db.execSQL("CREATE INDEX GdInfoProperty1_index ON GdInfo (Property1)");
		db.execSQL("CREATE INDEX GdInfoProperty2_index ON GdInfo (Property2)");
		db.execSQL("CREATE INDEX GdInfoGdStock_index ON GdInfo (GdStock)");
		db.execSQL("CREATE INDEX GdInfoGdPrice_index ON GdInfo (GdPrice)");
		
		db.execSQL("CREATE INDEX CheckMainShopID_index ON CheckMain (ShopID)");
		db.execSQL("CREATE INDEX CheckMainCheckID_index ON CheckMain (CheckID)");
		db.execSQL("CREATE INDEX CheckMainPositionID_index ON CheckMain (PositionID)");
		db.execSQL("CREATE INDEX CheckMainCheckTime_index ON CheckMain (CheckTime)");
		
		final int FIRST_DATABASE_VERSION = 2;
		onUpgrade(db, FIRST_DATABASE_VERSION, VERSION);
	}
	//TODO 更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
		case 1: // 如果我们覆盖的软件版本是1，则执行此分支。
			upgradeTables(db, "StockDetail",
					getColumnNames(db, "StockDetail"));
			break;
		case 2: // 如果我们覆盖的软件版本是2，则执行此分支。
			//盘点
			db.execSQL("create table if not exists CheckDetailTemTable("
					+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
					+ "ShopID nvarchar(24) NOT NULL,"
					+ "CheckID nvarchar(48) NOT NULL,"
					+ "PositionID nvarchar(48), "
					+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
					+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
					+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
					+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
					+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
					+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
					+ "Property2 nvarchar(64)," + "GdStock integer(9),"
					+ "GdPrice integer(9))");
			db.execSQL("CREATE INDEX if not exists ShopID_index ON CheckDetailTemTable (ShopID)");
			db.execSQL("CREATE INDEX if not exists CheckID_index ON CheckDetailTemTable (CheckID)");
			db.execSQL("CREATE INDEX if not exists PositionID_index ON CheckDetailTemTable (PositionID)");
			db.execSQL("CREATE INDEX if not exists GdBar_index ON CheckDetailTemTable (GdBar)");
			db.execSQL("CREATE INDEX if not exists CheckNum_index ON CheckDetailTemTable (CheckNum)");
			//入库
			db.execSQL("create table if not exists  InStockTemTable("
					+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
					+ "ShopID nvarchar(24) NOT NULL,"
					+ "CheckID nvarchar(48) NOT NULL,"
					+ "PositionID nvarchar(48), "
					+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
					+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
					+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
					+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
					+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
					+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
					+ "Property2 nvarchar(64)," + "GdStock integer(9),"
					+ "GdPrice integer(9))");
			db.execSQL("CREATE INDEX if not exists ShopID_index ON InStockTemTable (ShopID)");
			db.execSQL("CREATE INDEX if not exists CheckID_index ON InStockTemTable (CheckID)");
			db.execSQL("CREATE INDEX if not exists PositionID_index ON InStockTemTable (PositionID)");
			db.execSQL("CREATE INDEX if not exists GdBar_index ON InStockTemTable (GdBar)");
			db.execSQL("CREATE INDEX if not exists CheckNum_index ON InStockTemTable (CheckNum)");
			//出库
			db.execSQL("create table if not exists  OutStockTemTable("
					+ "ID integer primary key ASC AUTOINCREMENT NOT NULL,"
					+ "ShopID nvarchar(24) NOT NULL,"
					+ "CheckID nvarchar(48) NOT NULL,"
					+ "PositionID nvarchar(48), "
					+ "GdBar nvarchar(64) NOT NULL," + "CheckNum integer(9),"
					+ "GdArtNO nvarchar(36), " + "GdStyle nvarchar(36), "
					+ "GdColorID nvarchar(32)," + "GdSizeID nvarchar(32), "
					+ "CheckTime nvarchar(30)," + "ReservedMg nvarchar(64), "
					+ "GdName nvarchar(128)," + "GdColorName nvarchar(32), "
					+ "GdSizeName nvarchar(32)," + "Property1 nvarchar(64), "
					+ "Property2 nvarchar(64)," + "GdStock integer(9),"
					+ "GdPrice integer(9))");
			db.execSQL("CREATE INDEX if not exists ShopID_index ON OutStockTemTable (ShopID)");
			db.execSQL("CREATE INDEX if not exists CheckID_index ON OutStockTemTable (CheckID)");
			db.execSQL("CREATE INDEX if not exists PositionID_index ON OutStockTemTable (PositionID)");
			db.execSQL("CREATE INDEX if not exists GdBar_index ON OutStockTemTable (GdBar)");
			db.execSQL("CREATE INDEX if not exists CheckNum_index ON OutStockTemTable (CheckNum)");
			break;
		}

	}

	/**
	 * Upgrade tables. In this method, the sequence is: <b>
	 * <p>
	 * [1] Rename the specified table as a temporary table.
	 * <p>
	 * [2] Create a new table which name is the specified name.
	 * <p>
	 * [3] Insert data into the new created table, data from the temporary
	 * table.
	 * <p>
	 * [4] Drop the temporary table. </b>
	 * 
	 * @param db
	 *            The database.
	 * @param tableName
	 *            The table name.
	 * @param columns
	 *            The columns range, format is "ColA, ColB, ColC, ... ColN";
	 */
	protected void upgradeTables(SQLiteDatabase db, String tableName,
			String[] columns) {
		try {
			db.beginTransaction();

			// 1, Rename table.
			String tempTableName = "temp_" + tableName;
			String sql = "ALTER TABLE " + tableName + " RENAME TO "
					+ tempTableName;
			db.execSQL(sql);

			// 2, Create table.
			db.execSQL(CREATE_STOCKDETAIL);

			// 3, Load data
			Cursor cursor = db.rawQuery("select * from temp_StockDetail",
					null);
			while (cursor.moveToNext()) {
				db.execSQL(INSERT_DATA,new Object[] {
								cursor.getString(cursor
										.getColumnIndex("RackPlace")),
								cursor.getString(cursor
										.getColumnIndex("Barcode")),
								cursor.getString(cursor
										.getColumnIndex("SKU")),
								cursor.getString(cursor
										.getColumnIndex("RecvQty")),
								cursor.getString(cursor
										.getColumnIndex("ScanTime")),
								cursor.getString(cursor
										.getColumnIndex("NeedQty")),
								cursor.getString(cursor
										.getColumnIndex("KeeperId")),
								cursor.getString(cursor
										.getColumnIndex("GoodsName")),
								cursor.getString(cursor
										.getColumnIndex("StockPlace")) });
				// db.execSQL(INSERT_DATA);
			}
			// db.execSQL(INSERT_DATA);
			// Cursor cursor=db.rawQuery("select * from temp_StockDetail",
			// null);
			// while(cursor.moveToNext()){
			// for(int i=0;i<columns.length;i++){
			// String column=columns[i];
			// sql = "INSERT INTO " + tableName +
			// " (" + columns + ") " +
			// " SELECT " + columns + " FROM " + tempTableName;
			// // sql = "INSERT INTO " + tableName +
			// // " (" + column + ") " +
			// // " SELECT " + column + " FROM " + tempTableName;
			// // sql="insert into "+tableName+"set "+column+"=?"
			// // (WId,WName,AreaPrince,WareHouseType,Prince,City)
			// values(?,?,?,?,?,?)"
			// sql="ID,RackPlace,Barcode,SKU,RecvQty,ScanTime,NeedQty,"
			// + "KeeperId,GoodsName,StockPlace"
			// db.execSQL(sql);
			// }
			// }
			// cursor.close();
			// 4, Drop the temporary table.
			db.execSQL("DROP TABLE IF EXISTS " + tempTableName);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	protected String[] getColumnNames(SQLiteDatabase db, String tableName) {
		String[] columnNames = null;
		Cursor c = null;

		try {
			c = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
			if (null != c) {
				int columnIndex = c.getColumnIndex("name");
				if (-1 == columnIndex) {
					return null;
				}

				int index = 0;
				columnNames = new String[c.getCount()];
				for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
					columnNames[index] = c.getString(columnIndex);
					index++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
		}

		return columnNames;
	}

}
