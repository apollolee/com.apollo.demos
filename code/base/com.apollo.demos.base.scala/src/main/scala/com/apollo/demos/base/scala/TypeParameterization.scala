package com.apollo.demos.base.scala

object TypeParameterization extends App {
  val q = Queue("a", "b", "c")
  println(q.append("d").tail.tail.tail.head)

  class Queue1[T]; //val q1:Queue1[AnyRef] = new Queue1[String] //非协变（严谨的）。
  class Queue2[+T]; val q2: Queue2[AnyRef] = new Queue2[String] //协变。Java中无法做到。
  class Queue3[-T]; val q3: Queue3[String] = new Queue3[AnyRef] //逆变。Java中无法做到。

  val a1 = Array("a", "b", "c")
  //val a2:Array[Any] = a1 //Java中的数组类型参数是协变的，而Scala中是非协变的。
  val a3: Array[Any] = a1.asInstanceOf[Array[Any]] //Scala了为了处理与Java的协同，允许对数组进行asInstanceOf操作来模拟数组类型参数协变。

  class Cell[T](init: T) { //协变的设计和使用都收到了一定限制。
    private[this] var current = init
    def get = current
    def set(x: T) { current = x } //如果+T，会得到编译错误：协变类型T出现在值x的类型T的逆变位置。
  }
  val c1 = new Cell[String]("abc")
  //val c2:Cell[Any] = c1 //非协变，所以这里编译不过。
  //c2.set(1) //假使是协变，set方法中会出现逻辑问题。
  println(c1.get)

  class StrangeIntQueue extends Queue[Int] { //协变错误不光可变字段引起的，实际上要更为普遍。
    def head: Int = 1
    def tail: Queue[Int] = this
    def append(x: Int) = { println(Math.sqrt(x)); this }
  }
  //val x: Queue[Any] = new StrangeIntQueue //非协变，而且无法协变。
  //x.append("abc") //协变错误在编译是被阻止了，而Java中少量的协变错误是在运行时报错处理的。

  class Queue4[+T] {
    //var x: T = _ //实际上，如果T出现在方法的入参中，编译器就会报错，阻止协变定义。包括这里隐式生成的x_=(x:T)中的T。
  }

  //这个例子本身并无实际意义，只是为了说明编译器推断允许协变和逆变的规则，即编译器用的算法。搞清楚这个可以对一些编译器错误理解更为深入。
  abstract class Cat[-T, +U] { //类型参数在定义协变逆变非协变的同时也定义了相应位置上的状态，对应协变逆变非协变有+-和中立3种位置状态。这里Cat类型定义内，T位置上的状态是-，U的位置的状态是+。
    def meow[W](volume: T, listener: Cat[U, T]): Cat[Cat[U, T], U] //类型参数能够出现的每个位置都有+-状态之分。注意：W虽然也是定义，但方法里的定义是没有协变逆变之分，因为方法本身是没有父子层次关系的。
    //       -          -            -           +                 //这里+-是位置状态，而不是定义，可以看到，只有返回值位置上的状态是+，其他都是-。
    //                                   +  -        -          +  //位置的状态+-通过嵌套逐级推断，规则：外层+，内层不变，外层-，内层反转。注意：中立的反转还是中立。
    //                                                   +  -      //位置的状态全部推断出来后，状态为+的位置上可以出现协变类型参数而不允许出现逆变类型参数，-的位置正好相反，而状态为中立的位置对类型参数无限制。
  }

  class Fruit
  class Apple extends Fruit
  class Orange extends Fruit
  val aq = QueueEx(new Apple) //aq推断类型为QueueEx[Apple]
  val fq = aq.append(new Orange) //fq推断类型为QueueEx[Fruit]，Scala很聪明的自动把U推断为最近一级的超类Fruit。
  val fq1 = aq.append((new Orange).asInstanceOf[Fruit]) //fq1相当于fq推断后的代码。
  val fq2 = aq.append((new Orange).asInstanceOf[Any]) //也可以自己指定Any，这时fq2就是QueueEx[Any]了。
  val fq3 = aq.append("abc") //String和Apple最近的共同父类是AnyRef，即Object，所以fq3类型是QueueEx[Object]。
  val fq4 = aq.append(1) //Int和Apple最近的共同父类是Any，所以fq4类型是QueueEx[Any]。

  trait OutputChannel[-T] { //逆变。
    def write(x: T) //需要OutputChannel[String]的地方，可以传递OutputChannel[AnyRef]，而实际的write(x: AnyRef)是可以处理x是String的情况，但反过来就不行。
  }

