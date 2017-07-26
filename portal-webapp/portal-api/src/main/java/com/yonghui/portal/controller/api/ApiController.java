package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.model.report.PortalReport;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.redis.ReportUtil;
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
 * 报表表统一入口
 * 张海 2017.05.11
 */
@RestController
@RequestMapping("/api/portal")
public class ApiController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;

    /**
     * 报表存储过程报表统一入口
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode 报表编码,字段名唯一，且不允许修改
     * @return
     */
    @RequestMapping(value = "custom")
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String result = null;
        SysOperationLog log = (SysOperationLog) req.getAttribute(LOGIN_USER_OPERATION_LOG);
        // 1.根据报表唯一编码查询报表基本信息
        PortalReport report = reportUtil.getPortalReport(yongHuiReportCustomCode);
        try {
            // 参数处理
            parameter = HttpContextUtils.getRequestParameter(req);

            // 2.获取用户ip,url.参数
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(req));
            log.setReportcode(yongHuiReportCustomCode);

            // 3.是否从第三方系统数据结果
            if (report.getExecuteType() == ConstantsUtil.ExecuteType.FROMOTHER) {
                log.setRemark("第三方接口");
                // 调用外部接口获取数据
                result = reportUtil.routeResultByParam(report.getExecuteCode(), SQLFilter.sqlInject(parameter));
            } else {
                // 4.从平台数据结果
                list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
            }

        } catch (Exception e) {
            log.setStatus(1);
            log.setError(e.toString().substring(0, 2000));
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
            return R.error("请求数据失败");
        }

        // 5.返回数据集，必须固定格式
        if (report.getExecuteType() == ConstantsUtil.ExecuteType.FROMOTHER) {
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.containsKey("data")) {
                    log.setEndTime(new Date());
                    sysoperationLogService.SaveLog(log);
                    return R.success(jsonObject.get("data"));
                } else {
                    log.setStatus(1);
                    log.setEndTime(new Date());
                    log.setError("第三方返回结果没有data节点");
                    sysoperationLogService.SaveLog(log);
                    return R.error("第三方返回结果没有data节点");
                }
            } else {
                log.setStatus(1);
                log.setEndTime(new Date());
                log.setError("第三方返回结果为空");
                sysoperationLogService.SaveLog(log);
                return R.error("第三方返回结果为空");
            }
        } else {
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
            // 平台数据库结果
            return R.success(list);
        }

    }

    /**
     * 导出excel统一入口
     * 测试中,待完善
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode
     */
    @RequestMapping(value = "exportExcel")
    public void exportExcel(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode, String name) {
        name = StringUtils.isEmpty(name) ? "数据化运营平台" : name;
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        SysOperationLog log = (SysOperationLog) req.getAttribute(LOGIN_USER_OPERATION_LOG);

        try {
            parameter = HttpContextUtils.getRequestParameter(req);
            //获取用户ip,url.参数
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(req));

            // 根据报表唯一编码查询报表基本信息
            PortalReport report = reportUtil.getPortalReport(yongHuiReportCustomCode);
            report.setCellTitleName(GzipUtils.ungzip(report.getCellTitleName()));
            //String[] cellTitleName = {"useFlag1=状态", "hrScopename=门店", "empNo=工号", "empName1=姓名", "remark2=备注"};
            String[] cellTitleName = {};
            if (!StringUtils.isEmpty(report.getCellTitleName())) {
                cellTitleName = report.getCellTitleName().split("\\n");
            }

            // 数内容
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));

            ApiExportExport export = new ApiExportExport();
            HSSFWorkbook workbook = export.export(list, cellTitleName, name);

            String filename = java.net.URLEncoder.encode(name + ".xls", "UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);

            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();

        } catch (Exception e) {
            log.setStatus(1);
            log.setError(e.toString().substring(0, 2000));
        } finally {
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
        }
    }

    /**
     * 获取报表标题信息
     *
     * @param req
     * @param response
     * @param yongHuiReportCustomCode
     */
    @RequestMapping(value = "headers")
    public R headers(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode) {
        PortalReport report = reportUtil.getPortalReport(yongHuiReportCustomCode);

        report.setReportHotData(GzipUtils.ungzip(report.getReportHotData()));
        report.setReportHeadersFormatConsole(GzipUtils.ungzip(report.getReportHeadersFormatConsole()));
        report.setReportOuterHtml(GzipUtils.ungzip(report.getReportOuterHtml()));

        JSONObject jsonHeadersFormatConsol = JSONObject.parseObject(report.getReportHeadersFormatConsole());
        return R.success().put("countRows", report.getReportHeadersCountRows()).put("countCols", report.getReportHeadersCountCols()).put("yongHuiReportCustomCode", report.getCode()).put("headers", report.getReportHeadersConsole()).put("headersFormat", jsonHeadersFormatConsol).put("outerHtml", report.getReportOuterHtml());
    }

}
