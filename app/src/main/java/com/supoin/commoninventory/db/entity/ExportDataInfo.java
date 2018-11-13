package com.supoin.commoninventory.db.entity;

public class ExportDataInfo {
	// 门店编号
	public String ShopID = "";
	// 编号
	public String CheckID = "";
	// 库位
	public String PositionID = "";
	// 条码
	public String GdBar = "";
	// 货号
	public String GdArtNO = "";
	// 款式
	public String GdStyle = "";
	// 名称
	public String GdName = "";
	// 颜色id
	public String GdColorID = "";
	// 颜色
	public String GdColorName = "";
	// 尺码id
	public String GdSizeID = "";
	// 尺码
	public String GdSizeName = "";
	// 大类
	public String Big = "";
	// 小类
	public String Small = "";
	// 库存
	public int Stock = 0;
	// 价格
	public float Price = 0;
	// 数量
	public float CheckNum = 0;
	// 时间
	public String Time = "";
	public String getShopID() {
		return ShopID;
	}
	public void setShopID(String shopID) {
		ShopID = shopID;
	}
	public String getCheckID() {
		return CheckID;
	}
	public void setCheckID(String checkID) {
		CheckID = checkID;
	}
	public String getPositionID() {
		return PositionID;
	}
	public void setPositionID(String positionID) {
		PositionID = positionID;
	}
	public String getGdBar() {
		return GdBar;
	}
	public void setGdBar(String gdBar) {
		GdBar = gdBar;
	}
	public String getGdArtNO() {
		return GdArtNO;
	}
	public void setGdArtNO(String gdArtNO) {
		GdArtNO = gdArtNO;
	}
	public String getGdStyle() {
		return GdStyle;
	}
	public void setGdStyle(String gdStyle) {
		GdStyle = gdStyle;
	}
	public String getGdName() {
		return GdName;
	}
	public void setGdName(String gdName) {
		GdName = gdName;
	}
	public String getGdColorID() {
		return GdColorID;
	}
	public void setGdColorID(String gdColorID) {
		GdColorID = gdColorID;
	}
	public String getGdColorName() {
		return GdColorName;
	}
	public void setGdColorName(String gdColorName) {
		GdColorName = gdColorName;
	}
	public String getGdSizeID() {
		return GdSizeID;
	}
	public void setGdSizeID(String gdSizeID) {
		GdSizeID = gdSizeID;
	}
	public String getGdSizeName() {
		return GdSizeName;
	}
	public void setGdSizeName(String gdSizeName) {
		GdSizeName = gdSizeName;
	}
	public String getBig() {
		return Big;
	}
	public void setBig(String big) {
		Big = big;
	}
	public String getSmall() {
		return Small;
	}
	public void setSmall(String small) {
		Small = small;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public float getCheckNum() {
		return CheckNum;
	}
	public void setCheckNum(float checkNum) {
		CheckNum = checkNum;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}

}
