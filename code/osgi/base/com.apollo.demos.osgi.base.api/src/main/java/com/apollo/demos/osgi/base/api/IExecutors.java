/*
 * 此代码创建于 2017年2月4日 上午9:02:30。
 */
package com.apollo.demos.osgi.base.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface IExecutors {

    Future<?> submit(Runnable task);

    <T> Future<T> submit(Callable<T> task);

    <T> Future<T> submit(Runnable task, T result);

    ExecutorService newThreadPool(String name, int count);

    ExecutorService newFixedThreadPool(String name, int count);
}
