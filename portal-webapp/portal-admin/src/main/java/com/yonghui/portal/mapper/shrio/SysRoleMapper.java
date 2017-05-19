package com.yonghui.portal.mapper.shrio;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.shrio.SysRole;

import java.util.List;

/**
 * 角色管理
 * 
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
