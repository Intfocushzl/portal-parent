package com.yonghui.portal.service.impl.sales;

import com.yonghui.portal.mapper.sales.SalesAnalysisMapper;
import com.yonghui.portal.service.sales.SalesAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Author : 杨杨
 * Date : 2017/05/25
 * Description :
 */
public class SalesAnalysisServiceImpl implements SalesAnalysisService {

    @Autowired
    private SalesAnalysisMapper salesAnalysisMapper;

    @Override
    public List<Map<String, Object>> salesAnalysisDailyData(Map<String, Object> map) {
        String reportLabel=map.get("reportLabel").toString();
         if (reportLabel.equals("2")){
            map.put("reportLabel",6);
            return  salesAnalysisMapper.shopSalesDailyData(map);
        } if (reportLabel.equals("3")){
            map.put("reportLabel",8);
            return  salesAnalysisMapper.shopSalesDailyData(map);
        } if (reportLabel.equals("4")){
            map.put("reportLabel",2);
            return  salesAnalysisMapper.areaSalesDailyData(map);
        } if (reportLabel.equals("5")){
            map.put("reportLabel",5);
            return  salesAnalysisMapper.areaSalesDailyData(map);
        } if (reportLabel.equals("6")){
            map.put("reportLabel",8);
            return  salesAnalysisMapper.areaSalesDailyData(map);
        } if (reportLabel.equals("7")){
            map.put("reportLabel",10);
            return  salesAnalysisMapper.areaSalesDailyData(map);
        }
        return  salesAnalysisMapper.shopSalesDailyData(map);
    }

    @Override
    public List<Map<String, Object>> salesAnalysisMonthlyData(Map<String, Object> map) {
        String reportLabel=map.get("reportLabel").toString();
        if (reportLabel.equals("2")){
            map.put("reportLabel",6);
            return  salesAnalysisMapper.shopSalesMonthlyData(map);
        } if (reportLabel.equals("3")){
            map.put("reportLabel",8);
            return  salesAnalysisMapper.shopSalesMonthlyData(map);
        } if (reportLabel.equals("4")){
            map.put("reportLabel",7);
            return  salesAnalysisMapper.areaSalesMonthlyData(map);
        } if (reportLabel.equals("5")){
            map.put("reportLabel",8);
            return  salesAnalysisMapper.areaSalesMonthlyData(map);
        } if (reportLabel.equals("6")){
            map.put("reportLabel",9);
            return  salesAnalysisMapper.areaSalesMonthlyData(map);
        } if (reportLabel.equals("7")){
            map.put("reportLabel",10);
            return  salesAnalysisMapper.areaSalesMonthlyData(map);
        }
        return  salesAnalysisMapper.shopSalesMonthlyData(map);
    }
}
