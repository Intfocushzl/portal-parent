package com.yonghui.portal.service.sys;

import com.yonghui.portal.model.sys.SysOperationLog;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-07-24 18:01:46
 */
public interface SysVisitLogService {

    int reportTotal(Map<String,Object> map);

    List<Map<String,Object>> reportcount(Map<String,Object> map);

    SysOperationLog queryObject(Integer id);

    List<SysOperationLog> queryList(Map<String, Object> map);

    List<Map<String, Object>> queryVisit(Map<String, Object> map);

    List<Map<String, Object>> queryVisitByData(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysOperationLog sysOperationLog);

    void update(SysOperationLog sysOperationLog);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
