package wang.gnim.vertx3.ThirdPartyTest.asm;

import org.junit.Test;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

/**
 * Created by wanggnim on 2015/8/6.
 */
public class ClassVistorTest {

    @Test
    public void test() {
        try {
            ClassReader classReader = new ClassReader("wang.gnim.vertx3.ThirdPartyTest.asm.ClassVistorTest");
//            classReader.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void print() {
        System.out.println("ClassVistorTest");
    }
}
