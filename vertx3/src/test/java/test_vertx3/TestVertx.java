package test_vertx3;

import io.vertx.core.Vertx;

import org.junit.Assert;
import org.junit.Test;

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
}
