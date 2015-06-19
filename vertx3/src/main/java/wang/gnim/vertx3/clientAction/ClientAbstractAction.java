package wang.gnim.vertx3.clientAction;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public abstract class ClientAbstractAction extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getCanonicalName();
		vertx.eventBus().consumer(name, new Handler<Message<String>>() {

			@Override
			public void handle(Message<String> event) {
				execute(event.body());
			}
		});
		
	}

	public abstract void execute(String obj);
	
}
