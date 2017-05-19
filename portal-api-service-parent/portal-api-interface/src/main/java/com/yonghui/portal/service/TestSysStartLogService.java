package com.yonghui.portal.service;

import com.yonghui.portal.model.sys.SysStartLog;

import java.util.List;
import java.util.Map;

/**
 * rpc服务接口测试 系统启动日志记录
 * <p>
 * Created by 张海 on 2017/04/29.
 */
public interface TestSysStartLogService {

    SysStartLog queryObject(Long id);

    List<SysStartLog> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysStartLog sysStartLog);

    void update(SysStartLog sysStartLog);

    void delete(Long id);

    void deleteBatch(Long[] ids);

}
