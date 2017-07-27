package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.PortalOpenapiReportMapper;
import com.yonghui.portal.model.report.PortalOpenapiReport;
import com.yonghui.portal.service.report.PortalOpenapiReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalOpenapiReportService")
public class PortalOpenapiReportServiceImpl implements PortalOpenapiReportService {

	@Autowired
	private PortalOpenapiReportMapper portalOpenapiReportMapper;
	
	@Override
	public PortalOpenapiReport queryObject(Integer id){
		return portalOpenapiReportMapper.queryObject(id);
	}
	
	@Override
	public List<PortalOpenapiReport> queryList(Map<String, Object> map){
		return portalOpenapiReportMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return portalOpenapiReportMapper.queryTotal(map);
	}
	
	@Override
	public void save(PortalOpenapiReport portalOpenapiReport){
		portalOpenapiReportMapper.save(portalOpenapiReport);
	}
	
	@Override
	public void update(PortalOpenapiReport portalOpenapiReport){
		portalOpenapiReportMapper.update(portalOpenapiReport);
	}
	
	@Override
	public void delete(Integer id){
		portalOpenapiReportMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] codes){
		portalOpenapiReportMapper.deleteBatch(codes);
	}

	@Override
	public String getNewMaxCode() {
		return portalOpenapiReportMapper.getNewMaxCode();
	}

}
