package wang.gnim.vertx3.ThirdPartyTest.vertx;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import io.vertx.core.Vertx;

public class TestVertx {

	@Test
	public void testFacoty() {
		Vertx vertx1 = Vertx.factory.vertx();
		Vertx vertx2 = Vertx.factory.vertx();
		
		Assert.assertNotSame(vertx1, vertx2);
		
		Assert.assertNotSame(vertx1.eventBus(), vertx2.eventBus());
	}

    @Test
    public void test() {
        Vertx vertx1 = Vertx.vertx();
        Vertx vertx2 = Vertx.vertx();

        Assert.assertNotSame(vertx1, vertx2);

        Assert.assertNotSame(vertx1.eventBus(), vertx2.eventBus());
    }

    @Test
    public void testEventBus() {
        Vertx vertx1 = Vertx.factory.vertx();
        Vertx vertx2 = Vertx.factory.vertx();

        vertx1.eventBus().consumer("target", event -> System.out.println("recive:" + event.body()));
        vertx2.eventBus().publish("target", "hi");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
