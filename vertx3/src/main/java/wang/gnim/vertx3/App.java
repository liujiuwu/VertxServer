package wang.gnim.vertx3;

import io.vertx.core.Vertx;

public class App {
	
	public static void main(String[] args) {
//		Vertx.factory.vertx().deployVerticle("");
		new TCPServer().start();
		new HTTPServer().start();
	}
}
