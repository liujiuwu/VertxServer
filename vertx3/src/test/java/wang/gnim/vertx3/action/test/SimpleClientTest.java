package wang.gnim.vertx3.action.test;

import org.junit.Test;

import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.action.ClientAction;

public class SimpleClientTest {

	@Test
	public void test() {
		MockServer.start();
		
		ClientAction.SimpleClient.publish("2123123");
		ClientAction.SimpleClient.send("asdasdads");
		
	}
}