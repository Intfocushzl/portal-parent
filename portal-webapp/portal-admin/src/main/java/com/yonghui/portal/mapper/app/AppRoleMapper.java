package com.yonghui.portal.mapper.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.model.message.AppResource;
import com.yonghui.portal.model.message.AppRole;
import com.yonghui.portal.model.message.AppRoleResources;



public interface AppRoleMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AppRole record);

	int insertSelective(AppRole record);

	AppRole selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AppRole record);

	int updateByPrimaryKey(AppRole record);

	List<AppRole> selectAll();

	List<Map<String, Object>> selectRoleForComboBox();

	List<AppRole> selectRoleBywhere(Map<String, Object> where);

	int getTotal(Map<String, Object> where);

	List<AppRoleResources> selectRoleResources(Map<String, Object> where);
	
	List<AppResource> selectRoleKpiBases(Map<String, Object> where);

	List<AppResource> selectRoleAnalysisBases(Map<String, Object> where);

	List<AppResource> selectRoleAppBases(Map<String, Object> where);
	
	List<AppResource> selectReportsInfo(Map<String, Object> where);
	
	
	void deleteRoleResources(Map<String, Object> where);

	void updateRoleAppBases(Map<String, Object> where);

	void updateRoleAnalysisBases(Map<String, Object> where);

	void updateRoleKpiBases(Map<String, Object> where);

	Integer selectRoleResourcesCount(Map<String, Object> where);

	
	List<AppRoleResources> selectAllWithResId(Map<String, Object> where);

	void insertAppRoleResouces(Map<String, Object> where);
	void updateAppRoleResouces(Map<String, Object> where);
	void deleteAppRoleResouces(Map<String, Object> where);
}