/*
 * 此代码创建于 2015年6月5日 上午8:55:02。
 */
package com.apollo.demos.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SingletonDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
         * 多运行几次会出现以下错误：
         * 开始测试Singleton1...
         *
         * Singleton1发现错误：
         * 0.com.apollo.demos.concurrent.Singleton1@3d4eac69
         * 1.com.apollo.demos.concurrent.Singleton1@42a57993
         * 2.com.apollo.demos.concurrent.Singleton1@42a57993
         * 3.com.apollo.demos.concurrent.Singleton1@42a57993
         * 4.com.apollo.demos.concurrent.Singleton1@42a57993
         *
         * 结束测试Singleton1。
         */
        Singleton1.test();
        //Singleton2.test();
        //Singleton3.test();
    }

}

/*
 * 一般常见的单例，多线程下会有错误。
 */
class Singleton1 {

    static Singleton1 s_singleton = null;

    static Singleton1 getSingleton() {
        if (s_singleton == null) {
            s_singleton = new Singleton1();
        }

        return s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n开始测试Singleton1...");

        List<Future<Singleton1>> results = null;

        for (int i = 0; i < 1000; i++) {
            Singleton1.s_singleton = null;
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton1发现错误：");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n结束测试Singleton1。\n");
    }

    static List<Future<Singleton1>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //准备闭锁
        CountDownLatch startGate = new CountDownLatch(1); //起始闭锁

        List<Future<Singleton1>> results = new ArrayList<Future<Singleton1>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //等待所有线程准备完毕

            startGate.countDown(); //所有线程开始计数

            Singleton1 first = results.get(0).get();
            for (Future<Singleton1> result : results) {
                Singleton1 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton1> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton1>() {

            @Override
            public Singleton1 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton1.getSingleton();
            }

        };
    }

}

/*
 * 双重检查加锁模式（DCL），被理论上证明是反模式，会有几率造成获取到一个初始化一半的对象，TODO 目前未有有效的代码证明有问题。
 */
class Singleton2 {

    static Singleton2 s_singleton = null;

    static Singleton2 getSingleton() {

        if (s_singleton == null) {
            synchronized (Singleton2.class) {
                if (s_singleton == null) {
                    s_singleton = new Singleton2();
                }
            }
        }

        return s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n开始测试Singleton2...");

        List<Future<Singleton2>> results = null;

        for (int i = 0; i < 1000; i++) {
            Singleton2.s_singleton = null;
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton2发现错误：");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n结束测试Singleton2。\n");
    }

    static List<Future<Singleton2>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //准备闭锁
        CountDownLatch startGate = new CountDownLatch(1); //起始闭锁

        List<Future<Singleton2>> results = new ArrayList<Future<Singleton2>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //等待所有线程准备完毕

            startGate.countDown(); //所有线程开始计数

            Singleton2 first = results.get(0).get();
            for (Future<Singleton2> result : results) {
                Singleton2 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton2> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton2>() {

            @Override
            public Singleton2 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton2.getSingleton();
            }

        };
    }

}

/*
 * 延迟初始化，完全安全的方式。TODO 由于无法重置初始化类，目前无法有效的在单进程中验证。
 */
class Singleton3 {

    private static final class Singleton3Holder {

        static Singleton3 s_singleton = new Singleton3();

    }

    static Singleton3 getSingleton() {
        return Singleton3Holder.s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n开始测试Singleton3...");

        List<Future<Singleton3>> results = null;

        for (int i = 0; i < 1; i++) {
            //这里没法重置，只需1次循环
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton3发现错误：");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n结束测试Singleton3。\n");
    }

    static List<Future<Singleton3>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //准备闭锁
        CountDownLatch startGate = new CountDownLatch(1); //起始闭锁

        List<Future<Singleton3>> results = new ArrayList<Future<Singleton3>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //等待所有线程准备完毕

            startGate.countDown(); //所有线程开始计数

            Singleton3 first = results.get(0).get();
            for (Future<Singleton3> result : results) {
                Singleton3 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton3> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton3>() {

            @Override
            public Singleton3 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton3.getSingleton();
            }

        };
    }

}
