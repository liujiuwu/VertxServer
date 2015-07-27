package wang.gnim.vertx3.vertx;

import io.vertx.core.*;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.net.HTTPServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanggnim on 2015/7/27.
 */
public enum VertxManager {

    TCPSERVER {
        @Override
        public VertxOptions getVertxOptions() {
            VertxOptions vertxOptions = new VertxOptions();
            vertxOptions.setEventLoopPoolSize(1).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));
            return vertxOptions;
        }
    },
    HTTPSERVER {
        @Override
        public VertxOptions getVertxOptions() {
            VertxOptions vertxOptions = new VertxOptions();
            vertxOptions.setEventLoopPoolSize(1).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));
            return vertxOptions;
        }
    };

    VertxManager() {
        VertxOptions vertxOptions = getVertxOptions();
        vertx = Vertx.factory.vertx(vertxOptions);
        metricsService = MetricsService.create(vertx);
    }

    private final Vertx vertx;
    private MetricsService metricsService;
    private List<String> serverIds = new ArrayList<>();

    public abstract VertxOptions getVertxOptions();

    public MetricsService getMetricsService() {
        return metricsService;
    }

    public void deployVerticle(String name) {
        vertx.deployVerticle(name, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                if (event.succeeded()) {
                    serverIds.add(event.result());
                } else {

                }
            }
        });
    };

    public void undeploy(String deploymentID) {
        vertx.undeploy(deploymentID, new Handler<AsyncResult<Void>>() {
            @Override
            public void handle(AsyncResult<Void> event) {
                System.out.println(deploymentID + " 解除部署成功");
                undeploy(serverIds);
            }
        });
    };

    public void restartServer() {
        List<String> copyServerIds = new ArrayList<>();
        Collections.copy(serverIds, copyServerIds);
        serverIds.clear();

        undeploy(serverIds);
    }

    private void undeploy(List<String> copyServerIds) {
        if (copyServerIds.size() > 0) {
            String deploymentID = copyServerIds.remove(0);
            undeploy(deploymentID);
        }
    }

    public EventBus eventBus() {
        return vertx.eventBus();
    }
}
