package com.apollo.demos.osgi.base.impl.thread

import java.lang.System.getSecurityManager
import java.lang.Thread.NORM_PRIORITY
import java.lang.Thread.currentThread
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object NamedThreadFactory {
  def apply(name: String) = new NamedThreadFactory(name)
}

class NamedThreadFactory(name: String) extends ThreadFactory {
  private[this] val group: ThreadGroup = Option(getSecurityManager).map(_.getThreadGroup).getOrElse(currentThread.getThreadGroup)
  private[this] val threadNumber = new AtomicInteger(1)
  private[this] val namePrefix = name + "-"

  def newThread(task: Runnable) = {
    val thread = new Thread(group, task, namePrefix + threadNumber.getAndIncrement, 0)
    if (thread.isDaemon) thread.setDaemon(false)
    if (thread.getPriority != NORM_PRIORITY) thread.setPriority(NORM_PRIORITY)
    thread
  }
}
