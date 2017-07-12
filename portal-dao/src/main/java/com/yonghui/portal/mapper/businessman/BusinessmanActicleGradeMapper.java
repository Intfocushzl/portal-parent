package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleGrade;

import java.util.List;
import java.util.Map;

/**
 * 文章评分
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-07-06 09:49:53
 */
public interface BusinessmanActicleGradeMapper extends BaseMapper<BusinessmanActicleGrade> {

    List<BusinessmanActicleGrade> getListByActicleId(Integer id);

    List<Map<String, Object>> grade(Map<String, Object> map);
}
