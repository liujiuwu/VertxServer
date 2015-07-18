package wang.gnim.vertx3.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/18.
 */
public enum MetricManager {

    INSTANCE;

    private static final MetricRegistry metrics = new MetricRegistry();

    public void start() {
        startConsoleReporter();
        Meter requests = metrics.meter("requests");
        requests.mark();
    }

    public void startConsoleReporter() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

}
