package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanNotice;

import java.util.List;
import java.util.Map;

/**
 * 生意人公告信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanNoticeMapper extends BaseMapper<BusinessmanNotice> {

    public List<Map<String,Object>> noticeList(Map<String,Object> map);

    Map<String,Object> queryById(Object id);
}
