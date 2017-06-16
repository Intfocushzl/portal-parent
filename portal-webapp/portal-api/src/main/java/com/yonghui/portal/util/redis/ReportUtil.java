package com.yonghui.portal.util.redis;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.report.*;
import com.yonghui.portal.service.ApiService;
import com.yonghui.portal.util.ConstantsUtil;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.RRException;
import com.yonghui.portal.util.StringUtils;
import com.yonghui.portal.util.report.columns.HttpMethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghai on 2017/5/25.
 */
@Component("reportUtil")
public class ReportUtil {

    @Reference
    private ApiService apiService;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;

    /**
     * 获取报表数据
     *
     * @param yongHuiReportCustomCode
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> jdbcProListResultListMapByParam(String yongHuiReportCustomCode, String parameter) {
        // 根据报表唯一编码查询报表基本信息
        PortalReport report = getPortalReport(yongHuiReportCustomCode);
        List<Map<String, Object>> dataList = null;
        PortalDataSource portalDataSource = null;
        if (report.getExecuteType() == ConstantsUtil.ExecuteType.PROCEDURE) {
            // 储存过程方式
            PortalProcedure portalPro = getPortalProcedure(report.getExecuteCode());
            portalDataSource = getPortalDataSource(portalPro.getDataSourceCode());
            dataList = apiService.getListResultListMapByPro(report, portalPro, portalDataSource, parameter);
        } else if (report.getExecuteType() == ConstantsUtil.ExecuteType.EXECUTESQL) {
            // sql语句方式
            PortalExecuteSql portalExecuteSql = getPortalExecuteSql(report.getExecuteCode());
            portalDataSource = getPortalDataSource(portalExecuteSql.getDataSourceCode());
            dataList = apiService.getListResultListMapBySql(report, portalExecuteSql, portalDataSource, parameter);
        }
        return dataList;
    }

    /**
     * 获取业务报表标题信息
     *
     * @param yongHuiReportCustomCode
     * @return
     */
    public JSONObject getReportColumns(String yongHuiReportCustomCode) {
        if (StringUtils.isEmpty(yongHuiReportCustomCode)) {
            throw new RRException("报表编码不能为空");
        }
        String columnsJson = redisBizUtilApi.getReportColumns(yongHuiReportCustomCode);
        if (StringUtils.isEmpty(columnsJson)) {
            throw new RRException("未找到报表标题定义");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(columnsJson);
            return jsonObject;
        } catch (Exception e) {
            throw new RRException("报表标题JSON转换异常");
        }
    }

    /**
     * 获取业务报表信息
     *
     * @param yongHuiReportCustomCode
     * @return
     */
    public PortalReport getPortalReport(String yongHuiReportCustomCode) {
        if (StringUtils.isEmpty(yongHuiReportCustomCode)) {
            throw new RRException("报表编码不能为空");
        }
        String reportJson = redisBizUtilApi.getPortalReport(yongHuiReportCustomCode);
        if (StringUtils.isEmpty(reportJson)) {
            throw new RRException("报表编码 " + yongHuiReportCustomCode + "无效");
        }
        PortalReport report = JSONObject.parseObject(reportJson, PortalReport.class);
        if (report == null || StringUtils.isEmpty(report.getExecuteCode())) {
            throw new RRException("报表编码:" + yongHuiReportCustomCode + "无效，或未指定ExecuteCode");
        }
        return report;
    }

    /**
     * 获取存储过程信息
     *
     * @return
     */
    private PortalProcedure getPortalProcedure(String executeCode) {
        String executeJson = redisBizUtilApi.getPortalProcedure(executeCode);
        if (StringUtils.isEmpty(executeJson)) {
            throw new RRException("执行的存储过程不存在");
        }
        PortalProcedure portalPro = JSONObject.parseObject(executeJson, PortalProcedure.class);
        if (portalPro == null || StringUtils.isEmpty(portalPro.getDataSourceCode())) {
            throw new RRException("执行的存储过程不存在，或未指定DataSourceCode");
        }
        return portalPro;
    }

    /**
     * 获取执行sqld的信息
     *
     * @return
     */
    private PortalExecuteSql getPortalExecuteSql(String executeCode) {
        String executeJson = redisBizUtilApi.getPortalExecuteSql(executeCode);
        if (StringUtils.isEmpty(executeJson)) {
            throw new RRException("执行的SQL不存在");
        }
        PortalExecuteSql portalExecuteSql = JSONObject.parseObject(executeJson, PortalExecuteSql.class);
        if (portalExecuteSql == null || StringUtils.isEmpty(portalExecuteSql.getDataSourceCode())) {
            throw new RRException("执行的SQL不存在，或未指定DataSourceCode");
        }
        return portalExecuteSql;
    }

