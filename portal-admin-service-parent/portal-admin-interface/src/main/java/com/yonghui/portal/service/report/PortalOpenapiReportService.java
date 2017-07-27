package com.yonghui.portal.service.report;


import com.yonghui.portal.model.report.PortalOpenapiReport;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 14:01:50
 */
public interface PortalOpenapiReportService {
	
	PortalOpenapiReport queryObject(Integer id);
	
	List<PortalOpenapiReport> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalOpenapiReport portalOpenapiReport);
	
	void update(PortalOpenapiReport portalOpenapiReport);
	
	void delete(Integer id);
	
	void deleteBatch(String[] codes);

	/**
	 * 产生新的编码
	 *
	 * @return
	 */
	String getNewMaxCode();
}
