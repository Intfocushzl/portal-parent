package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanFavoritesMapper;
import com.yonghui.portal.model.businessman.BusinessmanFavorites;
import com.yonghui.portal.service.business.ApiFavoritesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class ApiFavoritesServiceImpl implements ApiFavoritesService {
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

	@Override
	public List<Map<String, Object>> favoriteList(Map<String, Object> map){
		return businessmanFavoritesMapper.favoriteList(map);
	}

	@Override
	public void editfavorite(Map<String, Object> map){
		 businessmanFavoritesMapper.editfavorite(map);
	}

	public BusinessmanFavorites favoriteDetail(Map<String, Object> map){
		return businessmanFavoritesMapper.favoriteDetail(map);
	}
}
