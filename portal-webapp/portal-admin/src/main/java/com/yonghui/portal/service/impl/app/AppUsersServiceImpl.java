package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.mapper.app.AppUsersMapper;
import com.yonghui.portal.model.app.AppUsers;
import com.yonghui.portal.service.app.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("appUsersService")
public class AppUsersServiceImpl implements AppUsersService {
	@Autowired
	private AppUsersMapper appUsersMapper;
	
	@Override
	public AppUsers queryObject(Integer id){
		return appUsersMapper.queryObject(id);
	}
	
	@Override
	public List<AppUsers> queryList(Map<String, Object> map){
		return appUsersMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appUsersMapper.queryTotal(map);
	}
	
	@Override
	public void save(AppUsers appUsers){
		appUsersMapper.save(appUsers);
	}
	
	@Override
	public void update(AppUsers appUsers){
		appUsersMapper.update(appUsers);
	}
	
	@Override
	public void delete(Integer id){
		appUsersMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		appUsersMapper.deleteBatch(ids);
	}
	
}
