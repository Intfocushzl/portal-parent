package com.yonghui.portal.model.global;


import java.io.Serializable;
import java.util.Date;

public class User  implements Serializable{

	//生成唯一系统号
	private String account;
	//
	private String name;
	//邮箱
	private String email;
	//手机号
	private String mobile;
	//菜单权限，默认ALL权限拥有全部。 逗号隔开为拥有权限
	private String  roleId;
	//密码
	private String pass;
	//工号
	private String jobNumber;
	//创建时间
	private Date createTime;
	//1激活，－1冻结
	private Integer status;
	//
	private Date createdAt;
	//
	private Date updatedAt;
	//0会员，1brova,2后台
	private Integer type;
	//大区
	private String largeArea;
	//省份
	private String province;
	//城市，ALL默认全部
	private String city;
	//拥有所属商行逗号隔开, ALL默认全部
	private String firm;
	//拥有门店逗号隔开,ALL默认全部
	private String storeNumber;
	//拥有群组逗号隔开,ALL默认全部
	private String groupId;

	//变更角色id

	private String changeMobile;
	//变更业态
	private Integer changeType;
	//变更大区
	private String changeLargeArea;
	//变更省份
	private String changeProvince;
	//变更城市
	private String changeCity;
	//变更商行
	private String changeFirm;
	//申请状态， 0默认状态（申请成功状态） , -1申请失败，1申请中
	private Integer changeStatus;
	//变更门店
	private String changeStoreNumber;
	//变更时间
	private Date changeTime;
	//备注
	private String remark;
	//新区、片区
	private String areaMans;
	//变更新区、片区
	private String changeAreaMans;
	//新角色临时标记
	private String rold;


	//角色名
	private String roleName;
	//角色类型
	private Integer roleType;
	//变更角色
	private String changeRoleId;
	//变更角色名称
	private String changeRoleName;


	//主键
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass == null ? null : pass.trim();
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber == null ? null : jobNumber.trim();
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber == null ? null : storeNumber.trim();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getLargeArea() {
		return largeArea;
	}

	public void setLargeArea(String largeArea) {
		this.largeArea = largeArea;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getChangeMobile() {
		return changeMobile;
	}

	public void setChangeMobile(String changeMobile) {
		this.changeMobile = changeMobile;
	}

	public String getChangeRoleId() {
		return changeRoleId;
	}

	public void setChangeRoleId(String changeRoleId) {
		this.changeRoleId = changeRoleId;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public String getChangeLargeArea() {
		return changeLargeArea;
	}

	public void setChangeLargeArea(String changeLargeArea) {
		this.changeLargeArea = changeLargeArea;
	}

	public String getChangeProvince() {
		return changeProvince;
	}

	public void setChangeProvince(String changeProvince) {
		this.changeProvince = changeProvince;
	}

	public String getChangeCity() {
		return changeCity;
	}

	public void setChangeCity(String changeCity) {
		this.changeCity = changeCity;
	}

	public String getChangeFirm() {
		return changeFirm;
	}

	public void setChangeFirm(String changeFirm) {
		this.changeFirm = changeFirm;
	}

	public Integer getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(Integer changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getChangeRoleName() {
		return changeRoleName;
	}

	public void setChangeRoleName(String changeRoleName) {
		this.changeRoleName = changeRoleName;
	}

	public String getChangeStoreNumber() {
		return changeStoreNumber;
	}

	public void setChangeStoreNumber(String changeStoreNumber) {
		this.changeStoreNumber = changeStoreNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getAreaMans() {
		return areaMans;
	}

	public void setAreaMans(String areaMans) {
		this.areaMans = areaMans;
	}

	public String getChangeAreaMans() {
		return changeAreaMans;
	}

	public void setChangeAreaMans(String changeAreaMans) {
		this.changeAreaMans = changeAreaMans;
	}

	public String getRold() {
		return rold;
	}

	public void setRold(String rold) {
		this.rold = rold;
	}
}