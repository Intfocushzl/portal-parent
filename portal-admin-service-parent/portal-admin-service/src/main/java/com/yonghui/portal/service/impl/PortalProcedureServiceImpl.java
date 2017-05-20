package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.api.PortalProcedureMapper;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.service.PortalProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalProcedureService")
public class PortalProcedureServiceImpl implements PortalProcedureService {

	@Autowired
	private PortalProcedureMapper portalProcedureMapper;
	
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
	
}
