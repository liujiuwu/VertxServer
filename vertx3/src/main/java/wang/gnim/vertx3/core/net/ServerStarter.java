package wang.gnim.vertx3.core.net;

import wang.gnim.vertx3.core.vertx.Vertxs;
import wang.gnim.vertx3.log.GameLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by wanggnim on 2015/7/27.
 */
public enum ServerStarter {

    INSTANCE;

    public void startHTTPServer() {
        Vertxs.HTTP_SERVER.deployVerticle(HTTPServerStarter.class.getCanonicalName());
    }

    public void startTCPServer() {
        Vertxs.TCP_SERVER.deployVerticle(TCPServerStarter.class.getCanonicalName());
    }

    public void restartHTTPServer() {
//        Vertxs.HTTP_SERVER.undeploy();
    }

    public void restartTCPServer() {
//        Vertxs.TCP_SERVER.undeploy();
    }
}
