package com.yonghui.portal.service.storeReplay;

import java.util.List;
import java.util.Map;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;
/**
 * 门店日复盘
 * huangzenglei@intfocus.com
 */

public interface StoreRePlayService {

    /**
     * 执行更新操作
     */
    public void excuteUpdate(String sql, PortalDataSource portalDataSource);


    /**
     * 通过sql查询
     */
    public List<Map<String, Object>> queryExecuteSql(String sql, PortalDataSource portalDataSource);

    /**
     * 查询用户详细信息
     * */
    public List<Map<String, Object>> queryUserInfo(String sql, PortalDataSource portalDataSource);

    /**
     * 通过 role_id 查询 行动方案
     * */
    public List<ActionPlan> queryActionPlan(String sql, PortalDataSource portalDataSource);

    /**
     * 通过 actionID 查询 评价列表
     * */
    public List<Evaluate> queryEvaluate(String sql, PortalDataSource portalDataSource);

    /**
     * 返回 门店列表 信息List<Map<String,Object>>
     * */
    public List<Map<String, Object>> queryAreaStoreShop(String sql, PortalDataSource portalDataSource);
}
