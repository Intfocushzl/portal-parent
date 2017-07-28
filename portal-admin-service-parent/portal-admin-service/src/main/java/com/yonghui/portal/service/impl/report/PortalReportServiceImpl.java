package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.PortalReportMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.report.PortalReport;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.report.PortalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalReportService")
public class PortalReportServiceImpl implements PortalReportService {
	@Autowired
	private PortalReportMapper portalReportMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public void savelog(SysLog sysLog) {
		sysLogMapper.save(sysLog);
	}
	@Override
	public PortalReport queryObject(Integer id){
		return portalReportMapper.queryObject(id);
	}

	@Override
	public PortalReport queryObjectByCode(String code) {
		return portalReportMapper.queryObjectByCode(code);
	}

	@Override
	public List<PortalReport> queryList(Map<String, Object> map){
		return portalReportMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return portalReportMapper.queryTotal(map);
	}
	
	@Override
	public void save(PortalReport portalReport){
		portalReportMapper.save(portalReport);
	}
	
	@Override
	public void update(PortalReport portalReport){
		portalReportMapper.update(portalReport);
	}
	
	@Override
	public void delete(Integer id){
		portalReportMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		portalReportMapper.deleteBatch(ids);
	}

	@Override
	public void deleteBatchByCodes(String[] codes) {
		portalReportMapper.deleteBatchByCodes(codes);
	}

	@Override
	public String getNewMaxCode() {
		return portalReportMapper.getNewMaxCode();
	}
}
