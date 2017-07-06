package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleRecommend;

import java.util.List;
import java.util.Map;

/**
 * 文章推荐置顶
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:30
 */
public interface BusinessmanActicleRecommendMapper extends BaseMapper<BusinessmanActicleRecommend> {

    List<BusinessmanActicleRecommend> queryAllList();

    int deleteAll();

    void saveRecommend(Map<String, Object> map);
}
