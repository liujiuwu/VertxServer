package wang.gnim.vertx3.net;

import wang.gnim.vertx3.vertx.VertxManager;

/**
 * Created by wanggnim on 2015/7/27.
 */
public enum ServerManager {

    TCP {
        @Override
        public void startServer() {
            VertxManager.TCPSERVER.deployVerticle(TCPServer.class.getCanonicalName());
        }

        @Override
        public void restartServer() {
            VertxManager.TCPSERVER.restartServer();
        }
    },
    HTTP {
        @Override
        public void startServer() {
            VertxManager.HTTPSERVER.deployVerticle(HTTPServer.class.getCanonicalName());
        }

        @Override
        public void restartServer() {
            VertxManager.HTTPSERVER.restartServer();
        }
    };

    public abstract void startServer() ;

    public abstract void restartServer() ;

}
