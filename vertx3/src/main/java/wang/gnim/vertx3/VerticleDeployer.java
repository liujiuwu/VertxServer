package wang.gnim.vertx3;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

import wang.gnim.vertx3.util.ClassFinder;

public class VerticleDeployer {

	public static void deployClientAction() {
		Vertx vertx = TCPServer.INSTANCE.vertx();

		DeploymentOptions options = new DeploymentOptions();
		List<Class> actions = ClassFinder.findClasses("wang.gnim.vertx3.clientAction", "");
		for (final Class class1 : actions) {
			if(!class1.getSuperclass().getSimpleName().equals("ClientAbstractAction"))
				continue;
			
			vertx.deployVerticle(class1.getCanonicalName(), options, new Handler<AsyncResult<String>>() {

				@Override
				public void handle(AsyncResult<String> event) {
					if(event.succeeded()) {
						System.out.println("deploy:" + class1.getCanonicalName());
					} else {
						System.out.println("faile deploy:" + class1.getCanonicalName());
					}
				}
			});
		}
	}
}
