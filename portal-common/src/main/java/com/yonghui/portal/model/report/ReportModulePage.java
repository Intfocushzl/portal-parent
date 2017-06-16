package com.yonghui.portal.model.report;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 报表专题信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-16 11:23:25
 */
public class ReportModulePage extends AuditAuto {

    //专题类型 0：PC专题 1：APP专题
    private Long pageType;
    //
    private String title;
    //原文
    private String content;
    //保存草稿内容
    private String designStructrue;
    //发布正式内容
    private String onlineStructrue;
    //1：草稿 2：已发布
    private Long status;

    /**
     * 设置：专题类型 0：PC专题 1：APP专题
     */
    public void setPageType(Long pageType) {
        this.pageType = pageType;
    }

    /**
     * 获取：专题类型 0：PC专题 1：APP专题
     */
    public Long getPageType() {
        return pageType;
    }

    /**
     * 设置：
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：原文
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：原文
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：保存草稿内容
     */
    public void setDesignStructrue(String designStructrue) {
        this.designStructrue = designStructrue;
    }

    /**
     * 获取：保存草稿内容
     */
    public String getDesignStructrue() {
        return designStructrue;
    }

    /**
     * 设置：发布正式内容
     */
    public void setOnlineStructrue(String onlineStructrue) {
        this.onlineStructrue = onlineStructrue;
    }

    /**
     * 获取：发布正式内容
     */
    public String getOnlineStructrue() {
        return onlineStructrue;
    }

    /**
     * 设置：1：草稿 2：已发布
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * 获取：1：草稿 2：已发布
     */
    public Long getStatus() {
        return status;
    }
}
