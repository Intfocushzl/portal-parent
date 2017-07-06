package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.mapper.app.AppUserRolesMapper;
import com.yonghui.portal.service.app.AppUserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appUserRolesService")
public class AppUserRolesServiceImpl implements AppUserRolesService {

	@Autowired
	private AppUserRolesMapper appUserRolesMapper;
	

	@Override
	public void saveOrUpdate(Integer userId, List<Integer> roleList) {
        if(roleList.size() == 0){
            return ;
        }
        //先删除角色与菜单关系
        appUserRolesMapper.delete(userId);

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("roleList", roleList);
        appUserRolesMapper.save(map);
	}

	@Override
	public List<Integer> queryRoleIdList(Integer userId) {
		return appUserRolesMapper.queryRoleIdList(userId);
	}

	@Override
	public void delete(Integer userId){
		appUserRolesMapper.delete(userId);
	}

}
