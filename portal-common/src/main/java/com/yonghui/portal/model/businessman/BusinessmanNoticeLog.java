package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 生意人公告信息阅读日志
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public class BusinessmanNoticeLog extends AuditAuto {

    //公告id
    private Long noticeId;

    /**
     * 设置：公告id
     */
    public void setNoticeId(Long noticeId) {
            this.noticeId = noticeId;
            }
    /**
     * 获取：公告id
     */
    public Long getNoticeId() {
            return noticeId;
            }
}
