package com.apollo.demos.osgi.base.impl.shell

import java.util.concurrent.TimeUnit.SECONDS

import com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor

object ShellHelper {
  final val Scope_Thread = "apollo.base.thread"

  val ColumnHeader_PoolParameter = List(("ID", 7), ("Core Pool Size", 3), ("Maximum Pool Size", 3), ("Keep Alive Time (Sec)", 4))
  def parameter(pool: ThreadPoolExecutor) = List(pool.id, pool.getCorePoolSize, pool.getMaximumPoolSize, pool.getKeepAliveTime(SECONDS))

  val ColumnHeader_PoolState = List(("ID", 7), ("State", 3), ("Pool Size", 2), ("Active Threads", 3), ("Queued Tasks", 3), ("Completed Tasks", 3), ("Task Count", 2), ("Largest Pool Size", 3))
  def state(pool: ThreadPoolExecutor) = { val cs = pool.currentState; List(pool.id, cs._1, cs._2, cs._3, cs._4, cs._5, (cs._3 + cs._4 + cs._5), pool.getLargestPoolSize) }

  private val TabSpace = 7

  def trace(columnHeader: List[(String, Int)]) = {
    val allSpace = (columnHeader.map(_._2 * TabSpace - 1).reduce(_ + _) + 1)
    val hr = "-" * allSpace
    println(hr)
    println(columnHeader.map(c => tab("| " + c._1, c._2)).mkString + "|")
    println(hr)
    (row: Option[List[Any]]) => println(row.map(r => (for (i <- 0 until r.size) yield tab("| " + r(i), columnHeader(i)._2)).mkString + "|").getOrElse(hr))
  }

  def table(columnHeader: List[(String, Int)], rowData: List[List[Any]]) = {
    val allSpace = (columnHeader.map(_._2 * TabSpace - 1).reduce(_ + _) + 1)
    val hr = "-" * allSpace + "\n"
    val sb = new StringBuilder
    sb ++= "\nTotal number is " + rowData.size + ".\n"
    sb ++= hr
    sb ++= columnHeader.map(c => tab("| " + c._1, c._2)).mkString + "|\n"
    sb ++= hr
    rowData.foreach(row => sb ++= (for (i <- 0 until row.size) yield tab("| " + row(i), columnHeader(i)._2)).mkString + "|\n")
    if (rowData.isEmpty) sb.toString else sb + hr
  }

  def list(arguments: Any*) = {
    val sb = new StringBuilder
    sb ++= "\nTotal number is " + arguments.size + ".\n"
    sb ++= "-" * ((4 * TabSpace - 1) * 6) + "\n"
    for (i <- 0 until arguments.size) sb ++= cell(arguments(i), i)
    sb + "\n"
  }

  def cell(argument: Any, index: Int): String = cell(argument, index, 6)
  def cell(argument: Any, index: Int, columnNum: Int): String = cell(argument, index, columnNum, 4)
  def cell(argument: Any, index: Int, columnNum: Int, tabNum: Int) = { tab(argument, tabNum) + (if ((index + 1) % columnNum == 0) "\n" else "") }

  def tab(argument: Any, tabNum: Int) = {
    var text = line(argument.toString)
    val balance = tabNum * TabSpace - text.length() - 1
    var tab = ""
    if (balance > 0) for (i <- 0 until balance) tab += " "
    else text = text.substring(0, text.length + balance - 4) + "... "
    text + tab
  }

  def line(text: String) = text.replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n").trim()
}
