package com.yonghui.portal.mapper;


import com.yonghui.portal.model.SysGenerator;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 */
public interface SysGeneratorMapper {

    SysGenerator queryObject(Object id);

    void save(SysGenerator t);

    int update(SysGenerator t);

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
