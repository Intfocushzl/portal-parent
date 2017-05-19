package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * rpc服务提供者 操作日志记录
 * <p>
 * Created by 张海 on 2017/04/29.
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    public SysLog queryObject(Long id) {
        return sysLogMapper.queryObject(id);
    }

    public List<SysLog> queryList(Map<String, Object> map) {
        return sysLogMapper.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return sysLogMapper.queryTotal(map);
    }

    public void save(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }

    public void update(SysLog sysLog) {
        sysLogMapper.update(sysLog);
    }

    public void delete(Long id) {
        sysLogMapper.delete(id);
    }

    public void deleteBatch(Long[] ids) {
        sysLogMapper.deleteBatch(ids);
    }

}
