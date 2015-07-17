package wang.gnim.vertx3.action;

import wang.gnim.vertx3.TCPServe;
import io.vertx.core.eventbus.EventBus;

public enum ClientAction {

	SimpleClient(wang.gnim.vertx3.action.test.SimpleClient.class),
	;
	
	private EventBus evnetBus;

	private String address;
	
	ClientAction(Class clazz) {
		evnetBus = TCPServe.INSTANCE.vertx().eventBus();
		address = clazz.getCanonicalName();
	}
	
	public void publish(Object message) {
		evnetBus.publish(address, message);
	}
	
	public void send(Object message) {
		evnetBus.publish(address, message);
	}
}
