package wang.gnim.vertx3.core.metrics.impl;

import com.alibaba.fastjson.JSON;
import io.vertx.core.json.JsonObject;

/**
 * Created by wanggnim on 2015/8/12.
 */
public class EventBusMetric {

    private final JsonObject obj;
    private static final String preName = "vertx.eventbus.";

    public EventBusMetric(JsonObject obj) {
        this.obj = obj;
    }

    public JsonObject getHandlers() {
        return obj.getJsonObject("handlers");
    }

    public JsonObject getAddress() {
        return obj.getJsonObject("handlers.myaddress");
    }

    public JsonObject getBytesRead() {
        return obj.getJsonObject("messages.bytes-read");
    }

    public JsonObject getBytesWritten() {
        return obj.getJsonObject("messages.bytes-written");
    }

    public JsonObject getPending() {
        return obj.getJsonObject("messages.pending");
    }

    public JsonObject getPendingLocal() {
        return obj.getJsonObject("messages.pending-local");
    }

    public JsonObject getPendingRemote() {
        return obj.getJsonObject("messages.pending-remote");
    }

    public JsonObject getReceived() {
        return obj.getJsonObject("messages.received");
    }

    public JsonObject getReceivedLocal() {
        return obj.getJsonObject("messages.received-local");
    }

    public JsonObject getReceivedRemote() {
        return obj.getJsonObject("messages.received-remote");
    }

    public JsonObject getDelivered() {
        return obj.getJsonObject("messages.delivered");
    }

    public JsonObject getDeliveredLocal() {
        return obj.getJsonObject("messages.delivered-local");
    }

    public JsonObject getDeliveredRemote() {
        return obj.getJsonObject("messages.delivered-remote");
    }

    public JsonObject getSent() {
        return obj.getJsonObject("messages.sent");
    }

    public JsonObject getSentLocal() {
        return obj.getJsonObject("messages.sent-local");
    }

    public JsonObject getSentRemote() {
        return obj.getJsonObject("messages.sent-remote");
    }

    public JsonObject getPublished() {
        return obj.getJsonObject("messages.published");
    }

    public JsonObject getPublishedLocal() {
        return obj.getJsonObject("messages.published-local");
    }

    public JsonObject getPublishedRemote() {
        return obj.getJsonObject("messages.published-remote");
    }

    public JsonObject getReplyFailures() {
        return obj.getJsonObject("messages.reply-failures");
    }

    public String toString() {
        return obj.toString();
    }

    public String toPrettyString() {
        return JSON.toJSONString(obj, true);
    }
}
