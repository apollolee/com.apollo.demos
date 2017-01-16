package com.apollo.demos.base.scala;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class TypeParameterization4J {

    public static void main(String[] args) {
        //ArrayList<Object> list1 = new ArrayList<String>(); //Java中不支持协变。
        ArrayList<?> list2 = new ArrayList<String>(); //?是Java对协变的一种妥协。

        String[] a1 = new String[] { "abc" };
        Object[] a2 = a1; //Java中数组类型是协变的。
        a2[0] = new Integer(17); //这里编译没有问题，但会得到一个运行时异常，告诉你不能这么干。
        String s = a1[0];
    }

}
