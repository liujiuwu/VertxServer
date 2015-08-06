package wang.gnim.vertx3.action;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import io.vertx.core.AbstractVerticle;

/**
 * 利用泛型,可以在实现类中直接设置可解析类型
 *
 * 利用ASM代替反射提高解析效率
 */
public abstract class AbstractAction<T extends MessageOrBuilder> extends AbstractVerticle {

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

    private AbstractMessageLite execute(byte[] obj) throws InvalidProtocolBufferException {


        return execute(obj);
    }

	public abstract AbstractMessageLite execute(T obj);

}
