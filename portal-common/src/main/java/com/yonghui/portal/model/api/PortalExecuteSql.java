package com.yonghui.portal.model.api;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 执行的sql语句
 * Created by 张海 on 2017/05/13
 */
public class PortalExecuteSql extends AuditAuto {

    // 存储过程标题
    private String title;
    // sql语句唯一编码
    private String sqlcode;
    // 执行语句
    private String executeSql;
    // JDBC数据源编码
    private String dataSourceCode;
    // 参数 @@分割 如 a@@b@@c
    private String parameter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSqlcode() {
        return sqlcode;
    }

    public void setSqlcode(String sqlcode) {
        this.sqlcode = sqlcode;
    }

    public String getDataSourceCode() {
        return dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getExecuteSql() {
        return executeSql;
    }

    public void setExecuteSql(String executeSql) {
        this.executeSql = executeSql;
    }
}
