package com.yonghui.portal.service.impl.storeReplay;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;
import com.yonghui.portal.service.data.ApiDataBaseSqlService;
import com.yonghui.portal.service.storeReplay.StoreRePlayService;
import com.yonghui.portal.util.storeReplay.CreateSql;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 门店日复盘
 * huangzenglei@intfocus.com
 */

@Service
public class StoreReplayServiceImpl implements StoreRePlayService {

    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private ApiDataBaseSqlService apiDataBaseSqlService;


    /**
     * 添加行动方案  excuteUpdateActionPlan
     */
    @Override
    public void excuteUpdateActionPlan(ActionPlan actionPlan, PortalDataSource portalDataSource) {
        CreateSql createSql = new CreateSql();
        //查询用户信息
        String sql = createSql.createSelectUserInfoById(actionPlan.getUserId());
        List<Map<String, Object>> list = apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
        //将用户信息放入 行动计划 中
        Map<String, Object> map = list.get(0);
        actionPlan.setUserName(map.get("user_name") == null ? null : (String) map.get("user_name"));
        actionPlan.setUserRoleId(Integer.toString(map.get("role_id") == null ? 0 : (Integer) map.get("role_id")));
        actionPlan.setUserId(map.get("user_num") == null ? null : (String) map.get("user_num"));
        //保存 现在分析 和 行动方案
        sql = createSql.createInsert(actionPlan, "insert", "action_plan");
        log.info("执行的sql:" + sql);
        apiDataBaseSqlService.insertTable(sql, portalDataSource);
    }

    /**
     * 添加评论 excuteUpdateEvaluate
     *
     * @param evaluate
     * @param portalDataSource
     */
    @Override
    public void excuteUpdateEvaluate(Evaluate evaluate, PortalDataSource portalDataSource) {
        CreateSql createSql = new CreateSql();

        //查询评价人信息
        String sql = createSql.createSelectUserInfoById(evaluate.getReplyUserId());
        log.info("执行的sql:" + sql);
        List<Map<String, Object>> list = apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
        //将评价人信息放入 评价 中
        Map<String, Object> map = list.get(0);
        evaluate.setUserName(map.get("user_name") == null ? null : (String) map.get("user_name"));
        evaluate.setUserRoleId(Integer.toString(map.get("role_id") == null ? 0 : (Integer) map.get("role_id")));
        //保存 评价信息
        sql = createSql.createInsert(evaluate, "insert", "evaluate");
        log.info("执行的sql:" + sql);
        apiDataBaseSqlService.insertTable(sql, portalDataSource);
    }

    /**
     * 通用查询
     * @param sql
     * @param portalDataSource
     * @return
     */
    @Override
    public List<Map<String, Object>> getBaseList(String sql,PortalDataSource portalDataSource){
        return apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
    }

}
