package com.yonghui.portal.service;


import com.yonghui.portal.model.api.PortalDataSource;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-17 16:35:37
 */
public interface PortalDataSourceService {
	
	PortalDataSource queryObject(Integer id);
	
	List<PortalDataSource> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalDataSource portalDataSource);
	
	void update(PortalDataSource portalDataSource);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
