package com.yonghui.portal.model.sys;

public class CProMeterdataInformation {
	
    private Long id;

    private String proname;

    private String taskname;

    private String prodb;

    private String owner;

    private String createdBy;

    private String createdAt;

    private Long runtime;

    private Integer statustype;

    private Integer dateparatype;

    private Integer shopparatype;

    private Integer useflag;

    private String updatetime;
    
    private String remark;

    private String fromtable;

    private String intable;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFromtable() {
        return fromtable;
    }

    public void setFromtable(String fromtable) {
        this.fromtable = fromtable == null ? null : fromtable.trim();
    }

    public String getIntable() {
        return intable;
    }

    public void setIntable(String intable) {
        this.intable = intable == null ? null : intable.trim();
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname == null ? null : proname.trim();
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
    }

    public String getProdb() {
        return prodb;
    }

    public void setProdb(String prodb) {
        this.prodb = prodb == null ? null : prodb.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createBy) {
        this.createdBy = createBy == null ? null : createBy.trim();
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Integer getStatustype() {
        return statustype;
    }

    public void setStatustype(Integer statustype) {
        this.statustype = statustype;
    }

    public Integer getDateparatype() {
        return dateparatype;
    }

    public void setDateparatype(Integer dateparatype) {
        this.dateparatype = dateparatype;
    }

    public Integer getShopparatype() {
        return shopparatype;
    }

    public void setShopparatype(Integer shopparatype) {
        this.shopparatype = shopparatype;
    }

    public Integer getUseflag() {
        return useflag;
    }

    public void setUseflag(Integer uesflag) {
        this.useflag = uesflag;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
	
}
