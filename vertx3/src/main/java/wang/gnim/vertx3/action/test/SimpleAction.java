package wang.gnim.vertx3.action.test;

import com.google.protobuf.InvalidProtocolBufferException;
import wang.gnim.protobuf.messages.Message;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;

@MsgIDSetter(msgID = Message.MsgID.Test)
public class SimpleAction extends AbstractAction {

    @Override
	public void execute(byte[] obj) throws InvalidProtocolBufferException {
        TestMessage.TestRequest message = TestMessage.TestRequest.parseFrom(obj);
        System.out.println("Data : " + message.getData());
	}

}
