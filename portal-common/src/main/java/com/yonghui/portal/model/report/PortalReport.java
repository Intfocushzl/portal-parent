package com.yonghui.portal.model.report;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 报表信息
 * Created by 张海 on 2017/05/11
 */
public class PortalReport extends AuditAuto {

    private Long id;
    // 唯一编码
    private String code;
    // 更新前的编码（标记redis）
    private String codeOld;
    // 标题
    private String title;
    // 执行类型 1,存储过程，2，select查询
    private int executeType;
    // 绑定的存储过程唯一编码
    private String executeCode;
    //标题列数据(表格最后一行数据)
    private String reportHeadersConsole;
    //标题列格式化数据
    private String reportHeadersFormatConsole;
    //表格数据
    private String reportHotData;
    //表格合并单元格数据
    private String reportMergedCellInfoCollection;
    //表格HTML
    private String reportOuterHtml;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getExecuteType() {
        return executeType;
    }

    public void setExecuteType(int executeType) {
        this.executeType = executeType;
    }

    public String getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(String executeCode) {
        this.executeCode = executeCode;
    }

    public String getCodeOld() {
        return codeOld;
    }

    public void setCodeOld(String codeOld) {
        this.codeOld = codeOld;
    }

    /**
     * 设置：标题列数据(表格最后一行数据)
     */
    public void setReportHeadersConsole(String reportHeadersConsole) {
        this.reportHeadersConsole = reportHeadersConsole;
    }

    /**
     * 获取：标题列数据(表格最后一行数据)
     */
    public String getReportHeadersConsole() {
        return reportHeadersConsole;
    }

    /**
     * 设置：标题列格式化数据
     */
    public void setReportHeadersFormatConsole(String reportHeadersFormatConsole) {
        this.reportHeadersFormatConsole = reportHeadersFormatConsole;
    }

    /**
     * 获取：标题列格式化数据
     */
    public String getReportHeadersFormatConsole() {
        return reportHeadersFormatConsole;
    }

    /**
     * 设置：表格数据
     */
    public void setReportHotData(String reportHotData) {
        this.reportHotData = reportHotData;
    }

    /**
     * 获取：表格数据
     */
    public String getReportHotData() {
        return reportHotData;
    }

    /**
     * 设置：表格合并单元格数据
     */
    public void setReportMergedCellInfoCollection(String reportMergedCellInfoCollection) {
        this.reportMergedCellInfoCollection = reportMergedCellInfoCollection;
    }

    /**
     * 获取：表格合并单元格数据
     */
    public String getReportMergedCellInfoCollection() {
        return reportMergedCellInfoCollection;
    }

    /**
     * 设置：表格HTML
     */
    public void setReportOuterHtml(String reportOuterHtml) {
        this.reportOuterHtml = reportOuterHtml;
    }

    /**
     * 获取：表格HTML
     */
    public String getReportOuterHtml() {
        return reportOuterHtml;
    }
}
