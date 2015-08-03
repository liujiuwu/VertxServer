package wang.gnim.vertx3.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by wanggnim on 2015/8/3.
 */
public class GameLogger {

    public static void log(String info) {
        logger.info(info);
    }
    private final static Logger logger = LoggerFactory.getLogger(GameLogger.class);
}
