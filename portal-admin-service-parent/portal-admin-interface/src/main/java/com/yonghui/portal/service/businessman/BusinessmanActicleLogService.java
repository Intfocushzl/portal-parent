package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanActicleLog;

import java.util.List;
import java.util.Map;

/**
 * 生意人文章信息阅读日志
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanActicleLogService {
	
	BusinessmanActicleLog queryObject(Long id);
	
	List<BusinessmanActicleLog> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanActicleLog businessmanActicleLog);
	
	void update(BusinessmanActicleLog businessmanActicleLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<Map<String, Object>> queryIsSee(Map<String, Object> map);

	List<BusinessmanActicleLog> getListByArticleId(Integer id);
}
