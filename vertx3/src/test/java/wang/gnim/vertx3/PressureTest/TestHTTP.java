package wang.gnim.vertx3.PressureTest;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/3.
 */
public class TestHTTP {

    public static  void main(String[] args) {
        Vertx vertx = Vertx.factory.vertx();
        HttpClient client = vertx.createHttpClient();

        HttpClientRequest request = client.get(8081, "localhost", "/Simple", new ResponseHandler());
        request.setChunked(true);
        request.write("123");
//        HttpClientRequest req = client.request(HttpMethod.GET, 8081, "localhost", "Simple", new ResponseHandler());
//        req.setChunked(true);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class ResponseHandler implements Handler<HttpClientResponse> {
        @Override
        public void handle(HttpClientResponse event) {
            event.bodyHandler(new Handler<Buffer>() {
                @Override
                public void handle(Buffer event) {
                    System.out.println("123");
                    System.out.println(event.getString(0, event.length()));
                }
            });
        }
    }
}
