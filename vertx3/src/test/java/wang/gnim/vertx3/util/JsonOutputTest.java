package wang.gnim.vertx3.util;

import org.junit.Test;

/**
 * Created by wanggnim on 2015/7/18.
 */
public class JsonOutputTest {

    @Test
    public void test_ok() {
        JsonOutput jo = new JsonOutput();
        jo.append("s_key", 1).printConsole();

    }
}
