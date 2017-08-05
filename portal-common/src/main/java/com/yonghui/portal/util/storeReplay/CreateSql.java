package com.yonghui.portal.util.storeReplay;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL 语句生成
 * huangzenglei@intfocus.com
 */
public class CreateSql<T> {
    private static final Logger logger = Logger.getLogger(CreateSql.class);

    /**
     * 生成 insert SQL
     */
    public String createInsert(T entity, String sqlType, String tableName) {
        String sql = sqlType + " into " + tableName;
        String column = ""; // 列
        String c_values = ""; // 列值
        List<Map<String, Object>> list = getFiledsInfo(entity);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("f_value") != null) {
                column += list.get(i).get("f_name") + ",";
                c_values += "'" + list.get(i).get("f_value") + "',";
            }
        }
        sql += "(" + column.substring(0, column.length() - 1) + ") values ("
                + c_values.substring(0, c_values.length() - 1) + ");";
        logger.info("创建 Insert " + tableName + " SQL语句： " + sql);
        return sql;
    }

    /**
     * 通过 UserNum  生成查询用户信息SQL
     */
    public String createSelectUserInfoById(String userId) {
        String sql = "select * from (select `t1`.`id` AS `user_id`" +
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
                " where `t1`.`user_num` = '" + userId + "') AS pp";
        logger.info("创建查询用户信息 SQL 语句： " + sql);
        return sql;
    }

    /**
     * 通过 UserNum  查询用户基本信息 包括 权限，组织
     */
    public String getUserInfoAndAreaStireShopInfoById(String userId) {
        String sql = "select pp.*, a.*,sku.sku_role_id  from " +
                "(select " +
                " /*`t1`.`id` AS `user_id`" +
                "        ,*/`t1`.`user_num` AS `user_num`" +
                //"				,`t1`.`user_name` AS `user_name`" +
               // "				,`t3`.`id` AS `group_id`" +
               // "				,`t3`.`group_name` AS `group_name`" +
                "				,`t5`.`id` AS `role_id`,`t5`" +
                "				.`role_name` AS `role_name` " +
                "     from `sys_users` `t1` " +
              //  "left join `sys_user_groups` `t2` on `t1`.`user_num` = '" + userId + "' and `t1`.`id` = `t2`.`user_id` " +
            //    "left join `sys_groups` `t3` on `t2`.`group_id` = `t3`.`id` " +
                "left join `sys_user_roles` `t4` on `t1`.`id` = `t4`.`user_id` " +
                "left join `sys_roles` `t5` on `t4`.`role_id` = `t5`.`id`" +
                " where `t1`.`user_num` = '" + userId + "') AS pp ";

        sql = sql + "LEFT JOIN ( " +
                " select " +
                " employeeNo " +
                ",b.AreaName " +
                ",b.areaMans as areaMans " +
/*                ",a.shopid as dept_ids " +
                ",b.sname" +
                ",groupid  as class_ids " +
                ",groupname " +*/
                ",concat(b.AreaMans,'-',a.shopid,'-',groupid) as type2_code " +
                ",concat(b.AreaMans,'-',b.sname,'-',groupname) as type2_name " +
                " from dw.d_hana_hr_employee a " +
                " left join dw.d_bravo_shop b " +
                " on a.shopID = b.SAP_ShopID " +
                " where a.employeeNo = '" + userId + "'" +
                " and a.lkpdate = DATE_FORMAT(DATE_ADD(now(),INTERVAL -1 day),'%Y%m') " +
                " ) as a ON pp.user_num = a.employeeNo ";

        sql = sql + " LEFT JOIN (select user_num , role_id as sku_role_id from  store_replay.sku_coach where user_num = '"+ userId +
                "') AS sku ON pp.user_num = sku.user_num ";

        return sql;
    }


    /**
     * 获取品类教练信息
     *
     * @param userId
     * @return
     */
    public String getSkuInfo(String userId) {
        return "select  a.role_id" +
                "       ,concat(b.area_mans , '-' ,c.store_code) as store_code " +
                "       , concat(b.area_mans , '-' , c.store_name) as store_name " +
                "       , concat(b.area_mans , '-' , d.group_code) as group_code" +
                "        , concat(b.area_mans , '-' , d.group_name) as group_name " +
                "        , b.area_mans" +
                "        from ( select  role_id " +
                "                      ,user_num " +
                "                 from store_replay.sku_coach " +
                "                where user_num = '"+userId+"'" +
                "        ) as a" +
                "        LEFT join store_replay.user_area as b on b.user_num = '"+userId+"'" +
                "        LEFT join store_replay.user_store as c on c.user_num = '"+userId+"'" +
                "        LEFT join store_replay.user_xiaodian as d on d.user_num = '"+userId+"'";

    }

    /**
     * 获取 小店，品类教练，个人 行动方案
     *
     * @param tag       类型标记
     * @param userId    用户id
     * @param roleids   权限
     * @param areaName  区域
     * @param createdAt 日期
     * @return
     */
    public String getActionPlan(String tag, String userId, String roleids, String areaName, String createdAt ,boolean sku) {
        String sql = "select" +
                "     '" + tag + "' as tag," +
                "     id," +
                "     user_id," +
                "     user_name," +
                "     store_code," +
//                "     CONCAT(IFNULL(a.AreaMans,''),'-',IFNULL(a.sname,'') , '-' , IFNULL(b.groupname,'')) store_name," +
                "     CONCAT(IF(plan.area_mans is null ,'',CONCAT(plan.area_mans,'')) " +
                "       ,IF(a.sname is null ,'',CONCAT('-',a.sname)) " +
                "       ,IF(b.groupname is null ,'',CONCAT('-',b.groupname) )) store_name," +
                "     user_role_id," +
                "     situation_analysis," +
                "     action_plan," +
//                "     remark," +
                "     updated_at," +
                "     created_at " +
                "  from store_replay.action_plan as plan " +
                "    left join (select DISTINCT sname , sap_shopid ,areaMans from dw.d_bravo_shop ) as a on  plan.store_code = a.SAP_ShopID " +
                "    left join (select DISTINCT groupid ,groupname from  dw.d_category) as b on b.groupid = plan.group_code " +
                " where 1=1 ";
        if (StringUtils.isNotBlank(userId) && !"null".equalsIgnoreCase(userId) && !sku) {
            sql = sql + " AND plan.user_id = '" + userId + "'";
        }
        // 品类教练
        if (sku) {
            sql = sql + " AND plan.group_code in (select xd.group_code " +
                    "    from store_replay.user_xiaodian xd where xd.user_num = '" + userId + "')";
        }
        if (StringUtils.isNotBlank(roleids) && !"null".equalsIgnoreCase(roleids)) {
            sql = sql + " AND plan.user_role_id in (" + roleids + ")";
        }
        if (StringUtils.isNotBlank(areaName) && !"null".equalsIgnoreCase(areaName)) {
            sql = sql + " AND area_mans in ("+ areaName +")";
        }
        if (StringUtils.isNotBlank(createdAt) && !"null".equalsIgnoreCase(createdAt)) {
            sql = sql + " AND date_index = '" + createdAt + "'";
        }
        return sql;
    }

    /**
     * 根据 actionId 查询 评价列表
     */
    public String createSelectEvaluateInfo(String actionId) {
        String sql = null;
        sql = "SELECT id, user_name, reply_user_id, store_id, store_name" +
                "	, user_role_id, action_plan_id, evaluation, remark, created_at" +
                "	, updated_at" +
                " FROM store_replay.evaluate" +
                " WHERE action_plan_id = '" + actionId + "'";
        logger.info("创建查询评价列表 SQL 语句： " + sql);
        return sql;

    }

    /**
     * 查询用户的评价列表
     *
     * @param userId
     * @param areaName
     * @param createdAt
     * @return
     */
    public String getEvaluateList(String tag, String userId, String areaName, String createdAt) {
        String sql = null;
        sql = " SELECT"
//                + " e.id,"
                + "     '" + tag + "' as tag,"
                + "         e.user_name,"
                + "         e.reply_user_id,"
//                + "         e.store_id,"
                + "         e.store_name,"
                + "         e.user_role_id,"
                + "         e.action_plan_id,"
                + "         e.evaluation,"
//                + "         e.remark,"
                + "         e.updated_at,"
                + "         e.created_at "
                + " FROM"
                + " store_replay.action_plan as plan,"
                + " store_replay.evaluate AS e"
                + " WHERE plan.id = e.action_plan_id";
        if (StringUtils.isNotBlank(userId) && !"null".equalsIgnoreCase(userId)) {
            sql = sql + " AND plan.user_id = '" + userId + "'";
        }
        if (StringUtils.isNotBlank(areaName) && !"null".equalsIgnoreCase(areaName)) {
            sql = sql + " AND substring_index(e.store_name,'-',1) in (" + areaName + ")";
        }
        if (StringUtils.isNotBlank(createdAt) && !"null".equalsIgnoreCase(createdAt)) {
            sql = sql + " AND DATE_FORMAT(plan.created_at, '%Y-%m-%d') = '" + createdAt + "'";
        }
        return sql;
    }

    /**
     * 品类教练查询用户的评价列表
     *
     * @param roleids
     * @param areaName
     * @param createdAt
     * @return
     */
    public String getEvaluateListByRole(String tag, String roleids, String areaName, String createdAt) {
        String sql = null;
        sql = " SELECT"
//                + " e.id,"
                + "     '" + tag + "' as tag,"
                + "         e.user_name,"
                + "         e.reply_user_id,"
//                + "         e.store_id,"
                + "         e.store_name,"
                + "         e.user_role_id,"
                + "         e.action_plan_id,"
                + "         e.evaluation,"
//                + "         e.remark,"
                + "         e.created_at,"
                + "         e.updated_at"
                + " FROM"
                + " store_replay.action_plan as plan,"
                + " store_replay.evaluate AS e"
                + " WHERE plan.id = e.action_plan_id";
//        if (StringUtils.isNotBlank(userId)) {
//            sql = sql + " AND plan.user_id = " + userId;
//        }
        if (StringUtils.isNotBlank(roleids) && !"null".equalsIgnoreCase(roleids)) {
            sql = sql + " AND e.user_role_id in (" + roleids + ")";
        }
        if (StringUtils.isNotBlank(areaName) && !"null".equalsIgnoreCase(areaName)) {
            sql = sql + " AND substring_index(e.store_name,'-',1) in (" + areaName + ")";
        }
        if (StringUtils.isNotBlank(createdAt) && !"null".equalsIgnoreCase(createdAt)) {
            sql = sql + " AND DATE_FORMAT(e.created_at, '%Y-%m-%d') = '" + createdAt + "'";
        }
        return sql;
    }

    /**
     * 根据属性名获取属性值
     */
    protected Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 属性名(f_name)，属性值(f_value)的map组成的list
     */
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
                    case "dateIndex":
                        infoMap.put("f_name", "date_index");
                        break;
                    case "areaMans":
                        infoMap.put("f_name", "area_mans");
                        break;
                    case "groupCode":
                        infoMap.put("f_name", "group_code");
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
