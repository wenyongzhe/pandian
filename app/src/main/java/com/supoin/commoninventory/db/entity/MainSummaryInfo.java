package com.supoin.commoninventory.db.entity;

public class MainSummaryInfo {
	public String CheckID; // �̵���
	public String PositionID; // ��λ���
	public float Qty;// ����

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

