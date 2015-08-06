package wang.gnim.vertx3.ThirdPartyTest.vertx;

import java.io.*;

import org.junit.Test;

import io.vertx.core.json.JsonObject;

/**
 * Created by wangming on 2015/6/29.
 */
public class TestJsonObject {

    @Test
    public  void test() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("./src/test/resource/config.json")));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while (( line = reader.readLine()) != null) {
                buffer.append(line.trim());
            }

            JsonObject jsonObj = new JsonObject(buffer.toString().trim());
            System.out.println(jsonObj.getJsonObject("vertxOptions").getString(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
