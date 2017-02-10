package com.apollo.demos.osgi.base.impl.thread

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.{ ThreadPoolExecutor => ThreadPoolExecutor4J }
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS

import com.apollo.demos.osgi.base.impl.Utilities.{ currentState => cs }
import com.apollo.demos.osgi.base.impl.Utilities.{ id => uid }

import ComplexThreadPoolExecutor.handler
import ThreadPoolMonitor.end
import ThreadPoolMonitor.endTask
import ThreadPoolMonitor.start
import ThreadPoolMonitor.startTask

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
    extends ThreadPoolExecutor4J(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, NamedThreadFactory(name), handler) {
  start(this)

  def id = name + "(" + uid(this) + ")"
  def currentState = cs(this)

  override def beforeExecute(thread: Thread, task: Runnable) { super.beforeExecute(thread, task); startTask(this, thread, task) }
  override def afterExecute(task: Runnable, ex: Throwable) { endTask(this, task); super.afterExecute(task, ex) }
  override def terminated() { end(this); super.terminated }
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
