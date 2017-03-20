package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allThreadPool

@Command(scope = Scope_Thread, name = "pool-summary", description = "Summary of all pool.")
class PoolSummary extends CommandBase {
  def onCommand() {
    println

    val ts = Thread.getAllStackTraces.size
    val tpPs = allThreadPool.map(_.getPoolSize).sum
    val fjpPs = allForkJoinPool.map(_.getPoolSize).sum
    val ps = tpPs + fjpPs
    println(table(List(("Threads", 2), ("Pool Threads", 3), ("Pool Rate", 2)), List(List(ts, ps, (ps * 100 / ts) + "%"))))

    val tpC = allThreadPool.size
    val tpAc = allThreadPool.map(_.getActiveCount).sum
    val tpQt = allThreadPool.map(_.getQueue.size).sum
    val tp = List("ThreadPool", tpC, tpPs, tpAc, (tpAc * 100 / tpPs) + "%", tpQt, (tpQt * 100 / tpPs) + "%")

    val fjpC = allForkJoinPool.size
    val fjpAc = allForkJoinPool.map(_.getActiveThreadCount).sum
    val fjpQt = allForkJoinPool.map(_.getQueuedTaskCount).sum
    val fjp = List("ForkJoinPool", fjpC, fjpPs, fjpAc, (fjpAc * 100 / fjpPs) + "%", fjpQt, (fjpQt * 100 / fjpPs) + "%")

    println(table(List(("Pool Type", 3), ("Pool Count", 2), ("Pool Threads", 3), ("Active Threads", 3), ("Thread Use Rate", 3), ("Queued Tasks", 3), ("Queued Rate", 3)), List(tp, fjp)))

    println
  }
}
