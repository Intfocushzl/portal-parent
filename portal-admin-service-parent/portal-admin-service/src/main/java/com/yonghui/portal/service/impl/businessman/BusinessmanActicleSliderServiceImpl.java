package com.yonghui.portal.service.impl.businessman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleSliderMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleSlider;
import com.yonghui.portal.service.businessman.BusinessmanActicleSliderService;

@Service("businessmanActicleSliderService")
public class BusinessmanActicleSliderServiceImpl implements BusinessmanActicleSliderService {
	@Autowired
	private BusinessmanActicleSliderMapper businessmanActicleSliderMapper;
	
	@Override
	public BusinessmanActicleSlider queryObject(Long id){
		return businessmanActicleSliderMapper.queryObject(id);
	}
	
	@Override
	public List<BusinessmanActicleSlider> queryList(Map<String, Object> map){
		return businessmanActicleSliderMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return businessmanActicleSliderMapper.queryTotal(map);
	}
	
	@Override
	public void save(BusinessmanActicleSlider businessmanActicleSlider){
		businessmanActicleSliderMapper.save(businessmanActicleSlider);
	}
	
	@Override
	public void update(BusinessmanActicleSlider businessmanActicleSlider){
		businessmanActicleSliderMapper.update(businessmanActicleSlider);
	}
	
	@Override
	public void delete(Long id){
		businessmanActicleSliderMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		businessmanActicleSliderMapper.deleteBatch(ids);
	}

    @Override
    public void deleteAll(){
        businessmanActicleSliderMapper.deleteAll();
    }

    @Override
    public List<BusinessmanActicleSlider> queryAllList() {
        return businessmanActicleSliderMapper.queryAllList();
    }

    @Override
    public void saveSlider(Map<String, Object> map) {
        businessmanActicleSliderMapper.saveSlider(map);
    }

}
