package wang.gnim.vertx.worker.command;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public abstract class BaseCommand extends Verticle {

	@Override
	public void start() {
		String className = getClass().getSimpleName();
		vertx.eventBus().registerHandler("/" + className, new Handler<Message<String>>() {

			public void handle(Message<String> event) {
				String message = execute(event);
				
				event.reply(message);
			}
		});
	}

	public abstract String execute(Message<String> event);
}
