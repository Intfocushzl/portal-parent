package com.yonghui.portal.service.report;


import com.yonghui.portal.model.report.PortalRouteReport;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:43:38
 */
public interface PortalRouteReportService {
	
	PortalRouteReport queryObject(Integer id);
	
	List<PortalRouteReport> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalRouteReport portalRouteReport);
	
	void update(PortalRouteReport portalRouteReport);
	
	void delete(Integer id);
	
	void deleteBatch(String[] codes);
}
