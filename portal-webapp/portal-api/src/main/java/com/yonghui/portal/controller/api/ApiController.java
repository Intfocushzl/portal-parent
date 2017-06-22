package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
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
        SysOperationLog log = (SysOperationLog) req.getAttribute(LOGIN_USER_OPERATION_LOG);
        try {
            parameter = HttpContextUtils.getRequestParameter(req);
            //获取用户ip,url.参数
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(req));
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
        } catch (Exception e) {
            return R.error(e.getMessage());
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
            if (!StringUtils.isEmpty(yongHuiReportCustomCode)) {
                List<Map<String, Object>> dataList = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(yongHuiReportCustomCode), SQLFilter.sqlInject(parameter));
                ApiExportExport export = new ApiExportExport();
                JSONObject jsonObject = reportUtil.getReportColumns(yongHuiReportCustomCode);
                HSSFWorkbook workbook = export.export(dataList, jsonObject);
                String filename = "YH-DATA.xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                workbook.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            R.error("导出excel异常");
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
