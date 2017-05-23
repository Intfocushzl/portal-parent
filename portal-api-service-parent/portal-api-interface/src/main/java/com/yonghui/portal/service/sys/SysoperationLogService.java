package com.yonghui.portal.service.sys;

import com.yonghui.portal.model.sys.SysOperationLog;

/**
 * Created by liuwei on 2017/05/22.
 */
public interface SysoperationLogService {

    //保存用户操作日志
    Integer SaveLog(SysOperationLog log);


}
