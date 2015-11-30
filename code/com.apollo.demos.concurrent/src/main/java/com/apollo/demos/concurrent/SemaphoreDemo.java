/*
 * 此代码创建于 2013-4-17 上午10:14:02。
 */
package com.apollo.demos.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    /**
     * 获得随机数。
     * @return 随机数。
     */
    private static String random() {
        return String.valueOf((int) (Math.random() * 1000));
    }

    public static void main(String[] args) {
        final BoundedSet<String> set = new BoundedSet<String>(10);

        new Thread(new Runnable() { //添加线程。

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                while (true) {
                    try {
                        if (set.add(random())) {
                            System.out.println("添加成功！集合大小为：" + set.size());
                            Thread.yield();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }).start();

        new Thread(new Runnable() { //移除线程。

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                while (true) {
                    if (set.remove((random()))) {
                        System.out.println("移除成功！集合大小为：" + set.size());
                        Thread.yield();
                    }
                }
            }

        }).start();
    }

}

/**
 * 有界集合。
 */
class BoundedSet<T> {

    /**
     * 信号量。
     */
    private final Semaphore m_semaphore;

    /**
     * 无界集合。
     */
    private final Set<T> m_set;

    /**
     * 构造方法。
     * @param bound 界值。
     */
    public BoundedSet(int bound) {
        m_semaphore = new Semaphore(bound);
        m_set = Collections.synchronizedSet(new HashSet<T>());
    }

    /**
     * 添加一个对象。
     * @param object 对象。
     * @return 标识添加是否成功。
     * @throws InterruptedException 获得信号量时，如果阻塞，此时有中断信号则抛此异常。
     */
    public boolean add(T object) throws InterruptedException {
        m_semaphore.acquire(); //获得信号量，如果没有就阻塞，等到有其他信号量释放。

        boolean wasAdded = false; //标识是否已经添加。

        try {
            wasAdded = m_set.add(object);

            return wasAdded;

        } finally {
            if (!wasAdded) { //如果没有添加成功，就释放一个信号量。
                m_semaphore.release();
            }
        }
    }

    /**
     * 移除一个对象。
     * @param object 对象。
     * @return 标识移除对象是否成功。
     */
    public boolean remove(Object object) {
        boolean wasRemoved = m_set.remove(object);

        if (wasRemoved) { //如果移除成功，则释放一个信号量。
            m_semaphore.release();
        }

        return wasRemoved;
    }

    /**
     * 集合大小。
     * @return 集合大小。
     */
    public int size() {
        return m_set.size();
    }

}
