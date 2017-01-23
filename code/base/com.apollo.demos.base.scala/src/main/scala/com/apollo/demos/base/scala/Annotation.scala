package com.apollo.demos.base.scala

object Annotation extends App {
  @deprecated def bigMistake() = {}
  @volatile var i = 5 //和Java的volatile语义相同。

  def describe(expr: Expr) = (expr: @unchecked) match { //unchecked目前看好像只能用于match，这个和Java不太一样。
    case Number(_) => "a number"
    case Var(_)    => "a variable"
  }
}

@SerialVersionUID(123) class Data extends Serializable {
  import scala.beans.BeanProperty
  @transient @BeanProperty val text = "123" //BeanProperty会在编译时自动生成getter和setter，一般供外部框架使用。
}
