package wang.gnim.vertx3.clientCommand.test;

import io.vertx.core.json.JsonObject;
import wang.gnim.vertx3.clientCommand.ClientAbstractCommand;

public class SimpleClient extends ClientAbstractCommand{

	@Override
	public String execute(JsonObject obj) {
		return "simple";
	}

}
