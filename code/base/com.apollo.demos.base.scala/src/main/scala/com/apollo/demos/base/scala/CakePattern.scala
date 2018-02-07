package com.apollo.demos.base.scala

object CakePattern extends App {
  trait EngineComponent { //引擎组件。
    trait Engine {
      object FuelType extends Enumeration { //燃料类型。
        type FuelType = Value
        val Diesel = Value("1") //柴油。
      }

      private var running = false

      def start(): Unit = { running = true /* ... */ }
      def stop(): Unit = { /* ... */ running = false }
      def isRunning: Boolean = running
      def fuelType: FuelType.FuelType
    }

    protected val engine: Engine //蛋糕模式每个组件都有一个用于覆写的对象。

    protected class DieselEngine extends Engine { //柴油引擎。
      override val fuelType = FuelType.Diesel
    }
  }

  trait CarComponent { //汽车组件。
    this: EngineComponent => //这个自类型是关键。

    trait Car {
      def drive(): Unit
      def park(): Unit
    }

    protected val car: Car

    protected class HondaCar extends Car {
      override def drive() { engine.start(); println("Vroom vroom") } //engine是EngineComponent中的，得益于自类型，开车前可以启动引擎。
      override def park() { println("Break!"); engine.stop() }
    }
  }

  trait FueltankComponent { //油箱组件。
    class Fueltank(val capacity: Int)
    protected val fueltank: Fueltank
  }

  trait GearboxComponent { //变速箱组件。
    trait Gearbox
    protected val gearbox: Gearbox
    protected class FiveGearbox extends Gearbox //五档变速箱。
  }

  object MyCar extends CarComponent with EngineComponent with FueltankComponent with GearboxComponent { //MyCar是一辆Honda，柴油发动机，60升的油箱，5级变速的车。
    override protected val engine = new DieselEngine()
    override protected val fueltank = new Fueltank(capacity = 60)
    override protected val gearbox = new FiveGearbox()
    override val car = new HondaCar()
  }

  MyCar.car.drive()
  MyCar.car.park()

  /*
   * 重点：
   *   1.HondaCar在实现过程中使用到了Engine，但是它即没有继承Engine也没实例化一个Engine字段，这和传统的依赖注入在效果上是无差别的，实际上就是实
   *     现了把Engine注入到HondaCar的目标。
   *   2.粘合互相依赖的组件的过程发生在MyCar的定义中。所有的组件都预留了protected val的字段，留待组装粘合的时候实例化。
   *   3.主动要去依赖其他组件的组件必定要将依赖的组件声明成自身类型，以便在组件内部自由引用被依赖组件的成员和方法。
   * 小结：
   *   蛋糕模式完全依赖语言自身的特性，没有外部框架依赖，类型安全，可以获得编译期的检查。但缺点也是很明显的，代码复杂，配置不灵活。就个人而言，不太会选择使用。
   */
}
