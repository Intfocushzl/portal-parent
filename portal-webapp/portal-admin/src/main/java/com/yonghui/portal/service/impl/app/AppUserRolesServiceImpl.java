package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.mapper.app.AppUserRolesMapper;
import com.yonghui.portal.service.app.AppUserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("appUserRolesService")
public class AppUserRolesServiceImpl implements AppUserRolesService {

	@Autowired
	private AppUserRolesMapper appUserRolesMapper;
	

	@Override
	public void saveOrUpdate(Long userId, List<Integer> roleIdList) {

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
