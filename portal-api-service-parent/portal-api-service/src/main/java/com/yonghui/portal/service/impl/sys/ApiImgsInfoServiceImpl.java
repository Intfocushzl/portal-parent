package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.businessman.ImgsInfoMapper;
import com.yonghui.portal.model.businessman.ImgsInfo;
import com.yonghui.portal.service.sys.ApiImgsInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiImgsInfoServiceImpl implements ApiImgsInfoService {
    @Autowired
    private ImgsInfoMapper imgsInfoMapper;

    @Override
    public ImgsInfo queryObject(Long id) {
        return imgsInfoMapper.queryObject(id);
    }

    @Override
    public List<ImgsInfo> queryList(Map<String, Object> map) {
        return imgsInfoMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return imgsInfoMapper.queryTotal(map);
    }

    @Override
    public void save(ImgsInfo imgsInfo) {
        imgsInfoMapper.save(imgsInfo);
    }

    @Override
    public void update(ImgsInfo imgsInfo) {
        imgsInfoMapper.update(imgsInfo);
    }

    @Override
    public void delete(Long id) {
        imgsInfoMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        imgsInfoMapper.deleteBatch(ids);
    }

}
