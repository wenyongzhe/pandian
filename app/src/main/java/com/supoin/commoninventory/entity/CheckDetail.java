package com.supoin.commoninventory.entity;

public class CheckDetail {
	// ���зֱ��������,����,���,����,��ɫID,��ɫ,����ID,����,�۸�
	// �ŵ���
	public String strShopID = "";
	// �̵���
	public String strCheckID = "";
	// ��λ���
	public String strPositionID = "";
	// ����
	public String strGdBar = "";
	// ���
	public int strStock = 0;
	// �۸�
	public int strPrice = 0;
	// ʱ��
	public String strTime = "";
	// ����
	public String strGdArtNO = "";
	// ���
	public String strGdStyle = "";
	// ��ɫ����
	public String strGdColorID = "";
	// �������
	public String strGdSizeID = "";
	// �̵�����
	public int dCheckNum = 0;
	// ����
	public String strGdName = "";
	// ��ɫ
	public String strGdColorName = "";
	// �ߴ�
	public String strGdSizeName = "";
	// ����1
	public String strProperty1 = "";
	// ����2
	public String strProperty2 = "";
	// ���
	public int dStock = 0;
	// �۸�
	public float dGdPrice = 0;
	// Ԥ���ֶ�
	public String strReservedMg = "";
	// Ψһ���ֶ�,���ϵͳ������Ψһ,���ʾ��Ψһ,�����ŵ�������������,����strGdBar����ͬ������
	public String strSingleGdBar = "";
	
	
	
	
	
	@Override
	public String toString() {
		return "CheckDetail [strShopID=" + strShopID + ", strCheckID="
				+ strCheckID + ", strPositionID=" + strPositionID
				+ ", strGdBar=" + strGdBar + ", strStock=" + strStock
				+ ", strPrice=" + strPrice + ", strTime=" + strTime
				+ ", strGdArtNO=" + strGdArtNO + ", strGdStyle=" + strGdStyle
				+ ", strGdColorID=" + strGdColorID + ", strGdSizeID="
				+ strGdSizeID + ", dCheckNum=" + dCheckNum + ", strGdName="
				+ strGdName + ", strGdColorName=" + strGdColorName
				+ ", strGdSizeName=" + strGdSizeName + ", strProperty1="
				+ strProperty1 + ", strProperty2=" + strProperty2 + ", dStock="
				+ dStock + ", dGdPrice=" + dGdPrice + ", strReservedMg="
				+ strReservedMg + ", strSingleGdBar=" + strSingleGdBar + "]";
	}

	public CheckDetail() {

	}

	public String getStrShopID() {
		return strShopID;
	}

	public void setStrShopID(String strShopID) {
		this.strShopID = strShopID;
	}

	public String getStrCheckID() {
		return strCheckID;
	}

	public void setStrCheckID(String strCheckID) {
		this.strCheckID = strCheckID;
	}

	public String getStrPositionID() {
		return strPositionID;
	}

	public void setStrPositionID(String strPositionID) {
		this.strPositionID = strPositionID;
	}

	public String getStrGdBar() {
		return strGdBar;
	}

	public void setStrGdBar(String strGdBar) {
		this.strGdBar = strGdBar;
	}

	public String getStrGdArtNO() {
		return strGdArtNO;
	}

	public void setStrGdArtNO(String strGdArtNO) {
		this.strGdArtNO = strGdArtNO;
	}

	public String getStrGdStyle() {
		return strGdStyle;
	}

	public void setStrGdStyle(String strGdStyle) {
		this.strGdStyle = strGdStyle;
	}

	public String getStrGdColorID() {
		return strGdColorID;
	}

	public void setStrGdColorID(String strGdColorID) {
		this.strGdColorID = strGdColorID;
	}

	public String getStrGdSizeID() {
		return strGdSizeID;
	}

	public void setStrGdSizeID(String strGdSizeID) {
		this.strGdSizeID = strGdSizeID;
	}

	public float getdCheckNum() {
		return dCheckNum;
	}

	public void setdCheckNum(int dCheckNum) {
		this.dCheckNum = dCheckNum;
	}

	public String getStrGdName() {
		return strGdName;
	}

	public void setStrGdName(String strGdName) {
		this.strGdName = strGdName;
	}

	public String getStrGdColorName() {
		return strGdColorName;
	}

	public void setStrGdColorName(String strGdColorName) {
		this.strGdColorName = strGdColorName;
	}

	public String getStrGdSizeName() {
		return strGdSizeName;
	}

	public void setStrGdSizeName(String strGdSizeName) {
		this.strGdSizeName = strGdSizeName;
	}

	public String getStrProperty1() {
		return strProperty1;
	}

	public void setStrProperty1(String strProperty1) {
		this.strProperty1 = strProperty1;
	}

	public String getStrProperty2() {
		return strProperty2;
	}

	public void setStrProperty2(String strProperty2) {
		this.strProperty2 = strProperty2;
	}

	public int getdStock() {
		return dStock;
	}

	public void setdStock(int dStock) {
		this.dStock = dStock;
	}

	public float getdGdPrice() {
		return dGdPrice;
	}

	public void setdGdPrice(float dGdPrice) {
		this.dGdPrice = dGdPrice;
	}

	public String getStrReservedMg() {
		return strReservedMg;
	}

	public void setStrReservedMg(String strReservedMg) {
		this.strReservedMg = strReservedMg;
	}

	public String getStrSingleGdBar() {
		return strSingleGdBar;
	}

	public void setStrSingleGdBar(String strSingleGdBar) {
		this.strSingleGdBar = strSingleGdBar;
	}

	public int getStrStock() {
		return strStock;
	}

	public void setStrStock(int strStock) {
		this.strStock = strStock;
	}

	public int getStrPrice() {
		return strPrice;
	}

	public void setStrPrice(int strPrice) {
		this.strPrice = strPrice;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}
	
	
	
//	public CheckDetail ent = new CheckDetail();
//
//	public void Reset()
//	{
//		ent.strGdArtNO = "";
//		ent.strGdStyle = "";
//		ent.strGdBar = "";
//		ent.strGdColorID = "";
//		ent.strGdColorName = "";
//		ent.strGdName = "";
//		ent.strGdSizeID = "";
//		ent.strGdSizeName = "";
//		ent.strProperty1 = "";
//		ent.strProperty2 = "";
//		ent.dStock = 0;
//		ent.strSingleGdBar = "";
//		ent.dCheckNum = 0;
//		ent.dGdPrice = 0;
//	}
}