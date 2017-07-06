package com.yonghui.portal.model.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:18
 */
public class AppUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    //
    private String userName;
    //
    private String userNum;
    //
    private String userPass;
    //
    private String email;
    //
    private String mobile;
    //
    private String tel;
    //
    private Date joinDate;
    //
    private String position;
    //
    private Integer deptId;
    //
    private String deptName;
    //
    private Integer activeFlag;
    //
    private Integer createUser;
    //
    private Integer updateUser;
    //
    private String memo;
    //
    private Date loadTime;
    //
    private Date createdAt;
    //
    private Date updatedAt;
    //
    private Date lastLoginAt;
    //
    private String lastLoginIp;
    //
    private String lastLoginBrowser;
    //
    private Integer signInCount;
    //
    private String lastLoginVersion;
    //
    private String accessToken;
    //
    private String storeIds;

    private List<Integer> roleIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 设置：
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取：
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置：
     */
    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    /**
     * 获取：
     */
    public String getUserNum() {
        return userNum;
    }

    /**
     * 设置：
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     * 获取：
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * 设置：
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取：
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置：
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * 获取：
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * 设置：
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取：
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置：
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取：
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 设置：
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取：
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置：
     */
    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * 获取：
     */
    public Integer getActiveFlag() {
        return activeFlag;
    }

    /**
     * 设置：
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 设置：
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取：
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置：
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置：
     */
    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    /**
     * 获取：
     */
    public Date getLoadTime() {
        return loadTime;
    }

    /**
     * 设置：
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取：
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置：
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取：
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置：
     */
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    /**
     * 获取：
     */
    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    /**
     * 设置：
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取：
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置：
     */
    public void setLastLoginBrowser(String lastLoginBrowser) {
        this.lastLoginBrowser = lastLoginBrowser;
    }

    /**
     * 获取：
     */
    public String getLastLoginBrowser() {
        return lastLoginBrowser;
    }

    /**
     * 设置：
     */
    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    /**
     * 获取：
     */
    public Integer getSignInCount() {
        return signInCount;
    }

    /**
     * 设置：
     */
    public void setLastLoginVersion(String lastLoginVersion) {
        this.lastLoginVersion = lastLoginVersion;
    }

    /**
     * 获取：
     */
    public String getLastLoginVersion() {
        return lastLoginVersion;
    }

    /**
     * 设置：
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 获取：
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 设置：
     */
    public void setStoreIds(String storeIds) {
        this.storeIds = storeIds;
    }

    /**
     * 获取：
     */
    public String getStoreIds() {
        return storeIds;
    }

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }

}
