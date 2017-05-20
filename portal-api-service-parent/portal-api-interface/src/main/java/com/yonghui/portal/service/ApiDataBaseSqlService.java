package com.yonghui.portal.service;

import com.yonghui.portal.model.report.PortalDataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库基础操作
 * <p>
 * 张海 2017.5.12
 */
public interface ApiDataBaseSqlService {

    /**
     * 通过存储过程查询数据
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    public List<Map<String, Object>> queryCallPro(String sql, PortalDataSource portalDataSource);

    /**
     * 通过sql查询
     *
     * @param sql
     * @param portalDataSource
     * @return
     */
    public List<Map<String, Object>> queryExecuteSql(String sql, PortalDataSource portalDataSource);

    /**
     * 查询表 【查询结果的顺序要和数据库字段的顺序一致】
     *
     * @param tabName    表名
     * @param fields     参数字段
     * @param data       参数字段数据
     * @param tab_fields 数据库的字段
     */
    public String[] query(String tabName, String[] fields, String[] data, String[] tab_fields, PortalDataSource portalDataSource);

    /**
     * 获取查询sql的总数
     *
     * @return
     */
    public Integer getCount(String sql, PortalDataSource portalDataSource);

    /**
     * @param querySql
     * @return
     * @notes 通过sql查询数据返回一个Map
     */
    public Map<String, Object> queryMapBysql(String querySql, PortalDataSource portalDataSource);

    /**
     * 后台分页显示
     *
     * @param tabName
     * @param pageNo
     * @param pageSize
     * @param tab_fields
     * @return
     */
    public List<String[]> queryForPage(String tabName, int pageNo, int pageSize, String[] tab_fields, PortalDataSource portalDataSource);

    /**
     * 清空表数据
     *
     * @param tabName 表名称
     */
    public void delete(String tabName, PortalDataSource portalDataSource);

    /**
     * 删除BI库中的指定表
     *
     * @param delsql 删除语句
     */
    public void deleteBySql(String delsql, PortalDataSource portalDataSource);

    /**
     * 清空表数据
     *
     * @param tabName 表名称
     */
    public void dropTable(String tabName, PortalDataSource portalDataSource);

    /**
     * 删除表集合
     *
     * @param tableList 表名称
     */
    public void dropTableList(List<String> tableList, PortalDataSource portalDataSource);

    /**
     * 用于注入参数
     *
     * @param data
     * @throws SQLException
     */
    public void excutePs(String sql, int length, String[] data) throws SQLException;

    /**
     * 执行批量sql
     *
     * @param sqlList ,需要执行的sql 集合
     * @throws SQLException
     */
    public void excuteSm(List<String> sqlList, PortalDataSource portalDataSource) throws SQLException;


    /**
     * 判断表是否存在
     *
     * @param tabName
     * @return
     */
    public boolean exitTable(String tabName, PortalDataSource portalDataSource);

    /**
     * 创建表
     */
    public void createTable(String sql, PortalDataSource portalDataSource);

    /**
     * 向库中插入表数据
     */
    public void insertTable(String sql, PortalDataSource portalDataSource);

    /**
     * @param tableName
     * @return
     * @notes 获取创建表的sql
     */
    public String getCreateTableSql(String tableName, PortalDataSource portalDataSource);

//    /**
//     * @param tableName
//     * @param dbName
//     * @return
//     * @notes 获取创建索引的sql
//     */
//    public String getCreateIndexSql(String tableName, PortalDataSource portalDataSource);

    /**
     * @param targetTableName
     * @return
     * @note 获取插入的sql语句
     */
    public String getInsertTableSql(String querySql, String targetTableName, PortalDataSource portalDataSource) throws SQLException;

    /**
     * 更新表数据
     *
     * @auth zzh
     */
    public void updateTable(String sql, PortalDataSource portalDataSource);

    /**
     * 执行sql语句
     *
     * @param sql
     */
    void execute(String sql, PortalDataSource portalDataSource);

    /**
     * @return
     * @ntoes 获取所有表明
     */
    List<String> getTableNames(PortalDataSource portalDataSource);

}
