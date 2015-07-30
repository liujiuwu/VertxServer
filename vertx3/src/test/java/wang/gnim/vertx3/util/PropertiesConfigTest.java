package wang.gnim.vertx3.util;

import org.junit.Test;

/**
 * Created by wanggnim on 2015/7/28.
 */
public class PropertiesConfigTest {

    @Test
    public void test_ok() {

        int port = PropertiesConfig.TCP_PORT.intValue();
        System.out.println(port);
    }
}
