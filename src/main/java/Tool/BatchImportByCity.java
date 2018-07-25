package Tool;

import com.ctrip.gs.destination.admin.soa.service.contract.CategoryPoiDto;
import com.ctrip.gs.destination.admin.soa.service.contract.ImportDataToCategoryTableRequestType;
import com.ctrip.gs.poi.business.soa.service.contract.GetPoiImagesRequestType;
import com.ctrip.gs.poi.business.soa.service.contract.PagingParamDto;
import com.ctrip.gs.poi.business.soa.service.contract.SearchPoiImageFilterParamDto;
import com.ctrip.platform.ai.query.common.*;
import org.apache.commons.lang.StringUtils;
import soaproxy.DestBusinessSOA4JavaProxy;
import soaproxy.PhotoSoaProxy;
import soaproxy.PoiSOAProxy;
import soaproxy.SunflowerBibleSOAProxy;
import util.ExportExcelUtil;
import util.ReadExcelUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lilianga on 2018/7/10.
 */
public class BatchImportByCity {

    public static void main(String[] args) throws Exception {
        List<List<String>> excelData = ReadExcelUtil.readExcel("D:\\机器导入城市（批量）.xlsx", 1);
        System.out.println(excelData.size());
        List<ImportDataToCategoryTableRequestType> categorys = new ArrayList<>();
        for (List<String> citys : excelData) {
            String districtId = citys.get(1).trim();
            long cityId = DestBusinessSOA4JavaProxy.getInstance().getCityIdByDistrictId(Long.parseLong(districtId));
            if (cityId != 0) {
                DestQueryDTO request = new DestQueryDTO();
                request.setAppId("common");
                request.setDestCityId(cityId);
                request.setFields(Arrays.asList("id,name,themes".split(",")));
                DestQueryResultDTO data = SunflowerBibleSOAProxy.getInstance().queryDest(request);
                List<ImportDataToCategoryTableRequestType> categorylist = getImportDataToCategoryTableRequestType(data, districtId);
                if (categorylist != null && categorylist.size() > 2) {
                    categorys.addAll(categorylist);
                }
            }
        }

        if (categorys.size() > 0) {
            List<List<String>> data = new ArrayList<>();
            for (ImportDataToCategoryTableRequestType category : categorys) {
                //AdminSOAProxy.getInstance().importDataToCategoryTable(category);
                for (CategoryPoiDto poi : category.getCategoryPoiList()) {
                    List<String> a = new ArrayList<>();
                    a.add(category.getCityId() + "");
                    a.add(category.getDistrictId() + "");
                    a.add(category.getCategoryCode());
                    a.add(category.getCategoryName());
                    a.add(category.getBrief());
                    a.add(category.getImageId());
                    a.add(category.getModifiedRank() + "");
                    a.add(poi.getPoiId() + "");
                    a.add(poi.getRank() + "");
                    data.add(a);
                }
            }
            ExportExcelUtil.exportExcel(data, "D:\\dypsj6.xlsx");
        }
        Runtime.getRuntime().exec("cmd /c start D:\\dypsj6.xlsx");
        System.out.println("over!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private static List<ImportDataToCategoryTableRequestType> getImportDataToCategoryTableRequestType(DestQueryResultDTO data, String districtId) {
        List<ImportDataToCategoryTableRequestType> requests = new ArrayList<>();
        ImportDataToCategoryTableRequestType request = null;
        if (data == null) return null;
        DestInfoDTO info = data.getResult().get(0);

        if (info.getThemes() == null || info.getThemes().size() == 0) return null;
        int index = 1;
        for (PoiTheme theme : info.getThemes()) {
            request = new ImportDataToCategoryTableRequestType();
            request.setCityId(info.getId());
            request.setDistrictId(Long.parseLong(districtId));
            request.setCategoryCode(theme.getCode());
            request.setCategoryName(theme.getName());
            request.setBrief(theme.getDescription());
            request.setModifiedRank((long) index);
            request.setChannelType(103);
            Long imageId = getPhotoId(theme.getImage());

            List<CategoryPoiDto> poiDtoList = new ArrayList<>();
            CategoryPoiDto poi = null;
            int rank = 1;
            if (theme.getPois() == null || theme.getPois().size() == 0) continue;
            if (imageId == null || imageId == 0) {
                GetPoiImagesRequestType request1 = new GetPoiImagesRequestType();
                PagingParamDto pagingParamDto = new PagingParamDto();
                pagingParamDto.setStart(1L);
                pagingParamDto.setCount(5L);
                request1.setPriorityShowCopyrightImage(true);
                SearchPoiImageFilterParamDto searchPoiImageFilterParamDto = new SearchPoiImageFilterParamDto();
                searchPoiImageFilterParamDto.setPoiId(theme.getPois().get(0).getId());
                request1.setFilterParam(searchPoiImageFilterParamDto);
                request1.setPagingParam(pagingParamDto);
                imageId = PoiSOAProxy.getInstance().getPoiImages(request1);
            }
            request.setImageId(imageId + "");
            for (PoiInfo poiInfo : theme.getPois()) {
                poi = new CategoryPoiDto();
                poi.setPoiId(poiInfo.getId());
                poi.setRank((long) rank);
                poiDtoList.add(poi);
                rank++;
            }
            request.setCategoryPoiList(poiDtoList);
            index++;
            requests.add(request);
        }
        return requests;
    }

    private static Long getPhotoId(String image) {
        if (StringUtils.isBlank(image)) return null;
        String path = "";
        if (image.contains("/images/")) {
            path = image.substring(image.indexOf("/images/") + 7);
        } else if (image.contains("/target/")) {
            path = image.substring(image.indexOf("/target/") + 7);
        }
        return PhotoSoaProxy.getInstance().getPhotoIdByFpath(path);
    }


}
