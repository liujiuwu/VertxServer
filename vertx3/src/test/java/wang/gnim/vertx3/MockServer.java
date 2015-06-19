package wang.gnim.vertx3;

import java.util.concurrent.TimeUnit;

public class MockServer {

	public static void start() {
		VerticleDeployer.deployClientAction();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
