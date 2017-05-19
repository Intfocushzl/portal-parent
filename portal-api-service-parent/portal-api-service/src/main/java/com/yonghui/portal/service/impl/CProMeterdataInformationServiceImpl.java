package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.sys.CProMeterdataInformationMapper;
import com.yonghui.portal.model.sys.CProMeterdataInformation;
import com.yonghui.portal.service.CProMeterdataInformationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/6.
 * 存储过程管理
 */
public class CProMeterdataInformationServiceImpl implements CProMeterdataInformationService{

    @Autowired
    private CProMeterdataInformationMapper cProMeterdataInformationMapper;

    @Override
    public List<CProMeterdataInformation> getProcedureList(Map<String, Object> map) {
        return cProMeterdataInformationMapper.getProcedureList(map);
    }

    @Override
    public CProMeterdataInformation getprocDetail(Long id) {
        return cProMeterdataInformationMapper.getprocDetail(id);
    }

    @Override
    public Map<String, Object> getprocSqlDetail(String proname) {
        return cProMeterdataInformationMapper.getprocSqlDetail(proname);
    }

    @Override
    public List<CProMeterdataInformation> getOwner() {
        return cProMeterdataInformationMapper.getOwner();
    }

    @Override
    public List<CProMeterdataInformation> getProdb() {
        return cProMeterdataInformationMapper.getProdb();
    }

    @Override
    public List<Map<String, Object>> getAddOwner() {
        return cProMeterdataInformationMapper.getAddOwner();
    }

    @Override
    public List<Map<String, Object>> getAddProdb() {
        return cProMeterdataInformationMapper.getAddProdb();
    }

    @Override
    public Integer insertSelective(CProMeterdataInformation cmi) {
        return cProMeterdataInformationMapper.insertSelective(cmi);
    }

    @Override
    public CProMeterdataInformation selectByPrimaryKey(Long id) {
        return cProMeterdataInformationMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(CProMeterdataInformation cmi) {
        return cProMeterdataInformationMapper.updateByPrimaryKeySelective(cmi);
    }
}
