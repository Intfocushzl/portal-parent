package com.yonghui.portal.controller.storeReplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.CreateSql;
import com.yonghui.portal.model.storeReplay.Evaluate;
import com.yonghui.portal.util.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.service.storeReplay.StoreRePlayService;
import com.yonghui.portal.util.R;
import org.springframework.web.bind.WebDataBinder;

/**
 * 门店日复盘统一接口
 *  huangzenglei@intfocus.com
 */
@RestController
@RequestMapping("/storeReplay")
public class ActionPlanController {
	private static final Logger logger = Logger.getLogger(ActionPlanController.class);

	@Reference
	private StoreRePlayService storeRePlayService;

	/**
	 * JDBC 数据源
	 * */
	public static PortalDataSource getPortalDataSource() {
		PortalDataSource portalDataSource = new PortalDataSource();
		portalDataSource.setJdbcDriver("com.mysql.jdbc.Driver");
		portalDataSource.setUrl("jdbc:mysql://123.59.75.85:3306/store_replay?autoReconnect=true&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false");
		portalDataSource.setUser("biuser");
		portalDataSource.setPassword("1234509876");
		portalDataSource.setCode("DATA_000002");
		portalDataSource.setMinConnectionsPerPartition(1);
		portalDataSource.setMaxConnectionsPerPartition(50);
		portalDataSource.setConnectionTag(2);
		return portalDataSource;
	}

	/**
	 * 提交 现在分析与行动方案
	 */
	@IgnoreAuth
	@RequestMapping(value = "addActionPlan", method = RequestMethod.GET)
	@ResponseBody
	public R addActionPlan(HttpServletRequest req, HttpServletResponse response, ActionPlan actionPlan) {
		String sql = null;
		List<Map<String, Object>> list = null;
		if (null != actionPlan) {
			//查询用户信息
			sql = new CreateSql().createSelectUserInfoById(actionPlan.getUserId());
			list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
			//将用户信息放入 行动计划 中
			Map<String, Object> map = list.get(0);
			actionPlan.setUserName(map.get("user_name") == null ? null : (String) map.get("user_name"));
			actionPlan.setUserRoleId(Integer.toString(map.get("role_id") == null ? 0 : (Integer) map.get("role_id")));
			actionPlan.setUserId(map.get("user_num") == null ? null : (String) map.get("user_num"));

			try {
				//保存 现在分析 和 行动方案
				sql = new CreateSql().createInsert(actionPlan, "insert", "action_plan");
				storeRePlayService.excuteUpdate(sql, getPortalDataSource());
			} catch (Exception e){
				return R.error("提交失败！");
			}
		}
		return R.success("提交成功！");
	}

	/**
	 * 提交评价
	 */
	@IgnoreAuth
	@RequestMapping(value = "addEvaluation", method = RequestMethod.GET)
	@ResponseBody
	public R addActionPlan(HttpServletRequest req, HttpServletResponse response, Evaluate evaluate) {
		String sql = null;
		if (null != evaluate) {
			//查询评价人信息
			sql = new CreateSql().createSelectUserInfoById(evaluate.getReplyUserId());
			List<Map<String, Object>> list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
			//将评价人信息放入 评价 中
			Map<String, Object> map = list.get(0);
			evaluate.setUserName(map.get("user_name") == null ? null : (String) map.get("user_name"));
			evaluate.setUserRoleId(Integer.toString(map.get("role_id") == null ? 0 : (Integer) map.get("role_id")));

			try {
				//保存 评价信息
				sql = new CreateSql().createInsert(evaluate, "insert", "evaluate");
				storeRePlayService.excuteUpdate(sql, getPortalDataSource());
			} catch (Exception e){
				return R.error("评论提交失败！");
			}
		}
		return R.success("评论提交成功！");
	}

