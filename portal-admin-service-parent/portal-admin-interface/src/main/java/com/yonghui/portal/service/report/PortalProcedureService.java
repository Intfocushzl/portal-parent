package com.yonghui.portal.service.report;

import com.yonghui.portal.model.report.PortalProcedure;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-17 18:15:12
 */
public interface PortalProcedureService {
	
	PortalProcedure queryObject(Integer id);

	PortalProcedure queryObjectByProcode(String procode);

	List<PortalProcedure> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalProcedure portalProcedure);
	
	void update(PortalProcedure portalProcedure);
	
	void delete(Integer id);
	
	void deleteBatch(String[] procodes);
}
