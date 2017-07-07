package com.yonghui.portal.util;

import org.apache.poi.hssf.usermodel.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 详情数据导出成excel
 * 操作Excel表格的功能类
 * <p>
 * 张海 2017.05.13
 */
public class ApiExportExport {

    /**
     * 统一excel导出
     * 必须保证标题数组长度和Map内容个数相同
     * 功能未测试！
     *
     * @param list          数据集合
     * @param cellTitleName 标题 格式如：amt:金额|date:日期|area:地区
     * @throws Exception
     */
    public HSSFWorkbook export(List<Map<String, Object>> list, String[] cellTitleName, String titleName) throws Exception {
        // 创建excel
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = wb.createSheet(titleName);
        // 创建第一行（标题行）
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

        // 在第一行上创建标题列
        HSSFCell cellTitle = null;
        int cellInde = 0;
        for (String str : cellTitleName) {
            cellTitle = rowTitle.createCell(cellInde);
            if (str.split("=").length == 1) {
                cellTitle.setCellValue(str.split("=")[0]);
            } else if (StringUtils.isEmpty(str.split("=")[1])) {
                cellTitle.setCellValue(str.split("=")[0]);
            } else {
                cellTitle.setCellValue(str.split("=")[1]);
            }
            cellTitle.setCellStyle(styleTitle);
            cellInde = cellInde + 1;
        }

        // 创建内容
        Map<String, Object> map = null;
        HSSFCellStyle styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cellInde = 0;
            HSSFCell cell = null;
            for (String str : cellTitleName) {
                cell = row.createCell(++cellInde);
                if (map.get(str.split("=")[0]) == null) {
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(map.get(str.split("=")[0]).toString());
                }
                cell.setCellStyle(styleCenter);
            }

        }
        return wb;
    }

    /**
     * 数字格式化
     *
     * @param dob
     * @return
     */
    public String dataFormat(Double dob) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(dob);
    }
}
