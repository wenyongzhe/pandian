package com.supoin.commoninventory.entity;
public class STROrder{
    public int iOriIndex;
    public int iDestIndex;
    public String strOriName;
    public STROrder(int iOriIndex,String strOriName,int iDestIndex){
        this.iOriIndex = iOriIndex;
        this.strOriName = strOriName;
        this.iDestIndex = iDestIndex;
    }


    public int getiOriIndex() {
		return iOriIndex;
	}


	public void setiOriIndex(int iOriIndex) {
		this.iOriIndex = iOriIndex;
	}


	public int getiDestIndex() {
		return iDestIndex;
	}


	public void setiDestIndex(int iDestIndex) {
		this.iDestIndex = iDestIndex;
	}


	public String getStrOriName() {
		return strOriName;
	}


	public void setStrOriName(String strOriName) {
		this.strOriName = strOriName;
	}
}