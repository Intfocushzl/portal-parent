package com.yonghui.portal.model.horse;

public class HorseOperateScore implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    // 月份
    private String sdate;

    // 店编
    private String sapshopid;

    // 小店id
    private Integer groupid;

    // 营运标准得分
    private Double thevalue;

    // 确认状态
    private Integer flag;

    // 确认帐号
    private String account;

    // 更新时间
    private String updatetime;

    // 行号
    private Integer rowNum;

    private String areamans;

    private String shopname;

    private String groupname;

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Double getThevalue() {
        return thevalue;
    }

    public void setThevalue(Double thevalue) {
        this.thevalue = thevalue;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

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
