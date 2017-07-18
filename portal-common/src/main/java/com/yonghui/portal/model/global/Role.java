package com.yonghui.portal.model.global;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色表
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-15 20:03:17
 */
public class Role implements Serializable {

    private Integer id;
    //角色唯一编码
    private Integer roleId;
    //角色名称
    private String name;
    //状态 0冻结，1激活
    private Integer status;
    //创建时间
    private Date createTime;
    //
    private Date createdAt;
    //
    private Date updatedAt;
    //0-平台，1-bravo，2-会员店，3-其他
    private Integer type;
    //1-人事2-财务3-招商4-其他
    private Integer ownerType;

    private Integer active;

    /**
     * 创建者ID
     */
    private Integer createUserId;

    private List<Integer> menuIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 设置：角色名称
     */
    public void setName(String name) {
            this.name = name;
            }
    /**
     * 获取：角色名称
     */
    public String getName() {
            return name;
            }
    /**
     * 设置：状态 0冻结，1激活
     */
    public void setStatus(Integer status) {
            this.status = status;
            }
    /**
     * 获取：状态 0冻结，1激活
     */
    public Integer getStatus() {
            return status;
            }
    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
            this.createTime = createTime;
            }
    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
            return createTime;
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
     * 设置：0-平台，1-bravo，2-会员店，3-其他
     */
    public void setType(Integer type) {
            this.type = type;
            }
    /**
     * 获取：0-平台，1-bravo，2-会员店，3-其他
     */
    public Integer getType() {
            return type;
            }
    /**
     * 设置：1-人事2-财务3-招商4-其他
     */
    public void setOwnerType(Integer ownerType) {
            this.ownerType = ownerType;
            }
    /**
     * 获取：1-人事2-财务3-招商4-其他
     */
    public Integer getOwnerType() {
            return ownerType;
            }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
