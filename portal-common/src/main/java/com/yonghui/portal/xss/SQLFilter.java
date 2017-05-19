package com.yonghui.portal.xss;

import com.yonghui.portal.util.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * SQL过滤
 *
 * @date 2017-04-01 16:16
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        String strLower = str.toLowerCase();

        //过滤允许字符 更新时间，创建时间，创建者
        String[] keywordsNotFilter = {"update_time", "create_time", "creater"};
        //判断是否允许字符
        for (String keyword : keywordsNotFilter) {
            if (strLower.equals(keyword)) {
                return str;
            }
        }

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "create", "drop"};
        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (strLower.indexOf(keyword) != -1) {
                throw new RRException("包含非法字符");
            }
        }

        return str;
    }
}
