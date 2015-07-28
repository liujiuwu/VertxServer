package wang.gnim.vertx3.action.test;

import org.junit.Test;

import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.vertx.Vertxs;

import java.util.concurrent.TimeUnit;

public class SimpleClientTest {

	@Test
	public void test() {
		MockServer.startLocalServer();
        Vertxs.TCP.eventBusPublish(SimpleAction.class, "hello");
	}
}
