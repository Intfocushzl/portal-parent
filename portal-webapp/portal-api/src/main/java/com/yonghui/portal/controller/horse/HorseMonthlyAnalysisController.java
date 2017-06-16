package com.yonghui.portal.controller.horse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.series.Pie;
import com.yonghui.portal.model.horse.HorseSort;
import com.yonghui.portal.service.horse.HorseMonthlyAnalysisService;
import com.yonghui.portal.util.StringTools;
import com.yonghui.portal.util.horse.horseExport;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuwei
 */
@Controller
@RequestMapping("/horse")
public class HorseMonthlyAnalysisController {

    Logger log = Logger.getLogger(this.getClass());

    @Reference
    private HorseMonthlyAnalysisService horseMonthlyAnalysisService;


    // 报表图
    @RequestMapping(value = "report", method = RequestMethod.GET)
    public void marketReport(HttpServletResponse response, String sdate, String sapshopid, String groupid) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 创建Option
        Option option = new Option();
        option.legend().data("15分-12分", "12分-10分", "10分-8分", "8分-6分", "6分-0分", "0分");
        option.title("得分项分布");
        // 饼图数据
        Pie pie = new Pie("得分项分布");
        List<Map<String, Object>> list = horseMonthlyAnalysisService.totel(sdate, sapshopid, groupid);
        Map<String, Object> totel = list.get(0);
        pie.data(new PieData("15分-12分", totel.get("fen15")));
        pie.data(new PieData("12分-10分", totel.get("fen12")));
        pie.data(new PieData("10分-8分", totel.get("fen10")));
        pie.data(new PieData("8分-6分", totel.get("fen8")));
        pie.data(new PieData("6分-0分", totel.get("fen6")));
        pie.data(new PieData("0分", totel.get("fen0")));
        option.series(pie);
        try {
            String str = JSONObject.toJSONString(option);
            StringTools.sWrite(response, str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("export")
    public void getShopHorseResultAscByListExport(HttpServletResponse response, HttpServletRequest request, String  lkpDate, String shopId, String groupId){
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<HorseSort>  list=new ArrayList<HorseSort>();
        list= horseMonthlyAnalysisService.getShopHorseResultAscByList(lkpDate, shopId, groupId);

        try {
            horseExport utils=new horseExport();
            HSSFWorkbook workbook= utils.exportByHydBudget(list);
            String filename="SM-Result.xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            log.error("导出文件异常", e);
        }
    }
}
