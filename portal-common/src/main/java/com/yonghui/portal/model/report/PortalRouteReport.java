package com.yonghui.portal.model.report;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:43:38
 */
public class PortalRouteReport extends AuditAuto {

    //报表唯一编码，api接口请求必须参数
    private String code;
    //请求外部接口key值
    private String key;
    //请求外部系统url
    private String url;
    //执行参数，格式如aa@@bb@@cc
    private String parameter;
    //请求外部系统名称
    private String name;

    /**
     * 设置：报表唯一编码，api接口请求必须参数
     */
    public void setCode(String code) {
            this.code = code;
            }
    /**
     * 获取：报表唯一编码，api接口请求必须参数
     */
    public String getCode() {
            return code;
            }
    /**
     * 设置：请求外部接口key值
     */
    public void setKey(String key) {
            this.key = key;
            }
    /**
     * 获取：请求外部接口key值
     */
    public String getKey() {
            return key;
            }
    /**
     * 设置：请求外部系统url
     */
    public void setUrl(String url) {
            this.url = url;
            }
    /**
     * 获取：请求外部系统url
     */
    public String getUrl() {
            return url;
            }
    /**
     * 设置：执行参数，格式如aa@@bb@@cc
     */
    public void setParameter(String parameter) {
            this.parameter = parameter;
            }
    /**
     * 获取：执行参数，格式如aa@@bb@@cc
     */
    public String getParameter() {
            return parameter;
            }
    /**
     * 设置：请求外部系统名称
     */
    public void setName(String name) {
            this.name = name;
            }
    /**
     * 获取：请求外部系统名称
     */
    public String getName() {
            return name;
            }
}
