package com.apollo.demos.base.scala

import scala.collection.mutable.ArrayBuffer

object Trait extends App {
  val basicIntQueue = new BasicIntQueue
  basicIntQueue.put(10)
  println(basicIntQueue.get)

  class MyQueue extends BasicIntQueue with Doubling //只定义了类，没有任何实现。BasicIntQueue类混入了Doubling特质。
  val myQueue = new MyQueue
  myQueue.put(10) //这里的put行为受特质堆叠而改变。
  println(myQueue.get)

  val queue1 = new BasicIntQueue with Incrementing with Filtering //混入特质的简写形式，连定义类都省了。
  queue1.put(-1); queue1.put(0); queue1.put(1);
  println(queue1.getString); println(queue1.getString); println(queue1.getString)

  val queue2 = new BasicIntQueue with Filtering with Incrementing //越靠右的特质先被执行，这里体现了特质中super的动态绑定效果。
  queue2.put(-1); queue2.put(0); queue2.put(1);
  println(queue2.getString); println(queue2.getString); println(queue2.getString)

  //特质和其他语言的多重继承相比，一个本质区别是方法的调用由类和被混入到类的特质的线性化所决定的。其他语言一般是多个里面以某种规则胜出一个，仅走多继承的一个分支。
  class Animal { def hi = "Animal" }
  trait Furry extends Animal { abstract override def hi = "Furry -> " + super.hi }
  trait HasLegs extends Animal { abstract override def hi = "HasLegs -> " + super.hi }
  trait FourLegged extends HasLegs { abstract override def hi = "FourLegged -> " + super.hi }
  class Cat extends Animal with Furry with FourLegged { override def hi = "Cat -> " + super.hi }

  println((new Animal).hi)
  println((new Animal with Furry).hi)
  println((new Animal with HasLegs).hi)
  println((new Animal with FourLegged).hi)
  println((new Animal with Furry with FourLegged).hi)
  println((new Animal with FourLegged with Furry).hi)
  println((new Cat).hi)
}

abstract class IntQueue { //这里的抽象类换成特质定义效果是一样的，不过从语义上，抽象类更准确一些。
  def isEmpty: Boolean
  def get: Int
  def put(x: Int)
  def getString = if (isEmpty) "null" else get
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def isEmpty = buf.isEmpty
  def get = buf.remove(0)
  def put(x: Int) { buf += x }
}

trait Doubling extends IntQueue { //加入的int翻倍。
  abstract override def put(x: Int) { super.put(x * 2) } //当特质的方法中使用了super，使用abstract override修饰是必须的。
}

trait Incrementing extends IntQueue { //加入的int加1。
  abstract override def put(x: Int) { super.put(x + 1) }
}

trait Filtering extends IntQueue { //只有正整数才允许被加入。
  abstract override def put(x: Int) { if (x >= 0) super.put(x) }
}
