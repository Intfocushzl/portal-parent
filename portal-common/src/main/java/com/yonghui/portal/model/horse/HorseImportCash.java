package com.yonghui.portal.model.horse;

public class HorseImportCash implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	// 月份
	private String sdate;

	// 店编
	private String sapshopid;

	// 小店id
	private Integer groupid;

	// 收银出错次数
	private Double wrong;

	// 收银人数
	private Double number;

	// 行号
	private Integer rowNum;

	// 确认状态
	private Integer flag;

	// 确认帐号
	private String account;

	// 更新时间
	private String updatetime;
	
	private String areamans;
	
	private String shopname;
	
	private String groupname;

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getSapshopid() {
		return sapshopid;
	}

	public void setSapshopid(String sapshopid) {
		this.sapshopid = sapshopid;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public Double getWrong() {
		return wrong;
	}

	public void setWrong(Double wrong) {
		this.wrong = wrong;
	}

	public Double getNumber() {
		return number;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}


	public String getAreamans() {
		return areamans;
	}

	public void setAreamans(String areamans) {
		this.areamans = areamans;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

}
