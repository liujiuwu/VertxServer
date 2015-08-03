package wang.gnim.vertx3.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Snapshot;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import wang.gnim.vertx3.util.JsonOutput;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/18.
 */
public class MetricManagerTest {

    @Before
    @Ignore
    public void testStartConsoleReporter_ok() throws InterruptedException {
        Metrics.TCP.startConsoleReporter();
    }

    @Test
    @Ignore
    public void testMeter() {
        Meter meter = Metrics.TCP.meter("meter");

        JsonOutput.builder()
                .append("Meter Count", meter.getCount())
                .append("Meter FifteenMinuteRate" , meter.getFifteenMinuteRate())
                .append("Meter FiveMinuteRate" , meter.getFiveMinuteRate())
                .append("Meter OneMinuteRate" , meter.getOneMinuteRate())
                .append("Meter MeanRate" , meter.getMeanRate())
                .printConsole();
    }

    @Test
    @Ignore
    public void testCounter() {
        Counter counter = Metrics.TCP.counter(BeTested.class, "counter");

        counter.inc();
        JsonOutput.builder()
                .append("Counter Count", counter.getCount())
                .printConsole();

        counter.dec();
        JsonOutput.builder()
                .append("Counter Count", counter.getCount())
                .printConsole();
    }

    @Test
    @Ignore
    public void testHistogram() throws InterruptedException {
        Histogram histogram = Metrics.TCP.histogram(BeTested.class, "histogram");

        Random random = new Random();
        for (int i = 1; i < 10; i++) {
            histogram.update(i);
            int sleep = random.nextInt(i);
            TimeUnit.SECONDS.sleep(i);
        }

        Snapshot snapshot = histogram.getSnapshot();

        JsonOutput.builder()
                .append("Meter Count:", histogram.getCount())
                .append("75thPercentile:", snapshot.get75thPercentile())
                .append("95thPercentile:", snapshot.get95thPercentile())
                .append("98thPercentile:", snapshot.get98thPercentile())
                .append("999thPercentile:", snapshot.get999thPercentile())
                .append("99thPercentile:", snapshot.get99thPercentile())
                .append("Max:", snapshot.getMax())
                .append("Mean:", snapshot.getMean())
                .append("Median:", snapshot.getMedian())
                .append("Min:", snapshot.getMin())
                .append("StdDev:", snapshot.getStdDev())
                .append("size:", snapshot.size())
                .printConsole();
    }

    @Test
    public void testTimer() {

    }

    @Test
    public void test() {

    }

}

class BeTested {

}