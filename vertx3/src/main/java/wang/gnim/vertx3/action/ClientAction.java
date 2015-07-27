package wang.gnim.vertx3.action;

import io.vertx.core.eventbus.EventBus;
import wang.gnim.vertx3.action.test.SimpleAction;
import wang.gnim.vertx3.vertx.VertxManager;

public enum ClientAction {

	SimpleClient(SimpleAction.class),
	;
	
	private EventBus evnetBus;

	private String address;
	
	ClientAction(Class clazz) {
		evnetBus = VertxManager.TCPSERVER.eventBus();
		address = clazz.getCanonicalName();
	}
	
	public void publish(Object message) {
		evnetBus.publish(address, message);
	}
	
	public void send(Object message) {
		evnetBus.publish(address, message);
	}
}
