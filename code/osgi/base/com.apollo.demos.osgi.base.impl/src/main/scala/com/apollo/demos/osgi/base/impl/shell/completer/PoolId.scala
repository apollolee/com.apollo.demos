package com.apollo.demos.osgi.base.impl.shell.completer

import org.apache.karaf.shell.console.CommandSessionHolder.getSession
import org.apache.karaf.shell.console.completer.ArgumentCompleter.ARGUMENTS_LIST
import org.apache.karaf.shell.console.completer.ArgumentCompleter.ArgumentList

import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allForkJoinPool
import com.apollo.demos.osgi.base.impl.thread.PoolMonitor.allThreadPool

class PoolId extends CompleterBase {
  def strings = getSession().get(ARGUMENTS_LIST).asInstanceOf[ArgumentList].getArguments.mkString(" ", " ", " ") match {
    case as if (as.contains(" -t ForkJoinPool ")) => allForkJoinPool.map(_.id)
    case as if (as.contains(" -t ThreadPool ") || !as.contains(" -t ")) => allThreadPool.map(_.id)
    case _ => Nil
  }
}
