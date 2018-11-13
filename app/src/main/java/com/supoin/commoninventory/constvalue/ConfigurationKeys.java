package com.supoin.commoninventory.constvalue;
public class ConfigurationKeys{
    /// <summary>
    /// 服务器地址
    /// </summary>
    public static  String ServiceIp = "192.168.1.198";//"serviceIp";
    /// <summary>
    /// 端口
    /// </summary>
    public static String ServicePort = "8886";//"servicePort";
    /// <summary>
    /// 地址头
    /// </summary>
    public static String UrlHead = "http://";//"urlHead";
    /// <summary>
    /// 登陆尾
    /// </summary>
    public static String LoginUrlTail = "/interface/services/AuthenticateService/logInPDA?";//"loginUrlTail";
    /// <summary>
    /// 基础数据尾
    /// </summary>
    public static String DownloadUrlTail ="/interface/services/uploadAndDownload/download?";// "downloadUrlTail";
    /// <summary>
    /// 上传单据尾
    /// </summary>
    public static String UploadUrlTail = "/interface/services/uploadAndDownload/saveOrUploadOneBill?";//"uploadUrlTail";
    /// <summary>
    /// 基础数据版本
    /// </summary>
    public static String BaseDataVersion = "baseDataVersion";
    /// <summary>
    /// 企业类型
    /// </summary>
    public static String CompanyType = "companyType";
    /// <summary>
    /// 通讯类型
    /// </summary>
    public static String ConnType = "connType";
    /// <summary>
    /// 药品信息设置
    /// </summary>
    public static String DrugInfoLogic = "drugInfoLogic";
    /// <summary>
    /// 重复信息设置
    /// </summary>
    public static String MessageLogic = "messageLogic";
    /// <summary>
    /// 连续扫描
    /// </summary>
    public static String ContinueScan = "continueScan";
    /// <summary>
    /// 登录记录
    /// </summary>
    public static String LoginRecord = "loginRecord";
    /// <summary>
    /// SN编号
    /// </summary>
    public static String DelimitSN = "delimitSN";
    /// <summary>
    /// 时间设置
    /// </summary>
    public static String DateTime = "dateTime";
    /// <summary>
    /// 产品编号(PC)
    /// </summary>//出入库002
    public static String ProductNum ="MI001\\SUPOIN\\SP002";//需要配置产品编号，用来检测版本更新，如果需要用到公司的版本升级管理模块，这个需要后台分配才能使用
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


}