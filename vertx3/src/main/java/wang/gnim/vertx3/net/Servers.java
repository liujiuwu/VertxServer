package wang.gnim.vertx3.net;

import wang.gnim.vertx3.vertx.Vertxs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by wanggnim on 2015/7/27.
 */
public enum Servers {

    TCP(Vertxs.TCP_SERVER, TCPServer.class),
    HTTP(Vertxs.HTTP_SERVER, HTTPServer.class);

    Servers(Vertxs vertxs, Class clazz) {
        this.vertxs = vertxs;
        this.clazz = clazz;
    }

    private Vertxs vertxs;
    private Class clazz;
    private List<String> serverIds = new ArrayList<>();

    public void start() {
        vertxs.deployVerticle(clazz.getCanonicalName());
    }

    public void restart() {
        List<String> copyServerIds = new ArrayList<>();
        Collections.copy(serverIds, copyServerIds);
        serverIds.clear();

        undeploy(serverIds);
    }

    private void undeploy(String deploymentID) {
        vertxs.undeploy(deploymentID, event -> {
            System.out.println(deploymentID + " 解除部署成功");
            undeploy(serverIds);
        });
    }

    private void undeploy(List<String> copyServerIds) {
        if (copyServerIds.size() > 0) {
            String deploymentID = copyServerIds.remove(0);
            undeploy(deploymentID);
        }
    }
}