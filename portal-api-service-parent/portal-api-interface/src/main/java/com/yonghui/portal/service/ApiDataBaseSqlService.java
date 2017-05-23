package com.yonghui.portal.service;

import com.yonghui.portal.model.report.PortalDataSource;

import java.util.List;
import java.util.Map;

/**
 * 数据库基础操作
 *
 * 张海 2017.5.12
 */
public interface ApiDataBaseSqlService {

    /**
     * 通过存储过程查询数据
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    public List<Map<String, Object>> queryCallPro(String sql, PortalDataSource portalDataSource);


    /**
     * 通过sql查询
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    public List<Map<String, Object>> queryExecuteSql(String sql, PortalDataSource portalDataSource);

}
