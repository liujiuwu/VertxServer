package wang.gnim.vertx3.core.net;

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
import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.vertx3.core.vertx.Vertxs;
import wang.gnim.vertx3.log.GameLogger;
import wang.gnim.vertx3.util.ByteUtil;
import wang.gnim.vertx3.util.PropertiesConfig;
import wang.gnim.vertx3.util.ServerResource;

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

                Vertxs.LOGIC.deployVerticle(class1.getCanonicalName(), options, event -> {
                    if (event.succeeded()) {
                        GameLogger.log("deploy:" + class1.getCanonicalName());
                    } else {
                        GameLogger.log("faile deploy:" + class1.getCanonicalName());
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

        private void route(NetSocket netSocket, Buffer event) {

            byte[] bytes = readBytesFromBuffer(event);
            try {
                MessageWrapper.Request request = MessageWrapper.Request.parseFrom(bytes);
                Vertxs.LOGIC.eventBusPublish(request.getMsgId(), bytes);
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
