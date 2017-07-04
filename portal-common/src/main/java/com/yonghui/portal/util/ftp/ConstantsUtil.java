package com.yonghui.portal.util.ftp;


/**
 * 常量定义
 * <p>
 * Created by 张海 on 2017/04/29.
 */

public class ConstantsUtil {

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

}


