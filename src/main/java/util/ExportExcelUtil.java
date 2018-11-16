package util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by lilianga on 2018/6/15.
 */
public class ExportExcelUtil {

    /**
     * 导出Excel
     *
     * @param values   内容
     * @param filePath 文件保存路径
     * @return
     */
    public static void exportExcel(List<List<String>> values, String filePath) {
        FileOutputStream fout = null;
        try {
            File file = new File(filePath);
            fout = new FileOutputStream(file);
            if (filePath.endsWith(".xlsx")) {
                if (values.size()>60000){
                    SXSSFWorkbook  wb = getSXSSFWorkbook(null, null, values, null);
                    wb.write(fout);
                }else {
                    XSSFWorkbook wb = getXSSFWorkbook(null, null, values, null);
                    wb.write(fout);
                }

            } else {
                HSSFWorkbook wb = getHSSFWorkbook(null, null, values, null);
                wb.write(fout);
            }

            fout.flush();
            String str = "导出" + filePath + "成功！";
            System.out.println(str);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
            String str1 = "导出" + filePath + "失败！";
            System.out.println(str1);
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<String> title, List<List<String>> values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        if (StringUtils.isBlank(sheetName)) sheetName = "sheet1";
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        if (title != null && title.size() > 0) {
            for (int i = 0; i < title.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(title.get(i));
                cell.setCellStyle(style);
            }
        }

        //创建内容
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values.get(i).size(); j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values.get(i).get(j));
            }
        }
        return wb;
    }

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static XSSFWorkbook getXSSFWorkbook(String sheetName, List<String> title, List<List<String>> values, XSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new XSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        if (StringUtils.isBlank(sheetName)) sheetName = "sheet1";
        XSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        XSSFCell cell = null;

        //创建标题
        if (title != null && title.size() > 0) {
            for (int i = 0; i < title.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(title.get(i));
                cell.setCellStyle(style);
            }
        }

        //创建内容
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values.get(i).size(); j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values.get(i).get(j));
            }
        }
        return wb;
    }


    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static SXSSFWorkbook getSXSSFWorkbook(String sheetName, List<String> title, List<List<String>> values, SXSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new SXSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        if (StringUtils.isBlank(sheetName)) sheetName = "sheet1";
        SXSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        SXSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        SXSSFCell cell = null;

        //创建标题
        if (title != null && title.size() > 0) {
            for (int i = 0; i < title.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(title.get(i));
                cell.setCellStyle(style);
            }
        }

        //创建内容
        for (int i = 0; i < values.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values.get(i).size(); j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values.get(i).get(j));
            }
        }
        return wb;
    }


}
