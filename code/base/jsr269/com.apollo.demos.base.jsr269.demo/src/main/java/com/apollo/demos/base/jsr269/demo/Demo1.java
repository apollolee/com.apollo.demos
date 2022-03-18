/*
 * 此代码创建于 2022年3月17日 下午10:59:21。
 */
package com.apollo.demos.base.jsr269.demo;

import static com.apollo.base.annotation.Log.Level.Debug;
import static com.apollo.base.annotation.Log.Level.Error;
import static com.apollo.base.annotation.Log.Level.Info;
import static com.apollo.base.annotation.Log.Level.Trace;
import static com.apollo.base.annotation.Log.Level.Warn;
import static com.apollo.base.annotation.Log.Location.Core;
import static com.apollo.base.annotation.Log.Location.EdgeIn;
import static com.apollo.base.annotation.Log.Location.EdgeOut;

import java.util.List;

import com.apollo.base.annotation.Log;

public class Demo1 {

    public static void main(String[] args) {
        hello("log");
        add(66, 88);
        sleep();

        Demo1 demo1 = new Demo1();
        demo1.setX(68);
        demo1.getX();

        demo1.size(null);
    }

    @Log
    public static void hello(String name) {
        System.out.println("Hello " + name + "!");
    }

    @Log(level = Debug, location = EdgeIn)
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

    @Log(level = Error)
    public int getX() {
        return m_x;
    }

    @Log(level = Trace)
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

    @Log(level = Info, location = Core)
    private void sizeX(List<String> text) {
        System.out.println(text.size());
    }

}
