package wang.gnim.vertx3.PressureTest;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.junit.Before;
import org.junit.Test;
import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.net.Servers;
import wang.gnim.vertx3.util.PropertiesConfig;
import wang.gnim.vertx3.vertx.Vertxs;

/**
 * Created by wanggnim on 2015/7/3.
 */
public class TestTCP {

    @Before
    public void start() {
        MockServer.startTcpServer();
    }

    @Test
    public void teset() {
        NetClient client = Vertxs.TCP_CLIENT.createTCPClient();
        client.connect(PropertiesConfig.TCP_PORT.intValue(), "localhost", event ->
                event.result().write("123"));
    }
}
