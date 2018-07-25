package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnectionUtil {

    /**
     * @param urlPath 下载路径
     *                下载存放目录
     * @return 返回下载文件
     * @throws Exception
     */
    public static void downloadFile(String urlPath, String saveDir) throws Exception {
        URL url = new URL(urlPath);
        // 连接类的父类，抽象类  
        URLConnection urlConnection = url.openConnection();
        // http的连接类  
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        // 设定请求的方法，默认是GET（对于知识库的附件服务器必须是GET，如果是POST会返�?405。流程附件迁移功能里面必须是POST，有�?区分。）  
        httpURLConnection.setRequestMethod("GET");
        // 设置字符编码  
        httpURLConnection.setRequestProperty("Charset", "UTF-8");
        // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）�??  
        int code = httpURLConnection.getResponseCode();


        InputStream inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        byte[] dataBytes = baos.toByteArray();

        InputStream retInputStream = new ByteArrayInputStream(dataBytes);

        File file = new File(saveDir);
        OutputStream out = new FileOutputStream(file);
        int size = 0;
        int lent = 0;
        byte[] buf = new byte[1024];
        while ((size = retInputStream.read(buf)) != -1) {
            lent += size;
            out.write(buf, 0, size);
        }
        inputStream.close();
        retInputStream.close();
        out.close();
    }


    public static void main(String[] args) throws Exception {
        // 下载文件测试
        String url = "https://dimg08.c-ctrip.com/images//100b050000000dk6pF3AF.jpg";
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadFile(url, "d:\\test\\"+fileName);
        System.out.println("下载完成");
    }
}  