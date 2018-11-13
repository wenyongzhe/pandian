package com.supoin.commoninventory.util;

import com.supoin.commoninventory.entity.BarSplitLenSet;
import com.supoin.commoninventory.entity.LengthSet;

public class GlobalRunConfig
{
	public LengthSet lengthSet = new LengthSet();
	public BarSplitLenSet barSplitSet = new BarSplitLenSet();

	private GlobalRunConfig()		//必须的
	{

	}

	public static GlobalRunConfig GetInstance()
	{
		if (instance == null)
		{
			instance = new GlobalRunConfig();
		}
		return instance;
	}

	private static GlobalRunConfig instance = null;
	
	/// <summary>
	/// 条码识别的方式，共3种，
	/// 分别为：1验证，0为不验证，2为提示(如果基础资料中不存在,仅提示,其他操作相同).默认为不验证
	/// </summary>
	public int iBarVerifyWay=0;


	/// <summary>
	/// 权限等级,1为隐藏管理员，0为普通权限不再显示相关配置模块，2为超级管理员可以显示相关配置模块
	/// </summary>
	public int iRightLevel=0;
	
	/// <summary>
	/// 1为已经F17，否则的话为非
	/// </summary>
	public int iIsF17=0;

	/// <summary>
	/// 是否启用唯一码
	/// 分别为：1启用，0不启用，默认为不启用。
	/// </summary>
	public int iSingleBarVerify=0;
	

	/// <summary>
	/// 扫描界面显示的行数
	/// </summary>
	public int iDisplayRows=30;


	/// <summary>
	/// 盘点处理为内部码还是外部码
	/// 分别为：0内部码，1外部码，
	/// </summary>
	public int iBarOutIn=1;

	/// <summary>
	/// 1为累加显示，0为逐行显示
	/// </summary>
	public int iScanDisplayWay=1;


	/// <summary>
	/// 1为输入数量，0为不输入数量，默认为不输入数量
	/// </summary>
	public int iScanInputQty=0;

	/// <summary>
	/// 是否截取或保留条码后面位数，0为不处理，1为截取条码前位数，2为截取条码后位数，
	///										   3为保留条码前位数，4为保留条码后位数，
	///									       默认为截取和保留
	/// </summary>
	public int iCurTailWay=0;
	
	/// <summary>
	/// 如果为截取，截取的位数
	/// </summary>
	public int iCurTailLen=0;

	/// <summary>
	/// 
	/// </summary>
	public int iBarSplitWay=1;

	/// <summary>
	/// 扫描方式，0为普通扫描，1为RFID扫描
	/// </summary>
	public int iScanMode;

	/// <summary>
	/// RFID欲扫描获取的内容，0为EPC，1为TID
	/// </summary>
	public int iRFIDContent;

	/// <summary>
	/// RFID扫描的距离设置，0近，1中，2远
	/// </summary>
	public int iRFIDDistant;

	/// <summary>
	/// RFID扫描模式，0为单件扫描，1为多件增量，2为多件全量的方式
	/// </summary>
	public int iRFIDScanMode;

	/// <summary>
	/// 扫描实际操作方式，0为手动间断性扫描，即按一次扫描执行一次扫描动作。1表示为连续性的扫描动作，按一次表示不间断地扫描
	/// </summary>
	public int iRFIDActualMM;

	public LengthSet getLengthSet() {
		return lengthSet;
	}

	public void setLengthSet(LengthSet lengthSet) {
		this.lengthSet = lengthSet;
	}

	public BarSplitLenSet getBarSplitSet() {
		return barSplitSet;
	}

	public void setBarSplitSet(BarSplitLenSet barSplitSet) {
		this.barSplitSet = barSplitSet;
	}

	public int getiBarVerifyWay() {
		return iBarVerifyWay;
	}

	public void setiBarVerifyWay(int iBarVerifyWay) {
		this.iBarVerifyWay = iBarVerifyWay;
	}

	public int getiRightLevel() {
		return iRightLevel;
	}

	public void setiRightLevel(int iRightLevel) {
		this.iRightLevel = iRightLevel;
	}

	public int getiIsF17() {
		return iIsF17;
	}

	public void setiIsF17(int iIsF17) {
		this.iIsF17 = iIsF17;
	}

	public int getiSingleBarVerify() {
		return iSingleBarVerify;
	}

	public void setiSingleBarVerify(int iSingleBarVerify) {
		this.iSingleBarVerify = iSingleBarVerify;
	}

	public int getiDisplayRows() {
		return iDisplayRows;
	}

	public void setiDisplayRows(int iDisplayRows) {
		this.iDisplayRows = iDisplayRows;
	}

	public int getiBarOutIn() {
		return iBarOutIn;
	}

	public void setiBarOutIn(int iBarOutIn) {
		this.iBarOutIn = iBarOutIn;
	}

	public int getiScanDisplayWay() {
		return iScanDisplayWay;
	}

	public void setiScanDisplayWay(int iScanDisplayWay) {
		this.iScanDisplayWay = iScanDisplayWay;
	}

	public int getiScanInputQty() {
		return iScanInputQty;
	}

	public void setiScanInputQty(int iScanInputQty) {
		this.iScanInputQty = iScanInputQty;
	}

	public int getiCurTailWay() {
		return iCurTailWay;
	}

	public void setiCurTailWay(int iCurTailWay) {
		this.iCurTailWay = iCurTailWay;
	}

	public int getiCurTailLen() {
		return iCurTailLen;
	}

	public void setiCurTailLen(int iCurTailLen) {
		this.iCurTailLen = iCurTailLen;
	}

	public int getiBarSplitWay() {
		return iBarSplitWay;
	}

	public void setiBarSplitWay(int iBarSplitWay) {
		this.iBarSplitWay = iBarSplitWay;
	}

	public int getiScanMode() {
		return iScanMode;
	}

	public void setiScanMode(int iScanMode) {
		this.iScanMode = iScanMode;
	}

	public int getiRFIDContent() {
		return iRFIDContent;
	}

	public void setiRFIDContent(int iRFIDContent) {
		this.iRFIDContent = iRFIDContent;
	}

	public int getiRFIDDistant() {
		return iRFIDDistant;
	}

	public void setiRFIDDistant(int iRFIDDistant) {
		this.iRFIDDistant = iRFIDDistant;
	}

	public int getiRFIDScanMode() {
		return iRFIDScanMode;
	}

	public void setiRFIDScanMode(int iRFIDScanMode) {
		this.iRFIDScanMode = iRFIDScanMode;
	}

	public int getiRFIDActualMM() {
		return iRFIDActualMM;
	}

	public void setiRFIDActualMM(int iRFIDActualMM) {
		this.iRFIDActualMM = iRFIDActualMM;
	}
	
	

}