package wang.gnim.vertx3.core.metrics;

import com.codahale.metrics.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/8/11.
 */
public class DropwizadMetrics {
    private final MetricRegistry metrics = new MetricRegistry();

    public Meter meter(String name) {
        Meter requests = metrics.meter(name);
        requests.mark();
        return requests;
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

    public void startConsoleReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }
}
