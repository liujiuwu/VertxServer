package wang.gnim.vertx3.action.test;

import wang.gnim.vertx3.action.AbstractAction;

public class SimpleAction extends AbstractAction {

	@Override
	public void execute(String obj) {
		System.out.println(obj);
	}

}
