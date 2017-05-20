package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.api.PortalDataSourceMapper;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.service.PortalDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalDataSourceService")
public class PortalDataSourceServiceImpl implements PortalDataSourceService {
	@Autowired
	private PortalDataSourceMapper portalDataSourceMapper;
	
	@Override
	public PortalDataSource queryObject(Integer id){
		return portalDataSourceMapper.queryObject(id);
	}
	
	@Override
	public List<PortalDataSource> queryList(Map<String, Object> map){
		return portalDataSourceMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return portalDataSourceMapper.queryTotal(map);
	}
	
	@Override
	public void save(PortalDataSource portalDataSource){
		portalDataSourceMapper.save(portalDataSource);
	}
	
	@Override
	public void update(PortalDataSource portalDataSource){
		portalDataSourceMapper.update(portalDataSource);
	}
	
	@Override
	public void delete(Integer id){
		portalDataSourceMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		portalDataSourceMapper.deleteBatch(ids);
	}
	
}
