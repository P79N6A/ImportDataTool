package Tool;

import com.ctrip.gs.destination.admin.soa.service.contract.PoiInfoDto;
import com.ctrip.gs.destination.admin.soa.service.contract.UpdateCategoryPoiRequestType;
import util.ReadExcelUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lilianga on 2018/9/6.
 */
public class UpdateCategoryPoi {
    public static void main(String[] args) throws Exception {
        List<List<String>> excelData = ReadExcelUtil.readExcel("D:\\必玩top榜更新-导入-9.4.xlsx", 0);
        System.out.println(excelData.size());
        int index = 0;
        Map<Long,List<PoiInfoDto>> map = new HashMap<>();
        for (List<String> data : excelData) {

            if (map.get(Long.parseLong(data.get(0)))==null){
                List<PoiInfoDto> list = new ArrayList<>();
                PoiInfoDto dto = new PoiInfoDto();
                dto.setRank(Integer.parseInt(data.get(4)));
                dto.setPoiId(Long.parseLong(data.get(2)));
                list.add(dto);
                map.put(Long.parseLong(data.get(0)),list);
            }else {
                PoiInfoDto dto = new PoiInfoDto();
                dto.setRank(Integer.parseInt(data.get(4)));
                dto.setPoiId(Long.parseLong(data.get(2)));
                map.get(Long.parseLong(data.get(0))).add(dto);
            }

        }
        UpdateCategoryPoiRequestType request = null;
        for (Long k : map.keySet()) {
            request = new UpdateCategoryPoiRequestType();
            request.setDistrictId(k);
            request.setPoiInfo(map.get(k));
            //AdminSOAProxy.getInstance().updateCategoryPoi(request);
        }

        // System.out.println(index);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!over!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
