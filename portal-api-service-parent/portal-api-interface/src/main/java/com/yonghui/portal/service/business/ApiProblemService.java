package com.yonghui.portal.service.business;


import com.yonghui.portal.model.businessman.BusinessmanProblem;

import java.util.List;
import java.util.Map;

/**
 * 用户问题反馈信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface ApiProblemService {
	
	BusinessmanProblem queryObject(Long id);
	
	List<BusinessmanProblem> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanProblem businessmanProblem);

	List<Map<String, Object>> problemDetail(Map<String, Object> map);
}
