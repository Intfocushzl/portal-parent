package com.yonghui.portal.model;


import com.yonghui.portal.model.base.AuditAuto;

/**
 * 代码生成配置信息表
 *
 */
public class SysGenerator extends AuditAuto {

	//项目基础包名
	private String syspackage;
	//作者
	private String author;
	//作者邮箱
	private String email;
	//
	private String tableprefix;

	/**
	 * 设置：项目基础包名
	 */
	public void setSyspackage(String syspackage) {
		this.syspackage = syspackage;
	}
	/**
	 * 获取：项目基础包名
	 */
	public String getSyspackage() {
		return syspackage;
	}
	/**
	 * 设置：作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：作者邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：作者邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：
	 */
	public void setTableprefix(String tableprefix) {
		this.tableprefix = tableprefix;
	}
	/**
	 * 获取：
	 */
	public String getTableprefix() {
		return tableprefix;
	}
}
