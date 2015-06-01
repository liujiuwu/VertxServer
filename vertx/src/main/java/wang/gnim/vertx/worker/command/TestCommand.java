package wang.gnim.vertx.worker.command;

import org.vertx.java.core.eventbus.Message;

public class TestCommand extends BaseCommand {

	@Override
	public String execute(Message<String> event) {
		return event.body();
	}

}
