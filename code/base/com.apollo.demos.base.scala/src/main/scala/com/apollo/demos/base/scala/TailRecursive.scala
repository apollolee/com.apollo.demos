package com.apollo.demos.base.scala

object TailRecursive extends App {
  def bang(x: Int): Int = if (x == 0) throw new Exception("bang!") else bang(x - 1) //Scala编译器只能做直接的尾递归调用优化，间接的无法支持。
  bang(5) //scala编译缺省是进行尾递归优化的，可以在编译时加入参数-g:notailcalls禁止尾递归优化，分别看看异常栈的差异。
}

object NonTailRecursive extends App {
  def bang(x: Int): Int = if (x == 0) throw new Exception("bang!") else bang(x - 1) + 1 //bang(x - 1) + 1这个表达式就不算是尾递归了。
  bang(5)
}
