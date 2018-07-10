package util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * Created by lilianga on 2018/6/15.
 */
public class ExportExcelUtil {
    public void exportExcel(Map<String,Long> dataMap)
    {
        //创建Excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表单
        HSSFSheet sheet = workbook.createSheet("iamgeInfo");
        int index = 0;
        for (String aaa : dataMap.keySet()) {
            //创建HSSFRow对象 （行）
            HSSFRow row = sheet.createRow(index++);
            //创建HSSFCell对象  （单元格）
            HSSFCell cell=row.createCell(0);
            //设置单元格的值
            cell.setCellValue(aaa);
            cell=row.createCell(1);
            cell.setCellValue(dataMap.get(aaa));
        }

    }

}
