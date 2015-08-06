package wang.gnim.vertx3.action.test;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;
import wang.gnim.vertx3.log.GameLogger;

@MsgIDSetter(msgID = MessageWrapper.MsgID.Test)
public class SimpleAction extends AbstractAction<TestMessage.TestRequest> {

    @Override
	public TestMessage.TestResponse execute(TestMessage.TestRequest obj) {
        GameLogger.log("Data : " + obj.getData());

        return TestMessage.TestResponse.newBuilder()
                .setData(56)
                .build();
	}

}
