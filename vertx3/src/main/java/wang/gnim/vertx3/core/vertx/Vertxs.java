package wang.gnim.vertx3.core.vertx;

import com.google.protobuf.AbstractMessageLite;
import io.vertx.core.*;
import io.vertx.core.eventbus.Message;
import io.vertx.core.net.NetClient;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.vertx3.log.GameLogger;
import wang.gnim.vertx3.util.ServerResource;

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
                GameLogger.log("Verticle部署成功：" + name);
            } else {
                GameLogger.log("Verticle部署失败：" + name);
            }
        });
    }

    public void deployVerticle(String name, Handler<AsyncResult<String>> completionHandler) {
        vertx.deployVerticle(name, completionHandler);
    }

    public void eventBusPublish(MessageWrapper.MsgID address, Object message) {
        String sendAddress = ServerResource.INSTANCE.getParserAddress(address);
        vertx.eventBus().publish(sendAddress, message);
    }

    /**
     * 发送消息,不接受返回值
     */
    public void eventBusSend(MessageWrapper.MsgID address, Object message) {
        String sendAddress = ServerResource.INSTANCE.getParserAddress(address);
        vertx.eventBus().send(sendAddress, message);
    }

    /**
     * 发送消息,接受返回值
     */
    public void eventBusSend(MessageWrapper.MsgID address, Object message,
                             Handler<AsyncResult<Message<byte[]>>> replyHandler) {
        String sendAddress = ServerResource.INSTANCE.getParserAddress(address);
        vertx.eventBus().send(sendAddress, message, replyHandler);
    }

    public void undeploy(String deploymentID, Handler<AsyncResult<Void>> handler) {
        vertx.undeploy(deploymentID, handler);
    }

    public NetClient createTCPClient() {
        return vertx.createNetClient();
    }

    public MetricsService createMetricsService() {
        return MetricsService.create(vertx);
    }

    public Vertx vertx() {
        return vertx;
    }
}
