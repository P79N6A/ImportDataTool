package soaproxy;

import com.ctrip.gs.photogallery.admin.soa.contract.AddPhotoUploadInfoRequestType;
import com.ctrip.gs.photogallery.admin.soa.contract.AddPhotoUploadInfoResponseType;
import com.ctrip.gs.photogallery.admin.soa.contract.GsPhotoGalleryAdminSoaClient;
import com.ctrip.gs.photogallery.admin.soa.contract.PhotoUploadInfoDto;
import com.ctrip.gs.photogallery.service.soa.contract.*;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

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
    private static GsPhotoGallerySoaClient client_;

    static {
        client = GsPhotoGalleryAdminSoaClient.getInstance();
        client_ = GsPhotoGallerySoaClient.getInstance();
    }

    public Long getUploadPhotoId(String path, int serverType) {
        try {
            AddPhotoUploadInfoRequestType request = new AddPhotoUploadInfoRequestType();
            PhotoUploadInfoDto dto = new PhotoUploadInfoDto();
            dto.setServerType(serverType);
            dto.setFpath(path);
            request.setDto(dto);
            AddPhotoUploadInfoResponseType response = client.addPhotoUploadInfo(request);
            if (response != null) {
                return response.getPhotoId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long getPhotoIdByFpath(String path) {
        if (StringUtils.isBlank(path)) return null;
        try {
            GetPhotoIdByFpathRequestType request = new GetPhotoIdByFpathRequestType();
            request.setFpath(path);
            GetPhotoIdByFpathResponseType response = client_.getPhotoIdByFpath(request);
            if (response == null || response.getPhotoIdList() == null || response.getPhotoIdList().size() == 0) return null;
            return response.getPhotoIdList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhotoUrlById(Long id) {
        if (id == null || id == 0) return null;
        GetPhotoListRequestType request = new GetPhotoListRequestType();
        request.setPhotoIdList(Arrays.asList(id));
        try {
            GetPhotoListResponseType response = client_.getPhotoList(request);
            if (response != null && response.getResultList().size() > 0) {
                PhotoInfoDto info = response.getResultList().get(0);
                return info.getPhotoUrl().getBaseUrl();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
