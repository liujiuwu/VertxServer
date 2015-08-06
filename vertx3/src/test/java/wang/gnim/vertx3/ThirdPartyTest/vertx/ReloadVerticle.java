package wang.gnim.vertx3.ThirdPartyTest.vertx;

import java.util.Date;

import io.vertx.core.AbstractVerticle;

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
