package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanComment;

import java.util.List;
import java.util.Map;

/**
 * 用户评论信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanCommentMapper extends BaseMapper<BusinessmanComment> {

    public List<Map<String,Object>> commentList(Map<String, Object> params);

}
