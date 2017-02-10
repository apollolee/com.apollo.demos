package com.apollo.demos.osgi.base.impl.shell.command

import java.lang.Thread.sleep

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

@Command(scope = Scope_Thread, name = "pool-trace", description = "Tracing thread pool state.")
class ThreadPoolTrace extends ThreadPoolBase {
  def onCommand(pool: ThreadPoolExecutor) {
    println("\nTracing " + pool.id + ".")
    val add = trace(ColumnHeader_PoolState)
    do {
      add(Some(state(pool)))
      try { sleep(1000) } catch { case ex: InterruptedException => add(None); println("Tracing is canceled.\n"); return }
    } while (!pool.isTerminated); add(None); println("Thread pool is terminated.\n")
  }
}
