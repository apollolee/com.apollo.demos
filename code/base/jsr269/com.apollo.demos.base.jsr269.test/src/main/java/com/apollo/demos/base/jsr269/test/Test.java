/*
 * 此代码创建于 2022年2月3日 下午9:30:52。
 */
package com.apollo.demos.base.jsr269.test;

import static com.apollo.demos.base.jsr269.LogLevel.DEBUG;

import com.apollo.demos.base.jsr269.Log;

public class Test {

    public static void main(String[] args) {
        test();
    }

    @Log(level = DEBUG)
    public static void test() {
        System.out.println("jsr269.test");
    }

}
