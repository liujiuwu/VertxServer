package wang.gnim.vertx3.core.metrics;

import com.alibaba.fastjson.JSON;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Snapshot;
import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import wang.gnim.vertx3.core.metrics.Metrics;
import wang.gnim.vertx3.core.metrics.impl.NetServerMetric;
import wang.gnim.vertx3.core.metrics.impl.VertxMetric;
import wang.gnim.vertx3.core.net.ServerStarter;
import wang.gnim.vertx3.util.JsonOutput;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/18.
 */
public class MetricsTest {


    @Test
    public void testVertxMetricsSnapshot() {
        VertxMetric vertxMetricsSnapshot = Metrics.TCP.getVertxMetrics();

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
        NetServerMetric metric = Metrics.TCP.getTCPMetric();
        System.out.println(metric.getBytesWritten());
    }
}
