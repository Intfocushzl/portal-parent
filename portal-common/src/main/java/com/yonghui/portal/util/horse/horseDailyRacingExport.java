package com.yonghui.portal.util.horse;

import com.yonghui.portal.model.horse.EveryDayHorseRacingNew;
import org.apache.poi.hssf.usermodel.*;

import java.text.DecimalFormat;
import java.util.List;


/**
 * 门店赛马详情数据导出成excel
 * 操作Excel表格的功能类
 */
public class horseDailyRacingExport {
    /*
     * 赛马结果分析
     */
    public HSSFWorkbook exportByHydBudget(List<EveryDayHorseRacingNew> list) throws Exception {

        // 创建excel
        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建sheet
        HSSFSheet sheet = wb.createSheet("天天赛马详情");

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

        cellTitle.setCellValue("日期");
        cellTitle.setCellStyle(styleTitle);
        int cellInde = 0;

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("店编");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("店名");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("小店ID");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("小店名称");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("项目名称");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("门店值");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("对标值");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("排名");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("均值上下标记");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("档次");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("得分");
        cellTitle.setCellStyle(styleTitle);

        cellTitle = rowTitle.createCell(++cellInde);
        cellTitle.setCellValue("参与排名门店数");
        cellTitle.setCellStyle(styleTitle);

        HSSFCellStyle styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        for (int i = 0; i < list.size(); i++) {
            EveryDayHorseRacingNew item = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cellInde = 0;
            HSSFCell cell = row.createCell(cellInde);
            cell.setCellValue(item.getSdate());
            cell.setCellStyle(styleCenter);
            // 大店Id
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getSapshopid());
            cell.setCellStyle(styleCenter);
            // 大店名称
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getSname());
            cell.setCellStyle(styleCenter);
            // 小店ID
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getGroupid());
            cell.setCellStyle(styleCenter);
            // 小店名称
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getGroupname());
            cell.setCellStyle(styleCenter);
            // 项目名称
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getProjectName());
            cell.setCellStyle(styleCenter);
            if (item.getProjectName().endsWith("库存周转天数")) {
                // 门店值
                cell = row.createCell(++cellInde);
                cell.setCellValue(dataFormat(item.getShopValue()));
                cell.setCellStyle(styleCenter);
                //对标值
                cell = row.createCell(++cellInde);
                cell.setCellValue(dataFormat(item.getOnValue()));
                cell.setCellStyle(styleCenter);
            } else {
                // 门店值
                cell = row.createCell(++cellInde);
                cell.setCellValue(dataFormat(item.getShopValue() * 100) + "%");
                cell.setCellStyle(styleCenter);
                //对标值
                cell = row.createCell(++cellInde);
                cell.setCellValue(dataFormat(item.getOnValue() * 100) + "%");
                cell.setCellStyle(styleCenter);
            }
            // 排名
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getRanking());
            cell.setCellStyle(styleCenter);
            // 升降标志
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getFlag());
            cell.setCellStyle(styleCenter);
            // 档次
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getAbc());
            cell.setCellStyle(styleCenter);
            // 得分
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getScore());
            cell.setCellStyle(styleCenter);
            // 参与排名门店数
            cell = row.createCell(++cellInde);
            cell.setCellValue(item.getShopNum());
            cell.setCellStyle(styleCenter);
        }
        return wb;
    }

    public String dataFormat(Double dob) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(dob);
    }
}
