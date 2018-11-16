package Tool;

import junit.framework.TestCase;
import util.HttpClientUtil;

/**
 * Created by lilianga on 2018/11/7.
 */
public class PopularListDataTest extends TestCase {

    public void testHttpPostWithJson() throws Exception {
        String jsonStr = "{\"count\":500,\"districtId\":2,\"orderBy\":\"rankscore:desc\",\"platform\":0,\"poiType\":\"SIGHT\",\"start\":1}";
        String url = "http://webapi.soa.ctripcorp.com/api/14369/searchSightPoiList";
        //PopularListData.httpPostWithJson(jsonStr,url,"dsfafds4556s5");
        String result = HttpClientUtil.sendHttpPostJson(url, jsonStr);
    }
}