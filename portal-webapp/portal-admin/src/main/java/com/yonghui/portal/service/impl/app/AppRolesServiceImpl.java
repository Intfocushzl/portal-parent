package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.mapper.app.AppRolesMapper;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("appRolesService")
public class AppRolesServiceImpl implements AppRolesService {
	@Autowired
	private AppRolesMapper appRolesMapper;
	
	@Override
	public AppRoles queryObject(Integer id){
		return appRolesMapper.queryObject(id);
	}
	
	@Override
	public List<AppRoles> queryList(Map<String, Object> map){
		return appRolesMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appRolesMapper.queryTotal(map);
	}
	
	@Override
	public void save(AppRoles appRoles){
		appRolesMapper.save(appRoles);
	}
	
	@Override
	public void update(AppRoles appRoles){
		appRolesMapper.update(appRoles);
	}
	
	@Override
	public void delete(Integer id){
		appRolesMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		appRolesMapper.deleteBatch(ids);
	}
	
}
