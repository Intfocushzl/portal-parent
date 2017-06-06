package com.yonghui.portal.util.horse;

import org.apache.poi.hssf.usermodel.*;

import java.util.List;
import java.util.Map;


/**
 * 操作Excel表格的功能类
 */
public class groupHorseExport {
    /*
     * 赛马结果分析
     */
    public HSSFWorkbook exportByHydBudget(List<Map<String, Object>> list) throws Exception {

        // 创建excel
        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建sheet
        HSSFSheet sheet = wb.createSheet("指标明细");

        // 创建一行
        HSSFRow rowTitle = sheet.createRow(0);

        // 创建标题栏样式
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        HSSFFont fontTitle = wb.createFont();
        // 宋体加粗
        fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTitle.setFontName("宋体");
        fontTitle.setFontHeight((short) 200);
        styleTitle.setFont(fontTitle);

        // 在行上创建1列
        HSSFCell cellTitle = rowTitle.createCell(0);

        // 在行上创建列
        cellTitle.setCellValue("月份");
        cellTitle.setCellStyle(styleTitle);
        int cellInde = 0;

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("集群/省区标记");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("商行id");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售额同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售同比成长率");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("综合毛利额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("综合毛利额同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("综合毛利额同比成长率");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客流");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客流同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客流同比成长率");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("库存金额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售成本");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("周转天数");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("损耗额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("生鲜销售额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("损耗率");
        cellTitle.setCellStyle(styleTitle);

        HSSFCellStyle styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cellInde = 0;
            HSSFCell cell = row.createCell(cellInde);

            cell.setCellValue(formatObjToStr(map.get("sdate")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("clusterflag")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("groupid")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("sale")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("saletb")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("saletblv")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profit")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profittb")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profittblv")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("kl")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("kltb")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("kltblv")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("closecostv")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("costvalue")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("zzts")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("lost")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("salefresh")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("lostlv")));
            cell.setCellStyle(styleCenter);
        }
        return wb;
    }

    public String formatObjToStr(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }
}
