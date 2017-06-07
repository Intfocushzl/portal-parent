package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 用户评论信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanComment extends AuditAuto {

    //用户id
    private Long userId;
    //文章id
    private Long acticleId;
    //正文
    private String content;

    /**
     * 设置：用户id
     */
    public void setUserId(Long userId) {
            this.userId = userId;
            }
    /**
     * 获取：用户id
     */
    public Long getUserId() {
            return userId;
            }
    /**
     * 设置：文章id
     */
    public void setActicleId(Long acticleId) {
            this.acticleId = acticleId;
            }
    /**
     * 获取：文章id
     */
    public Long getActicleId() {
            return acticleId;
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
}
