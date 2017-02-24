package com.apollo.demos.osgi.base.impl

import java.util.UUID
import java.util.UUID.randomUUID

import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Deactivate
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.api.ILogHelper
import com.apollo.demos.osgi.base.api.scala.{ LogHelper => LogHelperApi }

@Component(immediate = true)
@Service
class LogHelperImpl extends LogHelperApi {
  private val logger = getLogger(getClass)

  def trace(logger: Logger, msg: String, arguments: Any*) { log(logger.isTraceEnabled, logger.trace(_, _: _*), _.trace, logger, msg, arguments: _*) }
  def debug(logger: Logger, msg: String, arguments: Any*) { log(logger.isDebugEnabled, logger.debug(_, _: _*), _.debug, logger, msg, arguments: _*) }
  def info(logger: Logger, msg: String, arguments: Any*) { log(logger.isInfoEnabled, logger.info(_, _: _*), _.info, logger, msg, arguments: _*) }
  def warn(logger: Logger, msg: String, arguments: Any*) { log(logger.isWarnEnabled, logger.warn(_, _: _*), _.warn, logger, msg, arguments: _*) }
  def error(logger: Logger, msg: String, arguments: Any*) { log(logger.isErrorEnabled, logger.error(_, _: _*), _.error, logger, msg, arguments: _*) }

  @Activate def activate() { logger.debug("Activate.") }
  @Deactivate def deactivate() { logger.debug("Deactivate.") }
  @Modified def modified() { logger.debug("Modified.") }

  private def log(isEnabled: => Boolean, logging: (String, Object*) => Unit, dataLogging: Data => Unit, logger: Logger, msg: String, arguments: Any*) {
    val format = arguments.map(a => "[{}]").mkString
    def convert = {
      val args = arguments.map(_.asInstanceOf[Object]).toArray
      def data(i: Int, dataType: (String, String)) = {
        val data = Data(logger, dataType._1, args(i).toString)
        args(i) = dataType._2 + "->" + data.uuid
        Some(data)
      }
      val datas = for (i <- 0 until args.length; arg = args(i); if Option(arg).isDefined)
        yield arg match {
        case s: String if s.startsWith("Thread.Stack\n  at ") => data(i, Data_Thread_Stack)
        case o if o.toString().contains("\n") => data(i, Data_Text)
        case _ => None
      }
      (args.toList, datas)
    }
    def show() {
      val sb = new StringBuilder(msg)
      val segments = format.split("\\{\\}")
      for (i <- 0 until segments.length - 1) sb.append(segments(i)).append(Option(arguments(i)).map(_.toString()).getOrElse("null"))
      sb.append(segments(segments.length - 1))
      if (format.endsWith("{}")) sb.append(Option(arguments(segments.length - 1)).map(_.toString()).getOrElse("null"))
    }
    if (isEnabled) { val datas = convert; logging(msg + format, datas._1: _*); datas._2.filter(_.isDefined).map(_.get).foreach(dataLogging) }
    if (IsDebug) show
  }

  private object Data {
    def apply(logger: Logger, loggerPrefix: String, argument: Any) = new Data(getLogger(loggerPrefix + "." + logger.getName), randomUUID, argument)
  }
  private class Data(logger: Logger, val uuid: UUID, argument: Any) {
    def trace() = logger.trace("[{}]->{}", uuid, argument)
    def debug() = logger.debug("[{}]->{}", uuid, argument)
    def info() = logger.info("[{}]->{}", uuid, argument)
    def warn() = logger.warn("[{}]->{}", uuid, argument)
    def error() = logger.error("[{}]->{}", uuid, argument)
  }

  private val Data_Thread_Stack = ("thread.stack", "Thread.Stack")
  private val Data_Text = ("text", "Text")
  private val IsDebug = false
}

object LogHelper extends LogHelperImpl

@Component(immediate = true)
@Service
class LogHelperImpl4J extends ILogHelper {
  private val logger = getLogger(getClass)

  def trace(logger: Logger, msg: String, arguments: Object*) { LogHelper.trace(logger, msg, arguments: _*) }
  def debug(logger: Logger, msg: String, arguments: Object*) { LogHelper.debug(logger, msg, arguments: _*) }
  def info(logger: Logger, msg: String, arguments: Object*) { LogHelper.info(logger, msg, arguments: _*) }
  def warn(logger: Logger, msg: String, arguments: Object*) { LogHelper.warn(logger, msg, arguments: _*) }
  def error(logger: Logger, msg: String, arguments: Object*) { LogHelper.error(logger, msg, arguments: _*) }

  @Activate def activate() { logger.debug("Activate.") }
  @Deactivate def deactivate() { logger.debug("Deactivate.") }
  @Modified def modified() { logger.debug("Modified.") }
}
