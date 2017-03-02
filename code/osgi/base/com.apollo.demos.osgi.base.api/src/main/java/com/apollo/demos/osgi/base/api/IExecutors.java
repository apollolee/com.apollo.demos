/*
 * 此代码创建于 2017年2月4日 上午9:02:30。
 */
package com.apollo.demos.osgi.base.api;

import static java.util.concurrent.Executors.callable;
import static java.util.concurrent.ForkJoinTask.adapt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

public interface IExecutors {

    default Callable<Object> namedCallable(String name, Runnable task) {
        return named(name, callable(task));
    }

    default <T> Callable<T> namedCallable(String name, Runnable task, T result) {
        return named(name, callable(task, result));
    }

    <T> Callable<T> named(String name, Callable<T> task);

    default ForkJoinTask<?> namedForkJoinTask(String name, Runnable task) {
        return named(name, adapt(task));
    }

    default <T> ForkJoinTask<T> namedForkJoinTask(String name, Runnable task, T result) {
        return named(name, adapt(task, result));
    }

    default <T> ForkJoinTask<T> namedForkJoinTask(String name, Callable<T> task) {
        return named(name, adapt(task));
    }

    <T> ForkJoinTask<T> named(String name, ForkJoinTask<T> task);

    default Future<?> submit(String name, Runnable task) {
        return submit(name, callable(task));
    }

    default <T> Future<T> submit(String name, Runnable task, T result) {
        return submit(name, callable(task, result));
    }

    <T> Future<T> submit(String name, Callable<T> task);

    ExecutorService newThreadPool(String name, int count);

    ExecutorService newFixedThreadPool(String name, int count);

    default ForkJoinPool newForkJoinPool(String name) {
        return newForkJoinPool(name, Runtime.getRuntime().availableProcessors());
    }

    ForkJoinPool newForkJoinPool(String name, int parallelism);

}
