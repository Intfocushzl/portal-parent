package com.yonghui.portal.model.app;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public class AppUserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    //
    private Integer userId;
    //
    private Integer roleId;
    //
    private Integer createUser;
    //
    private Integer updateUser;
    //
    private String memo;
    //
    private Date loadTime;
    //
    private Date createdAt;
    //
    private Date updatedAt;

    /**
     * 设置：
     */
    public void setUserId(Integer userId) {
            this.userId = userId;
            }
    /**
     * 获取：
     */
    public Integer getUserId() {
            return userId;
            }
    /**
     * 设置：
     */
    public void setRoleId(Integer roleId) {
            this.roleId = roleId;
            }
    /**
     * 获取：
     */
    public Integer getRoleId() {
            return roleId;
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
}
