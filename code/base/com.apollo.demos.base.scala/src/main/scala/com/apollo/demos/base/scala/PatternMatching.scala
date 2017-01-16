package com.apollo.demos.base.scala

object PatternMatching extends App {
  def simplifyTop(expr: Expr) = expr match { //模式匹配完成化简表达式的操作。
    case UnOp("-", UnOp("-", e))  => e //--x化简为x。
    case BinOp("+", e, Number(0)) => e //x+0化简为x。
    case BinOp("*", e, Number(1)) => e //x*1化简为x。
    case _                        => expr
  }
  println(simplifyTop(UnOp("-", UnOp("-", Var("x"))))) //验证--x化简为x。

  val expr = BinOp("+", Number(1), Number(1))
  expr match {
    case BinOp(op, left, right) => println(expr + " is a binary operation.")
    case _                      => //匹配后无操作一律返回Unit
  }
  expr match {
    case BinOp(_, _, _) => println(expr + " is a binary operation.") //不关心参数时也可以用_替代。
    case _              => println("It's something else.")
  }

  def describe(x: Any) = x match { //常量模式。
    case 5       => "five"
    case true    => "truth"
    case "hello" => "hi!"
    case Nil     => "the empty list"
    case _       => "something else"
  }
  println(describe(5))
  println(describe(true))
  println(describe("hello"))
  println(describe(Nil))
  println(describe(List(1, 2, 3)))

  println(1 match { //变量模式。
    case 0             => "zero"
    case somethingElse => "not zero: " + somethingElse //指somethingElse为变量，这里可以匹配所有情况。
  })

  import Math.{ E, PI }
  println(E match {
    case PI => "strange math? PI = " + PI //这里的PI是常量模式，指的就是Math.PI。
    case _  => "OK"
  })
  val pi = PI
  println(E match {
    case pi => "strange math? PI = " + pi //这里的pi是变量模式，能匹配任何输入，和上面定义的val pi = PI没有任何关系。
    case _  => "OK" //加上这个case后，会产生编译警告，因为上一个case已经可以匹配所有情况了。
  })
  println(E match {
    case this.pi => "strange math? PI = " + pi //这里加个this就可以使用常量模式了。注意，这时需要pi是val而不能是var。PS：'pi'会报错，按理说应该也是可以的。
    case _       => "OK"
  })

  BinOp("+", Number(1), Number(0)) match { //构造器模式。
    case BinOp("+", e, Number(0)) => println("a deep match") //构造器模式的一个特定是匹配可以无限深度嵌套。
    case _                        =>
  }

  List(0, 1, 2) match { //序列模式。
    case List(0, _, _) => println("found it")
    case _             =>
  }

  List(0, 1, 2, 3, 4, 5) match {
    case List(0, _*) => println("found it") //*表示不检查后续元素个数。
    case _           =>
  }

  ("a", 3, true) match { //元组模式。
    case (a, b, c) => println("matched " + a + " " + b + " " + c)
    case _         =>
  }

  def size(x: Any) = x match { //类型模式。
    case s: String    => s.length
    case s: Set[_]    => s.size
    case m: Map[_, _] => m.size
    case _            => 1
  }
  println(size("abc"))
  println(size(Set("a", "b", "c")))
  println(size(Map(1 -> "a", 2 -> "b")))
  println(size(List("a", "b", "c")))
  println(size(PI))

  def isIntIntMap(x: Any) = x match {
    case m: Map[Int, Int] => true //这里会产生一个编译警告是告诉你模板类型在编译过后会被擦除，这和Java中是一样的。
    case _                => false
  }
  println(isIntIntMap(Map(1 -> 1)))
  println(isIntIntMap(Map("abc" -> "abc"))) //擦除意味着仅能匹配Map类型，而不能匹配Map的模板参数类型，因为编译后的字节码中是不会保存模板参数类型的。

  def isStringArray(x: Any) = x match {
    case a: Array[String] => true //数组是唯一的例外，这个被特殊处理过，Java中也是一样。
    case _                => false
  }
  println(isStringArray(Array(1, 2, 3)))
  println(isStringArray(Array("a", "b", "c")))

  println(UnOp("abs", UnOp("abs", Number(1))) match { //对于2次求绝对值化简。
    case UnOp("abs", e @ UnOp("abs", _)) => e //把深度匹配的中间结果保存在e中，注意@的特殊语法。
    case somethingElse                   => somethingElse
  })