	/**
	 * 查询 ActionPlan 以及 评价
	 */
	@IgnoreAuth
	@RequestMapping(value = "listActionPlan", method = RequestMethod.GET)
	@ResponseBody
	public R listActionPlan(HttpServletRequest req, HttpServletResponse response, String userId, String createdAt){
		String sql = null;
		Integer roleId = null;
		List<ActionPlan> actionPlans = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> listActionPlans = new ArrayList<Object>();
		CreateSql createSql = new CreateSql();
		if (null != userId) {
			//获取当前用户 role_id
			sql = createSql.createSelectUserInfoById(userId);
			List<Map<String, Object>> list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
			for (Map<String, Object> mapColumn : list) {
				roleId = mapColumn.get("role_id") == null ? 0 : (Integer) mapColumn.get("role_id");
			}

			//通过 role_id 获取行动方案

			if (44 == roleId) { //小店长 role_id = 44
				sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId;
				if (null != createdAt) {
					sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/","-") + "'";
				}
				actionPlans = storeRePlayService.queryActionPlan(sql, getPortalDataSource());
				//通过行动方案 ID 获取评价列表
				for (ActionPlan action : actionPlans) {
					action.setReplyer("小店回复");
					String  actionId = Integer.toString(action.getId());
					sql = createSql.createSelectEvaluateInfo(actionId);
					List<Evaluate> evaluates = storeRePlayService.queryEvaluate(sql, getPortalDataSource());
					action.setEvalutes(evaluates);
				}
				listActionPlans.add(actionPlans);
			} else if ( 7 == roleId) {   //战略团队: role_id = 7  只看 区长：role_id = 111
				sql = createSql.createSelectActionPlan(null) + "  where user_role_id = 111";
				actionPlans = storeRePlayService.queryActionPlan(sql, getPortalDataSource());
				for (ActionPlan action : actionPlans) {
					action.setReplyer("区总回复");
				}
				listActionPlans.add(actionPlans);;
			} else if (43 == roleId || 111 == roleId) {
				//区总团队：品类教练 role_id = 43, 店长 role_id = 43,  区长 role_id = 111

				//小店回复
				sql = createSql.createSelectActionPlan(null) + " where user_role_id = 44";
				if (null != createdAt) {
					sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/","-") + "'";
				}
				actionPlans = storeRePlayService.queryActionPlan(sql, getPortalDataSource());
				//通过行动方案 ID 获取评价列表
				for (ActionPlan action : actionPlans) {
					action.setReplyer("小店回复");
					String  actionId = Integer.toString(action.getId());
					sql = createSql.createSelectEvaluateInfo(actionId);
					List<Evaluate> evaluates = storeRePlayService.queryEvaluate(sql, getPortalDataSource());
					action.setEvalutes(evaluates);
				}
				listActionPlans.add(actionPlans);
//				map.put("小店回复",actionPlans);

				//品类教练回复
				sql = createSql.createSelectActionPlan(null) + " where user_role_id in (43, 111)";
				if (null != createdAt) {
					sql += " and DATE_FORMAT(created_at, '%Y-%m-%d') = '" + createdAt.replace("/","-") + "'";
				}
				actionPlans = storeRePlayService.queryActionPlan(sql, getPortalDataSource());
				//通过行动方案 ID 获取评价列表
				for (ActionPlan action : actionPlans) {
					action.setReplyer("品类教练回复");
					String  actionId = Integer.toString(action.getId());
					if (!"111".equals(action.getUserRoleId())) {  //区长的行动方案没有评价
						sql = createSql.createSelectEvaluateInfo(actionId);
						List<Evaluate> evaluates = storeRePlayService.queryEvaluate(sql, getPortalDataSource());
						action.setEvalutes(evaluates);
					}
				}
				listActionPlans.add(actionPlans);;
//				map.put("品类教练回复",actionPlans);

				//个人回复
				sql = createSql.createSelectActionPlan(null) + " where user_id = " + userId;
				actionPlans = storeRePlayService.queryActionPlan(sql, getPortalDataSource());
				//通过行动方案 ID 获取评价列表
				for (ActionPlan action : actionPlans) {
					action.setReplyer("个人回复");
					String actionId = Integer.toString(action.getId());
					if (!"111".equals(action.getUserRoleId())) {  //区长的行动方案没有评价
						sql = createSql.createSelectEvaluateInfo(actionId);
						List<Evaluate> evaluates = storeRePlayService.queryEvaluate(sql, getPortalDataSource());
						action.setEvalutes(evaluates);
					}
				}
				listActionPlans.add(actionPlans);
//				map.put("个人回复",actionPlans);

//				listActionPlans.add(map);
			}
		}
		return R.success(listActionPlans);
	}

