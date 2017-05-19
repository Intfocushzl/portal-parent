package com.yonghui.portal.model.api;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 数据源
 * Created by 张海 on 2017/05/12
 */
public class PortalDataSource extends AuditAuto {

    // 标题
    private String title;
    // 唯一编码
    private String code;
    // 连接类型 数据库驱动程序  com.mysql.jdbc.Driver
    private String jdbcDriver;
    // 连接地址 jdbc:mysql://localhost:3306/test
    // jdbc:mysql://10.67.241.242:3306/bamboo-yonghui?autoReconnect=true&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false
    private String url;
    // 用户名
    private String user;
    // 用户密码
    private String password;
    //数据库连接池的最小连接数
    private int minConnectionsPerPartition;
    //数据库连接池的最大连接数
    private int maxConnectionsPerPartition;
    //数据库连接方式 1从数据库连接池获取，2新建数据库连接
    private int connectionTag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinConnectionsPerPartition() {
        return minConnectionsPerPartition;
    }

    public void setMinConnectionsPerPartition(int minConnectionsPerPartition) {
        this.minConnectionsPerPartition = minConnectionsPerPartition;
    }

    public int getMaxConnectionsPerPartition() {
        return maxConnectionsPerPartition;
    }

    public void setMaxConnectionsPerPartition(int maxConnectionsPerPartition) {
        this.maxConnectionsPerPartition = maxConnectionsPerPartition;
    }

    public int getConnectionTag() {
        return connectionTag;
    }

    public void setConnectionTag(int connectionTag) {
        this.connectionTag = connectionTag;
    }
}
