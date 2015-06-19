package wang.gnim.vertx3.clientCommand;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public abstract class ClientAbstractCommand extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getSimpleName().split("Action")[0];
		
		vertx.eventBus().consumer(name, new Handler<Message<Object>>() {

			@Override
			public void handle(Message<Object> event) {
				execute(event);
			}
		});
		
	}

	public abstract void execute(Object obj);
}
