package wang.gnim.vertx3.PressureTest;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.junit.Before;
import org.junit.Test;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.net.Servers;
import wang.gnim.vertx3.util.PropertiesConfig;
import wang.gnim.vertx3.vertx.Vertxs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        connect(PropertiesConfig.TCP_PORT.intValue(), "localhost");

    }

    private void connect(int port, String host) {
        AsyncResult result = null;
        CountDownLatch latch = new CountDownLatch(1);
        NetClient client = Vertxs.TCP_CLIENT.createTCPClient();
        client.connect(port, host, new Handler<AsyncResult<NetSocket>>() {
                    @Override
                    public void handle(AsyncResult<NetSocket> event) {
                        NetSocket result = event.result();

                        long start = System.currentTimeMillis();

                        TestMessage.TestRequest request = TestMessage.TestRequest.newBuilder()
                                .setData(12)
                                .build();
                        for (int i = 0; i < 100000; i++) {
                            result.write("123");
                        }

                        long end = System.currentTimeMillis();

                        System.out.println(end - start);

                        result.handler(event1 -> {
                            latch.countDown();
                        });
                    }
                }
        );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
