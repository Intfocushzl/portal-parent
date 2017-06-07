package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanNotice;

import java.util.List;
import java.util.Map;

/**
 * 生意人公告信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanNoticeService {
	
	BusinessmanNotice queryObject(Long id);
	
	List<BusinessmanNotice> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanNotice businessmanNotice);
	
	void update(BusinessmanNotice businessmanNotice);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
