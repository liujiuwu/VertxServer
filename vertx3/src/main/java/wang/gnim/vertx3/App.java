package wang.gnim.vertx3;


public class App {
	
	public static void main(String[] args) {
        ServerResource.INSTANCE.init();

		TCPManager.INSTANCE.startServer();
		HTTPManager.INSTANCE.startServer();
	}
}
