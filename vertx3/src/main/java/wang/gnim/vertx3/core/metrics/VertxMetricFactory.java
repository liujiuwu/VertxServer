package wang.gnim.vertx3.core.metrics;

import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.core.metrics.impl.NetServerMetric;
import wang.gnim.vertx3.core.metrics.impl.VertxMetric;
import wang.gnim.vertx3.core.net.ServerCache;
import wang.gnim.vertx3.core.vertx.Vertxs;


/**
 * 服务器监控
 *
 * Created by wanggnim on 2015/7/18.
 */
public enum VertxMetricFactory {

    TCP(Vertxs.TCP_SERVER),
    HTTP(Vertxs.HTTP_SERVER);

    private MetricsService metricsService;
    private Vertxs vertxs;

    VertxMetricFactory(Vertxs vertxs) {
        metricsService = MetricsService.create(vertxs.vertx());
        this.vertxs = vertxs;
    }

    public VertxMetric getVertxMetrics() {
        JsonObject metric = metricsService.getMetricsSnapshot(vertxs.vertx());
        return new VertxMetric(metric);
    }

    public NetServerMetric getTCPMetric() {
        NetServer tcpServer = ServerCache.INSTANCE.getTcpServer();
        JsonObject metric = metricsService.getMetricsSnapshot(tcpServer);
        return new NetServerMetric(metric);
    }

    public JsonObject getHTTPMetrics() {
        HttpServer tcpServer = ServerCache.INSTANCE.getHTTPServer();
        JsonObject metric = metricsService.getMetricsSnapshot(tcpServer);
        return null;
    }

}
