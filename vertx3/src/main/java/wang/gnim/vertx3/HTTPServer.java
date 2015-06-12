package wang.gnim.vertx3;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;

public class HTTPServer {

	public void start() {
		Vertx vertx = Vertx.factory.vertx();
		
		HttpServerOptions options = new HttpServerOptions()
			.setAcceptBacklog(100);
		
		vertx.createHttpServer(options)
				.requestHandler(result -> {
					result.response().end();
				})
				.listen(8081);
		
	}
	
}
