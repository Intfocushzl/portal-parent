package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.report.PortalRouteReport;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
import com.yonghui.portal.util.redis.ReportUtil;
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
 * 调用外部系统，然后返回数据给客户端（portal_route_report管理）
 * liuwei 2017.06.01
 */
@RestController
@RequestMapping("/app/routeApi")
public class AppRouteApiController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private SysoperationLogService sysoperationLogService;

    @Autowired
    private ReportUtil reportUtil;


    /**
     * APP报表存储过程报表路由外部系统统一入口
     */
    @IgnoreAuth
    @RequestMapping(value = "report", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode,
                          String sign) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String result = null;
        JSONObject jsonObject = null;
        try {
            //根据code从redis查报表信息
            PortalRouteReport report = reportUtil.getPortalRouteReport(yongHuiReportCustomCode);
            //首先判断客户端秘钥是否正确
            Md5Util util = new Md5Util();
            String originSign = util.getMd5("MD5", 0, null, yongHuiReportCustomCode + report.getKey());
            if (!originSign.equals(sign)) {
                return R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "sign验证失败");
            }
            parameter = HttpContextUtils.getParameterForLog(req);
            SysOperationLog log = new SysOperationLog();
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setStartTime(new Date());
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(parameter);
            //调用外部接口获取数据
            result = reportUtil.routeResultByParam(yongHuiReportCustomCode, parameter, report.getUrl(), report.getKey());
            log.setEndTime(new Date());
            log.setRemark("route");
            sysoperationLogService.SaveLog(log);
            if (!StringUtils.isEmpty(result)) {
                jsonObject = JSONObject.parseObject(result);
            } else {
                return R.success();
            }
        } catch (Exception e) {
            R.error("执行App统一报表路由程序异常");
        }
        return R.success(jsonObject.get("data"));
    }
}
