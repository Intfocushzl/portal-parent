package com.yonghui.portal.model.channelTransparency;

import java.io.Serializable;
import java.util.Date;

public class ShopPlanVO implements Serializable{

    private static final long serialVersionUID = -1161395681992776283L;
    private String shopid;//门店ID

    private String url;//链接地址

    private Date updatedate;//更新时间

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid == null ? null : shopid.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}