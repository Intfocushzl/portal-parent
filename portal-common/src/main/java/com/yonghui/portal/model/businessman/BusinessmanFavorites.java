package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 用户收藏表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanFavorites extends AuditAuto {

    //收藏用户id
    private Long userId;
    //收藏文章id
    private Long acticleId;
    //1：已收藏 2：取消收藏
    private Long status;

    /**
     * 设置：收藏用户id
     */
    public void setUserId(Long userId) {
            this.userId = userId;
            }
    /**
     * 获取：收藏用户id
     */
    public Long getUserId() {
            return userId;
            }
    /**
     * 设置：收藏文章id
     */
    public void setActicleId(Long acticleId) {
            this.acticleId = acticleId;
            }
    /**
     * 获取：收藏文章id
     */
    public Long getActicleId() {
            return acticleId;
            }
    /**
     * 设置：1：已收藏 2：取消收藏
     */
    public void setStatus(Long status) {
            this.status = status;
            }
    /**
     * 获取：1：已收藏 2：取消收藏
     */
    public Long getStatus() {
            return status;
            }
}
