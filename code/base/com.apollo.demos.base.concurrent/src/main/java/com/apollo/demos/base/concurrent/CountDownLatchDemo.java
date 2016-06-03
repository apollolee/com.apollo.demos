/*
 * 此代码创建于 2013-4-17 上午09:35:41。
 */
package com.apollo.demos.base.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        final int threadSum = 100; //线程数
        final CountDownLatch readyGate = new CountDownLatch(threadSum); //预备起始闭锁
        final CountDownLatch startGate = new CountDownLatch(1); //起始闭锁
        final CountDownLatch endGate = new CountDownLatch(threadSum); //终止闭锁

        for (int i = 0; i < threadSum; i++) {
            new Thread(new Runnable() {

                /**
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "准备！");

                    readyGate.countDown();

                    try {
                        startGate.await();

                        try {
                            System.out.println(Thread.currentThread().getName() + "开始！");

                            Thread.sleep(3000);

                        } finally {
                            System.out.println(Thread.currentThread().getName() + "结束！");
                            endGate.countDown();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            }).start();
        }

        try {
            long startTime = System.currentTimeMillis();

            readyGate.await();

            System.out.println("开始门已打开！");

            startGate.countDown();

            endGate.await();

            System.out.println("结束门已打开！已耗费时间：" + (System.currentTimeMillis() - startTime) + "毫秒");

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
