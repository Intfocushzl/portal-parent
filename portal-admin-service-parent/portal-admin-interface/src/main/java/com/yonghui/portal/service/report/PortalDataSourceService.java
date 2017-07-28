package com.yonghui.portal.service.report;


import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.sys.SysLog;

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

	void savelog(SysLog sysLog);

	PortalDataSource queryObject(Integer id);
	
	List<PortalDataSource> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PortalDataSource portalDataSource);
	
	void update(PortalDataSource portalDataSource);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void deleteBatchByCodes(String[] codes);

    PortalDataSource queryObjectByCode(String code);

	/**
	 * 产生新的编码
	 *
	 * @return
	 */
	String getNewMaxCode();
}
