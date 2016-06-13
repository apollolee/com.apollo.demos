/*
 * 此代码创建于 2016年6月7日 上午11:55:30。
 */
package com.apollo.demos.base.jdk8;

import static java.lang.Thread.currentThread;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class Lambda {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        //最常用的线程输出。
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Hello world. [" + currentThread().getName() + "]");
            }

        }).start();

        //换成Lambda表达式后语法更紧凑。
        new Thread(() -> System.out.println("Hello lambda world. [" + currentThread().getName() + "]")).start();

        //Lambda表达式可以引用外层局部变量，实际效果等同于final，Lambda表达式也被称为闭包，和这种行为有一定关系。
        String name = "Nina";
        //name = "Jon"; //如果再次修改局部变量编译会报错，这种是既成事实的final。
        Runnable r = () -> System.out.println("Hello " + name + "."); //Lambda表达式实际上是一个函数接口，即只有一个方法的接口。

        //几种Lambda表达式的常见形式。
        Comparator<Integer> c = (i1, i2) -> i1.compareTo(i2);
        Function<Integer, String> p = (i) -> i.toString();
        p = i -> i.toString(); //单参数一般省略括号。
        p = (Integer i) -> { //可以选择带上参数类型和明显的return，但这种语法使用不多。
            return i.toString();
        };
        p = String::valueOf; //::操作符实际上是一种方法重定向，靠参数匹配，有了::操作符，Lambda表达式就可以始终保持精简状态。

        //高阶函数是指参数或返回值是函数接口的方法。
        //高阶函数也支持重载，但因为Lambda经常会省略类型，所以无法进行类型推断的场景会更多，这时Lambda表达式同样支持强制转型，但这样做不好。
        //如果IntPredicate扩展Predicate则系统直接推到IntPredicate，但因为函数接口只有一个方法，所以这种扩展其实意义不大。
        //基于以上原因，高阶函数最好不要设计使用重载，换个不同的函数名更好，这是基于函数式编程的设计思路。
        testOverload((IntPredicate) i -> true);

        //默认方法扩展了接口的功能。
        //在JDK8中，很多接口的向下兼容特性都依赖于默认方法机制。
        //在函数接口中，可以实现多个默认方法，但抽象方法必须只能有一个，Lambda表达式只会匹配这一个抽象方法。
        //函数默认方法又重新在Java中引入了多重继承的概念，使继承关系设计又多了一层复杂性。
        ((Fi1) () -> "Nina").hello();
        ((Fi2) () -> "Nina").hello();
        ((Fi3) () -> "Nina").hello();
        ((Fi4) () -> "Nina").hello();
        ((Fi5) () -> "Nina").hello();

        //为接口添加静态方法方便了工具类的管理。
        Fi1.hello(() -> "Nina");

        //返回Optional对象比返回null要好，配合Lambda表达式能大量减少代码中if (xxx == null)的出现。
        Optional<String> o = of("Nina");
        o.ifPresent(n -> System.out.println("Hello " + n + "."));
        o = empty();
        System.out.println("Hello " + o.orElse("world."));

        //::操作符进一步简化了Lambda表达式参数传递形式。注意：如果同时出现Lambda表达式强制转型加::操作符，Eclipse格式化器会无法正常工作。
        Fi6.hello(n -> n.length());
        Fi6.hello(String::length);
        Fi1.hello(String::new);
        Fi7.hello(String[]::new); //数组的初始参数是int型的数字。
    }

    static void testOverload(Predicate<Integer> lambda) {
        System.out.println("Predicate<Integer>");
    }

    static void testOverload(IntPredicate lambda) {
        System.out.println("IntPredicate");
    }

    @FunctionalInterface
    public static interface Fi1 { //即使不加标注，函数接口也可以正常使用，标注只是在编译时检测，并且提醒后面的维护者这是一个函数接口。

        public abstract String getName();

        public default void hello() {
            System.out.println("Hello " + getName() + ". (Fi1)");
        }

        public static void hello(Fi1 fi1) {
            System.out.println("Hello " + fi1.getName() + ". (Fi1 Static)");
        }

    }

    @FunctionalInterface
    public static interface Fi2 {

        public abstract String getName();

        public default void hello() {
            System.out.println("Hello " + getName() + ". (Fi2)");
        }

    }

    @FunctionalInterface
    public static interface Fi3 extends Fi1 {

        @Override
        public default void hello() {
            System.out.println("Hello " + getName() + ". (Fi3)");
        }

    }

    @FunctionalInterface
    public static interface Fi4 extends Fi1, Fi2 {

        @Override
        public default void hello() { //这个覆写是必须的，否则编译器会因为无法确定使用哪一个默认方法而报错。
            Fi1.super.hello(); //这里可以选择使用Fi1还是Fi2的默认方法。
        }

    }

    @FunctionalInterface
    public static interface Fi5 extends Fi1, Fi3 {

        @Override
        public default void hello() { //这个覆写不是必须。
            //Fi1.super.hello(); //Fi1的默认方法无法调用到，因为Fi3继承了Fi1，所以会完全覆盖掉Fi1的默认方法。
            Fi3.super.hello();
        }

    }

    @FunctionalInterface
    public static interface Fi6 {

        public abstract int length(String name);

        public static void hello(Fi6 fi6) {
            System.out.println("Hello, name length is " + fi6.length("Nina") + ".");
        }

    }

    @FunctionalInterface
    public static interface Fi7 {

        public abstract String[] names(int length);

        public static void hello(Fi7 fi7) {
            System.out.println("Hello, names length is " + fi7.names(7).length + ".");
        }

    }

}
