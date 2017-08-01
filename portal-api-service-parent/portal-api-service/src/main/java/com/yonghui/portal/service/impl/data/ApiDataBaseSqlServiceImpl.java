package com.yonghui.portal.service.impl.data;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.ConnectionHandle;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.service.data.ApiDataBaseSqlService;
import com.yonghui.portal.util.ConstantsUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mysql jdbc 操作
 * 使用BoneCP数据库连接池。比C3P0/DBCP连接池快25倍
 * 参数说明
 * acquireIncrement: 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
 * driveClass:数据库驱动
 * jdbcUrl:响应驱动的jdbcUrl
 * username:数据库的用户名
 * password:数据库的密码
 * idleConnectionTestPeriod:检查数据库连接池中控线连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0
 * idleMaxAge:连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
 * maxConnectionsPerPartition:每个分区最大的连接数
 * minConnectionsPerPartition:每个分区最小的连接数
 * partitionCount:分区数，默认值2，最小1，推荐3-4，视应用而定
 * acquireIncrement:每次去拿数据库连接的时候一次性要拿几个，默认值：2
 * statementsCacheSize:缓存prepared statements的大小，默认值：0
 * releaseHelperThreads:每个分区释放链接助理进程的数量，默认值：3，除非你的一个数据库连接的时间内做了很多工作，不然过多的助理进程会影响你的性能
 * <p>
 * 张海 2017.5.12
 */
@Service
public class ApiDataBaseSqlServiceImpl implements ApiDataBaseSqlService {

    private static final Logger logger = Logger.getLogger(ApiDataBaseSqlServiceImpl.class);

    private ApiDataBaseSqlServiceImpl() {
    }

    private static ApiDataBaseSqlServiceImpl apiDataBaseSqlService = null;

    /**
     * Mysql连接池 平台
     */
    private static final Object objectMysql = new Object();
    private static final Object objectConnMysql = new Object();
    private static BoneCPConfig configMysql;
    private static BoneCP connectionPoolMysql;

    /**
     * Mysql连接池 APP
     */
    private static final Object objectMysqlApp = new Object();
    private static final Object objectConnMysqlApp = new Object();
    private static BoneCPConfig configMysqlApp;
    private static BoneCP connectionPoolMysqlApp;

    /**
     * Hana连接池
     */
    private static final Object objectHana = new Object();
    private static final Object objectConnHana = new Object();
    private static BoneCPConfig configHana;
    private static BoneCP connectionPoolHana;

