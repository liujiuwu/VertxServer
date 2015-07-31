package test_non_project;

import com.googlecode.protobuf.format.JsonFormat;
import org.junit.Test;
import wang.gnim.protobuf.messages.TestMessage;

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
