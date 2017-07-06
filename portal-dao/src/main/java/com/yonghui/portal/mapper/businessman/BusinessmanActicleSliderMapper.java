package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanActicleSlider;

import java.util.List;
import java.util.Map;

/**
 * 文章轮播
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-06-28 10:06:31
 */
public interface BusinessmanActicleSliderMapper extends BaseMapper<BusinessmanActicleSlider> {

    List<BusinessmanActicleSlider> queryAllList();

    int deleteAll();

    void saveSlider(Map<String, Object> map);
}
