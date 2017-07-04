package com.yonghui.portal.model.channelTransparency;

import java.util.Date;

public class ShopDisVO {
	private Integer id;
	private String shopid;
	private String shopname;
	private String dateno;
	private String groupid;
	private String groupname;
	private String rackno;
	private String remark;
	private Date updatedate;
	private String imgurl;

	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getDateno() {
		return dateno;
	}

	public void setDateno(String dateno) {
		this.dateno = dateno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getRackno() {
		return rackno;
	}

	public void setRackno(String rackno) {
		this.rackno = rackno;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
