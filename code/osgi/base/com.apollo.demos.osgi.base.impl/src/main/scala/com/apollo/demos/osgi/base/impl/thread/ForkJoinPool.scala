package com.apollo.demos.osgi.base.impl.thread

import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.{ ForkJoinPool => ForkJoinPool4J }
import java.util.concurrent.ForkJoinPool.{ ForkJoinWorkerThreadFactory => ForkJoinWorkerThreadFactory4J }
import java.util.concurrent.{ ForkJoinWorkerThread => ForkJoinWorkerThread4J }

import com.apollo.demos.osgi.base.impl.Utilities.{ currentState => cs }
import com.apollo.demos.osgi.base.impl.Utilities.{ id => uid }

object ForkJoinPool {
  def apply(name: String): ForkJoinPool = apply(name, Math.min(0x7fff, Runtime.getRuntime.availableProcessors))
  def apply(name: String, parallelism: Int): ForkJoinPool = apply(name, parallelism, null, false)
  def apply(name: String, parallelism: Int, handler: UncaughtExceptionHandler, asyncMode: Boolean) = new ForkJoinPool(name, parallelism, handler, asyncMode)
}

class ForkJoinPool(val name: String, parallelism: Int, handler: UncaughtExceptionHandler, asyncMode: Boolean)
    extends ForkJoinPool4J(parallelism, new ForkJoinWorkerThreadFactory, handler, asyncMode)
    with NamedPool {
  def currentState = cs(this)

  override val nameSuffix = "#FJ"
  override def shutdown { super.shutdown; tryEnd }
  override def shutdownNow = { val result = super.shutdownNow; tryEnd; result }
}

class ForkJoinWorkerThread(pool: ForkJoinPool) extends ForkJoinWorkerThread4J(pool) {
  setName("Thread-" + getPoolIndex + "(" + uid(this) + ")@" + pool.id)
}

class ForkJoinWorkerThreadFactory extends ForkJoinWorkerThreadFactory4J {
  def newThread(pool: ForkJoinPool4J) = new ForkJoinWorkerThread(pool.asInstanceOf[ForkJoinPool])
}
