package com.supoin.commoninventory.entity;

import com.supoin.commoninventory.R;
import com.supoin.commoninventory.util.AlertUtil;

import android.R.bool;

public class ConfigEntity {

	// 远程同步 AgentURL

	public static String AgentURL = "http://sp:9012/";
	public static String AgentURLKey = "AgentURL";

	// 最近登录的用户名

	public static String UserId = "";
	public static String UserIdKey = "UserId";

	// 初始化模式

	public static Boolean IsInit = false;
	public static String IsInitKey = "IsInit";

	// HttpDownload方式下载时，保存的*.zip文件

	public static String ZipFile = "";
	public static String ZipFileKey = "ZipFile";

	// 初始化密码
	public static String PwInit = "123456";
	public static String PwInitKey = "PwInit";
	public static int PwTipInit = 0;
	public static String PwTipsKey = "PwTipsKey";
	public static String pwTipsAnswer = "pwTipsAnswer";

	// 唯一ID

	public static String POSID = "45e7f011-5932-49ba-82f4-6e49db8323fb";
	public static String POSIDKey = "POSID";
	// 导入串
//	public static String ImportStr = "外部码,内部码,货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格";
	public static String ImportStr = AlertUtil.getString(R.string.ImportStr);
	public static String ImportStrKey = "ImportStr";
	// 导出串
//	public static String ExportStr = "门店编号,编号,货位,条码,货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格,数量,时间";
	public static String ExportStr = AlertUtil.getString(R.string.ExportStr);
	public static String ExportStrKey = "ExportStr";

	// / 权限等级,1为隐藏管理员，0为普通权限不再显示相关配置模块，2为超级管理员可以显示相关配置模块
	public static String RightLevelStr = "0";
	public static String RightLevelKey = "RightLevel";
	// 导出关联方式，0为按条码关联，1为按内部码关联

	public static String UnionType = "0";
	public static String UnionTypeKey = "UnionType";

	// 删除方式，0为按编号删除，1为按编号，货位删除

	public static String DeleteType = "1";
	public static String DeleteTypeKey = "DeleteType";

	// 系统界面的主色RGB，中间以逗号分隔

	public static String InterfaceRGB = "100, 80, 156";
	public static String InterfaceRGBKey = "InterfaceRGB";

	// 盘点分隔符串，中间用(分隔

	public static String SeparatorStr = "制表符(空格(,(|(#(;($(~";
	public static String SeparatorStrKey = "SeparatorStr";
	// 导入分隔符串，中间用(分隔
	
	public static String SeparatorInStr = "制表符(空格(,(|(#(;($(~";
	public static String SeparatorInStrKey = "SeparatorStr";
	// 导出分隔符串，中间用(分隔
	
	public static String SeparatorOutStr = "制表符(空格(,(|(#(;($(~";
	public static String SeparatorOutStrKey = "SeparatorStr";

	// 配置扫描界面显示选项的索引及值
//	外部码,内部码,货号,款式,名称,颜色ID,颜色,尺码ID,尺码,大类,小类,库存,价格
	// 3:不显示,但已设置单行显示,2:显示，并且单行,1:只显示,0:不显示
	public static String DisplayItems = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsKey = "DisplayItems";
	
	//入库
	public static String DisplayItemsIn = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsInKey = "DisplayItemsIn";
	//出库
	public static String DisplayItemsOut = "0,0,0,0,1,0,1,0,1,0,0,0,0";
	public static String DisplayItemsOutKey = "DisplayItemsOut";
	

	// 导入修饰符
	public static String ImportDecaration = "无,双引号";
	public static String ImportDecarationKey = "ImportDecaration";

	// 导入修饰符项
	public static String ImportDecaIndex = "0";
	public static String ImportDecaIndexKey = "ImportDecaIndex";

	// 导出修饰符

	public static String ExportDecaration = "无,双引号";
	public static String ExportDecarationKey = "ExportDecaration";

	// 盘点导出修饰符项，与数量间以,分隔，数量是否要最后一位为1表示数据包括修饰符，否则不包括
	public static String ExportDecaIndex = "0,0";
	public static String ExportDecaIndexKey = "ExportDecaIndex";
	// 入库导出修饰符项，与数量间以,分隔，数量是否要最后一位为1表示数据包括修饰符，否则不包括
	public static String ExportDecaInIndex = "0,0";
	public static String ExportDecaInIndexKey = "ExportDecaInIndex";
	// 出库导出修饰符项，与数量间以,分隔，数量是否要最后一位为1表示数据包括修饰符，否则不包括
	public static String ExportDecaOutIndex = "0,0";
	public static String ExportDecaOutIndexKey = "ExportDecaOutIndex";

