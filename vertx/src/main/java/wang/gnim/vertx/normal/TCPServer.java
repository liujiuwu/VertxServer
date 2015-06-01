package wang.gnim.vertx.normal;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.platform.Verticle;

public class TCPServer extends Verticle {

	private NetServer server;
	
	@Override
	public void start() {
		server = vertx.createNetServer()
		.setAcceptBacklog(10000)	// 设置成10000用户的并发量
		.setReceiveBufferSize(100)	// XXX
		.setSendBufferSize(100)		// XXX 调试一个更合适的值
		.setSoLinger(5000)			// 服务器关闭时，阻塞5秒，处理剩余数据
		.setReuseAddress(true)		// 复用host
		.setTCPKeepAlive(true)		// 保持长连接
		.setTCPNoDelay(true)		// 一旦有数据就处理
		.setUsePooledBuffers(true)
		.connectHandler(new ConnectHandler())
		.listen(8081, new ListenHandler());
		
	}

	@Override
	public void stop() {
		if (server != null)
			server.close(new CloseHandler());
	}
	
	class ListenHandler implements Handler<AsyncResult<NetServer>> {

		public void handle(AsyncResult<NetServer> event) {
			if(event.succeeded()) {
				System.out.println("TCP服务器启动成功 : " + event.result().host() + ":" + event.result().port());
			}
			else {
				System.out.println("服务器启动失败 : " + event.cause());
			}
		}
		
	}
	
	class CloseHandler implements Handler<AsyncResult<Void>> {

		public void handle(AsyncResult<Void> event) {
			System.out.println("服务器close成功");
		}
	}
	
	class ConnectHandler implements Handler<NetSocket> {

		public void handle(NetSocket event) {
			event.dataHandler(new DataHandler());
		}
	}
	
	class DataHandler implements Handler<Buffer> {

		public void handle(Buffer event) {
			vertx.eventBus().publish("simple", event.getBytes());
		}

	}
}
