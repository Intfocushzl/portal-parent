package com.yonghui.portal.service.impl.cindex;

import com.yonghui.portal.mapper.cindex.CIndexReferMapper;
import com.yonghui.portal.model.cindex.CIndexRefer;
import com.yonghui.portal.service.cindex.CIndexReferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("cIndexReferService")
public class CIndexReferServiceImpl implements CIndexReferService {
	@Autowired
	private CIndexReferMapper cIndexReferMapper;
	
	@Override
	public CIndexRefer queryObject(Integer id){
		return cIndexReferMapper.queryObject(id);
	}
	
	@Override
	public List<CIndexRefer> queryList(Map<String, Object> map){
		return cIndexReferMapper.queryList(map);
	}

	@Override
	public List<CIndexRefer> queryListOpt() {
		return cIndexReferMapper.queryListOpt();
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return cIndexReferMapper.queryTotal(map);
	}
	
	@Override
	public void save(CIndexRefer cIndexRefer){
		cIndexReferMapper.save(cIndexRefer);
	}
	
	@Override
	public void update(CIndexRefer cIndexRefer){
		cIndexReferMapper.update(cIndexRefer);
	}
	
	@Override
	public void delete(Integer id){
		cIndexReferMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		cIndexReferMapper.deleteBatch(ids);
	}
	
}
