package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanSubjectInfoMapper;
import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;
import com.yonghui.portal.service.business.ApiSubjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiSubjectInfoServiceImpl implements ApiSubjectInfoService {
	@Autowired
	private BusinessmanSubjectInfoMapper businessmanSubjectInfoMapper;
	
	@Override
	public BusinessmanSubjectInfo queryObject(Long id){
		return businessmanSubjectInfoMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanSubjectInfo> queryList(Map<String, Object> map){
		return businessmanSubjectInfoMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanSubjectInfoMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanSubjectInfo businessmanSubjectInfo){
		businessmanSubjectInfoMapper.save(businessmanSubjectInfo);
	}
	
	@Override
	public void update(BusinessmanSubjectInfo businessmanSubjectInfo){
		businessmanSubjectInfoMapper.update(businessmanSubjectInfo);
	}
	
	@Override
	public void delete(Long id){
		businessmanSubjectInfoMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanSubjectInfoMapper.deleteBatch(ids);
	}

	@Override
    public List<BusinessmanSubjectInfo> acticleSubjectSelected(){
        return businessmanSubjectInfoMapper.acticleSubjectSelected();
    }

    @Override
    public List<BusinessmanSubjectInfo> getActicleSubjectSelected(){
        return businessmanSubjectInfoMapper.getActicleSubjectSelected();
    }
}
