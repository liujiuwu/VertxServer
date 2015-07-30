package wang.gnim.vertx3.util;

import wang.gnim.protobuf.messages.Message;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggnim on 2015/7/17.
 */
public enum ServerResource {

    INSTANCE;

    private List<Class> actions;
    private Map<Integer, String> parserAddresses = new HashMap<>();

    ServerResource() {
        actions = new ArrayList<>();
    }

    public void init() {
        String packageName = AbstractAction.class.getPackage().getName();
        List<Class> list = ClassFinder.findClasses(packageName, "");
        for (final Class class1 : list) {
            String simpleName = class1.getSimpleName();
            if (simpleName.equals(AbstractAction.class.getSimpleName())
                    || simpleName.equals(MsgIDSetter.class.getSimpleName()))
                continue;

            actions.add(class1);

            Annotation[] annotations = class1.getAnnotations();
            for (Annotation anno : annotations) {
                if(anno.annotationType() == MsgIDSetter.class) {
                    MsgIDSetter msgIDSetter = (MsgIDSetter)anno;
                    Message.MsgID msgID = msgIDSetter.msgID();
                    parserAddresses.put(msgID.getNumber(), class1.getCanonicalName());
                }
            }
        }
    }

    public List<Class> getActions() {
        return actions;
    }

    public String getParserAddress(Integer id) {
        return parserAddresses.get(id);
    }
}
