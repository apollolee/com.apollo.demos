package com.apollo.demos.osgi.base.impl.thread

import java.lang.System.currentTimeMillis
import java.lang.System.{ identityHashCode => id }
import java.lang.Thread.currentThread
import java.util.Timer
import java.util.TimerTask

import scala.collection.concurrent.Map
import scala.collection.concurrent.TrieMap

import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.impl.LogHelper
import com.apollo.demos.osgi.base.impl.Utilities.stack

object ThreadPoolMonitor {
  private[ThreadPoolMonitor] class TimeMonitor(val pool: ThreadPoolExecutor, val thread: Thread, val task: Runnable) extends TimerTask {
    val startTime = currentTimeMillis
    val taskName = NamedCallable.name(task)
    if (taskName.isDefined) thread.setName(taskName.get + "@" + thread.getName)
    def run() { warn("Task has elapsed 5 sec.", thread.getName, stack(thread)) }
  }

  val logger = getLogger(getClass)
  val threshold = 5000
  val pools: Map[Int, (ThreadPoolExecutor, Map[Int, TimeMonitor])] = TrieMap[Int, (ThreadPoolExecutor, Map[Int, TimeMonitor])]()
  val timer = new Timer("ThreadPoolMonitor", true)

  def start(pool: ThreadPoolExecutor) { info("Pool is started.", pool.id); pools.putIfAbsent(id(pool), (pool, TrieMap[Int, TimeMonitor]())) }
  def end(pool: ThreadPoolExecutor) { info("Pool is terminated.", pool.id); pools.remove(id(pool)) }

  def startTask(pool: ThreadPoolExecutor, thread: Thread, task: Runnable) {
    val tm = new TimeMonitor(pool, thread, task)
    debug("Task is started.", thread.getName)
    pools(id(pool))._2.put(id(thread), tm)
    timer.schedule(tm, threshold)
  }
  def endTask(pool: ThreadPoolExecutor, task: Runnable) {
    val tm = pools(id(pool))._2.remove(id(currentThread)).get
    info("Task is finished.", tm.thread.getName, currentTimeMillis - tm.startTime)
    if (tm.taskName.isDefined) tm.thread.setName(tm.thread.getName.drop(tm.taskName.get.length + 1))
    tm.cancel();
  }

  def allThreadPool = pools.values.map(_._1).toList

  private def trace(msg: String, arguments: Any*) { LogHelper.trace(logger, msg, arguments: _*) }
  private def debug(msg: String, arguments: Any*) { LogHelper.debug(logger, msg, arguments: _*) }
  private def info(msg: String, arguments: Any*) { LogHelper.info(logger, msg, arguments: _*) }
  private def warn(msg: String, arguments: Any*) { LogHelper.warn(logger, msg, arguments: _*) }
  private def error(msg: String, arguments: Any*) { LogHelper.error(logger, msg, arguments: _*) }
}
