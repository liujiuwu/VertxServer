package wang.gnim.vertx3;


public class App {
	
	public static void main(String[] args) {
		TCPServe.INSTANCE.startServer();
		HTTPServe.INSTANCE.startServer();
	}
}
