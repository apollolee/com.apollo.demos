/*
 * 此代码创建于 2013-8-4 下午3:00:19。
 */
package com.apollo.demos.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

    public static void main(String[] args) {
        callableWait();
        //runnableWait();
    }

    protected static void callableWait() {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        List<Future<String>> results = new ArrayList<Future<String>>();

        for (int i = 0; i < 10; i++) {
            final int result = i;
            results.add(service.submit(new Callable<String>() {

                /**
                 * @see java.util.concurrent.Callable#call()
                 */
                @Override
                public String call() throws Exception {
                    Thread.sleep((long) (Math.random() * 2000));
                    return String.valueOf(result);
                }

            }));
        }

        for (Future<String> result : results) {
            try {
                System.out.println(result.get());

            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }

        service.shutdown();

        System.out.println("全部结束！");
    }

    protected static void runnableWait() {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (int i = 0; i < 10; i++) {
            final int result = i;
            service.submit(new Runnable() {

                /**
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 2000));

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println(result);
                }

            });
        }

        service.shutdown();

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            System.out.println("全部结束！");

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
