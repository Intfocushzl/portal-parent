package com.yonghui.portal.service.impl.business;

import com.yonghui.portal.mapper.businessman.BusinessmanActicleLogMapper;
import com.yonghui.portal.mapper.businessman.BusinessmanActicleMapper;
import com.yonghui.portal.mapper.businessman.BusinessmanSubjectInfoMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicle;
import com.yonghui.portal.model.businessman.BusinessmanActicleLog;
import com.yonghui.portal.model.businessman.BusinessmanSubjectInfo;
import com.yonghui.portal.service.business.ApiActicleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ApiActicleServiceImpl implements ApiActicleService {
    @Autowired
    private BusinessmanActicleMapper businessmanActicleMapper;
    @Autowired
    private BusinessmanActicleLogMapper businessmanActicleLogMapper;
    @Autowired
    private BusinessmanSubjectInfoMapper businessmanSubjectInfoMapper;

    @Override
    public BusinessmanActicle queryObject(Long id) {
        return businessmanActicleMapper.queryObject(id);
    }

    @Override
    public List<BusinessmanActicle> queryList(Map<String, Object> map) {
        return businessmanActicleMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return businessmanActicleMapper.queryTotal(map);
    }

    @Override
    public List<Map<String, Object>> acticleList(Map<String, Object> map) {
        return businessmanActicleMapper.acticleList(map);
    }

    @Override
    public List<Map<String, Object>> acticleDetail(Map<String, Object> map) {
        List<Map<String, Object>> list = businessmanActicleMapper.acticleDetail(map);
        List<Map<String, Object>> list1 = businessmanActicleLogMapper.queryIsSee(map);
        if (list.size() != 0 && list1.size() != 0) {
            BusinessmanActicleLog log = new BusinessmanActicleLog();
            Long id = Long.parseLong(map.get("id").toString());
            Long creater = Long.parseLong(map.get("userId").toString());
            log.setActicleId(id);
            log.setCreater(creater);
        }
        return list;
    }

    @Override
    public List<BusinessmanSubjectInfo> acticleSubjectSelected() {
        return businessmanSubjectInfoMapper.acticleSubjectSelected();
    }

    public List<Map<String, Object>> acticleListForPc(Map<String, Object> map) {
        return businessmanActicleMapper.acticleListForPc(map);
    }

    public List<Map<String, Object>> acticleDetailForPc(Map<String, Object> map) {
        return businessmanActicleMapper.acticleDetailForPc(map);
    }

}
