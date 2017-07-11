package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;

public class Shop implements Serializable{

	private static final long serialVersionUID = 18009587126981594L;
	private String shopId;
	private String shopName;
	
	private String id;
	private String text;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
