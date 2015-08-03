package wang.gnim.vertx3.vertx;

import io.vertx.core.*;
import io.vertx.core.net.NetClient;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;

/**
 *
 * Created by wanggnim on 2015/7/27.
 */
public enum Vertxs {

    TCP_SERVER,
    HTTP_SERVER,
    TCP_CLIENT
    ;

    Vertxs() {
        DropwizardMetricsOptions metricsOptions = new DropwizardMetricsOptions().setEnabled(true);
        VertxOptions vertxOptions = new VertxOptions()
                .setEventLoopPoolSize(1)    // 每个Vertx实例只有一个单线程执行verticle,从底层避免并发危险
                .setWorkerPoolSize(4)       // 工作者线程用于执行DB操作,不会修改共享数据
                .setMetricsOptions(metricsOptions);

        vertx = Vertx.factory.vertx(vertxOptions);

    }

    private final Vertx vertx;

    public void deployVerticle(String name) {
        vertx.deployVerticle(name, event -> {
            if (event.succeeded()) {
                System.out.println("Verticle部署成功：" + name);
            } else {
                System.out.println("Verticle部署失败：" + name);
            }
        });
    }

    public void deployVerticle(String name, Handler<AsyncResult<String>> completionHandler) {
        vertx.deployVerticle(name, completionHandler);
    }

    public void eventBusPublish(Class address, Object message) {
        vertx.eventBus().publish(address.getCanonicalName(), message);
    }

    public void eventBusPublish(String address, Object message) {
        vertx.eventBus().publish(address, message);
    }

    public void eventBusSend(String address, Object message) {
        vertx.eventBus().send(address, message);
    };

    public void undeploy(String deploymentID, Handler<AsyncResult<Void>> handler) {
        vertx.undeploy(deploymentID, handler);
    }

    public NetClient createTCPClient() {
        return vertx.createNetClient();
    }

    public MetricsService createMetricsService() {
        return MetricsService.create(vertx);
    }
}
