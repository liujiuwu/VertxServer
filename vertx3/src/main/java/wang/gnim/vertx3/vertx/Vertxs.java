package wang.gnim.vertx3.vertx;

import io.vertx.core.*;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;

/**
 * Created by wanggnim on 2015/7/27.
 */
public enum Vertxs {

    TCP {

        @Override
        public VertxOptions getVertxOptions() {
            DropwizardMetricsOptions metricsOptions = new DropwizardMetricsOptions().setEnabled(true);
            VertxOptions vertxOptions = new VertxOptions()
                    .setEventLoopPoolSize(1)    // 每个Vertx实例只有一个单线程执行verticle,从底层避免并发危险
                    .setWorkerPoolSize(4)   // 工作者线程用于执行DB操作,不会修改共享数据
                    .setMetricsOptions(metricsOptions);

            return vertxOptions;
        }
    },
    HTTP {

        @Override
        public VertxOptions getVertxOptions() {
            DropwizardMetricsOptions metricsOptions = new DropwizardMetricsOptions().setEnabled(true);
            VertxOptions vertxOptions = new VertxOptions()
                    .setEventLoopPoolSize(1)
                    .setWorkerPoolSize(4)
                    .setMetricsOptions(metricsOptions);

            return vertxOptions;
        }
    };

    Vertxs() {
        VertxOptions vertxOptions = getVertxOptions();
        vertx = Vertx.factory.vertx(vertxOptions);
        metricsService = MetricsService.create(vertx);
    }

    private final Vertx vertx;
    private MetricsService metricsService;


    public abstract VertxOptions getVertxOptions();

    public MetricsService getMetricsService() {
        return metricsService;
    }

    public void deployVerticle(String name) {
        vertx.deployVerticle(name, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                if (event.succeeded()) {
                    System.out.println("Verticle部署成功：" + name);
                } else {
                    System.out.println("Verticle部署失败：" + name);
                }
            }
        });
    };

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
}
