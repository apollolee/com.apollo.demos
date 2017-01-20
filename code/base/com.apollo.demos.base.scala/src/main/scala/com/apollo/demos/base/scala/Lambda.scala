package com.apollo.demos.base.scala

import java.io.File
import java.io.FileReader

object Lambda extends App {
  val increase1 = (x: Int) => x + 1 //匿名函数可以赋值给变量，进行传递。以Java匿名内部类来看待这个东西会容易一些，这个东西在JDK8中有类比，但这里语法上更为简洁。
  println(increase1(5))
  println(increase1(10))

  val increase2 = (_: Int) + 1 //使用占位符的极简写法，这里因为编译器无法推断出_的类型，所以必须明确指出。
  println(increase2(5))
  println(increase2(10))

  val add = (_: Int) + (_: Int) //两个_也可以正常使用。
  println(add(5, 10))

  val someNumbers = List(1, 2, 3, 4, 5)
  println(someNumbers.filter(_ > 3)) //这里编译器可以推断_类型，所以能省的都省了，看上去非常已经简化到不能再简化了。
  someNumbers.foreach(println _) //这里_表示整个参数列表的占位符。其实连_都可以省了，这个前面演示过。

  def sum(a: Int, b: Int, c: Int) = a + b + c
  val s1 = sum _ //整个参数列表占位符看得更明确一些。
  println(s1(1, 2, 3))
  val s2 = sum(1, _: Int, 3) //偏函数，即函数的部分调用。
  println(s2(2))

  //Function和元组类似定义了22个，而且都是泛型的。Scala泛型的强大使得Scala基本不需要像JD8中那样为特定功能专门定制函数接口。
  println(s1) //把一个函数对象打印出来会得到<function3>，这是特质scala.Function3.toString的输出。

  var more = 1
  val increase3 = (x: Int) => x + more
  println(increase3(5));
  more = 2
  println(increase3(5)); //Scala中的闭包和JDK8中的不一样，闭包内可以响应外部变量的变化，这从侧面说明了Scala区分了变量和值这两个概念，这点比Java中的复杂。

  var r = 0
  List(1, 2, 3).foreach(n => r += n) //Scala还能在闭包中改变外部变量。
  println(r)

  def echo(args: String*) = args.foreach(println) //*等同于Java中的...
  echo("one")
  echo("hello", "world")

  val hw = Array("hello", "world")
  //echo(hw) //Java中这样写没有问题，在Scala中会编译错误。
  echo(hw: _*) //正确的写法，语法有些奇怪，比Java中麻烦。

  FileMatcher.filesEnding(".scala").foreach(println)

  def plainOldSum(x: Int, y: Int) = x + y
  def curriedSum(x: Int)(y: Int) = x + y
  println(plainOldSum(1, 2))
  println(curriedSum(1)(2))
  val onePlus1 = plainOldSum(1, _: Int) //部分实施（偏函数）。
  val onePlus2 = curriedSum(1)_ //柯里化，抛开底层差异，就语法看，柯里化在这里比部分实施更简化。但柯里化和部分实施各有应用场景，差异还是很大的。
  println(onePlus1(5))
  println(onePlus2(5))

  def curriedSumReal(x: Int) = (y: Int) => x + y //柯里化底层基本是这个意思，看懂这个的关键是要明白curriedSumReal的返回值是一个函数，是一个行为，而不是某个值。
  val onePlus3 = curriedSumReal(1)
  println(onePlus3(5))

  def twice(op: Double => Double, x: Double) = op(op(x)) //Double=>Double直接描述了函数类型，让函数成为一定公民。而在JDK8中，需要先抽象一个明确的函数接口，更为繁琐。
  println(twice(_ + 1, 5))

  def withFileReader(file: File)(op: FileReader => Unit) {
    val reader = new FileReader(file)
    try {
      op(reader)
    } finally {
      reader.close()
    }
  }
  withFileReader(new File("./pom.xml")) { //大括号写法。
    fr => println(fr.getEncoding)
  }
  val pomReader = withFileReader(new File("./pom.xml"))_ //柯里化写法。
  pomReader(fr => println(fr.getEncoding))

  def myAssert1(predicate: () => Boolean) = if (!predicate()) throw new AssertionError
  def myAssert2(predicate: => Boolean) = if (!predicate) throw new AssertionError //传名参数，更为简化。
  myAssert1(() => 5 > 3)
  myAssert2(5 > 3) //调用时体现了精简。
}

object FileMatcher {
  private def files(file: File): List[File] = file :: (if (file.isDirectory()) file.listFiles().flatMap(files).toList else Nil)
  private def files: List[File] = files(new File("."))
  private def filesMatching(matcher: String => Boolean) = for (file <- files if matcher(file.getName)) yield file
  def filesEnding(query: String) = filesMatching(_.endsWith(query))
  def filesContaining(query: String) = filesMatching(_.contains(query))
  def filesRegex(query: String) = filesMatching(_.matches(query))
}
