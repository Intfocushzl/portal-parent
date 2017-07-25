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

    /**
     * 访问日志
     * @param map
     * @return
     */
    List<Map<String, Object>> queryVisit(Map<String, Object> map);
}
