package com.yonghui.portal.service.storeReplay;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;

import java.util.List;
import java.util.Map;

/**
 * 门店日复盘
 * huangzenglei@intfocus.com
 */

public interface StoreRePlayService {

    /**
     * 执行更新操作 ActionPlan
     */
    public void excuteUpdateActionPlan(ActionPlan actionPlan, PortalDataSource portalDataSource);

    /**
     * 执行更新操作 Evaluate
     */
    public void excuteUpdateEvaluate(Evaluate evaluate, PortalDataSource portalDataSource);

    /**
     * 通用查询
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    public List<Map<String, Object>> getBaseList(String sql, PortalDataSource portalDataSource);

}
