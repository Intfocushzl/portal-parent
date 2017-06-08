package com.yonghui.portal.service.business;


import com.yonghui.portal.model.businessman.BusinessmanComment;

import java.util.List;
import java.util.Map;

public interface ApiCommentService {

	BusinessmanComment queryObject(Long id);
	
	List<BusinessmanComment> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanComment businessmanComment);
}
