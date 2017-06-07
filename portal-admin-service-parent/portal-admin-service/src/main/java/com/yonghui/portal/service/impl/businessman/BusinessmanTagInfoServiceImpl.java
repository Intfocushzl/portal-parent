package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanTagInfoMapper;
import com.yonghui.portal.model.businessman.BusinessmanTagInfo;
import com.yonghui.portal.service.businessman.BusinessmanTagInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("businessmanTagInfoService")
public class BusinessmanTagInfoServiceImpl implements BusinessmanTagInfoService {
	@Autowired
	private BusinessmanTagInfoMapper businessmanTagInfoMapper;
	
	@Override
	public BusinessmanTagInfo queryObject(Long id){
		return businessmanTagInfoMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanTagInfo> queryList(Map<String, Object> map){
		return businessmanTagInfoMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanTagInfoMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanTagInfo businessmanTagInfo){
		businessmanTagInfoMapper.save(businessmanTagInfo);
	}
	
	@Override
	public void update(BusinessmanTagInfo businessmanTagInfo){
		businessmanTagInfoMapper.update(businessmanTagInfo);
	}
	
	@Override
	public void delete(Long id){
		businessmanTagInfoMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanTagInfoMapper.deleteBatch(ids);
	}
	
}
