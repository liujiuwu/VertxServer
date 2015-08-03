package wang.gnim.vertx3.core.metrics;

import com.codahale.metrics.*;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.core.vertx.Vertxs;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务器监控
 *
 * Created by wanggnim on 2015/7/18.
 */
public enum Metrics {

    TCP(Vertxs.TCP_SERVER),
    HTTP(Vertxs.HTTP_SERVER);

    private final MetricRegistry metrics = new MetricRegistry();
    private final HealthCheckRegistry healthChecks = new HealthCheckRegistry();

    private MetricsService metricsService;
    private Vertxs vertxs;

    Metrics(Vertxs vertxs) {
        metricsService = vertxs.createMetricsService();
        this.vertxs = vertxs;
    }

    public JsonObject getMetricsSnapshot() {
        // TODO 等待后期vertx版本更新
        return metricsService.getMetricsSnapshot(vertxs.vertx());
    }

    public Meter meter(String name) {
        Meter requests = metrics.meter(name);
        requests.mark();
        return requests;
    }

    public void register() {

    }

    public Counter counter(Class clazz, String name) {
        final Counter counter = metrics.counter(MetricRegistry.name(clazz, name));
        return counter;
    }

    public Histogram histogram(Class clazz, String name) {
        final Histogram histogram = metrics.histogram(MetricRegistry.name(clazz, name));
        return histogram;
    }

    public Timer timer(Class clazz, String name) {
        final Timer timer = metrics.timer(MetricRegistry.name(clazz, name));
        return timer;
    }

    public void addMysqlHealthCheck() {
        healthChecks.register("mysql", new DatabaseHealthCheck());
    }

    public void runHealthChecks() {
        final Map<String, HealthCheck.Result> results = healthChecks.runHealthChecks();
        for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
            if (entry.getValue().isHealthy()) {
                System.out.println(entry.getKey() + " is healthy");
            } else {
                System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                final Throwable e = entry.getValue().getError();
                if (e != null) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startConsoleReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

}