package com.apollo.demos.osgi.base.impl.thread

import java.lang.Thread.sleep
import java.lang.Thread.`yield`

import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.impl.LogHelper
import com.apollo.demos.osgi.base.impl.Utilities.startThread

import PoolMonitor.allForkJoinPool
import PoolMonitor.allThreadPool

object PoolTracer {
  val logger = getLogger(getClass)
  val heartbeat = 5000L
  @volatile private var isStarted = false

  def isTracing = isStarted
  def startTrace() { if (!isStarted) { isStarted = true; startThread("PoolTracer", while (isStarted) { toLog; sleep(heartbeat); `yield` }) } }
  def endTrace() { isStarted = false }

  private def toLog() { allForkJoinPool.foreach(toLog); allThreadPool.foreach(toLog) }
  private def toLog(pool: ForkJoinPool) { val cs = pool.currentState; trace(pool.id + "#", cs._1, cs._3, cs._4, cs._5, cs._6, cs._7, cs._8) }
  private def toLog(pool: ThreadPoolExecutor) { val cs = pool.currentState; trace(pool.id + "#", cs._1, cs._2, cs._3, cs._4, cs._5) }

  private def toConsole() { allForkJoinPool.foreach(toConsole); allThreadPool.foreach(toConsole) }
  private def toConsole(pool: ForkJoinPool) { val cs = pool.currentState; printf("%s#[%s][%s][%s][%s][%s][%s][%s]", pool.id, cs._1, cs._3, cs._4, cs._5, cs._6, cs._7, cs._8) }
  private def toConsole(pool: ThreadPoolExecutor) { val cs = pool.currentState; printf("%s#[%s][%s][%s][%s][%s]", pool.id, cs._1, cs._2, cs._3, cs._4, cs._5) }

  private def trace(msg: String, arguments: Any*) { LogHelper.trace(logger, msg, arguments: _*) }
}
