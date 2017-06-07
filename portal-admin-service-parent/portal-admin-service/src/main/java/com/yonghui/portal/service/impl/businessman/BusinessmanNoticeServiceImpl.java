package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeMapper;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.service.businessman.BusinessmanNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanNoticeService")
public class BusinessmanNoticeServiceImpl implements BusinessmanNoticeService {
	@Autowired
	private BusinessmanNoticeMapper businessmanNoticeMapper;
	
	@Override
	public BusinessmanNotice queryObject(Long id){
		return businessmanNoticeMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanNotice> queryList(Map<String, Object> map){
		return businessmanNoticeMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanNoticeMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanNotice businessmanNotice){
		businessmanNoticeMapper.save(businessmanNotice);
	}
	
	@Override
	public void update(BusinessmanNotice businessmanNotice){
		businessmanNoticeMapper.update(businessmanNotice);
	}
	
	@Override
	public void delete(Long id){
		businessmanNoticeMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanNoticeMapper.deleteBatch(ids);
	}
	
}
