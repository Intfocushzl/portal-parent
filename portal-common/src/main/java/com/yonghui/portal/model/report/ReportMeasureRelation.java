package com.yonghui.portal.model.report;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-22 16:17:07
 */
public class ReportMeasureRelation extends AuditAuto {

    //报表唯一编码
    private String reportcode;
    //父id
    private String parentid;
    //是否子节点，0不是，1是
    private String isleaf;
    //id层级
    private String treecode;
    //排序
    private Integer sortid;
    //指标ID
    private Integer measureid;
    //指标名称
    private String measurename;
    //
    private String measurelab;
    //平台模块
    private String themename;
    //
    private String unittype;

    /**
     * 设置：报表唯一编码
     */
    public void setReportcode(String reportcode) {
        this.reportcode = reportcode;
    }

    /**
     * 获取：报表唯一编码
     */
    public String getReportcode() {
        return reportcode;
    }

    /**
     * 设置：父id
     */
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取：父id
     */
    public String getParentid() {
        return parentid;
    }

    /**
     * 设置：是否子节点，0不是，1是
     */
    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    /**
     * 获取：是否子节点，0不是，1是
     */
    public String getIsleaf() {
        return isleaf;
    }

    /**
     * 设置：id层级
     */
    public void setTreecode(String treecode) {
        this.treecode = treecode;
    }

    /**
     * 获取：id层级
     */
    public String getTreecode() {
        return treecode;
    }

    /**
     * 设置：排序
     */
    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    /**
     * 获取：排序
     */
    public Integer getSortid() {
        return sortid;
    }

    /**
     * 设置：指标ID
     */
    public void setMeasureid(Integer measureid) {
        this.measureid = measureid;
    }

    /**
     * 获取：指标ID
     */
    public Integer getMeasureid() {
        return measureid;
    }

    /**
     * 设置：指标名称
     */
    public void setMeasurename(String measurename) {
        this.measurename = measurename;
    }

    /**
     * 获取：指标名称
     */
    public String getMeasurename() {
        return measurename;
    }

    /**
     * 设置：
     */
    public void setMeasurelab(String measurelab) {
        this.measurelab = measurelab;
    }

    /**
     * 获取：
     */
    public String getMeasurelab() {
        return measurelab;
    }

    /**
     * 设置：平台模块
     */
    public void setThemename(String themename) {
        this.themename = themename;
    }

    /**
     * 获取：平台模块
     */
    public String getThemename() {
        return themename;
    }

    /**
     * 设置：
     */
    public void setUnittype(String unittype) {
        this.unittype = unittype;
    }

    /**
     * 获取：
     */
    public String getUnittype() {
        return unittype;
    }

}
