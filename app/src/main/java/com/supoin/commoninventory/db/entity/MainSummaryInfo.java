package com.supoin.commoninventory.db.entity;

public class MainSummaryInfo {
	public String CheckID; // 盘点编号
	public String PositionID; // 库位编号
	public float Qty;// 数量

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

	public float getQty() {
		return Qty;
	}

	public void setQty(float qty) {
		Qty = qty;
	}

}

