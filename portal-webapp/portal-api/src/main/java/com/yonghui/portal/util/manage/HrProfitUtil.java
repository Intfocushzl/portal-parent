package com.yonghui.portal.util.manage;

import com.yonghui.portal.model.manage.HrProfit;
import com.yonghui.portal.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 人事分红
 */
@Component("hrProfitUtil")
public class HrProfitUtil {

    Logger log = Logger.getLogger(this.getClass());

    public int totalCells;

    /**
     * @throws Exception
     * @author liuwei
     * <p>
     */
    public List<HrProfit> importHrProfit(Workbook wb) throws Exception {
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        int totalRows = sheet.getLastRowNum();// 所有行
        // 得到Excel的最大的一列(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        } else {
            throw new Exception("请按照模板填写数据后上传");
        }
        List<HrProfit> profitList = new ArrayList<HrProfit>();
        HrProfit profit;
        Row row;
        // 循环Excel行数,从第二行开始。
        for (int r = 1; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            profit = new HrProfit();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                // 判断是否为空
                boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                if (isNull) {
                    throw new Exception("填写的数据含有空值");
                }
                // 判断是否全部是数字
                if (c == 3) {
                    try {
                        stringToDate(cell.getStringCellValue().trim());
                    } catch (Exception e) {
                        throw new Exception("不是日期类型的数据！！" + "第" + r + "行" + (c + 1) + "正确格式yyyyMM");
                    }
                    profit.setLkpMonth(cell.getStringCellValue().trim());
                } else if (c == 2) {
                    profit.setShopid(cell.getStringCellValue().trim());
                } else if (c == 1) {
                    profit.setShopname(cell.getStringCellValue().trim());
                } else if (c == 4) {
                    profit.setAdj_profit(Float.valueOf(cell.getStringCellValue().trim()));
                } else if (c == 5) {
                    profit.setRemark(cell.getStringCellValue().trim());
                }
                } else {
                    throw new Exception("填写的数据含有空值！！" + "第" + r + "行" + (c + 1) + "列有空值");
                }
            }
            // 添加数据
            profitList.add(profit);
        }
        return profitList;
    }

    public Date stringToDate(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//小写的mm表示的是分钟
        return sdf.parse(dateStr);
    }
}
