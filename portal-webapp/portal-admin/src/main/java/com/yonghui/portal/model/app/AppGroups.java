package com.yonghui.portal.model.app;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public class AppGroups implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String groupName;

    private Integer createUser;

    private Integer updateUser;

    private String memo;

    private Date loadTime;

    private Date createdAt;

    private Date updatedAt;

    private String shopid;

    private String groupid;

    /**
     * 设置：
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取：
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置：
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置：
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取：
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置：
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置：
     */
    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    /**
     * 获取：
     */
    public Date getLoadTime() {
        return loadTime;
    }

    /**
     * 设置：
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取：
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置：
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取：
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置：
     */
    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    /**
     * 获取：
     */
    public String getShopid() {
        return shopid;
    }

    /**
     * 设置：
     */
    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */

    public String getGroupid() {
        return groupid;
    }
}
