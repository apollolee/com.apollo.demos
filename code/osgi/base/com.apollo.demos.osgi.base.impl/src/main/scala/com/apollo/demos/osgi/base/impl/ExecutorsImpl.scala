package com.apollo.demos.osgi.base.impl

import java.util.concurrent.Callable
import java.util.concurrent.ForkJoinTask

import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Deactivate
import org.apache.felix.scr.annotations.Modified
import org.apache.felix.scr.annotations.Service
import org.slf4j.LoggerFactory.getLogger

import com.apollo.demos.osgi.base.api.scala.{ Executors => ExecutorsApi }
import com.apollo.demos.osgi.base.impl.thread.ComplexThreadPoolExecutor
import com.apollo.demos.osgi.base.impl.thread.ForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.NamedCallable
import com.apollo.demos.osgi.base.impl.thread.NamedForkJoinTask
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

@Component(immediate = true)
@Service
class ExecutorsImpl extends ExecutorsApi {
  private val logger = getLogger(getClass)
  private val common = newThreadPool("ApolloCommonPool", 100)

  def named[T](name: String, task: Callable[T]) = if (task.isInstanceOf[NamedCallable[T]]) task else NamedCallable(name, task)
  def named[T](name: String, task: ForkJoinTask[T]) = if (task.isInstanceOf[NamedForkJoinTask[T]]) task else NamedForkJoinTask(name, task)

  def submit[T](name: String, task: Callable[T]) = common.submit(named(name, task))

  def newThreadPool(name: String, count: Int) = ComplexThreadPoolExecutor(name, count)
  def newFixedThreadPool(name: String, count: Int) = ThreadPoolExecutor(name, count)
  def newForkJoinPool(name: String, parallelism: Int) = ForkJoinPool(name, parallelism)

  @Activate def activate() { logger.debug("Activate.") }
  @Deactivate def deactivate() { logger.info("Deactivate."); common.shutdown } //钝化后，原common池的任何submit都会抛否决异常，调用处是静态引用时需要特别注意。
  @Modified def modified() { logger.debug("Modified.") }
}

object Executors extends ExecutorsImpl
