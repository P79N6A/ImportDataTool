package Tool;

import util.ExportExcelUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilianga on 2018/9/19.
 */
public class GaoDeMap {

    public static void main(String[] args) {
        BufferedReader reader = null;
        String temp = null;
        List<List<String>> values = new ArrayList<>();
        try {
            List<String> filePaths = readfile("D:\\b\\08");
            int line = 1;
            for (String filePath : filePaths) {
                File file = new File(filePath);

                reader = new BufferedReader(new FileReader(file));
                List<String> row = null;
                while ((temp = reader.readLine()) != null) {
                    row = new ArrayList<>();
                    String[] aaaa = temp.split("\\|\\|\\|");
                    for (String s : aaaa) {
                        row.add(s);
                    }
                    values.add(row);
                    //System.out.println("line" + line + ":" + temp);
                    line++;
                }
            }
            System.out.println("lineCount:" + line);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ExportExcelUtil.exportExcel(values, "D:\\b\\08.xlsx");
    }

    public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
        List<String> filePaths = new ArrayList<>();
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
/*                        System.out.println("path=" + readfile.getPath());
                        System.out.println("absolutepath=" + readfile.getAbsolutePath());*/
                        System.out.println("name=" + readfile.getName());
                        filePaths.add(readfile.getPath());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return filePaths;
    }
}
