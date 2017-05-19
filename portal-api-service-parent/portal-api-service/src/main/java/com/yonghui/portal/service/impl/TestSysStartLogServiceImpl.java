package com.yonghui.portal.service.impl;

import com.yonghui.portal.mapper.sys.SysStartLogMapper;
import com.yonghui.portal.model.sys.SysStartLog;
import com.yonghui.portal.service.TestSysStartLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * rpc服务提供者 系统启动日志记录
 * <p>
 * Created by 张海 on 2017/04/29.
 * @Service是dubbo本身的注解，跟配置文件中先声明一个bean然后再声明接口暴露的意思一样
 */
//@Service
public class TestSysStartLogServiceImpl implements TestSysStartLogService {
    @Autowired
    private SysStartLogMapper sysStartLogMapper;

    @Override
    public SysStartLog queryObject(Long id) {
        return sysStartLogMapper.queryObject(id);
    }

    @Override
    public List<SysStartLog> queryList(Map<String, Object> map) {
        return sysStartLogMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysStartLogMapper.queryTotal(map);
    }

    @Override
    public void save(SysStartLog sysStartLog) {
        sysStartLogMapper.save(sysStartLog);
    }

    @Override
    public void update(SysStartLog sysStartLog) {
        sysStartLogMapper.update(sysStartLog);
    }

    @Override
    public void delete(Long id) {
        sysStartLogMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysStartLogMapper.deleteBatch(ids);
    }

}
