package com.yonghui.portal.service.impl.shrio;

import com.yonghui.portal.mapper.shrio.SysRoleMenuMapper;
import com.yonghui.portal.service.shrio.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * 
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if(menuIdList.size() == 0){
			return ;
		}
		//先删除角色与菜单关系
		sysRoleMenuMapper.delete(roleId);
		
		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		sysRoleMenuMapper.save(map);
	}

	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuMapper.queryMenuIdList(roleId);
	}

}
