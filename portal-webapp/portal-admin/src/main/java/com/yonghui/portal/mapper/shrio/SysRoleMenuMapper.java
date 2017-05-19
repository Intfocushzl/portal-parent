package com.yonghui.portal.mapper.shrio;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.shrio.SysRoleMenu;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
