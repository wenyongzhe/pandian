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
	/// ������ʾ
	/// </summary>
	public String strPwdPrompt="";
	
	/// <summary>
	/// �û��ȼ��ݴ���Ϊ��0,1��2�����ȼ�������0ΪĬ�ϵ��û��ȼ���1Ϊ�����̵����ʹ��Ȩ�޵ȼ���2��δʹ��
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