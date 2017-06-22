package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.report.PortalOpenapiReport;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.HttpContextUtils;
import com.yonghui.portal.util.IPUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.util.redis.ReportUtil;
import com.yonghui.portal.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 提供统一报表数据接口给客户端（走的是统一接口，只是增加了sign验证）
 * APP报表存错过程报表统一入口
 * liuwei 2017.05.25
 */
@RestController
@RequestMapping("/app/api")
public class AppApiController {

    Logger log = Logger.getLogger(this.getClass());
    @Reference
    private SysoperationLogService sysoperationLogService;
    @Autowired
    private ReportUtil reportUtil;
    @Autowired
    private RedisBizUtilApi redisBizUtilApi;



    /**
     * APP报表存储过程报表统一入口
     *
     * @param req
     * @param response
     * @param openApiCode 报表编码,字段名唯一，且不允许修改
     * @param sign                    报表和token生成sign
     * @return
     */
    @OpenAuth
    @RequestMapping(value = "report", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String openApiCode,
                          String sign, String shopID , String barcode) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            parameter = HttpContextUtils.getRequestParameter(req);
            //记录日志
            SysOperationLog log = new SysOperationLog();
            log.setStartTime(new Date());
            log.setIp(new IPUtils().getIpAddr(req));
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(req));
            log.setRemark("App");
            String openApiJsonStr = redisBizUtilApi.getPortalOpenApiReport(openApiCode);
            PortalOpenapiReport portalOpenapiReport = null;
            String reportCode = "";
            if (StringUtils.isBlank(openApiJsonStr)) {
                reportCode = openApiCode;
            } else {
                portalOpenapiReport = JSONObject.parseObject(openApiJsonStr, PortalOpenapiReport.class);
                if(portalOpenapiReport == null || portalOpenapiReport.getReportcode() == null){
                    reportCode = openApiCode;
                }else{
                    reportCode = portalOpenapiReport.getReportcode();
                }
            }
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(reportCode), SQLFilter.sqlInject(parameter));
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
        } catch (Exception e) {
          return  R.error("执行APP报表存储过程报表异常");
        }
        return R.success(list);
    }
}