	// 是否替换间接DB，为1表示需要替换，替换完成，更新为0。

	public static String ReplaceIndirDB = "0";
	public static String ReplaceIndirDBKey = "ReplaceIndirDB";
	public static String ConfigureItem = "141349152831731037232739";
	public static String ConfigureItemKey = "ConfigureItem";
	// 刷新 选择查询界面

	public static String RefreshUI = "0";
	public static String RefreshUIKey = "RefreshUI";

	/*
	 * 扫描界面设置
	 */
	// 条形码验证方式 0不验证，1验证，2提示
	public static String BarCodeAuth = "0";
	public static String BarCodeAuthKey = "BarCodeAuth";
	// 启用唯一条码0不启用，1启用
	public static String UsingBarCode = "0";
	public static String UsingBarCodeKey = "UsingBarCode";
	// 是否输入数量 0输入，1不输入
	public static String IsPutInNum = "1";
	public static String IsPutInNumKey = "IsPutInNum";
	// 修改数量是否需要输入密码，0表示不需要，1表示需要
	public static String ModifyNumPW = "0";
	public static String ModifyNumPWKey = "ModifyNumPW";
	//0累加显示 ,1逐行显示
	public static String ScanningShowMode = "0";
	public static String ScanningShowModeKey = "ScanningShowMode";
	// 内部码或外部码 0内部码，1外部码
	public static String InOutCode = "0";
	public static String InOutCodeKey = "InOutCode";
	// 扫描显示行数
	public static String ScanningShowLineNumb = "30";
	public static String ScanningShowLineNumbKey = "ScanniinngShowLineNumb";

	/*
	 * 长度设置界面
	 */
	// 长度限制方式 0不限制长度，1等于限制，2范围长度
	public static String LengthLimit = "0";
	public static String LengthLimitKey = "LengthLimit";
	// 等于限制 6项设置
	public static String LengthEqualToLimit1 = "0";
	public static String LengthEqualToLimit1Key = "LengthEqualToLimit1";
	public static String LengthEqualToLimit2 = "0";
	public static String LengthEqualToLimit2Key = "LengthEqualToLimit2";
	public static String LengthEqualToLimit3 = "0";
	public static String LengthEqualToLimit3Key = "LengthEqualToLimit3";
	public static String LengthEqualToLimit4 = "0";
	public static String LengthEqualToLimit4Key = "LengthEqualToLimit4";
	public static String LengthEqualToLimit5 = "0";
	public static String LengthEqualToLimit5Key = "LengthEqualToLimit5";
	public static String LengthEqualToLimit6 = "0";
	public static String LengthEqualToLimit6Key = "LengthEqualToLimit6";
	// 范围限制 小值 大值
	public static String LengthLimitRangeMin = "0";
	public static String LengthLimitRangeMinKey = "LengthLimitRangeMin";
	public static String LengthLimitRangeMax = "0";
	public static String LengthLimitRangeMaxKey = "LengthLimitRangeMax";
	// 截去或保留条码 "0不处理","1截去条码后面位数","2截去条码前面位数","3保留条码后面位数","4保留条码前面位数"
	public static String LengthCutOrKeepBarCode = "0";
	public static String LengthCutOrKeepBarCodeKey = "LengthCutOrKeepBarCode";
	// 截去 保留位数
	public static String LengthCutOrKeepBarCodeNum = "1";
	public static String LengthCutOrKeepBarCodeNumKey = "LengthCutOrKeepBarCodeNum";
	/*
	 * 条码拆分界面
	 */
	// 选择条码拆分方式
	public static String BarCodeCutSetting = "0";
	public static String BarCodeCutSettingKey = "BarCodeCutSetting";

	// 款色位长 顺序
	public static String StyleBitLength = "0";
	public static String StyleBitLengthKey = "StyleBitLength";
	public static String StyleBitLengthOrder = "1";
	public static String StyleBitLengthOrderKey = "StyleBitLengthOrder";
	// 颜色位长 顺序
	public static String ColorBitLength = "0";
	public static String ColorBitLengthKey = "ColorBitLength";
	public static String ColorBitLengthOrder = "0";
	public static String ColorBitLengthOrderKey = "ColorBitLengthOrder";
	// 尺码位长 顺序
	public static String SizeBitLength = "0";
	public static String SizeBitLengthKey = "SizeBitLength";
	public static String SizeBitLengthOrder = "0";
	public static String SizeBitLengthOrderKey = "SizeBitLengthOrder";

