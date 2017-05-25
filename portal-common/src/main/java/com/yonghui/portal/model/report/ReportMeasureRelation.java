package com.yonghui.portal.model.report;

import com.yonghui.portal.model.base.AuditAuto;
import com.yonghui.portal.util.report.columns.Children;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-22 16:17:07
 */
public class ReportMeasureRelation extends AuditAuto {

    //报表唯一编码
    private String reportcode;
    //父节点编号
    private Long parentid;
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
    //标题每行列数
    private int childrenCount = 0;
    //节点层次（标题行数）
    private int lineCount = 1;

    /**
     * 孩子节点列表
     */
    private Children children = new Children();

    // 先序遍历，拼接JSON字符串
    public String toString() {
        String result = "";
        if (getId() == 0 || getParentid() == -1) {
            // 根节点
            result = "{"
                    + "content : '报表标题'"
                    + ", childrenCount : '" + getChildrenCount() + "'";
        } else {
            // 标题
            result = "{"
                    + "id : '" + getId() + "'"
                    + ", parentid : '" + getParentid() + "'"
                    + ", treecode : '" + getTreecode() + "'"
                    + ", sortid : '" + getSortid() + "'"
                    + ", measurelab : '" + getMeasurelab() + "'"
                    + ", measurename : '" + getMeasurename() + "'"
                    + ", childrenCount : '" + getChildrenCount() + "'";
        }
        if (children != null && children.getSize() != 0) {
            result += ", isleaf : false";
            result += ", children : " + children.toString();
        } else {
            result += ", isleaf : true";
        }

        return result + "}";
    }

    /**
     * 兄弟节点横向排序
     */
    public void sortChildren() {
        if (children != null && children.getSize() != 0) {
            // 排序,同时保存最大节点层次数
            setLineCount(children.sortChildren(getTreecode().split(".").length));
        }
    }

    /**
     * 添加孩子节点
     *
     * @param node
     */
    public void addChild(ReportMeasureRelation node) {
        this.children.addChild(node);
    }

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
    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取：父id
     */
    public Long getParentid() {
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

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}
