package wang.gnim.vertx3.clientCommand.metrics;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.HTTPServer;
import wang.gnim.vertx3.clientCommand.ClientAbstractCommand;

/**
 * Created by wanggnim on 2015/7/7.
 */
public class MetricCommand  extends ClientAbstractCommand {

    @Override
    public String execute(JsonObject obj) {
        MetricsService metricsService = HTTPServer.INSTANCE.getMetricsService();

        return metricsService.getMetricsSnapshot(HTTPServer.INSTANCE.getHttpServer()).toString();
    }
}
