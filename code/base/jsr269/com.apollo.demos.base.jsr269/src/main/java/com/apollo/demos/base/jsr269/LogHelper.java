/*
 * 此代码创建于 2022年3月16日 下午10:05:22。
 */
package com.apollo.demos.base.jsr269;

public interface LogHelper {

    static void log(Runnable task, String level) {
        System.out.println(level + ":start");
        task.run();
        System.out.println(level + ":end");
    }

}
