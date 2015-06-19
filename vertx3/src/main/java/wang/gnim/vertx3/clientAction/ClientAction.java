package wang.gnim.vertx3.clientAction;

import wang.gnim.vertx3.TCPServer;
import wang.gnim.vertx3.clientAction.test.SimpleClient;
import io.vertx.core.eventbus.EventBus;

public enum ClientAction {

	SimpleClient(SimpleClient.class),
	;
	
	private EventBus evnetBus;

	private String address;
	
	private ClientAction(Class clazz) {
		evnetBus = TCPServer.INSTANCE.vertx().eventBus();
		address = clazz.getCanonicalName();
	}
	
	public void publish(Object message) {
		evnetBus.publish(address, message);
	}
	
	public void send(Object message) {
		evnetBus.publish(address, message);
	}
}
