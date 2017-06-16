package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanActicle;

import java.util.List;
import java.util.Map;

/**
 * 生意人数据学院文章信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanActicleService {
	
	BusinessmanActicle queryObject(Long id);
	
	List<BusinessmanActicle> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanActicle businessmanActicle);
	
	void update(BusinessmanActicle businessmanActicle);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