  //协变与逆变协同的例子。
  class Publication(val title: String)
  class Book(title: String) extends Publication(title)
  object Library {
    val books = Set(new Book("Programming in Scala"), new Book("Walden"))
    def printBookList(info: Book => AnyRef) { for (book <- books) println(info(book)) } //隐性使用scala.Function1，和JDK8中类似。返回值定义为AnyRef是为了体现协变。
  }
  def getTitle(p: Publication): String = p.title //getTitle的类型：Publication => String。
  Library.printBookList(getTitle) //Publication => String传入Book => AnyRef没有编译错误是因为scala.Function1的入参类型是逆变，返回值类型是协变。

  println(QueueFinal("a", "b", "c").append("d").tail.tail.tail.head)

  class Person(val firstName: String, val lastName: String) extends Ordered[Person] {
    def compare(that: Person) = {
      val lastNameComparison = lastName.compareToIgnoreCase(that.lastName)
      if (lastNameComparison == 0) firstName.compareToIgnoreCase(that.firstName) else lastNameComparison
    }
    override def toString = firstName + " " + lastName
  }
  val robert = new Person("Robert", "Jones")
  val sally = new Person("Sally", "Smith")
  println(robert < sally)

  def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = { //T得到了上界限制，要求T必须混入特质Ordered[T]。
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _)             => ys
      case (_, Nil)             => xs
      case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys) else y :: merge(xs, ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs else { val (ys, zs) = xs splitAt n; merge(orderedMergeSort(ys), orderedMergeSort(zs)) }
  }
  val people = List(new Person("Larry", "Wall"), new Person("Anders", "Hejlsberg"), new Person("Guido", "van Rossum"), new Person("Alan", "Kay"), new Person("Yukihiro", "Matsumoto"))
  val sortedPeople = orderedMergeSort(people)
  println(sortedPeople)
  //println(orderedMergeSort(List(1, 2, 3))) //尽管这个例子很好的诠释了上界的用法，但对List的排序并不通用，这里Int并不是Ordered[Int]的子类。
}

trait Queue[T] {
  def head: T
  def tail: Queue[T]
  def append(x: T): Queue[T]
}

object Queue {
  def apply[T](xs: T*): Queue[T] = new QueueImpl[T](xs.toList, Nil)
  private class QueueImpl[T](private val leading: List[T], private val trailing: List[T]) extends Queue[T] { //QueueImpl[+T]会报错，协变不能传递给非协变。
    def mirror = if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil) else this
    def head = mirror.leading.head
    def tail = { val q = mirror; new QueueImpl(q.leading.tail, q.trailing) }
    def append(x: T) = new QueueImpl(leading, x :: trailing)
  }
}

trait QueueEx[+T] { //支持协变的Queue定义，也是Scala所推崇的。
  def head: T
  def tail: QueueEx[T]
  def append[U >: T](x: U): QueueEx[U] //下届，U为T的超类，和Java中U extends T类似。
}

object QueueEx {
  def apply[T](xs: T*): QueueEx[T] = new QueueImpl[T](xs.toList, Nil)
  private class QueueImpl[+T](private val leading: List[T], private val trailing: List[T]) extends QueueEx[T] { //QueueImpl[T]不会报错，非协变能传递给协变。
    def mirror = if (leading.isEmpty) new QueueImpl(trailing.reverse, Nil) else this
    def head = mirror.leading.head
    def tail = { val q = mirror; new QueueImpl(q.leading.tail, q.trailing) }
    def append[U >: T](x: U) = new QueueImpl(leading, x :: trailing)
  }
}

object QueueFinal { //上面Queue中的mirror在leading为空时，每次调用都会做List的空连接，有一定效率问题。这里对mirror做一定改进，并引入了状态数据。
  def apply[T](xs: T*): QueueEx[T] = new QueueImpl[T](xs.toList, Nil)
  private class QueueImpl[+T](private[this] var leading: List[T], private[this] var trailing: List[T]) extends QueueEx[T] { //协变在var上的特例，不加[this]会报错。
    private def mirror() { if (leading.isEmpty) while (!trailing.isEmpty) { leading = trailing.head :: leading; trailing = trailing.tail } }
    def head = { mirror(); leading.head }
    def tail = { mirror(); new QueueImpl(leading.tail, trailing) }
    def append[U >: T](x: U) = new QueueImpl(leading, x :: trailing)
  }
}
