package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanCommentMapper;
import com.yonghui.portal.model.businessman.BusinessmanComment;
import com.yonghui.portal.service.business.ApiCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanCommentService")
public class ApiCommentServiceImpl implements ApiCommentService {
	@Autowired
	private BusinessmanCommentMapper businessmanCommentMapper;
	
	@Override
	public BusinessmanComment queryObject(Long id){
		return businessmanCommentMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanComment> queryList(Map<String, Object> map){
		return businessmanCommentMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanCommentMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanComment businessmanComment){
		businessmanCommentMapper.save(businessmanComment);
	}
	
	@Override
	public void update(BusinessmanComment businessmanComment){
		businessmanCommentMapper.update(businessmanComment);
	}
	
	@Override
	public void delete(Long id){
		businessmanCommentMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanCommentMapper.deleteBatch(ids);
	}
	
}
