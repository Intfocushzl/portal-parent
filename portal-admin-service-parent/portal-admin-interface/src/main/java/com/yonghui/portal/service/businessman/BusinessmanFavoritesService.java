package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanFavorites;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanFavoritesService {
	
	BusinessmanFavorites queryObject(Long id);
	
	List<BusinessmanFavorites> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanFavorites businessmanFavorites);
	
	void update(BusinessmanFavorites businessmanFavorites);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