    /**
     * 获取数据源
     *
     * @param dataSourceCode
     * @return
     */
    private PortalDataSource getPortalDataSource(String dataSourceCode) {
        if (StringUtils.isEmpty(dataSourceCode)) {
            throw new RRException("执行语句未指定数据源");
        }
        String portalDataSourceJson = redisBizUtilApi.getPortalDataSource(dataSourceCode);
        if (StringUtils.isEmpty(portalDataSourceJson)) {
            throw new RRException("数据源" + dataSourceCode + "不存在");
        }
        PortalDataSource portalDataSource = JSONObject.parseObject(portalDataSourceJson, PortalDataSource.class);
        if (portalDataSource == null) {
            throw new RRException("数据源" + dataSourceCode + "不存在");
        }
        return portalDataSource;
    }

    /**
     * 获取路由业务报表信息
     *
     * @param yongHuiReportCustomCode
     * @return
     */
    public PortalRouteReport getPortalRouteReport(String yongHuiReportCustomCode) {
        if (StringUtils.isEmpty(yongHuiReportCustomCode)) {
            throw new RRException("报表编码不能为空");
        }
        String reportJson = redisBizUtilApi.getPortalRouteReport(yongHuiReportCustomCode);
        if (StringUtils.isEmpty(reportJson)) {
            throw new RRException("报表编码 " + yongHuiReportCustomCode + "无效");
        }
        PortalRouteReport report = JSONObject.parseObject(reportJson, PortalRouteReport.class);
        if (report == null || StringUtils.isEmpty(report.getCode())) {
            throw new RRException("报表编码:" + yongHuiReportCustomCode + "无效，或未指定yongHuiReportCustomCode");
        }
        return report;
    }


    /**
     * 获取路由报表数据
     *
     * @param yongHuiReportCustomCode
     * @param parameter
     * @return
     */
    public String routeResultByParam(String yongHuiReportCustomCode, String parameter, String url, String key) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(yongHuiReportCustomCode) || StringUtils.isEmpty(url)) {
            throw new RRException("报表编码或者请求url不能为空");
        }
        // 根据报表唯一编码查询报表基本信息
        PortalRouteReport report = getPortalRouteReport(yongHuiReportCustomCode);
        String result = null;
        String routeParameter = StringUtils.getRouteParameter(parameter, report.getParameter());
        StringBuffer sb = new StringBuffer();
        String[] arr = routeParameter.split("&");
        for (String p : arr) {
            if (p.split("=").length == 1) {
                map.put(p.split("=")[0], "");
                sb.append("");
            } else if (StringUtils.isEmpty(p.split("=")[1])) {
                map.put(p.split("=")[0], "");
                sb.append("");
            } else if (!StringUtils.isEmpty(p.split("=")[1])) {
                map.put(p.split("=")[0], p.split("=")[1]);
                sb.append(p.split("=")[1]);
            }
        }
        //请求参数加密（key + parameter + key）
        Md5Util util = new Md5Util();
        String sign = util.getMd5("MD5", 0, null, key + sb + key);
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        try {
            result = httpUtil.getGetResult(url, map);
        } catch (Exception e) {
            throw new RRException("调用外部系统出错：" + yongHuiReportCustomCode + "异常信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据url和参数直接调用外部接口（临时提供app扫码接口，接口标准未统一）
     *
     * @param parameter
     * @param url
     * @return
     */
    public String qRResultByParam(String parameter, String url) {
        if (StringUtils.isEmpty(parameter) || StringUtils.isEmpty(url)) {
            throw new RRException("报表参数或者请求url不能为空");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //处理参数
        String routeParameter = StringUtils.getRouteParameter(parameter, parameter);
        String[] arr = routeParameter.split("&");
        for (String p : arr) {
            if (p.split("=").length == 1) {
                map.put(p.split("=")[0], "");
            } else if (StringUtils.isEmpty(p.split("=")[1])) {
                map.put(p.split("=")[0], "");
            } else if (!StringUtils.isEmpty(p.split("=")[1])) {
                map.put(p.split("=")[0], p.split("=")[1]);
            }
        }
            url = url + "?shopId=" + map.get("shopID") + "&barcodeId=" + map.get("barcode");
        HttpMethodUtil httpUtil = new HttpMethodUtil();
        String result = httpUtil.getGetResult(url, new HashMap<String,Object>());
        return result;
    }

    /**
     *获取openApi报表信息
     * @param openApiCode
     * @return
     */
    public PortalOpenapiReport  getPortalOpenApiReport(String openApiCode) {
        if (StringUtils.isEmpty(openApiCode)) {
            throw new RRException("报表编码不能为空");
        }
        String reportJson = redisBizUtilApi.getPortalOpenApiReport(openApiCode);
        if (StringUtils.isEmpty(reportJson)) {
            throw new RRException("报表编码 " + openApiCode + "无效");
        }
        PortalOpenapiReport report = JSONObject.parseObject(reportJson, PortalOpenapiReport.class);
        if (report == null || StringUtils.isEmpty(report.getCode())) {
            throw new RRException("报表编码:" + openApiCode + "无效，或未指定openApiCode");
        }
        return report;
    }

}
