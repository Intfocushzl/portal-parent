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
        public static final String DATA_000004 = "DATA_000004";         //帆软数据源
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
        public static final String INTERNAL_MESSAGE = "内部错误,请联系工程师!!";       //内部错误!
    }

    //自定义错误消息
    public abstract class ExceptionCode {
        public static final int TO_LOGIN = -99;                // 跳转到登录
        public static final int INTERNAL_ERROR = 500;          // 内部错误
        public static final int SIGN_ERROR = -98;              // openApi请求sign校验错误
    }

    //获取数据方式
    public abstract class ExecuteType {
        public static final int PROCEDURE = 1;     //存储过程
        public static final int EXECUTESQL = 2;     //自定义sql
    }

    public static final String APP_BASE_URL = "http://yonghui-test.idata.mobi/api/v2";

    //APP的常量接口
    public abstract class AppBaseUrl {
        //菜单
//        public static final String APP_BASE_GET_REPORT_URL = APP_BASE_URL + "/reports";      //获取生意概况菜单列表
        public static final String APP_BASE_GET_KPI_URL = APP_BASE_URL + "/kpis";      //仪表盘列表
        public static final String APP_BASE_GET_ANALYSE_URL = APP_BASE_URL + "/analyses";      //获取报表菜单列表
        public static final String APP_BASE_GET_APP_URL = APP_BASE_URL + "/apps";      //获取专题菜单列表
//        public static final String APP_BASE_POST_REPORT_URL = APP_BASE_URL + "/report";      //创建生意概况菜单
        public static final String APP_BASE_POST_KPI_URL = APP_BASE_URL + "/kpi";      //仪表盘
        public static final String APP_BASE_POST_ANALYSE_URL = APP_BASE_URL + "/analyse";      //创建报表菜单
        public static final String APP_BASE_POST_APP_URL = APP_BASE_URL + "/app";      //创建专题菜单
        //角色
        public static final String APP_BASE_GET_ROLE_URL = APP_BASE_URL + "/roles";      //获取角色列表
        public static final String APP_BASE_POST_ROLE_URL = APP_BASE_URL + "/role";      //创建角色
        //菜单与角色
        //角色与菜单
        //群组
        public static final String APP_BASE_GET_GROUP_URL = APP_BASE_URL + "/groups";      //获取群组列表
        public static final String APP_BASE_POST_GROUP_URL = APP_BASE_URL + "/group";      //创建群组
        //用户
        public static final String APP_BASE_GET_USER_URL = APP_BASE_URL + "/users";      //获取用户列表
        public static final String APP_BASE_POST_USER_URL = APP_BASE_URL + "/user";      //创建用户
        //用户与角色
    }

}


