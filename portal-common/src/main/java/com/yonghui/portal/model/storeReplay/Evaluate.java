package com.yonghui.portal.model.storeReplay;

import java.io.Serializable;

/**
 *	品类教练评价
 *	huangzenglei@intfocus.com
 */
public class Evaluate extends Base implements Serializable {

	//提交人ID
	private String replyUserId;
	
	//提交人名称
	private String userName;
	
	//门店编号
	private String storeId;
	
	//评价人门店名称
	private String storeName;
	
	//用户角色 id，共有五个角色，小店长、店长、区长、品类教练、战略团队
	private String userRoleId;
	
	//现状分析 ID
	private String actionPlanId;
	
	//评价内容
	private String evaluation;
	
	public Evaluate() {}

	public Evaluate(String replyUserId, String userName, String storeId, String storeName, String userRoleId,
			String actionPlanId, String evaluation) {
		this.replyUserId = replyUserId;
		this.userName = userName;
		this.storeId = storeId;
		this.storeName = storeName;
		this.userRoleId = userRoleId;
		this.actionPlanId = actionPlanId;
		this.evaluation = evaluation;
	}

	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getActionPlanId() {
		return actionPlanId;
	}

	public void setActionPlanId(String actionPlanId) {
		this.actionPlanId = actionPlanId;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
}
