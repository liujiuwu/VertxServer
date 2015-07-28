package wang.gnim.vertx3;


import wang.gnim.vertx3.net.Servers;
import wang.gnim.vertx3.net.ServerResource;

public class App {
	
	public static void main(String[] args) {
        ServerResource.INSTANCE.init();

        Servers.HTTP.start();
        Servers.TCP.start();

	}
}
