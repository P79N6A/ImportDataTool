package soaproxy;

import com.ctrip.gs.destination.admin.soa.service.contract.*;


public class AdminSOAProxy {
    private static AdminSOAProxy _instance;

    public static AdminSOAProxy getInstance() {
        if (_instance == null) {
            _instance = new AdminSOAProxy();
        }
        return _instance;
    }

    private static DestAdminServiceClient client;

    static {
        client = DestAdminServiceClient.getInstance();
    }

    public ImportDataToCategoryTableResponseType importDataToCategoryTable(ImportDataToCategoryTableRequestType request) {
        try {
            return client.importDataToCategoryTable(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UpdateCategoryResponseType updateCategory(UpdateCategoryRequestType request) {
        try {
            return client.updateCategory(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UpdateCategoryPoiResponseType updateCategoryPoi(UpdateCategoryPoiRequestType request) {
        try {
            return client.updateCategoryPoi(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
