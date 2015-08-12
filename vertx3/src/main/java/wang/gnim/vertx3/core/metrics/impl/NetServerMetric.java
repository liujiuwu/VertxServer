package wang.gnim.vertx3.core.metrics.impl;

import com.alibaba.fastjson.JSON;
import io.vertx.core.json.JsonObject;

/**
 * Created by wanggnim on 2015/8/12.
 */
public class NetServerMetric {

    private final JsonObject obj;
    private static final String preName = "vertx.net.servers.<host>:<port>.";

    public NetServerMetric(JsonObject obj) {
        this.obj = obj;
    }

    public JsonObject getOpenNetsockets() {
        return obj.getJsonObject(preName + "open-netsockets");
    }

    public JsonObject getOpenNetsockets(String host) {
        return obj.getJsonObject(preName + "open-netsockets.<remote-host>");
    }

    public JsonObject getConnections() {
        return obj.getJsonObject(preName + "connections");
    }

    public JsonObject getExceptions() {
        return obj.getJsonObject(preName + "exceptions");
    }

    public JsonObject getBytesRead() {
        return obj.getJsonObject(preName + "bytes-read");
    }

    public JsonObject getBytesWritten() {
        return obj.getJsonObject(preName + "bytes-written");
    }

    public String toString() {
        return obj.toString();
    }

    public String toPrettyString() {
        return JSON.toJSONString(obj, true);
    }
}
