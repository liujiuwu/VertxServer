package wang.gnim.vertx3.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wanggnim on 2015/7/28.
 */
public enum PropertiesConfig {

    TCP_PORT;

    PropertiesConfig() {
        String name = this.name();
        stringValue = PropertiesLoader.INSTANCE.getProperty(name);
    }

    private String stringValue;

    public String stringValue() {
        return stringValue;
    }

    public int intValue() {
        try {
            return Integer.parseInt(stringValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static enum PropertiesLoader {
        INSTANCE;

        private Properties properties;
        {
            properties = new Properties();
            InputStream ins = PropertiesLoader.class.getClassLoader().getResourceAsStream("server.properties");
            try {
                properties.load(ins);
                ins.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        public String getProperty(String name) {
            return properties.getProperty(name);
        }
    }

}
