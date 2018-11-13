package com.supoin.commoninventory.db.entity;

/// <summary>
// / 商品扫描界面列表内容
// / </summary>
public class CheckListEntity {
	public String strBar = "";
	public int dNum = 0;

	public String getStrBar() {
		return strBar;
	}

	public void setStrBar(String strBar) {
		this.strBar = strBar;
	}

	public float getdNum() {
		return dNum;
	}

	public void setdNum(int dNum) {
		this.dNum = dNum;
	}

	@Override
	public boolean equals(Object o) {
		// TODO 自动生成的方法存根
		if (!(o instanceof CheckListEntity))
			return false;
		CheckListEntity p = (CheckListEntity) o;
		return (this.strBar.equals(p.strBar)) && (this.dNum == p.dNum);
	}

}
