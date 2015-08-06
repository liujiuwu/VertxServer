package wang.gnim.vertx3;

import wang.gnim.vertx3.log.GameLogger;
import wang.gnim.vertx3.util.ServerResource;
import wang.gnim.vertx3.core.net.Servers;
import wang.gnim.vertx3.core.vertx.Vertxs;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MockServer {

    static {
        ServerResource.INSTANCE.init();
    }

	public static void startLocalServer() {
        Collection<String> actions = ServerResource.INSTANCE.getActionNames();
        CountDownLatch latch = new CountDownLatch(actions.size());
        for (final String class1 : actions) {
            Vertxs.TCP_SERVER.deployVerticle(class1, event -> {
                if (event.succeeded()) {
                    GameLogger.log(class1 + "  部署成功");
                } else {
                    GameLogger.log(class1 + "  部署失败");
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startTcpServer() {
        Servers.TCP.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
