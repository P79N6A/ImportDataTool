package soaproxy;

import junit.framework.TestCase;

/**
 * Created by lilianga on 2018/6/15.
 */
public class PhotoSoaProxyTest extends TestCase {

    public void testGetUploadPhotoId() throws Exception {
        Long imageId = PhotoSoaProxy.getInstance().getUploadPhotoId("/1005070000002syam4814.jpg", 16);
        System.out.println(imageId);
    }
}