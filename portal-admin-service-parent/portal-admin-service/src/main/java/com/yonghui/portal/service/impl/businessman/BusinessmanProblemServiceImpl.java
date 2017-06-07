package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanProblemMapper;
import com.yonghui.portal.model.businessman.BusinessmanProblem;
import com.yonghui.portal.service.businessman.BusinessmanProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("businessmanProblemService")
public class BusinessmanProblemServiceImpl implements BusinessmanProblemService {
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
	public void update(BusinessmanProblem businessmanProblem){
		businessmanProblemMapper.update(businessmanProblem);
	}
	
	@Override
	public void delete(Long id){
		businessmanProblemMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanProblemMapper.deleteBatch(ids);
	}
	
}
