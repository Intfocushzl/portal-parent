package com.yonghui.portal.service;


import com.yonghui.portal.model.api.PortalReport;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-18 13:05:16
 */
public interface PortalReportService {
	
	PortalReport queryObject(Integer id);
	
	List<PortalReport> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalReport portalReport);
	
	void update(PortalReport portalReport);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
