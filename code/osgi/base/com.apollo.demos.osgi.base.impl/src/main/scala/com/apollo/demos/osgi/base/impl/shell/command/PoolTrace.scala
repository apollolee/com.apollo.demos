package com.apollo.demos.osgi.base.impl.shell.command

import java.lang.Thread.sleep

import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.ForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.NamedPool
import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

@Command(scope = Scope_Thread, name = "pool-trace", description = "Tracing pool state.")
class PoolTrace extends PoolBase {
  def onCommand(pool: NamedPool) {
    println("\nTracing " + pool.id + ".")

    val add = pool match {
      case _: ForkJoinPool       => trace(ColumnHeader_State_ForkJoinPool)
      case _: ThreadPoolExecutor => trace(ColumnHeader_State_ThreadPool)
    }

    do {
      add(Some(pool match {
        case pool: ForkJoinPool       => state(pool)
        case pool: ThreadPoolExecutor => state(pool)
      }))
      try { sleep(1000) } catch { case ex: InterruptedException => add(None); println("Tracing is canceled.\n"); return }
    } while (!pool.isTerminated); add(None); println("Pool is terminated.\n")
  }
}
