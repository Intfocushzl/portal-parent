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
import com.yonghui.portal.util.redis.ReportUtil;
import com.yonghui.portal.xss.SQLFilter;
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
 * 对外部系统提供统一的数据接口（portal_openapi_report管理）
 * liuwei 2017.06.07
 */
@RestController
@RequestMapping("/openApi/portal")
public class OpenApiController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private SysoperationLogService sysoperationLogService;

    @Autowired
    private ReportUtil reportUtil;


    /**
     * 外部系统调用openApi统一入口
     */
    @OpenAuth
    @RequestMapping(value = "report", method = RequestMethod.POST)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String openApiCode) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String result = null;
        JSONObject jsonObject = null;
        try {
            //根据code从redis查报表信息
            PortalOpenapiReport report = reportUtil.getPortalOpenApiReport(openApiCode);
            parameter = HttpContextUtils.getRequestParameter(req);
            SysOperationLog log = new SysOperationLog();
            log.setIp(new IPUtils().getIpAddr(req));
            log.setStartTime(new Date());
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(HttpContextUtils.getParameterForLog(req));
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject(openApiCode), SQLFilter.sqlInject(parameter));
            log.setEndTime(new Date());
            log.setRemark("openApi");
            sysoperationLogService.SaveLog(log);
        } catch (Exception e) {
           return R.error("执行openApi统一报表程序异常");
        }
        return R.success(list);
    }
}
