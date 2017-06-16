package com.yonghui.portal.model.cindex;

import com.yonghui.portal.model.base.AuditAuto;

import java.util.Date;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:12:47
 */
public class ReportDimIndex extends AuditAuto {

    //纬度ID（唯一）
    private Integer dimid;
    //纬度名称
    private String dimname;
    //纬度后标
    private String dimlab;
    //
    private Date updatetime;

    /**
     * 设置：纬度ID（唯一）
     */
    public void setDimid(Integer dimid) {
        this.dimid = dimid;
    }

    /**
     * 获取：纬度ID（唯一）
     */
    public Integer getDimid() {
        return dimid;
    }

    /**
     * 设置：纬度名称
     */
    public void setDimname(String dimname) {
        this.dimname = dimname;
    }

    /**
     * 获取：纬度名称
     */
    public String getDimname() {
        return dimname;
    }

    /**
     * 设置：纬度后标
     */
    public void setDimlab(String dimlab) {
        this.dimlab = dimlab;
    }

    /**
     * 获取：纬度后标
     */
    public String getDimlab() {
        return dimlab;
    }

    /**
     * 设置：
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取：
     */
    public Date getUpdatetime() {
        return updatetime;
    }
}
