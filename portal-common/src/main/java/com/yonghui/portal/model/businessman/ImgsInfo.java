package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 图片信息
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public class ImgsInfo extends AuditAuto {

    //图片名称
    private String name;
    //系统名称
    private String sysName;
    //图片格式
    private String imgType;
    //图片大小
    private String imgSize;
    //图品根目录
    private String imgPath;
    //目录ID
    private Long directoryId;

    /**
     * 设置：图片名称
     */
    public void setName(String name) {
            this.name = name;
            }
    /**
     * 获取：图片名称
     */
    public String getName() {
            return name;
            }
    /**
     * 设置：系统名称
     */
    public void setSysName(String sysName) {
            this.sysName = sysName;
            }
    /**
     * 获取：系统名称
     */
    public String getSysName() {
            return sysName;
            }
    /**
     * 设置：图片格式
     */
    public void setImgType(String imgType) {
            this.imgType = imgType;
            }
    /**
     * 获取：图片格式
     */
    public String getImgType() {
            return imgType;
            }
    /**
     * 设置：图片大小
     */
    public void setImgSize(String imgSize) {
            this.imgSize = imgSize;
            }
    /**
     * 获取：图片大小
     */
    public String getImgSize() {
            return imgSize;
            }
    /**
     * 设置：图品根目录
     */
    public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
            }
    /**
     * 获取：图品根目录
     */
    public String getImgPath() {
            return imgPath;
            }
    /**
     * 设置：目录ID
     */
    public void setDirectoryId(Long directoryId) {
            this.directoryId = directoryId;
            }
    /**
     * 获取：目录ID
     */
    public Long getDirectoryId() {
            return directoryId;
            }
}
