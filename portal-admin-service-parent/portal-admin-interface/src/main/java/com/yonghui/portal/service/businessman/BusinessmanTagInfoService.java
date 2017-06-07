package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanTagInfo;

import java.util.List;
import java.util.Map;

/**
 * 标签信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanTagInfoService {
	
	BusinessmanTagInfo queryObject(Long id);
	
	List<BusinessmanTagInfo> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanTagInfo businessmanTagInfo);
	
	void update(BusinessmanTagInfo businessmanTagInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
