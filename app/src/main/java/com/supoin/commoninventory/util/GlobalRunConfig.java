package com.supoin.commoninventory.util;

import com.supoin.commoninventory.entity.BarSplitLenSet;
import com.supoin.commoninventory.entity.LengthSet;

public class GlobalRunConfig
{
	public LengthSet lengthSet = new LengthSet();
	public BarSplitLenSet barSplitSet = new BarSplitLenSet();

	private GlobalRunConfig()		//�����
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
	/// ����ʶ��ķ�ʽ����3�֣�
	/// �ֱ�Ϊ��1��֤��0Ϊ����֤��2Ϊ��ʾ(������������в�����,����ʾ,����������ͬ).Ĭ��Ϊ����֤
	/// </summary>
	public int iBarVerifyWay=0;


	/// <summary>
	/// Ȩ�޵ȼ�,1Ϊ���ع���Ա��0Ϊ��ͨȨ�޲�����ʾ�������ģ�飬2Ϊ��������Ա������ʾ�������ģ��
	/// </summary>
	public int iRightLevel=0;
	
	/// <summary>
	/// 1Ϊ�Ѿ�F17������Ļ�Ϊ��
	/// </summary>
	public int iIsF17=0;

	/// <summary>
	/// �Ƿ�����Ψһ��
	/// �ֱ�Ϊ��1���ã�0�����ã�Ĭ��Ϊ�����á�
	/// </summary>
	public int iSingleBarVerify=0;
	

	/// <summary>
	/// ɨ�������ʾ������
	/// </summary>
	public int iDisplayRows=30;


	/// <summary>
	/// �̵㴦��Ϊ�ڲ��뻹���ⲿ��
	/// �ֱ�Ϊ��0�ڲ��룬1�ⲿ�룬
	/// </summary>
	public int iBarOutIn=1;

	/// <summary>
	/// 1Ϊ�ۼ���ʾ��0Ϊ������ʾ
	/// </summary>
	public int iScanDisplayWay=1;


	/// <summary>
	/// 1Ϊ����������0Ϊ������������Ĭ��Ϊ����������
	/// </summary>
	public int iScanInputQty=0;

	/// <summary>
	/// �Ƿ��ȡ�����������λ����0Ϊ������1Ϊ��ȡ����ǰλ����2Ϊ��ȡ�����λ����
	///										   3Ϊ��������ǰλ����4Ϊ���������λ����
	///									       Ĭ��Ϊ��ȡ�ͱ���
	/// </summary>
	public int iCurTailWay=0;
	
	/// <summary>
	/// ���Ϊ��ȡ����ȡ��λ��
	/// </summary>
	public int iCurTailLen=0;

	/// <summary>
	/// 
	/// </summary>
	public int iBarSplitWay=1;

	/// <summary>
	/// ɨ�跽ʽ��0Ϊ��ͨɨ�裬1ΪRFIDɨ��
	/// </summary>
	public int iScanMode;

	/// <summary>
	/// RFID��ɨ���ȡ�����ݣ�0ΪEPC��1ΪTID
	/// </summary>
	public int iRFIDContent;

	/// <summary>
	/// RFIDɨ��ľ������ã�0����1�У�2Զ
	/// </summary>
	public int iRFIDDistant;

	/// <summary>
	/// RFIDɨ��ģʽ��0Ϊ����ɨ�裬1Ϊ���������2Ϊ���ȫ���ķ�ʽ
	/// </summary>
	public int iRFIDScanMode;

	/// <summary>
	/// ɨ��ʵ�ʲ�����ʽ��0Ϊ�ֶ������ɨ�裬����һ��ɨ��ִ��һ��ɨ�趯����1��ʾΪ�����Ե�ɨ�趯������һ�α�ʾ����ϵ�ɨ��
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