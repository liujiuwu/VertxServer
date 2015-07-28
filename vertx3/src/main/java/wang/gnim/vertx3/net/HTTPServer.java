package wang.gnim.vertx3.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import wang.gnim.vertx3.util.ClassFinder;

import java.util.List;

/**
 *
 * Created by wanggnim on 2015/7/17.
 */
public class HTTPServer extends AbstractVerticle {
	private HttpServer server;

	@Override
	public void start() {
		HttpServerOptions options = new HttpServerOptions().setAcceptBacklog(100);

		HttpServer server = vertx.createHttpServer().requestHandler(new RequestHandler())
				.listen(9001, new ListenHandler());
	}

	private class RequestHandler implements Handler<HttpServerRequest> {

		public void handle(HttpServerRequest event) {

			JSONObject json = new JSONObject();
			json.put("absoluteURI", event.absoluteURI());
			json.put("headers", event.headers());
			json.put("method", event.method());
			json.put("path", event.path());
			json.put("params", event.params());
			json.put("query", event.query());
			json.put("uri", event.uri());

			String str = JSON.toJSONString(json, SerializerFeature.PrettyFormat);

			HttpServerResponse response = event.response();
			response.setChunked(true);
			response.write(str);
			response.end();

			event.bodyHandler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer event) {
					System.out.println("23123");
					System.out.println(event.getString(0, event.length()));
				}
			});

			event.handler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer event) {
					System.out.println("handler");
				}
			});

			event.endHandler(new Handler<Void>() {
				@Override
				public void handle(Void event) {
					System.out.println("endHandler");
				}
			});

			event.exceptionHandler(new Handler<Throwable>() {
				@Override
				public void handle(Throwable event) {
					System.out.println("exceptionHandler");
				}
			});

		}
	}

	private class ListenHandler implements Handler<AsyncResult<HttpServer>> {

		public void handle(AsyncResult<HttpServer> event) {
			if (event.succeeded()) {
				System.out.println("HTTP listen successed");
				deployCommand();
			} else {
				event.cause().printStackTrace();
			}
		}

		public void deployCommand() {
			DeploymentOptions options = new DeploymentOptions();
			List<Class> actions = ClassFinder.findClasses("wang.gnim.vertx3.clientCommand", "");
			for (final Class class1 : actions) {
				if (!class1.getSuperclass().getSimpleName().equals("ClientAbstractCommand"))
					continue;

				vertx.deployVerticle(class1.getCanonicalName(), options, new Handler<AsyncResult<String>>() {

					public void handle(AsyncResult<String> event) {
						if (event.succeeded()) {
							System.out.println("deploy:" + class1);
						} else {
							System.out.println("faile deploy:" + class1);
						}
					}
				});
			}
		}
	}
}
