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
import org.apache.commons.lang.StringUtils;
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
            return R.error(e.getMessage());
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
    public R addEvaluation(HttpServletRequest req, HttpServletResponse response, Evaluate evaluate, String dataSourceCode) {
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
        List listActionPlans = new ArrayList<List<Map<String, Object>>>();
        if (StringUtils.isNotBlank(userId)) {
            try {
                if (null != createdAt) {
                    PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
                    listActionPlans = storeRePlayService.getActionPlanList(userId, createdAt, portalDataSource);
                } else {
                    return R.error("日期为必填");
                }
            } catch (Exception e) {
                logger.error("查询 ActionPlan 以及评价失败：", e);
                return R.error("查询 ActionPlan 以及评价失败");
            }
        }
        return R.success(listActionPlans);
    }

    /**
     * 查询评价列表
     * dataSourceCode DATA_000005 参数必填
     * 通过 actionId 行动方案ID
     */
    @OpenAuth
    @RequestMapping(value = "listEvaluation", method = RequestMethod.GET)
    @ResponseBody
    public R listEvaluation(HttpServletRequest req, HttpServletResponse response, String userId, String actionId, String dataSourceCode) {
        String sql = null;
        List<Map<String, Object>> list = null;
        if (StringUtils.isNotBlank(actionId)) {
            sql = new CreateSql().createSelectEvaluateInfo(actionId);
            PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
            list = storeRePlayService.getBaseList(sql, portalDataSource);
            if (list.size() == 0) {
                return R.error("目前无评价信息");
            }
        } else {
            return R.error("请选择行动方案ID");
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
        List<Map<String, Object>> list = null;
        if (StringUtils.isNotBlank(userId)) {
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
        String roleId = null;
        String skuRoleId = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapValue = new HashMap<>();
        List<Map<String, Object>> list = null;
        List<Map<String, Object>> listSku = new ArrayList<>();
        PortalDataSource portalDataSource = reportUtil.getPortalDataSource(dataSourceCode);
        if (null != userId) {
            CreateSql createSql = new CreateSql();
            //获取用户权限
            sql = createSql.getUserInfoAndAreaStireShopInfoById(userId);
            list = storeRePlayService.getBaseList(sql, portalDataSource);
            if (list.size() > 0) {
                roleId = list.get(0).get("role_id").toString();
            }
            //获取 用户 大区-门店-商行
            if (list.size() > 0) {
                //根据权限返回信息

                if ("44".equals(roleId)) { //小店长
                    //大区-门店-小店
                    mapValue.put("type", 3);
                    mapValue.put("code", list.get(0).get("type2_code"));
                    mapValue.put("name", list.get(0).get("type2_name"));
                    result.add(mapValue);
                } else if ("43".equals(roleId)) { //品类教练 店长 区总
                    sql = createSql.getSkuInfo(userId);
                    listSku = storeRePlayService.getBaseList(sql, portalDataSource);
                    skuRoleId = listSku.get(0).get("role_id").toString();
                    if ("45".equalsIgnoreCase(skuRoleId)) {
                        // 品类教练，需要取出“大区-门店”、“大区-品类”
                        for (Map<String, Object> map : listSku) {
                            if (null != map.get("store_code")) {
                                //大区-门店
                                mapValue.put("type", 2);
                                mapValue.put("code", map.get("store_code").toString());
                                mapValue.put("name", map.get("store_name").toString());
                                result.add(mapValue);
                            }
                            if (null != map.get("group_code")) {
                                //大区-商行
                                mapValue = new HashMap<String, Object>();
                                mapValue.put("type", 4);
                                mapValue.put("code", map.get("group_code").toString());
                                mapValue.put("name", map.get("group_name").toString());
                                result.add(mapValue);
                            }
                        }
                    } else if ("111".equalsIgnoreCase(skuRoleId)) {
                        for (Map<String, Object> mp : listSku) {
                            //只有大区
                            mapValue = new HashMap<String, Object>();
                            mapValue.put("type", 1);
                            mapValue.put("code", mp.get("area_mans").toString());
                            mapValue.put("name", mp.get("area_mans").toString());
                            result.add(mapValue);
                        }
                    }
                }
            }
        }
        return R.success(result);
    }
}
