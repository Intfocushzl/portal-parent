package com.yonghui.portal.mapper.base;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 *
 * 张海 2017.05.11
 */
public interface BaseMapper<T> {

    /**
     * 保存对象
     *
     * @param t
     */
    void save(T t);

    /**
     * 保存
     *
     * @param map
     */
    void save(Map<String, Object> map);

    /**
     * 批量保存
     *
     * @param list
     */
    void saveBatch(List<T> list);

    /**
     * 更新对象
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 更新对象
     *
     * @param map
     * @return
     */
    int update(Map<String, Object> map);

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    int delete(Object id);

    /**
     * 删除对象
     *
     * @param map
     * @return
     */
    int delete(Map<String, Object> map);

    /**
     * 批量删除对象
     *
     * @param id
     * @return
     */
    int deleteBatch(Object[] id);

    /**
     * 查询对象
     *
     * @param id
     * @return
     */
    T queryObject(Object id);

    /**
     * 查询对象
     *
     * @param map
     * @return
     */
    T queryObjectByMap(Map<String, Object> map);

    /**
     * 查询集合
     *
     * @param map
     * @return
     */
    List<T> queryList(Map<String, Object> map);

    /**
     * 查询集合
     *
     * @param id
     * @return
     */
    List<T> queryList(Object id);

    /**
     * 查询总数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @return
     */
    int queryTotal();

    /**
     * 调用存储过程
     *
     * @param map
     * @return
     */
    Map<String, Object> callProMap(Map<String, Object> map);

    /**
     * 调用存储过程
     * @param map
     * @return
     */
    List <Map> callProListMap(Map<String, Object> map);
}
