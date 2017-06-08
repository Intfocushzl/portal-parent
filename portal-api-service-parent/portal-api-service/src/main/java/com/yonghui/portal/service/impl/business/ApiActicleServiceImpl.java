package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicle;
import com.yonghui.portal.service.business.ApiActicleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiActicleServiceImpl implements ApiActicleService {
	@Autowired
	private BusinessmanActicleMapper businessmanActicleMapper;
	
	@Override
	public BusinessmanActicle queryObject(Long id){
		return businessmanActicleMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanActicle> queryList(Map<String, Object> map){
		return businessmanActicleMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanActicleMapper.queryTotal(map);
	}

	public List<Map<String, Object>> acticleList(Map<String, Object> map){
		return businessmanActicleMapper.acticleList(map);
	}
	
}
