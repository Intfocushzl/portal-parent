package com.yonghui.portal.model.message;

public class JobInformation {
    private Integer id;

    private String fieldsSelected;

    private String jobKey;

    private String isNoRole;

    private String isIncludeReceiver;

    private String messageType;

    private String objId;

    private String sendType;

    private String objUser;

    private String roleUser;

    private String acounterUser;

    private String type;

    private String triggertime;

    private String executetime;

    private String cycle;

    private String dayinmounth;

    private String dayinweek;
    
    private String jumpType ;
    
    private String objUserType;
    
    private String endTime ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldsSelected() {
        return fieldsSelected;
    }

    public void setFieldsSelected(String fieldsSelected) {
        this.fieldsSelected = fieldsSelected == null ? null : fieldsSelected.trim();
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey == null ? null : jobKey.trim();
    }

    public String getIsNoRole() {
        return isNoRole;
    }

    public void setIsNoRole(String isNoRole) {
        this.isNoRole = isNoRole == null ? null : isNoRole.trim();
    }

    public String getIsIncludeReceiver() {
        return isIncludeReceiver;
    }

    public void setIsIncludeReceiver(String isIncludeReceiver) {
        this.isIncludeReceiver = isIncludeReceiver == null ? null : isIncludeReceiver.trim();
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId == null ? null : objId.trim();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    public String getObjUser() {
        return objUser;
    }

    public void setObjUser(String objUser) {
        this.objUser = objUser == null ? null : objUser.trim();
    }

    public String getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser == null ? null : roleUser.trim();
    }

    public String getAcounterUser() {
        return acounterUser;
    }

    public void setAcounterUser(String acounterUser) {
        this.acounterUser = acounterUser == null ? null : acounterUser.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTriggertime() {
        return triggertime;
    }

    public void setTriggertime(String triggertime) {
        this.triggertime = triggertime == null ? null : triggertime.trim();
    }

    public String getExecutetime() {
        return executetime;
    }

    public void setExecutetime(String executetime) {
        this.executetime = executetime == null ? null : executetime.trim();
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }

    public String getDayinmounth() {
        return dayinmounth;
    }

    public void setDayinmounth(String dayinmounth) {
        this.dayinmounth = dayinmounth == null ? null : dayinmounth.trim();
    }

    public String getDayinweek() {
        return dayinweek;
    }

    public void setDayinweek(String dayinweek) {
        this.dayinweek = dayinweek == null ? null : dayinweek.trim();
    }

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
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