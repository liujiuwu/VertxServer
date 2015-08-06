package wang.gnim.vertx3.action;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import io.vertx.core.AbstractVerticle;

/**
 * 利用泛型,可以在实现类中直接设置可解析类型
 *
 */
public abstract class AbstractAction extends AbstractVerticle {

	@Override
	public void start() throws Exception {

        MsgIDSetter msgId = getClass().getAnnotation(MsgIDSetter.class);
		vertx.eventBus().consumer(msgId.msgID().name(), event -> {
            try {
                AbstractMessageLite reMessage = execute((byte[]) event.body());
                event.reply(reMessage.toByteArray());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        });
	}

    public abstract AbstractMessageLite execute(byte[] obj) throws InvalidProtocolBufferException ;

}
