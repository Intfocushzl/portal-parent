package com.yonghui.portal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuwei on 2017/6/16.
 * 用于拼接手机扫一扫json字符串（临时使用），未来走统一接口配置生成
 */
public class QrCodeUtil {
    public JSONArray createJsonTemplate(List<Object> list, String shopId) {
        String shopName = "";
        //封装实时数据
        List<String[]> dataPastList = new ArrayList<String[]>();
        //封装非实时数据
        List<String[]> dataCurrentList = new ArrayList<String[]>();
        //第一个hana销售量；
        Map<String, Object> map = (Map<String, Object>) list.get(0);
        // 第二个实时数据；
        JSONArray jsonArray = (JSONArray) list.get(1);
        // 第三部分非实时数据
        List<Map<String, Object>> datalist = (List<Map<String, Object>>) list.get(2);
        String saleDate = map.get("saleDate").toString();
        String[] saleDate1 = new String[50];
        if (saleDate != null && saleDate != "") {
            saleDate1 = saleDate.split(",");
        }
        String[] saleAmount1 = null;
        String saleAmount = map.get("saleAmount").toString();
        if (saleAmount != null && saleAmount != "") {
            saleAmount1 = saleAmount.split(",");
        } else {
            saleAmount1 = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        }

        String goodsName = map.get("goodsName").toString();
        //实时数据
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            if (job.get("shopId").equals(shopId)) {
                shopName = formatObj(job.get("shopName")).toString();
            }
            String[] pastStr = {formatObj(job.get("shopName")).toString(), "", formatObj(job.get("shopName")).toString(), formatObj(job.get("totalAmount")).toString(),
                    formatObj(job.get("totalSalesValue")).toString(), formatObj(job.get("totalStock")).toString(),
                    formatObj(job.get("totalInventoryValue")).toString()};
            dataPastList.add(pastStr);
        }
        //非实时数据
        for (int i = 0; i < datalist.size(); i++) {
            String[] currentStr = {formatObj(datalist.get(i).get("shopName")).toString(), "", formatObj(datalist.get(i).get("shopName")).toString(), formatObj(datalist.get(i).get("nowPrice")).toString(), formatObj(datalist.get(i).get("normalPrice")).toString(),
                    formatObj(datalist.get(i).get("taxCost")).toString(), formatObj(datalist.get(i).get("DMS")).toString(),
                    formatObj(datalist.get(i).get("avgQty")).toString(), formatObj(datalist.get(i).get("monthAmount")).toString(),
                    formatObj(datalist.get(i).get("lastYearMonthAmount")).toString(), formatObj(datalist.get(i).get("lastMonthAmount")).toString()};
            dataCurrentList.add(currentStr);
        }

        JSONArray jsonAry1 = new JSONArray();
        JSONObject node = new JSONObject();
        node.put("title", shopName);
        JSONArray jsonAry2 = new JSONArray();
        //=======================================================
        JSONObject node1 = new JSONObject();
        node1.put("type", "banner");
        JSONObject node2 = new JSONObject();
        node2.put("title", "");
        node2.put("subtitle", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String date = format.format(new Date().getTime());
        node2.put("date", date);
        node2.put("info",
                "说明<br>1.趋势图：展示前14天销售额的趋势<br>2.实时数据每5分钟更新一次（左上角的时间为实时数据刷新截止时间点）<br>3.非实时数据每天更新一次<br>" + format1.format(new Date().getTime()));

        node1.put("config", node2);
        //=======================================================
        JSONObject node4 = new JSONObject();
        node4.put("type", "chart");
        JSONArray jsonAry4 = new JSONArray();
        JSONObject node3 = new JSONObject();
        node3.put("title", "no-set");
        node3.put("chart_type", "line-or-bar");
        String[] legend = {goodsName + "前14天销售额趋势"};
        node3.put("legend", legend);
        node3.put("xAxis", saleDate1);
        //================销售额标题
        JSONArray jsonAry11 = new JSONArray();
        JSONObject node11 = new JSONObject();
        node11.put("type", "value");
        node11.put("name", "销售额");
        jsonAry11.add(node11);
        node3.put("yAxis", jsonAry11);
        //================销售额数据
        JSONArray jsonAry12 = new JSONArray();
        JSONObject node12 = new JSONObject();
        node12.put("name", "销售额");
        node12.put("type", "line");
        if (saleAmount1 == null || saleAmount1.equals("") || saleAmount1.length == 0) {
            node12.put("data", "");
        } else {
            node12.put("data", saleAmount1);
        }
        jsonAry12.add(node12);
        node3.put("series", jsonAry12);
        jsonAry4.add(node3);
        node4.put("config", node3);
        //=======================================================end
        JSONObject node6 = new JSONObject();
        node6.put("type", "tables#v3");
        JSONArray jsonAry5 = new JSONArray();
        JSONObject node5 = new JSONObject();

        String[] aa = {"自身ID", "父ID", "门店", "实时销售", "实时销售额", "实时库存量", "实时库存金额"};
        node5.put("head", aa);
        node5.put("data", dataPastList);
        JSONObject node7 = new JSONObject();
        node7.put("table", node5);
        node7.put("title", "实时");

        JSONObject node8 = new JSONObject();
        String[] aa1 = {"自身ID", "父ID", "门店", "当前售价", "正常售价", "含税成本价", "DMS", "前14天日均销售", "本月销售额", "同比销售额", "环比销售额"};
        node8.put("head", aa1);
        node8.put("data", dataCurrentList);
        JSONObject node9 = new JSONObject();
        node9.put("table", node8);
        node9.put("title", "销售概况");

        jsonAry5.add(node7);
        jsonAry5.add(node9);
        node6.put("config", jsonAry5);
        //=======================================================
        jsonAry2.add(node1);
        jsonAry2.add(node4);
        jsonAry2.add(node6);
        node.put("parts", jsonAry2);
        jsonAry1.add(node);
        System.out.println(jsonAry1);
        return jsonAry1;
    }

    public Object formatObj(Object obj) {
        if (obj == null) {
            obj = "";
        }
        return obj;
    }

    public String getDate() {
        List<String> list = new ArrayList<String>();
        Date dNow = new Date(); // 当前时间
        Date dBefore = new Date();
        for (int i = 1; i <= 14; i++) {
            Calendar calendar = Calendar.getInstance(); // 得到日历
            calendar.setTime(dNow);// 把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -i); // 设置为前一天
            dBefore = calendar.getTime(); // 得到前一天的时间
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd"); // 设置时间格式
            String defaultStartDate = sdf.format(dBefore); // 格式化前一天
            list.add(defaultStartDate);
        }
        String saleDate = new String();
        for (int i = list.size() - 1; i >= 0; i--) {
            saleDate = saleDate + list.get(i) + ",";
        }
        saleDate.substring(0, saleDate.length() - 1);
        return saleDate;
    }
}
