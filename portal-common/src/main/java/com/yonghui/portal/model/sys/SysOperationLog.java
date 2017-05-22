package com.yonghui.portal.model.sys;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 * Created by liuwei on 2017/05/22.
 */
public class SysOperationLog implements Serializable {
	private static final long serialVersionUID = 1L;
	//id
	private Long id;
	//工号
	private String jobNumber;
	//用户操作url
	private String url;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//请求参数
	private String parameter;
	//IP地址
	private String ip;
	//备注
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
