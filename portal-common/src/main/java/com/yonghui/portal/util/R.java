package com.yonghui.portal.util;

import java.util.HashMap;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 请求结果信息
     *
     * @param code   响应码
     * @param data   响应数据
     * @param msg 相信信息说明
     */
    private R(int code, Object data, String msg) {
        setCode(code);
        setMsg(msg);
        setData(data);
    }

    /**
     * 请求失败
     *
     * @return
     */
    public static R error() {
        return newInstance(ConstantsUtil.CommonCode.ERROR_CODE, "", ConstantsUtil.CommonMessage.ERROR_MESSAGE);
    }

    /**
     * 请求失败
     *
     * @param msg 自定义异常信息
     * @return
     */
    public static R error(String msg) {
        return newInstance(ConstantsUtil.CommonCode.ERROR_CODE, "", msg);
    }

    /**
     * 请求失败
     *
     * @param code   自定义异常编码
     * @param msg 自定义异常信息
     * @return
     */
    public static R error(int code, String msg) {
        return newInstance(code, "", msg);
    }

    /**
     * 请求成功
     *
     * @param data 响应数据
     * @return
     */
    public static R success(Object data) {
        return newInstance(ConstantsUtil.CommonCode.SUCCESS_CODE, data, ConstantsUtil.CommonMessage.SUCCESS_MESSAGE);
    }

    /**
     * 请求成功
     *
     * @return
     */
    public static R success() {
        return newInstance(ConstantsUtil.CommonCode.SUCCESS_CODE, "", ConstantsUtil.CommonMessage.SUCCESS_MESSAGE);
    }

    /**
     * 自定义结果信息
     *
     * @param key   属性名称
     * @param value 结果数据
     * @return
     */
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 设置响应结果状态码
     *
     * @param code 请求结果状态码
     * @return
     */
    public R setCode(int code) {
        put("code", code);
        return this;
    }

    /**
     * 设置响应数据
     *
     * @param data 结果数据
     * @return
     */
    public R setData(Object data) {
        put("data", data);
        return this;
    }

    /**
     * 设置响应结果信息
     *
     * @param msg 请求结果信息说明
     * @return
     */
    public R setMsg(String msg) {
        if (null == msg) {
            put("msg", "");
        } else {
            put("msg", msg);
        }
        return this;
    }

    public static R newInstance(int code, Object data, String msg) {
        return new R(code, data, msg);
    }

}
