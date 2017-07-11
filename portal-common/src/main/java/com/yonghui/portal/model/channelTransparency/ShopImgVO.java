package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;
import java.util.Date;

public class ShopImgVO implements Serializable {


	private static final long serialVersionUID = -3054937546024831815L;
	private Integer id;
	private String shopid;
	private String imgurl;
	private String title;
	private Date updatedate;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
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

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
