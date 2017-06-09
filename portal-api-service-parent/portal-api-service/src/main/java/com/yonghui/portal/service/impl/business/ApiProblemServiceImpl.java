package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanProblemMapper;
import com.yonghui.portal.model.businessman.BusinessmanProblem;
import com.yonghui.portal.service.business.ApiProblemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiProblemServiceImpl implements ApiProblemService {
	@Autowired
	private BusinessmanProblemMapper businessmanProblemMapper;
	
	@Override
	public BusinessmanProblem queryObject(Long id){
		return businessmanProblemMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanProblem> queryList(Map<String, Object> map){
		return businessmanProblemMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanProblemMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanProblem businessmanProblem){
		businessmanProblemMapper.save(businessmanProblem);
	}

	@Override
	public List<Map<String, Object>> problemDetail(Map<String, Object> map){
		return businessmanProblemMapper.problemDetail(map);
	}
}
