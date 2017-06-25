package com.yonghui.portal.mapper.businessman;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.businessman.BusinessmanFavorites;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏表
 * 
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface BusinessmanFavoritesMapper extends BaseMapper<BusinessmanFavorites> {

    List<Map<String, Object>> favoriteList(Map<String, Object> map);

    void editfavorite(Map<String, Object> map);

    BusinessmanFavorites favoriteDetail(Map<String, Object> map);

    List<BusinessmanFavorites> getListByArticleId(Integer id);

    void addFavorites(Map<String, Object> map);
}
