package soaproxy;

import com.ctrip.gs.photogallery.admin.soa.contract.AddPhotoUploadInfoRequestType;
import com.ctrip.gs.photogallery.admin.soa.contract.AddPhotoUploadInfoResponseType;
import com.ctrip.gs.photogallery.admin.soa.contract.GsPhotoGalleryAdminSoaClient;
import com.ctrip.gs.photogallery.admin.soa.contract.PhotoUploadInfoDto;

/**
 * Created by lilianga on 2018/6/15.
 */
public class PhotoSoaProxy {

    private static PhotoSoaProxy _instance;

    public static PhotoSoaProxy getInstance() {
        if (_instance == null) {
            _instance = new PhotoSoaProxy();
        }
        return _instance;
    }

    private static GsPhotoGalleryAdminSoaClient client;

    static {
        client =  GsPhotoGalleryAdminSoaClient.getInstance();
    }

    public Long getUploadPhotoId(String path,int serverType){
        try {
            AddPhotoUploadInfoRequestType request = new AddPhotoUploadInfoRequestType();
            PhotoUploadInfoDto dto = new PhotoUploadInfoDto();
            dto.setServerType(serverType);
            dto.setFpath(path);
            request.setDto(dto);
            AddPhotoUploadInfoResponseType response = client.addPhotoUploadInfo(request);
            if (response!=null){
                return response.getPhotoId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
