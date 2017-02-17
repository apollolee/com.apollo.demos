package com.apollo.demos.osgi.base.impl.thread

import java.util.concurrent.Callable

import org.slf4j.LoggerFactory.getLogger

object NamedCallable {
  val logger = getLogger(getClass)
  def apply[T](name: String, callable: Callable[T]) = new NamedCallable(name, callable)
  private[thread] def name(task: Runnable) = try { //TODO 注意：这里依赖JDK的私有实现，目前暂无其他办法解决。
    val callableField = task.getClass.getDeclaredField("callable"); callableField.setAccessible(true)
    val callable = callableField.get(task)
    if (callable.isInstanceOf[NamedCallable[_]]) Some(callable.asInstanceOf[NamedCallable[_]].name) else None

  } catch {
    case ex: Throwable => logger.warn("Get name is failed.", ex); None
  }
}

class NamedCallable[R](val name: String, callable: Callable[R]) extends Callable[R] {
  def call = callable.call
}
