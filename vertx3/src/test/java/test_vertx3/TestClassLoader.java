package test_vertx3;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanggnim on 2015/7/14.
 */
public class TestClassLoader {

    @Test
    public void test_ClassLoader() {
        ClassLoader cl = TestClassLoader.class.getClassLoader();

        try {
            Class<?> clazz = cl.loadClass(TestClassLoader.class.getCanonicalName());
            TestClassLoader obj = (TestClassLoader)clazz.newInstance();
            obj.print();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_UrlClassLoader() {
        File file = new File("test_vertx3");

        List<URL> list = new ArrayList<>();
        try {
            list.add(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader cl = new URLClassLoader((URL[]) list.toArray(new URL[1]));
        try {
            Class<?> clazz = cl.loadClass(TestClassLoader.class.getCanonicalName());
            TestClassLoader obj = (TestClassLoader)clazz.newInstance();

            obj.print();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_ClassReload() {
        for (int i = 0; i < 5; i++) {

            ClassLoader cl = TestClassLoader.class.getClassLoader();

            try {
                Class<?> clazz = cl.loadClass(TestClassLoader.class.getCanonicalName());
                TestClassLoader obj = (TestClassLoader)clazz.newInstance();
                obj.print();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void print() {
        System.out.println("1 : " + new Date().toLocaleString());
    }
}
