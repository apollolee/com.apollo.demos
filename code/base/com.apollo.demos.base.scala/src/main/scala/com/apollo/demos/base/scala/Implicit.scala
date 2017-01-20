package com.apollo.demos.base.scala

object Implicit extends App {
  //println(123.length) //编译不过，Int没有找到成员length。
  implicit def intToString(i: Int) = i.toString()
  println(123.length) //通过隐式转换，相当于扩展了Int的能力。

  import ImplicitUtil.doubleToInt
  val i: Int = 3.5 //这里需要明确的引入doubleToInt。

  val euro: Euro = new Dollar //这里不需要引入，编译器会在目标类型和期待类型的伴生对象中查找隐式转换方法。

  //implicit def stringToSeq(s: String) = new Seq[Char] { def length = s.length; def apply(i: Int) = s.charAt(i) } //RandomAccessSeq已被移除，用Seq替代。
  println("abc123".exists(_.isDigit)) //Predef中已加入了String的这种转换，如果上面的隐式转换添加，会产生冲突。

  import Base.Rational
  val y = new Rational(2, 3)
  implicit def intTORational(x: Int) = new Rational(x)
  println(2 * y) //这里是典型的用implicit来转换方法调用的接受者的例子（与新类型的交互操作）。而上面的用法都是隐式转换为期望类型。

  val map = Map(1 -> "a", 2 -> "b", 2 -> "c") //这里模拟新的语法，->方法是定义在ArrowAssoc中的（返回Tuple2），而Any到ArrowAssoc的转换被定义到了Predef中。

  def greet(name: String)(implicit pormpt: PreferredPrompt) { //柯里化。
    println("Welcome, " + name + ". The system is ready.")
    println(pormpt.preference)
  }

  val bobsPrompt = new PreferredPrompt("relax> ")
  greet("Bob")(bobsPrompt) //正常的柯里化调用。

  {
    import JoesPrefs.prompt
    greet("Joe") //引入隐式转换后，调用时简化了。
  }

  def greetX(name: String)(implicit pormpt: PreferredPrompt, drink: PreferredDrink) { //这里implicit修饰的是整个参数列表，而不仅仅是pormpt。
    println("Welcome, " + name + ". The system is ready.")
    print("But while you work, ")
    println("why not enjoy a cup of " + drink.preference + "?")
    println(pormpt.preference)
  }

  {
    import JoesPrefs.prompt
    //greetX("Joe") //部分引入无法完成整个参数列表的转换。
    //greetX("Joe")(new PreferredDrink("tea")) //柯里化参数列表内不能部分隐式转换。
    greetX("Joe")(prompt, new PreferredDrink("tea")) //这样倒是可以，但这里跟隐式转换没有关系。
  }

  {
    import JoesPrefs._
    greetX("Joe") //OK。
  }

  def maxListUpBound[T <: Ordered[T]](elements: List[T]): T = elements match { //maxListUpBound和TypeParameterization.orderedMergeSort例子类似。
    case List()    => throw new IllegalArgumentException("empty list!")
    case List(x)   => x
    case x :: rest => val maxRest = maxListUpBound(rest); if (x > maxRest) x else maxRest
  }
  import TypeParameterization.Person
  println(maxListUpBound(List(new Person("Larry", "Wall"), new Person("Anders", "Hejlsberg"), new Person("Guido", "van Rossum"))))
  //println(maxListUpBound(List(1, 2, 3))) //依然不适用T不是Ordered[T]子类的情况。

  def maxListImpParm[T](elements: List[T])(implicit orderer: T => Ordered[T]): T = elements match {
    case List()    => throw new IllegalArgumentException("empty list!")
    case List(x)   => x
    case x :: rest => val maxRest = maxListImpParm(rest)(orderer); if (orderer(x) > maxRest) x else maxRest
  }
  println(maxListImpParm(List(1, 5, 10, 3))) //现在可以了，但我们没有提供任何orderer，这是因为Scala库提供了这些。
  println(maxListImpParm(List(1.5, 5.2, 10.7, 3.1415926)))
  println(maxListImpParm(List("one", "two", "three")))

  def maxList[T](elements: List[T])(implicit orderer: T => Ordered[T]): T = elements match {
    case List()    => throw new IllegalArgumentException("empty list!")
    case List(x)   => x
    case x :: rest => val maxRest = maxListImpParm(rest); if (x > maxRest) x else maxRest //这两处的orderer都可以省略，由编译器隐式转换出相应代码。
  }
  println(maxList(List(1, 5, 10, 3)))
  println(maxList(List(1.5, 5.2, 10.7, 3.1415926)))
  println(maxList(List("one", "two", "three")))

  def maxListX[T <% Ordered[T]](elements: List[T]): T = elements match { //orderer实际上没有必要出现在字面上，可以进一步省略这样写。
    case List()    => throw new IllegalArgumentException("empty list!")
    case List(x)   => x
    case x :: rest => val maxRest = maxListImpParm(rest); if (x > maxRest) x else maxRest
  }
  println(maxListX(List(1, 5, 10, 3)))
  println(maxListX(List(1.5, 5.2, 10.7, 3.1415926)))
  println(maxListX(List("one", "two", "three")))
  //def maxListUpBound[T <: Ordered[T]](elements: List[T]): T
  //def maxListX[T <% Ordered[T]](elements: List[T]): T
  //比较一下，虽然定义只有一个:和%的差异，但内部机制却相差非常大。
  //上界：T <: Ordered[T]表达的含义是：T要是一个Ordered[T]，即T必须是Ordered[T]的子类型。
  //视界：T <% Ordered[T]表达的含义是：任何T都行，只要T能够被当做Ordered[T]。
  //一个问题：如果T就是一个Ordered[T]，T <% Ordered[T]会怎么处理呢？Predef.identity定义了什么都不做的转换，在这里把T转换Ordered[T]。

  //在使用scalac编译时加入-Xprint:typer参数可以看到所有隐式转换被显示化的代码，在Eclipse下目前无法看到编译的输出，在Maven下或命令行下应该可以。
}

object ImplicitUtil { implicit def doubleToInt(x: Double): Int = x.toInt } //这里如果不明确指明返回类型，上面会编译不过，很奇怪。而所在域定义的隐式转换没有此问题。

object Dollar { implicit def dollarToEuro(x: Dollar): Euro = new Euro }
class Dollar
class Euro

class PreferredPrompt(val preference: String)
class PreferredDrink(val preference: String)
object JoesPrefs {
  implicit val prompt: PreferredPrompt = new PreferredPrompt("Yes, master> ") //这里依然必须明确指明返回值类型，估计应该是Scala为了可读性做的妥协。
  implicit val drink: PreferredDrink = new PreferredDrink("tea")
}
