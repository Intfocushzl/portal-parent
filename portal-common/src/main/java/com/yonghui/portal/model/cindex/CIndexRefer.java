package com.yonghui.portal.model.cindex;

import com.yonghui.portal.model.base.AuditAuto;

import java.util.Date;

/**
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-02 17:08:01
 */
public class CIndexRefer extends AuditAuto {

    //参考名
    private String refername;
    //下标
    private String referchar;
    //定义
    private String def;
    //
    private Date updatetime;

    /**
     * 设置：参考名
     */
    public void setRefername(String refername) {
            this.refername = refername;
            }
    /**
     * 获取：参考名
     */
    public String getRefername() {
            return refername;
            }
    /**
     * 设置：下标
     */
    public void setReferchar(String referchar) {
            this.referchar = referchar;
            }
    /**
     * 获取：下标
     */
    public String getReferchar() {
            return referchar;
            }
    /**
     * 设置：定义
     */
    public void setDef(String def) {
            this.def = def;
            }
    /**
     * 获取：定义
     */
    public String getDef() {
            return def;
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
