package test_vertx3;

import io.vertx.core.AbstractVerticle;

import java.util.Date;

/**
 * Created by wanggnim on 2015/7/14.
 */
public class ReloadVerticle  extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        System.out.println(new Date().toLocaleString() + " --- ");
    }

    public String toString() {
        return "";
    }
}
