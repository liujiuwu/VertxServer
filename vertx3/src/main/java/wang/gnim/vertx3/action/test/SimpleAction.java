package wang.gnim.vertx3.action.test;

import com.google.protobuf.InvalidProtocolBufferException;
import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;
import wang.gnim.vertx3.log.GameLogger;

@MsgIDSetter(msgID = MessageWrapper.MsgID.Test)
public class SimpleAction extends AbstractAction {

    @Override
	public TestMessage.TestResponse execute(byte[] obj) throws InvalidProtocolBufferException {
        TestMessage.TestRequest request = TestMessage.TestRequest.parseFrom(obj);

        GameLogger.log("Data : " + request.getData());

        return TestMessage.TestResponse.newBuilder()
                .setData(56)
                .build();
	}

}
