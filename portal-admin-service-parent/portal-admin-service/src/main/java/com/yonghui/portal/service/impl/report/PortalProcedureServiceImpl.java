package com.yonghui.portal.service.impl.report;

import com.yonghui.portal.mapper.report.PortalProcedureMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.report.PortalProcedureService;
import com.yonghui.portal.util.ComputerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.itextpdf.text.html.HtmlTags.S;

@Service("portalProcedureService")
public class PortalProcedureServiceImpl implements PortalProcedureService {

	@Autowired
	private PortalProcedureMapper portalProcedureMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public void savelog(SysLog sysLog) {
		sysLogMapper.save(sysLog);
	}

	@Override
	public PortalProcedure queryObject(Integer id){
		return portalProcedureMapper.queryObject(id);
	}

	@Override
	public PortalProcedure queryObjectByProcode(String procode){
		return portalProcedureMapper.queryObjectByProcode(procode);
	}
	
	@Override
	public List<PortalProcedure> queryList(Map<String, Object> map){
		return portalProcedureMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return portalProcedureMapper.queryTotal(map);
	}
	
	@Override
	public void save(PortalProcedure portalProcedure){
		portalProcedureMapper.save(portalProcedure);
	}
	
	@Override
	public void update(PortalProcedure portalProcedure){
		portalProcedureMapper.update(portalProcedure);
	}
	
	@Override
	public void delete(Integer id){
		portalProcedureMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] procodes){
		portalProcedureMapper.deleteBatchByProcodes(procodes);
	}

	@Override
	public String getNewMaxCode() {
		return portalProcedureMapper.getNewMaxCode();
	}
}
