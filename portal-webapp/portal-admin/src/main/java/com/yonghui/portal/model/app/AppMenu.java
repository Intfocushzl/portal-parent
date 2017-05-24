package com.yonghui.portal.model.app;

import java.io.Serializable;
import java.util.Date;

/**
 * Author : 杨杨
 * Date : 2017/05/23
 * Description :
 */
public class AppMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //
    private Long menuId;
    //
    private String name;
    //
    private String subName;

    private String url;
    //
    private String ownerDept;
    //
    private String computerDept;
    //
    private String link;


    private String unit;
    //
    private String roleIds;
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
    //
    private Integer groupOrder;
    //
    private Integer itemOrder;
    //
    private Date audioUpdatedAt;

    private Integer type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getOwnerDept() {
        return ownerDept;
    }

    public void setOwnerDept(String ownerDept) {
        this.ownerDept = ownerDept;
    }

    public String getComputerDept() {
        return computerDept;
    }

    public void setComputerDept(String computerDept) {
        this.computerDept = computerDept;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Date getAudioUpdatedAt() {
        return audioUpdatedAt;
    }

    public void setAudioUpdatedAt(Date audioUpdatedAt) {
        this.audioUpdatedAt = audioUpdatedAt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}