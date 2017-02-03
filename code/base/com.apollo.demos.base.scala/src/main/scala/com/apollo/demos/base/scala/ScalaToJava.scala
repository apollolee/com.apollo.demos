package com.apollo.demos.base.scala

import ScalaToJava4J._

object ScalaToJava extends App {
  log4j("Test.", Array("1", "2", "3"))
  log4j("Test.", Array("1", "2", "3"): _*) //这个差别是很重要的，Java中...的语法，只有通过_*来体现。

  def log4s(msg: String, arguments: Any*) { println("Log4S:" + msg + arguments.mkString("[", "][", "]")) }
  log4s("Test.", Array("1", "2", "3"))
  log4s("Test.", Array("1", "2", "3"): _*) //Scala中Any*也是这样的，可变参数传递都需要通过_*展开。
  log4s("Test.", List("a", "b", "c"): _*) //不一定要是Array，任何一个可以列举出元素的对象都可以用_*展开。

  val is = Array("1", "2", "3")
  def id(argument: Any) = argument.getClass.getName + "@" + Integer.toHexString(System.identityHashCode(argument))
  def varArgs(arguments: Any*) { println(id(arguments)) }
  println(id(is))
  varArgs(is: _*) //不是源数组了。
  varArgs(is) //一样也不是源数组了，注意：Any*无法从通过内层数组来改变外层数组，这和Java的...是不一样的。
}
