package com.yonghui.portal.model.common;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 门店地址
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-07-17 19:56:17
 */
public class DBravoShopAddress extends AuditAuto {

    //门店编码
    private String shopid;
    //地址
    private String address;
    //经度
    private String longitude;
    //维度
    private String latitude;

    /**
     * 设置：门店编码
     */
    public void setShopid(String shopid) {
            this.shopid = shopid;
            }
    /**
     * 获取：门店编码
     */
    public String getShopid() {
            return shopid;
            }
    /**
     * 设置：地址
     */
    public void setAddress(String address) {
            this.address = address;
            }
    /**
     * 获取：地址
     */
    public String getAddress() {
            return address;
            }
    /**
     * 设置：经度
     */
    public void setLongitude(String longitude) {
            this.longitude = longitude;
            }
    /**
     * 获取：经度
     */
    public String getLongitude() {
            return longitude;
            }
    /**
     * 设置：维度
     */
    public void setLatitude(String latitude) {
            this.latitude = latitude;
            }
    /**
     * 获取：维度
     */
    public String getLatitude() {
            return latitude;
            }
}
