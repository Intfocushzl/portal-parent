package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanNoticeLog;

import java.util.Map;

/**
 * 生意人公告信息阅读日志
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface BusinessmanNoticeLogMapper extends BaseMapper<BusinessmanNoticeLog> {

    BusinessmanNoticeLog queryIsSee(Map<String,Object> map);

}
