package wang.gnim.vertx3.PressureTest;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

/**
 * Created by wanggnim on 2015/7/3.
 */
public class TestTCP {

    public static void main(String[] args) {
        NetClient client = Vertx.factory.vertx().createNetClient();
        client.connect(8080, "localhost", new Handler<AsyncResult<NetSocket>>() {
                    @Override
                    public void handle(AsyncResult<NetSocket> event) {
                        if (event.succeeded()) {
                            NetSocket result = event.result();

                            result.write("Hello server");

                            result.handler(new Handler<Buffer>() {
                                @Override
                                public void handle(Buffer event) {
                                    System.out.println(event.getString(0, event.length()));
                                }
                            });
                        } else {
                            event.cause().printStackTrace();
                        }
                    }
                }

        );

        client.close();
        System.out.println("close");
    }
}
