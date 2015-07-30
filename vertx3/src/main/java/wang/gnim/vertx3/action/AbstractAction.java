package wang.gnim.vertx3.action;

import io.vertx.core.AbstractVerticle;

public abstract class AbstractAction extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		String name = getClass().getCanonicalName();
		vertx.eventBus().consumer(name, event -> {
//				execute(event.body());
        });
		
	}

	public abstract void execute(String obj);

}
