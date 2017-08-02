package com.yonghui.portal.mapper.sys;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.sys.SysOperationLog;

import java.util.List;
import java.util.Map;

/**
 * 用户操作日志
 * Created by liuwei on 2017/05/22.
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    int reportTotal(Map<String,Object> map);

    List<Map<String,Object>> reportcount(Map<String,Object> map);

    //保存用户操作日志
    Integer saveLog(SysOperationLog log);

    /**
     * 近30天访问日志
     * @param map
     * @return
     */
    List<Map<String, Object>> queryVisit(Map<String, Object> map);

    /**
     * 当日各时段访问日志
     * @param map
     * @return
     */
    List<Map<String, Object>> queryVisitByData(Map<String, Object> map);

}
