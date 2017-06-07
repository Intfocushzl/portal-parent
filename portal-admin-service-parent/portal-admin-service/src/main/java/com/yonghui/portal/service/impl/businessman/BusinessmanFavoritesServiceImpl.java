package com.yonghui.portal.service.impl.businessman;

import com.yonghui.portal.mapper.businessman.BusinessmanFavoritesMapper;
import com.yonghui.portal.model.businessman.BusinessmanFavorites;
import com.yonghui.portal.service.businessman.BusinessmanFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("businessmanFavoritesService")
public class BusinessmanFavoritesServiceImpl implements BusinessmanFavoritesService {
	@Autowired
	private BusinessmanFavoritesMapper businessmanFavoritesMapper;
	
	@Override
	public BusinessmanFavorites queryObject(Long id){
		return businessmanFavoritesMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanFavorites> queryList(Map<String, Object> map){
		return businessmanFavoritesMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanFavoritesMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanFavorites businessmanFavorites){
		businessmanFavoritesMapper.save(businessmanFavorites);
	}
	
	@Override
	public void update(BusinessmanFavorites businessmanFavorites){
		businessmanFavoritesMapper.update(businessmanFavorites);
	}
	
	@Override
	public void delete(Long id){
		businessmanFavoritesMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanFavoritesMapper.deleteBatch(ids);
	}
	
}
