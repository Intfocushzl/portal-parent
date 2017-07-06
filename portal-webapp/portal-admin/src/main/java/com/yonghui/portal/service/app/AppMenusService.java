package com.yonghui.portal.service.app;


import com.yonghui.portal.model.app.AppMenu;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:20
 */
public interface AppMenusService {
	
	AppMenu queryObject(Map<String, Object> map);
	
	List<AppMenu> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppMenu AppMenu);
	
	void update(AppMenu AppMenu);
	
	void delete(Map<String, Object> map);
	
	void deleteBatch(Map<String, Object> map);

    List<AppMenu> queryAllMenuList();
}
