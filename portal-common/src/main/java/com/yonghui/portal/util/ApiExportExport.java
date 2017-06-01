package com.yonghui.portal.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 详情数据导出成excel
 * 操作Excel表格的功能类
 * <p>
 * 张海 2017.05.13
 */
public class ApiExportExport {

    // 创建excel
    private HSSFWorkbook wb = new HSSFWorkbook();
    // 创建sheet
    private HSSFSheet sheet = wb.createSheet("YongHui-数据");
    // 标题行
    private List<HSSFRow> rowTitles = new ArrayList<>();
    // 标题层级记录
    private Map<Integer, Integer> zindexMap = new HashMap<>();
    // 当前标题层级（第n行）
    private int zindex = 0;
    // 报表数据标题列
    private StringBuffer cellTitleSb = new StringBuffer();

    /**
     * 统一excel导出
     * 必须保证标题数组长度和Map内容个数相同
     * 功能未测试！
     *
     * @param list       数据集合
     * @param jsonObject 标题
     * @throws Exception
     */
    public HSSFWorkbook export(List<Map<String, Object>> list, JSONObject jsonObject) throws Exception {
        // 创建标题栏样式
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        HSSFFont fontTitle = wb.createFont();
        // 宋体加粗
        fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTitle.setFontName("宋体");
        fontTitle.setFontHeight((short) 200);
        styleTitle.setFont(fontTitle);
        // 在行上创建标题列
        HSSFCell cellTitle = null;
        int cellInde = 0;
        // 创建标题行
        int lineCount = Integer.parseInt(jsonObject.get("lineCount").toString());
        for (int i = 0; i <= lineCount; i++) {
            // 创建标题行
            rowTitles.add(sheet.createRow(i));
        }
        // 一级标题 数组
        JSONArray jsonArray = jsonObject.getJSONArray("children");
        // 遍历所有标题
        treeTitleList(jsonArray);

        /*for (JSONObject json : jsonArray) {
            cellTitle = rowTitle.createCell(cellInde);
            if (str.split(":").length == 1) {
                cellTitle.setCellValue(str.split(":")[0]);
            } else if (StringUtils.isEmpty(str.split(":")[1])) {
                cellTitle.setCellValue(str.split(":")[0]);
            } else {
                cellTitle.setCellValue(str.split(":")[1]);
            }
            cellTitle.setCellStyle(styleTitle);
            cellInde = cellInde + 1;
        }*/


        // 创建内容
        /*Map<String, Object> map = null;
        HSSFCellStyle styleCenter = wb.createCellStyle();
        styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            cellInde = 0;
            HSSFCell cell = null;
            for (String str : cellTitleName) {
                cell = row.createCell(++cellInde);
                if (map.get(str.split(":")[0]) == null) {
                    cell.setCellValue("");
                } else {
                    cell.setCellValue(map.get(str.split(":")[0]).toString());
                }
                cell.setCellStyle(styleCenter);
            }

        }*/
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


    /**
     * 解析标题树
     *
     * @param jsonArray
     * @return
     */
    public void treeTitleList(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            // 当前行
            zindex = Integer.parseInt(job.get("zindex").toString()) - 1;
            /*columnCount = Integer.parseInt(job.get("zindex").toString()) - 1;

            if (true == Boolean.parseBoolean(job.get("isleaf").toString())) {
                cellTitleSb.append(job.get("measurelab"));
            }`
            if (job.get("zindex")){

            }*/
                System.out.println(job.get("treecode"));
            if (false == Boolean.parseBoolean(job.get("isleaf").toString())) {
                JSONArray c_jsonArray = job.getJSONArray("children");
                treeTitleList(c_jsonArray);
            }
        }
    }

}