    /**
     * MySql
     * <p>
     * 静态工厂方法 创建数据库连接池
     *
     * @param portalDataSource
     * @return
     */
    public static BoneCP getMySqlInstance(PortalDataSource portalDataSource) {
        synchronized (objectMysql) {
            if (connectionPoolMysql == null) {
                // 数据库连接池不存在,新建连接池
                try {
                    configMysql = new BoneCPConfig();
                    try {
                        Class.forName(portalDataSource.getJdbcDriver());// 加载Mysql数据驱动
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(), e);
                    }
                    //数据库的JDBC URL
                    configMysql.setJdbcUrl(portalDataSource.getUrl());
                    //数据库用户名
                    configMysql.setUsername(portalDataSource.getUser());
                    //数据库用户密码
                    configMysql.setPassword(portalDataSource.getPassword());
                    //数据库连接池的最小连接数
                    configMysql.setMinConnectionsPerPartition(portalDataSource.getMinConnectionsPerPartition());
                    //数据库连接池的最大连接数
                    configMysql.setMaxConnectionsPerPartition(portalDataSource.getMaxConnectionsPerPartition());
                    //设置分区  分区数为3 ，默认值2，最小1，推荐3-4，视应用而定
                    configMysql.setPartitionCount(4);
                    //当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数  每次去拿数据库连接的时候一次性要拿几个,默认值：2
                    configMysql.setAcquireIncrement(5);
                    configMysql.setStatementsCacheSize(0);
                    configMysql.setCloseConnectionWatch(false);
                    configMysql.setLogStatementsEnabled(true);
                    configMysql.setLazyInit(false);
                    configMysql.setTransactionRecoveryEnabled(false);
                    //设置数据库连接池
                    connectionPoolMysql = new BoneCP(configMysql);
                    logger.info("创建平台数据库连接池" + configMysql.getJdbcUrl());
                } catch (SQLException e) {
                    logger.error("创建平台数据库连接池失败" + e.getMessage(), e);
                }
            }
            return connectionPoolMysql;
        }
    }

    /**
     * 通过BoneCP连接池获取 MySql数据库连接
     *
     * @return
     */
    private static ConnectionHandle getMySqlConnectionHandle(PortalDataSource portalDataSource) {
        synchronized (objectConnMysql) {
            ConnectionHandle connectionHandle = null;
            try {
                connectionHandle = (ConnectionHandle) getMySqlInstance(portalDataSource).getConnection();
                logger.info("平台数据库连接地址：" + connectionHandle.getUrl());
            } catch (Exception e) {
                logger.error("平台数据库连接失败" + e.getMessage());
            }
            //返回所建立的数据库连接
            return connectionHandle;
        }
    }

    /**
     * MySql APP
     * <p>
     * 静态工厂方法 创建数据库连接池
     *
     * @param portalDataSource
     * @return
     */
    public static BoneCP getMySqlInstanceApp(PortalDataSource portalDataSource) {
        synchronized (objectMysqlApp) {
            if (connectionPoolMysqlApp == null) {
                // 数据库连接池不存在,新建连接池
                try {
                    configMysqlApp = new BoneCPConfig();
                    try {
                        Class.forName(portalDataSource.getJdbcDriver());// 加载Mysql数据驱动
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(), e);
                    }
                    //数据库的JDBC URL
                    configMysqlApp.setJdbcUrl(portalDataSource.getUrl());
                    //数据库用户名
                    configMysqlApp.setUsername(portalDataSource.getUser());
                    //数据库用户密码
                    configMysqlApp.setPassword(portalDataSource.getPassword());
                    //数据库连接池的最小连接数
                    configMysqlApp.setMinConnectionsPerPartition(portalDataSource.getMinConnectionsPerPartition());
                    //数据库连接池的最大连接数
                    configMysqlApp.setMaxConnectionsPerPartition(portalDataSource.getMaxConnectionsPerPartition());
                    //设置分区  分区数为3 ，默认值2，最小1，推荐3-4，视应用而定
                    configMysqlApp.setPartitionCount(4);
                    //当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数  每次去拿数据库连接的时候一次性要拿几个,默认值：2
                    configMysqlApp.setAcquireIncrement(5);
                    configMysqlApp.setStatementsCacheSize(0);
                    configMysqlApp.setCloseConnectionWatch(false);
                    configMysqlApp.setLogStatementsEnabled(true);
                    configMysqlApp.setLazyInit(false);
                    configMysqlApp.setTransactionRecoveryEnabled(false);
                    //设置数据库连接池
                    connectionPoolMysqlApp = new BoneCP(configMysqlApp);
                    logger.info("创建APP数据库连接池" + configMysqlApp.getJdbcUrl());
                } catch (SQLException e) {
                    logger.error("创建APP数据库连接池失败" + e.getMessage(), e);
                }
            }
            return connectionPoolMysqlApp;
        }
    }

    /**
     * 通过BoneCP连接池获取 MySql APP 数据库连接
     *
     * @return
     */
    private static ConnectionHandle getMySqlConnectionHandleApp(PortalDataSource portalDataSource) {
        synchronized (objectConnMysqlApp) {
            ConnectionHandle connectionHandle = null;
            try {
                connectionHandle = (ConnectionHandle) getMySqlInstanceApp(portalDataSource).getConnection();
                logger.info("APP数据库连接地址：" + connectionHandle.getUrl());
            } catch (Exception e) {
                logger.error("APP数据库连接失败" + e.getMessage());
            }
            //返回所建立的数据库连接
            return connectionHandle;
        }
    }

    /**
     * SAP HANA
     * <p>
     * 静态工厂方法 创建数据库连接池
     *
     * @param portalDataSource
     * @return
     */
    public static BoneCP getHanaInstance(PortalDataSource portalDataSource) {
        synchronized (objectHana) {
            if (connectionPoolHana == null) {
                // 数据库连接池不存在,新建连接池
                try {
                    configHana = new BoneCPConfig();
                    try {
                        Class.forName(portalDataSource.getJdbcDriver());// 加载Mysql数据驱动
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(), e);
                    }
                    //数据库的JDBC URL
                    configHana.setJdbcUrl(portalDataSource.getUrl());
                    //数据库用户名
                    configHana.setUsername(portalDataSource.getUser());
                    //数据库用户密码
                    configHana.setPassword(portalDataSource.getPassword());
                    //数据库连接池的最小连接数
                    configHana.setMinConnectionsPerPartition(portalDataSource.getMinConnectionsPerPartition());
                    //数据库连接池的最大连接数
                    configHana.setMaxConnectionsPerPartition(portalDataSource.getMaxConnectionsPerPartition());
                    //设置分区  分区数为3 ，默认值2，最小1，推荐3-4，视应用而定
                    configHana.setPartitionCount(4);
                    //当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数  每次去拿数据库连接的时候一次性要拿几个,默认值：2
                    configHana.setAcquireIncrement(5);
                    //设置数据库连接池
                    connectionPoolHana = new BoneCP(configHana);
                    logger.info("创建数据库连接池" + configHana.getJdbcUrl());
                } catch (SQLException e) {
                    logger.error("创建数据库连接池失败" + e.getMessage(), e);
                }
            }
            return connectionPoolHana;
        }
    }

    /**
     * 通过BoneCP连接池获取 Hana数据库连接
     *
     * @return
     */
    private static ConnectionHandle getHanaConnectionHandle(PortalDataSource portalDataSource) {
        synchronized (objectConnHana) {
            ConnectionHandle connectionHandle = null;
            try {
                connectionHandle = (ConnectionHandle) getHanaInstance(portalDataSource).getConnection();
                logger.info("数据库连接地址：" + connectionHandle.getUrl());
            } catch (Exception e) {
                logger.error("数据库连接失败" + e.getMessage());
            }
            //返回所建立的数据库连接
            return connectionHandle;
        }
    }

    /**
     * 新建数据库连接
     *
     * @return
     */
    private static Connection getConnectionNew(PortalDataSource portalDataSource) {
        Connection connection = null;
        try {
            //classLoader,加载对应驱动
            Class.forName(portalDataSource.getJdbcDriver());
            connection = (Connection) DriverManager.getConnection(portalDataSource.getUrl(), portalDataSource.getUser(), portalDataSource.getPassword());
            logger.info("新建数据库连接地址：" + portalDataSource.getUrl());
        } catch (Exception e) {
            logger.error("新建数据库连接失败" + e.getMessage());
        }
        //返回所建立的数据库连接
        return connection;
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private static Connection getConnection(PortalDataSource portalDataSource) {
        if (ConstantsUtil.DataSourceCode.DATA_000001.equals(portalDataSource.getCode())) {
            //主mysql连接池
            return getMySqlConnectionHandle(portalDataSource);
        } else if (ConstantsUtil.DataSourceCode.DATA_000002.equals(portalDataSource.getCode())) {
            //jdbc动态数据源
            return getConnectionNew(portalDataSource);
        } else if (ConstantsUtil.DataSourceCode.DATA_000003.equals(portalDataSource.getCode())) {
            //主hana连接池
            return getHanaConnectionHandle(portalDataSource);
        } else if (ConstantsUtil.DataSourceCode.DATA_000004.equals(portalDataSource.getCode())) {
            //帆软
            return getConnectionNew(portalDataSource);
        } else if (ConstantsUtil.DataSourceCode.DATA_000005.equals(portalDataSource.getCode())) {
            //APP-85库
            return getConnectionNew(portalDataSource);
        }
        return null;
    }

    /**
     * 通过存储过程查询，将JDBC ResultSet结果集转成List
     *
     * @param sql
     * @param portalDataSource 数据源
     * @return
     */
    @Override
    public List<Map<String, Object>> queryCallPro(String sql, PortalDataSource portalDataSource) {
        Connection conn;
        ResultSet rs = null;
        CallableStatement cs = null;
        // 获取连接，即连接到数据库
        conn = getConnection(portalDataSource);
        List<Map<String, Object>> reMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowData = null;
        try {
            //调用存储过程
            cs = conn.prepareCall(sql);
            //执行查询操作，并获取结果集
            rs = cs.executeQuery();
            //获得结果集结构信息,元数据
            ResultSetMetaData md = rs.getMetaData();
            //获得列数
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    //md.getColumCount() 返回所有字段的数目
                    //md.getColumName() 获取sql语句中field的原始名字
                    //md.getColumType() 根据字段的索引值取得字段的类型
                    //md.getColumnLabel() 获取field的SQL AS的值
                    rowData.put(md.getColumnLabel(i), rs.getObject(i));
                }
                reMapList.add(rowData);
            }
        } catch (SQLException e) {
            logger.error("查询失败" + e.getMessage());
        } finally {
            close(conn, cs, rs);
        }
        return reMapList;
    }

    /**
     * 通过自定义sql语句查询，将JDBC ResultSet结果集转成List
     *
     * @param sql
     * @param portalDataSource 数据源
     * @return
     */
    @Override
    public List<Map<String, Object>> queryExecuteSql(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> reMapList = new ArrayList<Map<String, Object>>();
        Map<String, Object> rowData = null;
        try {
            // 获取连接，即连接到数据库
            conn = getConnection(portalDataSource);
            stmt = conn.createStatement();
            //执行查询操作，并获取结果集
            rs = stmt.executeQuery(sql);
            //获得结果集结构信息,元数据
            ResultSetMetaData md = rs.getMetaData();
            //获得列数
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                reMapList.add(rowData);
            }
        } catch (SQLException e) {
            logger.error("查询失败" + e.getMessage());
        } finally {
            close(conn, stmt, rs);
        }
        return reMapList;
    }


    /**
     * 插入表数据
     */
    @Override
    public void insertTable(String sql, PortalDataSource portalDataSource) {
        if (StringUtils.isBlank(sql)) {
            return;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs;

        // 获取连接，即连接到数据库
        conn = getConnection(portalDataSource);
        try {
            st = conn.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("执行插入数据失败" + e.getMessage(), e);
        } finally {
            close(conn, st);
        }
    }

    /**
     * 更新表数据
     *
     * @auth zzh
     */
    @Override
    public void updateTable(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        // 获取连接，即连接到数据库
        conn = getConnection(portalDataSource);
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            logger.error("执行更新数据失败" + e.getMessage(), e);
        } finally {
            close(conn, ps);
        }
    }


    /**
     * 关闭数据库连接
     *
     * @param conn
     * @param statement
     */
    void close(Connection conn, Statement statement) {
        try {
            //关闭流
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } finally {
                if (conn != null && !conn.isClosed()) {
                    conn.close(); //关闭线程池句柄
                }
            }
            //logger.info("数据库连接是否关闭：" + conn.isClosed());
        } catch (SQLException e) {
            logger.info("数据库链接关闭异常：" + e.getMessage(), e);
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     * @param rs
     */
    void close(Connection conn, ResultSet rs) {
        try {
            if (null != rs && rs.isClosed()) {
                rs.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close(); //关闭线程池句柄
            }
            //logger.info("数据库连接是否关闭：" + conn.isClosed());;
        } catch (SQLException e) {
            logger.info("数据库链接关闭异常：" + e.getMessage(), e);
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     * @param statement
     * @param rs
     */
    void close(Connection conn, Statement statement, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.info("数据库链接关闭异常：" + e.getMessage(), e);
        } finally {
            close(conn, statement);
        }
        //logger.info("数据库连接是否关闭：" + conn.isClosed());
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     * @param callableStatement
     * @param rs
     */
    void close(Connection conn, CallableStatement callableStatement, ResultSet rs) {
        try {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } finally {
                close(conn, callableStatement);
            }
            //logger.info("数据库连接是否关闭：" + conn.isClosed());
        } catch (SQLException e) {
            logger.info("数据库链接关闭异常：" + e.getMessage(), e);
        }
    }

}


