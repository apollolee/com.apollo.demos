/*
 * 此代码创建于 2022年2月2日 下午8:42:42。
 */
package com.apollo.demos.base.javaagent.premain.test;

public class Test {

    /**
     * 1.先编译premain，再找到premain-1.0.0.jar在你的maven本地仓库中的位置。
     * 2.配置VM arguments为：-javaagent:d:\apollo\work\development\maven\local-repository\com\apollo\demos\base\javaagent\premain\1.0.0\premain-1.0.0.jar=abc
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("main start");

        try {
            Thread.sleep(3000);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("main end");
    }

}
