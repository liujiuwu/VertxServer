package test_non_project;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

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
            obj.print("test_ClassLoader");
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
        File file = new File("test_non_project");

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

            obj.print("test_UrlClassLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void test_ClassReload() {
        for (int i = 0; i < 5; i++) {

            ClassLoader cl = TestClassLoader.class.getClassLoader();

            try {
                Class<?> clazz = cl.loadClass(TestClassLoader.class.getCanonicalName());
                TestClassLoader obj = (TestClassLoader)clazz.newInstance();
                obj.print("test_ClassReload");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void test_UrlClassReloader() {
        File file = new File("test_non_project");

        List<URL> list = new ArrayList<>();
        try {
            list.add(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader cl = new URLClassLoader((URL[]) list.toArray(new URL[1]));
        try {
            for (int i = 0; i < 5; i++) {
                Class<?> clazz = cl.loadClass(TestClassLoader.class.getCanonicalName());
                TestClassLoader obj = (TestClassLoader)clazz.newInstance();
                obj.print("test_UrlClassLoader");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void print(String arg) {
        System.out.println(arg + " : " + new Date().toLocaleString());
    }
}
