package com.yonghui.portal.service.business;


import com.yonghui.portal.model.businessman.BusinessmanFavorites;
import com.yonghui.portal.util.ApiQuery;

import java.util.List;
import java.util.Map;

/**
 * 用户收藏表
 *
 * @author zhanghai
 * @email walk_hai@163.com
 * @date 2017-06-07 16:33:29
 */
public interface ApiFavoritesService {

    BusinessmanFavorites queryObject(Long id);

    List<BusinessmanFavorites> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(BusinessmanFavorites businessmanFavorites);

    void update(BusinessmanFavorites businessmanFavorites);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    List<Map<String, Object>> favoriteList(Map<String, Object> map);

    void editfavorite(Map<String, Object> map);

    BusinessmanFavorites favoriteDetail(Map<String, Object> map);

    void addFavorites(Map<String, Object> map);

    int favoriteTotal(Map<String, Object> map);
}
