package soaproxy;

import com.ctrip.platform.ai.query.common.DestQueryDTO;
import com.ctrip.platform.ai.query.common.DestQueryResultDTO;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by lilianga on 2018/7/11.
 */
public class SunflowerBibleSOAProxyTest extends TestCase {

    public void testQueryDest() throws Exception {
        DestQueryDTO request = new DestQueryDTO();
        request.setAppId("common");
        request.setVersion("713.000");
        request.setStartCityId(11L);
        request.setDestCityId(11L);
        request.setFields(Arrays.asList("id,name,themes".split(",")));
        DestQueryResultDTO result = SunflowerBibleSOAProxy.getInstance().queryDest(request);
        System.out.println(result.getResult().size());
    }
}