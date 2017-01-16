package com.apollo.demos.base.scala

sealed abstract class Expr //封闭Expr类，其子类必须在同一个文件中定义。
case class Var(name: String) extends Expr //加case定义样本类。
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

object CaseClass extends App {
  val v = Var("x") //样本类自动实现apply函数。
  val op = BinOp("+", Number(1), v)
  println(v.name) //构造器参数自动隐身获得val前缀。
  println(op.left)
  println(op) //自动获得toString,hashCode和equals的“自然”实现。
  println(op.right == Var("x"))
}
