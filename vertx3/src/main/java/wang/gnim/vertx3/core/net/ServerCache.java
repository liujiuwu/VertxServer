package wang.gnim.vertx3.core.net;

import io.vertx.core.http.HttpServer;
import io.vertx.core.net.NetServer;

/**
 *
 * Created by wanggnim on 2015/8/12.
 */
public enum ServerCache {

    INSTANCE;

    private HttpServer httpServer;
    private NetServer tcpServer;

    public void addTCPServer(NetServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    public void addHTTPServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public NetServer getTcpServer() {
        return tcpServer;
    }

    public HttpServer getHTTPServer() {
        return httpServer;
    }
}
