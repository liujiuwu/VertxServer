package wang.gnim.vertx3.core.metrics;

import com.codahale.metrics.health.HealthCheck;

/**
 *
 * Created by wanggnim on 2015/7/18.
 */
public class DatabaseHealthCheck extends HealthCheck {

    public DatabaseHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.unhealthy("Can't ping database");
    }
}
