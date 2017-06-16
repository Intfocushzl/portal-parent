package com.yonghui.portal.service.sys;

import com.yonghui.portal.model.sys.SysFtpConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统FTP配置信息表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-09 11:39:14
 */
public interface SysFtpConfigService {

    SysFtpConfig queryObject(Long id);

    List<SysFtpConfig> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysFtpConfig sysFtpConfig);

    void update(SysFtpConfig sysFtpConfig);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
