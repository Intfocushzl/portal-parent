package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;
import java.util.Date;

public class ShopDisVO implements Serializable {

	private static final long serialVersionUID = -7393474936118727085L;
	private Integer id;
	private String shopid;//店铺ID
	private String shopname;//店铺名称
	private String dateno;//档期
	private String groupid;//商行ID
	private String groupname;//商行名称
	private String rackno;//陈列位
	private String remark;// 备注
	private Date updatedate;//更新时间
	private String imgurl;//图片链接

	
	
	
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
