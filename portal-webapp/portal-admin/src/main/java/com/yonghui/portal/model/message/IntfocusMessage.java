package com.yonghui.portal.model.message;

/*
 * @desc:作为前端和后端的参数
 * */

public class IntfocusMessage {

	private String jobName ;
	private String type;
	private String triggerTime;
	private String executeTime;
	private String cycle;
	private String dayInMounth;
	private String dayInWeek;
	private String MessageType;
	private String jumpType;
	private String objID;
	private String exceptionRule;
	private String isNoRule;
	private String isIncludeReceiver;
	private String message;
	private String accountUser;
	private String objUser;
	private String roleUser;
	private String SendType;
	private String fieldsSelected;
	private String SqlScript;
	private String objUserType;
	private String endTime;
	public String getSendType() {
		return SendType;
	}
	public void setSendType(String sendType) {
		SendType = sendType;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}
	public String getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getDayInMounth() {
		return dayInMounth;
	}
	public void setDayInMounth(String dayInMounth) {
		this.dayInMounth = dayInMounth;
	}
	public String getDayInWeek() {
		return dayInWeek;
	}
	public void setDayInWeek(String dayInWeek) {
		this.dayInWeek = dayInWeek;
	}
	public String getMessageType() {
		return MessageType;
	}
	public void setMessageType(String messageType) {
		MessageType = messageType;
	}
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	public String getObjID() {
		return objID;
	}
	public void setObjID(String objID) {
		this.objID = objID;
	}
	public String getExceptionRule() {
		return exceptionRule;
	}
	public void setExceptionRule(String exceptionRule) {
		this.exceptionRule = exceptionRule;
	}
	public String getIsNoRule() {
		return isNoRule;
	}
	public void setIsNoRule(String isNoRule) {
		this.isNoRule = isNoRule;
	}
	public String getIsIncludeReceiver() {
		return isIncludeReceiver;
	}
	public void setIsIncludeReceiver(String isIncludeReceiver) {
		this.isIncludeReceiver = isIncludeReceiver;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccountUser() {
		return accountUser;
	}
	public void setAccountUser(String accountUser) {
		this.accountUser = accountUser;
	}
	public String getObjUser() {
		return objUser;
	}
	public void setObjUser(String objUser) {
		this.objUser = objUser;
	}
	public String getRoleUser() {
		return roleUser;
	}
	public void setRoleUser(String roleUser) {
		this.roleUser = roleUser;
	}
	public String getFieldsSelected() {
		return fieldsSelected;
	}
	public void setFieldsSelected(String fieldsSelected) {
		this.fieldsSelected = fieldsSelected;
	}
	public String getSqlScript() {
		return SqlScript;
	}
	public void setSqlScript(String sqlScript) {
		SqlScript = sqlScript;
	}
	public String getObjUserType() {
		return objUserType;
	}
	public void setObjUserType(String objUserType) {
		this.objUserType = objUserType;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
