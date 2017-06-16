package com.yonghui.portal.service.app;

import com.yonghui.portal.model.app.AppUsers;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:18
 */
public interface AppUsersService {
	
	AppUsers queryObject(Integer id);
	
	List<AppUsers> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppUsers appUsers);
	
	void update(AppUsers appUsers);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
