package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.annotation.DataSource;
import com.yonghui.portal.db.DataSourceConstants;
import com.yonghui.portal.mapper.app.AppGroupsMapper;
import com.yonghui.portal.model.app.AppGroups;
import com.yonghui.portal.service.app.AppGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("appGroupsService")
public class AppGroupsServiceImpl implements AppGroupsService {
	@Autowired
	private AppGroupsMapper appGroupsMapper;
	
	@Override
    @DataSource(DataSourceConstants.MYSQL_PORTAL_APP)
	public AppGroups queryObject(Integer id){
		return appGroupsMapper.queryObject(id);
	}
	
	@Override
    @DataSource(DataSourceConstants.MYSQL_PORTAL_APP)
	public List<AppGroups> queryList(Map<String, Object> map){
		return appGroupsMapper.queryList(map);
	}
	
	@Override
    @DataSource(DataSourceConstants.MYSQL_PORTAL_APP)
	public int queryTotal(Map<String, Object> map){
		return appGroupsMapper.queryTotal(map);
	}
	
	@Override
	public void save(AppGroups appGroups){
		appGroupsMapper.save(appGroups);
	}
	
	@Override
	public void update(AppGroups appGroups){
		appGroupsMapper.update(appGroups);
	}
	
	@Override
	public void delete(Integer id){
		appGroupsMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		appGroupsMapper.deleteBatch(ids);
	}
	
}
