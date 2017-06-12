package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleLog;

import java.util.List;
import java.util.Map;

/**
 * 生意人文章信息阅读日志
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanActicleLogMapper extends BaseMapper<BusinessmanActicleLog> {

    List<Map<String, Object>> queryIsSee(Map<String, Object> map);
}
