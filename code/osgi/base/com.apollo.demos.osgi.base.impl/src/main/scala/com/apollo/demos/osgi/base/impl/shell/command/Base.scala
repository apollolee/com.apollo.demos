package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Argument
import org.apache.karaf.shell.commands.{ Option => Option4J }
import org.apache.karaf.shell.console.OsgiCommandSupport

import com.apollo.demos.osgi.base.impl.thread.NamedPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allThreadPool

abstract class CommandBase extends OsgiCommandSupport {
  def onCommand(): Unit
  def doExecute(): Object = { onCommand; null }
}

abstract class Base extends CommandBase {
  @Option4J(name = "-t", aliases = Array("--type"), description = "The type of Pool.", required = false, multiValued = false) var poolType: String = "ThreadPool"
}

abstract class PoolBase extends Base {
  @Argument(index = 0, name = "poolId", description = "The ID of pool.", required = true, multiValued = false) var poolId: String = _

  def onCommand(pool: NamedPool): Unit
  def onCommand() = poolType match {
    case "ForkJoinPool" => tryOnCommand(allForkJoinPool.find(_.id == poolId))
    case "ThreadPool"   => tryOnCommand(allThreadPool.find(_.id == poolId))
    case poolType       => println("\nError pool type [" + poolType + "].\n")
  }

  private def tryOnCommand(pool: Option[NamedPool]) { if (pool.isDefined) onCommand(pool.get) else println("\nPool is not existed.\n") }
}
