package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.PortalExecuteSqlMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.report.PortalExecuteSql;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.report.PortalExecuteSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalExecuteSqlService")
public class PortalExecuteSqlServiceImpl implements PortalExecuteSqlService {
	@Autowired
	private PortalExecuteSqlMapper portalExecuteSqlMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public void savelog(SysLog sysLog) {
		sysLogMapper.save(sysLog);
	}
	
	@Override
	public PortalExecuteSql queryObject(Integer id){
		return portalExecuteSqlMapper.queryObject(id);
	}

	@Override
	public PortalExecuteSql queryObjectBySqlcode(String sqlcode){
		return portalExecuteSqlMapper.queryObjectBySqlcode(sqlcode);
	}
	
	@Override
	public List<PortalExecuteSql> queryList(Map<String, Object> map){
		return portalExecuteSqlMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return portalExecuteSqlMapper.queryTotal(map);
	}
	
	@Override
	public void save(PortalExecuteSql portalExecuteSql){
		portalExecuteSqlMapper.save(portalExecuteSql);
	}
	
	@Override
	public void update(PortalExecuteSql portalExecuteSql){
		portalExecuteSqlMapper.update(portalExecuteSql);
	}
	
	@Override
	public void delete(Integer id){
		portalExecuteSqlMapper.delete(id);
	}
	
	@Override
	public void deleteBatchBySqlcodes(String[] sqlcodes){
		portalExecuteSqlMapper.deleteBatchBySqlcodes(sqlcodes);
	}

	@Override
	public String getNewMaxCode() {
		return portalExecuteSqlMapper.getNewMaxCode();
	}
}
