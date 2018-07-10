package soaproxy;

import BO.DistrictCityMapDO;
import BO.DistrictCityParentDO;
import com.ctrip.gs.bestpractise.ThreadContext;
import com.ctrip.gs.destination.business.soa.service.contract.*;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lilianga on 2018/7/10.
 */
public class DestBusinessSOA4JavaProxy {
    private static DestBusinessSOA4JavaProxy _instance = null;

    public static DestBusinessSOA4JavaProxy getInstance() {
        if (_instance == null) {
            _instance = new DestBusinessSOA4JavaProxy();
        }
        return _instance;
    }

    private static DestBusinessServiceClient _client = null;

    static {
        _client = DestBusinessServiceClient.getInstance();
        _client.setFormat("json");
    }


    public long getCityIdByDistrictId(long districtId) {
        List<DistrictCityMapDO> mappingList = getDistrictCityMappingByDistrictId(districtId);
        if (mappingList != null && !mappingList.isEmpty()) {
            for (DistrictCityMapDO map : mappingList) {
                if (!"base".equals(map.getType()))
                    continue;
                if (map.getCategoryId() == 3) {
                    return map.getGlobalId();
                } else if (map.getParentList() != null && !map.getParentList().isEmpty()) {
                    return getCityMapItemParentCityId(map.getParentList());
                }
            }
        }
        return 0L;
    }

    private long getCityMapItemParentCityId(List<DistrictCityParentDO> parentList) {
        if (parentList == null || parentList.isEmpty())
            return 0;
        List<DistrictCityParentDO> reverseList = Lists.reverse(parentList);
        for (DistrictCityParentDO parent : reverseList) {
            if (parent.getCategoryId() == 3) {
                return parent.getGlobalId();
            }
        }
        return 0;
    }

    public List<DistrictCityMapDO> getDistrictCityMappingByDistrictId(long districtId) {
        try {
            GetDistrictCityMappingRequestType req = new GetDistrictCityMappingRequestType();
            req.setDistrictId(districtId);
            GetDistrictCityMappingResponseType response = _client.getDistrictCityMapping(req);
            if (response != null && response.getMappingList() != null && response.getMappingList().size() > 0) {
                List<DistrictCityMapDO> resultList = new ArrayList<>();
                for (DistrictCityMap map : response.getMappingList()) {
                    DistrictCityMapDO result = new DistrictCityMapDO();
                    result.setGlobalId(map.getGlobalId());
                    result.setCategoryId(map.getCategoryId());
                    result.setType(map.getType());
                    result.setName(map.getName());
                    result.setEName(map.getEname());
                    if (map.getParentList() != null && !map.getParentList().isEmpty()) {
                        List<DistrictCityParentDO> parent = new ArrayList<>();
                        for (DistrictCityParentMap parentMap : map.getParentList()) {
                            DistrictCityParentDO parentItem = new DistrictCityParentDO();
                            parentItem.setGlobalId(parentMap.getGlobalId());
                            parentItem.setCategoryId(parentMap.getCategoryId());
                            parentItem.setType(parentMap.getType());
                            parentItem.setName(parentMap.getName());
                            parentItem.setEName(parentMap.getEname());
                            parent.add(parentItem);
                        }
                        result.setParentList(parent);
                    }
                    resultList.add(result);
                }
                return resultList;
            }
        } catch (Exception ex) {
            ThreadContext.getCurrent().getErrorLogger("getDistrictCityMappingByDistrictId").appendError(ex).push();
        }
        return null;
    }
}
