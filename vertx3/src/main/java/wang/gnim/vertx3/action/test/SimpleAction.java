package wang.gnim.vertx3.action.test;

import wang.gnim.protobuf.messages.Message;
import wang.gnim.protobuf.messages.TestMessage;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;

@MsgIDSetter(msgID = Message.MsgID.Test)
public class SimpleAction extends AbstractAction {

	@Override
	public void execute(String obj) {
		System.out.println(obj);
	}

}
