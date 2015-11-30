/*
 * 此代码创建于 2013-4-17 下午05:22:29。
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.Exchanger;

import com.apollo.base.util.BaseUtilities;

public class ExchangerDemo {

    public static void main(String[] args) {
        final Exchanger<long[]> exchanger = new Exchanger<long[]>();

        new Thread(new Runnable() {

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                while (true) {
                    try {
                        long[] data = exchanger.exchange(new long[10]);

                        for (int i = 0; i < data.length; i++) {
                            System.out.print(data[i] + "\t");
                        }

                        System.out.print("\n");

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    Thread.yield();
                }
            }

        }).start();

        new Thread(new Runnable() {

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                int count = 0;
                long[] data = new long[0];

                while (true) {
                    try {
                        data = exchanger.exchange(data);

                        for (int i = 0; i < data.length; i++) {
                            while (true) {
                                if (BaseUtilities.isPrime(count++)) {
                                    data[i] = count - 1;
                                    break;
                                }
                            }

                            Thread.yield();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    Thread.yield();
                }
            }

        }).start();
    }

}
