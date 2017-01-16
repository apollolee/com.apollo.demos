package com.apollo.demos.base.scala

import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object Helloworld extends App {
  println("Hello, world!")

  val i: Int = 1 //i是变量名，冒号后面为变量的类型。
  //var s: String //变量定义必须赋值（初始化），这点和Java差别不同。

  val a = 1 //变量类型一般可以省略，由编译器推断出来。
  //a = 2 //val等同于final，不可以再次赋值。

  var b = 1
  b = 2 //var可以再次赋值。

  def max1(x: Int, y: Int): Int = { //连return都省了。
    if (x > y) x
    else y
  }
  println(max1(3, 4))

  def max2(x: Int, y: Int): Int = if (x > y) x else y //进一步简化。
  println(max1(4, 5))

  def greet1(): Unit = println("Hello, world!") //Unit在可以这里等同于void。
  greet1()
  greet1 //无参方法在这里调用可以连括号都省了，特别注意：有些时候这种省略形式是必须的。

  def greet2() = println("Hello, scala world!") //连Unit也可以省略。
  greet2

  val greet = Array("Hello", "scala", "world")
  greet.foreach(s => println(s)) //这个和JDK8的函数式编程很像。
  for (s <- greet) println(s) //另一种写法。
  for (i <- 0 to 2) println(greet(i)) //数组访问下标不是greet[i]，而是像方法调用一样。
  for (i <- 0.to(2)) println(greet(i)) //to其实是个方法调用。

  println(1 + 2)
  println(1.+(2)) //Scala没有操作符的概念，所有符号都可以作为方法名，而方法调用又可以简写，所以1+2看起来像表达式一样，实际上是一个方法调用。

  println(greet(0)) //greet(0)就是 greet.apply(0)的简写。
  println(greet.apply(0))

  greet(0) = "Hi1"; println(greet(0)) //greet(0) = "Hi"就是greet.update(0, "Hi")的简写。
  greet.update(0, "Hi2"); println(greet(0))

  val is1 = List(1, 2)
  val is2 = List(3, 4)
  val is3 = is1 ::: is2; println(is3)
  val is4 = is2.:::(is1); println(is4) //没错:::也是一个方法，不过这里有一个特例，以:结尾的方法调用者是右侧操作数。
  val is5 = 0 :: is1; println(is5)
  val is6 = is1.::(0); println(is6)
  val is7 = 1 :: 2 :: 3 :: Nil; println(is7) //Nil表示空的List::右操作数可以级联使用。
  val is8 = Nil.::(3).::(2).::(1); println(is8) //上面的简化调用更加自然。

  //List上的常用方法，很多和JDK8中的Stream一样，不过这里和List结合的更紧密，不需要先取List的Stream。
  val ss = "will" :: "fill" :: "until" :: Nil
  println(ss.count(s => s.length == 4))
  println(ss.mkString(";"))
  println(ss.mkString("[", ";", "]"))
  println(ss.drop(2))
  println(ss.dropRight(2))
  println(ss.dropWhile(s => s.length == 4)) //dropWhile等同于老版Scala的remove方法。
  println(ss.exists(s => s == "until"))
  println(ss.filter(s => s.length == 4))
  println(ss.forall(s => s.endsWith("l")))
  ss.foreach(s => println(s))
  ss.foreach(println)
  println(ss.isEmpty) //注意：isEmpty方法调用必须省略括号，否则编译不过。
  println(ss.length)
  println(ss.head)
  println(ss.last)
  println(ss.init)
  println(ss.tail)
  println(ss.map(s => s + "y"))
  println(ss.reverse)
  println(ss.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)) //sortWith等同于老版Scala的sort方法。

  val pair = (99, "hello") //元组在Java中没有，其实非常有用。
  println(pair._1) //注意：元组序号从1开始，而不是0。
  println(pair._2)

  val t3 = Tuple3(99, "hello", true) //元组类型名一般省略，系统最高支持Tuple22。
  println(t3._1)
  println(t3._2)
  println(t3._3)

  var set = Set("a", "b")
  set += "c" //因为set没有+=方法，这个+=先会被具体化为set = set + "c"，然后再调用set.+方法，最后因为set没有定义=方法，所以使用系统赋值逻辑。
  println(set.contains("c"))

  val mutableSet = scala.collection.mutable.Set("a", "b")
  mutableSet += "c" //val为什么可以重新赋值？因为mutableSet定义了+=方法，所以这个+=是方法调用，而不是赋值。
  println(mutableSet.contains("c"))

  var map = Map(1 -> "a", 2 -> "b") //在Java中，这句需要拆成好多行写才行。
  map += 3 -> "c"
  println(map(3))

  val mutableMap = scala.collection.mutable.Map(1 -> "a", 2 -> "b")
  mutableMap += 3 -> "c"
  println(mutableMap(3))

  Source.fromFile("./pom.xml").getLines().foreach(println) //foreach是个高阶函数。

  class ChecksumAccumulator {
    var value = 3 //没有访问控制符表示public，也就是Scala的类中缺省是public。
    private var sum = 0
    def checksum(): Int = ~(sum & 0xFF) + 1
    def add(b: Byte) {
      //b = 2 //方法入参都是val的，等同于Java入参加上final。
      sum += b
    }
  }

  val ca = new ChecksumAccumulator
  println(ca.value)
  //println(ca.sum) //私有的会报编译错误。
  ca.add(2)
  println(ca.checksum())

  object ChecksumAccumulator { //object表示单例对象，功能可以类比Java中的static。
    private val cache = scala.collection.mutable.Map[String, Int]()
    def calculate(s: String): Int =
      if (cache.contains(s))
        cache(s)
      else {
        val ca = new ChecksumAccumulator
        for (c <- s) ca.add(c.toByte)
        val cs = ca.checksum()
        cache += s -> cs
        cs
      }
  }

  println(ChecksumAccumulator.calculate("Hello"))

  //String的三引号特殊语法，可以避免使用转意符。
  println("""Hello,
             new String !""")
  println("""|Hello,
             |new String !""".stripMargin)

  val symbol = 'abc //'打头的字面量会被映射为scala.Symbol，用于动态语言中指定标识符。
  println(symbol.getClass)

  //中缀操作符。
  println(1 + 2)
  println("Hello, world!" indexOf 'o')
  println("Hello, world!" indexOf ('o', 5))

  //前缀操作符。（标识符中能作为前缀操作符的只有+、-、!和~四个，定义时方法名必须带有unary_前缀）
  println(-7) //等同于下面的调用。
  println(7.unary_-)

  //后缀操作符。（后缀操作符方法都不带参数）
  println(7 toLong)

  //注意：这里定义不带参数方法时，可以带()，也可以不带，Scala的惯例是，如果没有副作用就去掉()，否则就加上()。
  def show1() { println("Hello, world!") }
  def show2 { println("Hello, world!") }
  show1 //带()调用时，可以带()也可以不带。
  show1()
  show2
  //show2() //不带()调用时也必须不带()，否则编译错。有一个潜在好处是：如果show2定义从函数变成变量，调用处将不会有任何影响。

  println(List(1, 2, 3) == null)
  println(null == List(1, 2, 3))

  println(2 + 2 * 7) //Scala根据操作符的第一个字母优先级判断操作符的优先级，参见Scala操作符优先级表。
  println(2 << 2 + 2) //等同于2 << (2 + 2)

  class Rational(n: Int, d: Int) extends Ordered[Rational] { //混入特质Ordered，类似于Java中实现Comparable接口，不过功能更强大。
    require(d != 0)
    private val g = gcd(n.abs, d.abs)
    val numer = n / g
    val denom = d / g
    def this(n: Int) = this(n, 1) //辅助构造器。
    def add(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
    def +(that: Rational): Rational = add(that)
    def +(i: Int): Rational = this + new Rational(i) //注意：这里只是演示了一下重载方法，其实有了隐式转换，这些Int的重载都是不需要的。
    def -(that: Rational): Rational = new Rational(numer * that.denom - that.numer * denom, denom * that.denom)
    def -(i: Int): Rational = this - new Rational(i)
    def *(that: Rational): Rational = new Rational(numer * that.numer, denom * that.denom)
    def *(i: Int): Rational = this * new Rational(i)
    def /(that: Rational): Rational = new Rational(numer * that.numer, denom * that.numer)
    def /(i: Int): Rational = this / new Rational(i)
    override def toString = numer + "/" + denom
    def compare(that: Rational) = this.numer * that.denom - that.numer * this.denom //实现了特质Ordered的抽象方法。
    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b) //求最大公约数。
  }

  val x = new Rational(1, 2)
  val y = new Rational(2, 3)
  println(x + y)
  println(x + x * y)
  println((x + x) * y)
  println(x + (x * y))
  println(y * y)
  println(y * 2)
  println(x > y)
  println(x < y)

  implicit def intTORational(x: Int) = new Rational(x) //隐式转换帮助编译器将下面的2转换为Rational，从而正常调用。
  println(2 * y)

  Thread.`yield`() //yield是Scala的关键字，需要用单引号扩起变成字面量标识符。

  println(if (3 > 5) 5 else 8)

  def greet3() { println("hi") }
  println(greet3() == ()) //()表示Unit。这里会有一个编译警告：比较两个Unit类型是否相等将总是返回true。

  val files = new File(".").listFiles()
  for (file <- files) println(file)
  for (i <- 1 to 4) println("Iteration " + i)
  for (file <- files if file.isFile if file.getName.endsWith(".xml")) println(file) //if子句等同于过滤效果，可以添加多个。
  for (i <- 1 until 4) println("Iteration " + i)
  for (
    file <- files if file.getName.endsWith(".xml"); //注意：这个的分号在for()时是不可省略的，但如何换成for{}分号可省略。
    line <- Source.fromFile(file).getLines if line.trim().contains("<url>") //<-也可以添加多个，等同于多重循环。
  ) println(file + ": " + line.trim()) //if子句等同于过滤效果，可以添加很多个。
  for (
    file <- files if file.getName.endsWith(".xml");
    line <- Source.fromFile(file).getLines; trimmed = line.trim if trimmed.contains("<url>") //trimmed是流间变量绑定，可以减少trim方法的调用开销。注意分号的灵活应用。
  ) println(file + ": " + trimmed)

  val xmlNames = for (file <- files if file.getName.endsWith(".xml")) yield file.getName //通过yield，for表达式也能求值了，相当于收集每次循环的值放在一起。
  xmlNames.foreach(println)

  try {
    val fr = new FileReader("aaa.txt")

  } catch {
    case ex: FileNotFoundException => println("File not found.")
    case ex: IOException           => println("IO Error.")

  } finally {
    println("Do finally.")
  }

  def f: Int = try { return 1 } finally { return 2 }
  def g: Int = try { 1 } finally { 2 } //通过finally返回值不是一个好主意。
  println(f)
  println(g)

  val n = 2
  val n2x = n match { //switch被match取代，但match的匹配能力要强大的多，后面单独演示。
    case 1 => "a"
    case 2 => "b"
    case 3 => "c"
    case _ => "z"
  }
  println(n2x)

  val a1 = 1; //注意需要加分号。
  {
    val a1 = 2 //内层a1会遮盖外层a1。Java中是不允许这种定义的。
    println(a1)
  }
  println(a1)

  def makeRowSeq(row: Int) =
    for (col <- 1 to 9) yield {
      val prod = (row * col).toString
      val padding = " " * (4 - prod.length)
      padding + prod
    }
  def makeRow(row: Int) = makeRowSeq(row).mkString
  def multiTable = (for (row <- 1 to 9) yield makeRow(row)).mkString("\n")
  println(multiTable)

  def multiTableX = {
    def makeRow(row: Int) = {
      def makeRowSeq = //注意：makeRowSeq在语义上使用的是外层的row，可以省略定义，进一步简化。
        for (col <- 1 to 9) yield {
          val prod = (row * col).toString
          val padding = " " * (4 - prod.length)
          padding + prod
        }
      makeRowSeq.mkString //这里就可以看的到简化调用的效果了。
    }
    (for (row <- 1 to 9) yield makeRow(row)).mkString("\n")
  }
  println(multiTableX) //本地函数灵活使用可以减少命名空间的污染，同时也是一种信息隐藏手段。

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

  val abc = "abc"
  //def abc = "abc" //变量和函数共享命名空间，即变量和函数在同一域内不能重名。

  val cat: Cat = new Tiger(true, 5)
  println(cat.getAge)
  println(cat.hello1)
  println(cat.hello2)
  println(cat.dangerous)
  println(cat.isItDangerous)

  def error(message: String): Nothing = throw new RuntimeException(message) //Nothing是所有类最下层的子类。
  def divide1(x: Int, y: Int): Int = if (y != 0) x / y else error("Can't divide by zero") //error返回Nothing，Nothing是Int的子类，所以编译没有问题。

  def empty: Null = null //Null是所有AnyRef最下层的子类。
  def divide2(x: Int, y: Int): String = if (y != 0) (x / y).toString else empty //empty返回Null，Null是String的子类，所以编译没有问题。

  val any: Any = "abc"
  if (any.isInstanceOf[String]) { //Scala并不鼓励类型识别和转换，通常使用模式匹配会更好。
    println(any.asInstanceOf[String].length)
  }

  class Time1 { //Scala版的JavaBean。
    var hour = 12
    var minute = 0
  }
  val time1 = new Time1
  time1.hour = 5; println(time1.hour) //赋值。
  time1.hour_=(10); println(time1.hour) //xxx_=是Scala版的setXxx。

  class Time2 { //Time实际上会被编译为Time2。所有的非私有变量都会被配置上getter和setter。
    private[this] var h = 12
    private[this] var m = 0
    def hour: Int = h
    def hour_=(x: Int) { h = x }
    def minute: Int = m
    def minute_=(x: Int) { m = x }
  }

  class Time3 {
    private[this] var h = 12
    private[this] var m = 0
    def hour: Int = h
    def hour_=(x: Int) { println("set hour"); h = x }
    def minute: Int = m
    def minute_=(x: Int) { m = x }
  }
  val time3 = new Time3
  time3.hour = 10 //xxx = 10 -> xxx_=(10)

  class Thermometer { //温度计字段只保存了摄氏度一个数据，而华氏度是转换出来的。
    var celsius: Float = _ //_表示用未初始化值，在Java中不需要赋值即可，但Scala中，那种写法被赋予另外的含义，所以这里必须用= _，这里Float未初始化值为0。
    def fahrenheit = celsius * 9 / 5 + 32
    def fahrenheit_=(f: Float) { celsius = (f - 32) * 5 / 9 }
    override def toString = fahrenheit + "F/" + celsius + "C"
  }
  val thermometer = new Thermometer
  println(thermometer)
  thermometer.fahrenheit = 0; println(thermometer) //从外部使用上看，像同时提供了2个字段一样。
  thermometer.celsius = 100; println(thermometer)

}

