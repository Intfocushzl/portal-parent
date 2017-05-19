package com.yonghui.portal.service.shrio;

import com.yonghui.portal.model.shrio.SysRole;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 */
public interface SysRoleService {
	
	SysRole queryObject(Long roleId);
	
	List<SysRole> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRole role);
	
	void update(SysRole role);
	
	void deleteBatch(Long[] roleIds);
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
