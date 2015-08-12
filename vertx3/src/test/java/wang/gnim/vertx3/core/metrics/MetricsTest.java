package wang.gnim.vertx3.core.metrics;

import org.junit.Test;
import wang.gnim.vertx3.core.metrics.impl.NetServerMetric;
import wang.gnim.vertx3.core.metrics.impl.VertxMetric;
import wang.gnim.vertx3.core.net.ServerStarter;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/18.
 */
public class MetricsTest {


    @Test
    public void testVertxMetricsSnapshot() {
        VertxMetric vertxMetricsSnapshot = VertxMetricFactory.TCP.getVertxMetrics();

        System.out.println(vertxMetricsSnapshot.toPrettyString());
        System.out.println(vertxMetricsSnapshot.getVerticles());
    }

    @Test
    public void testTCPMetric() {
        ServerStarter.INSTANCE.startTCPServer();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NetServerMetric metric = VertxMetricFactory.TCP.getTCPMetric();
        System.out.println(metric.getBytesWritten());
    }
}
