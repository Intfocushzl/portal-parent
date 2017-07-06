package com.yonghui.portal.mapper.app;

import com.yonghui.portal.mapper.base.BaseMapper;
import com.yonghui.portal.model.app.AppMenu;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangyang
 * @email 80715104@yonghui.cn
 * @date 2017-05-22 17:27:18
 */
public interface KpiBasesMapper extends BaseMapper<AppMenu> {

    void deleteMenu(Integer roleId);

    void saveMenu(Map<String, Object> map);

    List<AppMenu> queryAllMenuList();

    List<Map<String,Object>> queryMenuList(Integer roleId);
}
