package wang.gnim.vertx3.util;

import wang.gnim.protobuf.messages.MessageWrapper;
import wang.gnim.vertx3.action.AbstractAction;
import wang.gnim.vertx3.action.MsgIDSetter;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 
 * Created by wanggnim on 2015/7/17.
 */
public enum ServerResource {

	INSTANCE;

	private Map<MessageWrapper.MsgID, String> parserAddresses = new HashMap<>();

	ServerResource() {
	}

	public void init() {
		String packageName = AbstractAction.class.getPackage().getName();
		List<Class> list = ClassFinder.findClasses(packageName, "");
		for (final Class<? extends AbstractAction> class1 : list) {

			MsgIDSetter msgIDSetter = class1.getAnnotation(MsgIDSetter.class);
			if (msgIDSetter == null)
				continue;

			MessageWrapper.MsgID msgID = msgIDSetter.msgID();
			parserAddresses.put(msgID, class1.getCanonicalName());
		}
	}

	public String getParserAddress(MessageWrapper.MsgID id) {
		return parserAddresses.get(id);
	}

    public Collection<String> getActionNames() {
        return parserAddresses.values();
    }

    public Collection<MessageWrapper.MsgID> getActionIds() {
        return parserAddresses.keySet();
    }
}
