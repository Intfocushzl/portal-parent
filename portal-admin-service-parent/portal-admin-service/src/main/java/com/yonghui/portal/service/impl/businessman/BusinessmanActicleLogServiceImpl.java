package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleLogMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleLog;
import com.yonghui.portal.service.businessman.BusinessmanActicleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("businessmanActicleLogService")
public class BusinessmanActicleLogServiceImpl implements BusinessmanActicleLogService {
	@Autowired
	private BusinessmanActicleLogMapper businessmanActicleLogMapper;
	
	@Override
	public BusinessmanActicleLog queryObject(Long id){
		return businessmanActicleLogMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanActicleLog> queryList(Map<String, Object> map){
		return businessmanActicleLogMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanActicleLogMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanActicleLog businessmanActicleLog){
		businessmanActicleLogMapper.save(businessmanActicleLog);
	}
	
	@Override
	public void update(BusinessmanActicleLog businessmanActicleLog){
		businessmanActicleLogMapper.update(businessmanActicleLog);
	}
	
	@Override
	public void delete(Long id){
		businessmanActicleLogMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanActicleLogMapper.deleteBatch(ids);
	}

    public List<Map<String, Object>> queryIsSee(Map<String, Object> map){
       return businessmanActicleLogMapper.queryIsSee(map);
	}

	@Override
	public List<BusinessmanActicleLog> getListByArticleId(Integer id) {
		return businessmanActicleLogMapper.getListByArticleId(id);
	}
}
