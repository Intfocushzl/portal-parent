package com.yonghui.portal.controller.api;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonghui.portal.annotation.OpenAuth;
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
import java.util.*;

/**
 * APP扫码接口
 * 单独的业务逻辑 只针对app扫码
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

    public static final String URL = "http://10.0.12.15:32128/api/v1/businessman/search/scene2";

    /**
     * 查我们自己的dws库  非实时数据  门店实时销售、实时库存、销售相关的数据
     * 调用外部接口  实时数据
     *
     * @return
     */
    @OpenAuth
    @RequestMapping(value = "qrCode", method = RequestMethod.GET)
    @ResponseBody
    public R portalCustom(HttpServletRequest req, HttpServletResponse response, String shopID, String barcode) {
        String parameter = null;
        String result = null;
        JSONObject jsonObject = null;
        JSONArray jsonArrayResult =null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> hanaList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> shopIdList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<Object> dateList = new ArrayList<Object>();
        try {
            //调用自己库查数据
            parameter = HttpContextUtils.getRequestParameter(req);
            SysOperationLog log = new SysOperationLog();
            log.setIp(new IPUtils().getIpAddr(req));
            log.setStartTime(new Date());
            log.setUrl(req.getRequestURL().toString());
            log.setParameter(parameter);
            //首先根据parameter里面的shopId去查  该门店所在区域下下的所有门店
            shopIdList = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject("REP_000045"), SQLFilter.sqlInject(parameter));
            //查hana数据库  查询商品信息
            hanaList = reportUtil.jdbcProListResultListMapByParam("REP_000099", SQLFilter.sqlInject(parameter));
            String saleDate = "";
            String saleAmount = "";
            String goodsName = "";
            for (Map<String, Object> item : hanaList) {
                saleDate = saleDate + item.get("SALEDATE").toString() + ",";
                saleAmount = saleAmount + item.get("AMOUNT").toString() + ",";
                goodsName = item.get("GOODSNAME").toString();
            }
            if (saleDate != null && !saleDate.equals("")) {
                saleDate.substring(0, saleDate.length() - 1);
            }
            if (saleAmount != null && !saleAmount.equals("")) {
                saleAmount.substring(0, saleAmount.length() - 1);
            }
            map = new HashMap<String, Object>();
            map.put("saleDate", saleDate);
            map.put("saleAmount", saleAmount);
            map.put("goodsName",goodsName);
            //将查出来的门店结果，拼接成参数
            StringBuffer newParam = new StringBuffer();
            for (Map<String, Object> item : shopIdList) {
                newParam.append("'" + item.get("shopId") + "',");
            }
            String newParamStr = "shopID=" + newParam.toString().substring(1, newParam.toString().length() - 2) + "@@barcode=" + barcode;
            //查非实时数据（我们自己的库），注意此处code  admin配置的是98  为了给app扫码统一code（REP_000043）
            list = reportUtil.jdbcProListResultListMapByParam(SQLFilter.sqlInject("REP_000043"), newParamStr);
            //调用外部接口获取数据
            result = reportUtil.qRResultByParam(SQLFilter.sqlInject(newParamStr), URL);
            if (!StringUtils.isEmpty(result)) {
                jsonObject = JSONObject.parseObject(result);
            } else {
                return R.error();
            }
            Map<String, Object> resMap = new HashMap<String, Object>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            //第一个hana销售量；第二个实时数据；第三部分非实时数据
            dateList.add(0, map);
            dateList.add(1, jsonArray);
            dateList.add(2, list);
            QrCodeUtil util = new QrCodeUtil();
            if(dateList != null && dateList.size()>0){
                jsonArrayResult = util.createJsonTemplate(dateList , shopID);
            }else{
                return R.success(jsonArrayResult);
            }
            log.setEndTime(new Date());
            log.setRemark("app@@qrCode");
            sysoperationLogService.SaveLog(log);
        } catch (Exception e) {
          return  R.error("执行APP报表存储过程报表异常");
        }
        return R.success(jsonArrayResult);
    }
}
