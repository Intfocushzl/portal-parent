package com.yonghui.portal.service.businessman;


import com.yonghui.portal.model.businessman.BusinessmanActicleGrade;

import java.util.List;
import java.util.Map;

/**
 * 文章评分
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-07-06 09:49:53
 */
public interface BusinessmanActicleGradeService {
	
	BusinessmanActicleGrade queryObject(Long id);
	
	List<BusinessmanActicleGrade> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanActicleGrade businessmanActicleGrade);
	
	void update(BusinessmanActicleGrade businessmanActicleGrade);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    List<BusinessmanActicleGrade> getListByActicleId(Integer id);
}
