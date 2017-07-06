package com.yonghui.portal.model.businessman;

import com.yonghui.portal.model.base.AuditAuto;

import java.util.List;

/**
 * 专题信息表
 *
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public class BusinessmanSubjectInfo extends AuditAuto {

    //父id，0是父
    private Integer pid;
    //父专题名
    private String pname;
    //专题名称
    private String name;
    //排序位
    private Integer position;
    //是否展开
    private Boolean open;

    private List<BusinessmanSubjectInfo> children;


    /**
     * 设置：父id，0是父
     */
    public void setPid(Integer pid) {
            this.pid = pid;
            }
    /**
     * 获取：父id，0是父
     */
    public Integer getPid() {
            return pid;
            }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 设置：专题名称
     */
    public void setName(String name) {
            this.name = name;
            }
    /**
     * 获取：专题名称
     */
    public String getName() {
            return name;
            }
    /**
     * 设置：排序位
     */
    public void setPosition(Integer position) {
            this.position = position;
            }
    /**
     * 获取：排序位
     */
    public Integer getPosition() {
            return position;
            }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }


    public List<BusinessmanSubjectInfo> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessmanSubjectInfo> children) {
        this.children = children;
    }
}
