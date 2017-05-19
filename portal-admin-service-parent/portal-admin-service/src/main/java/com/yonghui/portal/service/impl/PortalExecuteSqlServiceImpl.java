package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.api.PortalExecuteSqlMapper;
import com.yonghui.portal.model.api.PortalExecuteSql;
import com.yonghui.portal.service.PortalExecuteSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("portalExecuteSqlService")
public class PortalExecuteSqlServiceImpl implements PortalExecuteSqlService {
	@Autowired
	private PortalExecuteSqlMapper portalExecuteSqlMapper;
	
	@Override
	public PortalExecuteSql queryObject(Integer id){
		return portalExecuteSqlMapper.queryObject(id);
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
	public void deleteBatch(Integer[] ids){
		portalExecuteSqlMapper.deleteBatch(ids);
	}
	
}
