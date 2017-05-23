package com.yonghui.portal.service.impl.sys;

import com.yonghui.portal.mapper.sys.SysOperationLogMapper;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;

import javax.annotation.Resource;

/**
 * Created by liuwei on 2017/5/22.
 */
public class SysoperationLogServiceImpl implements SysoperationLogService {

    @Resource
    private SysOperationLogMapper sysOperationLogMapper;

    @Override
    public Integer SaveLog(SysOperationLog log) {
        return sysOperationLogMapper.saveLog(log);
    }
}
