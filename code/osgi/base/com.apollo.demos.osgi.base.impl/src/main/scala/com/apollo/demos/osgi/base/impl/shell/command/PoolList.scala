package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.shell.ShellHelper.table
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allThreadPool

@Command(scope = Scope_Thread, name = "pool-list", description = "List all pool parameter.")
class PoolList extends Base {
  def onCommand() = poolType match {
    case "ForkJoinPool" => println(table(ColumnHeader_Parameter_ForkJoinPool, allForkJoinPool.sortBy(_.id).map(parameter)))
    case "ThreadPool"   => println(table(ColumnHeader_Parameter_ThreadPool, allThreadPool.sortBy(_.id).map(parameter)))
    case poolType       => println("\nError pool type [" + poolType + "].\n")
  }
}
