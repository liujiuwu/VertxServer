package wang.gnim.vertx3.ThirdPartyTest;

import org.junit.Test;

import wang.gnim.protobuf.messages.TestMessage;

import com.googlecode.protobuf.format.JsonFormat;

/**
 * Created by wanggnim on 2015/7/31.
 */
public class ProtobufJavaFormatTest {

    @Test
    public void test2JSON() {
        TestMessage.TestRequest request = TestMessage.TestRequest.newBuilder()
                .setData(12)
                .build();

        String jsonFormat = JsonFormat.printToString(request);
        System.out.println(jsonFormat);
    }
}
