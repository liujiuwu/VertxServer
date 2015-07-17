package wang.gnim.vertx3;


public class App {
	
	public static void main(String[] args) {
		TCPManager.INSTANCE.startServer();
//		HTTPManager.INSTANCE.startServer();
	}
}
