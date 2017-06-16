package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 生意人文章信息阅读日志
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanActicleLog extends AuditAuto {

    //文章id
    private Long acticleId;

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
}
