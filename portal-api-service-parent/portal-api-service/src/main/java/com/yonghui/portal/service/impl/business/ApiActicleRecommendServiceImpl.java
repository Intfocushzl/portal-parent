package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleRecommendMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleRecommend;
import com.yonghui.portal.service.business.ApiActicleRecommendService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiActicleRecommendServiceImpl implements ApiActicleRecommendService {

	@Autowired
	private BusinessmanActicleRecommendMapper businessmanActicleRecommendMapper;
	
	@Override
	public BusinessmanActicleRecommend queryObject(Long id){
		return businessmanActicleRecommendMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanActicleRecommend> queryList(Map<String, Object> map){
		return businessmanActicleRecommendMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanActicleRecommendMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanActicleRecommend businessmanActicleRecommend){
		businessmanActicleRecommendMapper.save(businessmanActicleRecommend);
	}
	
	@Override
	public void update(BusinessmanActicleRecommend businessmanActicleRecommend){
		businessmanActicleRecommendMapper.update(businessmanActicleRecommend);
	}
	
	@Override
	public void delete(Long id){
		businessmanActicleRecommendMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanActicleRecommendMapper.deleteBatch(ids);
	}
    @Override
    public void deleteAll(){
        businessmanActicleRecommendMapper.deleteAll();
    }

    @Override
    public List<BusinessmanActicleRecommend> queryAllList() {
        return businessmanActicleRecommendMapper.queryAllList();
    }

    @Override
    public void saveRecommend(Map<String, Object> map) {
        businessmanActicleRecommendMapper.saveRecommend(map);
    }

	public List<Map<String, Object>> queryRecommend(){
		return businessmanActicleRecommendMapper.queryRecommend();
	}

}
