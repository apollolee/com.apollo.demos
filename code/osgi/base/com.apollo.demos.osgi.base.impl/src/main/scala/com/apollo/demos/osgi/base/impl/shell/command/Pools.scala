package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allThreadPool

@Command(scope = Scope_Thread, name = "pools", description = "List all pool state.")
class Pools extends Base {
  def onCommand() = poolType match {
    case "ForkJoinPool" => println(table(ColumnHeader_State_ForkJoinPool, allForkJoinPool.sortBy(_.id).map(state)))
    case "ThreadPool"   => println(table(ColumnHeader_State_ThreadPool, allThreadPool.sortBy(_.id).map(state)))
    case poolType       => println("\nError pool type [" + poolType + "].\n")
  }
}
