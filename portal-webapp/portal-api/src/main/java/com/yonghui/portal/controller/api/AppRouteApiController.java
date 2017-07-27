package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.OpenAuth;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.HttpContextUtils;
import com.yonghui.portal.util.IPUtils;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.RedisBizUtilApi;
import com.yonghui.portal.util.redis.ReportUtil;
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

    @Autowired
    private RedisBizUtilApi redisBizUtilApi;


    /**
     * APP报表存储过程报表路由外部系统统一入口
     */
    @OpenAuth
    @RequestMapping(value = "report", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String provideCode,
                          String sign) {
        String parameter = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String result = null;
        JSONObject jsonObject = null;
        SysOperationLog log = new SysOperationLog();
        try {
            parameter = HttpContextUtils.getParameterForLog(req);
            IPUtils iputil = new IPUtils();
            log.setIp(iputil.getIpAddr(req));
            log.setStartTime(new Date());
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(parameter);
            log.setRemark("第三方接口");

            //调用外部接口获取数据
            result = reportUtil.routeResultByParam(provideCode, parameter);
            if (!StringUtils.isEmpty(result)) {
                jsonObject = JSONObject.parseObject(result);
            } else {
                return R.success("转JSON格式失败").put("result", result);
            }
        } catch (Exception e) {
            log.setStatus(1);
            log.setError(e.toString().substring(0, 2000));
            log.setEndTime(new Date());
            sysoperationLogService.SaveLog(log);
            return R.error("调用第三方接口失败");
        }
        log.setEndTime(new Date());
        sysoperationLogService.SaveLog(log);
        return R.success(jsonObject.get("data"));
    }
}
