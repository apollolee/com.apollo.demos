/*
 * 此代码创建于 2015年11月19日 下午1:46:10。
 */
package com.apollo.demos.test.junit;

public class Fibonacci {

    public static int get(int n) {
        if (n <= 0) {
            return 0;

        } else if (n == 1) {
            return 1;

        } else {
            return get(n - 1) + get(n - 2);
        }
    }

}
