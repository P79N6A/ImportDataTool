package Tool;

import com.ctrip.gs.destination.admin.soa.service.contract.UpdateCategoryRequestType;
import soaproxy.AdminSOAProxy;
import util.ReadExcelUtil;

import java.util.List;

/**
 * Created by lilianga on 2018/7/25.
 */
public class UpdateCategory {

    public static void main(String[] args) throws Exception {
        List<List<String>> excelData = ReadExcelUtil.readExcel("D:\\categoryData.xlsx", 0);
        System.out.println(excelData.size());
        int index = 0;
        for (List<String> data : excelData) {
            UpdateCategoryRequestType request = new UpdateCategoryRequestType();
            request.setCategoryId(Long.parseLong(data.get(0)));
            request.setChannelType(3);
            request.setSourceFrom("AI");
            if (data.get(1).endsWith("mustPlay")) {
                request.setCategoryTags("19996");
            }
            AdminSOAProxy.getInstance().updateCategory(request);
            index++;
/*            if (index > 10) {
                break;
            }*/
        }
        System.out.println(index);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!over!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
