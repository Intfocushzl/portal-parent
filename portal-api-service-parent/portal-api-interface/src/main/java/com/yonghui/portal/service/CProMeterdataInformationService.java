package com.yonghui.portal.service;

import com.yonghui.portal.model.sys.CProMeterdataInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/6.
 */
public interface CProMeterdataInformationService {

    List<CProMeterdataInformation> getProcedureList(Map<String, Object> map);

    CProMeterdataInformation getprocDetail(Long id);

    Map<String,Object> getprocSqlDetail(String proname);

    List<CProMeterdataInformation> getOwner();

    List<CProMeterdataInformation> getProdb();

    List<Map<String,Object>> getAddOwner();

    List<Map<String,Object>> getAddProdb();

    Integer insertSelective(CProMeterdataInformation cmi);

    CProMeterdataInformation selectByPrimaryKey(Long id);

    Integer updateByPrimaryKeySelective(CProMeterdataInformation cmi);
}
