package com.apollo.demos.osgi.base.impl.shell.completer

import java.util.{ List => JList }

import org.apache.karaf.shell.console.Completer
import org.apache.karaf.shell.console.completer.StringsCompleter

abstract class CompleterBase extends Completer {
  def strings: List[String]
  def complete(buffer: String, cursor: Int, candidates: JList[String]) = new StringsCompleter(strings.toArray).complete(buffer, cursor, candidates: JList[_])
}