	/**
	 * 查询评价列表
	 */
	@IgnoreAuth
	@RequestMapping(value = "listEvaluation", method = RequestMethod.GET)
	@ResponseBody
	public R listEvaluation(HttpServletRequest req, HttpServletResponse response, String userId, String actionId){
		String sql = null;
		List<Evaluate> evaluates = null;

		sql = new CreateSql().createSelectEvaluateInfo(actionId);
		evaluates = storeRePlayService.queryEvaluate(sql,getPortalDataSource());
		if (evaluates.size() == 0) {
			return R.error("目前无评价信息");
		}
		return R.success(evaluates);
	}

	/**
	 * 查询用户信息
	 * */
	@IgnoreAuth
	@RequestMapping(value = "userInfo", method = RequestMethod.GET)
	@ResponseBody
	public R userInfo(HttpServletRequest req, HttpServletResponse response, String userId){
		String sql = null;
		CreateSql createSql = new CreateSql();
		Map<String, Object> userMap = new HashMap<String, Object>();
		if (null != userId) {
			sql = createSql.createSelectUserInfoById(userId);
			try{
				List<Map<String, Object>> list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
				for (Map<String, Object> mapColumn : list) {
					userMap.put("userId",mapColumn.get("user_num"));
					userMap.put("roleName",mapColumn.get("role_name"));
					userMap.put("roleId",mapColumn.get("role_id"));
					userMap.put("groupName",mapColumn.get("group_name"));
					userMap.put("userName",mapColumn.get("user_name"));
				}
			} catch (Exception e){
				return R.error("用户信息查询失败！");
			}
		}
		return R.success(userMap);
	}

	/**
	 * 查询 大区- 门店 - 商行
	 * */
	@IgnoreAuth
	@RequestMapping(value = "listStore", method = RequestMethod.GET)
	@ResponseBody
	public R listStore(HttpServletRequest req, HttpServletResponse response, String userId){
		String sql = null;
		String res = null;
		List<Map<String, Object>> list = null;
		if (null != userId) {
			//获取用户权限
			String roleId = null;
			sql = new CreateSql().createSelectUserInfoById(userId);
			list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
			for (Map<String, Object> map : list) {
				roleId = map.get("role_id") == null ? null : Integer.toString((Integer) map.get("role_id"));
			}

			//获取 用户 大区-门店-商行
			sql = new CreateSql().createSelectAreaStireShopInfo(userId);
			list = storeRePlayService.queryAreaStoreShop(sql, getPortalDataSource());
			for (Map<String, Object> map : list) {
				//根据权限返回信息
				if ("44".equals(roleId)) { //小店长
					res = map.get("areaMans") + "-" + map.get("sname") +  "-" + map.get("groupname");
				} else if ("43".equals(roleId)) { //品类教练 店长 区总
					res = map.get("areaMans") + "-" + map.get("sname");
				} else if ("111".equals(roleId)) { //战略团队
					res = map.get("areaMans") + "";
				}
			}
		}
		return R.success(res);
	}

