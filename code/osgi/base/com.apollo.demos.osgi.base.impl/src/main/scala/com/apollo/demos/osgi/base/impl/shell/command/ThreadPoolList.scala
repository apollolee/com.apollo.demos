package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolMonitor.allThreadPool

@Command(scope = Scope_Thread, name = "pool-list", description = "List all thread pool parameter.")
class ThreadPoolList extends CommandBase {
  def onCommand() = {
    println(table(ColumnHeader_PoolParameter, allThreadPool.sortBy(_.id).map(parameter)))
  }
}