	/*
	 * 货号，码位长界面
	 */
	// 货号，码位长 顺序
	public static String GoodIdBitLength = "0";
	public static String GoodIdBitLengthKey = "GoodIdBitLength";
	public static String GoodIdBitLengthOrder = "1";
	public static String GoodIdBitLengthOrderKey = "GoodIdBitLengthOrder";
	// 尺码位长 顺序
	public static String SizeBitLength0 = "0";
	public static String SizeBitLength0Key = "SizeBitLength0";
	public static String SizeBitLength0Order = "2";
	public static String SizeBitLength0OrderKey = "SizeBitLength0Order";
	/*
	 * 款色码分隔符界面
	 */
	// 款式分隔符 顺序
	public static String StyleSeparator = "-";
	public static String StyleSeparatorKey = "StyleSeparator";
	public static String StyleSeparatorOrder = "1";
	public static String StyleSeparatorOrderKey = "StyleSeparatorOrder";
	// 颜色分隔符 顺序
	public static String ColorSeparator = "-";
	public static String ColorSeparatorKey = "ColorSeparator";
	public static String ColorSeparatorOrder = "0";
	public static String ColorSeparatorOrderKey = "ColorSeparatorOrder";
	// 尺码分隔符 顺序
	public static String SizeSeparator = "-";
	public static String SizeSeparatorKey = "SizeSeparator";
	public static String SizeSeparatorOrder = "0";
	public static String SizeSeparatorOrderKey = "SizeSeparatorOrder";

	/*
	 * 货号，码分隔符界面
	 */
	// 尺码分隔符 顺序
	public static String GoodsIdSeparator2 = "-";
	public static String GoodsIdSeparator2Key = "GoodsIdSeparator2";
	public static String GoodsIdSeparatorOrder2 = "1";
	public static String GoodsIdSeparatorOrder2Key = "GoodsIdSeparatorOrde";
	public static String SizeSeparator2 = "-";
	public static String SizeSeparator2Key = "SizeSeparator2";
	public static String SizeSeparatorOrder2 = "2";
	public static String SizeSeparatorOrder2Key = "SizeSeparatorOrder2";

	/*
	 * 其它设置界面
	 */
	// 是否导出 表头 0导出表头 1不导出表头
	public static String IsExportHeader = "0";
	public static String IsExportHeaderKey = "IsExportHeader";
	// 是否导出扫描顺序，0为不导出，1为导出
	public static String IsExportSO = "0";
	public static String IsExportSOKey = "IsExportSO";
	// 导出管理单位，0为按编号导出，1为按编号，货位导出 <summary>
	public static String ExportType = "0";
	public static String ExportTypeKey = "ExportType";
	// 导出文件类型，0txt,1csv类型，请注意0
	public static String ExportFT = "0";
	public static String ExportFTKey = "ExportFT";
	// 全部导出是否合并异出，0为合并导出，1为全部导出时分别导出0
	public static String MergeAllExport = "0";
	public static String MergeAllExportKey = "MergeAllExport";
	// 数量拆分导出，0为不拆分，1为拆分
	public static String ExportNumSplit = "0";
	public static String ExportNumSplitKey = "ExportNumSplit";
	// 资料导入方式，0为全量导入（覆盖导入），1为增量导入
	public static String ExportWay = "0";
	public static String ExportWayKey = "ExportWay";
	// 导出方式，0为条码+总数，1为条码+1
	public static String ExportMode= "0";
	public static String ExportModeKey = "ExportModeKey";
	/*
	 * 其它设置
	 */

	// 系统门店编号 设置
	public static String ShopID = "000000";
	public static String ShopIDKey = "ShopID";
	// 系统客户名称设置
	public static String ShopName = "销邦科技";
	public static String ShopNameKey = "ShopName";
	// 自动弹出系统 时间设置0不弹出1弹出
	public static String ShowSettingTime = "1";
	public static String ShowSettingTimeKey = "ShowSettingTime";
	// 盘点是否关闭WIFI "0是", "1否"
	public static String IsCloseWIFI = "0";
	public static String IsCloseWIFIKey = "IsCloseWIFI";

	// 删除数据管理单位0按编号删除 1按编号，货位删除
	public static String DeleteDataWay = "1";
	public static String DeleteDataWayKey = "DeleteDataWay";
	// 删除数据密码管理 0不需要 1需要
	public static String DeleteDataPSW = "0";
	public static String DeleteDataPSWKey = "DeleteDataPSW";
	// 数据是否允许小数 0不允许  1允许1位 2允许2位
	public static String IsDataDecimalPoint = "0";
	public static String IsDataDecimalPointKey = "IsDataDecimalPoint";
	// 退出系统密码控制 0不控制 1控制
	public static String ExitSystemPSW = "0";
	public static String ExitSystemPSWKey = "ExitSystemPSW";

