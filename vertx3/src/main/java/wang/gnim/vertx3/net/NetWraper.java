package wang.gnim.vertx3.net;

import io.vertx.core.net.NetSocket;

/**
 * Created by wanggnim on 2015/7/29.
 */
public class NetWraper {

    private NetSocket netSocket;

    public NetSocket getNetSocket() {
        return netSocket;
    }

    public void setNetSocket(NetSocket netSocket) {
        this.netSocket = netSocket;
    }
}
