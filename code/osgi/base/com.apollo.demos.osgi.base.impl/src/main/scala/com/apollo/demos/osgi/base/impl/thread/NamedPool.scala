package com.apollo.demos.osgi.base.impl.thread

import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicBoolean

import com.apollo.demos.osgi.base.impl.Utilities.{ id => uid }

import PoolMonitor.end
import PoolMonitor.start

trait NamedPool extends ExecutorService {
  start(this)

  val name: String
  val nameSuffix = ""
  val isEnd = new AtomicBoolean(false)

  def id = name + nameSuffix + "(" + uid(this) + ")"
  def tryEnd = if (isEnd.compareAndSet(false, true)) { end(this); true } else false
}
