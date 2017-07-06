package com.yonghui.portal.controller.sales;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yonghui.portal.annotation.IgnoreAuth;
import com.yonghui.portal.service.sales.SalesAnalysisService;
import com.yonghui.portal.util.R;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨杨 销售概况
 */

@RestController
@RequestMapping("/sales/salesAnalysis")
public class SalesAnalysisController {

    Logger log = Logger.getLogger(this.getClass());
    /*
     * sdate varchar(10),#查询日期 areamans varchar(10),#新大区 shopId
     * varchar(255),#门店ID groupname varchar(255),#商行名称 classname
     * varchar(255),#课组名称 sdate2 varchar(10),#下载的起始日期 edate2 varchar(10)#下载的结束日期
     */

    @Reference
    private SalesAnalysisService salesAnalysisService;

    /**
     * 1.
     *
     * @author 杨杨 ;
     * @description:命名：shop、store 门店 business 小店、商行 district 大区
     */
    @ResponseBody
    @RequestMapping(value = "salesDailyData")
    @IgnoreAuth
    public R salesDailyData(HttpServletResponse response, String reportLabel, String sDate, String areaMans, String shopId
            , String groupName, String className, String sDate1, String eDate1) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (reportLabel == null || reportLabel.equals("") || reportLabel.equals("null")) {
            reportLabel = "1";
        }
        if (areaMans == null || areaMans.equals("") || areaMans.equals("null")) {
            areaMans = "上海区";
        }
        if (shopId == null || shopId.equals("") || shopId.equals("null")) {
            shopId = "9342";
        }
        if (groupName == null || groupName.equals("") || groupName.equals("null")) {
            groupName = "清洁用品";
        }
        if (className == null || className.equals("") || className.equals("null")) {
            className = "清洁用品课";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("reportLabel", reportLabel);
        map.put("sDate", sDate);
        map.put("areaMans", areaMans);
        map.put("shopId", shopId);
        map.put("groupName", groupName);
        map.put("className", className);
        map.put("sDate1", sDate1);
        map.put("eDate1", eDate1);
        System.err.println("参数" + map);
        List<Map<String, Object>> sales;
        try {
            sales = salesAnalysisService.salesAnalysisDailyData(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("请求数据异常");
        }
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> amountResult = new HashMap<>();
        Map<String, Object> profitResult = new HashMap<>();
        Map<String, Object> customerSheetResult = new HashMap<>();
        Map<String, Object> customerPriceResult = new HashMap<>();

        List<Double> nowMonthDatas0 = new ArrayList<>();
        List<Double> nowMonthDatas1 = new ArrayList<>();
        List<Double> nowMonthDatas2 = new ArrayList<>();
        List<Double> nowMonthDatas3 = new ArrayList<>();

        List<Double> lastYearDatas0 = new ArrayList<>();
        List<Double> lastYearDatas1 = new ArrayList<>();
        List<Double> lastYearDatas2 = new ArrayList<>();
        List<Double> lastYearDatas3 = new ArrayList<>();

        List<Double> lastMonthDatas0 = new ArrayList<>();
        List<Double> lastMonthDatas1 = new ArrayList<>();
        List<Double> lastMonthDatas2 = new ArrayList<>();
        List<Double> lastMonthDatas3 = new ArrayList<>();

        System.err.println("趋势-结果" + sales.toString());

        for (int i = 0; i < sales.size(); i++) {
            Map<String, Object> vo = sales.get(i);
            if (Integer.parseInt(vo.get("type").toString()) == 0) {
                nowMonthDatas0.add(Double.parseDouble((vo.get("amount").toString())));
                nowMonthDatas1.add(Double.parseDouble((vo.get("profit").toString())));
                nowMonthDatas2.add(Double.parseDouble((vo.get("CustomerSheet").toString())));
                nowMonthDatas3.add(Double.parseDouble((vo.get("CustomerPrice").toString())));
            } else if (Integer.parseInt(vo.get("type").toString()) == 1) {
                lastYearDatas0.add(Double.parseDouble((vo.get("amount").toString())));
                lastYearDatas1.add(Double.parseDouble((vo.get("profit").toString())));
                lastYearDatas2.add(Double.parseDouble((vo.get("CustomerSheet").toString())));
                lastYearDatas3.add(Double.parseDouble((vo.get("CustomerPrice").toString())));
            } else {
                lastMonthDatas0.add(Double.parseDouble((vo.get("amount").toString())));
                lastMonthDatas1.add(Double.parseDouble((vo.get("profit").toString())));
                lastMonthDatas2.add(Double.parseDouble((vo.get("CustomerSheet").toString())));
                lastMonthDatas3.add(Double.parseDouble((vo.get("CustomerPrice").toString())));
            }
        }
        amountResult.put("title", "销售趋势对比");
        amountResult.put("nowMonth", nowMonthDatas0);
        amountResult.put("lastYear", lastYearDatas0);
        amountResult.put("lastMonth", lastMonthDatas0);

        profitResult.put("title", "毛利趋势对比");
        profitResult.put("nowMonth", nowMonthDatas1);
        profitResult.put("lastYear", lastYearDatas1);
        profitResult.put("lastMonth", lastMonthDatas1);

        customerSheetResult.put("title", "客流趋势对比");
        customerSheetResult.put("nowMonth", nowMonthDatas2);
        customerSheetResult.put("lastYear", lastYearDatas2);
        customerSheetResult.put("lastMonth", lastMonthDatas2);

        customerPriceResult.put("title", "客单趋势对比");
        customerPriceResult.put("nowMonth", nowMonthDatas3);
        customerPriceResult.put("lastYear", lastYearDatas3);
        customerPriceResult.put("lastMonth", lastMonthDatas3);

        result.put("amount", amountResult);
        result.put("profit", profitResult);
        result.put("customerSheet", customerSheetResult);
        result.put("customerPrice", customerPriceResult);

        if (sales != null && sales.size() > 0) {
            String month = sales.get(0).get("lkpdate").toString().substring(0, 6);
            String[] lineDownDate = new String[31];
            for (int i = 0; i < 31; i++) {
                if (i < 9) {
                    lineDownDate[i] = month + "0" + (i + 1);
                } else {
                    lineDownDate[i] = month + (i + 1);
                }
            }
            result.put("xLine", lineDownDate);
        } else {
            result.put("xLine", new String[0]);
        }

        log.info(R.success(result));
        return R.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "salesMonthlyData")
    @IgnoreAuth
    public R salesMonthlyData(HttpServletResponse response, String reportLabel, String sDate, String areamans, String shopId
            , String groupName, String className, String sDate1, String eDate1) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (reportLabel == null || reportLabel.equals("") || reportLabel.equals("null")) {
            reportLabel = "1";
        }
        if (areamans == null || areamans.equals("") || areamans.equals("null")) {
            areamans = "上海区";
        }
        if (shopId == null || shopId.equals("") || shopId.equals("null")) {
            shopId = "9342";
        }
        if (groupName == null || groupName.equals("") || groupName.equals("null")) {
            groupName = "清洁用品";
        }
        if (className == null || className.equals("") || className.equals("null")) {
            className = "清洁用品课";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("reportLabel", reportLabel);
        map.put("sDate", sDate);
        map.put("areaMans", areamans);
        map.put("shopId", shopId);
        map.put("groupName", groupName);
        map.put("className", className);
        map.put("sDate1", sDate1);
        map.put("eDate1", eDate1);
        System.err.println("参数" + map);
        List<Map<String, Object>> sales;
        try {
            sales = salesAnalysisService.salesAnalysisMonthlyData(map);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("请求数据异常");
        }
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> amountResult = new HashMap<>();
        Map<String, Object> profitResult = new HashMap<>();
        Map<String, Object> customerSheetResult = new HashMap<>();
        Map<String, Object> customerPriceResult = new HashMap<>();

        List<Double> datas0 = new ArrayList<>();
        List<Double> datas1 = new ArrayList<>();
        List<Double> datas2 = new ArrayList<>();
        List<Double> datas3 = new ArrayList<>();

        System.err.println("趋势-结果" + sales.toString());

        for (int i = 0; i < sales.size(); i++) {
            Map<String, Object> vo = sales.get(i);
            datas0.add(Double.parseDouble((vo.get("amount").toString())));
            datas1.add(Double.parseDouble((vo.get("profit").toString())));
            datas2.add(Double.parseDouble((vo.get("CustomerSheet").toString())));
            datas3.add(Double.parseDouble((vo.get("CustomerPrice").toString())));
        }
        amountResult.put("title", "销售趋势对比");
        amountResult.put("nowMonth", datas0);

        profitResult.put("title", "毛利趋势对比");
        profitResult.put("nowMonth", datas1);

        customerSheetResult.put("title", "客流趋势对比");
        customerSheetResult.put("nowMonth", datas2);

        customerPriceResult.put("title", "客单趋势对比");
        customerPriceResult.put("nowMonth", datas3);

        result.put("amount", amountResult);
        result.put("profit", profitResult);
        result.put("customerSheet", customerSheetResult);
        result.put("customerPrice", customerPriceResult);

        log.info(R.success(result));
        return R.success(result);
    }
}
