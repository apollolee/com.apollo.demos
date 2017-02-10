/*
 * 此代码创建于 2017年1月25日 下午3:02:38。
 */
package com.apollo.demos.osgi.base.tester;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.base.api.IExecutors;

@Component
public class ThreadPoolTester {

    private static final Logger s_logger = LoggerFactory.getLogger(ThreadPoolTester.class);

    @Reference
    private IExecutors m_executors;

    public ThreadPoolTester() {
        s_logger.info("New.");
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate.");

        testCommonPool(1000, 10000, 3000, 200, 100);
        testCommonPool(1000, 5000, 1000, 100, 20);
        testDefaultPool("DefaultPool-Slow", 50, 5, 1000, 10000, 3000, 200, 100);
        testDefaultPool("DefaultPool-Fast", 50, 5, 1000, 5000, 1000, 100, 20);
        testFixedPool("FixedPool-Slow", 20, 5, 1000, 10000, 3000, 200, 100);
        testFixedPool("FixedPool-Fast", 20, 5, 1000, 5000, 1000, 100, 20);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.info("Deactivate.");
        m_pools.forEach(es -> es.shutdownNow());
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.info("Modified.");
    }

    private List<ExecutorService> m_pools = new ArrayList<>();

    void testCommonPool(int taskNum, int taskSleep, int taskMinSleep, int submitSleep, int submitMinSleep) {
        mockTasks(m_executors::submit, empty(), taskNum, taskSleep, taskMinSleep, submitSleep, submitMinSleep);
    }

    void testDefaultPool(String name,
                         int count,
                         int poolNum,
                         int taskNum,
                         int taskSleep,
                         int taskMinSleep,
                         int submitSleep,
                         int submitMinSleep) {
        testPool(i -> m_executors.newThreadPool(name + "-" + i, count),
                 poolNum,
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep);
    }

    void testFixedPool(String name,
                       int count,
                       int poolNum,
                       int taskNum,
                       int taskSleep,
                       int taskMinSleep,
                       int submitSleep,
                       int submitMinSleep) {
        testPool(i -> m_executors.newFixedThreadPool(name + "-" + i, count),
                 poolNum,
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep);
    }

    void testPool(Function<Integer, ExecutorService> newPool,
                  int poolNum,
                  int taskNum,
                  int taskSleep,
                  int taskMinSleep,
                  int submitSleep,
                  int submitMinSleep) {
        for (int i = 0; i < poolNum; i++) {
            ExecutorService pool = newPool.apply(i);
            m_pools.add(pool);
            mockTasks(pool::submit, of(pool), taskNum, taskSleep, taskMinSleep, submitSleep, submitMinSleep);
        }
    }

    void mockTasks(Function<Runnable, Future<?>> submit,
                   Optional<ExecutorService> pool,
                   int taskNum,
                   int taskSleep,
                   int taskMinSleep,
                   int submitSleep,
                   int submitMinSleep) {
        m_executors.submit(() -> {
            for (int i = 0; i < taskNum; i++) {
                submit.apply(() -> sleepRandom(taskSleep, taskMinSleep));
                sleepRandom(submitSleep, submitMinSleep);
            }
            pool.ifPresent(p -> submit.apply(() -> p.shutdown()));
        });
    }

    void sleep(long millis) {
        try {
            Thread.sleep(millis);
            Thread.yield();

        } catch (InterruptedException ex) {
        }
    }

    int random(int n) {
        return (int) (Math.random() * n);
    }

    void sleepRandom(int n, int min) {
        sleep(random(n) + min);
    }

    void sleepRandom(int n) {
        sleepRandom(n, 0);
    }

}
