package com.yonghui.portal.model.platform;

import java.util.Date;

/**
 * 绿标门店
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-26 17:02:43
 */
public class BravoShop {

    //旧的店编号
    private String shopid;
    //新店编号
    private String sapShopid;
    //门店名称
    private String sname;
    //门店编码
    private Integer shopno;
    //是否开店
    private Integer congou;
    //开店日期：1、正常开业中
    private String opendate;
    //区域编码
    private Integer sno;
    //区域ID
    private String areaid;
    //区域名称
    private String areaname;
    //
    private String typeComm;
    //
    private String typename;
    //
    private String area;
    //省份
    private String province;
    //城市
    private String city;
    //店组编号
    private Integer shopgroupid;
    //店组名称
    private String shopgroupname;
    //小区域
    private String areamans;
    //更新时间
    private Date updatetime;

    /**
     * 设置：旧的店编号
     */
    public void setShopid(String shopid) {
            this.shopid = shopid;
            }
    /**
     * 获取：旧的店编号
     */
    public String getShopid() {
            return shopid;
            }
    /**
     * 设置：新店编号
     */
    public void setSapShopid(String sapShopid) {
            this.sapShopid = sapShopid;
            }
    /**
     * 获取：新店编号
     */
    public String getSapShopid() {
            return sapShopid;
            }
    /**
     * 设置：门店名称
     */
    public void setSname(String sname) {
            this.sname = sname;
            }
    /**
     * 获取：门店名称
     */
    public String getSname() {
            return sname;
            }
    /**
     * 设置：门店编码
     */
    public void setShopno(Integer shopno) {
            this.shopno = shopno;
            }
    /**
     * 获取：门店编码
     */
    public Integer getShopno() {
            return shopno;
            }
    /**
     * 设置：是否开店
     */
    public void setCongou(Integer congou) {
            this.congou = congou;
            }
    /**
     * 获取：是否开店
     */
    public Integer getCongou() {
            return congou;
            }
    /**
     * 设置：开店日期：1、正常开业中
     */
    public void setOpendate(String opendate) {
            this.opendate = opendate;
            }
    /**
     * 获取：开店日期：1、正常开业中
     */
    public String getOpendate() {
            return opendate;
            }
    /**
     * 设置：区域编码
     */
    public void setSno(Integer sno) {
            this.sno = sno;
            }
    /**
     * 获取：区域编码
     */
    public Integer getSno() {
            return sno;
            }
    /**
     * 设置：区域ID
     */
    public void setAreaid(String areaid) {
            this.areaid = areaid;
            }
    /**
     * 获取：区域ID
     */
    public String getAreaid() {
            return areaid;
            }
    /**
     * 设置：区域名称
     */
    public void setAreaname(String areaname) {
            this.areaname = areaname;
            }
    /**
     * 获取：区域名称
     */
    public String getAreaname() {
            return areaname;
            }
    /**
     * 设置：
     */
    public void setTypeComm(String typeComm) {
            this.typeComm = typeComm;
            }
    /**
     * 获取：
     */
    public String getTypeComm() {
            return typeComm;
            }
    /**
     * 设置：
     */
    public void setTypename(String typename) {
            this.typename = typename;
            }
    /**
     * 获取：
     */
    public String getTypename() {
            return typename;
            }
    /**
     * 设置：
     */
    public void setArea(String area) {
            this.area = area;
            }
    /**
     * 获取：
     */
    public String getArea() {
            return area;
            }
    /**
     * 设置：省份
     */
    public void setProvince(String province) {
            this.province = province;
            }
    /**
     * 获取：省份
     */
    public String getProvince() {
            return province;
            }
    /**
     * 设置：城市
     */
    public void setCity(String city) {
            this.city = city;
            }
    /**
     * 获取：城市
     */
    public String getCity() {
            return city;
            }
    /**
     * 设置：店组编号
     */
    public void setShopgroupid(Integer shopgroupid) {
            this.shopgroupid = shopgroupid;
            }
    /**
     * 获取：店组编号
     */
    public Integer getShopgroupid() {
            return shopgroupid;
            }
    /**
     * 设置：店组名称
     */
    public void setShopgroupname(String shopgroupname) {
            this.shopgroupname = shopgroupname;
            }
    /**
     * 获取：店组名称
     */
    public String getShopgroupname() {
            return shopgroupname;
            }
    /**
     * 设置：小区域
     */
    public void setAreamans(String areamans) {
            this.areamans = areamans;
            }
    /**
     * 获取：小区域
     */
    public String getAreamans() {
            return areamans;
            }
    /**
     * 设置：更新时间
     */
    public void setUpdatetime(Date updatetime) {
            this.updatetime = updatetime;
            }
    /**
     * 获取：更新时间
     */
    public Date getUpdatetime() {
            return updatetime;
            }
}
