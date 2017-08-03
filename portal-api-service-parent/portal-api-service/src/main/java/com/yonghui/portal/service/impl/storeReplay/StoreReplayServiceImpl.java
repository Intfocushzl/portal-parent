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

import java.util.ArrayList;
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
        String sql = null;
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
        String sql = null;
        //保存 评价信息
        sql = createSql.createInsert(evaluate, "insert", "evaluate");
        log.info("执行的sql:" + sql);
        apiDataBaseSqlService.insertTable(sql, portalDataSource);
    }

    /**
     * 通用查询
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    @Override
    public List<Map<String, Object>> getBaseList(String sql, PortalDataSource portalDataSource) {
        return apiDataBaseSqlService.queryExecuteSql(sql, portalDataSource);
    }

    @Override
    public List<Map<String, Object>> getActionPlanList(String userId, String createdAt, PortalDataSource portalDataSource) {
        Integer roleId = null;
        String areaName = null;
        List<Map<String, Object>> list = null;
        List<Map<String, Object>> listAction = null;
        String sql = "";
        List listActionPlans = new ArrayList<List<Map<String, Object>>>();
        CreateSql createSql = new CreateSql();

        //获取当前用户 role_id
        sql = createSql.createSelectUserInfoById(userId);
        list = getBaseList(sql, portalDataSource);
        if (list.size() > 0) {
            roleId = list.get(0).get("role_id") == null ? 0 : (Integer) list.get(0).get("role_id");
        }

        //获取 用户 大区-门店-商行
        sql = createSql.createSelectAreaStireShopInfo(userId);
        list = getBaseList(sql, portalDataSource);
        if (list.size() > 0) {
            //根据权限返回信息
            areaName = list.get(0).get("areaMans") + "";
        }

        //通过 role_id 获取行动方
        if (44 == roleId) { //小店长 role_id = 44
            sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId + " and locate('" + areaName + "',store_name) > 0 ";
            if (null != createdAt) {
                sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
            }
            //小店回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            //通过行动方案 ID 获取评价列表
            for (Map<String, Object> mapObj : listAction) {
                mapObj.put("replyer", "小店回复");
                String actionId = mapObj.get("id").toString();
                sql = createSql.createSelectEvaluateInfo(actionId);
                //评论
                list = getBaseList(sql, portalDataSource);
                mapObj.put("evaluates", list);
            }
            listActionPlans.add(listAction);
        } else if (7 == roleId) {   //战略团队: role_id = 7  只看 区长：role_id = 111
            sql = createSql.createSelectActionPlan(null) + "  where user_role_id = 111 ";
            if (null != createdAt) {
                sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
            }
            //"区总回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            for (Map<String, Object> mapObj : listAction) {
                mapObj.put("replyer", "区总回复");
            }
            listActionPlans.add(listAction);
        } else if (43 == roleId || 111 == roleId) { //区总团队：品类教练 role_id = 43, 店长 role_id = 43,  区长 role_id = 111
            //小店回复
            sql = createSql.createSelectActionPlan(null) + " where user_role_id = 44 and locate('" + areaName + "',store_name) > 0 ";
            if (null != createdAt) {
                sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
            }
            //小店回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            //通过行动方案 ID 获取评价列表
            for (Map<String, Object> mapObj : listAction) {
                mapObj.put("replyer", "小店回复");
                String actionId = mapObj.get("id").toString();
                sql = createSql.createSelectEvaluateInfo(actionId);
                //评论
                list = getBaseList(sql, portalDataSource);
                mapObj.put("evaluates", list);
            }
            listActionPlans.add(listAction);

            //品类教练回复
            sql = createSql.createSelectActionPlan(null) + " where user_role_id in (43, 111) and locate('" + areaName + "',store_name) > 0 ";
            if (null != createdAt) {
                sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
            }
            //品类教练回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            //通过行动方案 ID 获取评价列表
            for (Map<String, Object> mapObj : listAction) {
                mapObj.put("replyer", "品类教练回复");
                String actionId = mapObj.get("id").toString();
                if (!"111".equals(mapObj.get("userRoleId"))) {  //区长的行动方案没有评价
                    sql = createSql.createSelectEvaluateInfo(actionId);
                    //评论
                    list = getBaseList(sql, portalDataSource);
                    mapObj.put("evaluates", list);
                }
            }
            listActionPlans.add(listAction);

            //个人回复
            sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId;
            if (null != createdAt) {
                sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
            }
            //个人回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            //通过行动方案 ID 获取评价列表
            for (Map<String, Object> mapObj : listAction) {
                mapObj.put("replyer", "个人回复");
                String actionId = mapObj.get("id").toString();
                if (!"111".equals(mapObj.get("userRoleId"))) {  //区长的行动方案没有评价
                    sql = createSql.createSelectEvaluateInfo(actionId);
                    //评论
                    list = getBaseList(sql, portalDataSource);
                    mapObj.put("evaluates", list);
                }
            }
            listActionPlans.add(listAction);
        }
        return listActionPlans;
    }

}
