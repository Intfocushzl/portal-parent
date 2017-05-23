package com.yonghui.portal.mapper.sys;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.model.sys.SysOperationLog;

/**
 * 用户操作日志
 * Created by liuwei on 2017/05/22.
 */
public interface SysOperationLogMapper extends BaseMapper<SysLog> {

    //保存用户操作日志
    Integer saveLog(SysOperationLog log);
}
