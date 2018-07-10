package soaproxy;

import com.ctrip.basebiz.data.recommendation.idl.SunflowerBibleServiceClient;
import com.ctrip.platform.ai.query.common.DestQueryDTO;
import com.ctrip.platform.ai.query.common.DestQueryResultDTO;

/**
 * Created by lilianga on 2018/7/10.
 */
public class SunflowerBibleSOAProxy {
    private static SunflowerBibleSOAProxy _instance;

    public static SunflowerBibleSOAProxy getInstance() {
        if (_instance == null) {
            _instance = new SunflowerBibleSOAProxy();
        }
        return _instance;
    }

    private static SunflowerBibleServiceClient client;

    static {
        client = SunflowerBibleServiceClient.getInstance();
    }

    public DestQueryResultDTO queryDest(DestQueryDTO request) {
        DestQueryResultDTO result = null;
        try {
            result = client.queryDest(request);
            if (result == null || result.getResult() == null || result.getResult().size() == 0) return null;
            result.getResult().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
