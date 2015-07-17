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

public enum TCPServe {

	INSTANCE;
	
	private Vertx vertx;
    private List<String> serverIds = new ArrayList<>();

	TCPServe() {
		VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.setEventLoopPoolSize(1);
		vertx = Vertx.factory.vertx(vertxOptions);
	}
	
	public Vertx vertx() {
		return vertx;
	}
	
	public void startServer() {
        vertx.deployVerticle(Server.class.getCanonicalName(), new Handler<AsyncResult<String>>() {
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

    private class Server extends AbstractVerticle {

        @Override
        public void start() {
            HttpServerOptions options = new HttpServerOptions()
                    .setAcceptBacklog(100);

            vertx.createNetServer(options)
                    .connectHandler(new ConnectHandler())
                    .listen(8080, new ListenHandler());
        }
    }

	private class ListenHandler implements Handler<AsyncResult<NetServer>>{
		
		@Override
		public void handle(AsyncResult<NetServer> event) {
			if(event.succeeded()) {
				System.out.println("TCP listen successed");
                deployAction();
			}
			else {
                event.cause().printStackTrace();
			}
		}

        public void deployAction() {

            DeploymentOptions options = new DeploymentOptions();
            List<Class> actions = ClassFinder.findClasses("wang.gnim.vertx3.clientAction", "");
            for (final Class class1 : actions) {
                if(!class1.getSuperclass().getSimpleName().equals("ClientAbstractAction"))
                    continue;

                vertx.deployVerticle(class1.getCanonicalName(), options, new Handler<AsyncResult<String>>() {

                    @Override
                    public void handle(AsyncResult<String> event) {
                        if(event.succeeded()) {
                            System.out.println("deploy:" + class1.getCanonicalName());
                        } else {
                            System.out.println("faile deploy:" + class1.getCanonicalName());
                        }
                    }
                });
            }
        }
	}

	private class ConnectHandler implements Handler<NetSocket> {
		@Override
		public void handle(final NetSocket netSocket) {

			netSocket.closeHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {

				}
			});
			
			netSocket.drainHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
				}
			});
			
			netSocket.endHandler(new Handler<Void>() {

				@Override
				public void handle(Void event) {
				}
			});
			
			netSocket.exceptionHandler(new Handler<Throwable>() {

				@Override
				public void handle(Throwable event) {
                    event.printStackTrace();
				}
			});
			
			netSocket.handler(new Handler<Buffer>() {

				@Override
				public void handle(Buffer event) {
					System.out.println(event.getString(0, event.length()));
					netSocket.write("0");
				}
			});
		}
	}
}
