package wang.gnim.vertx3.net;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
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

			netSocket.handler(event -> {
                byte[] bytes = event.getBytes(0, event.length());
                netSocket.write("revice");
            });
		}

        /**
         * 减少GC
         * 反射性能太差,考虑其他
         */
        private void route() {

        }
	}
}
