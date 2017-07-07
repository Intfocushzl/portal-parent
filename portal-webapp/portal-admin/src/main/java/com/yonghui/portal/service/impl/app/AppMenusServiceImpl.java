package com.yonghui.portal.service.impl.app;

import com.alibaba.dubbo.common.json.JSONArray;
import com.yonghui.portal.mapper.app.AnalysisBasesMapper;
import com.yonghui.portal.mapper.app.AppBasesMapper;
import com.yonghui.portal.mapper.app.KpiBasesMapper;
import com.yonghui.portal.model.app.AppMenu;
import com.yonghui.portal.service.app.AppMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("appMenusService")
public class AppMenusServiceImpl implements AppMenusService {

    @Autowired
    private KpiBasesMapper kpiBasesMapper;
    @Autowired
    private AnalysisBasesMapper analysisBasesMapper;
    @Autowired
    private AppBasesMapper appBasesMapper;

    @Override
    public AppMenu queryObject(Map<String, Object> map) {
        Integer menuId = Integer.parseInt(map.get("menuId").toString());
        Integer type = Integer.parseInt(map.get("type").toString());
        if (type == 1) {
            return kpiBasesMapper.queryObject(menuId);
        } else if (type == 2) {
            return analysisBasesMapper.queryObject(menuId);
        }
        return appBasesMapper.queryObject(menuId);
    }

    @Override
    public List<AppMenu> queryList(Map<String, Object> map) {
        Integer type = Integer.parseInt(map.get("type") == null ? "1" : map.get("type").toString());
        if (type == 1) {
            return kpiBasesMapper.queryList(map);
        } else if (type == 2) {
            return analysisBasesMapper.queryList(map);
        }
        return appBasesMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        Integer type = Integer.parseInt(map.get("type") == null ? "1" : map.get("type").toString());
        if (type == 1) {
            return kpiBasesMapper.queryTotal(map);
        } else if (type == 2) {
            return analysisBasesMapper.queryTotal(map);
        }
        return appBasesMapper.queryTotal(map);
    }

    @Override
    public void save(AppMenu appMenu) {
        if (appMenu.getType() == 1) {
            kpiBasesMapper.save(appMenu);
        }
        if (appMenu.getType() == 2) {
            analysisBasesMapper.save(appMenu);
        }
        appBasesMapper.save(appMenu);
    }

    @Override
    public void update(AppMenu appMenu) {
        if (appMenu.getType() == 1) {
            kpiBasesMapper.update(appMenu);
        }
        if (appMenu.getType() == 2) {
            analysisBasesMapper.update(appMenu);
        }
        appBasesMapper.update(appMenu);
    }

    @Override
    public void delete(Map<String, Object> map) {
//        Integer menuId = Integer.parseInt(map.get("menuId").toString());
//        Integer type = Integer.parseInt(map.get("type").toString());
//        if (type == 1) {
//            kpiBasesMapper.delete(menuId);
//        }
//        if (type == 2) {
//            analysisBasesMapper.delete(menuId);
//        }
//        appBasesMapper.delete(menuId);
    }

    @Override
    public void deleteBatch(Map<String, Object> map) {
        JSONArray array = ((JSONArray) map.get("menuIds"));
        Integer[] menuIds = new Integer[array.length()];
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                Integer menuId = (Integer) array.get(i);
                menuIds[i]=menuId;
            }
        } else {
            return;
        }
        Integer type = Integer.parseInt(map.get("type").toString());
        if (type == 1) {
            kpiBasesMapper.deleteBatch(menuIds);
        }
        if (type == 2) {
            analysisBasesMapper.deleteBatch(menuIds);
        }
        appBasesMapper.deleteBatch(menuIds);
    }

    @Override
    public List<AppMenu> queryAllMenuList() {
        return kpiBasesMapper.queryAllMenuList();
    }

}
