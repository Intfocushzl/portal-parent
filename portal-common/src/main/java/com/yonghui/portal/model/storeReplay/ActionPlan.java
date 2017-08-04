package com.yonghui.portal.model.storeReplay;

import java.io.Serializable;
import java.util.List;

/**
 *	行动计划
 *  huangzenglei@intfocus.com
 */
public class ActionPlan extends Base implements Serializable{
	
	//用户编号
	private String userId;
	
	//用户名称
	private String userName;
	
	//门店编号
	private String storeCode;
	
	//门店名称
	private String storeName;
	
	//用户角色 id，共有五个角色，小店长、店长、区长、品类教练、战略团队
	private String userRoleId;
	
	//销售现状分析
	private String situationAnalysis;
	
	//行动计划内容
	private String actionPlan;

	//回复类型 如：小店回复
	private String replyer;

	//时间索引
	private String dateIndex;

	//大区
	private String areaMans;

	public String getAreaMans() {
		return areaMans;
	}

	public void setAreaMans(String areaMans) {
		this.areaMans = areaMans;
	}

	//品类名称
	private String groupName;

	//品类编号
	private String groupCode;

	public String getDateIndex() {
		return dateIndex;
	}

	public void setDateIndex(String dateIndex) {
		this.dateIndex = dateIndex;
	}

	//评价列表
	private List<Evaluate> evaluates;

	public ActionPlan() {}

	public ActionPlan(String userId, String userName, String storeCode, String storeName, String userRoleId, String situationAnalysis, String actionPlan, String replyer, String dateIndex, String areaMans, String groupName, String groupCode, List<Evaluate> evaluates) {
		this.userId = userId;
		this.userName = userName;
		this.storeCode = storeCode;
		this.storeName = storeName;
		this.userRoleId = userRoleId;
		this.situationAnalysis = situationAnalysis;
		this.actionPlan = actionPlan;
		this.replyer = replyer;
		this.dateIndex = dateIndex;
		this.areaMans = areaMans;
		this.groupName = groupName;
		this.groupCode = groupCode;
		this.evaluates = evaluates;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getReplyer() {
		return replyer;
	}

	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}

	public List<Evaluate> getEvaluates() {
		return evaluates;
	}

	public void setEvaluates(List<Evaluate> evaluates) {
		this.evaluates = evaluates;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode == null ? null : storeCode.trim();
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName == null ? null : storeName.trim();
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId == null ? null : userRoleId.trim();
	}

	public String getSituationAnalysis() {
		return situationAnalysis;
	}

	public void setSituationAnalysis(String situationAnalysis) {
		this.situationAnalysis = situationAnalysis == null ? null : situationAnalysis.trim();
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan == null ? null : actionPlan.trim();
	}
}
