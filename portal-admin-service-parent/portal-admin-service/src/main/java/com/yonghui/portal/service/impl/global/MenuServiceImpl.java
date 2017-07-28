package com.yonghui.portal.service.impl.global;

import com.yonghui.portal.mapper.global.MenuMapper;
import com.yonghui.portal.mapper.sys.SysLogMapper;
import com.yonghui.portal.model.global.Menu;
import com.yonghui.portal.model.global.RoleMenu;
import com.yonghui.portal.model.sys.SysLog;
import com.yonghui.portal.service.global.MenuService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/5/8.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void savelog(SysLog sysLog) {
        sysLogMapper.save(sysLog);
    }
    @Override
    public RoleMenu getUserRoleId(Integer roleId) throws Exception {
        return menuMapper.getUserRoleId(roleId);
    }

    @Override
    public List<Menu> listMenu() throws Exception {
        return menuMapper.listMenu();
    }

    @Override
    public List<Menu> listRoleMenu(List<Integer> list) throws Exception {
        return menuMapper.listRoleMenu(list);
    }

    @Override
    public Menu queryObject(Integer id) {
        return menuMapper.queryObject(id);
    }

    @Override
    public List<Menu> queryList(Map<String, Object> map) {
        return menuMapper.queryList(map);
    }

    @Override
    public List<Menu> queryUserList(Long userId) {
        return menuMapper.queryUserList(userId);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return menuMapper.queryTotal(map);
    }

    @Override
    public void save(Menu menu) {
        menuMapper.save(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public void delete(Integer id) {
        menuMapper.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        menuMapper.deleteBatch(ids);
    }

    public List<Map<String, Object>> queryLargeAreaList() {
        return menuMapper.getLargeAreaList();
    }

    public List<Map<String, Object>> queryAreamsList(String largeArea) {
        Map<String, Object> map=new HashedMap();
        map.put("largeArea",largeArea);
        return menuMapper.getNewAreaMansListByLargeArea(map);
    }

    public List<Map<String, Object>> queryFirmsList() {
        return menuMapper.getFifthRotation();
    }

    @Override
    public List<Map<String, Object>> queryShopsList(Map<String, Object> map) {
        return menuMapper.getBravoShopList(map);
    }

    @Override
    public List<Menu> queryChildrenList(Integer pid) {
        return menuMapper.queryChildrenList(pid);
    }

    @Override
    public List<Menu> queryMenuSort(Integer id) {
        return menuMapper.queryMenuSort(id);
    }

}
