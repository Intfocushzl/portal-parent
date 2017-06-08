package com.yonghui.portal.util;


/**
 * 常量定义
 * <p>
 * Created by 张海 on 2017/04/29.
 */

public class ConstantsUtil {

    //数据库连接
    public abstract class DataSourceCode {
        public static final String DATA_000001 = "DATA_000001";         //主mysql连接池
        public static final String DATA_000002 = "DATA_000002";         //jdbc动态数据源
        public static final String DATA_000003 = "DATA_000003";         //主hana连接池
    }

    //通用的状态码
    public abstract class CommonCode {
        public static final int SUCCESS_CODE = 0;      //获取数据成功状态码
        public static final int ERROR_CODE = 1;        //获取数据出错状态码
    }

    //通用的消息
    public abstract class CommonMessage {
        public static final String SUCCESS_MESSAGE = "请求数据成功!";     //获取数据失败
        public static final String ERROR_MESSAGE = "请求数据出错!!";      //获取数据出错!
    }

    //自定义错误消息
    public abstract class ExceptionCode {
        public static final int TO_LOGIN = -99;                // 跳转到登录
        public static final int INTERNAL_ERROR = 500;          // 内部错误
        public static final int SIGN_ERROR = -98;          // openApi请求sign校验错误
    }

    //获取数据方式
    public abstract class ExecuteType {
        public static final int PROCEDURE = 1;     //存储过程
        public static final int EXECUTESQL = 2;     //自定义sql
    }

}


