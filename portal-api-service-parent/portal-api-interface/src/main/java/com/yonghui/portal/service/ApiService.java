package com.yonghui.portal.service;

import java.util.List;
import java.util.Map;

/**
 * api系统总接口
 * <p>
 * Created by 张海 on 2017/05/11
 */
public interface ApiService {

    /**
     * JDBC 执行存储过程或者sql语句
     *
     * @param typeCode  报表编码
     * @param parameter 请求参数 如：aa=AAA@@bb=CC@@dd=DD
     * @return
     */
    List<Map<String, Object>> jdbcProListResultListMapByParam(String typeCode, String parameter);

}
