package com.yonghui.portal.model.table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**表头MODEL
 * @author Shengwm
 * @date 2017/05/23
 */
public class TableTitle implements Serializable {
    private Long id;

    private String reportcode;

    private String parentid;

    private String isleaf;

    private String treecode;

    private Integer sortid;

    private Integer measureid;

    private String measurename;

    private String measurelab;

    private String themename;

    private String unittype;

    private Date updatetime;

    private String remark;

    private Long creater;

    private Date createTime;

    private Long modifier;

    private Date modifyTime;

    private Long disabled;

    private Long version;

    private List<TableTitle> children;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportcode() {
        return reportcode;
    }

    public void setReportcode(String reportcode) {
        this.reportcode = reportcode == null ? null : reportcode.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf == null ? null : isleaf.trim();
    }

    public String getTreecode() {
        return treecode;
    }

    public void setTreecode(String treecode) {
        this.treecode = treecode == null ? null : treecode.trim();
    }

    public Integer getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }

    public Integer getMeasureid() {
        return measureid;
    }

    public void setMeasureid(Integer measureid) {
        this.measureid = measureid;
    }

    public String getMeasurename() {
        return measurename;
    }

    public void setMeasurename(String measurename) {
        this.measurename = measurename == null ? null : measurename.trim();
    }

    public String getMeasurelab() {
        return measurelab;
    }

    public void setMeasurelab(String measurelab) {
        this.measurelab = measurelab == null ? null : measurelab.trim();
    }

    public String getThemename() {
        return themename;
    }

    public void setThemename(String themename) {
        this.themename = themename == null ? null : themename.trim();
    }

    public String getUnittype() {
        return unittype;
    }

    public void setUnittype(String unittype) {
        this.unittype = unittype == null ? null : unittype.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getDisabled() {
        return disabled;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<TableTitle> getChildren() {
        return children;
    }

    public void setChildren(List<TableTitle> children) {
        this.children = children;
    }
}