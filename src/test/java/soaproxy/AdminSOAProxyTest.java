package soaproxy;

import com.ctrip.gs.destination.admin.soa.service.contract.CategoryPoiDto;
import com.ctrip.gs.destination.admin.soa.service.contract.ImportDataToCategoryTableRequestType;
import com.ctrip.gs.destination.admin.soa.service.contract.ImportDataToCategoryTableResponseType;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by lilianga on 2018/6/19.
 */
public class AdminSOAProxyTest extends TestCase {

    public void testImportDataToCategoryTable() throws Exception {
        ImportDataToCategoryTableRequestType category = new ImportDataToCategoryTableRequestType();
        category.setCategoryName("原始地貌");
        category.setBrief("探访神奇自然地貌");
        category.setCategoryId(5246L);
        category.setDistrictId(275L);
        category.setImageId("272757308");
        category.setModifiedRank(1l);
        CategoryPoiDto poi = new CategoryPoiDto();
        poi.setPoiId(78466l);
        poi.setRank(3l);
        category.setCategoryPoiList(Arrays.asList(poi));

        ImportDataToCategoryTableResponseType response = AdminSOAProxy.getInstance().importDataToCategoryTable(category);
        System.out.println(response.getTotalCount());
    }
}