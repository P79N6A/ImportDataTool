package soaproxy;

import com.ctrip.gs.bestpractise.ThreadContext;
import com.ctrip.soa.hotel.queryws.productservice.v1.*;
import com.google.common.collect.Lists;

import java.util.Arrays;

/**
 * Created by yapingchen on 2018-05-22.
 */
public class HotelProductServiceSOAProxy {
    private static final HotelProductServiceSOAProxy _instance = new HotelProductServiceSOAProxy();

    public static HotelProductServiceSOAProxy getInstance() {
        return _instance;
    }

    private static final HotelProductServiceClient client;

    static {
        client = HotelProductServiceClient.getInstance();
    }

    public HotelAllDataEntity getHotelStaticInfo(long hotelId) {
        try {
            GetHotelStaticInfoRequestType request = new GetHotelStaticInfoRequestType();
            request.setHotelID(Lists.newArrayList((int) hotelId));
            request.setReturnDataTypeList(Lists.newArrayList(
                    ReturnDataType.HotelSimpleEntity,
                    ReturnDataType.HotelCommentEntity,
                    ReturnDataType.CoordinateEntity,
                    ReturnDataType.Hotel_HotelBasicRoomPictureEntityList,
                    ReturnDataType.BasicRoomType_HotelBasicRoomPictureEntityList
            ));

            GetHotelStaticInfoResponseType response = client.getHotelStaticInfo(request);
            if (response != null && response.getHotelDataList() != null && response.getHotelDataList().size() > 0) {
                return response.getHotelDataList().get(0);
            }
        } catch (Exception ex) {
            ThreadContext.getCurrent().getErrorLogger("HotelProductServiceSOAProxy.getHotelStaticInfo").appendError(ex).push();
        }
        return null;
    }

    public GetHotelListResponseType getHotelListByCityId(Integer cityId, int pageIndex) {
        try {
            GetHotelListRequestType request = new GetHotelListRequestType();
            request.setCity(Arrays.asList(cityId));
            request.setMasterAndSubMode(Arrays.asList(MasterAndSubModeType.MasterHotel));
            request.setPageSize(100);
            request.setPageIndex(pageIndex);
            GetHotelListResponseType response = client.getHotelList(request);
            if (response != null && response.getTotalCount() > 0) {
                return response;
            }
        } catch (Exception ex) {
            ThreadContext.getCurrent().getErrorLogger("HotelProductServiceSOAProxy.getHotelList").appendError(ex).push();
        }
        return null;
    }


}
