package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanProblemReplyMapper;
import com.yonghui.portal.model.businessman.BusinessmanProblemReply;
import com.yonghui.portal.service.businessman.BusinessmanProblemReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanProblemReplyService")
public class BusinessmanProblemReplyServiceImpl implements BusinessmanProblemReplyService {
	@Autowired
	private BusinessmanProblemReplyMapper businessmanProblemReplyMapper;
	
	@Override
	public BusinessmanProblemReply queryObject(Long id){
		return businessmanProblemReplyMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanProblemReply> queryList(Map<String, Object> map){
		return businessmanProblemReplyMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanProblemReplyMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanProblemReply businessmanProblemReply){
		businessmanProblemReplyMapper.save(businessmanProblemReply);
	}
	
	@Override
	public void update(BusinessmanProblemReply businessmanProblemReply){
		businessmanProblemReplyMapper.update(businessmanProblemReply);
	}
	
	@Override
	public void delete(Long id){
		businessmanProblemReplyMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanProblemReplyMapper.deleteBatch(ids);
	}
	
}
