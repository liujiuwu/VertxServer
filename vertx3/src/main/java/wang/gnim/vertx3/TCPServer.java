package wang.gnim.vertx3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import wang.gnim.vertx3.util.ClassFinder;

import java.util.List;

/**
 * Created by wanggnim on 2015/7/17.
 */
public class TCPServer extends AbstractVerticle {
	@Override
	public void start() {
		HttpServerOptions options = new HttpServerOptions().setAcceptBacklog(100);

		vertx.createNetServer(options).connectHandler(new ConnectHandler()).listen(9000, new ListenHandler());
	}

	private class ListenHandler implements Handler<AsyncResult<NetServer>> {

		@Override
		public void handle(AsyncResult<NetServer> event) {
			if (event.succeeded()) {
				System.out.println("TCP listen successed");
				deployAction();
			} else {
				event.cause().printStackTrace();
			}
		}

		public void deployAction() {

			DeploymentOptions options = new DeploymentOptions();
			List<Class> actions = ClassFinder.findClasses("wang.gnim.vertx3.clientAction", "");
			for (final Class class1 : actions) {
				if (!class1.getSuperclass().getSimpleName().equals("ClientAbstractAction"))
					continue;

				vertx.deployVerticle(class1.getCanonicalName(), options, new Handler<AsyncResult<String>>() {

					@Override
					public void handle(AsyncResult<String> event) {
						if (event.succeeded()) {
							System.out.println("deploy:" + class1.getCanonicalName());
						} else {
							System.out.println("faile deploy:" + class1.getCanonicalName());
						}
					}
				});
			}
		}
	}

	private class ConnectHandler implements Handler<NetSocket> {
		@Override
		public void handle(final NetSocket netSocket) {

			netSocket.closeHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {

				}
			});

			netSocket.drainHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
				}
			});

			netSocket.endHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
				}
			});

			netSocket.exceptionHandler(new Handler<Throwable>() {

				@Override
				public void handle(Throwable event) {
					event.printStackTrace();
				}
			});

			netSocket.handler(new Handler<Buffer>() {

				@Override
				public void handle(Buffer event) {
					System.out.println(event.getString(0, event.length()));
					netSocket.write("0");
				}
			});
		}
	}
}
