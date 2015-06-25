package wang.gnim.vertx3.clientCommand;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public abstract class ClientAbstractCommand extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getSimpleName().split("Action")[0];
		
		vertx.eventBus().consumer(name, new Handler<Message<Object>>() {

			@Override
			public void handle(Message<Object> event) {
				JsonObject object = new JsonObject(event.body().toString());
				execute(object);
			}
		});
		
	}

	public abstract String execute(JsonObject obj);
}
