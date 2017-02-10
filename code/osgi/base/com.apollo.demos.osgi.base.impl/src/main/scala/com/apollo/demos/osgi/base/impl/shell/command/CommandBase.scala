package com.apollo.demos.osgi.base.impl.shell.command

import org.apache.karaf.shell.console.OsgiCommandSupport

abstract class CommandBase extends OsgiCommandSupport {
  def onCommand(): Unit
  def doExecute(): Object = { onCommand; null }
}