  println(BinOp("+", Number(1), Number(1)) match { //对n+n做化简，变为2n。
    case BinOp("+", x, y) if x == y => BinOp("*", Number(2), x) //模式守卫，if子句让匹配更加精确和灵活。
    case somethingElse              => somethingElse
  })

  def simplifyAll(expr: Expr): Expr = expr match { //和simplifyTop不同，这里定义的方法需要明确指明返回值类型，因为下面存在递归而无法自动推算出类型。
    case UnOp("-", UnOp("-", e))  => simplifyAll(e)
    case BinOp("+", e, Number(0)) => simplifyAll(e)
    case BinOp("*", e, Number(1)) => simplifyAll(e)
    case UnOp(op, e)              => UnOp(op, simplifyAll(e)) //匹配所有UnOp，对其项进一步化简。
    case BinOp(op, left, right)   => BinOp(op, simplifyAll(left), simplifyAll(right)) //匹配所有BinOp，对其项进一步化简。
    case _                        => expr //模式重叠时，按顺序从上到下匹配，当下面匹配完全被上面遮盖时，会有编译警告。
  }
  println(simplifyTop(UnOp("-", UnOp("-", UnOp("-", UnOp("-", Var("x"))))))) //simplifyTop只能处理第一层化简，即----x化简为--x。
  println(simplifyAll(UnOp("-", UnOp("-", UnOp("-", UnOp("-", Var("x"))))))) //----x化简为x。

  def describe1(expr: Expr) = expr match { //这个编译错误是因为，Expr类被定义为sealed，封闭类进行模式匹配时，编译器会检测是否穷尽了所有情况。
    case Number(_) => "a number"
    case Var(_)    => "a variable"
  }
  def describe2(expr: Expr) = expr match { //一种办法是加全匹配消除警告。
    case Number(_) => "a number"
    case Var(_)    => "a variable"
    case _         => throw new RuntimeException //不会发生
  }
  def describe3(expr: Expr) = (expr: @unchecked) match { //这种方式更优雅。注意：这种编译检查仅针对封闭类。
    case Number(_) => "a number"
    case Var(_)    => "a variable"
  }

  val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
  println(capitals.get("France")) //Map返回Option，和JDK8中的Optional类似。
  println(capitals.get("Japan"))
  println(capitals.get("England"))
  def show(capital: Option[String]) = capital match {
    case Some(s) => s //这种匹配相当于剥离了Some封装层。
    case None    => "?"
  }
  println(show(capitals get "France")) //操作符写法，省一对括号。
  println(show(capitals get "Japan"))
  println(show(capitals get "England"))

  val tuple = (123, "abc")
  val (number, string) = tuple //把元组赋值到2个变量中，是一种模式的隐式应用。
  println(number)
  println(string)

  val exp = BinOp("*", Number(5), Number(1))
  val BinOp(op, left, right) = exp //这里不是定义BinOp，而是定了op,left,right3个变量，使用BinOp对exp进行模式解构。
  println(op)
  println(left)
  println(right)

  val withDefault: Option[Int] => Int = {
    case Some(i) => i //在偏函数中直接使用case进行匹配。
    case None    => 0
  }
  println(withDefault(Some(10)))
  println(withDefault(None))

  val second1: List[Int] => Int = { //这里编译警告是说匹配不全，因为List就是一个封闭类。
    case x :: y :: _ => y
  }
  println(second1(List(1, 2, 3))) //如果传入低于2个的元素的List会抛异常。

  val second2: PartialFunction[List[Int], Int] = { //使用偏函数特质定义。
    case x :: y :: _ => y
  }
  println(second2(List(1, 2, 3)))
  println(second2.isDefinedAt(List(1, 2, 3))) //偏函数特质可以检测哪种参数可以匹配。
  println(second2.isDefinedAt(List())) //所谓的偏函数是相对于完整函数而言，偏函数对部分输入是不考虑输出的，即非法输入永远不会发生，这种函数其实非常多。

  new PartialFunction[List[Int], Int] { //second2等同于定义了下面的代码。
    def apply(xs: List[Int]) = xs match {
      case x :: y :: _ => y
    }
    def isDefinedAt(xs: List[Int]) = xs match {
      case x :: y :: _ => true
      case _           => false
    }
  }

  for ((country, city) <- capitals) println("The capital of " + country + " is " + city) //for表达式中也可以使用模式。

  val fruits = List(Some("apple"), None, Some("orange"))
  for (Some(fruit) <- fruits) println(fruit) //从集合中挑选匹配于模式的元素。
}