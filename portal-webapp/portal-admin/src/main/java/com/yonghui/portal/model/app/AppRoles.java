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
public class AppRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    //角色id
    private Integer id;
    //角色名称
    private String roleName;
    //创建人
    private Integer createUser;
    //修改人
    private Integer updateUser;
    //
    private String memo;
    //
    private Date loadTime;
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;
     
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    /**
     * 设置：
     */
    public void setRoleName(String roleName) {
            this.roleName = roleName;
            }
    /**
     * 获取：
     */
    public String getRoleName() {
            return roleName;
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
