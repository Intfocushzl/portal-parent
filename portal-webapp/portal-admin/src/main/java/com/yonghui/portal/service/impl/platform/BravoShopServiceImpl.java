package com.yonghui.portal.service.impl.platform;

import com.yonghui.portal.mapper.platform.BravoShopMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.platform.BravoShop;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.platform.BravoShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("bravoShopService")
public class BravoShopServiceImpl implements BravoShopService {
	@Autowired
	private BravoShopMapper bravoShopMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public void savelog(SysLog sysLog) {
		sysLogMapper.save(sysLog);
	}
	@Override
	public BravoShop queryObject(String shopid){
		return bravoShopMapper.queryObject(shopid);
	}
	
	@Override
	public List<BravoShop> queryList(Map<String, Object> map){
		return bravoShopMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return bravoShopMapper.queryTotal(map);
	}
	
	@Override
	public void save(BravoShop bravoShop){
		bravoShopMapper.save(bravoShop);
	}
	
	@Override
	public void update(BravoShop bravoShop){
		bravoShopMapper.update(bravoShop);
	}
	
	@Override
	public void delete(String shopid){
		bravoShopMapper.delete(shopid);
	}
	
	@Override
	public void deleteBatch(String[] shopids){
		bravoShopMapper.deleteBatch(shopids);
	}
	
}
