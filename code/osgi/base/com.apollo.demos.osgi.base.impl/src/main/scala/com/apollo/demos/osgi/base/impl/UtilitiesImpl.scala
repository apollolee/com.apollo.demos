package com.apollo.demos.osgi.base.impl

import java.lang.Integer.toHexString
import java.lang.System.identityHashCode
import java.util.concurrent.ThreadPoolExecutor

import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Deactivate
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Service
import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.api.scala.{ Utilities => UtilitiesApi }
import com.apollo.demos.osgi.base.api.scala.Utilities.stringToInt
import com.apollo.demos.osgi.base.api.scala.Utilities.stringToLong

@Component(immediate = true)
@Service
class UtilitiesImpl extends UtilitiesApi {
  private val logger = getLogger(getClass)

  def id(obj: Object) = Option(obj).map(o => "0x" + toHexString(identityHashCode(o))).getOrElse("null")

  def threadId(thread: Thread) = Option(thread).map(_.getName + "(" + id(thread) + ")").getOrElse("null")

  def className(obj: Object) = Option(obj).map(_.getClass.getName).getOrElse("null")

  def stack(thread: Thread) = thread.getStackTrace.mkString("Thread.Stack\n  at ", "\n  at ", "\n")

  def startThread(name: String, task: => Unit) = new Thread(new Runnable() { def run() { task } }, name).start

  def currentState(pool: ThreadPoolExecutor): (String, Int, Int, Int, Long) = currentState(pool.toString)
  private[impl] def currentState(stateInfo: String): (String, Int, Int, Int, Long) = {
    val extractor = """.*\[(.*),.*= (\d*),.*= (\d*),.*= (\d*),.*= (\d*)\]""".r
    val extractor(state, poolSize, activeThreads, queuedTasks, completedTasks) = stateInfo
    import UtilitiesApi.{ stringToInt, stringToLong }
    (state, poolSize, activeThreads, queuedTasks, completedTasks)
  }

  @Activate def activate() { logger.debug("Activate.") }
  @Deactivate def deactivate() { logger.debug("Deactivate.") }
  @Modified def modified() { logger.debug("Modified.") }
}

object Utilities extends UtilitiesImpl
