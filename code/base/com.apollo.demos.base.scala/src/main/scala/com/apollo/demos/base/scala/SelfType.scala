package com.apollo.demos.base.scala

object SelfType extends App {
  class Self {
    self => //代表this指针，也就是说给this起一个别名，名称随意，不必非要是self。
    val scala = "scala"
    def foo = self.scala + this.scala
  }
  println(new Self().foo)

  class Outer {
    out => //为this起个别名为out，在内部类中可以避免和内部类的this冲突。
    val spark = "v1"
    class Inner {
      val spark = "v2"
      println(out.spark + this.spark)
      println(Outer.this.spark + this.spark) //也支持Java中的语法，不过显得没那么优雅。
    }
    new Inner
  }
  new Outer

  //起别名对自类型来说只是副产品，自类型真正的用途是对类型做使用上的约束。
  trait S1
  trait S2 { this: S1 => } //由于这里不需要做别名，直接用this更方便。这里的意思是：with S2的时候，必须也同时混入S1。
  class S3 extends S2 with S1
  //class S3_1 extends S2 //编译时非法继承错误：illegal inheritance; self-type S3_1 does not conform to S2's selftype S2 with S1

  trait S4 { this: S1 with S2 => } //要指定多个需要混入的特质时，可以使用with。
  class S5 extends S4 with S2 with S1 //这里必须也混入S1，S1是嵌套指定的。
  //class S5_1 extends S4 with S2 //这样就不行，自类型使用的约束是具有传递性的。

  trait S6 { def v6 = "S6" }
  trait S7 { this: S6 => def v7 = "S7" + v6 } //指定使用约束以后，很自然的就能使用约束类的方法或字段。这里S7中使用了S6中的方法。
  trait S8 extends S6 { def v8 = "S7" + v6 } //这个extends有区别大吗？
  trait S8_1 extends Comparable[Int] { //注意：当继承一个Java的接口时，Scala IDE（Eclipse）有几率误报非法继承错误，应该是bug，但Scala编译器没有这个问题。
    this: Comparable[Int] => //在继承接口的特质上添加自类型到接口能解决这个问题。
  }

  trait S6_1 extends S6 { override def v6 = "S6_1" + super.v6 } //extends时，覆写方法会强制你加上override关键字，同时，你可以使用super调用到父类方法。
  object TestS6_1 extends S6_1
  println(TestS6_1.v6)

  trait S6_2 { s6: S6 => def v6 = "S6_2" } //自类型时，不强制加override关键字。
  //trait S6_3 { s6: S6 => def v6 = "S6_3" + super.v6 } //这里没有继承，所以无法使用super关键字。
  //trait S6_4 { s6: S6 => def v6 = "S6_4" + s6.v6 } //用自类型指向时，相当于this.v6，s6只是this的一个别名，无法向super那样指向父类的v6方法。。
  //trait S6_5 { s6: S6 => def v6 = "S6_5" + S6.super.v6 } //这样也不行，实际上自类型中无法调用到被覆盖的父类方法。
  object TestS6_2 extends S6_2 with S6 { override def v6 = super.v6 } //因为有同名方法，编译器会强制TestS6_2覆写同名方法，这里可以使用super，规则见Trait.scala。
  println(TestS6_2.v6)

  //小结：自类型没有像extends ... with ... 那样引入super机制，不适用于覆写，更多的是适用于组合，详见CakePattern.scala。
}
