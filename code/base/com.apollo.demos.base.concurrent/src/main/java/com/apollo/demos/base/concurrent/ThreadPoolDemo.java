/*
 * 此代码创建于 2017年1月23日 下午3:32:12。
 */
package com.apollo.demos.base.concurrent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        test(Executors.newCachedThreadPool(), 100); //无界，但不支持队列，所有提交任务都会创建线程，海量提交时很难控制。好处是可以支持空闲线程回收。
        test(Executors.newFixedThreadPool(20), 100); //无界队列，但不支持空闲线程回收。
        test(ExecutorsX.newCachedThreadPool("CachedThreadPoolDemo"), 100);
        test(ExecutorsX.newFixedThreadPool("FixedThreadPoolDemo", 20), 100);
    }

    static void test(ExecutorService es, long taskNum) {
        monitor(es);
        for (int i = 0; i < taskNum; i++) {
            es.submit(() -> sleep((long) (Math.random() * 10000)));
        }
        es.submit(() -> es.shutdown());
    }

    static void monitor(ExecutorService es) {
        new Thread(() -> {
            while (!es.isShutdown()) {
                System.out.println(es);
                sleep(1000);
                Thread.yield();
            }
        }).start();
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}

class ExecutorsX {

    static class NamedThreadFactory implements ThreadFactory {

        private final ThreadGroup m_group;

        private final AtomicInteger m_threadNumber = new AtomicInteger(1);

        private final String m_namePrefix;

        NamedThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            m_group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            m_namePrefix = name + "-";
        }

        public Thread newThread(Runnable task) {
            Thread thread = new Thread(m_group, task, m_namePrefix + m_threadNumber.getAndIncrement(), 0);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }

    }

    public static ExecutorService newFixedThreadPool(String name, int nThreads) {
        return new ThreadPoolExecutorX(name, nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()); //无法支持空闲线程收缩。
    }

    public static ExecutorService newCachedThreadPool(String name) {
        return new ThreadPoolExecutorX(name, 0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()); //可以有界，但无法支持排队。
    }

}

class ThreadPoolExecutorX extends ThreadPoolExecutor {

    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

    public ThreadPoolExecutorX(String name,
                               int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ExecutorsX.NamedThreadFactory(name), defaultHandler);
    }

    public ThreadPoolExecutorX(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public ThreadPoolExecutorX(String name,
                               int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue,
                               RejectedExecutionHandler handler) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ExecutorsX.NamedThreadFactory(name), handler);
    }

    public ThreadPoolExecutorX(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue,
                               ThreadFactory threadFactory,
                               RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        ThreadPoolMonitor.addThreadPool(this);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable task) {
        super.beforeExecute(thread, task);
        ThreadPoolMonitor.startTask(this, thread, task);
    }

    @Override
    protected void afterExecute(Runnable task, Throwable ex) {
        ThreadPoolMonitor.endTask(this, task);
        super.afterExecute(task, ex);
    }

}

class ThreadPoolMonitor {

    static class TP {

        public TP(ThreadPoolExecutor tp) {
            this.tp = tp;
            tms = new ConcurrentHashMap<>();
        }

        ThreadPoolExecutor tp;

        ConcurrentHashMap<Integer, TimeMonitor> tms;

    }

    static class TimeMonitor extends TimerTask {

        Thread m_thread;

        Runnable m_task;

        long m_startTime;

        public TimeMonitor(Thread thread, Runnable task) {
            m_thread = thread;
            m_task = task;
            m_startTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            System.out.println("Task has elapsed 5 sec.[" + m_thread.getName() + "]\n" + getStack());
        }

        String getStack() {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement st : m_thread.getStackTrace()) {
                sb.append("  ").append(st).append("\n");
            }
            return sb.toString();
        }

    }

    static ConcurrentHashMap<Integer, TP> s_tps = new ConcurrentHashMap<>();

    static Timer s_timer = new Timer("ThreadPoolMonitorTimer", true);

    public static void addThreadPool(ThreadPoolExecutor tp) {
        s_tps.put(id(tp), new TP(tp));
    }

    public static void startTask(ThreadPoolExecutor tp, Thread thread, Runnable task) {
        TimeMonitor tm = new TimeMonitor(thread, task);
        s_tps.get(id(tp)).tms.put(id(task), tm);
        s_timer.schedule(tm, 5000);
    }

    public static void endTask(ThreadPoolExecutor tp, Runnable task) {
        TimeMonitor tm = s_tps.get(id(tp)).tms.remove(id(task));
        System.out.println("Task is finished. Elapsed " + (System.currentTimeMillis() - tm.m_startTime) / 1000 + " sec.[" + tm.m_thread.getName() + "]");
        tm.cancel();
    }

    public static int id(Object obj) {
        return System.identityHashCode(obj);
    }

}