object Hellomain {
  def main(args: Array[String]): Unit = {
    println("Hello, main!")
  }
}

object FileMatcher {
  private def files(file: File): List[File] = file :: (if (file.isDirectory()) file.listFiles().flatMap(files).toList else Nil)
  private def files: List[File] = files(new File("."))
  private def filesMatching(matcher: String => Boolean) = for (file <- files if matcher(file.getName)) yield file
  def filesEnding(query: String) = filesMatching(_.endsWith(query))
  def filesContaining(query: String) = filesMatching(_.contains(query))
  def filesRegex(query: String) = filesMatching(_.matches(query))
}

abstract class Cat(private var age: Int) { //构造器参数带有val或者var的表示参数即字段，还可以带有访问控制符等。
  val dangerous = false //想禁止多态可以加final修饰。
  def getAge = age
  def hello1 = "I'm a cat."
  def hello2: String //没有实现的方法等同于Java中的抽象方法，但Scala中不能加abstract修饰。注意：此时需要加上返回值类型，否则是Unit。
  def isItDangerous = if (dangerous) "Yes, I'm dangerous." else "No, I'm not dangerous." //字段的多态。
}

class Tiger(override val dangerous: Boolean, age: Int) extends Cat(age) { //同名字段和方法一样，必须覆写。参数中的age是普通参数，不是字段。
  override def hello1 = "I'm a tiger." //当覆写的是具体方法时，override必选。注意：这里依然不允许不覆写父类的同名方法。
  def hello2 = "I'm a tiger." //当覆写的是抽象方法时，override可选。
}

final abstract class NoInstance1 //这种定义是一个小技巧，类似于Java中把final类的无参构造器定义为private，得到一个无法new的类。
class NoInstance2 private //定义个私有构造器。
