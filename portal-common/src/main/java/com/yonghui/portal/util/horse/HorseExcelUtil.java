package com.yonghui.portal.util.horse;

import com.yonghui.portal.model.horse.HorseImportCash;
import com.yonghui.portal.model.horse.HorseOperateScore;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.yonghui.portal.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/*
 * 赛马excel数据导入
 * liuwei
 * 
 */

public class HorseExcelUtil {

    Logger log = Logger.getLogger(this.getClass());

    public int totalCells;

    /**
     * @throws Exception
     * @author liuwei
     * <p>
     * 赛马导入excel数据(营运得分项)
     */
    public List<HorseOperateScore> importExcelForHorseScore(Workbook wb) throws Exception {
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
        List<HorseOperateScore> scoreList = new ArrayList<HorseOperateScore>();
        HorseOperateScore score;
        Row row;
        // 循环Excel行数,从第三行开始。
        for (int r = 2; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            score = new HorseOperateScore();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 设置行号
                    score.setRowNum(r + 1);
                    // 判断是否为空
                    boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                    if (isNull) {
                        throw new Exception("填写的数据含有空值");
                    }
                    // 判断是否全部是数字
                    try {
                        isNumber(cell.getStringCellValue().trim());
                    } catch (Exception e) {
                        throw new Exception("填写的数据含有非法字符");
                    }
                    if (c == 0) {
                        if (cell.getStringCellValue().trim().length() != 6) {
                            throw new Exception("请填写正确的六位数日期，例如201704");
                        }
                        score.setSdate(cell.getStringCellValue().trim());
                    } else if (c == 1) {
                        score.setSapshopid(cell.getStringCellValue().trim());
                    } else if (c == 2) {
                        score.setGroupid(Integer.valueOf(cell.getStringCellValue().trim()));
                    } else if (c == 3) {
                        score.setThevalue(Double.valueOf(cell.getStringCellValue().trim()));
                    }
                }
            }
            // 添加数据
            scoreList.add(score);
        }
        return scoreList;
    }

    /**
     * @throws Exception
     * @author liuwei
     * <p>
     * 赛马导入excel数据(收银准确性)
     */
    public List<HorseImportCash> importExcelForHorsePayTruely(Workbook wb) throws Exception {
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
        List<HorseImportCash> payTruely = new ArrayList<HorseImportCash>();
        HorseImportCash pay;
        Row row;
        // 循环Excel行数,从第三行开始。
        for (int r = 1; r <= totalRows; r++) {
            row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            pay = new HorseImportCash();
            // 循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                // 格式化，每个单元个都为字符串
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // 设置行号
                    pay.setRowNum(r + 1);
                    // 判断是否为空
                    boolean isNull = StringUtils.isEmpty(cell.getStringCellValue().trim());
                    if (isNull) {
                        throw new Exception("填写的数据含有空值");
                    }
                    // 判断是否全部是数字
                    try {
                        isNumber(cell.getStringCellValue().trim());
                    } catch (Exception e) {
                        throw new Exception("填写的数据含有非法字符");
                    }
                    if (c == 0) {
                        pay.setSdate(cell.getStringCellValue().trim());
                    } else if (c == 1) {
                        pay.setSapshopid(cell.getStringCellValue().trim());
                    } else if (c == 2) {
                        pay.setGroupid(Integer.valueOf(cell.getStringCellValue().trim()));
                    } else if (c == 3) {
                        pay.setWrong(Double.valueOf(cell.getStringCellValue().trim()));
                    } else if (c == 4) {
                        pay.setNumber(Double.valueOf(cell.getStringCellValue().trim()));
                    }
                }
            }
            // 添加数据
            payTruely.add(pay);
        }
        return payTruely;
    }

    public  Double isNumber(String str) {
        return Double.parseDouble(str);
    }
}
