package com.yonghui.portal.service.impl;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.ConnectionHandle;
import com.yonghui.portal.model.api.PortalDataSource;
import com.yonghui.portal.service.ApiDataBaseSqlService;
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

    private static final Object object = new Object();
    private static final Object objectConn = new Object();
    private static BoneCPConfig config;
    private static BoneCP connectionPool;

    /**
     * 静态工厂方法 创建数据库连接池
     *
     * @param portalDataSource
     * @return
     */
    public static BoneCP getInstance(PortalDataSource portalDataSource) {
        synchronized (object) {
            if (connectionPool == null) {
                // 数据库连接池不存在,新建连接池
                try {
                    config = new BoneCPConfig();
                    try {
                        Class.forName(portalDataSource.getJdbcDriver());// 加载Mysql数据驱动
                    } catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(), e);
                    }
                    //数据库的JDBC URL
                    config.setJdbcUrl(portalDataSource.getUrl());
                    //数据库用户名
                    config.setUsername(portalDataSource.getUser());
                    //数据库用户密码
                    config.setPassword(portalDataSource.getPassword());
                    //数据库连接池的最小连接数
                    config.setMinConnectionsPerPartition(portalDataSource.getMinConnectionsPerPartition());
                    //数据库连接池的最大连接数
                    config.setMaxConnectionsPerPartition(portalDataSource.getMaxConnectionsPerPartition());
                    //config.setConnectionTimeout(200, TimeUnit.SECONDS);
                    //设置分区  分区数为3 ，默认值2，最小1，推荐3-4，视应用而定
                    config.setPartitionCount(4);
                    //当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数 <!-- 每次去拿数据库连接的时候一次性要拿几个,默认值：2
                    config.setAcquireIncrement(5);
                    config.setStatementsCacheSize(0);
                    config.setInitSQL("select 1");
                    config.setConnectionTestStatement("");
                    config.setCloseConnectionWatch(false);
                    config.setLogStatementsEnabled(true);
                    config.setLazyInit(false);
                    config.setTransactionRecoveryEnabled(false);
                    //设置数据库连接池
                    connectionPool = new BoneCP(config);
                    logger.info("创建数据库连接池" + config.getJdbcUrl());
                } catch (SQLException e) {
                    logger.error("创建数据库连接池失败" + e.getMessage(), e);
                }
            }
            return connectionPool;
        }
    }

    /**
     * 通过BoneCP连接池获取数据库连接
     *
     * @return
     */
    private static ConnectionHandle getConnectionHandle(PortalDataSource portalDataSource) {
        synchronized (objectConn) {
            ConnectionHandle connectionHandle = null;
            try {
                connectionHandle = (ConnectionHandle) getInstance(portalDataSource).getConnection();
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
        if (portalDataSource.getConnectionTag() == 1) {
            //从连接池中获取数据库连接
            return getConnectionHandle(portalDataSource);
        } else if (portalDataSource.getConnectionTag() == 2) {
            //新建数据库连接
            return getConnectionNew(portalDataSource);
        }
        return null;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close(); //关闭线程池句柄
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
                    //md.getColumName() 根据字段的索引值取得字段的名称
                    //md.getColumType() 根据字段的索引值取得字段的类型
                    rowData.put(md.getColumnName(i), rs.getObject(i));
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
     * 后台分页显示
     *
     * @param tabName
     * @param pageNo
     * @param pageSize
     * @param tab_fields
     * @return
     */
    @Override
    public List<String[]> queryForPage(String tabName, int pageNo, int pageSize, String[] tab_fields, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        List<String[]> list = new ArrayList<String[]>();
        try {
            // 获取连接，即连接到数据库
            conn = getConnection(portalDataSource);
            String sql = "select * from " + tabName + " LIMIT ?,? ; ";
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //注入参数
            ps.setInt(1, pageNo);
            ps.setInt(2, pageSize);
            //查询结果集
            rs = ps.executeQuery();
            //存放结果集
            while (rs.next()) {
                String[] result = new String[tab_fields.length];
                for (int i = 0; i < tab_fields.length; i++) {
                    result[i] = rs.getString(tab_fields[i]);
                }
                list.add(result);
            }
        } catch (SQLException e) {
            logger.error("查询失败" + e.getMessage());
        } finally {
            close(conn, ps);
        }
        return list;
    }

    /**
     * 查询表 【查询结果的顺序要和数据库字段的顺序一致】
     *
     * @param tabName    表名
     * @param fields     参数字段
     * @param data       参数字段数据
     * @param tab_fields 数据库的字段
     */
    @Override
    public String[] query(String tabName, String[] fields, String[] data, String[] tab_fields, PortalDataSource portalDataSource) {
        Connection conn;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        String[] result = null;
        try {
            String sql = "select * from " + tabName + " where ";
            int length = fields.length;
            for (int i = 0; i < length; i++) {
                sql += fields[i] + " = ? ";
                //防止最后一个,
                if (i < length - 1) {
                    sql += " and ";
                }
            }
            sql += ";";
            logger.info("查询sql:" + sql);
            //预处理SQL 防止注入
            excutePs(sql, length, data);
            ps = conn.prepareStatement(sql);
            //查询结果集
            rs = ps.executeQuery();
            //存放结果集
            result = new String[tab_fields.length];
            while (rs.next()) {
                for (int i = 0; i < tab_fields.length; i++) {
                    result[i] = rs.getString(tab_fields[i]);
                }
            }
        } catch (SQLException e) {
            logger.error("查询失败" + e.getMessage());
        } finally {
            close(conn, ps, rs);
        }
        return result;
    }

    /**
     * 获取查询sql的总数
     *
     * @return
     */
    @Override
    public Integer getCount(String sql, PortalDataSource portalDataSource) {
        Connection conn;
        PreparedStatement ps = null;
        Statement stmt;
        ResultSet rs;
        logger.info("查询sql:" + sql);
        int count = 0;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("获取总数失败" + e.getMessage(), e);
        } finally {
            close(conn, ps);
        }
        return count;
    }

    /**
     * @param querySql
     * @return
     * @notes 通过sql查询数据返回一个Map
     */
    @Override
    public Map<String, Object> queryMapBysql(String querySql, PortalDataSource portalDataSource) {
        logger.info("<<<<<<<<<<<<<<<<查询mapSql" + querySql);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        logger.error("<<<<<<<<<<查询数据的slq>>>>>>>>>>>>>>>>" + querySql);
        Map<String, Object> reMap = new HashMap<String, Object>();
        try {
            conn = getConnectionHandle(portalDataSource);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                StringBuffer sb = new StringBuffer();
                for (int j = 1; j <= columnCount; j++) {
                    Object o = rs.getObject(j);
                    reMap.put(rsmd.getColumnName(j), rs.getObject(j));
                }
            }
        } catch (SQLException e) {
            logger.error("<<<<<<<<<<<<<查询Map集合失败" + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
        }
        return reMap;
    }

    /**
     * 清空表数据
     *
     * @param tabName 表名称
     */
    @Override
    public void delete(String tabName, PortalDataSource portalDataSource) {
        Connection conn;
        PreparedStatement ps = null;
        Statement stmt;
        ResultSet rs;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库

        try {
            String sql = "delete from " + tabName + ";";
            logger.info("删除数据的sql:" + sql);
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            logger.error("删除数据失败" + e.getMessage());
        } finally {
            close(conn, ps);
        }
    }

    /**
     * 删除库中的指定表
     *
     * @param delsql 删除语句
     */
    @Override
    public void deleteBySql(String delsql, PortalDataSource portalDataSource) {
        Connection conn;
        PreparedStatement ps = null;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库

        try {
            logger.info("删除数据的sql:" + delsql);
            //预处理SQL 防止注入
            ps = conn.prepareStatement(delsql);
            //执行
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            logger.error("删除数据失败" + e.getMessage());
        } finally {
            close(conn, ps);
        }
    }

    /**
     * 删除表
     *
     * @param tabName 表名称
     * @auth zzh
     */
    @Override
    public void dropTable(String tabName, PortalDataSource portalDataSource) {
        Connection conn;
        PreparedStatement ps = null;
        ResultSet rs;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库

        try {
            String sql = "DROP TABLE IF EXISTS " + tabName + ";";
            logger.info("删除表的sql:" + sql);
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            ps.executeUpdate();
            conn.commit();
            //关闭线程池句柄
        } catch (SQLException e) {
            logger.error("删除表失败" + e.getMessage());
        } finally {
            close(conn, ps);
        }
    }

    /**
     * 清空表数据
     *
     * @param tableList 表名称集合
     */
    @Override
    public void dropTableList(List<String> tableList, PortalDataSource portalDataSource) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        logger.info("<<<<<<<<<<<<<<<需要删除的表集合>>>>>>>>>>>>>>>" + tableList.toString());
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        try {
            if (null != tableList && tableList.size() > 0) {
                //conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
                stmt = conn.createStatement();
                for (int i = 0; i < tableList.size(); i++) {
                    String sql = "DROP TABLE IF EXISTS " + tableList.get(i);
                    logger.info("<<<<<<<<<<<<<<<删除表sql>>>>>>>>>>>>>>>" + sql);
                    stmt.addBatch(sql);
                }
                stmt.executeBatch();
                conn.commit();
            }
        } catch (SQLException e) {
            logger.error("批量执行sql失败" + e.getMessage(), e);
        } finally {
            close(conn, stmt);
        }
    }

    /**
     * 用于注入参数
     *
     * @param data
     * @throws SQLException
     */
    @Override
    public void excutePs(String sql, int length, String[] data) throws SQLException {
        Connection conn = null;
        PreparedStatement ps;
        Statement stmt;
        ResultSet rs;
        //预处理SQL 防止注入
        ps = conn.prepareStatement(sql);
        //注入参数
        for (int i = 0; i < length; i++) {
            ps.setString(i + 1, data[i]);
        }
    }

    /**
     * 执行批量sql
     *
     * @param sqlList ,需要执行的sql 集合
     * @throws SQLException
     */
    @Override
    public void excuteSm(List<String> sqlList, PortalDataSource portalDataSource) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        logger.info("批量sql集合:" + sqlList);
        logger.info("批量sql集合:" + sqlList.size());
        try {
            if (null != sqlList && sqlList.size() > 0) {
                conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                for (int i = 0; i < sqlList.size(); i++) {
                    stmt.addBatch(sqlList.get(i));
                }
                stmt.executeBatch();
                conn.commit();
                logger.info("<<<<<<<<<<<<<<<<<<执行成功>>>>>>>>>>>>>>>>>>");
            }
        } catch (SQLException e) {
            logger.error("批量执行sql失败" + e.getMessage());
        } finally {
            close(conn, stmt);
        }
    }

    /**
     * 判断表是否存在
     *
     * @param tabName
     * @return
     */
    @Override
    public boolean exitTable(String tabName, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean flag = false;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        try {
            String sql = "select id from " + tabName + ";";
            //预处理SQL 防止注入
            ps = conn.prepareStatement(sql);
            //执行
            flag = ps.execute();

        } catch (SQLException e) {
            logger.error("判断表是否存在失败" + e.getMessage());
        } finally {
            close(conn, ps);
        }
        return flag;
    }

    /**
     * 创建表
     */
    @Override
    public void createTable(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        try {
            logger.info("建表语句是：" + sql);
            conn = getConnectionHandle(portalDataSource);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            logger.error("建表失败" + e.getMessage(), e);
        } finally {
            close(conn, ps);
        }
    }

    /**
     * 插入表数据
     *
     * @auth zhzh
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
//        sql = "INSERT hossv2_new_dev.ods_broker_dt_20170105 SELECT * FROM broker WHERE 1=1 AND NOW() > create_time LIMIT 200000";

        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        try {
            logger.info("插入数据语句是：" + sql);
            st = conn.createStatement();
//            ps = conn.prepareStatement(sql);
            st.executeUpdate(sql);
//            ps.executeUpdate(sql);
            conn.commit();
        } catch (SQLException e) {
            logger.error("执行插入数据失败" + e.getMessage(), e);
        } finally {
            close(conn, st);
        }
    }

    /**
     * 更新表数据
     */
    @Override
    public void updateTable(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConnectionHandle(portalDataSource); // 首先要获取连接，即连接到数据库
        try {
            logger.info("更新数据语句是：" + sql);
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
     * @param tableName
     * @return
     * @notes 获取创建表的sql
     */
    @Override
    public String getCreateTableSql(String tableName, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        String createTableSql = "";
        conn = getConnectionHandle(portalDataSource);
        String sql = "SHOW CREATE TABLE " + tableName;
        try {
            logger.info("获取创建表的sql：" + sql);
            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                createTableSql = resultSet.getString("Create Table");
            }
        } catch (SQLException e) {
            logger.error("执行创建表失败" + e.getMessage(), e);
        } finally {
            close(conn, ps);
        }
        return createTableSql;
    }

//    /**
//     * @param tableName
//     * @param dbName
//     * @return
//     * @notes 获取创建表的sql
//     */
//    @Override
//    public List<String> getCreateIndexSql(String tableName, PortalDataSource portalDataSource) {
//        List<String> indexList = new ArrayList<String>();
////        Connection conn = null;
////        PreparedStatement ps = null;
////        String createIndexSql = "";
////        String Columns = "";
////        String Indexes = "";
////        String Index_Type = "";
////        conn = getConnectionHandle(portalDataSource);
////        String sql = "SHOW CREATE INDEX FROM  " + tableName;
////        try {
////            logger.info("获取创建表的sql：" + sql);
////            ps = conn.prepareStatement(sql);
////            ResultSet resultSet = ps.executeQuery();
////            while (resultSet.next()) {
////                Columns = resultSet.getString("Columns");
////                Indexes = resultSet.getString("Indexes");
////                Index_Type = resultSet.getString("Index Type");
////
////                if(Index_Type.equals("Unique")){
////                    Index_Type = "Unique";
////                }else if(Index_Type.equals("Unique")){
////
////                }
////                indexList.add( "alter table table_name add "+Index_Type+" ("+Columns+")");
////            }
//        ewruen
//        } catch (SQLException e) {
//            logger.error("<<<<<<<<<<<<<<<<<执行生成索引失败" + e.getMessage(), e);
//        } finally {
//            close(conn, ps);
//        }
//        return indexList;
//    }

    /**
     * @param targetTableName
     * @return
     * @note 获取插入的sql语句
     */
    public String getInsertTableSql(String querySql, String targetTableName, PortalDataSource portalDataSource) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        logger.info("<<<<<<<<<<查询数据的slq>>>>>>>>>>>>>>>>" + querySql);
        StringBuffer insertTableSql = new StringBuffer();
        StringBuffer valueSBuffer = new StringBuffer();
        try {
            conn = getConnectionHandle(portalDataSource);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            StringBuffer sbf = new StringBuffer();
            for (int m = 1; m <= columnCount; m++) {
                if (m == columnCount) {
                    sbf.append(rsmd.getColumnName(m));
                } else {
                    sbf.append(rsmd.getColumnName(m) + ",");
                }
            }
            insertTableSql.append("insert into " + targetTableName + "(" + sbf.toString() + ") values ");
            int i = 0;
            while (rs.next()) {
                i++;
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j <= columnCount; j++) {
                    Object o = rs.getObject(j);
                    String dataType = rsmd.getColumnTypeName(j).toLowerCase();
                    if (null == o || StringUtils.isBlank(o.toString()) || o.toString().equals("0000-00-00 00:00:00")) {
                        /**字符串类型**/
                        if (dataType.equals("varchar")) {
                            sb.append("' '");
                        } else if (dataType.equals("datetime")) {
                            sb.append("'1970-12-12 00:00:00'");
                        } else {
                            sb.append("null");
                        }
                    } else {
                        /**整形或者字节符号无需加单引号**/
                        if (dataType.equals("bigint") || dataType.equals("int") || dataType.equals("bit") || dataType.equals("numeric") || dataType.equals("tinyint")) {
                            sb.append(o);
                        } else {
                            sb.append("'" + o.toString().replace("'", "") + "'");
                        }
                    }
                    if (j != columnCount) {
                        sb.append(",");
                    }
                }
                valueSBuffer.append("(" + sb.toString() + ")").append(",");
            }
        } catch (SQLException e) {
            logger.error("生成插入sql失败" + e.getMessage(), e);
        } finally {
            close(conn, stmt, rs);
            if (StringUtils.isNotBlank(valueSBuffer.toString())) {
                return insertTableSql.append(valueSBuffer.toString().substring(0, valueSBuffer.length() - 1)).toString();
            } else {
                return null;
            }
        }
    }

    /**
     * 执行sql语句
     *
     * @param sql
     */
    @Override
    public void execute(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        PreparedStatement ps = null;
        conn = getConnectionHandle(portalDataSource);
        try {
            logger.info("==== 执行语句：" + sql);
            ps = conn.prepareStatement(sql);
            ps.execute(sql);
            conn.commit();
        } catch (SQLException e) {
            logger.error("执行失败: " + e.getMessage(), e);
        } finally {
            close(conn, ps);
        }
    }

    /**
     * @return
     * @ntoes 获取所有表明
     */
    public List<String> getTableNames(PortalDataSource portalDataSource) {
        List<String> tableList = new ArrayList<String>();
        Connection conn = null;
        ResultSet rs = null;
        conn = getConnectionHandle(portalDataSource);
        try {
            DatabaseMetaData md = conn.getMetaData();
            rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                String tableName = rs.getString(3);
                if ((tableName.substring(0, 4).equals("dwd_")) || (tableName.substring(0, 4).equals("ods_"))) {
                    tableList.add(tableName);
                }
            }
        } catch (SQLException e) {
            logger.error("执行失败: " + e.getMessage(), e);
        } finally {
            close(conn, rs);
            return tableList;
        }
    }
}


