package com.yonghui.portal.service.impl.shrio;

import com.yonghui.portal.mapper.shrio.SysUserMapper;
import com.yonghui.portal.model.shrio.SysUser;
import com.yonghui.portal.service.shrio.SysRoleService;
import com.yonghui.portal.service.shrio.SysUserRoleService;
import com.yonghui.portal.service.shrio.SysUserService;
import com.yonghui.portal.utils.Constant;
import com.yonghui.portal.utils.RRException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;

	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}

	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}

	public SysUser queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}
	
	public SysUser queryObject(Long userId) {
		return sysUserMapper.queryObject(userId);
	}

	public List<SysUser> queryList(Map<String, Object> map){
		return sysUserMapper.queryList(map);
	}
	
	public int queryTotal(Map<String, Object> map) {
		return sysUserMapper.queryTotal(map);
	}

	@Transactional
	public void save(SysUser user) {
		user.setCreateTime(new Date());
		//sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		sysUserMapper.save(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Transactional
	public void update(SysUser user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		sysUserMapper.update(user);
		
		//检查角色是否越权
		checkRole(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserMapper.deleteBatch(userId);
	}

	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserMapper.updatePassword(map);
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUser user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		
		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
