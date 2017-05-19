package com.yonghui.portal.service;


import com.yonghui.portal.model.api.PortalExecuteSql;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-17 16:35:37
 */
public interface PortalExecuteSqlService {
	
	PortalExecuteSql queryObject(Integer id);
	
	List<PortalExecuteSql> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalExecuteSql portalExecuteSql);
	
	void update(PortalExecuteSql portalExecuteSql);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
