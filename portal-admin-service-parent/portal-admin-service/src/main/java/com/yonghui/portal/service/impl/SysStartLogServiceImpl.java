package com.yonghui.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.mapper.sys.SysStartLogMapper;
import com.yonghui.portal.model.sys.SysStartLog;
import com.yonghui.portal.service.SysStartLogService;

@Service("sysStartLogService")
public class SysStartLogServiceImpl implements SysStartLogService {
	@Autowired
	private SysStartLogMapper sysStartLogMapper;
	
	@Override
	public SysStartLog queryObject(Long id){
		return sysStartLogMapper.queryObject(id);
	}
	
	@Override
	public List<SysStartLog> queryList(Map<String, Object> map){
		return sysStartLogMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysStartLogMapper.queryTotal(map);
	}
	
	@Override
	public void save(SysStartLog sysStartLog){
		sysStartLogMapper.save(sysStartLog);
	}
	
	@Override
	public void update(SysStartLog sysStartLog){
		sysStartLogMapper.update(sysStartLog);
	}
	
	@Override
	public void delete(Long id){
		sysStartLogMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysStartLogMapper.deleteBatch(ids);
	}
	
}
