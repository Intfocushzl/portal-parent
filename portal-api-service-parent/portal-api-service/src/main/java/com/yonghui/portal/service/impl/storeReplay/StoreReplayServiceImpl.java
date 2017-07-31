package com.yonghui.portal.service.impl.storeReplay;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yonghui.portal.model.storeReplay.ActionPlan;
import com.yonghui.portal.model.storeReplay.Evaluate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.ConnectionHandle;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.service.storeReplay.StoreRePlayService;
import com.yonghui.portal.util.ConstantsUtil;

/**
 * 门店日复盘
 * huangzenglei@intfocus.com
 */

@Service
public class StoreReplayServiceImpl implements StoreRePlayService{

	private static final Logger logger = Logger.getLogger(StoreReplayServiceImpl.class);
	
    /**
     * Mysql连接池
     */
    private static final Object objectMysql = new Object();
    private static final Object objectConnMysql = new Object();
    private static BoneCPConfig configMysql;
    private static BoneCP connectionPoolMysql;

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
                    logger.info("创建数据库连接池" + configMysql.getJdbcUrl());
                } catch (SQLException e) {
                    logger.error("创建数据库连接池失败" + e.getMessage(), e);
                }
            }
            return connectionPoolMysql;
        }
    }

    /**
     * 通过BoneCP连接池获取 MySql数据库连接
     * @return
     */
    private static ConnectionHandle getMySqlConnectionHandle(PortalDataSource portalDataSource) {
        synchronized (objectConnMysql) {
            ConnectionHandle connectionHandle = null;
            try {
                connectionHandle = (ConnectionHandle) getMySqlInstance(portalDataSource).getConnection();
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
     * @return
     */
    private static Connection getConnection(PortalDataSource portalDataSource) {
        if (ConstantsUtil.DataSourceCode.DATA_000001.equals(portalDataSource.getCode())) {
            //主mysql连接池
            return getMySqlConnectionHandle(portalDataSource);
        } else if (ConstantsUtil.DataSourceCode.DATA_000002.equals(portalDataSource.getCode())) {
            //jdbc动态数据源
            return getConnectionNew(portalDataSource);
        }else if(ConstantsUtil.DataSourceCode.DATA_000004.equals(portalDataSource.getCode())){
            return getConnectionNew(portalDataSource);
        }
        return null;
    }

    /**
     * 执行 Insert
     * */
	@Override
	public void excuteUpdate(String sql, PortalDataSource portalDataSource) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 获取连接，即连接到数据库
            conn = getConnection(portalDataSource);
            stmt = conn.createStatement();
            //执行查询操作，并获取结果集
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("信息执行 Insert 失败" + e.getMessage());
        } finally {
            close(conn, stmt);
        }
	}

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
     * 返回用户信息List<Map<String,Object>>
     * */
    public List<Map<String, Object>> queryUserInfo(String sql, PortalDataSource portalDataSource) {
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
                    if (1 == i) {
                        rowData.put("user_id" , rs.getObject(1));
                    } else if (4 == i) {
                        rowData.put("group_id" , rs.getObject(4));
                    } else if ( 6 == i) {
                        rowData.put("role_id" , rs.getObject(6));
                    } else {
                        rowData.put(md.getColumnName(i), rs.getObject(i));
                    }
                }
                reMapList.add(rowData);
            }
        } catch (SQLException e) {
            logger.error("用户信息查询失败" + e.getMessage());
        } finally {
            close(conn, stmt, rs);
        }
        return reMapList;
    }

    /**
     * 返回门店列表信息List<Map<String,Object>>
     * */
    public List<Map<String, Object>> queryAreaStoreShop(String sql, PortalDataSource portalDataSource) {
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
            logger.error("门店列表信息查询失败" + e.getMessage());
        } finally {
            close(conn, stmt, rs);
        }
        return reMapList;
    }

    /**
     * 查询 行动方案
     * */
    @Override
    public List<ActionPlan> queryActionPlan(String sql, PortalDataSource portalDataSource) {
        List<ActionPlan> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
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
            ActionPlan actionPlan = null;
            while (rs.next()) {
                actionPlan = new ActionPlan();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (int i = 1; i <= columnCount; i++) {
                        if (null != rs.getObject(i)  && "userId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setUserId((String)rs.getObject(i));;
                        } else if (null != rs.getObject(i) && "userName".equalsIgnoreCase(md.getColumnName(i).replace("_",""))) {
                            actionPlan.setUserName((String) rs.getObject(i));
                        } else if (null != rs.getObject(i) && "storeCode".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setStoreCode((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "storeName".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setStoreName((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "userRoleId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setUserRoleId((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "situationAnalysis".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setSituationAnalysis((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "actionPlan".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setActionPlan((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "remark".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                            actionPlan.setRemark((String)rs.getObject(i));
                        } else if (null != rs.getObject(i) && "created_at".equals(md.getColumnName(i))) {
                            actionPlan.setCreateAt(format.format(rs.getObject(i)));
                        } else if (null != rs.getObject(i) && "updated_at".equals(md.getColumnName(i))) {
                            actionPlan.setUpdateAt(format.format(rs.getObject(i)));
                        } else if (null != rs.getObject(i) && "id".equals(md.getColumnName(i))) {
                            actionPlan.setId((Integer)rs.getObject(i));
                        }
                }
                list.add(actionPlan);
            }
        } catch (SQLException e) {
            logger.error("查询‘行动方案’失败" + e.getMessage());
        } finally {
            close(conn, stmt, rs);
        }
        return list;
    }

    /**
     * 查询 评价
     * */
    @Override
    public List<Evaluate> queryEvaluate(String sql, PortalDataSource portalDataSource) {
        List<Evaluate> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
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
            Evaluate evaluate = null;
            while (rs.next()) {
                evaluate = new Evaluate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (int i = 1; i <= columnCount; i++) {
                    if (null != rs.getObject(i) && "id".equals(md.getColumnName(i))) {
                        evaluate.setId((Integer)rs.getObject(i));
                    } else if (null != rs.getObject(i)  && "replyUserId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setReplyUserId((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "userName".equalsIgnoreCase(md.getColumnName(i).replace("_",""))) {
                        evaluate.setUserName((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "storeId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setStoreId((String )rs.getObject(i));
                    } else if (null != rs.getObject(i) && "storeName".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setStoreName((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "userRoleId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setUserRoleId((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "actionPlanId".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setActionPlanId((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "evaluation".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setEvaluation((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "remark".equalsIgnoreCase( md.getColumnName(i).replace("_",""))) {
                        evaluate.setRemark((String)rs.getObject(i));
                    } else if (null != rs.getObject(i) && "created_at".equals(md.getColumnName(i))) {
                        evaluate.setCreateAt(format.format(rs.getObject(i)));
                    } else if (null != rs.getObject(i) && "updated_at".equals(md.getColumnName(i))) {
                        evaluate.setUpdateAt(format.format(rs.getObject(i)));
                    }
                }
                list.add(evaluate);
            }
        } catch (SQLException e) {
            logger.error("查询‘评价列表’失败" + e.getMessage());
        } finally {
            close(conn, stmt, rs);
        }
        return list;
    }

    /**
     * 关闭数据库连接
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
