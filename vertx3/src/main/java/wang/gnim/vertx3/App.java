package wang.gnim.vertx3;


import wang.gnim.vertx3.core.net.ServerStarter;
import wang.gnim.vertx3.util.ServerResource;

public class App {
	
	public static void main(String[] args) {
        ServerResource.INSTANCE.init();

        ServerStarter.INSTANCE.startTCPServer();
        ServerStarter.INSTANCE.startHTTPServer();

	}
}
