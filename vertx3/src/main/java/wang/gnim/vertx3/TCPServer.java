package wang.gnim.vertx3;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;

public class TCPServer {

	public void start() {
		Vertx vertx = Vertx.factory.vertx();
		
		HttpServerOptions options = new HttpServerOptions()
			.setAcceptBacklog(100);
		
		vertx.createNetServer(options)
		.connectHandler(new ConnectHandler())
		.listen(8080, new ListenHandler());
	}
	
	private class ListenHandler implements Handler<AsyncResult<NetServer>>{
		
		@Override
		public void handle(AsyncResult<NetServer> event) {
			if(event.succeeded())
				System.out.println("listen successed");
			else
				System.out.println(event.cause());
		}
	}
	
	private class ConnectHandler implements Handler<NetSocket> {
		@Override
		public void handle(NetSocket netSocket) {

			netSocket.closeHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
					System.out.println("closeHandler");
				}
			});
			
			netSocket.drainHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
					System.out.println("drainHandler");
				}
			});
			
			netSocket.endHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
					System.out.println("endHandler");
				}
			});
			
			netSocket.exceptionHandler(new Handler<Throwable>() {

				@Override
				public void handle(Throwable event) {
					System.out.println("exceptionHandler");
				}
			});
			
			netSocket.handler(new Handler<Buffer>() {

				@Override
				public void handle(Buffer event) {
					System.out.println("handler");
					netSocket.write("123");
				}
			});
		}
	}
}