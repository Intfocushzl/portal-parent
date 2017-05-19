package com.yonghui.portal.mapper.shrio;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.shrio.SysUserRole;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
