package wang.gnim.vertx3.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.vertx.core.*;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.MetricsService;

public enum HTTPManager {

	INSTANCE;

	private final Vertx vertx;
	private MetricsService metricsService;

	private List<String> serverIds = new ArrayList<>();

	HTTPManager() {
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(1).setMetricsOptions(new DropwizardMetricsOptions().setEnabled(true));

		vertx = Vertx.factory.vertx(vertxOptions);

		metricsService = MetricsService.create(vertx);
	}

	public Vertx vertx() {
		return vertx;
	}

	public MetricsService getMetricsService() {
		return metricsService;
	}

	public void startServer() {
		vertx.deployVerticle(HTTPServer.class.getCanonicalName(), new Handler<AsyncResult<String>>() {
			@Override
			public void handle(AsyncResult<String> event) {
				if (event.succeeded()) {
					serverIds.add(event.result());
				} else {

				}
			}
		});

	}

	public void restartServer() {
		List<String> copyServerIds = new ArrayList<>();
		Collections.copy(serverIds, copyServerIds);
		serverIds.clear();

		undeploy(serverIds);
	}

	private void undeploy(List<String> copyServerIds) {
		if (copyServerIds.size() > 0) {
			String id = copyServerIds.remove(0);
			vertx.undeploy(id, new Handler<AsyncResult<Void>>() {
				@Override
				public void handle(AsyncResult<Void> event) {
					System.out.println(id + " 解除部署成功");
					undeploy(serverIds);
				}
			});
		}
	}

}
