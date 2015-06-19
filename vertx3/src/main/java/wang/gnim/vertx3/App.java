package wang.gnim.vertx3;


public class App {
	
	public static void main(String[] args) {
		VerticleDeployer.deployClientAction();
		TCPServer.INSTANCE.start();
	}
}
