package com.yonghui.portal.service.business;

import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;

import java.util.List;
import java.util.Map;

/**
 * 专题信息表
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public interface ApiSubjectInfoService {
	
	BusinessmanSubjectInfo queryObject(Long id);
	
	List<BusinessmanSubjectInfo> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BusinessmanSubjectInfo businessmanSubjectInfo);
	
	void update(BusinessmanSubjectInfo businessmanSubjectInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    List<BusinessmanSubjectInfo> acticleSubjectSelected();

    List<BusinessmanSubjectInfo> getActicleSubjectSelected();
}
