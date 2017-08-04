package com.yonghui.portal.service.impl.storeReplay;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;
import com.yonghui.portal.service.data.ApiDataBaseSqlService;
import com.yonghui.portal.service.storeReplay.StoreRePlayService;
import com.yonghui.portal.util.RRException;
import com.yonghui.portal.util.storeReplay.CreateSql;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        List<Map<String, Object>> listSku = new ArrayList<>();
        String sql = null;
        String skuRoleId = null;
        //保存 现在分析 和 行动方案
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateIndex = format.format(new Date());
        actionPlan.setDateIndex(dateIndex);

        if ("43".equals(actionPlan.getUserRoleId())) {
            sql = createSql.getSkuInfo(actionPlan.getUserId());
            listSku = getBaseList(sql, portalDataSource);
            if (listSku.size() > 0) {
                skuRoleId = listSku.get(0).get("role_id").toString();
                if (null != skuRoleId) {
                    actionPlan.setUserRoleId(skuRoleId);
                }
            } else {
                throw new RRException("当前用户不区总团队表中");
            }
        }

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
        String skuRoleId = null;
        String areaName = null;
        List<Map<String, Object>> list = null;
        List<Map<String, Object>> listAction = null;
        String sql = "";
        List listActionPlans = new ArrayList<List<Map<String, Object>>>();
        CreateSql createSql = new CreateSql();

        //获取当前用户 权限-大区-门店-商行
        sql = createSql.getUserInfoAndAreaStireShopInfoById(userId);
        list = getBaseList(sql, portalDataSource);
        if (list.size() > 0) {
            roleId = Integer.parseInt(list.get(0).get("role_id").toString()) ;
             if (43 == roleId) {
                 skuRoleId = list.get(0).get("sku_role_id").toString();
             }
            areaName = list.get(0).get("areaMans") == null ? null :list.get(0).get("areaMans") + "";
        }

        //通过 role_id 获取行动方案
        if (44 == roleId) { //小店长 role_id = 44
            sql = createSql.getActionPlan("xd", userId, null, areaName, createdAt.replace("/", ""),false);
            //小店回复 行动方案
            listAction = getBaseList(sql, portalDataSource);
            //小店回复 评论
            sql = createSql.getEvaluateList(null, areaName, createdAt.replace("/", "-"));
            list = getBaseList(sql, portalDataSource);
            List<Map<String, Object>> evaluate = null;
            for (Map<String, Object> evaluates : list) {
                //通过行动方案 ID 获取评价列表
                for (Map<String, Object> mapObj : listAction) {
                    if (evaluates.get("action_plan_id").toString().equals(mapObj.get("id").toString())) {
//                        mapObj.put("replyer", "小店回复");
                        evaluate = new ArrayList<Map<String, Object>>();
                        evaluate.add(evaluates);
                        mapObj.put("evaluates", evaluate);
                    }
                }
            }
            if (listAction.size() != 0) {
                listActionPlans.add(listAction);
            }
        } else if (7 == roleId) {   //战略团队: role_id = 7  只看 区长：role_id = 111
            sql = createSql.getActionPlan("qz", null, "111", null, createdAt.replace("/", ""),false);
            //"区总回复 行动方案
            List<Map<String, Object>> evaluate = new ArrayList<Map<String, Object>>();
            listAction = getBaseList(sql, portalDataSource);
            for (Map<String, Object> mapObj : listAction) {
                evaluate = new ArrayList<Map<String, Object>>();
                mapObj.put("replyer", "区总回复");
                mapObj.put("evaluates", evaluate);
            }
            if (listAction.size() != 0) {
                listActionPlans.add(listAction);
            }
        } else if (43 == roleId || 111 == roleId) { //区总团队：品类教练 role_id = 43, 店长 role_id = 43,  区长 role_id = 111
            //行动方案  xd小店    pj品类教练   gr个人  dq大区
            boolean sku = false;
            if("45".equals(skuRoleId)){
                sku = true ;
            }

            sql = createSql.getActionPlan("xd", userId, "44", areaName, createdAt.replace("/", "") , sku);
            sql = sql + " UNION ALL ";
            sql = sql + createSql.getActionPlan("pj", null, "43,111", areaName, createdAt.replace("/", ""),false);
            sql = sql + " UNION ALL ";
            sql = sql + createSql.getActionPlan("gr", userId, null, null, createdAt.replace("/",  ""),false);

            //sql = sql + " UNION ALL ";
            //sql = sql + createSql.getActionPlan("dq", null, "45,111", areaName, createdAt.replace("/", ""));
            // 品类教练
            //sql = sql + " UNION ALL ";
            //sql = sql + createSql.getActionPlan("pl", null, "45,111", areaName, createdAt.replace("/", ""));


            listAction = getBaseList(sql, portalDataSource);

            List<Map<String, Object>> listActionXd = new ArrayList<>();
            List<Map<String, Object>> listActionPj = new ArrayList<>();
            List<Map<String, Object>> listActionGr = new ArrayList<>();

            for (Map<String, Object> mapObj : listAction) {
                if (mapObj.containsKey("tag")) {
                    if ("xd".equals(mapObj.get("tag").toString())) {
                        listActionXd.add(mapObj);
                    } else if ("pj".equals(mapObj.get("tag").toString())) {
                        listActionPj.add(mapObj);
                    } else if ("gr".equals(mapObj.get("tag").toString())) {
                        listActionGr.add(mapObj);
                    }
                }
            }

            //1.小店回复
            sql = createSql.getEvaluateListByRole("45", areaName, createdAt.replace("/", "-"));
            list = getBaseList(sql, portalDataSource);
            for (Map<String, Object> mapObj : listActionXd) {
                List<Map<String, Object>> listEvaluatesXd = new ArrayList<>();
                for (Map<String, Object> evaluates : list) {
                    if (evaluates.get("action_plan_id").toString().equals(mapObj.get("id").toString())) {
                        listEvaluatesXd.add(evaluates);
                    }
                }
//                mapObj.put("replyer", "小店回复");
                mapObj.put("evaluates", listEvaluatesXd);
            }
            if (listActionXd.size() != 0) {
                listActionPlans.add(listActionXd);
            }

            //2.品类教练回复 评论
            sql = createSql.getEvaluateListByRole("111", areaName, createdAt.replace("/", "-"));
            list = getBaseList(sql, portalDataSource);
            for (Map<String, Object> mapObj : listActionPj) {
                List<Map<String, Object>> listEvaluatesPj = new ArrayList<>();
                for (Map<String, Object> evaluates : list) {
                    if (evaluates.get("action_plan_id").toString().equals(mapObj.get("id").toString())) {
                        listEvaluatesPj.add(evaluates);
                    }
                }
                mapObj.put("evaluates", listEvaluatesPj);
            }
            if (listActionPj.size() != 0) {
                listActionPlans.add(listActionPj);
            }

            //3.个人所有行动方案的评论
            sql = createSql.getEvaluateList(null, areaName, createdAt.replace("/", "-"));
            list = getBaseList(sql, portalDataSource);
            for (Map<String, Object> mapObj : listActionGr) {
                List<Map<String, Object>> listEvaluatesGr = new ArrayList<>();
                for (Map<String, Object> evaluates : list) {
                    if (evaluates.get("action_plan_id").toString().equals(mapObj.get("id").toString())) {
                        listEvaluatesGr.add(evaluates);
                    }
                }
//                mapObj.put("replyer", "个人回复");
                mapObj.put("evaluates", listEvaluatesGr);
            }
            if (listActionGr.size() != 0) {
                listActionPlans.add(listActionGr);
            }
        }
        return listActionPlans;
    }

}