package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Argument

import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolMonitor.allThreadPool

abstract class ThreadPoolBase extends CommandBase {
  @Argument(index = 0, name = "poolId", description = "The ID of thread pool.", required = true, multiValued = false) var poolId: String = _

  def onCommand(pool: ThreadPoolExecutor): Unit
  def onCommand() {
    val pool = allThreadPool.find(_.id == poolId)
    if (pool.isDefined) onCommand(pool.get) else println("\nThread pool is not existed.\n")
  }
}
