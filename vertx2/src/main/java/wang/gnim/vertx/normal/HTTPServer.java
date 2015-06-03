package wang.gnim.vertx.normal;

import java.util.Map.Entry;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class HTTPServer extends Verticle {

	@Override
	public void start() {
		
		HttpServer server = vertx.createHttpServer();
		
		requestHandler(server);

		listen(server);
		
	}

	/**
	 * @param server
	 */
	private void listen(HttpServer server) {
		server.listen(8080,"localhost", new Handler<AsyncResult<HttpServer>>() {

			public void handle(AsyncResult<HttpServer> event) {
				if (event.succeeded()) {
					System.out.println("Http服务器启动成功：" + 8080);
					
				} else {
					event.cause().printStackTrace();
				}
			}
		});
	}

	/**
	 * @param server
	 */
	private void requestHandler(HttpServer server) {
		server.requestHandler(new Handler<HttpServerRequest>() {
			
			public void handle(final HttpServerRequest event) {
				
				JsonObject jo = new JsonObject();
				for (Entry<String, String> entry : event.params().entries()) {
					jo.putString(entry.getKey(), entry.getValue());
				}

				vertx.eventBus().send(event.path(), jo.encode(), new Handler<Message<String>>() {

					public void handle(Message<String> reply) {
						
						HttpServerResponse response = event.response();
						response.setChunked(true);
						
						response.write(reply.body());
						
						response.end();
					}
				});
			}
		});
	}
	
}
