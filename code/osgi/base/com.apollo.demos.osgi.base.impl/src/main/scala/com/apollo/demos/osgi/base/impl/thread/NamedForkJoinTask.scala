package com.apollo.demos.osgi.base.impl.thread

import java.lang.Thread.currentThread
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.ForkJoinTask.getPool

import PoolMonitor.endTask
import PoolMonitor.startTask

object NamedForkJoinTask {
  def apply[T](name: String, forkJoinTask: ForkJoinTask[T]) = new NamedForkJoinTask(name, forkJoinTask)
}

class NamedForkJoinTask[R](val name: String, forkJoinTask: ForkJoinTask[R]) extends ForkJoinTask[R] {
  //TODO 注意：这里依赖JDK的私有实现，目前暂无其他办法解决。
  override def getRawResult = forkJoinTask.getRawResult

  override def setRawResult(value: R) {
    val setRawResultMethod = forkJoinTask.getClass.getDeclaredMethod("setRawResult", value.getClass); setRawResultMethod.setAccessible(true)
    setRawResultMethod.invoke(forkJoinTask, value.asInstanceOf[Object])
  }

  override def exec: Boolean = {
    val pool = getPool.asInstanceOf[NamedPool]
    startTask(pool, currentThread, this)
    val execMethod = classOf[ForkJoinTask[R]].getDeclaredMethod("exec"); execMethod.setAccessible(true)
    val result = execMethod.invoke(forkJoinTask).asInstanceOf[Boolean]
    endTask(pool, currentThread, this)
    return result
  }
}
