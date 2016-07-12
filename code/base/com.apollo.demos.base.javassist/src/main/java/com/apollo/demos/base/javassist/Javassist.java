/*
 * 此代码创建于 2016年6月15日 下午2:55:03。
 */
package com.apollo.demos.base.javassist;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;
import static javassist.CtClass.booleanType;
import static javassist.CtClass.intType;
import static javassist.CtClass.longType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class Javassist {

    public static void main(String[] args) throws Exception { //检查异常会非常多，这里不单独处理。
        //getDefault的pool中带有系统路径，如果不带系统路径，像String这种常用类都没有，这些基础类分布在方法的参数和返回值。
        ClassPool pool = ClassPool.getDefault();

        //修改C1类的hollo方法。
        CtClass cc = pool.get("com.apollo.demos.base.javassist.Javassist$C1");
        //CtClass cc = pool.get(C1.class.getName()); //这样写是不行的，会触发C1类提前加载。
        CtMethod cm = cc.getDeclaredMethod("hollo");
        cm.setBody("System.out.println(\"Hello, javassist world.\");");
        cm.insertBefore("System.out.println(\"Welcome.\");");
        cm.insertAfter("System.out.println(\"Goodbye.\");");
        cc.toClass(); //这里只是触发当前类加载器加载被修改后的C1类字节码。注意：如果当前类加载器加载过C1，那么此次会抛异常，修改后的C1不会在下面体现。
        //cc.defrost(); //解冻后就可以再次修改。
        //cm.insertAfter("System.out.println(\"Goodbye.\");"); //类被加载后（toClass被调用后），再修改类就会报类被冻结的异常。和老涂碰到的yang类冻结异常是一样，这种异常很可能是yang内部BUG。
        //cc.toClass(); //如果不更新类，即使再次修改成功也不会有所体现，但再次调用toClass并不意味着可以动态更新类，而会收到一个重复类加载异常。
        C1.hollo(); //显示一下修改后的成果。

        //Javassist工作在字节码和类加载器之间，字节码->篡改->类加载，or 字节码->篡改->写到字节码文件（.class文件）。
        //Javassist的能力是修改类的字节码，能否动态改变类的行为取决于类加载器能否支持类的卸载和加载，Javassist本身并不具备动态性。
        //Javassist主要用于Maven插件开发，以及动态创建新的Class，因为新的Class都是没有被加载过的。另外一种潜在用途是结合OSGi，动态篡改bundle，并控制OSGi框架update bundle。

        //动态创建实现I1接口的C2类。
        cc = pool.makeClass("com.apollo.demos.base.javassist.dynamic.C2");
        cc.setInterfaces(new CtClass[] { pool.get(I1.class.getName()) });
        cm = CtNewMethod.make("public void hollo() { System.out.println(\"Hello, dynamic C2.\"); }", cc);
        cc.addMethod(cm); //即使CtNewMethod.make调用中带了cc，这里cc依然需要addMethod一下。
        Object o = cc.toClass().newInstance(); //动态创建的类虽然有名字，但只能通过反射来new对象。
        ((I1) o).hollo(); //显示一下。

        //修改C3类，最常见的例子是方法前后加打印日志。
        cc = pool.get("com.apollo.demos.base.javassist.Javassist$C3");
        cm = cc.getDeclaredMethod("move");
        //关联方法参数的时，需要在目标类编译时加上 -g 选项，该选项可以把本地变量的声明保存在class文件中，默认是不加这个参数的。
        //因为默认一般不加这个参数，所以Javassist也提供了一些特殊的变量来代表方法参数：$1,$2,$args等等。
        //要注意的是，插入的source文本中不能引用方法本地变量的声明，但是可以允许声明一个新的方法本地变量，除非目标类编译时加入-g选项。
        cm.insertBefore("System.out.println(\"[dx = \" + $1 + \" , dy = \" + $2 + \"]\");");
        cm.insertAfter("System.out.println(\"[x = \" + $0.m_x + \" , y = \" + $0.m_y + \"]\");"); //$0等同于this。
        cc.toClass();
        C3 c3 = new C3();
        c3.move(1, 1);
        c3.move(2, 2);
        c3.move(3, 3);

        //修改C4类，如何选取想修改的东西。
        cc = pool.get("com.apollo.demos.base.javassist.Javassist$C4");
        CtField cf = cc.getField("m_a"); //最简单的选取方式。
        cf = cc.getField("m_a", "I"); //descriptor选取，"I"表示int。字段名是唯一的，所以选择一个字段是不用加类型的，一般加类型是为了确定这个字段是否按预取的类型存在。
        cf = cc.getField("m_b", "Ljava/lang/String;"); //如果类型是非源生类型，有特定的descriptor格式。
        cm = cc.getMethod("set", "(J)V"); //方法的参数类型和返回类型也是可以descriptor选取的，()中为参数，()后为返回值。由于方法可以重载，所以方法加descriptor选取会使用更多一些。
        cm = cc.getDeclaredMethod("set", new CtClass[] { longType }); //使用CtClass指明类型可读性更高，而且不需要指明返回值类型，尽管这样通常还是不如descriptor选取简洁。
        cm = cc.getMethod("set", "(Ljava/lang/Integer;C)Ljava/lang/Boolean;"); //非源生类型。注意类名后面的分号，这个不是分隔符，而是类型的一部分，即使单写也有加分号。
        cm = cc.getMethod("set", "()V"); //无参数也可以选择。
        System.out.println(cf.getFieldInfo().getDescriptor()); //字段descriptor不知道怎么写时，可以输出看一下。
        System.out.println(cm.getSignature()); //方法descriptor也可以输出。
        cf.setType(booleanType); //字段拿到后可以干些很多在正常途径干不了的事情，比如改变字段类型。
        cm.setBody("{m_b = true;System.out.println(\"m_b = \" + m_b);}"); //注意加{}。
        //cm.insertBefore("m_b = true;"); //这里不能用insert，由于m_b的类型已经被修改，任何set中原本关联m_b的字节码已经发生变化了。
        cm = cc.getMethod("set", "(Ljava/lang/Integer;C)Ljava/lang/Boolean;");
        //cm.setGenericSignature("(Ljava/lang/Integer;C)V"); //想改变返回值，这个做不到，目前还没找到方法，可能不行。
        cm.addParameter(intType); //但是可以加个参数。
        Class<?> c = cc.toClass();
        C4 c4 = new C4();
        c4.set();
        //c4.set(1, '1'); //不能直接调用，会抛java.lang.NoSuchMethodError，因为方法已经多加了一个参数。
        Method m = c.getMethod("set", Integer.class, Character.TYPE, Integer.TYPE); //这种在这只能用反射调用了，注意源生类型的设置方式。
        m.invoke(c4, 1, '1', 1); //方法定了，这里类型就无所谓了，会自动装箱拆箱。

        //通常，对不是自己的代码需要做充分的假设，各种不存在都需要处理。
        cc = ofNullable(pool.getOrNull("com.apollo.demos.base.javassist.Javassist$C5")).orElse(pool.makeClass("com.apollo.demos.base.javassist.Javassist$C5"));
        try {
            cc.getField("m_a", "I");

        } catch (NotFoundException ex) {
            cc.addField(CtField.make("private int m_a;", cc));
        }
        try {
            cc.getConstructor("I");

        } catch (NotFoundException ex) {
            cc.addConstructor(CtNewConstructor.make("public C5(int a) {m_a = a;}", cc));
        }
        try {
            cc.getMethod("getA", "()I");

        } catch (NotFoundException ex) {
            cc.addMethod(CtNewMethod.make("public int getA() {return m_a;}", cc));
        }
        c = cc.toClass();
        o = c.getConstructor(Integer.TYPE).newInstance(1);
        System.out.println(c.getMethod("getA").invoke(o));

        //读取CLASS级别的标注是maven插件中很常见的用法。
        //Javassist不具备篡改标注的能力，因为标注的工作范围大都集中在SOURCE和CLASS这两个级别上，这两个一般是在Javassist前端处理，所以Javassist即使能篡改标注也无太多应用场景。
        cc = pool.get("com.apollo.demos.base.javassist.Javassist$E1"); //枚举底层也是一个Class。
        for (CtField f : cc.getFields()) {
            A1 a = (A1) f.getAnnotation(A1.class);
            System.out.println(f.getName() + ":" + a.value());
        }

        //Javassist不支持jdk 1.5以上的语法。
        cc = pool.makeClass("com.apollo.demos.base.javassist.Javassist$C6");
        //cm = CtNewMethod.make("public void a() { java.util.Objects.hash(\"123\"); }", cc); //...等效于[]，虽然javac支持，但Javassist并不支持，这也可以证明Javassist并没有调用javac。
        cm = CtNewMethod.make("public void a() { java.util.Objects.hash(new Object[] { \"123\" }); }", cc);
        cc.addMethod(cm);
        //cm = CtNewMethod.make("public void b() { java.util.Objects.equals(1, 2); }", cc); //很遗憾，自动装箱拆箱也不支持了。
        cm = CtNewMethod.make("public void b() { java.util.Objects.equals(Integer.valueOf(1), Integer.valueOf(2)); }", cc);
        cc.addMethod(cm);
        //cm = CtNewMethod.make("public <T> String c(T a) { a.toString(); }", cc); //很遗憾，也不支持模板。
        cc.toClass();
    }

    public static class C1 {

        public static void hollo() {
            System.out.println("Hello, world.");
        }

    }

    public static interface I1 {

        public abstract void hollo();

    }

    @SuppressWarnings("unused")
    public static class C3 {

        private int m_x = 0;

        private int m_y = 0;

        public void move(int dx, int dy) {
            m_x += dx;
            m_y += dy;
        }

    }

    @SuppressWarnings("unused")
    public static class C4 {

        private int m_a;

        private String m_b;

        public void set(long a) {
        }

        public Boolean set(Integer a, char b) {
            System.out.println("false");
            return FALSE;
        }

        public void set() {
            System.out.println("m_b = " + m_b);
        }

    }

    /*public static class C5 {

        public int getA() {
            return 5;
        }

    }*/

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.CLASS)
    public static @interface A1 {

        public int value();

    }

    public static enum E1 {

        @A1(1)
        A,

        @A1(2)
        B,

        @A1(3)
        C;

    }

}
