package com.apollo.demos.osgi.base.impl.thread

import java.lang.System.getSecurityManager
import java.lang.Thread.NORM_PRIORITY
import java.lang.Thread.currentThread
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.{ ThreadPoolExecutor => ThreadPoolExecutor4J }
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.atomic.AtomicInteger

import com.apollo.demos.osgi.base.impl.Utilities.{ currentState => cs }
import com.apollo.demos.osgi.base.impl.Utilities.{ id => uid }

import ComplexThreadPoolExecutor.handler
import PoolMonitor.endTask
import PoolMonitor.startTask

object ThreadPoolExecutor {
  private val defaultHandler: RejectedExecutionHandler = new AbortPolicy()

  def apply(name: String, count: Int): ThreadPoolExecutor = apply(name, count, count, 0L, SECONDS, new LinkedBlockingQueue[Runnable]())

  def apply(name: String,
            corePoolSize: Int,
            maximumPoolSize: Int,
            keepAliveTime: Long,
            unit: TimeUnit,
            workQueue: BlockingQueue[Runnable]): ThreadPoolExecutor =
    apply(name, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, defaultHandler)

  def apply(name: String,
            corePoolSize: Int,
            maximumPoolSize: Int,
            keepAliveTime: Long,
            unit: TimeUnit,
            workQueue: BlockingQueue[Runnable],
            handler: RejectedExecutionHandler) =
    new ThreadPoolExecutor(name, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler)
}

class ThreadPoolExecutor(val name: String,
                         corePoolSize: Int,
                         maximumPoolSize: Int,
                         keepAliveTime: Long,
                         unit: TimeUnit,
                         workQueue: BlockingQueue[Runnable],
                         handler: RejectedExecutionHandler)
    extends ThreadPoolExecutor4J(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler)
    with NamedPool {
  class NamedThreadFactory extends ThreadFactory {
    private[this] val group: ThreadGroup = Option(getSecurityManager).map(_.getThreadGroup).getOrElse(currentThread.getThreadGroup)
    private[this] val threadNumber = new AtomicInteger(1)
    private[this] val nameSuffix = "@" + id

    def newThread(task: Runnable) = {
      val thread = new Thread(group, task, "", 0)
      thread.setName("Thread-" + threadNumber.getAndIncrement + "(" + uid(thread) + ")" + nameSuffix)
      if (thread.isDaemon) thread.setDaemon(false)
      if (thread.getPriority != NORM_PRIORITY) thread.setPriority(NORM_PRIORITY)
      thread
    }
  }

  setThreadFactory(new NamedThreadFactory)

  def currentState = cs(this)

  override def beforeExecute(thread: Thread, task: Runnable) { super.beforeExecute(thread, task); startTask(this, thread, task) }
  override def afterExecute(task: Runnable, ex: Throwable) { endTask(this, currentThread, task); super.afterExecute(task, ex) }
  override def terminated { tryEnd }
}

object ComplexThreadPoolExecutor {
  private val handler = new RejectedExecutionHandler() {
    def rejectedExecution(task: Runnable, executor: ThreadPoolExecutor4J) {
      executor.asInstanceOf[ComplexThreadPoolExecutor].fixed.submit(task)
    }
  }

  def apply(name: String, count: Int): ComplexThreadPoolExecutor = apply(name, (count * 0.2).intValue, count)

  def apply(name: String, corePoolSize: Int, maximumPoolSize: Int): ComplexThreadPoolExecutor = apply(name, corePoolSize, maximumPoolSize, 30, SECONDS)

  def apply(name: String, corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit) =
    new ComplexThreadPoolExecutor(name, corePoolSize, maximumPoolSize, keepAliveTime, unit)
}

class ComplexThreadPoolExecutor(name: String,
                                corePoolSize: Int,
                                maximumPoolSize: Int,
                                keepAliveTime: Long,
                                unit: TimeUnit)
    extends ThreadPoolExecutor(name + "-Cached", 0, maximumPoolSize - corePoolSize, keepAliveTime, unit, new SynchronousQueue[Runnable](), handler) {
  private val fixed = ThreadPoolExecutor(name + "-Fixed", corePoolSize)

  override def shutdown() = { fixed.shutdown; super.shutdown }
  override def shutdownNow() = { val all = fixed.shutdownNow; all.addAll(super.shutdownNow); all }
}
