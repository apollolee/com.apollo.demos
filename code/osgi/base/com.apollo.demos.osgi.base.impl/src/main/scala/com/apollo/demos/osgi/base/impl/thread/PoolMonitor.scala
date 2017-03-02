package com.apollo.demos.osgi.base.impl.thread

import java.lang.System.currentTimeMillis
import java.lang.System.{ identityHashCode => id }
import java.util.Timer
import java.util.TimerTask

import scala.collection.concurrent.Map
import scala.collection.concurrent.TrieMap

import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.impl.LogHelper
import com.apollo.demos.osgi.base.impl.Utilities.stack

object PoolMonitor {
  private[PoolMonitor] class TimeMonitor(val thread: Thread, task: AnyRef) extends TimerTask {
    val startTime = currentTimeMillis
    val taskName = task match {
      case task: NamedForkJoinTask[_] => Some(task.name)
      case task: Runnable             => NamedCallable.name(task)
      case _                          => None
    }
    val threadName = if (taskName.isDefined) { thread.setName(taskName.get + "@" + thread.getName); thread.getName } else thread.getName
    def run() { warn("Task has elapsed 5 sec.", threadName, stack(thread)) }
  }

  val logger = getLogger(getClass)
  val threshold = 5000
  val pools: Map[Int, (NamedPool, Map[(Int, Int), TimeMonitor])] = TrieMap[Int, (NamedPool, Map[(Int, Int), TimeMonitor])]()
  val timer = new Timer("PoolMonitor", true)

  def start(pool: NamedPool) { info("Pool is started.", pool.id); pools.putIfAbsent(id(pool), (pool, TrieMap[(Int, Int), TimeMonitor]())) }
  def end(pool: NamedPool) { info("Pool is terminated.", pool.id); pools.remove(id(pool)) }

  def startTask(pool: NamedPool, thread: Thread, task: AnyRef) {
    val tm = new TimeMonitor(thread, task)
    debug("Task is started.", thread.getName)
    pools(id(pool))._2.put((id(thread), id(task)), tm)
    timer.schedule(tm, threshold)
  }
  def endTask(pool: NamedPool, thread: Thread, task: AnyRef) {
    val tm = pools(id(pool))._2.remove((id(thread), id(task))).get
    info("Task is finished.", tm.thread.getName, currentTimeMillis - tm.startTime)
    if (tm.taskName.isDefined) tm.thread.setName(tm.thread.getName.drop(tm.taskName.get.length + 1))
    tm.cancel();
  }

  def allThreadPool = pools.values.map(_._1).filter(_.isInstanceOf[ThreadPoolExecutor]).map(_.asInstanceOf[ThreadPoolExecutor]).toList
  def allForkJoinPool = pools.values.map(_._1).filter(_.isInstanceOf[ForkJoinPool]).map(_.asInstanceOf[ForkJoinPool]).toList

  private def trace(msg: String, arguments: Any*) { LogHelper.trace(logger, msg, arguments: _*) }
  private def debug(msg: String, arguments: Any*) { LogHelper.debug(logger, msg, arguments: _*) }
  private def info(msg: String, arguments: Any*) { LogHelper.info(logger, msg, arguments: _*) }
  private def warn(msg: String, arguments: Any*) { LogHelper.warn(logger, msg, arguments: _*) }
  private def error(msg: String, arguments: Any*) { LogHelper.error(logger, msg, arguments: _*) }
}
