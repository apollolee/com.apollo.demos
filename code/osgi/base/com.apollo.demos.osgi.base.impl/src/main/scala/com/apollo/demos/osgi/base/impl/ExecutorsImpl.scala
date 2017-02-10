package com.apollo.demos.osgi.base.impl

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService

import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Deactivate
import org.apache.felix.scr.annotations.Service

import com.apollo.demos.osgi.base.api.scala.{ Executors => ExecutorsApi }
import com.apollo.demos.osgi.base.impl.thread.ComplexThreadPoolExecutor
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

@Component
@Service
class ExecutorsImpl extends ExecutorsApi {
  private val common = newThreadPool("CommonThreadPool", 100)

  def submit(task: Runnable) = common.submit(task)
  def submit[T](task: Callable[T]) = common.submit(task)
  def submit[T](task: Runnable, result: T) = common.submit(task, result)

  def newThreadPool(name: String, count: Int): ExecutorService = ComplexThreadPoolExecutor(name, count)
  def newFixedThreadPool(name: String, count: Int): ExecutorService = ThreadPoolExecutor(name, count)

  @Deactivate def deactivate() { common.shutdown }
}

object Executors extends ExecutorsImpl