	/*
	 * 命名方式选择界面
	 */
	// 导出前缀/summary>
	public static String ExportPrefix = AlertUtil.getString(R.string.bill_inventory);
	public static String ExportPrefixKey = "ExportPrefix";
	// 导出的命名方式0("默认的命名方式");1("默认的命名方式+_(单据总数量)");2("默认的命名方式+_年月日");3("默认的命名方式+_年月日+_(单据总数量)");
	//	4("默认的命名方式+_年月日时分秒");5("默认的命名方式+_年月日时分秒+_(单据总数量)");6("前缀++_年月日");7("前缀++_年月日+_(单据总数量)");
	//	8("前缀++_年月日时分秒");9("前缀++_年月日时分秒+_(单据总数量)");
	// 入库导出前缀/summary>
	public static String ExportInPrefix = AlertUtil.getString(R.string.bill_instock);
	public static String ExportInPrefixKey = "ExportInPrefix";
	// 出库导出前缀/summary>
	public static String ExportOutPrefix = AlertUtil.getString(R.string.bill_outstock);
	public static String ExportOutPrefixKey = "ExportOutPrefix";
	public static String ExportNameWay = "0";
	public static String ExportNameWayKey = "ExPrefixAndIndex";

	// 删除数据是否需要输入密码，0表示不需要，1表示需要

	public static String DeletePW = "1";
	public static String DeletePWKey = "DeletePW";

	// 是否是测试版，1为是，是的话，在登录的时候提示系统为测试版，否则的话不显示

	public static String IsForTest = "0";
	public static String IsForTestKey = "IsForTest";

	// 另外一个Indir数据库放置的位置，F放于Flash上面，S放于Storage Card上面。

	public static String FlashOrST = "F";
	public static String FlashOrSTKey = "FlashOrST";

	// 是否导入基础资料，1为是，0为不导入

	public static String IsImportBasedata = "1";
	public static String IsImportBasedataKey = "IsImportBasedata";

	// 导入文件是否有抬头，1为有，0为无

	public static String ImportTopTitle = "1";
	public static String ImportTopTitleKey = "ImportTopTitle";

	// 当前客户使用的ERP

	public static String ERP = "000000";
	public static String ERPKey = "ERP";

	// 针对相对的迅尔ERP配置的信息

	//
	public static String XunerInfo = "5,30,5,8";
	public static String XunerInfoKey = "XunerInfo";

	// 针对相对的管家婆ERP配置的信息
	public static String GraspInfo = "000000,销邦科技";
	public static String GraspInfoKey = "GraspInfo";

	// 基础资料导入，是否需要添加增量导入方式,0为不添加，1为添加。该功能在程序上表现为如果选择了添加，则在导入时，会弹增量和全量的方式供选择

	public static String AddImportIncreWay = "0";
	public static String AddImportIncreWayKey = "AddImportIncreWay";

	// 记录上次用户选择的导入基础资料方式，0为覆盖导入，1为增量导入

	public static String LastImportWay = "0";
	public static String LastImportWayKey = "LastImportWay";

	// 退出是否需要密码，0不需要，1需要

	public static String ExitPwd = "0";
	public static String ExitPwdKey = "ExitPwd";

	// 导出数量是否拆分，0不拆分，1拆分

	public static String SplitQty = "0";
	public static String SplitQtyKey = "SplitQty";

	// 当前系统扫描声音文件名，若为标准系统声音，则名字为“标准系统声音”，否则为指定的声音文件下的文件名称

	public static String ScanSound = "标准系统声音";
	public static String ScanSoundKey = "ScanSound";

	//
	// 扫描为空是否需要声音0为不需要，1为需要,该选项已经取消，

	// 是否弹出设置时间窗体.1为设置，其他不弹出窗体进行设置.

	public static String SetTime = "0";
	public static String SetTimeKey = "SetTime";

	// 枪版本信息

	public static String PDAVersion = "1";
	public static String PDAVersionKey = "PDAVersion";

	// 扫描时声音，马达和蜂鸣器控制
	// 0单独喇叭
	// 1单独马达
	// 2单独蜂鸣器
	// 3喇叭+马达
	// 4蜂鸣器+马达
	// 5关闭
	// -1为读取不到键值

	public static String ScanSoundType = "3";
	public static String ScanSoundTypeKey = "ScanSoundType";

