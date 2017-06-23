package com.yonghui.portal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> map = (Map<String,Object>)list.get(0);
        // 第二个实时数据；
        JSONArray jsonArray = (JSONArray)list.get(1);
        // 第三部分非实时数据
        List<Map<String, Object>> datalist = (List<Map<String, Object>>)list.get(2);
        String saleDate = map.get("saleDate").toString();
        String[] saleDate1 = new String[50];
        if(saleDate!=null && saleDate!=""){
             saleDate1 = saleDate.split(",");
        }
        String[] saleAmount1 = new String[50];
        String saleAmount = map.get("saleAmount").toString();
        if(saleAmount!=null && saleAmount!=""){
            saleAmount1 = saleAmount.split(",");
        }

        String goodsName = map.get("goodsName").toString();
        //实时数据
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            if(job.get("shopId").equals(shopId)){
                shopName = formatObj(job.get("shopName")).toString();
            }
            String[] pastStr = {formatObj(job.get("shopName")).toString(), "", formatObj(job.get("shopName")).toString(), formatObj(job.get("totalAmount")).toString(),
                    formatObj(job.get("totalSalesValue")).toString(), formatObj(job.get("totalStock")).toString(),
                    formatObj(job.get("totalInventoryValue")).toString()};
            dataPastList.add(pastStr);
        }
        //非实时数据
        for (int i = 0; i < datalist.size(); i++) {
            String[] currentStr = {formatObj(datalist.get(i).get("shopName")).toString(), "", formatObj(datalist.get(i).get("shopName")).toString(), formatObj(datalist.get(i).get("normalPrice")).toString(),
                    formatObj(datalist.get(i).get("taxCost")).toString(), formatObj(datalist.get(i).get("DMS")).toString(),
                    formatObj(datalist.get(i).get("avgQty")).toString(), formatObj(datalist.get(i).get("monthAmount")).toString(),
                    formatObj(datalist.get(i).get("lastYearMonthAmount")).toString(), formatObj(datalist.get(i).get("lastYearMonthAmount")).toString()};
            dataCurrentList.add(currentStr);
        }

        JSONArray jsonAry1 = new JSONArray();
        JSONObject node = new JSONObject();
        node.put("title", shopName);
        JSONArray jsonAry2 = new JSONArray();
        //=======================================================
        JSONObject node1 = new JSONObject();
        node1.put("type", "banner");
        JSONArray jsonAry3 = new JSONArray();
        JSONObject node2 = new JSONObject();
        node2.put("title", "");
        node2.put("subtitle", "");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String date = format.format(new Date().getTime());
        node2.put("date", date);
        node2.put("info",
                "（1）趋势图，展示前14天的销售额的趋势\\u003cbr/\\u003e（2）时间点，为了告知实时销售的数据的截止时间\\u003cbr/\\u003e\\u003cbr\\u003e\\u003csmall\\u003e数据更新时间:" + format1.format(new Date().getTime()) + "+0800\u003c/small\u003e");
        jsonAry3.add(node2);

        node1.put("config", jsonAry3);
        //=======================================================
        JSONObject node4 = new JSONObject();
        node4.put("type", "chart");
        JSONArray jsonAry4 = new JSONArray();
        JSONObject node3 = new JSONObject();
        node3.put("title", "no-set");
        node3.put("chart_type", "line-or-bar");
        node3.put("legend", goodsName + "前14天销售额趋势");
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
        node12.put("name", goodsName + "前14天销售额趋势");
        node12.put("type", "line");
        node12.put("data", saleAmount1);
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
        JSONArray jsonAry6 = new JSONArray();
        jsonAry6.add(node5);
        JSONObject node7 = new JSONObject();
        node7.put("table", jsonAry6);
        node7.put("title", "实时");

        JSONObject node8 = new JSONObject();
        String[] aa1 = {"自身ID", "父ID", "门店", "正常售价", "含税成本价", "DMS", "前14天日均销售", "本月销售额", "同比销售额", "环比销售额"};
        node8.put("head", aa1);
        node8.put("data", dataCurrentList);
        JSONArray jsonAry8 = new JSONArray();
        jsonAry8.add(node8);
        JSONObject node9 = new JSONObject();
        node9.put("table", jsonAry8);
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
}
