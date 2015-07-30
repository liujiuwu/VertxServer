package wang.gnim.vertx3;

import wang.gnim.vertx3.util.ServerResource;
import wang.gnim.vertx3.net.Servers;
import wang.gnim.vertx3.vertx.Vertxs;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MockServer {

	public static void startLocalServer() {
        List<Class> actions = ServerResource.INSTANCE.getActions();
        CountDownLatch latch = new CountDownLatch(actions.size());
        for (final Class class1 : actions) {
            Vertxs.TCP_SERVER.deployVerticle(class1.getCanonicalName(), event -> {
                if (event.succeeded()) {
                    latch.countDown();
                    System.out.println(class1.getCanonicalName() + "  部署成功");
                } else {

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
        ServerResource.INSTANCE.getActions();
        Servers.TCP.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
