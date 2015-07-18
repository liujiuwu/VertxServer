package wang.gnim.vertx3;


import wang.gnim.vertx3.metrics.MetricManager;
import wang.gnim.vertx3.net.HTTPManager;
import wang.gnim.vertx3.net.ServerResource;
import wang.gnim.vertx3.net.TCPManager;

public class App {
	
	public static void main(String[] args) {
        ServerResource.INSTANCE.init();
        MetricManager.INSTANCE.start();

        TCPManager.INSTANCE.startServer();
		HTTPManager.INSTANCE.startServer();
	}
}
