package com.yonghui.portal.model.message;

public class AppRoleResources {
	private int    roleId    ;
	private String roleName   ;
	private int    objId  ;
	private int    objType;
	private String    objName ;
	private int    memo    ;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getObjId() {
		return objId;
	}
	public void setObjId(int objId) {
		this.objId = objId;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	public int getMemo() {
		return memo;
	}
	public void setMemo(int memo) {
		this.memo = memo;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	@Override
	public String toString() {
		return "AppRoleResources [roleId=" + roleId + ", roleName=" + roleName + ", objId=" + objId + ", objType="
				+ objType + ", objName=" + objName + ", memo=" + memo + "]";
	}
	
	
}
