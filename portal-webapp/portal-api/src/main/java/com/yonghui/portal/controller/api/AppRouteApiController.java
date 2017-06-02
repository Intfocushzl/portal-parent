package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.report.PortalRouteReport;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.ConstantsUtil;
import com.yonghui.portal.util.HttpContextUtils;
import com.yonghui.portal.util.Md5Util;
import com.yonghui.portal.util.R;
import com.yonghui.portal.util.redis.ReportUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * APP报表存错过程报表统一入口（路由外部系统）
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

    public static final String TOKEN = "yhappQKXYfkjqn8Yq6ojACkwXRnt35322896dfd9419f9d2c4080b064d89a";


    /**
     * APP报表存储过程报表路由外部系统统一入口
     */
    @IgnoreAuth
    @RequestMapping(value = "report", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode,
                          String sign) {
        String parameter = null;
        String result = null;
        try {
            //首先判断客户端秘钥是否正确
            Md5Util util = new Md5Util();
            String originSign = util.getMd5("MD5", 0, null, yongHuiReportCustomCode + TOKEN);
            if (!originSign.equals(sign)) {
                return R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "sign验证失败");
            }
            parameter = HttpContextUtils.getParameterForLog(req);
            //准备调用外部系统参数
            PortalRouteReport report = reportUtil.getPortalRouteReport(yongHuiReportCustomCode);
            //逻辑处理
           result = reportUtil.routeResultByParam(yongHuiReportCustomCode, parameter,report.getUrl(),report.getKey());
        } catch (Exception e) {
            R.error("执行App统一报表路由程序异常");
        }
        return R.success(result);
    }
}
