package com.yonghui.portal.model.channelTransparency;

import java.util.Date;

public class ShopRackVO {
    private Long id;

    private String shopid;

    private String shopname;

    private String groupid;

    private String groupname;

    private String distype;

    private String imgurl;

    private String rackno;

    private String area;
    
    private int disNum;
    
    private Date updateDate;
    
    

    public int getDisNum() {
		return disNum;
	}

	public void setDisNum(int disNum) {
		this.disNum = disNum;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getDistype() {
        return distype;
    }

    public void setDistype(String distype) {
        this.distype = distype == null ? null : distype.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getRackno() {
        return rackno;
    }

    public void setRackno(String rackno) {
        this.rackno = rackno == null ? null : rackno.trim();
    }

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
    
}