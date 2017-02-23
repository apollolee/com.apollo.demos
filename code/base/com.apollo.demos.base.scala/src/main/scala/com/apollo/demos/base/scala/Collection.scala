package com.apollo.demos.base.scala

object Collection extends App {
  val emptyList: List[Nothing] = List() //List的T是协变，而Nothing是最底层的类，即所有类的子类。
  val empryStringList: List[String] = emptyList //因为协变，所以emptyList可以当任何List的子类。

  val fruit = "apples" :: "oranges" :: "pears" :: Nil
  val nums = 1 :: 2 :: 3 :: 4 :: Nil
  println(nums.head); println(nums.tail)

  val List(f1, f2, f3) = fruit //明确知道元素个数时使用这种模式匹配进行解构。
  println(f1); println(f2); println(f3)

  val i1 :: i2 :: rest = nums //在不确定具体元素个数的情况下使用。
  println(i1); println(i2); println(rest)

  def isort(xs: List[Int]): List[Int] = xs match { //插入排序例子。
    case List()    => List()
    case x :: rest => insert(x, isort(rest))
  }
  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List()  => List(x)
    case y :: ys => if (x <= y) x :: xs else y :: insert(x, ys)
  }
  println(isort(List(2, 1, 5, 9, 7)))

  println(List(1, 2) ::: List(3, 4, 5))
  def append[T](xs: List[T], ys: List[T]): List[T] = xs match { //手动实现:::方法，这里体现了对递归数据结构的分治原则。
    case List()    => ys
    case x :: rest => x :: append(rest, ys) //匹配模式体现的是“分”，递归算法体现的是“治”。
  }
  println(append(List(1, 2), List(3, 4, 5)))

  println(List(1, 2, 3).length == 0) //length需要遍历全部元素，因此效率不高。
  println(List(1, 2, 3).isEmpty) //用isEmpty判空效率要更高。

  val abcde = List('a', 'b', 'c', 'd', 'e')
  println(abcde.last); println(nums.init) //功能上和head，tail相对，但效率上有差别，head，tail运行是时间常量，而last，init要遍历整个列表，耗时与列表长度成正比。
  println(abcde.reverse) //如果操作集中在列表尾部，通过操作反转后的列表通常操作效率会更高。

  println(abcde.take(2)) //take泛化了init，可以指定任意长度的前缀。
  println(abcde.drop(2)) //drop泛化了tail，但注意，这里是指定丢弃任意长度的前缀后剩下的后缀。
  println(abcde.splitAt(2)) //splitAt从指定位置把列表一分为二，注意，返回的是元组。

  println(abcde.apply(2)); println(abcde(2)) //取指定位置元素，index从0开始，通常使用隐式的apply调用。
  println(abcde.indices) //返回所有有效索引值组成的列表。

  println(abcde.zip(abcde.indices)); println(abcde.zipWithIndex) //zip把任意两个列表组成对偶列表，zipWithIndex方法等同于为这个功能提供的特定方法。
  println(nums.zip(fruit)) //匹配长度最小的列表，其他部分被丢弃。

  println(abcde.mkString("[", ",", "]")) //mkString和JDK8中的StringJoiner功能一致，这里更好理解一些。
  val buf = new StringBuilder("hollo"); abcde.addString(buf, "[", ",", "]"); println(buf) //addString是mkString的变体，可以更好的处理字符串拼接。

  val arr = abcde.toArray
  println(arr); println(arr.toList) //toArray和toList能够相互转换，注意：arr无法正确显示。

  val arrInt = new Array[Int](10)
  List(1, 2, 3).copyToArray(arrInt, 3) //插入指定数组的指定位置。
  println(arrInt.toList)

  val it = abcde.iterator //等同于老版本中elements。
  println(it.next); println(it.next) //迭代访问。

  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = { //归并排序例子，效率比插入排序要高。
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _)             => ys
      case (_, Nil)             => xs
      case (x :: xs1, y :: ys1) => if (less(x, y)) x :: merge(xs1, ys) else y :: merge(xs, ys1)
    }
    val n = xs.length / 2
    if (n == 0) xs else { val (ys, zs) = xs.splitAt(n); merge(msort(less)(ys), msort(less)(zs)) }
  }
  println(msort((x: Int, y: Int) => x < y)(List(5, 7, 1, 3))) //普通用法。
  val intSort = msort((x: Int, y: Int) => x < y)_; val reverseIntSort = msort((x: Int, y: Int) => x > y)_ //柯里化用法。
  val mixedInts = List(4, 1, 9, 0, 5, 8, 3, 6, 2, 7)
  println(intSort(mixedInts)) //正向排序。
  println(reverseIntSort(mixedInts)) //反向排序。

  println(List(1, 2, 3).map(_ + 1))
  val words = List("the", "quick", "brown", "fox")
  println(words)
  println(words.map(_.length))
  println(words.map(_.toList.reverse.mkString))
  println(words.map(_.toList))
  println(words.flatMap(_.toList))
  println(List.range(1, 5).flatMap(i => List.range(1, i).map(j => (i, j)))) //构建1<=j<i<5的(i,j)对偶。
  println(for (i <- List.range(1, 5); j <- List.range(1, i)) yield (i, j)) //用for语句同样能构建1<=j<i<5的(i,j)对偶。
  var sum = 0; List(1, 2, 3, 4, 5).foreach(sum += _); println(sum)

  println(List(1, 2, 3, 4, 5).filter(_ % 2 == 0)) //可以看出，这里比JDK8更简洁，在filter后直接是原类型了，而在JDK8中还需要collect才能变成想要的类型。
  println(words.filter(_.length == 3))
  println(List(1, 2, 3, 4, 5).partition(_ % 2 == 0))
  println(List(1, 2, 3, 4, 5).find(_ % 2 == 0))
  println(List(1, 2, 3, 4, 5).find(_ <= 0))
  println(List(1, 2, 3, -4, 5).takeWhile(_ > 0))
  println(words.dropWhile(_.startsWith("t")))
  println(List(1, 2, 3, -4, 5).span(_ > 0)) //xs span p 等价于 (xs takeWhile p, xs dropWhile p)

  println(List(1, 2, 3, 4, 5).forall(_ > 0)) //forall等同于JDK8中的allMatch
  println(List(1, 2, 3, -4, 5).forall(_ > 0))
  println(List(1, 2, 3, -4, 5).exists(_ > 0)) //exists等同于JDK8中的anyMatch
  println(List(1, 2, 3, -4, 5).exists(_ < 0))

  def sum(xs: List[Int]): Int = (0 /: xs)(_ + _) //左折叠操作符/:是柯里化定义，第二参数列表是一个函数。操作符柯里化调用有点怪，习惯就好。
  println(sum(List(1, 2, 3, 4, 5)))
  def product(xs: List[Int]): Int = (1 /: xs)(_ * _) //(z /: List(a, b, c))(op) 等价于 op(op(op(z, a), b), c)，在JDK8中可以通过reduce完成一样的功能，但较为难理解。
  println(product(List(1, 2, 3, 4, 5)))
  println(("" /: words)(_ + " " + _)) //最前面多了一个空格。
  println((words.head /: words.tail)(_ + " " + _)) //想去掉这样写就可以了。
  def flattenLeft[T](xss: List[List[T]]) = (List[T]() /: xss)(_ ::: _) //针对flatten功能，左折叠和右折叠都能实现，但右折叠效率更高。
  def flattenRight[T](xss: List[List[T]]) = (xss :\ List[T]())(_ ::: _) //右折叠，/和\代表着操作树方向，具有记忆作用。
  println(flattenLeft(List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))))
  println(flattenRight(List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))))
  println(words.foldLeft("")(_ + " " + _)) //如果操作符不喜欢，可以直接调用方法，功能一样的。
  println(words.foldRight("")(_ + " " + _))
  def reverseLeft[T](xs: List[T]) = (List[T]() /: xs)((ys, y) => y :: ys) //一种耗时为线性关系的List反转算法。
  println(reverseLeft(List(1, 2, 3, 4, 5)))

  println(List(1, -3, 4, 2, 6).sortWith(_ < _))
  println(List(1, -3, 4, 2, 6).sorted) //注意：sorted是柯里化定义，第一参数列表为implicit的，Int有缺省实现，所有不需要设置，非常简洁的实现。
  println(words.sortWith(_.length < _.length))
  println(words.sortBy(_.length)) //同样sortBy也是柯里化定义的，第二参数列表为implicit的，Int有缺省实现，所有不需要设置。

  println(List.apply(1, 2, 3))
  println(List.range(1, 5))
  println(List.range(1, 9, 2))
  println(List.range(9, 1, -3))
  println(List.fill(5)('a')) //老版本make完成相同功能。

  val zipped = "abcde".toList.zip(List(1, 2, 3))
  println(zipped)
  println(zipped.unzip) //老版本unzip在List对象上，而不是类上。

  println(List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9)).flatten) //老版本flatten在List对象上，而不是类上。
  println(List.concat(List('a', 'b'), List('c')))
  println(List.concat(List(), List('b'), List('c')))
  println(List.concat())
}
