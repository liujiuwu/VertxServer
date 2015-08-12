package wang.gnim.vertx3.core.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import wang.gnim.vertx3.log.GameLogger;
import wang.gnim.vertx3.util.ClassFinder;
import wang.gnim.vertx3.util.ServerResource;

import java.util.List;

/**
 *
 * Created by wanggnim on 2015/7/17.
 */
public class HTTPServerStarter extends AbstractVerticle {

	@Override
	public void start() {
		HttpServerOptions options = new HttpServerOptions().setAcceptBacklog(100);

        HttpServer server = vertx.createHttpServer(options).requestHandler(new RequestHandler())
                .listen(9001, new ListenHandler());

        ServerCache.INSTANCE.addHTTPServer(server);
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

			event.bodyHandler(event1 -> {
                System.out.println("23123");
                System.out.println(event1.getString(0, event1.length()));
            });

			event.handler(event1 -> System.out.println("handler"));

			event.endHandler(event1 -> System.out.println("endHandler"));

			event.exceptionHandler(event1 -> System.out.println("exceptionHandler"));

		}
	}

	private class ListenHandler implements Handler<AsyncResult<HttpServer>> {

		public void handle(AsyncResult<HttpServer> event) {
			if (event.succeeded()) {
                GameLogger.log("HTTP listen successed");
//				deployCommand();
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

				vertx.deployVerticle(class1.getCanonicalName(), options, event -> {
                    if (event.succeeded()) {
                        GameLogger.log("deploy:" + class1);
                    } else {
                        GameLogger.log("faile deploy:" + class1);
                    }
                });
			}
		}
	}
}
