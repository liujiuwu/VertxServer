package wang.gnim.vertx3.action.test;

import wang.gnim.vertx3.action.ClientAbstractAction;

public class SimpleClient extends ClientAbstractAction{

	@Override
	public void execute(String obj) {
		System.out.println(obj);
	}

}
