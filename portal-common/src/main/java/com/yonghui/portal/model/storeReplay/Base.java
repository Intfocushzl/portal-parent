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
	private String updatedAt;

	//记录创建时间
	private String createdAt;

	//备注
	private String remark;

	public Base() {}

	public Base(Integer id, String updatedAt, String createdAt, String remark) {
		this.id = id;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.remark = remark;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}
