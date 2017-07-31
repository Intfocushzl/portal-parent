package com.yonghui.portal.model.storeReplay;

import java.io.Serializable;

/**
 *	公有字段
 *	 huangzenglei@intfocus.com
 */
public class Base implements Serializable{
	
	//主键
	private Integer id; 
	
	//最后一次更新时间
	private String updateAt;
	
	//记录创建时间
	private String createAt;

	//备注
	private String remark;

	public Base() {}

	public Base(Integer id, String updateAt, String createAt, String remark) {
		this.id = id;
		this.updateAt = updateAt;
		this.createAt = createAt;
		this.remark = remark;
	}
	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt == null ? null : createAt.trim();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt == null ? null : updateAt.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}
