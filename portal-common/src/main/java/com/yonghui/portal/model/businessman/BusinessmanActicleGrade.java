package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 文章评分
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-07-06 09:49:53
 */
public class BusinessmanActicleGrade extends AuditAuto {

    //打分用户id
    private Long userId;
    //打分用户姓名
    private String userName;
    //文章id
    private Long acticleId;
    //文章标题
    private String acticleTitle;

    //分数，1-5分
    private Double grade;

    /**
     * 设置：打分用户id
     */
    public void setUserId(Long userId) {
            this.userId = userId;
            }
    /**
     * 获取：打分用户id
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
     * 设置：分数，1-5分
     */
    public void setGrade(Double grade) {
            this.grade = grade;
            }
    /**
     * 获取：分数，1-5分
     */
    public Double getGrade() {
            return grade;
            }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActicleTitle() {
        return acticleTitle;
    }

    public void setActicleTitle(String acticleTitle) {
        this.acticleTitle = acticleTitle;
    }
}
