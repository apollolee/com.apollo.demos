package com.apollo.demos.osgi.base.api.scala

import java.util.concurrent.ThreadPoolExecutor

import com.apollo.demos.osgi.base.api.IUtilities

object Utilities {
  implicit def stringToInt(intString: String) = Integer.parseInt(intString)
  implicit def stringToLong(longString: String) = java.lang.Long.parseLong(longString)
}

trait Utilities extends IUtilities {
  def startThread(name: String, task: => Unit): Unit
  def currentState(threadPool: ThreadPoolExecutor): (String, Int, Int, Int, Long)
}
