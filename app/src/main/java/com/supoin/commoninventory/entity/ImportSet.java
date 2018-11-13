package com.supoin.commoninventory.entity;

public class ImportSet{
	public int iBarcode = 0;
	public int iCode=0;
	public int iArtNO = 0;
	public int iStyle = 0;
	public int iName = 0;
	public int iColorID = 0;
	public int iColorName = 0;
	public int iSizeID = 0;
	public int iSizeName = 0;
	public int iBig = 0;
	public int iSmall = 0;
	public int iStock = 0;
	public int iPrice = 0;
	public int iColumnNum = 0;

	public String strListSeparator = "0";
	public ImportSet(){
		
	}
	public int getiBarcode() {
		return iBarcode;
	}
	public void setiBarcode(int iBarcode) {
		this.iBarcode = iBarcode;
	}
	public int getiCode() {
		return iCode;
	}
	public void setiCode(int iCode) {
		this.iCode = iCode;
	}
	public int getiArtNO() {
		return iArtNO;
	}
	public void setiArtNO(int iArtNO) {
		this.iArtNO = iArtNO;
	}
	public int getiStyle() {
		return iStyle;
	}
	public void setiStyle(int iStyle) {
		this.iStyle = iStyle;
	}
	public int getiName() {
		return iName;
	}
	public void setiName(int iName) {
		this.iName = iName;
	}
	public int getiColorID() {
		return iColorID;
	}
	public void setiColorID(int iColorID) {
		this.iColorID = iColorID;
	}
	public int getiColorName() {
		return iColorName;
	}
	public void setiColorName(int iColorName) {
		this.iColorName = iColorName;
	}
	public int getiSizeID() {
		return iSizeID;
	}
	public void setiSizeID(int iSizeID) {
		this.iSizeID = iSizeID;
	}
	public int getiSizeName() {
		return iSizeName;
	}
	public void setiSizeName(int iSizeName) {
		this.iSizeName = iSizeName;
	}
	public int getiBig() {
		return iBig;
	}
	public void setiBig(int iBig) {
		this.iBig = iBig;
	}
	public int getiSmall() {
		return iSmall;
	}
	public void setiSmall(int iSmall) {
		this.iSmall = iSmall;
	}
	public int getiStock() {
		return iStock;
	}
	public void setiStock(int iStock) {
		this.iStock = iStock;
	}
	public int getiPrice() {
		return iPrice;
	}
	public void setiPrice(int iPrice) {
		this.iPrice = iPrice;
	}
	public int getiColumnNum() {
		return iColumnNum;
	}
	public void setiColumnNum(int iColumnNum) {
		this.iColumnNum = iColumnNum;
	}
	public String getStrListSeparator() {
		return strListSeparator;
	}
	public void setStrListSeparator(String strListSeparator) {
		this.strListSeparator = strListSeparator;
	}

	@Override
	public String toString() {
		return "ImportSet [iBarcode=" + iBarcode + ", iCode=" + iCode
				+ ", iArtNO=" + iArtNO + ", iStyle=" + iStyle + ", iName="
				+ iName + ", iColorID=" + iColorID + ", iColorName="
				+ iColorName + ", iSizeID=" + iSizeID + ", iSizeName="
				+ iSizeName + ", iBig=" + iBig + ", iSmall=" + iSmall
				+ ", iStock=" + iStock + ", iPrice=" + iPrice + ", iColumnNum="
				+ iColumnNum + ", strListSeparator=" + strListSeparator + "]";
	}
}