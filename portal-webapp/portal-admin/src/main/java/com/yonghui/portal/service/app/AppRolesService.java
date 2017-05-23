package com.yonghui.portal.service.app;


import com.yonghui.portal.model.app.AppRoles;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:19
 */
public interface AppRolesService {
	
	AppRoles queryObject(Integer id);
	
	List<AppRoles> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppRoles appRoles);
	
	void update(AppRoles appRoles);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
