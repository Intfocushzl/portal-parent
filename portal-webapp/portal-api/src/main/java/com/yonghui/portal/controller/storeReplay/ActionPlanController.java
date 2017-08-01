package com.yonghui.portal.controller.storeReplay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;
import com.yonghui.portal.service.storeReplay.StoreRePlayService;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import com.yonghui.portal.util.storeReplay.CreateSql;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店日复盘统一接口
 * huangzenglei@intfocus.com
 */
@RestController
@RequestMapping("/storeReplay")
public class ActionPlanController {
    private static final Logger logger = Logger.getLogger(ActionPlanController.class);

    @Reference
    private StoreRePlayService storeRePlayService;
    @Autowired
    private ReportUtil reportUtil;

    /**
     * 提交 现在分析与行动方案
     * 参数：userId,行动方案参数
     * dataSourceCode DATA_000005 参数必填
     * openApiCode 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "addActionPlan", method = RequestMethod.POST)
    @ResponseBody
    public R addActionPlan(HttpServletRequest req, HttpServletResponse response, ActionPlan actionPlan, String dataSourceCode) {
        try {
            if (null != actionPlan) {
                PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
                storeRePlayService.excuteUpdateActionPlan(actionPlan, portalDataSource);
            } else {
                return R.error("提交失败！参数不能为空");
            }
        } catch (Exception e) {
            logger.error("ActionPlan添加失败： ", e);
        }
        return R.success("提交成功！");
    }

    /**
     * 提交评价
     * dataSourceCode DATA_000005 参数必填
     * openApiCode 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "addEvaluation", method = RequestMethod.POST)
    @ResponseBody
    public R addActionPlan(HttpServletRequest req, HttpServletResponse response, Evaluate evaluate, String dataSourceCode) {
        try {
            if (null != evaluate) {
                PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
                storeRePlayService.excuteUpdateEvaluate(evaluate, portalDataSource);
            } else {
                return R.error("提交失败！参数不能为空");
            }
        } catch (Exception e) {
            logger.error("Evaluate修改失败： ", e);
        }
        return R.success("提交成功！");
    }

    /**
     * 查询 ActionPlan 行动方案 以及 评价
     * dataSourceCode DATA_000005 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "listActionPlan", method = RequestMethod.GET)
    @ResponseBody
    public R listActionPlan(HttpServletRequest req, HttpServletResponse response, String userId, String createdAt, String dataSourceCode) {
        Integer roleId = null;
        String areaName = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Map<String, Object>> listAction = null;
        List listActionPlans = new ArrayList<List<Map<String, Object>>>();
        String sql = "";
        logger.info("请求URL： " + req.getRequestURL() + "?" + req.getQueryString());
        if (null != userId) {
            try {
                CreateSql createSql = new CreateSql();
                PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
                //获取当前用户 role_id
                sql = createSql.createSelectUserInfoById(userId);
                list = storeRePlayService.getBaseList(sql, portalDataSource);
                for (Map<String, Object> mapColumn : list) {
                    roleId = mapColumn.get("role_id") == null ? 0 : (Integer) mapColumn.get("role_id");
                }
                logger.info("获取当前用户 role_id SQL：" + sql);
                //获取 用户 大区-门店-商行
                sql = createSql.createSelectAreaStireShopInfo(userId);
                list = storeRePlayService.getBaseList(sql, portalDataSource);
                for (Map<String, Object> areaMap : list) {
                    //根据权限返回信息
                    areaName = areaMap.get("areaMans") + "";
                }

                //通过 role_id 获取行动方案
                if (44 == roleId) { //小店长 role_id = 44
                    sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId + " and locate('" + areaName + "',store_name) > 0 ";
                    if (null != createdAt) {
                        sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
                    }
                    logger.info("小店回复 SQL：" + sql);
                    //行动方案
                    listAction = storeRePlayService.getBaseList(sql, portalDataSource);

                    //通过行动方案 ID 获取评价列表
                    for (Map<String, Object> mapObj : listAction) {
                        mapObj.put("replyer", "小店回复");
                        String actionId = mapObj.get("id").toString();
                        sql = createSql.createSelectEvaluateInfo(actionId);
                        //评论
                        list = storeRePlayService.getBaseList(sql, portalDataSource);
                        mapObj.put("evaluates", list);
                    }

                    listActionPlans.add(listAction);

                } else if (7 == roleId) {   //战略团队: role_id = 7  只看 区长：role_id = 111
                    sql = createSql.createSelectActionPlan(null) + "  where user_role_id = 111 ";
                    if (null != createdAt) {
                        sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
                    }
                    logger.info("区总回复 SQL：" + sql);
                    //行动方案
                    listAction = storeRePlayService.getBaseList(sql, portalDataSource);
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
                    logger.info("小店回复 SQL：" + sql);
                    //行动方案
                    listAction = storeRePlayService.getBaseList(sql, portalDataSource);
                    //通过行动方案 ID 获取评价列表
                    for (Map<String, Object> mapObj : listAction) {
                        mapObj.put("replyer", "小店回复");
                        String actionId = mapObj.get("id").toString();
                        sql = createSql.createSelectEvaluateInfo(actionId);
                        //评论
                        list = storeRePlayService.getBaseList(sql, portalDataSource);
                        mapObj.put("evaluates", list);
                    }

                    listActionPlans.add(listAction);

                    //品类教练回复
                    sql = createSql.createSelectActionPlan(null) + " where user_role_id in (43, 111) and locate('" + areaName + "',store_name) > 0 ";
                    if (null != createdAt) {
                        sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/", "-") + "'";
                    }
                    logger.info("品类教练回复 SQL：" + sql);
                    //行动方案
                    listAction = storeRePlayService.getBaseList(sql, portalDataSource);
                    //通过行动方案 ID 获取评价列表
                    for (Map<String, Object> mapObj : listAction) {
                        mapObj.put("replyer", "品类教练回复");
                        String actionId = mapObj.get("id").toString();
                        if (!"111".equals(mapObj.get("userRoleId"))) {  //区长的行动方案没有评价
                            sql = createSql.createSelectEvaluateInfo(actionId);
                            //评论
                            list = storeRePlayService.getBaseList(sql, portalDataSource);
                            mapObj.put("evaluates", list);
                        }
                    }
                    listActionPlans.add(listAction);

                    //个人回复
                    sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId;
                    logger.info("个人回复 SQL：" + sql);
                    //行动方案
                    listAction = storeRePlayService.getBaseList(sql, portalDataSource);
                    //通过行动方案 ID 获取评价列表
                    for (Map<String, Object> mapObj : listAction) {
                        mapObj.put("replyer", "个人回复");
                        String actionId = mapObj.get("id").toString();
                        if (!"111".equals(mapObj.get("userRoleId"))) {  //区长的行动方案没有评价
                            sql = createSql.createSelectEvaluateInfo(actionId);
                            //评论
                            list = storeRePlayService.getBaseList(sql, portalDataSource);
                            mapObj.put("evaluates", list);
                        }
                    }
                    listActionPlans.add(listAction);
                }
            } catch (Exception e) {
                logger.error("查询 ActionPlan 以及评价失败：", e);
                return R.error();
            }

        }
        return R.success(listActionPlans);
    }

    /**
     * 查询评价列表
     * dataSourceCode DATA_000005 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "listEvaluation", method = RequestMethod.GET)
    @ResponseBody
    public R listEvaluation(HttpServletRequest req, HttpServletResponse response, String userId, String actionId, String dataSourceCode) {
        String sql = null;
        List<Map<String, Object>> list = null;
        logger.info("请求URL： " + req.getRequestURL() + "?" + req.getQueryString());
        sql = new CreateSql().createSelectEvaluateInfo(actionId);
        PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
        list = storeRePlayService.getBaseList(sql, portalDataSource);
        if (list.size() == 0) {
            return R.error("目前无评价信息");
        }
        return R.success(list);
    }

    /**
     * 查询用户信息
     * dataSourceCode DATA_000005 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    @ResponseBody
    public R userInfo(HttpServletRequest req, HttpServletResponse response, String userId, String dataSourceCode) {
        String sql = null;
        Map<String, Object> userMap = new HashMap<String, Object>();
        logger.info("请求URL： " + req.getRequestURL() + "?" + req.getQueryString());
        List<Map<String, Object>> list = null;
        if (null != userId) {
            CreateSql createSql = new CreateSql();
            try {
                PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
                sql = createSql.createSelectUserInfoById(userId);
                list = storeRePlayService.getBaseList(sql, portalDataSource);
                for (Map<String, Object> mapColumn : list) {
                    userMap.put("userId", mapColumn.get("user_num"));
                    userMap.put("roleName", mapColumn.get("role_name"));
                    userMap.put("roleId", mapColumn.get("role_id"));
                    userMap.put("groupName", mapColumn.get("group_name"));
                    userMap.put("userName", mapColumn.get("user_name"));
                }
            } catch (Exception e) {
                logger.error("查询用户信息失败：", e);
                return R.error("用户信息查询失败！");
            }
        } else {
            return R.error("无效的用户编号");
        }
        return R.success(userMap);
    }

    /**
     * 查询 大区- 门店 - 商行
     * dataSourceCode DATA_000005 参数必填
     */
    @OpenAuth
    @RequestMapping(value = "listStore", method = RequestMethod.GET)
    @ResponseBody
    public R listStore(HttpServletRequest req, HttpServletResponse response, String userId, String dataSourceCode) {
        String sql = null;
        String res = null;
        String roleId = null;
        List<Map<String, Object>> list = null;
        List<String> returnList = new ArrayList<String>();
        logger.info("请求URL： " + req.getRequestURL() + "?" + req.getQueryString());

        PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
        if (null != userId) {
            CreateSql createSql = new CreateSql();
            //获取用户权限
            sql = createSql.createSelectUserInfoById(userId);
            list = storeRePlayService.getBaseList(sql, portalDataSource);
            for (Map<String, Object> map : list) {
                roleId = map.get("role_id") == null ? null : Integer.toString((Integer) map.get("role_id"));
            }

            //获取 用户 大区-门店-商行
            sql = createSql.createSelectAreaStireShopInfo(userId);
            list = storeRePlayService.getBaseList(sql, portalDataSource);
            for (Map<String, Object> map : list) {
                //根据权限返回信息
                if ("44".equals(roleId)) { //小店长
                    res = map.get("areaMans") + "-" + map.get("sname") + "-" + map.get("groupname");
                } else if ("43".equals(roleId)) { //品类教练 店长 区总
                    res = map.get("areaMans") + "-" + map.get("sname");
                } else if ("111".equals(roleId)) { //战略团队
                    res = map.get("areaMans") + "";
                }
            }
            if (null == res || "".equals(res)) {
                return R.error("获取用户门店信息失败！ 用户编号为: " + userId);
            }
            returnList.add(res);
        }
        return R.success(returnList);
    }

    @InitBinder("actionPlan")
    public void initBinderActionPlan(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("actionPlan.");
    }

    @InitBinder("evaluate")
    public void initBinderEvaluate(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("evaluate.");
    }
}
