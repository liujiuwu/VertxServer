package wang.gnim.vertx3.model;

import com.google.protobuf.AbstractMessageLite;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

/**
 * Created by wanggnim on 2015/8/3.
 */
public class Player {

    private NetSocket netSocket;

    public void setNetSocket(NetSocket netSocket) {
        this.netSocket = netSocket;
    }

    public void write(AbstractMessageLite messageLite) {
        byte[] message = messageLite.toByteArray();

    }
}
