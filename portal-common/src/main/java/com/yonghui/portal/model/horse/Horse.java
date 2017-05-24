package com.yonghui.portal.model.horse;

import java.io.Serializable;

public class Horse implements Serializable{

	private static final long serialVersionUID = 1L;

	private int lkpDate;
	private int gid;
	private String shopId;
	private String shopName;
	private int groupId;
	
	public int getLkpDate() {
		return lkpDate;
	}
	public void setLkpDate(int lkpDate) {
		this.lkpDate = lkpDate;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
}
