package wang.gnim.vertx3;

import java.util.List;

import wang.gnim.vertx3.util.ClassFinder;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;

public enum HTTPServer {

	INSTANCE;
	
	private final Vertx vertx;
	
	private HTTPServer() {
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(1);
		vertx = Vertx.factory.vertx(vertxOptions);
	}
	
	public Vertx vertx() {
		return vertx;
	}
	
	public void start() {
		
		HttpServerOptions options = new HttpServerOptions()
			.setAcceptBacklog(100);
		
		vertx.createHttpServer(options)
				.requestHandler(new Handler<HttpServerRequest>() {

					@Override
					public void handle(HttpServerRequest event) {

					}
				}).listen(8081, new ListenHandler());

	}
	
	private class ListenHandler implements Handler<AsyncResult<HttpServer>>{
		
		@Override
		public void handle(AsyncResult<HttpServer> event) {
			if(event.succeeded()) {
				System.out.println("HTTP listen successed");
				deployClientCommand();
			}
			else {
				System.out.println(event.cause());
			}
		}
		
		public void deployClientCommand() {
			DeploymentOptions options = new DeploymentOptions();
			List<Class> actions = ClassFinder.findClasses("wang.gnim.vertx3.clientCommand", "");
			for (Class class1 : actions) {
				if(!class1.getSuperclass().getSimpleName().equals("ClientAbstractCommand"))
					continue;
				
				vertx.deployVerticle(class1.getCanonicalName(), options, new Handler<AsyncResult<String>>() {

					@Override
					public void handle(AsyncResult<String> event) {
						if(event.succeeded()) {
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
