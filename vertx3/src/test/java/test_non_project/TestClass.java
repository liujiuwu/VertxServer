package test_non_project;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wanggnim on 2015/7/30.
 */
public class TestClass {

    @Test
    public void testPackageName() {
        String name = TestClass.class.getPackage().getName();
        Assert.assertEquals("test_non_project", name);
    }
}
