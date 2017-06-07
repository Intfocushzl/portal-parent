package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 用户问题反馈信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public class BusinessmanProblem extends AuditAuto {

    //用户id
    private Long userId;
    //标题
    private String title;
    //问题正文
    private String content;
    //上传的图片地址，最多三张 如 http://aa,http://bb,http:/cc
    private String images;
    //0：已解决 1：未解决
    private Long status;

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
     * 设置：问题正文
     */
    public void setContent(String content) {
            this.content = content;
            }
    /**
     * 获取：问题正文
     */
    public String getContent() {
            return content;
            }
    /**
     * 设置：上传的图片地址，最多三张 如 http://aa,http://bb,http:/cc
     */
    public void setImages(String images) {
            this.images = images;
            }
    /**
     * 获取：上传的图片地址，最多三张 如 http://aa,http://bb,http:/cc
     */
    public String getImages() {
            return images;
            }
    /**
     * 设置：0：已解决 1：未解决
     */
    public void setStatus(Long status) {
            this.status = status;
            }
    /**
     * 获取：0：已解决 1：未解决
     */
    public Long getStatus() {
            return status;
            }
}
