package com.yonghui.portal.model.api;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 报表信息
 * Created by 张海 on 2017/05/11
 */
public class PortalReport extends AuditAuto {

    private Long id;
    // 唯一编码
    private String code;
    // 标题
    private String title;
    // 执行类型 1,存储过程，2，select查询
    private int executeType;
    // 绑定的存储过程唯一编码
    private String executeCode;

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
}
