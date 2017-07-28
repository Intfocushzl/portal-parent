package com.yonghui.portal.mapper.app;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.model.message.JobInformation;
import com.yonghui.portal.model.message.JobInformationWithBLOBs;
public interface JobInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobInformationWithBLOBs record);

    int insertSelective(JobInformationWithBLOBs record);

    JobInformationWithBLOBs selectByPrimaryKey(Integer id);

    JobInformationWithBLOBs selectByJobKey(String key);

    int updateByPrimaryKeySelective(JobInformationWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(JobInformationWithBLOBs record);

    int updateByPrimaryKey(JobInformation record);

	List<JobInformationWithBLOBs> selectJobAll(Map<String , Object> where);
}