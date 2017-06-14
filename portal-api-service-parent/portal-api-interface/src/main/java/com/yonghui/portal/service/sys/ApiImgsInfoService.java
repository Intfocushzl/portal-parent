package com.yonghui.portal.service.sys;


import com.yonghui.portal.model.businessman.ImgsInfo;

import java.util.List;
import java.util.Map;

/**
 * 图片信息
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface ApiImgsInfoService {
	
	ImgsInfo queryObject(Long id);
	
	List<ImgsInfo> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ImgsInfo imgsInfo);
	
	void update(ImgsInfo imgsInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
