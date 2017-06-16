package com.yonghui.portal.model.cindex;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:08:02
 */
public class CIndexAperture extends AuditAuto {

    //一级流程
    private String fprocess;
    //二级流程
    private String sprocess;
    //主题
    private String theme;
    //字段名
    private String fieldname;
    //指标名称
    private String indexname;
    //一集群定义
    private String fristdef;
    //二集群定义
    private String bravodef;
    //其他平台定义
    private String otherdef;
    //衡量内容
    private String measure;
    //数据源
    private String ds;
    //表名
    private String tablename;
    //负责人
    private String cman;
    //负责人电话
    private String cmanphone;
    //负责人邮箱
    private String cmanemail;
    //平台
    private String platform;
    //连接信息
    private String connectioninfo;
    //数据库
    private String database;
    //sql语法
    private String sql;
    //创建人
    private String createdBy;
    //关联指标
    private String cindexname;
    //关联表
    private String ctablename;
    //血缘分析
    private String bloodrelation;

    /**
     * 设置：一级流程
     */
    public void setFprocess(String fprocess) {
        this.fprocess = fprocess;
    }

    /**
     * 获取：一级流程
     */
    public String getFprocess() {
        return fprocess;
    }

    /**
     * 设置：二级流程
     */
    public void setSprocess(String sprocess) {
        this.sprocess = sprocess;
    }

    /**
     * 获取：二级流程
     */
    public String getSprocess() {
        return sprocess;
    }

    /**
     * 设置：主题
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * 获取：主题
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 设置：字段名
     */
    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    /**
     * 获取：字段名
     */
    public String getFieldname() {
        return fieldname;
    }

    /**
     * 设置：指标名称
     */
    public void setIndexname(String indexname) {
        this.indexname = indexname;
    }

    /**
     * 获取：指标名称
     */
    public String getIndexname() {
        return indexname;
    }

    /**
     * 设置：一集群定义
     */
    public void setFristdef(String fristdef) {
        this.fristdef = fristdef;
    }

    /**
     * 获取：一集群定义
     */
    public String getFristdef() {
        return fristdef;
    }

    /**
     * 设置：二集群定义
     */
    public void setBravodef(String bravodef) {
        this.bravodef = bravodef;
    }

    /**
     * 获取：二集群定义
     */
    public String getBravodef() {
        return bravodef;
    }

    /**
     * 设置：其他平台定义
     */
    public void setOtherdef(String otherdef) {
        this.otherdef = otherdef;
    }

    /**
     * 获取：其他平台定义
     */
    public String getOtherdef() {
        return otherdef;
    }

    /**
     * 设置：衡量内容
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * 获取：衡量内容
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * 设置：数据源
     */
    public void setDs(String ds) {
        this.ds = ds;
    }

    /**
     * 获取：数据源
     */
    public String getDs() {
        return ds;
    }

    /**
     * 设置：表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * 获取：表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 设置：负责人
     */
    public void setCman(String cman) {
        this.cman = cman;
    }

    /**
     * 获取：负责人
     */
    public String getCman() {
        return cman;
    }

    /**
     * 设置：负责人电话
     */
    public void setCmanphone(String cmanphone) {
        this.cmanphone = cmanphone;
    }

    /**
     * 获取：负责人电话
     */
    public String getCmanphone() {
        return cmanphone;
    }

    /**
     * 设置：负责人邮箱
     */
    public void setCmanemail(String cmanemail) {
        this.cmanemail = cmanemail;
    }

    /**
     * 获取：负责人邮箱
     */
    public String getCmanemail() {
        return cmanemail;
    }

    /**
     * 设置：平台
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取：平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置：连接信息
     */
    public void setConnectioninfo(String connectioninfo) {
        this.connectioninfo = connectioninfo;
    }

    /**
     * 获取：连接信息
     */
    public String getConnectioninfo() {
        return connectioninfo;
    }

    /**
     * 设置：数据库
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * 获取：数据库
     */
    public String getDatabase() {
        return database;
    }

    /**
     * 设置：sql语法
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获取：sql语法
     */
    public String getSql() {
        return sql;
    }

    /**
     * 设置：创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取：创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置：关联指标
     */
    public void setCindexname(String cindexname) {
        this.cindexname = cindexname;
    }

    /**
     * 获取：关联指标
     */
    public String getCindexname() {
        return cindexname;
    }

    /**
     * 设置：关联表
     */
    public void setCtablename(String ctablename) {
        this.ctablename = ctablename;
    }

    /**
     * 获取：关联表
     */
    public String getCtablename() {
        return ctablename;
    }

    /**
     * 设置：血缘分析
     */
    public void setBloodrelation(String bloodrelation) {
        this.bloodrelation = bloodrelation;
    }

    /**
     * 获取：血缘分析
     */
    public String getBloodrelation() {
        return bloodrelation;
    }
}
