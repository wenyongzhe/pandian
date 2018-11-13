package com.supoin.commoninventory.entity;

public class VerifyWay{
	public int iInUsage = 0;
	public int iItem = 0;
	public String strValue = "";
	public String strDescription = "";
	
	public VerifyWay(){
		
	}

	public int getiInUsage() {
		return iInUsage;
	}

	public void setiInUsage(int iInUsage) {
		this.iInUsage = iInUsage;
	}

	public int getiItem() {
		return iItem;
	}

	public void setiItem(int iItem) {
		this.iItem = iItem;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	
	
}