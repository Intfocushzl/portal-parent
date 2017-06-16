package com.yonghui.portal.service.impl.cindex;

import com.yonghui.portal.mapper.cindex.CIndexApertureMapper;
import com.yonghui.portal.model.cindex.CIndexAperture;
import com.yonghui.portal.service.cindex.CIndexApertureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("cIndexApertureService")
public class CIndexApertureServiceImpl implements CIndexApertureService {
	@Autowired
	private CIndexApertureMapper cIndexApertureMapper;
	
	@Override
	public CIndexAperture queryObject(Integer id){
		return cIndexApertureMapper.queryObject(id);
	}
	
	@Override
	public List<CIndexAperture> queryList(Map<String, Object> map){
		return cIndexApertureMapper.queryList(map);
	}

	@Override
	public List<CIndexAperture> queryListOpt() {
		return cIndexApertureMapper.queryListOpt();
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return cIndexApertureMapper.queryTotal(map);
	}
	
	@Override
	public void save(CIndexAperture cIndexAperture){
		cIndexApertureMapper.save(cIndexAperture);
	}
	
	@Override
	public void update(CIndexAperture cIndexAperture){
		cIndexApertureMapper.update(cIndexAperture);
	}
	
	@Override
	public void delete(Integer id){
		cIndexApertureMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		cIndexApertureMapper.deleteBatch(ids);
	}
	
}
