package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanNoticeMapper;
import com.yonghui.portal.model.businessman.BusinessmanNotice;
import com.yonghui.portal.service.businessman.BusinessmanNoticeService;
import com.yonghui.portal.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sun.tools.doclint.Entity.nu;


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
		if(businessmanNotice.getStatus() ==1){ //保存草稿  状态1 草稿字段不为空  正文为空 禁用
			businessmanNotice.setContentManuscript(businessmanNotice.getContent());
			businessmanNotice.setContent(null);
			businessmanNotice.setDisabled(1L);
		}else if(businessmanNotice.getStatus() == 2){//发布  状态2     启用
			businessmanNotice.setDisabled(0L);
			businessmanNotice.setContentManuscript(businessmanNotice.getContent());
		}
		businessmanNoticeMapper.save(businessmanNotice);
	}
	
	@Override
	public void update(BusinessmanNotice businessmanNotice){
		if(businessmanNotice.getStatus()==1){//发布完修改存草稿  都不为空 草稿字段新 内容老  状态2   启用
			businessmanNotice.setStatus(2L);
			businessmanNotice.setContentManuscript(businessmanNotice.getContent());
			businessmanNotice.setContent(businessmanNotice.getOldContent());
			businessmanNotice.setDisabled(0L);
		}else if(businessmanNotice.getStatus()==2){//发不完修改发布			一样最新		2		启用
			businessmanNotice.setDisabled(0L);
			businessmanNotice.setContentManuscript(businessmanNotice.getContent());
		}
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
