package com.yonghui.portal.service.report;


import com.yonghui.portal.model.report.PortalExecuteSql;
import com.yonghui.portal.model.sys.SysLog;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-05-17 16:35:37
 */
public interface PortalExecuteSqlService {

    void savelog(SysLog sysLog);

    PortalExecuteSql queryObject(Integer id);

    PortalExecuteSql queryObjectBySqlcode(String sqlcode);

    List<PortalExecuteSql> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(PortalExecuteSql portalExecuteSql);

    void update(PortalExecuteSql portalExecuteSql);

    void delete(Integer id);

    void deleteBatchBySqlcodes(String[] sqlcodes);

    /**
     * 产生新的编码
     *
     * @return
     */
    String getNewMaxCode();
}
