package wang.gnim.vertx3;


import wang.gnim.vertx3.net.ServerManager;
import wang.gnim.vertx3.net.ServerResource;

public class App {
	
	public static void main(String[] args) {
        ServerResource.INSTANCE.init();

        ServerManager.HTTP.startServer();
        ServerManager.TCP.startServer();

	}
}
