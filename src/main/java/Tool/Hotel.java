package Tool;

import com.ctrip.soa.hotel.queryws.productservice.v1.HotelAllDataEntity;
import com.ctrip.soa.hotel.queryws.productservice.v1.HotelSimpleEntity;
import soaproxy.HotelProductServiceSOAProxy;
import util.ExportExcelUtil;
import util.ReadExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilianga on 2018/10/23.
 */
public class Hotel {
    public static void main(String[] args) throws Exception {
        List<List<String>> excelData = ReadExcelUtil.readExcel("D:\\hotelid.xlsx", 0);
        List<List<String>> exportData=new ArrayList<>();
        for (List<String> strings : excelData) {
            long id = Long.parseLong(strings.get(0));
            HotelAllDataEntity entity = HotelProductServiceSOAProxy.getInstance().getHotelStaticInfo(id);
            if (entity != null && entity.getHotelStaticBaseInfoEntity() != null && entity.getHotelStaticBaseInfoEntity().getHotelSimpleEntity() != null) {
                HotelSimpleEntity simpleEntity = entity.getHotelStaticBaseInfoEntity().getHotelSimpleEntity();
                List<String> list = new ArrayList<>();
                list.add(simpleEntity.getHotel()+"");
                list.add(simpleEntity.getHotelName());
                list.add(simpleEntity.getCountry()+"");
                list.add(simpleEntity.getProvince()+"");
                list.add(simpleEntity.getCity()+"");
                list.add(simpleEntity.getDistrict()+"");
                list.add(simpleEntity.getMasterHotelID()+"");
                exportData.add(list);
            }

        }

        ExportExcelUtil.exportExcel(exportData, "D:\\hotel.xlsx");

    }
}
