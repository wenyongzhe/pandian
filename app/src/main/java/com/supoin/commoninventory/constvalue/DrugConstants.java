package com.supoin.commoninventory.constvalue;

public class DrugConstants{
    public  String PRODUCE_IN = "ProduceWareHouseIn";
    public static  String PUR_IN = "PurchaseWareHouseIn";
    public  String RETURN_IN = "ReturnWareHouseIn";
    public  String ALL_IN = "AllocateWareHouseIn";
    public  String ALL_OUT = "AllocateWareHouseOut";
    public  String CEHCK_OUT = "CheckWareHouseOut";
    public  String DIRECT_OUT = "DirectAllocateWareHouseOut";
    public  String DES_OUT = "DestoryWareHouseOut";
    public  String RETURN_OUT = "ReturnWareHouseOut";
    public  String REWORK_OUT = "ReworkWareHouseOut";
    public  String SALES_OUT = "SalesWareHouseOut";
    public static  String WARE_HOUSE_IN = "WareHouseIn";
    public static  String WARE_HOUSE_OUT = "WareHouseOut";
    public static String STOCK_IN = "StockIn";
    public static String STOCK_OUT = "StockOut";
    public  String Include = "Include";
    public static   String NoInclude = "NoInclude";
    public  String NoMessage = "NoMessage";
    public static   String Message = "Message";
    public  String Factory = "Factory";
    public  String Market = "Market";
    public  String Delivery = "Delivery";
    public  String Terminal = "Terminal";
    public static  String Account = "Account";
    public  static String Customer = "Customer";
    public  static String DrugCode = "Drugcode";
    public static String SerialNumber = "0300245";//SalesPoint.Device.Core.Sys.SerialNumber();
    /// <summary>
    /// 默认配置ERP
    /// </summary>
    public static String DefaultERP = "000000";
    /// <summary>
    /// 迅尔ERP
    /// </summary>
    public static String XE = "Xuner";
    /// <summary>
    /// 迅尔默认配置
    /// </summary>
    public static String XunerInfo = "5,30,5,8";
    /// <summary>
    /// 管家婆ERP
    /// </summary>
    public static String GJP = "Grasp";
    /// <summary>
    /// 管家婆默认配置信息
    /// </summary>
    public static String GraspInfo = "000000,销邦科技";
    
  //RFID功率初始化
  	public static String stockOpw="30";
  	public static String stockMode="0";//0代表普通，1代表RFID
  	public static String returnOpw="30";
  	public static String returnMode="0";
  	public static String receiptOpw="30";
  	public static String searchOpw="20";
  	public static String receiptMode="0";
  	//RFID readtime
  	public static String rfidReadTime="50";
  	//RFID sleep
  	public static String rfidSleep="100";
  	//按键模式
  	public static String keyMode = "KEYMODE";
  	
}