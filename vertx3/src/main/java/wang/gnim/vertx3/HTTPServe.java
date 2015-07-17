package wang.gnim.vertx3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;
import wang.gnim.vertx3.util.ClassFinder;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;

public enum HTTPServe {

	INSTANCE;

	private final Vertx vertx;
	private MetricsService metricsService;

	private List<String> serverIds = new ArrayList<>();

	HTTPServe() {
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(1).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));

		vertx = Vertx.factory.vertx(vertxOptions);

		metricsService = MetricsService.create(vertx);
	}

	public Vertx vertx() {
		return vertx;
	}

	public MetricsService getMetricsService() {
		return metricsService;
	}

	public void startServer() {
		vertx.deployVerticle(Server.class.getCanonicalName(), new Handler<AsyncResult<String>>() {
			@Override
			public void handle(AsyncResult<String> event) {
				if (event.succeeded()) {
					serverIds.add(event.result());
				} else {

				}
			}
		});

	}

	public void restartServer() {
		List<String> copyServerIds = new ArrayList<>();
		Collections.copy(serverIds, copyServerIds);
		serverIds.clear();

		undeploy(serverIds);
	}

	private void undeploy(List<String> copyServerIds) {
		if (copyServerIds.size() > 0) {
			String id = copyServerIds.remove(0);
			vertx.undeploy(id, new Handler<AsyncResult<Void>>() {
				@Override
				public void handle(AsyncResult<Void> event) {
					System.out.println(id + " 解除部署成功");
					undeploy(serverIds);
				}
			});
		}
	}

	private class Server extends AbstractVerticle {
		private HttpServer server;

		@Override
		public void start() {
			HttpServerOptions options = new HttpServerOptions().setAcceptBacklog(100);

			HttpServer server = vertx.createHttpServer().requestHandler(new RequestHandler())
					.listen(8081, new ListenHandler());

			JsonObject metrics = metricsService.getMetricsSnapshot(server);
		}

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
				// deployClientCommand();
			} else {
				event.cause().printStackTrace();
			}
		}

		public void deployClientCommand() {
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
