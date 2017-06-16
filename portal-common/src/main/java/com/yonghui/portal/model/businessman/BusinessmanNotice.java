package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

import java.util.Date;

/**
 * 生意人公告信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanNotice extends AuditAuto {
    //发布后修改：存为草稿时保存之前的内容
    private String oldContent;
    //公告类型 1：系统公告 2：业务公告 3：预警消息 4：报表评论
    private Long noticeType;
    //发布范围角色编码 如：101,102,103
    private String noticeRoles;
    //发布范围群组编码 如：10001,10002,10003
    private String noticeGroups;
    //是否APP推送 0:推送 1：不推送
    private Long appPush;
    //标题
    private String title;
    //正文
    private String content;
    //草稿
    private String contentManuscript;
    //摘要
    private String abstracts;
    //封面图
    private String coverImg;
    //1：草稿 2：已发布
    private Long status;
    //阅读数
    private Long pageview;
    //过期时间，有效截止期
    private Date expireTime;

    /**
     * 设置：公告类型 1：系统公告 2：业务公告 3：预警消息 4：报表评论
     */
    public void setNoticeType(Long noticeType) {
            this.noticeType = noticeType;
            }
    /**
     * 获取：公告类型 1：系统公告 2：业务公告 3：预警消息 4：报表评论
     */
    public Long getNoticeType() {
            return noticeType;
            }
    /**
     * 设置：发布范围角色编码 如：101,102,103
     */
    public void setNoticeRoles(String noticeRoles) {
            this.noticeRoles = noticeRoles;
            }
    /**
     * 获取：发布范围角色编码 如：101,102,103
     */
    public String getNoticeRoles() {
            return noticeRoles;
            }
    /**
     * 设置：发布范围群组编码 如：10001,10002,10003
     */
    public void setNoticeGroups(String noticeGroups) {
            this.noticeGroups = noticeGroups;
            }
    /**
     * 获取：发布范围群组编码 如：10001,10002,10003
     */
    public String getNoticeGroups() {
            return noticeGroups;
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

    public String getContentManuscript() {
        return contentManuscript;
    }

    public void setContentManuscript(String contentManuscript) {
        this.contentManuscript = contentManuscript;
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
    /**
     * 设置：过期时间，有效截止期
     */
    public void setExpireTime(Date expireTime) {
            this.expireTime = expireTime;
            }
    /**
     * 获取：过期时间，有效截止期
     */
    public Date getExpireTime() {
            return expireTime;
            }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

}
