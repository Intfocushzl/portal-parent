package com.yonghui.portal.model.sys;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 系统FTP配置信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-09 11:39:14
 */
public class SysFtpConfig extends AuditAuto {

    //用户名
    private String username;
    //密码
    private String password;
    //地址
    private String host;
    //端口
    private Integer port;

    private String rootpath;

    public String getRootpath() {
        return rootpath;
    }

    public void setRootpath(String rootpath) {
        this.rootpath = rootpath;
    }

    /**
     * 设置：用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：地址
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取：地址
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置：端口
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 获取：端口
     */
    public Integer getPort() {
        return port;
    }
}
