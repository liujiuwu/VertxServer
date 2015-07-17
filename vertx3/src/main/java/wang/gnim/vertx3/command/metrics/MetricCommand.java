package wang.gnim.vertx3.command.metrics;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.HTTPServe;
import wang.gnim.vertx3.command.ClientAbstractCommand;

/**
 * Created by wanggnim on 2015/7/7.
 */
public class MetricCommand  extends ClientAbstractCommand {

    @Override
    public String execute(JsonObject obj) {
        MetricsService metricsService = HTTPServe.INSTANCE.getMetricsService();
//        metricsService.getMetricsSnapshot(HTTPServe.INSTANCE.getHttpServer()).toString()
        return "";
    }
}
