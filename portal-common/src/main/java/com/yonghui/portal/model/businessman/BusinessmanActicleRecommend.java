package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 文章推荐置顶
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public class BusinessmanActicleRecommend extends AuditAuto {

    //文章类型 1：文章 2：视频
    private Long acticleType;
    //文章id
    private Integer acticleId;
    //排序
    private Integer orderNum;

    private String title;

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
     * 设置：文章id
     */
    public void setActicleId(Integer acticleId) {
            this.acticleId = acticleId;
            }
    /**
     * 获取：文章id
     */
    public Integer getActicleId() {
            return acticleId;
            }
    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
            this.orderNum = orderNum;
            }
    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
            return orderNum;
            }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
