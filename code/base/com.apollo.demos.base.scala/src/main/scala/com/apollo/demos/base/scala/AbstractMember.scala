package com.apollo.demos.base.scala

object AbstractMember extends App {
  trait Abstract {
    type T //抽象类型，很像参数化类型，但比参数化类型要简单，而且更加封闭。
    def transform(x: T): T //抽象方法。
    val initial: T //抽象val。
    var current: T //抽象var。
  }
  class Concrete extends Abstract {
    type T = String
    def transform(x: String): String = x + x
    val initial: String = "hi"
    var current: String = initial
  }

  abstract class Fruit {
    val v: String
    def m: String
  }
  abstract class Apple extends Fruit {
    val v: String
    val m: String //可以用val重写def。
  }
  abstract class BadApple extends Fruit {
    //def v: String //不可以用def重写val。
    def m: String
  }

  trait AbstractTime { //抽象var会隐式生成抽象的getter和setter方法。
    var hour: Int
    var minute: Int
  }

  trait RationalTrait {
    val numerArg: Int
    val denomArg: Int
    override def toString = numerArg + "/" + denomArg
  }
  println(new RationalTrait { val numerArg = 1; val denomArg = 2 }) //类似于Java的匿名内部类对象的创建。

  trait RationalTraitX {
    val numerArg: Int
    val denomArg: Int
    require(denomArg != 0)
    private val g = gcd(numerArg.abs, denomArg.abs)
    val numer = numerArg / g
    val denom = denomArg / g
    override def toString = numer + "/" + denom
    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  }
  try {
    println(new RationalTraitX { val numerArg = 2; val denomArg = 4 }) //子类绑定抽象val发生在特质初始化后，所以未初始化的denomArg还是0，导致require检测不过。
  } catch { case t: Throwable => t.printStackTrace() }
  println(new { val numerArg = 2; val denomArg = 4 } with RationalTraitX) //必须用这种预初始化字段语法。

  object TwoThirds extends { val numerArg = 2; val denomArg = 3 } with RationalTraitX //object初始化字段。
  println(TwoThirds)

  class RationalClass(n: Int, d: Int) extends { val numerArg = n; val denomArg = d } with RationalTraitX //class初始化字段。
  println(new RationalClass(3, 4))

  println(new { val numerArg = 2; val denomArg = numerArg * 2 } with RationalTraitX) //OK.
  //println(new { val numerArg = 2; val denomArg = this.numerArg * 2 } with RationalTraitX) //this还没初始化好，不能用。

  object Demo {
    val x = { print("Initializing x -> "); "Done" }
  }
  Demo; print("Demo -> "); println(Demo.x)
  object LazyDemo {
    lazy val x = { print("Initializing x -> "); "Done" } //lazy val会在第一次使用时才初始化。小技巧：{…}可以求值，相当于x有了一个无参构造器。
  }
  LazyDemo; print("LazyDemo -> "); println(LazyDemo.x)

  trait LazyRationalTrait {
    val numerArg: Int
    val denomArg: Int
    private lazy val g = { require(denomArg != 0); gcd(numerArg.abs, denomArg.abs) }
    lazy val numer = numerArg / g
    lazy val denom = denomArg / g
    override def toString = numer + "/" + denom
    private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  }
  println(new LazyRationalTrait { val numerArg = 2; val denomArg = 4 }) //使用lazy val的方式后，用匿名创建方式也OK了。

  class Food
  abstract class Animal {
    def eat(food: Food)
  }
  class Grass extends Food
  /*class Cow extends Animal {
    override def eat(food: Grass) {} //想让牛只吃草，这种建模做不到。
  }*/
  class Fish extends Food
  //val bessy: Animal = new Cow //假设上面的Cow定义成功。
  //bessy.eat(new Fish) //这里从api上可以执行牛吃鱼，而实际调用却不符合Cow.eat定义，当然也不合理。

  abstract class AnimalX {
    type SuitableFood <: Food //和类型参数化相似，这里也可以使用上界和下界。
    def eat(food: SuitableFood)
  }
  class Cow extends AnimalX {
    type SuitableFood = Grass
    override def eat(food: Grass) {} //OK。
  }
  val bessy: AnimalX = new Cow
  //bessy.eat(new Fish) //不能吃鱼。
  //bessy.eat(new Grass) //不能吃草？
  //bessy.eat(new Food) //不能吃食物！？eat使用了子类中定义的类型，意味着父类对象禁止调用这个方法。

  class DogFood extends Food
  class Dog extends AnimalX {
    type SuitableFood = DogFood
    override def eat(food: DogFood) {}
  }

  val lassie = new Dog
  val bootsie = new Dog
  val james = new Cow
  lassie.eat(new bootsie.SuitableFood) //两条狗的食物可以相互吃，注意：这里语法允许new一个type。
  //lassie.eat(new james.SuitableFood) //狗不能吃牛适合的食物。

  class Outer { class Inner } //内部类，和Java比较相似。
  val o1 = new Outer
  val o2 = new Outer
  val o1i1 = new o1.Inner

  object Color extends Enumeration { //Scala中没有枚举机制，而是使用内部类的方式实现。
    val Red, Green, Blue = Value
  }
  for (c <- Color.values) println(c)

  object Direction extends Enumeration {
    val North = Value("North")
    val East = Value("East")
    val South = Value("South")
    val West = Value("West")
  }
  for (d <- Direction.values) println(d)
  println(Direction.East.id)
  println(Direction(1))
}
