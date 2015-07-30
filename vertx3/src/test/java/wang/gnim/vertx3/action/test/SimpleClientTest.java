package wang.gnim.vertx3.action.test;

import org.junit.Test;

import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.vertx.Vertxs;

import java.util.concurrent.TimeUnit;

public class SimpleClientTest {

	@Test
	public void test() {
		MockServer.startLocalServer();

        TestMessage.TestRequest request = TestMessage.TestRequest.newBuilder()
                .setData(12)
                .build();

         Vertxs.TCP_SERVER.eventBusPublish(SimpleAction.class, request.toByteArray());

    }
}
