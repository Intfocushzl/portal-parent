package com.yonghui.portal.service;


import com.yonghui.portal.model.SysGenerator;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 */
public interface SysGeneratorService {

	/**
	 * 配置管理
	 * @param id
	 * @return
	 */
	SysGenerator queryObject(Long id);
	void save(SysGenerator sysGenerator);
	void update(SysGenerator sysGenerator);

	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	Map<String, String> queryTable(String tableName);
	
	List<Map<String, String>> queryColumns(String tableName);
	
	/**
	 * 生成代码
	 */
	byte[] generatorCode(String[] tableNames);
}
