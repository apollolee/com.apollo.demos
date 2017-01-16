package com.apollo.demos.base.scala

class Outer { //Scala没有public关键字，因为缺少的就是public。实际访问控制符就两个，private和protected。
  class Inner {
    private def f() { println("f") }
    class InnerMost { f() } //OK.
  }
  //(new Inner).f() //Java中外部类可以访问内部内私有成员，这里不行。
}

package p {
  class Super { protected def f() { println("f") } }
  class Sub extends Super { f() }
  //class Other { (new Super).f() } //Java中同包中可以访问protected成员，这里不行。
}

package rockets {
  package navigation {
    private[rockets] class Navigator {
      protected[navigation] def useStarChart() {} //保护授权包可见，等价于Java的protected。
      class LegOfJourney {
        private[Navigator] val distance = 100 //私有授权外部类可见。等价于Java的private。
        //总结。
        private[rockets] val distance1 = 100 //在外部包可见。
        private[navigation] val distance2 = 100 //与Java包可见等价，即Java中不加任何访问控制符。
        private[Navigator] val distance3 = 100 //与Java私有语义相同。
        private[LegOfJourney] val distance4 = 100 //与private val distance4 = 100等价，Scala缺省。
        private[this] val distance5 = 100 //对象私有，注意：只有[this]这一个是控制域在原语义上缩小，其他的都是扩大。
      }
      def getDistance = (new LegOfJourney).distance //LegOfJourney.distance是私有的，但类Navigator得到了授权。
      private[this] var speed = 200 //比Java更严格的对象私有控制。
      //def getSpeed(other: Navigator) = other.speed //other不是this，所以speed禁止访问。
    }
    object Go {
      def start(navigator: Navigator) { navigator.useStarChart() } //Navigator.useStarChart是保护的，但包得到了授权。
    }
  }
  package launch {
    import navigation._
    object Vehicle {
      private[launch] val guide = new Navigator
      //def go() { guide.useStarChart() } //不可见。
      def go() { Go.start(guide) }
    }
  }
}

class Rocket {
  import Rocket.fuel //伴生类能够访问伴生对象的私有成员。
  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10
  def chooseStrategy(rocket: Rocket) {
    if (rocket.canGoHomeAgain) //同样，伴生对象也可以访问伴生类的私有成员。
      goHome()
    else
      pickAStar()
  }
  def goHome() {}
  def pickAStar() {}
}
