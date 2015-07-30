package wang.gnim.vertx3.net;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import wang.gnim.protobuf.messages.Message;
import wang.gnim.vertx3.util.ByteUtil;
import wang.gnim.vertx3.util.PropertiesConfig;
import wang.gnim.vertx3.util.ServerResource;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by wanggnim on 2015/7/17.
 */
public class TCPServer extends AbstractVerticle {
	@Override
	public void start() {
		HttpServerOptions options = new HttpServerOptions().setAcceptBacklog(100);

		vertx.createNetServer(options)
                .connectHandler(new ConnectHandler())
                .listen(PropertiesConfig.TCP_PORT.intValue(), new ListenHandler());
	}

	private class ListenHandler implements Handler<AsyncResult<NetServer>> {

		@Override
		public void handle(AsyncResult<NetServer> event) {
			if (event.succeeded()) {
				deployAction();
			} else {
				event.cause().printStackTrace();
			}
		}

		public void deployAction() {

			DeploymentOptions options = new DeploymentOptions();
            List<Class> actions = ServerResource.INSTANCE.getActions();
            for (final Class class1 : actions) {

				vertx.deployVerticle(class1.getCanonicalName(), options, event -> {
                    if (event.succeeded()) {
                        System.out.println("deploy:" + class1.getCanonicalName());
                    } else {
                        System.out.println("faile deploy:" + class1.getCanonicalName());
                    }
                });
			}
		}
	}

	private class ConnectHandler implements Handler<NetSocket> {
		@Override
		public void handle(final NetSocket netSocket) {

			netSocket.closeHandler(event -> System.out.println("closeHandler"));

			netSocket.drainHandler(event -> System.out.println("drainHandler"));

			netSocket.endHandler(event -> System.out.println("endHandler"));

			netSocket.exceptionHandler(Throwable::printStackTrace);

			netSocket.handler(event ->
                route(netSocket, event)
            );
		}

        /**
         * 反射性能太差,考虑其他
         */
        private void route(NetSocket netSocket, Buffer event) {

            byte[] bytes = readBytesFromBuffer(event);
            try {
                Message.Request request = Message.Request.parseFrom(bytes);
                int msgID = request.getMsgId();
                String address = ServerResource.INSTANCE.getParserAddress(msgID);
                vertx.eventBus().publish(address, bytes);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }

        private byte[] readBytesFromBuffer(Buffer event) {
            ByteBuf byteBuf = event.getByteBuf();

            byte[] length = new byte[2];
            byteBuf.readBytes(length);
            short realLength = ByteUtil.bytes2Short(length);

            byte[] protos = new byte[realLength];
            byteBuf.readBytes(protos);

            return protos;
        }
	}
}
