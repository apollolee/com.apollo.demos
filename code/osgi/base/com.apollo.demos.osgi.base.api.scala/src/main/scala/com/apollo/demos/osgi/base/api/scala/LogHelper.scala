package com.apollo.demos.osgi.base.api.scala

import org.slf4j.Logger

trait LogHelper {
  def trace(logger: Logger, msg: String, arguments: Any*)
  def debug(logger: Logger, msg: String, arguments: Any*)
  def info(logger: Logger, msg: String, arguments: Any*)
  def warn(logger: Logger, msg: String, arguments: Any*)
  def error(logger: Logger, msg: String, arguments: Any*)
}
