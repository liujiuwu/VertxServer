package wang.gnim.vertx3.command.metrics;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.command.AbstractCommand;
import wang.gnim.vertx3.core.vertx.Vertxs;

/**
 * Created by wanggnim on 2015/7/7.
 */
public class MetricCommand  extends AbstractCommand {

    @Override
    public String execute(JsonObject obj) {
//        MetricsService metricsService = Vertxs.HTTP_SERVER.getMetricsService();
        return "";
    }
}
