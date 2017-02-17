/*
 * 此代码创建于 2017年2月4日 上午9:02:30。
 */
package com.apollo.demos.osgi.base.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface IExecutors {

    Callable<Object> namedCallable(String name, Runnable task);

    <T> Callable<T> namedCallable(String name, Runnable task, T result);

    <T> Callable<T> named(String name, Callable<T> task);

    Future<?> submit(String name, Runnable task);

    <T> Future<T> submit(String name, Runnable task, T result);

    <T> Future<T> submit(String name, Callable<T> task);

    ExecutorService newThreadPool(String name, int count);

    ExecutorService newFixedThreadPool(String name, int count);

}
