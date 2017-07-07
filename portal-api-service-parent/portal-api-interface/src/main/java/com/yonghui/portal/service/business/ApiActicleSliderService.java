package com.yonghui.portal.service.business;

import com.yonghui.portal.model.businessman.BusinessmanActicleSlider;

import java.util.List;
import java.util.Map;

/**
 * 文章轮播
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:31
 */
public interface ApiActicleSliderService {
	
	BusinessmanActicleSlider queryObject(Long id);
	
	List<BusinessmanActicleSlider> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanActicleSlider businessmanActicleSlider);
	
	void update(BusinessmanActicleSlider businessmanActicleSlider);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void deleteAll();

    List<BusinessmanActicleSlider> queryAllList();

    void saveSlider(Map<String, Object> map);

	List<Map<String, Object>> querySlider();
}
