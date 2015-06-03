package wang.gnim.vertx;

import org.vertx.java.core.net.NetClient;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;

public class MockClient {

	public static NetClient client() {
		PlatformManager pm = PlatformLocator.factory.createPlatformManager();
		return pm.vertx().createNetClient();
	}
}
