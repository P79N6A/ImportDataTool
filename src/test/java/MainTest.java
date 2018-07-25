import org.junit.Test;

/**
 * Created by lilianga on 2018/6/15.
 */
public class MainTest {

    @Test
    public void testPhotoId() {
        String image = "https://youimg1.c-ctrip.com/target/ZA0l0i0000009bwvlC1A6.jpg";
        if (image.contains("/images/")){
            System.out.println(image.substring(image.indexOf("/images/")+7));
        }else if (image.contains("/target/")){
            System.out.println(image.substring(image.indexOf("/target/")+7));
        }
    }
}
