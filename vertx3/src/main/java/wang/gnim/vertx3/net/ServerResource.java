package wang.gnim.vertx3.net;

import wang.gnim.vertx3.util.ClassFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanggnim on 2015/7/17.
 */
public enum ServerResource {

    INSTANCE;

    private List<Class> actions;

    ServerResource() {
        actions = new ArrayList<>();
    }

    public void init() {
        List<Class> list = ClassFinder.findClasses("wang.gnim.vertx3.clientAction", "");
        for (final Class class1 : list) {
            if (!class1.getSuperclass().getSimpleName().equals("ClientAbstractAction"))
                continue;
            actions.add(class1);
        }
    }

    public List<Class> getActions() {
        return actions;
    }

}