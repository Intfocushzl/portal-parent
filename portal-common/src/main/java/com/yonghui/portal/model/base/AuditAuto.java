package com.yonghui.portal.model.base;

import java.util.Date;

/**
 * 审计类
 * <p>
 * Created by 张海 on 2017/04/27.
 */
public class AuditAuto extends AutoId {
    //0启用，1禁用
    protected Long disabled;
    //备注说明
    protected String remark = null;
    //创建者
    protected Long creater = null;
    //创建时间
    protected Date createTime = null;
    //修改者
    protected Long modifier = null;
    //修改时间
    protected Date modifyTime = null;
    //版本号
    protected Long version;

    public Long getDisabled() {
        return disabled;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
