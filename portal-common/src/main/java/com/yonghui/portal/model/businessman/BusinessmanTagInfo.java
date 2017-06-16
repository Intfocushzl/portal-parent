package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

/**
 * 标签信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public class BusinessmanTagInfo extends AuditAuto {

    //标签名
    private String tagName;
    //标签类型 1：文章，2：视频
    private Long tagType;

    /**
     * 设置：标签名
     */
    public void setTagName(String tagName) {
            this.tagName = tagName;
            }
    /**
     * 获取：标签名
     */
    public String getTagName() {
            return tagName;
            }
    /**
     * 设置：标签类型 1：文章，2：视频
     */
    public void setTagType(Long tagType) {
            this.tagType = tagType;
            }
    /**
     * 获取：标签类型 1：文章，2：视频
     */
    public Long getTagType() {
            return tagType;
            }
}
