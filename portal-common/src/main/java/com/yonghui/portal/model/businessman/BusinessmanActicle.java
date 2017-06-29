package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 生意人数据学院文章信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanActicle extends AuditAuto {

    //正文草稿
    private String contentManuscript;
    //文章类型 1：文章 2：视频
    private Long acticleType;
    //发布范围角色编码 如：101,102,103
    private String acticleRoles;
    //发布范围群组编码 如：10001,10002,10003
    private String acticleGroups;
    //是否APP推送 0:推送 1：不推送
    private Long appPush;
    //标签：最多三个 如 aa,bb,cc
    private String tagInfo;
    //标题
    private String title;
    //正文
    private String content;
    //摘要
    private String abstracts;
    //封面图
    private String coverImg;
    //1：草稿 2：已发布
    private Long status;
    //阅读数
    private Long pageview;
    private String attachFile;
    private String attachVideo;

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getAttachVideo() {
        return attachVideo;
    }

    public void setAttachVideo(String attachVideo) {
        this.attachVideo = attachVideo;
    }

    /**
     * 设置：文章类型 1：文章 2：视频
     */
    public void setActicleType(Long acticleType) {
        this.acticleType = acticleType;
    }

    /**
     * 获取：文章类型 1：文章 2：视频
     */
    public Long getActicleType() {
        return acticleType;
    }

    /**
     * 设置：发布范围角色编码 如：101,102,103
     */
    public void setActicleRoles(String acticleRoles) {
        this.acticleRoles = acticleRoles;
    }

    /**
     * 获取：发布范围角色编码 如：101,102,103
     */
    public String getActicleRoles() {
        return acticleRoles;
    }

    /**
     * 设置：发布范围群组编码 如：10001,10002,10003
     */
    public void setActicleGroups(String acticleGroups) {
        this.acticleGroups = acticleGroups;
    }

    /**
     * 获取：发布范围群组编码 如：10001,10002,10003
     */
    public String getActicleGroups() {
        return acticleGroups;
    }

    /**
     * 设置：是否APP推送 0:推送 1：不推送
     */
    public void setAppPush(Long appPush) {
        this.appPush = appPush;
    }

    /**
     * 获取：是否APP推送 0:推送 1：不推送
     */
    public Long getAppPush() {
        return appPush;
    }

    /**
     * 设置：标签：最多三个 如 aa,bb,cc
     */
    public void setTagInfo(String tagInfo) {
        this.tagInfo = tagInfo;
    }

    /**
     * 获取：标签：最多三个 如 aa,bb,cc
     */
    public String getTagInfo() {
        return tagInfo;
    }

    /**
     * 设置：标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：正文
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取：正文
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置：摘要
     */
    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    /**
     * 获取：摘要
     */
    public String getAbstracts() {
        return abstracts;
    }

    /**
     * 设置：封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取：封面图
     */
    public String getCoverImg() {
        return coverImg;
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

    /**
     * 设置：阅读数
     */
    public void setPageview(Long pageview) {
        this.pageview = pageview;
    }

    /**
     * 获取：阅读数
     */
    public Long getPageview() {
        return pageview;
    }

    public String getContentManuscript() {
        return contentManuscript;
    }

    public void setContentManuscript(String contentManuscript) {
        this.contentManuscript = contentManuscript;
    }
}
