package wang.gnim.vertx3.action.test;

import org.junit.Test;

import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.MockServer;
import wang.gnim.vertx3.core.vertx.Vertxs;

import java.util.concurrent.TimeUnit;

public class SimpleClientTest {

	@Test
	public void test() {
		MockServer.startLocalServer();

        TestMessage.TestRequest request = TestMessage.TestRequest.newBuilder()
                .setData(12)
                .build();

         Vertxs.TCP_SERVER.eventBusSendReply(MessageWrapper.MsgID.Test, request.toByteArray());

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
