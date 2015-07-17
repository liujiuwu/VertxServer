package wang.gnim.vertx3;


import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import wang.gnim.vertx3.util.ClassFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TCPManager {

	INSTANCE;
	
	private Vertx vertx;
    private List<String> serverIds = new ArrayList<>();

	TCPManager() {
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(1);
		vertx = Vertx.factory.vertx(vertxOptions);
	}
	
	public Vertx vertx() {
		return vertx;
	}
	
	public void startServer() {
        vertx.deployVerticle(TCPServer.class.getCanonicalName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> event) {
                if (event.succeeded()) {
                    serverIds.add(event.result());
                    System.out.println("TCP 服务部署成功：" + event.result());
                } else {
                    System.out.println("TCP 服务部署失败");
                    event.cause().printStackTrace();
                }
            }
        });
	}

    public void restartServer() {
        List<String> copyServerIds = new ArrayList<>();
        Collections.copy(serverIds, copyServerIds);
        serverIds.clear();

        undeploy(serverIds);
    }

    private void undeploy(List<String> copyServerIds) {
        if (copyServerIds.size() > 0) {
            String id = copyServerIds.remove(0);
            vertx.undeploy(id, new Handler<AsyncResult<Void>>() {
                @Override
                public void handle(AsyncResult<Void> event) {
                    System.out.println(id + " 解除部署成功");
                    undeploy(serverIds);
                }
            });
        }
    }

}
