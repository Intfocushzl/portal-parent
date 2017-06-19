package com.yonghui.portal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwei on 2017/6/16.
 * 用于拼接手机扫一扫json字符串（临时使用），未来走统一接口配置生成
 */
public class QrCodeUtil {
    public JSONArray createJsonTemplate(List<Map<String, Object>> list) {
        Map<String, Object> map = list.get(0);
        for (Map<String, Object> item : list) {

        }


        JSONArray jsonAry1 = new JSONArray();
        JSONObject node = new JSONObject();
        node.put("title", "五四店");
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
                "1）趋势图，展示前14天的销售额的趋势\u003cbr/\u003e（2）时间点，为了告知实时销售的数据的截止时间\u003cbr/\u003e\u003cbr\u003e\u003csmall\u003e数据更新时间:" + format1.format(new Date().getTime()) + "+0800\u003c/small\u003e");
        jsonAry3.add(node2);

        node1.put("config", jsonAry3);
        //=======================================================
        JSONObject node4 = new JSONObject();
        node4.put("type", "chart");
        JSONArray jsonAry4 = new JSONArray();
        JSONObject node3 = new JSONObject();
        node3.put("title", "no-set");
        node3.put("chart_type", "line-or-bar");
        node3.put("legend", "hana取出来的商品名称+前14天销售额趋势");
        node3.put("xAxis", "hana取出来的sdate拼成json对象");
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
        node12.put("name", "hana取出来的商品名称+前14天销售额趋势");
        node12.put("type", "line");
        node12.put("data", "hana销售额数据并转成json对象");
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
        node5.put("data", "待定");
        JSONArray jsonAry6 = new JSONArray();
        jsonAry6.add(node5);
        JSONObject node7 = new JSONObject();
        node7.put("table", jsonAry6);
        node7.put("title", "实时");

        JSONObject node8 = new JSONObject();
        String[] aa1 = {"自身ID", "父ID", "门店", "正常售价", "含税成本价", "DMS", "前14天日均销售", "本月销售额", "同比销售额", "环比销售额"};
        node8.put("head", aa1);
        node8.put("data", "待定");
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
}
