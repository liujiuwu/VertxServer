package wang.gnim.vertx3.action;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import io.vertx.core.AbstractVerticle;
import wang.gnim.protobuf.messages.TestMessage;

public abstract class AbstractAction extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getCanonicalName();
		vertx.eventBus().consumer(name, event -> {
            try {
                AbstractMessageLite reMessage = execute((byte[]) event.body());
                event.reply(reMessage.toByteArray());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        });
	}

	public abstract AbstractMessageLite execute(byte[] obj) throws InvalidProtocolBufferException;

}
