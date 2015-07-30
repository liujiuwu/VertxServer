package wang.gnim.vertx3.action;

import com.google.protobuf.GeneratedMessageLite;
import wang.gnim.protobuf.messages.Message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by wanggnim on 2015/7/30.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgIDSetter {

    Message.MsgID msgID();
//    Class<GeneratedMessageLite> parser();
}
