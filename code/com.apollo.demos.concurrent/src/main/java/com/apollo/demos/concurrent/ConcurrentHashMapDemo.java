/*
 * 此代码创建于 2013-3-12 下午03:03:57。
 */
package com.apollo.demos.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentHashMapDemo {

    public static void main(String[] args) throws InterruptedException {
        PerformanceCompare.test();
    }

}

class PerformanceCompare {

    static void test() throws InterruptedException {
        test(Collections.synchronizedMap(new HashMap<Long, Long>()), 10);
        test(new ConcurrentHashMap<Long, Long>(), 10);
    }

    static void test(Map<Long, Long> map, int size) throws InterruptedException {
        System.out.println("\n开始测试 " + map.getClass().getName());

        long sum = 0;
        for (int i = 0; i < size; i++) {
            map.clear();
            sum += test(map);
        }

        System.out.println("结束测试 " + map.getClass().getName() + " : 平均消耗：" + sum / size + " " + TimeUnit.MILLISECONDS);
    }

    static long test(Map<Long, Long> map) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(200);

        CountDownLatch readyGate = new CountDownLatch(200); //准备闭锁
        CountDownLatch startGate = new CountDownLatch(1); //起始闭锁
        CountDownLatch endGate = new CountDownLatch(200); //终止闭锁

        for (int i = 0; i < 100; i++) {
            executor.execute(getReadTask(map, readyGate, startGate, endGate));

            if (map instanceof ConcurrentHashMap) {
                executor.execute(getWriteTask((ConcurrentHashMap<Long, Long>) map, readyGate, startGate, endGate));

            } else {
                executor.execute(getWriteTask(map, readyGate, startGate, endGate));
            }
        }

        readyGate.await(); //等待所有线程准备完毕

        long startTime = System.currentTimeMillis();
        startGate.countDown(); //所有线程开始计数

        endGate.await(); //计数线程全部计数完毕
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(map.getClass().getName() + " : 共计消耗：" + elapsed + " " + TimeUnit.MILLISECONDS + " Map.Size = " + map.size());

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

        return elapsed;
    }

    static Runnable getReadTask(final Map<Long, Long> map,
                                final CountDownLatch readyGate,
                                final CountDownLatch startGate,
                                final CountDownLatch endGate) {
        return new Runnable() {

            @Override
            public void run() {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                Long key = null;

                for (int i = 0; i < 10000; i++) {
                    if (key == null) {
                        key = getRandom();
                    }

                    key = map.get(key);
                }

                endGate.countDown();
            }

        };
    }

    static Runnable getWriteTask(final Map<Long, Long> map,
                                 final CountDownLatch readyGate,
                                 final CountDownLatch startGate,
                                 final CountDownLatch endGate) {
        return new Runnable() {

            @Override
            public void run() {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                for (int i = 0; i < 10000; i++) {
                    long key = getRandom();
                    long value = getRandom();

                    synchronized (map) {
                        if (map.containsKey(key)) {
                            i--;

                        } else {
                            map.put(key, value);
                        }
                    }
                }

                endGate.countDown();
            }

        };
    }

    static Runnable getWriteTask(final ConcurrentHashMap<Long, Long> map,
                                 final CountDownLatch readyGate,
                                 final CountDownLatch startGate,
                                 final CountDownLatch endGate) {
        return new Runnable() {

            @Override
            public void run() {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                for (int i = 0; i < 10000; i++) {
                    long key = getRandom();
                    long value = getRandom();

                    if (null != map.putIfAbsent(key, value)) {
                        i--;
                    }
                }

                endGate.countDown();
            }

        };
    }

    static long getRandom() {
        return (long) (Math.random() * Long.MAX_VALUE); //随机数范围可以调整MAP分布区间的大小
    }

}
