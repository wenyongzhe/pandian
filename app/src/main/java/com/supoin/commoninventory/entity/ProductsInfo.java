package com.supoin.commoninventory.entity;

public class ProductsInfo {
    public String SKU;//SKU
    public String Barcode;//条码 
    public String Title;//品名
    public int  Updated;
    public String RackPlace;//货位
    public String ProductMfctCode;//货号
    public int UpdateBarcode;//
    public int  UpdatedRackPlace;
    public String SalePrice;//会员价格
    public String ListPrice;//市场价
    public String VipPrice;//vip价
    public String Status;//商品状态
    public String SKUDeleted;//商品是否有效
    public String PisAddDate;//上架时间
    public String Season;
    public String Describe;
    public String Promotion;
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getBarcode() {
		return Barcode;
	}
	public void setBarcode(String barcode) {
		Barcode = barcode;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getUpdated() {
		return Updated;
	}
	public void setUpdated(int updated) {
		Updated = updated;
	}
	public String getRackPlace() {
		return RackPlace;
	}
	public void setRackPlace(String rackPlace) {
		RackPlace = rackPlace;
	}
	public String getProductMfctCode() {
		return ProductMfctCode;
	}
	public void setProductMfctCode(String productMfctCode) {
		ProductMfctCode = productMfctCode;
	}
	public int getUpdateBarcode() {
		return UpdateBarcode;
	}
	public void setUpdateBarcode(int updateBarcode) {
		UpdateBarcode = updateBarcode;
	}
	public int getUpdatedRackPlace() {
		return UpdatedRackPlace;
	}
	public void setUpdatedRackPlace(int updatedRackPlace) {
		UpdatedRackPlace = updatedRackPlace;
	}
	public String getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(String salePrice) {
		SalePrice = salePrice;
	}
	public String getListPrice() {
		return ListPrice;
	}
	public void setListPrice(String listPrice) {
		ListPrice = listPrice;
	}
	public String getVipPrice() {
		return VipPrice;
	}
	public void setVipPrice(String vipPrice) {
		VipPrice = vipPrice;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getSKUDeleted() {
		return SKUDeleted;
	}
	public void setSKUDeleted(String sKUDeleted) {
		SKUDeleted = sKUDeleted;
	}
	public String getPisAddDate() {
		return PisAddDate;
	}
	public void setPisAddDate(String pisAddDate) {
		PisAddDate = pisAddDate;
	}
	public String getSeason() {
		return Season;
	}
	public void setSeason(String season) {
		Season = season;
	}
	public String getDescribe() {
		return Describe;
	}
	public void setDescribe(String describe) {
		Describe = describe;
	}
	public String getPromotion() {
		return Promotion;
	}
	public void setPromotion(String promotion) {
		Promotion = promotion;
	}
}
