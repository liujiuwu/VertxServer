package wang.gnim.vertx.worker.action;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public abstract class BaseAction extends Verticle {

	@Override
	public void start() {

		String[] type = getClass().getSimpleName().split("Action");
		vertx.eventBus().registerHandler(type[0], new SimpleHandler(),
				new ResultHandler());
	}

	class SimpleHandler implements Handler<Message<byte[]>> {

		public void handle(Message<byte[]> event) {
			handler(event);
		}
	}

	class ResultHandler implements Handler<AsyncResult<Void>> {

		public void handle(AsyncResult<Void> event) {
			resultHandler(event);
		}
	}
	
	public abstract void handler(Message<byte[]> event);
	public abstract void resultHandler(AsyncResult<Void> event);
	
	
	private void parseMessage(Message<byte[]> event) {
		byte[] body = event.body();
		
	}
}
