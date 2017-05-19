package com.yonghui.portal.service.impl.shrio;

import com.yonghui.portal.mapper.shrio.SysMenuMapper;
import com.yonghui.portal.model.shrio.SysMenu;
import com.yonghui.portal.service.shrio.SysMenuService;
import com.yonghui.portal.service.shrio.SysRoleMenuService;
import com.yonghui.portal.service.shrio.SysUserService;
import com.yonghui.portal.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = sysMenuMapper.queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenu> userMenuList = new ArrayList<SysMenu>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	public List<SysMenu> queryNotButtonList() {
		return sysMenuMapper.queryNotButtonList();
	}

	public List<SysMenu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == 1){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	public SysMenu queryObject(Long menuId) {
		return sysMenuMapper.queryObject(menuId);
	}

	public List<SysMenu> queryList(Map<String, Object> map) {
		return sysMenuMapper.queryList(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return sysMenuMapper.queryTotal(map);
	}

	public void save(SysMenu menu) {
		sysMenuMapper.save(menu);
	}

	public void update(SysMenu menu) {
		sysMenuMapper.update(menu);
	}

	@Transactional
	public void deleteBatch(Long[] menuIds) {
		sysMenuMapper.deleteBatch(menuIds);
	}
	
	public List<SysMenu> queryUserList(Long userId) {
		return sysMenuMapper.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		
		for(SysMenu entity : menuList){
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
