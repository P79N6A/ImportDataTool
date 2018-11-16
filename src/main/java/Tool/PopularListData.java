package Tool;

import BO.PoiUV;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.gs.poi.sightbusiness.soa.service.contract.SearchPoiInfoDto;
import com.ctrip.gs.poi.sightbusiness.soa.service.contract.SearchSightPoiListRequestType;
import com.ctrip.gs.poi.sightbusiness.soa.service.contract.SearchSightPoiListResponseType;
import util.ExportExcelUtil;
import util.HttpClientUtil;
import util.ReadExcelUtil;

import java.io.*;
import java.util.*;

/**
 * Created by lilianga on 2018/11/6.
 */
public class PopularListData {

    public static String url = "http://webapi.soa.ctripcorp.com/api/14369/searchSightPoiList";

    public static void main(String[] args) throws Exception {

/*
        SearchSightPoiListRequestType requestType = new SearchSightPoiListRequestType();
        requestType.setDistrictId(2L);
        requestType.setOrderBy("rankscore:desc");
        requestType.setPlatform(0);
        requestType.setPoiType("SIGHT");
        requestType.setStart(1);
        requestType.setCount(500);*/
        List<List<String>> res = new ArrayList<>();
        List<String> uvList = ReadExcelUtil.readCsv("D:\\45天uv.csv");
        List<PoiUV> poiUVs = new ArrayList<>();
        uvList.remove(0);
        for (String s : uvList) {
            String[] poiAndUV = s.split(",");
            PoiUV poiUV = new PoiUV();
            poiUV.setUv(Long.parseLong(poiAndUV[1]));
            poiUV.setPoiId(Long.parseLong(poiAndUV[0]));
            poiUVs.add(poiUV);
        }
        //Collections.sort(poiUVs, (PoiUV o1, PoiUV o2) -> (int) o1.getUv() - (int) o2.getUv());

        Collections.sort(poiUVs, new Comparator<PoiUV>() {
            @Override
            public int compare(PoiUV o1, PoiUV o2) {
                return new Long(o2.getUv()).compareTo(new Long(o1.getUv()));
            }
        });
        File file = new File("D:\\150city.txt");
        String temp = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> row = new ArrayList<>();
        while ((temp = reader.readLine()) != null) {
            row.add(temp.trim());
        }
        reader.close();
        for (String s1 : row) {
            int rank = 1;
            /*Map<String, String> parameters = new HashMap<>();
            parameters.put("districtId", s1);
            parameters.put("orderBy", "rankscore:desc");
            parameters.put("poiType", "SIGHT");
            parameters.put("start", "1");
            parameters.put("count", "500");

            String result = sendPost(url, parameters);*/

            SearchSightPoiListRequestType requestType = new SearchSightPoiListRequestType();
            requestType.setDistrictId(Long.parseLong(s1));
            requestType.setOrderBy("rankscore:desc");
            requestType.setPlatform(0);
            requestType.setPoiType("SIGHT");
            requestType.setStart(1);
            requestType.setCount(500);
            String result = HttpClientUtil.sendHttpPostJson(url, JSON.toJSONString(requestType));
            SearchSightPoiListResponseType response = JSONObject.parseObject(result, SearchSightPoiListResponseType.class);
            List<SearchPoiInfoDto> list = response.getResult();
            System.out.println(s1 + ":" + list.size());
            if (list != null && list.size() > 0) {
                List<String> poiInfo = null;
                OUT:
                for (PoiUV poiUV : poiUVs) {
                    for (SearchPoiInfoDto o : list) {
                        if (poiUV.getPoiId() == (o.getPoiId())) {
                            poiInfo = new ArrayList<>();
                            poiInfo.add(s1 + "");
                            poiInfo.add(o.getDistrictName());
                            poiInfo.add(o.getPoiId() + "");
                            poiInfo.add(o.getName());
                            poiInfo.add(poiUV.getUv() + "");
                            poiInfo.add("" + rank++);
                            res.add(poiInfo);
                            if (rank > 20) break OUT;
                        }
                    }
                }
            }
        }
        System.out.println(res.size());
        ExportExcelUtil.exportExcel(res, "D:\\poiuv3.xlsx");

    }


    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),
                                    "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
