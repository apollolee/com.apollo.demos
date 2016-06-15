/*
 * 此代码创建于 2016年6月15日 下午2:55:03。
 */
package com.apollo.demos.base.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class Javassist {

    public static void main(String[] args) {
        //getDefault的pool中带有系统路径，如果不带系统路径，像String这种常用类都没有，这些基础类分布在方法的参数和返回值。
        ClassPool pool = ClassPool.getDefault();

        //修改C1类的hollo方法。
        try {
            CtClass cc = pool.get("com.apollo.demos.base.javassist.Javassist$C1");
            CtMethod cm = cc.getDeclaredMethod("hollo");
            cm.setBody("System.out.println(\"Hello, javassist world.\");");
            cm.insertBefore("System.out.println(\"Welcome.\");");
            cm.insertAfter("System.out.println(\"Goodbye.\");");
            cc.toClass(); //这里只是触发当前类加载器加载被修改后的C1类字节码。注意：如果当前类加载器加载过C1，那么此次会抛异常，修改后的C1不会在下面体现。
            C1.hollo(); //显示一下修改后的成果。

        } catch (NotFoundException | CannotCompileException ex) {
            ex.printStackTrace();
        }

        //Javassist的能力是修改类的字节码，能否动态改变类的行为取决于类加载器能否支持类的卸载和加载，Javassist本身并不具备动态性。
        //Javassist主要用于Maven插件开发，以及动态创建新的Class，因为新的Class都是没有被加载过的。
        //动态创建实现I1接口的C2类。
        try {
            CtClass cc = pool.makeClass("com.apollo.demos.base.javassist.dynamic.C2");
            cc.setInterfaces(new CtClass[] { pool.get(I1.class.getName()) });
            CtMethod cm = CtNewMethod.make("public void hollo() { System.out.println(\"Hello, dynamic C2.\"); }", cc);
            cc.addMethod(cm);
            I1 o = (I1) cc.toClass().newInstance(); //动态创建的类虽然有名字，但只能通过反射来new对象。
            o.hollo(); //显示一下。

        } catch (NotFoundException | CannotCompileException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        //修改C3类。
        try {
            CtClass cc = pool.get("com.apollo.demos.base.javassist.Javassist$C3");
            CtMethod cm = cc.getDeclaredMethod("move");
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

        } catch (NotFoundException | CannotCompileException ex) {
            ex.printStackTrace();
        }
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

}
