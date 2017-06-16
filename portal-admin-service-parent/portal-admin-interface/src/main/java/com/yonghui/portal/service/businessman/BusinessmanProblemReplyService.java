package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanProblemReply;

import java.util.List;
import java.util.Map;

/**
 * 用户问题回复信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface BusinessmanProblemReplyService {
	
	BusinessmanProblemReply queryObject(Long id);
	
	List<BusinessmanProblemReply> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanProblemReply businessmanProblemReply);
	
	void update(BusinessmanProblemReply businessmanProblemReply);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
