package com.yonghui.portal.model.global;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Menu implements Serializable{
    private Integer id;

    private String name;

    private String pName;

    private String url;

    private Integer pid;

    private String icon;

    private Date createTime;

    private String createdAt;

    private String updatedAt;

    private Integer status;

    private Integer sort;

    /**
     * ztree属性
     */
    private Boolean open;
    //easy ui
    private List<Menu> children;
    private String text;
    private String state;
//    
    private String checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public String getState() {
//		return state;
////		return "open";
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}
//
//	
//

	public String getChecked() {
		return checked;
//		return checked="true";
//		return "true";
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}



