package Tool;

import util.ReadExcelUtil;

import java.util.List;

/**
 * Created by lilianga on 2018/7/10.
 */
public class BatchImportByCity {

    public static void main(String[] args) throws Exception {
        List<List<String>> excelData =ReadExcelUtil.readExcel("D:\\机器导入城市（批量）.xlsx",0);
        System.out.println(excelData.size());
    }
}
