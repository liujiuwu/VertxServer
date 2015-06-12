package test_vertx3;

import junit.framework.Assert;
import io.vertx.core.Vertx;

import org.junit.Test;

public class TestVertx {

	@Test
	public void test() {
		Vertx vertx1 = Vertx.factory.vertx();
		Vertx vertx2 = Vertx.factory.vertx();
		
		Assert.assertNotSame(vertx1, vertx2);
	}
}
