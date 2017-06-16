package com.yonghui.portal.util.fresh;

import org.apache.poi.hssf.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * Created by xrr on 2017/6/15.
 */
public class SaleAreaExport {



    public HSSFWorkbook exportByHydBudget(List<Map<String, Object>> list) throws Exception {

        // 创建excel
        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建sheet
        HSSFSheet sheet = wb.createSheet("销售=>大区");

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
        cellTitle.setCellValue("大区");
        cellTitle.setCellStyle(styleTitle);
        int cellInde = 0;

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("小区");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("课组");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("可比门店数");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("毛利额");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("毛利率");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客流量");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客单价");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("销售金额同店同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("毛利额同店同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("客流量同店同比");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("毛利率同比差异");
        cellTitle.setCellStyle(styleTitle);

        HSSFCellStyle styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cellInde = 0;
            HSSFCell cell = row.createCell(cellInde);

            cell.setCellValue(formatObjToStr(map.get("areaName")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("areaMans")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("className")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("shopNon")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("saleAmount")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profit")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profitRate")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("customer")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("guestPrice")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("saleTDTB")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("profitTDTB")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("customerTDTB")));
            cell.setCellStyle(styleCenter);

            cell = row.createCell(++cellInde);
            cell.setCellValue(formatObjToStr(map.get("TD_Profitratediff")));
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
