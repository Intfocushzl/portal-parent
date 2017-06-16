package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanNoticeLog;

import java.util.List;
import java.util.Map;

/**
 * 生意人公告信息阅读日志
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface BusinessmanNoticeLogService {
	
	BusinessmanNoticeLog queryObject(Long id);
	
	List<BusinessmanNoticeLog> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanNoticeLog businessmanNoticeLog);
	
	void update(BusinessmanNoticeLog businessmanNoticeLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
