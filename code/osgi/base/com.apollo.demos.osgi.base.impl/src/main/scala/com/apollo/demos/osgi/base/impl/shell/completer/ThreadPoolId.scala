package com.apollo.demos.osgi.base.impl.shell.completer

import com.apollo.demos.osgi.base.impl.thread.ThreadPoolMonitor.allThreadPool

class ThreadPoolId extends CompleterBase {
  def strings = allThreadPool.map(_.id)
}
