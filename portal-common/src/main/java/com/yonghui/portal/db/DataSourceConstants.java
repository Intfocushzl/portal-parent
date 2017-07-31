package com.yonghui.portal.db;

/**
 * 数据源常量
 * 命名必须与DataSourceEnum相同
 * 命名规则：数据库类型+业务系统名称+数据库名称
 *
 * Created by 张海 on 2017/04/27.
 */
public class DataSourceConstants {

    // mysql
    public final static String MYSQL_PORTAL_MASTER = "mysqlPortalMaster";
    public static final String MYSQL_PORTAL_SLAVE = "mysqlPortalSlave";
    public static final String MYSQL_PORTAL_APP = "mysqlPortalApp";

    // sqlserver
    public static final String SQLSERVER_PORTAL_MASTER = "sqlserverPortalMaster";
    public final static String SQLSERVER_PORTAL_SLAVE = "sqlserverPortalSlave";

    // oracle
    public static final String ORACLE_PORTAL_MASTER = "oraclePortalMaster";
    public static final String ORACLE_PORTAL_SLAVE = "oraclePortalSlave";

}
