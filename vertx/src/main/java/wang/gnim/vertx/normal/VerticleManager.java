package wang.gnim.vertx.normal;

import java.util.List;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import wang.gnim.vertx.util.ClassFinder;

public class VerticleManager extends Verticle {

	@Override
	public void start() {
		
//		deplyTcpServer();
		deplyHttpServer();

		deplyActionWorker();
		deplyCommandWorker();
	}
	
	private void deplyHttpServer() {
		int cores = Runtime.getRuntime().availableProcessors() / 2;	// XXX
		container.deployVerticle(HTTPServer.class.getCanonicalName(), cores, new Handler<AsyncResult<String>>() {

			public void handle(AsyncResult<String> event) {
				if (event.succeeded()) {
					System.out.println("Http 部署成功, ID is " + event.result());
				} else {
					event.cause().printStackTrace();
				}
			}
		});
	}
	
	private void deplyTcpServer() {
		int cores = Runtime.getRuntime().availableProcessors() / 2;	// XXX
		container.deployVerticle(TCPServer.class.getCanonicalName(), cores, new Handler<AsyncResult<String>>() {

			public void handle(AsyncResult<String> event) {
				if (event.succeeded()) {
					System.out.println("TCP 部署成功, ID is " + event.result());
				} else {
					event.cause().printStackTrace();
				}
				
			}
		});
	}

	private void deplyCommandWorker() {
		List<Class> workers = ClassFinder.findClasses("wang.gnim.vertx.worker.command", "Command");
		for (final Class clazz : workers) {
			JsonObject config = new JsonObject();
			container.deployWorkerVerticle(clazz.getCanonicalName(), config, 1, true, new Handler<AsyncResult<String>>() {

				public void handle(AsyncResult<String> event) {
					System.out.println(clazz.getSimpleName() + " 部署完成");
				}
			});
		}
	}
	
	private void deplyActionWorker() {
		List<Class> workers = ClassFinder.findClasses("wang.gnim.vertx.worker.action", "Action");
		for (final Class clazz : workers) {
			JsonObject config = new JsonObject();
			container.deployWorkerVerticle(clazz.getCanonicalName(), config, 1, false, new Handler<AsyncResult<String>>() {

				public void handle(AsyncResult<String> event) {
					System.out.println(clazz.getSimpleName() + " 部署完成");
				}
			});
		}
	}
	
}
