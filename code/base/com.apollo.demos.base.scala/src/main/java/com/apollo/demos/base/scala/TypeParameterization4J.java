package com.apollo.demos.base.scala;

import java.sql.SQLException;
import java.sql.SQLTransientException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class TypeParameterization4J {

    public static void main(String[] args) {
        //ArrayList<Object> list1 = new ArrayList<String>(); //Java中看起来不支持协变。
        ArrayList<?> list2 = new ArrayList<String>(); //换成?就可以了。

        ArrayList<? extends Object> list3 = new ArrayList<String>(); //其实Java中也支持协变，?是? extends Object协变的简写，但很多人误解为?是 Object的简写。
        ArrayList<? super SQLTransientException> list4 = new ArrayList<SQLException>(); //Java中也支持逆变。
        //实际上Java在泛型上并不提出协变和逆变概念，取而代之的是Java泛型中重要的PECS法则。

        String[] a1 = new String[] { "abc" };
        Object[] a2 = a1; //Java中数组类型是固定协变的，不可以非协变。
        a2[0] = new Integer(17); //这里编译没有问题，但会得到一个运行时异常，告诉你不能这么干。
        String s = a1[0];
    }

}

interface Queue4J<T1, T2 extends T1> { //Java中定义和Scala中的同样能力的协变，需要双参，T1的定义显然是非常拖沓。

    T2 head();

    Queue4J<T1, T2> tail();

    Queue4J<T1, T2> append(T2 x);

}
