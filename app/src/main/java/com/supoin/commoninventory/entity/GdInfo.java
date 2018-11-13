package com.supoin.commoninventory.entity;

public class GdInfo {
	// 外部码
	public String strGdBarcode = "";
	// 内部码
	public String strGdCode = "";
	// 货号
	public String strGdArtNO = "";
	// 款号
	public String strGdStyle = "";
	public String strGdName = "";
	public String strGdColorID = "";
	public String strGdColorName = "";
	public String strGdSizeID = "";
	public String strGdSizeName = "";
	public String strProperty1 = "";
	public String strProperty2 = "";
	public int dGdStock = 0;
	public float dGdPrice = 0;
	public GdInfo() {
	}

	public String getStrGdBarcode() {
		return strGdBarcode;
	}

	public void setStrGdBarcode(String strGdBarcode) {
		this.strGdBarcode = strGdBarcode;
	}

	public String getStrGdCode() {
		return strGdCode;
	}

	public void setStrGdCode(String strGdCode) {
		this.strGdCode = strGdCode;
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

	public String getStrGdName() {
		return strGdName;
	}

	public void setStrGdName(String strGdName) {
		this.strGdName = strGdName;
	}

	public String getStrGdColorID() {
		return strGdColorID;
	}

	public void setStrGdColorID(String strGdColorID) {
		this.strGdColorID = strGdColorID;
	}

	public String getStrGdColorName() {
		return strGdColorName;
	}

	public void setStrGdColorName(String strGdColorName) {
		this.strGdColorName = strGdColorName;
	}

	public String getStrGdSizeID() {
		return strGdSizeID;
	}

	public void setStrGdSizeID(String strGdSizeID) {
		this.strGdSizeID = strGdSizeID;
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

	public int getdGdStock() {
		return dGdStock;
	}

	public void setdGdStock(int dGdStock) {
		this.dGdStock = dGdStock;
	}

	public float getdGdPrice() {
		return dGdPrice;
	}

	public void setdGdPrice(float dGdPrice) {
		this.dGdPrice = dGdPrice;
	}
//	public GdInfo ent = new GdInfo();
//
//	public void Reset()
//	{
//		ent.strGdArtNO = "";
//		ent.strGdBarcode = "";
//		ent.strProperty1 = "";
//		ent.strGdCode = "";
//		ent.strGdColorID = "";
//		ent.strGdColorName = "";
//		ent.strGdName = "";
//		ent.strGdSizeID = "";
//		ent.strGdSizeName = "";
//		ent.strProperty2 = "";
//		ent.strGdStyle = "";
//		ent.dGdPrice = 0;
//		ent.dGdStock = 0;
//	}
}