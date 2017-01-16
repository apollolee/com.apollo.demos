package com.apollo.demos.base.scala.import0 {
  class Fruit(val name: String, val color: String)
  object Fruits {
    val Apple = new Fruit("Apple", "Red")
    val Orange = new Fruit("Orange", "Yellow")
    val Pear = new Fruit("Pear", "Yellow")
  }
  object Notebooks { val Apple = "MacBook" }
}

package com.apollo.demos.base.scala {
  package import01 {
    import com.apollo.demos.base.scala.import0._ //_在这里等同于Java中的*。
    class Import {
      val fruit = Fruits.Apple
      println(Notebooks.Apple)
    }
  }
  package import02 {
    import com.apollo.demos.base.scala.import0.Fruit
    import com.apollo.demos.base.scala.import0.Fruits
    class Import {
      val fruit = Fruits.Apple
      //println(Notebooks.Apple) //不在访问空间。
    }
  }
  package import03 {
    import com.apollo.demos.base.scala.import0.Fruit
    import com.apollo.demos.base.scala.import0.Fruits._ //相当于Java中的静态导入。
    class Import {
      val fruit = Apple
    }
  }
  package import04 {
    import com.apollo.demos.base.scala.import0.Fruit
    class Import {
      def showFruit(fruit: Fruit) {
        import fruit._ //引用不一定都要写在开头，可以在域的任何地方，而且可以引用对象内的成员，比如这里的fruit是个对象。
        println(name + "：" + color) //引用后，可以直接在域内使用fruit对象的成员。
      }
    }
  }
  package import05 {
    import java.util.regex //引用包。
    class Import {
      val pat = regex.Pattern.compile("a*b")
    }
  }
  package import06 {
    import com.apollo.demos.base.scala.import0.Fruits.{ Apple, Orange } //过滤细节。
    class Import {
      val apple = Apple
      val orange = Orange
      //val pear = Pear //Pear没有被引用。
    }
  }
  package import07 {
    import com.apollo.demos.base.scala.import0.Fruits.{ Apple => McIntosh, Orange } //重命名引用。
    class Import {
      val apple = McIntosh
    }
  }
  package import08 {
    import java.sql.{ Date => SDate }
    class Import {
      val date = SDate.valueOf("2016-1-1")
    }
  }
  package import09 {
    import java.{ sql => S }
    class Import {
      val date = S.Date.valueOf("2016-1-1")
    }
  }
  package import10 {
    import com.apollo.demos.base.scala.import0.Fruits.{ Apple => McIntosh, _ } //Apple被换成McIntosh，余下的被引用。
    class Import {
      //val apple = Apple //Apple不可用。
      val apple = McIntosh
      val orange = Orange
      val pear = Pear
    }
  }
  package import11 {
    import com.apollo.demos.base.scala.import0.Fruits.{ Pear => _, _ } //表示Pear不被引用，余下的被引用。特别注意这里=>_的用法。
    class Import {
      val apple = Apple
      val orange = Orange
      //val pear = Pear //Pear不可用。
    }
  }
  package import12 {
    import com.apollo.demos.base.scala.import0.Notebooks._
    import com.apollo.demos.base.scala.import0.Fruits.{ Apple => _, _ } //表示Apple不引用，实际上是意图是Apple用Notebooks中的。
    class Import {
      val apple = Apple //这里Apple用的是Notebooks.Apple。
      val orange = Orange
      val pear = Pear
    }
  }
}
