/*
 * 此代码创建于 2013-4-17 下午04:36:08。
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        final int threadSum = 10;
        final CyclicBarrier barrier = new CyclicBarrier(threadSum, new Runnable() {

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                System.out.println("栅栏已打开！");
            }

        });

        for (int i = 0; i < threadSum; i++) {
            new Thread(new Runnable() {

                /**
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "已启动！");

                    try {
                        Thread.sleep(3000);

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "已到底栅栏！");

                    try {
                        barrier.await();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();

                    } catch (BrokenBarrierException ex) {
                        ex.printStackTrace();
                    }
                }

            }).start();
        }
    }

}
