package wang.gnim.vertx3.util;

/**
 * Created by wanggnim on 2015/7/30.
 */
public class ByteUtil {

    public static short bytes2Short(byte[] input) {
        return (short) ((input[0] & 0xFF) | ((input[1] << 8) & 0xFF00));
    }


}
