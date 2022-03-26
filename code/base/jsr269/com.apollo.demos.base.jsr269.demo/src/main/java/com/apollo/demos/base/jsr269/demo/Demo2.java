/*
 * 此代码创建于 2022年3月17日 下午10:59:21。
 */
package com.apollo.demos.base.jsr269.demo;

import static com.apollo.base.annotation.Log.Level.Debug;
import static com.apollo.base.annotation.Log.Level.Warn;
import static com.apollo.base.annotation.Log.Location.EdgeOut;
import static java.util.Arrays.asList;
import static java.util.Arrays.setAll;

import java.util.List;

import com.apollo.base.annotation.Log;

@Log(mapper = "com.apollo.demos.base.jsr269.demo.Demo1.Mapper")
public class Demo2 {

    public static void main(String[] args) {
        hello("log");
        add(66, 88);
        sleep();

        Demo2 demo1 = new Demo2();
        demo1.setX(68);
        demo1.getX();

        demo1.size(null);

        list(50);
        listX(50);
    }

    public static void hello(String name) {
        System.out.println("Hello " + name + "!");
    }

    static int add(int a, int b) {
        return a + b;
    }

    protected static void sleep() {
        try {
            sleepX();

        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Log(level = Warn, location = EdgeOut)
    private static final void sleepX() throws Throwable {
        Thread.sleep(500);
    }

    int m_x;

    public int getX() {
        return m_x;
    }

    void setX(int x) {
        m_x = x;
    }

    @Log(ignore = true)
    protected final void size(List<String> texts) {
        try {
            sizeX(texts);

        } catch (Throwable ex) {
        }
    }

    @Log(level = Debug, location = EdgeOut)
    private void sizeX(List<String> text) {
        System.out.println(text.size());
    }

    @Log
    static List<Integer> list(int size) {
        Integer[] data = new Integer[size];
        setAll(data, i -> i);
        return asList(data);
    }

    static List<Integer> listX(int size) {
        Integer[] data = new Integer[size];
        setAll(data, i -> i);
        return asList(data);
    }
}
