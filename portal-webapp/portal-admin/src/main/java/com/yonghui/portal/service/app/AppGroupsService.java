package com.yonghui.portal.service.app;

import com.yonghui.portal.model.app.AppGroups;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public interface AppGroupsService {
	
	AppGroups queryObject(Integer id);
	
	List<AppGroups> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppGroups appGroups);
	
	void update(AppGroups appGroups);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
