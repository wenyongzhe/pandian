package com.supoin.commoninventory.entity;
public class User{
	/// <summary>
	/// 
	/// </summary>
	public String strUserNO="";

	/// <summary>
	/// 
	/// </summary>
	public String strUserName="";

	/// <summary>
	/// 
	/// </summary>
	public String strPassword="";

	/// <summary>
	/// 密码提示
	/// </summary>
	public String strPwdPrompt="";
	
	/// <summary>
	/// 用户等级暂处理为了0,1，2三个等级，其中0为默认的用户等级，1为控制盘点程序使用权限等级，2暂未使用
	/// </summary>
	public int iUserLevel=0;
	public User(){
		
	}
	public String getStrUserNO() {
		return strUserNO;
	}
	public void setStrUserNO(String strUserNO) {
		this.strUserNO = strUserNO;
	}
	public String getStrUserName() {
		return strUserName;
	}
	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getStrPwdPrompt() {
		return strPwdPrompt;
	}
	public void setStrPwdPrompt(String strPwdPrompt) {
		this.strPwdPrompt = strPwdPrompt;
	}
	public int getiUserLevel() {
		return iUserLevel;
	}
	public void setiUserLevel(int iUserLevel) {
		this.iUserLevel = iUserLevel;
	}
	
}