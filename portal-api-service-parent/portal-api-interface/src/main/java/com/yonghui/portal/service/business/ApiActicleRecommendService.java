package com.yonghui.portal.service.business;

import com.yonghui.portal.model.businessman.BusinessmanActicleRecommend;

import java.util.List;
import java.util.Map;

/**
 * 文章推荐置顶
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public interface ApiActicleRecommendService {
	
	BusinessmanActicleRecommend queryObject(Long id);
	
	List<BusinessmanActicleRecommend> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanActicleRecommend businessmanActicleRecommend);
	
	void update(BusinessmanActicleRecommend businessmanActicleRecommend);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void deleteAll();

    List<BusinessmanActicleRecommend> queryAllList();

    void saveRecommend(Map<String, Object> map);

	List<Map<String, Object>> queryRecommend();
}
