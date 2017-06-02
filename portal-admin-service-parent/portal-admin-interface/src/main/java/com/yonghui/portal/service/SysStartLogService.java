package com.yonghui.portal.service;

import com.yonghui.portal.model.sys.SysStartLog;

import java.util.List;
import java.util.Map;

/**
 * 系统启动日志记录
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-01 17:32:54
 */
public interface SysStartLogService {
	
	SysStartLog queryObject(Long id);
	
	List<SysStartLog> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysStartLog sysStartLog);
	
	void update(SysStartLog sysStartLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
