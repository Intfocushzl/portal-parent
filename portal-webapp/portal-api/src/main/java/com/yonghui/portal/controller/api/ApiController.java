package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.report.PortalDataSource;
import com.yonghui.portal.model.report.PortalExecuteSql;
import com.yonghui.portal.model.report.PortalProcedure;
import com.yonghui.portal.model.report.PortalReport;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.ApiService;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.xss.SQLFilter;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yonghui.portal.interceptor.AuthorizationInterceptor.LOGIN_USER_OPERATION_LOG;

/**
 * 报表存错过程报表统一入口
 * 张海 2017.05.11
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private ApiService apiService;
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;

    /**
     * 报表存储过程报表统一入口
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode 报表编码,字段名唯一，且不允许修改
     * @return
     */
    @RequestMapping(value = "portal/custom")
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            parameter = HttpContextUtils.getRequestParameter(req);
            SysOperationLog log = (SysOperationLog) req.getAttribute(LOGIN_USER_OPERATION_LOG);
            log.setEndTime(new Date());
            //获取用户ip,url.参数
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getRequestParameter(req));
            sysoperationLogService.SaveLog(log);
            list = jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
        } catch (Exception e) {
            log.error("执行统一报表程序异常", e);
            R.error();
        }
        return R.success(list);
    }

    /**
     * 导出excel统一入口
     * 测试中,待完善
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode
     */
    @IgnoreAuth
    @RequestMapping(value = "exportExcel")
    public void exportExcel(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        try {
            String parameter = HttpContextUtils.getRequestParameter(req);
            if (!StringUtils.isEmpty(yongHuiReportCustomCode)) {List<Map<String, Object>> dataList = jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
                ApiExportExport export = new ApiExportExport();
                //测试
                String[] cellTitleName = {"sdate:日期", "flag:列名1", "abc:列名2", "title:说明", "sname:店名"};
                HSSFWorkbook workbook = export.export(dataList, cellTitleName, "YongHui-数据");
                String filename = "YH-DATA.xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            log.error("导出excel异常", e);
        }
    }

    /**
     * 获取报表数
     *
     * @param yongHuiReportCustomCode
     * @param parameter
     * @return
     */
    private List<Map<String, Object>> jdbcProListResultListMapByParam(String yongHuiReportCustomCode, String parameter) {
        // 根据报表唯一编码查询报表基本信息
        PortalReport report = getPortalReport(yongHuiReportCustomCode);
        List<Map<String, Object>> dataList = null;
        PortalDataSource portalDataSource = null;
        if (report.getExecuteType() == 1) {
            // 储存过程方式
            PortalProcedure portalPro = getPortalProcedure(report.getExecuteCode());
            portalDataSource = getPortalDataSource(portalPro.getDataSourceCode());
            dataList = apiService.getListResultListMapByPro(report, portalPro, portalDataSource, parameter);
        } else if (report.getExecuteType() == 2) {
            // sql语句方式
            PortalExecuteSql portalExecuteSql = getPortalExecuteSql(report.getExecuteCode());
            portalDataSource = getPortalDataSource(portalExecuteSql.getDataSourceCode());
            dataList = apiService.getListResultListMapBySql(report, portalExecuteSql, portalDataSource, parameter);
        }
        return dataList;
    }

    /**
     * 获取业务报表信息
     *
     * @param yongHuiReportCustomCode
     * @return
     */
    private PortalReport getPortalReport(String yongHuiReportCustomCode) {
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
}
