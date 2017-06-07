package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeLogMapper;
import com.yonghui.portal.model.businessman.BusinessmanNoticeLog;
import com.yonghui.portal.service.businessman.BusinessmanNoticeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanNoticeLogService")
public class BusinessmanNoticeLogServiceImpl implements BusinessmanNoticeLogService {
	@Autowired
	private BusinessmanNoticeLogMapper businessmanNoticeLogMapper;
	
	@Override
	public BusinessmanNoticeLog queryObject(Long id){
		return businessmanNoticeLogMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanNoticeLog> queryList(Map<String, Object> map){
		return businessmanNoticeLogMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanNoticeLogMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanNoticeLog businessmanNoticeLog){
		businessmanNoticeLogMapper.save(businessmanNoticeLog);
	}
	
	@Override
	public void update(BusinessmanNoticeLog businessmanNoticeLog){
		businessmanNoticeLogMapper.update(businessmanNoticeLog);
	}
	
	@Override
	public void delete(Long id){
		businessmanNoticeLogMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanNoticeLogMapper.deleteBatch(ids);
	}
	
}
