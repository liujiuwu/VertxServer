package wang.gnim.vertx3.core.metrics.impl;

import com.alibaba.fastjson.JSON;
import io.vertx.core.json.JsonObject;

/**
 * Created by wanggnim on 2015/8/12.
 */
public class VertxMetric {

    private final JsonObject obj;

    public VertxMetric(JsonObject obj) {
        this.obj = obj;
    }

    public JsonObject getEventLoopSize() {
        return obj.getJsonObject("vertx.event-loop-size");
    }

    public JsonObject getWorkerPoolSize() {
        return obj.getJsonObject("vertx.worker-pool-size");
    }

    public JsonObject getClusterHost() {
        return obj.getJsonObject("vertx.cluster-host");
    }

    public JsonObject getClusterPort() {
        return obj.getJsonObject("vertx.cluster-port");
    }

    public JsonObject getVerticles() {
        return obj.getJsonObject("vertx.verticles");
    }

    public String toString() {
        return obj.toString();
    }

    public String toPrettyString() {
        return JSON.toJSONString(obj, true);
    }
}
