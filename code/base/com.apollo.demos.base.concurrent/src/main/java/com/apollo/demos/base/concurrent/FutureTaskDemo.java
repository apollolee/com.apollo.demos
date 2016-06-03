/*
 * 此代码创建于 2013-4-17 上午09:14:22。
 */
package com.apollo.demos.base.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

    /**
     * 任务，耗费五秒，然后返回一个字符串。
     */
    private static FutureTask<String> s_task = new FutureTask<String>(new Callable<String>() {

        /**
         * @see java.util.concurrent.Callable#call()
         */
        @Override
        public String call() throws Exception {
            System.out.println("任务线程开始！");
            Thread.sleep(5000);
            System.out.println("任务线程结束！");

            return "TestFutureTask";
        }

    });

    public static void main(String[] args) {
        new Thread(s_task).start();

        try {
            System.out.println("主线程获取： " + s_task.get());

        } catch (InterruptedException ex) {
            ex.printStackTrace();

        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

}
