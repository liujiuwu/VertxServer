package wang.gnim.vertx3.command;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public abstract class AbstractCommand extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getSimpleName().split("Command")[0];
		
		vertx.eventBus().consumer(name, new Handler<Message<String>>() {

			public void handle(Message<String> event) {
				JsonObject object = new JsonObject(event.body());
				execute(object);
			}
		});
		
	}

	public abstract String execute(JsonObject obj);
}
