package com.yonghui.portal.mapper.shrio;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.shrio.SysMenu;

import java.util.List;

/**
 * 菜单管理
 * 
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenu> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> queryNotButtonList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenu> queryUserList(Long userId);
}
