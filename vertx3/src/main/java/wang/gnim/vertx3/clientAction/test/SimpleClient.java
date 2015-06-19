package wang.gnim.vertx3.clientAction.test;

import wang.gnim.vertx3.clientAction.ClientAbstractAction;

public class SimpleClient extends ClientAbstractAction{

	@Override
	public void execute(String obj) {
		System.out.println(obj);
	}

}
