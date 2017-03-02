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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;
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
public class PoolTester {

    class Calculator extends RecursiveTask<Integer> {

        private static final long serialVersionUID = 1L;

        private static final int THRESHOLD = 2;

        private final int m_start, m_end, m_taskSleep, m_taskMinSleep;

        public Calculator(int start, int end, int taskSleep, int taskMinSleep) {
            m_start = start;
            m_end = end;
            m_taskSleep = taskSleep;
            m_taskMinSleep = taskMinSleep;
        }

        @Override
        protected Integer compute() {
            sleepRandom(m_taskSleep, m_taskMinSleep);
            int sum = 0;
            if ((m_end - m_start) < THRESHOLD) {
                for (int i = m_start; i <= m_end; i++) {
                    sum += i;
                }

            } else {
                int middle = (m_start + m_end) / 2;
                ForkJoinTask<Integer> left = m_executors.named(m_start + "+" + middle, new Calculator(m_start,
                                                                                                      middle,
                                                                                                      m_taskSleep,
                                                                                                      m_taskMinSleep));
                ForkJoinTask<Integer> right = m_executors.named((middle + 1) + "+" + m_end, new Calculator(middle + 1,
                                                                                                           m_end,
                                                                                                           m_taskSleep,
                                                                                                           m_taskMinSleep));
                left.fork();
                right.fork();

                sum = left.join() + right.join();
            }
            return sum;
        }

    }

    private static final Logger s_logger = LoggerFactory.getLogger(PoolTester.class);

    @Reference
    private IExecutors m_executors;

    public PoolTester() {
        s_logger.info("New.");
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate.");

        testCommonPool("CommonPool-Slow", 1000, 10000, 3000, 200, 100);
        testCommonPool("CommonPool-Fast", 1000, 5000, 1000, 100, 20);

        testDefaultPool("DefaultPool-Slow", 50, 5, 1000, 10000, 3000, 200, 100, true);
        testDefaultPool("DefaultPool-Fast", 50, 5, 1000, 5000, 1000, 100, 20, true);

        testFixedPool("FixedPool-Slow", 20, 5, 1000, 10000, 3000, 200, 100, true);
        testFixedPool("FixedPool-Fast", 20, 5, 1000, 5000, 1000, 100, 20, true);

        testForkJoinPool("ForkJoinPool-Slow", 10, 5, 20, 500, 100, 2000, 200);
        testForkJoinPool("ForkJoinPool-Fast", 10, 5, 5, 500, 100, 1000, 100);

        testForkJoinPool_NoForkJoin("ForkJoinPool-NoForkJoin-Slow", 20, 5, 1000, 10000, 3000, 200, 100, true);
        testForkJoinPool_NoForkJoin("ForkJoinPool-NoForkJoin-Fast", 20, 5, 1000, 5000, 1000, 100, 20, true);

        testDefaultPool("DefaultPool-NoTaskName", 10, 2, 500, 5000, 1000, 100, 20, false);
        testFixedPool("FixedPool-NoTaskName", 5, 2, 500, 5000, 1000, 100, 20, false);
        testForkJoinPool_NoForkJoin("ForkJoinPool-NoForkJoin-NoTaskName", 5, 2, 500, 5000, 1000, 100, 20, false);
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

    void testCommonPool(String mockName, int taskNum, int taskSleep, int taskMinSleep, int submitSleep, int submitMinSleep) {
        mock(m_executors::submit, mockName, empty(), taskNum, taskSleep, taskMinSleep, submitSleep, submitMinSleep);
    }

    void testDefaultPool(String name,
                         int count,
                         int poolNum,
                         int taskNum,
                         int taskSleep,
                         int taskMinSleep,
                         int submitSleep,
                         int submitMinSleep,
                         boolean hasTaskName) {
        testPool(name,
                 i -> m_executors.newThreadPool(name + "-" + i, count),
                 poolNum,
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep,
                 hasTaskName);
    }

    void testFixedPool(String name,
                       int count,
                       int poolNum,
                       int taskNum,
                       int taskSleep,
                       int taskMinSleep,
                       int submitSleep,
                       int submitMinSleep,
                       boolean hasTaskName) {
        testPool(name,
                 i -> m_executors.newFixedThreadPool(name + "-" + i, count),
                 poolNum,
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep,
                 hasTaskName);
    }

    void testForkJoinPool(String name, int count, int poolNum, int submissionNum, int taskSleep, int taskMinSleep, int end, int endMin) {
        for (int i = 0; i < poolNum; i++) {
            String poolName = name + "-" + i;
            ForkJoinPool pool = m_executors.newForkJoinPool(poolName, count);
            m_executors.submit("Mock-Tasks-" + poolName, () -> {
                List<Integer> ends = new ArrayList<>();
                List<ForkJoinTask<Integer>> tasks = new ArrayList<>();
                for (int j = 0; j < submissionNum; j++) {
                    ends.add(random(end) + endMin);
                    tasks.add(m_executors.named("Submission-" + j, new Calculator(1, ends.get(j), taskSleep, taskMinSleep)));
                }
                tasks.forEach(pool::submit);
                for (int j = 0, size = ends.size(); j < size; j++) {
                    try {
                        s_logger.debug(poolName + "-Submission-" + j + ": 1 ~ " + ends.get(j) + " = " + tasks.get(j).get());

                    } catch (Throwable ex) {
                        s_logger.error(poolName + "-Submission-" + j + " get is failed.", ex);
                    }
                }
                pool.shutdown();
            });
        }
    }

    void testForkJoinPool_NoForkJoin(String name,
                                     int count,
                                     int poolNum,
                                     int taskNum,
                                     int taskSleep,
                                     int taskMinSleep,
                                     int submitSleep,
                                     int submitMinSleep,
                                     boolean hasTaskName) {
        testPool(name,
                 i -> m_executors.newForkJoinPool(name + "-" + i, count),
                 poolNum,
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep,
                 hasTaskName);
    }

    void testPool(String name,
                  Function<Integer, ExecutorService> newPool,
                  int poolNum,
                  int taskNum,
                  int taskSleep,
                  int taskMinSleep,
                  int submitSleep,
                  int submitMinSleep,
                  boolean hasTaskName) {
        for (int i = 0; i < poolNum; i++) {
            ExecutorService pool = newPool.apply(i);
            m_pools.add(pool);
            mock((n, t) -> hasTaskName ? pool.submit(m_executors.namedCallable(n, t)) : pool.submit(t),
                 name + "-" + i,
                 of(pool),
                 taskNum,
                 taskSleep,
                 taskMinSleep,
                 submitSleep,
                 submitMinSleep);
        }
    }

    void mock(BiFunction<String, Runnable, Future<?>> submit,
              String mockName,
              Optional<ExecutorService> pool,
              int taskNum,
              int taskSleep,
              int taskMinSleep,
              int submitSleep,
              int submitMinSleep) {
        m_executors.submit("Mock-Tasks-" + mockName, () -> {
            for (int i = 0; i < taskNum; i++) {
                submit.apply("Task-" + i, () -> sleepRandom(taskSleep, taskMinSleep));
                sleepRandom(submitSleep, submitMinSleep);
            }
            pool.ifPresent(p -> submit.apply("Mock-Shutdown-" + mockName, () -> p.shutdown()));
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
