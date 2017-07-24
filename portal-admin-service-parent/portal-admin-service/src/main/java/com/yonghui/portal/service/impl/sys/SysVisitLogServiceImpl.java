package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.sys.SysOperationLogMapper;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysVisitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysVisitLogService")
public class SysVisitLogServiceImpl implements SysVisitLogService {
	@Autowired
	private SysOperationLogMapper sysOperationLogMapper;
	
	@Override
	public SysOperationLog queryObject(Integer id){
		return sysOperationLogMapper.queryObject(id);
	}
	
	@Override
	public List<SysOperationLog> queryList(Map<String, Object> map){
		return sysOperationLogMapper.queryList(map);
	}

	@Override
	public List<Map<String, Object>> queryVisit(Map<String, Object> map) {
		return sysOperationLogMapper.queryVisit(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysOperationLogMapper.queryTotal(map);
	}
	
	@Override
	public void save(SysOperationLog sysOperationLog){
		sysOperationLogMapper.save(sysOperationLog);
	}
	
	@Override
	public void update(SysOperationLog sysOperationLog){
		sysOperationLogMapper.update(sysOperationLog);
	}
	
	@Override
	public void delete(Integer id){
		sysOperationLogMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysOperationLogMapper.deleteBatch(ids);
	}
	
}
