package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 用户问题回复信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public class BusinessmanProblemReply extends AuditAuto {

    //问题id
    private Long problemId;
    //回复正文
    private String content;

    /**
     * 设置：问题id
     */
    public void setProblemId(Long problemId) {
            this.problemId = problemId;
            }
    /**
     * 获取：问题id
     */
    public Long getProblemId() {
            return problemId;
            }
    /**
     * 设置：回复正文
     */
    public void setContent(String content) {
            this.content = content;
            }
    /**
     * 获取：回复正文
     */
    public String getContent() {
            return content;
            }
}
