package soaproxy;

import com.ctrip.gs.poi.business.soa.service.contract.GetPoiImagesRequestType;
import com.ctrip.gs.poi.business.soa.service.contract.GetPoiImagesResponseType;
import com.ctrip.gs.poi.business.soa.service.contract.PoiBusinessServiceClient;
import com.ctrip.gs.poi.business.soa.service.contract.PoiImageDto;

/**
 * Created by lilianga on 2018/7/23.
 */
public class PoiSOAProxy {
    private static PoiSOAProxy _instance;

    public static PoiSOAProxy getInstance() {
        if (_instance == null) {
            _instance = new PoiSOAProxy();
        }
        return _instance;
    }

    private static PoiBusinessServiceClient client;

    static {
        client=PoiBusinessServiceClient.getInstance();
    }

    public Long getPoiImages(GetPoiImagesRequestType request) {
        try {
            GetPoiImagesResponseType response = client.getPoiImages(request);
            if (response != null && response.getPoiImages() != null && response.getPoiImages().size() > 0){
                PoiImageDto image = response.getPoiImages().get(0);
                if (image.getImageDetail()==null||image.getImageDetail().getImageBasicInfo()==null) return null;
                return image.getImageDetail().getImageBasicInfo().getImageId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