	// 库存,数量小数点位数,0表示处理成整数,1，2分别表示处理小数点后1位和2位。20131017 Added

	public static String DotCounter = "0";
	public static String DotCounterKey = "DotCounterKey";

	// RFID相关，以|分隔，五个域分别表示：扫描方式，0普通扫描，1RFID扫描
	// 工作距离设置，0近，1中，2远
	// 扫描模式设置，0单件扫描，1多件增量，2为多件全量
	// 扫描内容设置，0为EPC，1为TID
	// 实际扫描方式设置：0为手动间断性扫描，1为连续扫描方式

	public static String RFIDCorresponding = "1|2|0|1|0";
	public static String RFIDCorrespondingKey = "RFIDCorresponding";

	// 导入导出设置界面
	public static String inputOutputSetting;


	// 迅尔设置
	public static String XunerSetting1 = "0";
	public static String XunerSetting1Key = "XunerSetting1";
	public static String XunerSetting2 = "0";
	public static String XunerSetting2Key = "XunerSetting2";
	public static String XunerSetting3 = "0";
	public static String XunerSetting3Key = "XunerSetting3";
	public static String XunerSetting4 = "0";
	public static String XunerSetting4Key = "XunerSetting4";
	
	// 管家婆设置
	public static String GuanJiaPoshopNo = "";
	public static String GuanJiaPoshopNoKey = "GuanJiaPoshopNo";
	public static String GuanJiaPoshopName = "";
	public static String GuanJiaPoshopNameKey = "GuanJiaPoshopName";

	// erp设置,0为不设置,1为迅尔,2为管家婆,0为默认设置
	public static String ERPSetting = "0";
	public static String ERPSettingKey = "ERPSetting";

	// 扫描显示
	public static String scanDisplay = "scanDisplay";
	public static boolean scanDisplayKey;
	
	// 商品编号
	public static String goods = "goods";
	public static boolean goodsKey;
	public static String goodsDisPlayOnline = "goodsDisPlayOnline";
	public static boolean goodsDisPlayOnlineKey;

	public static String style = "style";
	public static boolean styleKey;
	public static String styleDisPlayOnline = "styleDisPlayOnline";
	public static boolean styleDisPlayOnlineKey;

	public static String name = "name";
	public static boolean nameKey;
	public static String nameDisPlayOnline = "nameDisPlayOnline";
	public static boolean nameDisPlayOnlineKey;

	public static String color = "color";
	public static boolean colorKey;
	public static String colorDisPlayOnline = "colorDisPlayOnline";
	public static boolean colorDisPlayOnlineKey;

	public static String colorId = "colorId";
	public static boolean colorIdKey;
	public static String colorIdDisPlayOnline = "colorIdDisPlayOnline";
	public static boolean colorIdDisPlayOnlineKey;

	public static String size = "size";
	public static boolean sizeKey;
	public static String sizeDisPlayOnline = "sizeDisPlayOnline";
	public static boolean sizeDisPlayOnlineKey;

	public static String sizeId = "sizeId";
	public static boolean sizeIdKey;
	public static String sizeIdDisPlayOnline = "sizeIdDisPlayOnline";
	public static boolean sizeIdDisPlayOnlineKey;

	public static String bigSort = "bigSort";
	public static boolean bigSortKey;
	public static String bigSortDisPlayOnline = "bigSort";
	public static boolean bigSortDisPlayOnlineKey;

	public static String smallSort = "smallSort";
	public static boolean smallSortKey;
	public static String smallSortDisPlayOnline = "smallSortDisPlayOnline";
	public static boolean smallSortDisPlayOnlineKey;

	public static String quantity = "quantity";
	public static boolean quantityKey;
	public static String quantityDisPlayOnline = "quantityDisPlayOnline";
	public static boolean quantityDisPlayOnlineKey;

	public static String price = "price";
	public static boolean priceKey;
	public static String priceDisPlayOnline = "priceDisPlayOnline";
	public static boolean priceDisPlayOnlineKey;

	// 系统升级配置
	public static final String SYSTEM_UPDATE_URL = "SYSTEM_UPDATE_URL";
	public static final String SYSTEM_UPDATE_ENTERPRISE_ID = "SYSTEM_UPDATE_ENTERPRISE_ID";
	public static final String SYSTEM_UPDATE_PROMPT = "SYSTEM_UPDATE_PROMPT";// true/false
	
	/*
	 * 扫描模式设置
	 */
	// 扫描模式 0条码，1RFID
	public static String ScanPattern = "0";
	public static String ScanPatternKey = "ScanPattern";
	
	

}