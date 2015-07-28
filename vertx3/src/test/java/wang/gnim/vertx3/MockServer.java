package wang.gnim.vertx3;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import wang.gnim.vertx3.net.ServerResource;
import wang.gnim.vertx3.vertx.Vertxs;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MockServer {

	public static void startLocalServer() {
        List<Class> actions = ServerResource.INSTANCE.getActions();
        CountDownLatch latch = new CountDownLatch(actions.size());
        for (final Class class1 : actions) {
            Vertxs.TCP.deployVerticle(class1.getCanonicalName(), new Handler<AsyncResult<String>>() {
                @Override
                public void handle(AsyncResult<String> event) {
                    if (event.succeeded()) {
                        latch.countDown();
                        System.out.println(class1.getCanonicalName() + "  部署成功");
                    } else {

                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startTcpServer() {

    }
}
