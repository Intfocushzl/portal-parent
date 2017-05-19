package com.yonghui.portal.utils.redis;

import com.yonghui.portal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis业务数据处理
 *
 * @author 张海 2017.05.13
 */
@Component("redisBizUtilAdmin")
public class RedisBizUtilAdmin {

    @Autowired
    private RedisUtilAdmin redisUtil;

    //数据运营平台统一前缀
    private static String KEY_PREFIX = "portal";

    //////////////////////////报表存储过程配置信息////////////////////////////////
    // 设置存储过程信息到redis
    public void setPortalProcedure(String procodeOld, String procode, String value) {
        if (!StringUtils.isEmpty(procodeOld) && !procodeOld.equals(procode)) {
            redisUtil.remove(String.format("%s:report:procedure", KEY_PREFIX), procodeOld);
        }
        redisUtil.put(String.format("%s:report:procedure", KEY_PREFIX), procode, value);
    }

    // 根据存储过程唯一编码从redis中获取存储过程信息
    public String getPortalProcedure(String procode) {
        return redisUtil.get(String.format("%s:report:procedure", KEY_PREFIX), procode);
    }

    // 根据存储过程唯一编码从redis中删除
    public void removePortalProcedure(String procode) {
        redisUtil.remove(String.format("%s:report:procedure", KEY_PREFIX), procode);
    }

    // 根据存储过程唯一编码判断是否存在配置
    public boolean hasPortalProcedure(String procode) {
        return redisUtil.hasKey(String.format("%s:report:procedure", KEY_PREFIX), procode);
    }

    /*  *******************报表sql语句配置信息****************      */
    public void setPortalSql(String sql, String value) {
        redisUtil.put(String.format("%s:report:sql", KEY_PREFIX), sql, value);
    }

}
