package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanProblem;

import java.util.List;
import java.util.Map;

/**
 * 用户问题反馈信息表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:28
 */
public interface BusinessmanProblemMapper extends BaseMapper<BusinessmanProblem> {

    public List<Map<String, Object>> problemDetail(Map<String, Object> map);

    public List<Map<String, Object>> problemList(Map<String, Object> map);

    int problemTotal(Map<String, Object> map);
}
