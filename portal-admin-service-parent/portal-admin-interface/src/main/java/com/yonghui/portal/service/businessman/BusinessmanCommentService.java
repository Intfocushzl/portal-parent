package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanComment;

import java.util.List;
import java.util.Map;

/**
 * 用户评论信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanCommentService {
	
	BusinessmanComment queryObject(Long id);
	
	List<BusinessmanComment> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanComment businessmanComment);
	
	void update(BusinessmanComment businessmanComment);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
