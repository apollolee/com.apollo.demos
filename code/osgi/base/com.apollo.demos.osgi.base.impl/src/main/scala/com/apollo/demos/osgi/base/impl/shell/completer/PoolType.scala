package com.apollo.demos.osgi.base.impl.shell.completer

class PoolType extends CompleterBase {
  def strings = List("ThreadPool", "ForkJoinPool")
}
