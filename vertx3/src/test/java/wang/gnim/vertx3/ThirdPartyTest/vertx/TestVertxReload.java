package wang.gnim.vertx3.ThirdPartyTest.vertx;

import java.util.concurrent.TimeUnit;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * Created by wanggnim on 2015/7/14.
 */
public class TestVertxReload {

    public static String id = "";

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            deploy();
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            undeploy();
        }
    }

    public static void deploy() {
        Vertx vertx = Vertx.vertx();
        String className = ReloadVerticle.class.getCanonicalName();

        vertx.deployVerticle(className, new Handler<AsyncResult<String>>() {

            @Override
            public void handle(AsyncResult<String> event) {
                if(event.succeeded()) {
                    System.out.println("deploy:" + className + "  " + event.result());
                    id = event.result();
                } else {
                    System.out.println("faile deploy:" + className);
                    event.cause().printStackTrace();
                }
            }
        });
    }

    public static void undeploy() {
        Vertx vertx = Vertx.vertx();
        String className = ReloadVerticle.class.getCanonicalName();
        vertx.undeploy(id, new Handler<AsyncResult<Void>>() {
            @Override
            public void handle(AsyncResult<Void> event) {
                if(event.succeeded()) {
                    System.out.println("undeploy:" + className + "  " + id);
                } else {
                    System.out.println("faile undeploy:" + className + "  " + id);
                    event.cause().printStackTrace();
                }
            }
        });
    }
}

