package com.yonghui.portal.model.app;

import java.io.Serializable;
import java.util.List;

/**
 * Author : 杨杨
 * Date : 2017/05/23
 * Description :
 */
public class AppMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    //
    private Integer menuId;
    //大分类  1生意概况 2 报表 3专题
    private String name;
    //一级分类
    private String subName1;
    //二级分类
    private String subName2;
    //标题
    private  String title;
    //关联报表ID
    private Integer kpiId;
    //报表ID
    private Integer reportId;
    //模板ID
    private Integer templateId;
    //访问链接
    private String url;
    //图标名
    private String  icon;
    //图标地址
    private String iconUrl;
    //是否公开通用（无权限设置，所有角色可看）
    private Boolean publicly;
    //是否有语音
    private Boolean   hasAudio;
    //健康值
    private Integer    healthValue;
    //创建时间
    private String createdAt;
    //更新时间
    private String updatedAt;
    //
    private Integer groupOrder;
    //
    private Integer itemOrder;
    //类型
    private Integer type;
    //备注
    private String remark;

    private Integer active;

    //子菜单列表
    private List<AppMenu> menuList;
    //拥有该菜单权限的角色列表
    private String roleIds;

    private List<Integer> roleIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName1() {
        return subName1;
    }

    public void setSubName1(String subName1) {
        this.subName1 = subName1;
    }

    public String getSubName2() {
        return subName2;
    }

    public void setSubName2(String subName2) {
        this.subName2 = subName2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getKpiId() {
        return kpiId;
    }

    public void setKpiId(Integer kpiId) {
        this.kpiId = kpiId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getPublicly() {
        return publicly;
    }

    public void setPublicly(Boolean publicly) {
        this.publicly = publicly;
    }

    public Boolean getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public Integer getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(Integer healthValue) {
        this.healthValue = healthValue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<AppMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<AppMenu> menuList) {
        this.menuList = menuList;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }
}