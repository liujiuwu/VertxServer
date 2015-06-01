package wang.gnim.vertx;

import java.io.IOException;
import java.net.URL;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;

import wang.gnim.vertx.normal.VerticleManager;

public class App {

	public static void main(String[] args) {
		
		PlatformManager pm = PlatformLocator.factory.createPlatformManager();

		URL[] classpath = {};
		String includes = null;
		JsonObject config = new JsonObject();

		pm.deployVerticle(VerticleManager.class.getCanonicalName(), config,
				classpath, 1, includes, new DeployVerticle());
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class DeployVerticle implements AsyncResultHandler<String> {

		public void handle(AsyncResult<String> asyncResult) {
			if (asyncResult.succeeded()) {
				System.out.println("VerticleManager 部署完成： "
						+ asyncResult.result());
			} else {
				asyncResult.cause().printStackTrace();
			}
		}
	}
}
