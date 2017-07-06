package com.yonghui.portal.service.impl.app;

import com.yonghui.portal.mapper.app.AppRolesMapper;
import com.yonghui.portal.mapper.app.KpiBasesMapper;
import com.yonghui.portal.mapper.shrio.SysRoleMenuMapper;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.model.app.AppRoles;
import com.yonghui.portal.service.app.AppRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("appRolesService")
public class AppRolesServiceImpl implements AppRolesService {
	@Autowired
	private AppRolesMapper appRolesMapper;
    @Autowired
    private KpiBasesMapper kpiBasesMapper;
	
	@Override
	public AppRoles queryObject(Integer id){
		return appRolesMapper.queryObject(id);
	}
	
	@Override
	public List<AppRoles> queryList(Map<String, Object> map){
		return appRolesMapper.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appRolesMapper.queryTotal(map);
	}
	
	@Override
	public void save(AppRoles appRoles){
		appRolesMapper.save(appRoles);
//        //检查权限是否越权
//        checkPrems(appRoles);

        //保存角色与菜单关系
        saveOrUpdate(appRoles.getId(), appRoles.getMenuList());
	}
	
	@Override
	public void update(AppRoles appRoles){
		appRolesMapper.update(appRoles);

        //        //检查权限是否越权
//        checkPrems(appRoles);

        //保存角色与菜单关系
        saveOrUpdate(appRoles.getId(), appRoles.getMenuList());
	}
	
	@Override
	public void delete(Integer id){
		appRolesMapper.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		appRolesMapper.deleteBatch(ids);
	}



    @Transactional
    public void saveOrUpdate(Integer roleId, List<Map<String,Object>> menuList) {
        //先删除角色与菜单关系
        kpiBasesMapper.deleteMenu(roleId);
        if(menuList.size() == 0){
            return ;
        }
        List<Map<String,Object>> list=new ArrayList<>();
        for (Map<String,Object> m:menuList) {
            if (m.get("menuId")!=null&&Integer.parseInt(m.get("menuId").toString())>0){
                list.add(m);
            }
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", roleId);
        map.put("menuList", list);
        kpiBasesMapper.saveMenu(map);
    }

    public List<Map<String,Object>> queryMenuList(Integer roleId) {
        return kpiBasesMapper.queryMenuList(roleId);
    }

}
