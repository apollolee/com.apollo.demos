package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolMonitor.allThreadPool

@Command(scope = Scope_Thread, name = "pools", description = "List all thread pool state.")
class ThreadPools extends CommandBase {
  def onCommand() = {
    println(table(ColumnHeader_PoolState, allThreadPool.sortBy(_.id).map(state)))
  }
}
