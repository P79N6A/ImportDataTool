package Tool;

import com.ctrip.gs.destination.admin.soa.service.contract.UpdateCategoryRequestType;
import soaproxy.AdminSOAProxy;

/**
 * Created by lilianga on 2018/7/25.
 */
public class UpdateCategory {

    public static void main(String[] args) throws Exception {
/*        List<List<String>> excelData = ReadExcelUtil.readExcel("D:\\categoryData.xlsx", 0);
        System.out.println(excelData.size());
        int index = 0;
        for (List<String> data : excelData) {
            UpdateCategoryRequestType request = new UpdateCategoryRequestType();
            request.setCategoryId(Long.parseLong(data.get(0)));
            if (data.get(1).endsWith("mustPlay")) {
                request.setChannelType(113);
                AdminSOAProxy.getInstance().updateCategory(request);
            }

            index++;
*//*            if (index > 10) {
                break;
            }*//*
        }*/

        UpdateCategoryRequestType request = new UpdateCategoryRequestType();
        request.setCategoryId(9405L);
        request.setChannelType(113);
        AdminSOAProxy.getInstance().updateCategory(request);


        // System.out.println(index);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!over!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
