package com.supoin.commoninventory.db.entity;

/// <summary>
// / ��Ʒɨ������б�����
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
		// TODO �Զ����ɵķ������
		if (!(o instanceof CheckListEntity))
			return false;
		CheckListEntity p = (CheckListEntity) o;
		return (this.strBar.equals(p.strBar)) && (this.dNum == p.dNum);
	}

}
