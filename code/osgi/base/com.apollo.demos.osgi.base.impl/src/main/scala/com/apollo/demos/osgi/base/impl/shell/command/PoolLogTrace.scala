package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.commands.Argument
import org.apache.karaf.shell.commands.Command

import com.apollo.demos.osgi.base.impl.shell.ShellHelper._
import com.apollo.demos.osgi.base.impl.thread.PoolTracer.endTrace
import com.apollo.demos.osgi.base.impl.thread.PoolTracer.isTracing
import com.apollo.demos.osgi.base.impl.thread.PoolTracer.logger
import com.apollo.demos.osgi.base.impl.thread.PoolTracer.startTrace

@Command(scope = Scope_Thread, name = "pool-log-trace", description = "Control log tracing pool state.")
class PoolLogTrace extends CommandBase {
  @Argument(index = 0, name = "state", description = "on or off.", required = false, multiValued = false) var onOrOff: String = _

  def onCommand() {
    if (!logger.isTraceEnabled) println("\nWarning: Pool tracer log level is not TRACE.")
    Option(onOrOff) match {
      case Some("on")  => { startTrace; println("\nTurn on log tracing.") }
      case Some("off") => { endTrace; println("\nTurn off log tracing.") }
      case Some(arg)   => println("\nError argument: " + arg + ".")
      case None        => println("\nLog tracing is " + Some(isTracing).map(if (_) "on" else "off").get + ".")
    }
    println
  }
}
