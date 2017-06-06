package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.model.sys.SysOperationLog;
import com.yonghui.portal.service.sys.SysoperationLogService;
import com.yonghui.portal.util.*;
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
 * APP扫码接口
 * liuwei 2017.06.01
 */
@RestController
@RequestMapping("/app/api")
public class AppQrCodeApiController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private SysoperationLogService sysoperationLogService;

    @Autowired
    private ReportUtil reportUtil;

    public static final String TOKEN = "yhappQKXYfkjqn8Yq6ojACkwXRnt35322896dfd9419f9d2c4080b064d89a";

    public static final String URL = "http://10.0.12.15:32128/api/v1/businessman/search/scene2";

    /**
     * 查我们自己的dws库  非实时数据  门店实时销售、实时库存、销售相关的数据
     *调用外部接口  实时数据
     * @return
     */
    @IgnoreAuth
    @RequestMapping(value = "qrCode", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String yongHuiReportCustomCode,
                          String sign, String shopID, String barcode) {
        String parameter = null;
        String result = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> shopIdList = new ArrayList<Map<String, Object>>();
        try {
            //首先判断客户端秘钥是否正确
            Md5Util util = new Md5Util();
            String originSign = util.getMd5("MD5", 0, null, yongHuiReportCustomCode + TOKEN);
            if (!originSign.equals(sign)) {
                return R.error(ConstantsUtil.ExceptionCode.TO_LOGIN, "sign验证失败");
            }
            //调用自己库查数据
            parameter = HttpContextUtils.getRequestParameter(req);
            SysOperationLog log = new SysOperationLog();
            log.setIp(new IPUtils().getIpAddr(req));
            log.setStartTime(new Date());
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(parameter);
            //首先根据parameter里面的shopId去查  该门店所在区域下下的所有门店
            shopIdList = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject("REP_000045"), SQLFilter.sqlInject(parameter));
            //将查出来的门店结果，拼接成参数
            StringBuffer newParam = new StringBuffer();
            for(Map<String, Object> item : shopIdList){
                newParam.append("'"+item.get("shopId")+"',");
            }
            String newParamStr = "shopID="+newParam.toString().substring(1,newParam.toString().length()-2)+"@@barcode="+barcode;
            //注意此处code  admin配置的是98  为了给app扫码统一code（REP_000043）
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject("REP_000043"),newParamStr);
            //调用外部接口获取数据
            result = reportUtil.qRResultByParam(parameter, URL);
            log.setEndTime(new Date());
            log.setRemark("app@@qrCode");
            sysoperationLogService.SaveLog(log);
        } catch (Exception e) {
            R.error("执行APP报表存储过程报表异常");
        }
        return R.success(list).put("data2", JSONObject.parse(result));
    }
}