	/**
	 * 时实数据呼叫接口
	 * */
	@IgnoreAuth
	@RequestMapping(value = "reportApi", method = RequestMethod.GET)
	@ResponseBody
	public R  reportApi(HttpServletRequest req, HttpServletResponse response, String dimensionCode, String areaName,
						String shopCode, String groupCode, String skuCode, String userId){
		HashMap<String,Object> returnMap = null;
		if (null != userId) {
			//获取用户权限
			String roleId = null;
			String sql = null;
			sql = new CreateSql().createSelectUserInfoById(userId);
			try{
				List<Map<String, Object>> list = storeRePlayService.queryUserInfo(sql, getPortalDataSource());
				for (Map<String, Object> map : list) {
					roleId = map.get("role_id") == null ? null : Integer.toString((Integer) map.get("role_id"));
				}
				if (null == areaName || "".equals(areaName) && Integer.parseInt(dimensionCode) < 8) {
					//获取 维度为 1 - 7  表示战略除外的角色登录，这时只能看到自己的大区，前台未知角色大区，故后台给出
					sql = new CreateSql().createSelectAreaStireShopInfo(userId);
					list = storeRePlayService.queryAreaStoreShop(sql, getPortalDataSource());
					for (Map<String, Object> map : list) {
						areaName = map.get("areaMans") + "";
					}
				}
				returnMap = getReturnMap(dimensionCode, areaName, shopCode, groupCode, skuCode);
			} catch (Exception e){
				logger.error("实时数据获取失败！",e);
				return R.error("实时数据获取失败！");
			}
		} else {
			return R.error("用户编号错误！");
		}
		return R.success(returnMap.get("data"));
	}

	/**
	 * 返回请求的数据
	 */
	public static HashMap<String,Object> getReturnMap(String dimensionCode, String areaName, String shopCode, String groupCode, String skuCode){
		String params = null;
		String encodeParams = null ;
		String openApiCode = "OPENAPI_000008";
		String url ="http://yhapi.yonghui.cn/yhportal/openApi/portal/report?";//请求接口地址
		String str = null;
		try {
			switch (dimensionCode) {
				case "1":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8");
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName;
					break;
				case "2":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&shop_id="+ shopCode;
					params = encodeParams;
					break;
				case "3":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8");
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName;
					break;
				case "4":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8") +
							"&group_id=" + groupCode;
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName + "&group_id=" + groupCode;
					break;
				case "5":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8");
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName;
					break;
				case "6":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8")
							+ "&group_id=" + groupCode;
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName + "&group_id=" + groupCode;
					break;
				case "7":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8") +
							"&goods_id=" + skuCode;
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName + "&goods_id=" + skuCode;
					break;
				case "8":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode;
					params = encodeParams;
					break;
				case "9":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8");
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName;
					break;
				case "10":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" +
							URLEncoder.encode(areaName,"UTF-8") + "&shop_id="+ shopCode;
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName + "&shop_id="+ shopCode;
					break;
				case "11":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode;
					params = encodeParams;
					break;
				case "12":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8");
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName;
					break;
				case "13":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + URLEncoder.encode(areaName,"UTF-8")
							+ "&group_id=" + groupCode;
					params = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&area_mans=" + areaName + "&group_id=" + groupCode;
					break;
				case "14":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode;
					params = encodeParams;
					break;
				case "15":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&group_id=" + groupCode;
					params = encodeParams;
					break;
				case "16":
					encodeParams = "openApiCode="+ openApiCode + "&report_label="+ dimensionCode + "&goods_id=" + skuCode;
					params = encodeParams;
					break;
				default:
					break;
			}
			str = HttpUtil.getData(url ,encodeParams,params );
			str = str.replaceAll("AmountTBRate","sales_year_on_year_percentage")
					.replaceAll("AreaMans","dimension")
					.replaceAll("AmountTB","sales_year_on_year")
					.replaceAll("SortID","ranking")
					.replaceAll("Amount","sales_amount")
					.replaceAll("ShopID","shopCode")
					.replaceAll("ColorFlag","remark")
					.replaceAll("GroupName","groupName")
					.replaceAll("GroupID","groupCode")
					.replaceAll("GoodsName","skuName")
					.replaceAll("GoodsID","skuCode")
					.replaceAll("ShopName","shopName")
					.replaceAll("UpdateTime","timestamp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.parseObject(str,HashMap.class);
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
