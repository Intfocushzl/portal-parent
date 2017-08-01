package com.yonghui.portal.util.storeReplay;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  SQL 语句生成
 *  huangzenglei@intfocus.com
 */
public class CreateSql<T> {
    private static final Logger logger = Logger.getLogger(CreateSql.class);

    /**
     * 生成 insert SQL
     * */
    public String createInsert(T entity, String sqlType, String tableName) {
        String sql = sqlType + " into " + tableName;
        String column = ""; // 列
        String c_values = ""; // 列值
        List<Map<String, Object>> list = getFiledsInfo(entity);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("f_value") != null ) {
                column += list.get(i).get("f_name") + ",";
                c_values += "'" + list.get(i).get("f_value") + "',";
            }
        }
        sql += "(" + column.substring(0, column.length() - 1) + ") values ("
                + c_values.substring(0, c_values.length() - 1) + ");";
        logger.info("创建 Insert " + tableName +" SQL语句： " + sql);
        return sql;
    }

    /**
     *通过 UserNum  生成查询用户信息SQL
     */
    public String createSelectUserInfoById(String userId){
       String sql = "select `t1`.`id` AS `user_id`" +
                "        ,`t1`.`user_num` AS `user_num`" +
                "				,`t1`.`user_name` AS `user_name`" +
                "				,`t3`.`id` AS `group_id`" +
                "				,`t3`.`group_name` AS `group_name`" +
                "				,`t5`.`id` AS `role_id`,`t5`" +
                "				.`role_name` AS `role_name` " +
                "     from `sys_users` `t1` " +
                "left join `sys_user_groups` `t2` on `t1`.`id` = `t2`.`user_id` " +
                "left join `sys_groups` `t3` on `t2`.`group_id` = `t3`.`id` " +
                "left join `sys_user_roles` `t4` on `t1`.`id` = `t4`.`user_id` " +
                "left join `sys_roles` `t5` on `t4`.`role_id` = `t5`.`id`" +
                " where `t1`.`user_num` = " + userId;
        logger.info("创建查询用户信息 SQL 语句： " + sql);
        return sql;
    };

    /**
     *  SQL 取得行动方案
     */
    public String createSelectActionPlan(String str){
        String sql = "select" +
                    "     id," +
                    "     user_id," +
                    "     user_name," +
                    "     store_code," +
                    "     store_name," +
                    "     user_role_id," +
                    "     situation_analysis," +
                    "     action_plan," +
                    "     remark," +
                    "     created_at," +
                    "     updated_at" +
                    "  from store_replay.action_plan " ;
        logger.info("创建查询行动方案 SQL 语句： " + sql);
        return sql;
    }

    /**
     * 根据 actionId 查询 评价列表
     * */
    public String createSelectEvaluateInfo(String actionId){
        String sql = null;
        sql = "SELECT id, user_name, reply_user_id, store_id, store_name" +
                "	, user_role_id, action_plan_id, evaluation, remark, created_at" +
                "	, updated_at" +
                " FROM evaluate" +
                " WHERE action_plan_id = " + actionId;
        logger.info("创建查询评价列表 SQL 语句： " + sql);
        return sql;

    }

    /**
     * 根据 userId（usernum） 查询 大区 - 门店 - 商行
     * */
    public String createSelectAreaStireShopInfo(String userId){
        String sql = null;
        sql = " select * from (select employeename as user_name" +
                ",employeeNo as user_num" +
                ",b.AreaName" +
                ",b.areaMans as areaMans " +
                ",a.shopid as dept_ids" +
                ",b.sname" +
                ",groupid  as class_ids" +
                ",groupname " +
                "from dw.d_hana_hr_employee a " +
                "left join dw.d_bravo_shop b " +
                "on a.shopID = b.SAP_ShopID " +
                "where a.lkpdate = DATE_FORMAT(DATE_ADD(now(),INTERVAL -1 day),'%Y%m')" +
                "  and groupid is not null and a.employeeNo = " + userId + ") as a";
        logger.info("创建门店列表 SQL 语句： " + sql);
        return sql;
    }

    /**
     * 根据属性名获取属性值
     * */
    protected Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     *属性名(f_name)，属性值(f_value)的map组成的list
     * */
    protected List getFiledsInfo(Object o) {
        String obj_name = o.getClass().getSimpleName().toString();
        Field[] fields = o.getClass().getDeclaredFields();
//			String[] fieldNames = new String[fields.length];
        List<Map> list = new ArrayList();
        Map<String, Object> infoMap;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap<String, Object>();
            if ("ActionPlan".equals(obj_name)) {
                switch (fields[i].getName()) {
                    case "userId":
                        infoMap.put("f_name", "user_id");
                        break;
                    case "userName":
                        infoMap.put("f_name", "user_name");
                        break;
                    case "storeCode":
                        infoMap.put("f_name", "store_code");
                        break;
                    case "storeName":
                        infoMap.put("f_name", "store_name");
                        break;
                    case "userRoleId":
                        infoMap.put("f_name", "user_role_id");
                        break;
                    case "situationAnalysis":
                        infoMap.put("f_name", "situation_analysis");
                        break;
                    case "actionPlan":
                        infoMap.put("f_name", "action_plan");
                        break;
                    case "remark":
                        infoMap.put("f_name", "remark");
                        break;
                    default:
                        break;
                }
            } else if ("Evaluate".equals(obj_name)) {
                switch (fields[i].getName()) {
                    case "replyUserId":
                        infoMap.put("f_name", "reply_user_id");
                        break;
                    case "userName":
                        infoMap.put("f_name", "user_name");
                        break;
                    case "storeId":
                        infoMap.put("f_name", "store_id");
                        break;
                    case "storeName":
                        infoMap.put("f_name", "store_name");
                        break;
                    case "userRoleId":
                        infoMap.put("f_name", "user_role_id");
                        break;
                    case "actionPlanId":
                        infoMap.put("f_name", "action_plan_id");
                        break;
                    case "evaluation":
                        infoMap.put("f_name", "evaluation");
                        break;
                    case "remark":
                        infoMap.put("f_name", "remark");
                        break;
                    default:
                        break;
                }
            }

            infoMap.put("f_value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }
}
