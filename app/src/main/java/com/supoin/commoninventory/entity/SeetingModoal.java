package com.supoin.commoninventory.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SeetingModoal implements Parcelable{
	private String id;
	private String displayOption;
	private String displaySingleLine;
	
	private boolean goodNo;
	private boolean goodNoDisplay;
	//款式
	private boolean type;
	private boolean typeDisplay;
	
	private boolean name;
	private boolean nameDisplay;
	
	private boolean colorId;
	private boolean colorIdDisplay;
	
	private boolean color;
	private boolean colorDisplay;
	
	private boolean size;
	private boolean sizeDisplay;
	
	private boolean sizeId;
	private boolean sizeIdDisplay;
	
	private boolean bigSort;
	private boolean bigSortDisplay;
	
	private boolean smallSort;
	private boolean smallSortDisplay;
	
	private boolean quantity;
	private boolean quantityDisplay;
	
	private boolean price;
	private boolean priceDisplay;
	
	
	public SeetingModoal(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplayOption() {
		return displayOption;
	}
	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
	}
	public String getDisplaySingleLine() {
		return displaySingleLine;
	}
	public void setDisplaySingleLine(String displaySingleLine) {
		this.displaySingleLine = displaySingleLine;
	}
	public boolean isGoodNo() {
		return goodNo;
	}
	public void setGoodNo(boolean goodNo) {
		this.goodNo = goodNo;
		ConfigEntity.goodsKey = goodNo;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
		ConfigEntity.styleKey = type;
	}
	public boolean isName() {
		return name;
	}
	public void setName(boolean name) {
		this.name = name;
		ConfigEntity.nameKey = name;
	}
	public boolean isColorId() {
		return colorId;
	}
	public void setColorId(boolean colorId) {
		this.colorId = colorId;
		ConfigEntity.colorIdKey = colorId;
	}
	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
		ConfigEntity.colorKey = color;
	}
	public boolean isSize() {
		return size;
	}
	public void setSize(boolean size) {
		this.size = size;
		ConfigEntity.sizeKey = size;
	}
	public boolean isSizeId() {
		return sizeId;
	}
	public void setSizeId(boolean sizeId) {
		this.sizeId = sizeId;
		ConfigEntity.sizeIdKey = sizeId;
	}
	public boolean isBigSort() {
		return bigSort;
	}
	public void setBigSort(boolean bigSort) {
		this.bigSort = bigSort;
		ConfigEntity.bigSortKey = bigSort;
	}
	public boolean isSmallSort() {
		return smallSort;
	}
	public void setSmallSort(boolean smallSort) {
		this.smallSort = smallSort;
		ConfigEntity.smallSortKey = smallSort;
	}
	public boolean isQuantity() {
		return quantity;
	}
	public void setQuantity(boolean quantity) {
		this.quantity = quantity;
		ConfigEntity.quantityKey = quantity;
	}
	public boolean isPrice() {
		return price;
	}
	public void setPrice(boolean price) {
		this.price = price;
		ConfigEntity.priceKey = price;
	}
	
	
	
	//设置是否单行显示
	public boolean isGoodNoDisplay() {
		return goodNoDisplay;
	}
	public void setGoodNoDisplay(boolean goodNoDisplay) {
		this.goodNoDisplay = goodNoDisplay;
		ConfigEntity.goodsDisPlayOnlineKey = goodNoDisplay;
	}
	public boolean isTypeDisplay() {
		return typeDisplay;
	}
	public void setTypeDisplay(boolean typeDisplay) {
		this.typeDisplay = typeDisplay;
		ConfigEntity.styleDisPlayOnlineKey = typeDisplay;
	}
	public boolean isNameDisplay() {
		return nameDisplay;
	}
	public void setNameDisplay(boolean nameDisplay) {
		this.nameDisplay = nameDisplay;
		ConfigEntity.nameDisPlayOnlineKey = nameDisplay;
	}
	public boolean isColorIdDisplay() {
		return colorIdDisplay;
	}
	public void setColorIdDisplay(boolean colorIdDisplay) {
		this.colorIdDisplay = colorIdDisplay;
		ConfigEntity.colorIdDisPlayOnlineKey = colorIdDisplay;
	}
	public boolean isColorDisplay() {
		return colorDisplay;
	}
	public void setColorDisplay(boolean colorDisplay) {
		this.colorDisplay = colorDisplay;
		ConfigEntity.colorDisPlayOnlineKey = colorDisplay;
	}
	public boolean isSizeDisplay() {
		return sizeDisplay;
	}
	public void setSizeDisplay(boolean sizeDisplay) {
		this.sizeDisplay = sizeDisplay;
		ConfigEntity.sizeDisPlayOnlineKey = sizeDisplay;
	}
	public boolean isSizeIdDisplay() {
		return sizeIdDisplay;
	}
	public void setSizeIdDisplay(boolean sizeIdDisplay) {
		this.sizeIdDisplay = sizeIdDisplay;
		ConfigEntity.sizeIdDisPlayOnlineKey = sizeIdDisplay;
	}
	public boolean isBigSortDisplay() {
		return bigSortDisplay;
	}
	public void setBigSortDisplay(boolean bigSortDisplay) {
		this.bigSortDisplay = bigSortDisplay;
		ConfigEntity.bigSortDisPlayOnlineKey = bigSortDisplay;
	}
	public boolean isSmallSortDisplay() {
		return smallSortDisplay;
	}
	public void setSmallSortDisplay(boolean smallSortDisplay) {
		this.smallSortDisplay = smallSortDisplay;
		ConfigEntity.smallSortDisPlayOnlineKey = smallSortDisplay;
	}
	public boolean isQuantityDisplay() {
		return quantityDisplay;
	}
	public void setQuantityDisplay(boolean quantityDisplay) {
		this.quantityDisplay = quantityDisplay;
		ConfigEntity.quantityDisPlayOnlineKey = quantityDisplay;
	}
	public boolean isPriceDisplay() {
		return priceDisplay;
	}
	public void setPriceDisplay(boolean priceDisplay) {
		this.priceDisplay = priceDisplay;
		ConfigEntity.priceDisPlayOnlineKey = priceDisplay;
	}
	
	@Override
	public String toString() {
		return "SeetingModoal [id=" + id + ", displayOption=" + displayOption
				+ ", displaySingleLine=" + displaySingleLine + ", goodNo="
				+ goodNo + ", goodNoDisplay=" + goodNoDisplay + ", type="
				+ type + ", typeDisplay=" + typeDisplay + ", name=" + name
				+ ", nameDisplay=" + nameDisplay + ", colorId=" + colorId
				+ ", colorIdDisplay=" + colorIdDisplay + ", color=" + color
				+ ", colorDisplay=" + colorDisplay + ", size=" + size
				+ ", sizeDisplay=" + sizeDisplay + ", sizeId=" + sizeId
				+ ", sizeIdDisplay=" + sizeIdDisplay + ", bigSort=" + bigSort
				+ ", bigSortDisplay=" + bigSortDisplay + ", smallSort="
				+ smallSort + ", smallSortDisplay=" + smallSortDisplay
				+ ", quantity=" + quantity + ", quantityDisplay="
				+ quantityDisplay + ", price=" + price + ", priceDisplay="
				+ priceDisplay + "]";
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO 自动生成的方法存根
		dest.writeByte((byte)(goodNo ?1:0));
		dest.writeByte((byte)(goodNoDisplay ?1:0));
		dest.writeByte((byte)(type ?1:0));
		dest.writeByte((byte)(typeDisplay ?1:0));
		dest.writeByte((byte)(name ?1:0));
		dest.writeByte((byte)(nameDisplay ?1:0));
		dest.writeByte((byte)(color ?1:0));
		dest.writeByte((byte)(colorDisplay ?1:0));
		dest.writeByte((byte)(colorId ?1:0));
		dest.writeByte((byte)(colorIdDisplay ?1:0));
		dest.writeByte((byte)(size ?1:0));
		dest.writeByte((byte)(sizeDisplay ?1:0));
		dest.writeByte((byte)(sizeId ?1:0));
		dest.writeByte((byte)(sizeIdDisplay ?1:0));
		dest.writeByte((byte)(bigSort ?1:0));
		dest.writeByte((byte)(bigSortDisplay ?1:0));
		dest.writeByte((byte)(smallSort ?1:0));
		dest.writeByte((byte)(smallSortDisplay ?1:0));
		dest.writeByte((byte)(quantity ?1:0));
		dest.writeByte((byte)(quantityDisplay ?1:0));
		dest.writeByte((byte)(price ?1:0));
		dest.writeByte((byte)(priceDisplay ?1:0));
	}

	public static final Parcelable.Creator<SeetingModoal> CREATOR = new Parcelable.Creator<SeetingModoal>() 
		     
		{
	         public SeetingModoal createFromParcel(Parcel in) 
	         {
	             return new SeetingModoal(in);
	         }

	         public SeetingModoal[] newArray(int size) 
	         {
	             return new SeetingModoal[size];
	         }
	     };
		     
	     public SeetingModoal(Parcel in) 
	     {
	    	 goodNo =in.readByte()!=0;
	    	 goodNoDisplay =in.readByte()!=0;
	    	 type = in.readByte()!=0;
	    	 typeDisplay = in.readByte()!=0;
	    	 name = in.readByte()!=0;
	    	 nameDisplay = in.readByte()!=0;
	    	 color = in.readByte()!=0;
	    	 colorDisplay = in.readByte()!=0;
	    	 colorId = in.readByte()!=0;
	    	 colorIdDisplay = in.readByte()!=0;
	    	 size = in.readByte()!=0;
	    	 sizeDisplay = in.readByte()!=0;
	    	 sizeId = in.readByte()!=0;
	    	 sizeIdDisplay = in.readByte()!=0;
	    	 bigSort = in.readByte()!=0;
	    	 bigSortDisplay = in.readByte()!=0;
	    	 smallSort = in.readByte()!=0;
	    	 smallSortDisplay = in.readByte()!=0;
	    	 quantity = in.readByte()!=0;
	    	 quantityDisplay = in.readByte()!=0;
	    	 price = in.readByte()!=0;
	    	 priceDisplay = in.readByte()!=0;
	     }
	
}
