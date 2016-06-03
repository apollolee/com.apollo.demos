/*
 * 此代码创建于 2015年11月21日 上午10:53:44。
 */
package com.apollo.demos.base.test.powermock;

public class C {

    private static void throwException() {
        throw new RuntimeException("Do not invoke.");
    }

    public static void doNotInvoke() {
        throwException();
    }

}
