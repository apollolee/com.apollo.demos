/*
 * 此代码创建于 2013-2-17 下午03:56:08。
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCountDemo {

    public static void main(String[] args) throws InterruptedException {
        //Count.count(); //普通类似i++的计数
        AtomicCount.count(); //原子操作的计数
    }

}

/**
 * 普通计数类。
 */
class Count {

    int m_count = 0;

    void increment() {
        m_count++;
    }

    /**
     * 多线程计数。
     */
    static void count() {
        for (int i = 0; i < 10; i++) { //重复10次
            final CountDownLatch startGate = new CountDownLatch(1); //起始闭锁
            final CountDownLatch endGate = new CountDownLatch(100); //终止闭锁

            final Count count = new Count(); //普通计数
            for (int j = 0; j < 100; j++) { //创建100个计数线程
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            startGate.await(); //起始闭锁阻塞

                            try {
                                for (int i = 0; i < 100; i++) { //计数100次自增
                                    count.increment();
                                }

                            } finally {
                                endGate.countDown(); //终止闭锁计数
                            }

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                }).start();
            }

            startGate.countDown(); //所有线程开始计数

            try {
                endGate.await(); //计数线程全部计数完毕
                System.out.println("第" + i + "次：Count = " + count.m_count);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}

/**
 * 原子计数类。
 */
class AtomicCount {

    AtomicInteger m_count = new AtomicInteger(0);

    void increment() {
        m_count.incrementAndGet();
    }

    /**
     * 多线程计数。
     */
    static void count() {
        for (int i = 0; i < 10; i++) { //重复10次
            final CountDownLatch startGate = new CountDownLatch(1); //起始闭锁
            final CountDownLatch endGate = new CountDownLatch(100); //终止闭锁

            final AtomicCount count = new AtomicCount(); //普通计数
            for (int j = 0; j < 100; j++) { //创建100个计数线程
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            startGate.await(); //起始闭锁阻塞

                            try {
                                for (int i = 0; i < 100; i++) { //计数100次自增
                                    count.increment();
                                }

                            } finally {
                                endGate.countDown(); //终止闭锁计数
                            }

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                }).start();
            }

            startGate.countDown(); //所有线程开始计数

            try {
                endGate.await(); //计数线程全部计数完毕
                System.out.println("第" + i + "次：AtomicCount = " + count.m_count.get());

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
