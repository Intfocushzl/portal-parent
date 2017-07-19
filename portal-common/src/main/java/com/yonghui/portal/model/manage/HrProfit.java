package com.yonghui.portal.model.manage;

public class HrProfit implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String lkpMonth;

    private String shopid;

    private String rtshopid;

    private Float adj_profit;

    private String remark;

    private String creater;

    private String shopname;

    public String getLkpMonth() {
        return lkpMonth;
    }

    public void setLkpMonth(String lkpMonth) {
        this.lkpMonth = lkpMonth;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getRtshopid() {
        return rtshopid;
    }

    public void setRtshopid(String rtshopid) {
        this.rtshopid = rtshopid;
    }

    public Float getAdj_profit() {
        return adj_profit;
    }

    public void setAdj_profit(Float adj_profit) {
        this.adj_profit = adj_profit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}
